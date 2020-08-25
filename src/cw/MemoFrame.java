/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// 텍스트 메모 프레임을 나타내는 클래스
@SuppressWarnings({ "serial", "unused" })
public class MemoFrame extends FrameManager implements ActionListener {
	// 프레임 구성에 필요한 변수들
	JLabel lbTitle, lbDescription, lbDate;
	JTextField tfTitle;
	JTextArea taDescription;
	JButton btnAdd, btnCancel, btnUpdate, btnDelete;

	DiaryFrame df;
	String userId, num;	// 넘겨받을 userid와 num을 저장할 변수

	// 메모를 추가할 때의 생성자
	public MemoFrame(String userid, DiaryFrame df) {
		this.df = df;
		this.userId = userid;
		addMemeoUI();	// 새 메모를 추가할 때의 UI
	}
	// 메모를 수정하거나 삭제할 때의 생성자
	public MemoFrame(String userid, String no, DiaryFrame df) {
		this.num = no;
		this.userId = userid;
		this.df = df;
		selectedMemoUI();	// 선택된 메모를 수정하거나 삭제할 때의 UI
		
		DiaryManager dm = new DiaryManager();
		TextMemo tm = dm.getSelectedMemo(no);	// 선택된 로우의 글넘버의 메모 객체를 생성
		viewData(tm);	// 선택된 메모객체를 화면에 표시
	}

	// TextMemo의 메모정보를 가지고 화면에 세팅해주는 메소드
	private void viewData(TextMemo vMemo) {
		String userId = vMemo.getUserId();
		String date = vMemo.getDate();
		String title = vMemo.getTitle();
		String description = vMemo.getDescription();

		tfTitle.setText(title);
		lbDate.setText(date);
		taDescription.setText(description);
	}
	// 프레임 테이블에서 한 로우를 선택했을 때의 메모 프레임(수정, 삭제용)
	private void selectedMemoUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setTitle("메모 수정");
		setSize(400, 500);
		setLayout(null);
		// 현재 디스플레이의 가운데에 표시
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		// 컴포넌트 생성
		lbTitle = new JLabel();
		lbDescription = new JLabel();
		lbDate = new JLabel();
		tfTitle = new JTextField();
		taDescription = new JTextArea();
		btnUpdate = new JButton();
		btnDelete = new JButton();
		btnCancel = new JButton();
		
		// 컴포넌트 등록
		createJLabel(c, lbTitle, "제목 : ", 50, 25, 10, 10);
		createJLabel(c, lbDate, "날짜 : ", 150, 15, 10, 35);
		createJLabel(c, lbDescription, "내용 : ", 50, 25, 10, 50);
		createJTextField(c, tfTitle, "", 330, 25, 50, 10);
		createJTextArea(c, taDescription, "", 330, 280, 50, 55);
		createJButton(c, btnUpdate, "수정", 100, 35, 25, 375);
		createJButton(c, btnDelete, "삭제", 100, 35, 150, 375);
		createJButton(c, btnCancel, "취소", 100, 35, 275, 375);
		
		// 버튼에 액션이벤트리스너 등록
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		
		setVisible(true);
		setResizable(false);
	}
	// 새 메모를 추가할 때의 메모프레임(새 메모 추가용)
	private void addMemeoUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setTitle("메모 추가");
		setSize(400, 500);
		setLayout(null);
		// 현재 디스플레이의 가운데에 표시
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		// 컴포넌트 생성
		lbTitle = new JLabel();
		lbDescription = new JLabel();
		tfTitle = new JTextField();
		taDescription = new JTextArea();
		btnAdd = new JButton();
		btnCancel = new JButton();
		
		// 컴포넌트 등록
		createJLabel(c, lbTitle, "제목 : ", 50, 25, 10, 10);
		createJLabel(c, lbDescription, "내용 : ", 50, 25, 10, 50);
		createJTextField(c, tfTitle, "", 330, 25, 50, 10);
		createJTextArea(c, taDescription, "", 330, 300, 50, 50);
		createJButton(c, btnAdd, "추가", 100, 35, 90, 375);
		createJButton(c, btnCancel, "취소", 100, 35, 210, 375);
		
		// 버튼에 액션이벤트리스너 등록
		btnAdd.addActionListener(this);
		btnCancel.addActionListener(this);
		
		setVisible(true);
		setResizable(false);
	}
	
	// 액션이벤트 처리를 위한 메소드
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnAdd) {	// 추가 버튼이 눌렸을 때
			insertButton();	// 추가 실행 메소드를 호출
			dispose();	// 현재 프레임을 종료
		} else if (ae.getSource() == btnCancel) {	// 취소 버튼이 눌렸을 때
			dispose();	// 현재 프레임을 종료
			new DiaryFrame(userId);	// 다시 DiaryFrame을 호출
		} else if (ae.getSource() == btnUpdate) {	// 수정 버튼이 눌렸을 때
			int x = JOptionPane.showConfirmDialog(this, "정말 수정하시겠습니까?", "수정", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				updateButton();	// 수정 실행 메소드를 호출
				dispose();	// 현재 프레임을 종료
			} else {
				JOptionPane.showMessageDialog(this, "수정을 취소하였습니다.");
			}
		} else if (ae.getSource() == btnDelete) {	// 삭제 버튼이 눌렸을 때
			int x = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				deleteButton();	// 삭제 실행 메소드를 호출
				dispose();	// 현재 프레임을 종료
			} else {
				JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다.");
			}
		}
		df.jTableRefresh();	// 프레임 테이블을 새로고침함
	}
	// 삭제 버튼이 눌렸을 때의 실행 메소드
	private void deleteButton() {
		TextMemo tm = getViewData();	// 메모프레임에 나타난 정보를 메모객체에 담음
		DiaryManager dm = new DiaryManager();
		boolean ok = dm.deleteMemo(tm);	// 실제 DB에서 메모를 삭제
		
		if (ok) {	// 삭제에 성공했다면
			JOptionPane.showMessageDialog(this, "삭제완료");
			dispose(); // 현재 프레임 종료
			new DiaryFrame(userId);	// 다시 DiaryFrame호출
		} else {	// 삭제에 실패했다면
			JOptionPane.showMessageDialog(this, "삭제실패");
		}
	}
	// 수정 버튼이 눌렸을 때의 실행 메소드
	private void updateButton() {
		TextMemo tm = getViewData();	// 메모프레임에 나타난 정보를 메모객체에 담음
		DiaryManager dm = new DiaryManager();
		boolean ok = dm.updateMemo(tm);	// 실제 DB에서 메모를 수정

		if (ok) {	// 수정에 성공했다면
			JOptionPane.showMessageDialog(this, "수정되었습니다.");
			dispose(); // 현재 프레임 종료
			new DiaryFrame(userId);	// 다시 DiaryFrame호출
		} else {	// 수정에 실패했다면
			JOptionPane.showMessageDialog(this, "수정실패: 값을 확인하세요");
		}
	}
	// 추가 버튼이 눌렸을 때의 실행 메소드
	private void insertButton() {
		TextMemo tm = getViewData();	// 메모프레임에 나타난 정보를 메모객체에 담음
		DiaryManager dm = new DiaryManager();
		boolean ok = dm.insertMemo(tm);	// 실제 DB에서 메모를 추가

		if (ok) {	// 추가에 성공했다면
			JOptionPane.showMessageDialog(this, "메모가 추가되었습니다.");
			dispose();	// 현재 프레임 종료
			new DiaryFrame(userId);	// 다시 DiaryFrame호출
		} else {	// 추가에 실패했다면
			JOptionPane.showMessageDialog(this, "메모가 정상적으로 추가되지 않았습니다.");
		}
	}
	// MemoFrame에 저장된 data를 메모 객체에 담음
	public TextMemo getViewData() {
		TextMemo tm = new TextMemo();
		// 메모프레임에서 정보를 얻어 문자열에 저장
		String userid = userId;
		String title = tfTitle.getText();
		String description = taDescription.getText();
		String no = num;
		
		LocalDate currentDate = LocalDate.now();	// 현재 시스템의 날짜 정보
		LocalTime currentTime = LocalTime.now(); 	// 현재 시스템의 시간 정보
		String date = currentDate.toString() +" | "+currentTime.toString();	// 날짜와 시간 정보를 합친 문자열
		
		// 저장됨 문자열을 메모 객체에 저장
		tm.setUserId(userid);
		tm.setDate(date);
		tm.setTitle(title);
		tm.setDescription(description);
		tm.setNum(no);
		
		return tm;	// 메모객체 tm을 리턴
	}
}
