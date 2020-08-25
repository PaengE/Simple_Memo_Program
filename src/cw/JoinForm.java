/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
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


// ȸ������ Form�� ��Ÿ���ִ� Ŭ����
@SuppressWarnings("serial")
public class JoinForm extends JFrame implements ActionListener {
	// �������� ���� ������
	JPanel p;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton btnInsert, btnCancel, btnDelete;

	GridBagLayout gb;
	GridBagConstraints gbc;
	// ������
	JoinForm(){
		createUI();
	}
	// ȸ������ �� ����
	private void createUI() {
		// ���� ���÷����� ����� ǥ��
		setSize(350, 200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - super.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - super.getHeight() / 2);
		setLocation(screenX, screenY);
		this.setTitle("ȸ������");
		
		// �׸���鷹�̾ƿ� ���� �� ���̾ƿ� ����
		gb = new GridBagLayout();
		setLayout(gb);
		// �����¿츦 ��ä��
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		// ���� ���� �յ�й�
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// ���̵� �� �� �ؽ�Ʈ�ʵ� ���
		JLabel lbId = new JLabel("���̵� : ");
		tfId = new JTextField(20);
		gbAdd(lbId, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 3, 1);

		// ��й�ȣ �� �� �ؽ�Ʈ�ʵ� ���
		JLabel lbPwd = new JLabel("��й�ȣ : ");
		pfPwd = new JPasswordField(20);
		gbAdd(lbPwd, 0, 1, 1, 1);
		gbAdd(pfPwd, 1, 1, 3, 1);

		// ���ο� �г��� �����ϰ� ��ư�׷��� ������ ��
		// ��ư�׷��� �׸���鷹�̾ƿ��� ����
		JPanel pButton = new JPanel();
		btnInsert = new JButton("����");
		btnDelete = new JButton("Ż��");
		btnCancel = new JButton("���");
		pButton.add(btnInsert);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 10, 4, 1);

		// ��ư���� �׼Ǹ����� ���
		btnInsert.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	// �׸���鷹�̾ƿ��� ���̴� �޼ҵ�
	// (x,y)��ǥ�� w*h���� ���� ����
	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);	// ������Ʈ ���̿� ���� �ֱ�
		add(c, gbc);
	}
	
	
	// �׼Ǹ����� actionPerformed override
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		LoginManager lm = new LoginManager();
		if (e.getSource() == btnInsert) {	// ������ ��ư�� ���� ��ư�� ��
			// ����� ��ü ���� �� �ش� �ؽ�Ʈ�ʵ� ������ ��ü �ʱ�ȭ
			User user = new User();
			user.setId((String)tfId.getText());
			user.setPwd((String)pfPwd.getText());
			
			lm.join(user.getId(), user.getPwd()); // ����� ��ü�� ������ ��� join�� ȣ��
		} else if (e.getSource() == btnDelete) {	// ������ ��ư�� Ż�� ��ư�� ��
			int x = JOptionPane.showConfirmDialog(this, "���� Ż���Ͻðڽ��ϱ�?", "Ż��", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				lm.delete(tfId.getText(), pfPwd.getText());	// �ش� �ؽ�Ʈ�ʵ忡 �ִ� ������ delete�� ȣ��
			}
		} else if (e.getSource() == btnCancel) {	// ������ ��ư�� ��� ��ư�� ��
			dispose();	// ���� �ش� ������ ����
		}
	}
}
