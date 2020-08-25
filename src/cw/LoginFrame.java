/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
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

// �α��� ������ Ŭ����
@SuppressWarnings("serial")
public class LoginFrame extends FrameManager implements ActionListener {
	// �����ӿ� ���� ������
	JLabel lbProgName, lbId, lbPwd;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton btnLogin, btnJoin, btnExit;
	LoginManager lm;
	
	static String dbFileURL = "jdbc:sqlite:diary.db";	// ����� DB �̸�
	
	// ������
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setTitle("���� ���̾");
		setSize(400, 400);
		setLayout(null);
		// ���� ���÷����� ����� ǥ��
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		// ������Ʈ ����
		lbProgName = new JLabel();
		lbId = new JLabel();
		lbPwd = new JLabel();
		tfId = new JTextField();
		pfPwd = new JPasswordField();
		btnLogin = new JButton();
		btnJoin = new JButton();
		btnExit = new JButton();
		
		// �����ӿ� ������Ʈ ����
		createJLabel(c, lbProgName, "PRIVATE DIARY", 100, 50, 150, 50);
		createJLabel(c, lbId, "���̵�", 50, 25, 90, 130);
		createJLabel(c, lbPwd, "��й�ȣ", 100, 25, 89, 170);
		createJTextField(c, tfId, "", 150, 25, 175, 130);
		createJPasswordField(c, pfPwd, "", 150, 25, 175, 170);
		createJButton(c, btnLogin, "�α���", 100, 25, 40, 250);
		createJButton(c, btnJoin, "ȸ������", 100, 25, 150, 250);
		createJButton(c, btnExit, "����", 100, 25, 260, 250);
		
		// ��ư�� �׼Ǹ����� ���
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		btnExit.addActionListener(this);
		
		setVisible(true);
		setResizable(false);
		
		Connection conn = null;	// DB ���� ��ü
		Statement stmt = null;	// DB �������� ���� ��ü
		String sql = "";		// sql ���ڿ�
		
		// DB ���� �õ�
		try {
			Class.forName("org.sqlite.JDBC");	// sqlite ����̹��� �ε�
			conn = DriverManager.getConnection(dbFileURL);	// DB ����
			
			// user �̸��� ���̺��� �̹� �������� ������ ����
			sql = "CREATE TABLE IF NOT EXISTS user(\r\n" + 
					"	userid VARCHAR(25) PRIMARY KEY,\r\n" + 
					"	pwd VARCHAR(25) NOT NULL\r\n" + 
					");";
			stmt = conn.createStatement();	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			int count = stmt.executeUpdate(sql);	// sql ����
			if(count == 0) {
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
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
	
	// �׼Ǹ����� actionPerformed override
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		LoginManager lm = new LoginManager();
		if (e.getSource() == btnLogin) {	// �α��� ��ư�� ������ ��
			String userId = lm.login(tfId.getText(), pfPwd.getText());	// login�� ȣ�� �� ���ϰ��� ����
			if(!userId.equals("")) {	// ���ϰ��� �� ���ڿ��� �ƴ϶��(�α��� ������ ��)
				new DiaryFrame(userId);	// userId�� �ѱ�鼭 DiaryFrame�� ȣ��
				System.out.println("LOGIN SUCCESS: userId = " +userId);
				dispose();	// ���� ������ ����
			} else {	// ���ϰ��� �� ���ڿ��̶��(�α��� ����)
				JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� �Է����ּ���.");
			}
		} else if (e.getSource() == btnJoin) {	// ȸ������ ��ư�� ������ ��
			new JoinForm();	// ȸ������ ���� ȣ��
		} else if (e.getSource() == btnExit) {	// ���� ��ư�� ������ ��
			int x = JOptionPane.showConfirmDialog(this, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				System.exit(0);	// ���α׷� ����
			} 
		}
	}
	
}
