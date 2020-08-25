/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


// 회원가입 Form을 나타내주는 클래스
@SuppressWarnings("serial")
public class JoinForm extends JFrame implements ActionListener {
	// 프레임을 위한 변수들
	JPanel p;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton btnInsert, btnCancel, btnDelete;

	GridBagLayout gb;
	GridBagConstraints gbc;
	// 생성자
	JoinForm(){
		createUI();
	}
	// 회원가입 폼 생성
	private void createUI() {
		// 현재 디스플레이의 가운데에 표시
		setSize(350, 200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - super.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - super.getHeight() / 2);
		setLocation(screenX, screenY);
		this.setTitle("회원가입");
		
		// 그리드백레이아웃 생성 및 레이아웃 지정
		gb = new GridBagLayout();
		setLayout(gb);
		// 상하좌우를 꽉채움
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		// 남는 여백 균등분배
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// 아이디 라벨 및 텍스트필드 등록
		JLabel lbId = new JLabel("아이디 : ");
		tfId = new JTextField(20);
		gbAdd(lbId, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 3, 1);

		// 비밀번호 라벨 및 텍스트필드 등록
		JLabel lbPwd = new JLabel("비밀번호 : ");
		pfPwd = new JPasswordField(20);
		gbAdd(lbPwd, 0, 1, 1, 1);
		gbAdd(pfPwd, 1, 1, 3, 1);

		// 새로운 패널을 생성하고 버튼그룹을 부착한 후
		// 버튼그룹을 그리드백레이아웃에 부착
		JPanel pButton = new JPanel();
		btnInsert = new JButton("가입");
		btnDelete = new JButton("탈퇴");
		btnCancel = new JButton("취소");
		pButton.add(btnInsert);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 10, 4, 1);

		// 버튼마다 액션리스너 등록
		btnInsert.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	// 그리드백레이아웃에 붙이는 메소드
	// (x,y)좌표에 w*h개의 셀을 차지
	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);	// 컴포넌트 사이에 여백 주기
		add(c, gbc);
	}
	
	
	// 액션리스너 actionPerformed override
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		LoginManager lm = new LoginManager();
		if (e.getSource() == btnInsert) {	// 눌려진 버튼이 가입 버튼일 때
			// 사용자 객체 생성 후 해당 텍스트필드 값으로 객체 초기화
			User user = new User();
			user.setId((String)tfId.getText());
			user.setPwd((String)pfPwd.getText());
			
			lm.join(user.getId(), user.getPwd()); // 사용자 객체의 정보를 얻어 join을 호출
		} else if (e.getSource() == btnDelete) {	// 눌려진 버튼이 탈퇴 버튼일 때
			int x = JOptionPane.showConfirmDialog(this, "정말 탈퇴하시겠습니까?", "탈퇴", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				lm.delete(tfId.getText(), pfPwd.getText());	// 해당 텍스트필드에 있는 값으로 delete를 호출
			}
		} else if (e.getSource() == btnCancel) {	// 눌려진 버튼이 취소 버튼일 때
			dispose();	// 현재 해당 프레임 종료
		}
	}
}
