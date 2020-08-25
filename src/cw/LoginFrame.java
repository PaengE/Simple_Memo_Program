/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// 로그인 프레임 클래스
@SuppressWarnings("serial")
public class LoginFrame extends FrameManager implements ActionListener {
	// 프레임에 쓰일 변수들
	JLabel lbProgName, lbId, lbPwd;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton btnLogin, btnJoin, btnExit;
	LoginManager lm;
	
	static String dbFileURL = "jdbc:sqlite:diary.db";	// 사용할 DB 이름
	
	// 생성자
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setTitle("팽이 다이어리");
		setSize(400, 400);
		setLayout(null);
		// 현재 디스플레이의 가운데에 표시
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		// 컴포넌트 생성
		lbProgName = new JLabel();
		lbId = new JLabel();
		lbPwd = new JLabel();
		tfId = new JTextField();
		pfPwd = new JPasswordField();
		btnLogin = new JButton();
		btnJoin = new JButton();
		btnExit = new JButton();
		
		// 프레임에 컴포넌트 부착
		createJLabel(c, lbProgName, "PRIVATE DIARY", 100, 50, 150, 50);
		createJLabel(c, lbId, "아이디", 50, 25, 90, 130);
		createJLabel(c, lbPwd, "비밀번호", 100, 25, 89, 170);
		createJTextField(c, tfId, "", 150, 25, 175, 130);
		createJPasswordField(c, pfPwd, "", 150, 25, 175, 170);
		createJButton(c, btnLogin, "로그인", 100, 25, 40, 250);
		createJButton(c, btnJoin, "회원가입", 100, 25, 150, 250);
		createJButton(c, btnExit, "종료", 100, 25, 260, 250);
		
		// 버튼에 액션리스너 등록
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		btnExit.addActionListener(this);
		
		setVisible(true);
		setResizable(false);
		
		Connection conn = null;	// DB 연결 객체
		Statement stmt = null;	// DB 쿼리문을 담을 객체
		String sql = "";		// sql 문자열
		
		// DB 연결 시도
		try {
			Class.forName("org.sqlite.JDBC");	// sqlite 드라이버를 로드
			conn = DriverManager.getConnection(dbFileURL);	// DB 연결
			
			// user 이름의 테이블이 이미 존재하지 않으면 생성
			sql = "CREATE TABLE IF NOT EXISTS user(\r\n" + 
					"	userid VARCHAR(25) PRIMARY KEY,\r\n" + 
					"	pwd VARCHAR(25) NOT NULL\r\n" + 
					");";
			stmt = conn.createStatement();	// DB에 넘길 객체로 초기화
			int count = stmt.executeUpdate(sql);	// sql 실행
			if(count == 0) {
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
			try {
				if(stmt != null)
					stmt.close();
				if(conn != null) 
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 액션리스너 actionPerformed override
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		LoginManager lm = new LoginManager();
		if (e.getSource() == btnLogin) {	// 로그인 버튼을 눌렀을 때
			String userId = lm.login(tfId.getText(), pfPwd.getText());	// login을 호출 후 리턴값을 받음
			if(!userId.equals("")) {	// 리턴값이 빈 문자열이 아니라면(로그인 성공한 것)
				new DiaryFrame(userId);	// userId를 넘기면서 DiaryFrame을 호출
				System.out.println("LOGIN SUCCESS: userId = " +userId);
				dispose();	// 현재 프레임 종료
			} else {	// 리턴값이 빈 문자열이라면(로그인 실패)
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력해주세요.");
			}
		} else if (e.getSource() == btnJoin) {	// 회원가입 버튼을 눌렀을 때
			new JoinForm();	// 회원가입 폼을 호출
		} else if (e.getSource() == btnExit) {	// 종료 버튼을 눌렀을 때
			int x = JOptionPane.showConfirmDialog(this, "정말 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				System.exit(0);	// 프로그램 종료
			} 
		}
	}
	
}
