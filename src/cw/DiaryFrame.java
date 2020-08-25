/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

// Diary프레임을 구성하는 클래스
@SuppressWarnings("serial")
public class DiaryFrame extends JFrame implements MouseListener, ActionListener{
	// 프레임 구성에 쓰이는 변수
	@SuppressWarnings("rawtypes")
	Vector v, cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane scroll;
	JPanel panel;
	JButton btnAddMemo, btnLogout;
	String userId;
	
	static String dbFileURL = "jdbc:sqlite:diary.db";	// 사용할 DB 이름
	
	// 생성자
	public DiaryFrame(String userId) {
		super("팽이 다이어리");
		this.userId = userId;
		setSize(1000, 400);
		// 현재 디스플레이의 가운데에 표시
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		DiaryManager dm = new DiaryManager();
		v = dm.getMemosList(userId);	// userId로 검색한 레코드들을 벡터에 저장
		cols = getColumn();	// 프레임 테이블에 저장할 속성명 벡터 생성
		
		model = new DefaultTableModel(v, cols) {	// 테이블모델 생성
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jTable = new JTable(model);	// 새 JTable 생성
		jTable.getTableHeader().setReorderingAllowed(false);	// 프레임 테이블 컬럼의 이동 방지
		jTable.getTableHeader().setResizingAllowed(false);	// 프레임 테이블 컬럼의 사이즈를 고정
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	// 프레임 테이블 행을 하나만 선택하게끔 지정
		// 각 속성명의 컬럼너비 조정
		jTable.getColumn("게시물넘버").setPreferredWidth(1);
		jTable.getColumn("날짜").setPreferredWidth(40);
		jTable.getColumn("제목").setPreferredWidth(50);
		jTable.getColumn("내용").setPreferredWidth(500);
		// 스크롤 생성 및 추가
		scroll = new JScrollPane(jTable);
		add(scroll);
		
		// 버튼 패널 생성 및 추가
		panel = new JPanel();
		btnAddMemo = new JButton("메모 추가");
		btnLogout = new JButton("로그아웃");
		panel.add(btnAddMemo);
		panel.add(btnLogout);
		add(panel, BorderLayout.SOUTH);
		
		// 프레임 테이블과 버튼에 마우스이벤트리스너와 액션이벤트리스너를 각각 등록
		jTable.addMouseListener(this);
		btnAddMemo.addActionListener(this);
		btnLogout.addActionListener(this);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Connection conn = null;	// 연결 객체
		Statement stmt = null;	// DB에 쿼리문을 담을 객체
		String sql = "";	// sql문을 담을 문자열
		try {
			Class.forName("org.sqlite.JDBC");	// sqlite 드라이버를 로드
			conn = DriverManager.getConnection(dbFileURL);	// DB 연결
			
			// text_memo 테이블이 없으면 생성
			// text_memo테이블의 userid가 user테이블의 userid를 참조함(외래키 지정)
			sql = "CREATE TABLE IF NOT EXISTS text_memo(\r\n" + 
					"	idx INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
					"	userid VARCHAR(25) NOT NULL,\r\n" + 
					"	date VARCHAR(25),\r\n" + 
					"	title VARCHAR(100),\r\n" + 
					"	description VARCHAR(3000),\r\n" + 
					"	FOREIGN KEY(userid)" + 
					"	REFERENCES user(userid) ON DELETE CASCADE" + 
					");";
			stmt = conn.createStatement();	// DB에 넘길 객체로 초기화
			int count = stmt.executeUpdate(sql);	// sql 실행
			if(count == 0) {
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
			try {
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// 
				e.printStackTrace();
			}
		}
	}
	// 프레임 테이블의 1행에 위치할 값들을 정의
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector getColumn() {	
		// 1행에 위치할 속성 명들을 벡터에 담음
		Vector col = new Vector();
		col.add("게시물넘버");
		col.add("날짜");
		col.add("제목");
		col.add("내용");
		
		return col;	// col 벡터 리턴
	}
	// 프레임 테이블을 새로고침(다시 생성)
	public void jTableRefresh() {
		DiaryManager dm = new DiaryManager();
		DefaultTableModel model = new DefaultTableModel(dm.getMemosList(userId), getColumn());	// 새 테이블모델 생성
		jTable.setModel(model);	// JTable에 모델을 지정
	}

	// 테이블 행을 클릭했을때의 이벤트 처리 메소드
	@Override
	public void mouseClicked(MouseEvent e) {
		int r = jTable.getSelectedRow();	// 선택한 행이 몇번째 행인지를 계산
		String no = (String)jTable.getValueAt(r, 0);	// r번째 행의 0번째 속성명(글넘버)을 저장
		dispose();	// 현재 프레임 종료
		new MemoFrame(userId, no, this);	// 로그인한 userid, 선택된 행의 글넘버, 현재 DiaryFrame을 MemoFrame에 넘김
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	// 버튼 액션리스너 이벤트처리 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAddMemo) {	// 메모추가 버튼을 눌렀을 때
			dispose();	// 현재 프레임을 종료
			new MemoFrame(userId, this);	// 로그인한 userid와 현재 DiaryFrame을 MemoFrame에 넘김
		} else if(e.getSource() == btnLogout) {	// 로그아웃 버튼을 눌렀을 때
			dispose();	// 현재 프레임을 종료
			new LoginFrame();	// 다시 로그인프레임을 띄움
		}
	}
}
