/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
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

// �ؽ�Ʈ �޸� �������� ��Ÿ���� Ŭ����
@SuppressWarnings({ "serial", "unused" })
public class MemoFrame extends FrameManager implements ActionListener {
	// ������ ������ �ʿ��� ������
	JLabel lbTitle, lbDescription, lbDate;
	JTextField tfTitle;
	JTextArea taDescription;
	JButton btnAdd, btnCancel, btnUpdate, btnDelete;

	DiaryFrame df;
	String userId, num;	// �Ѱܹ��� userid�� num�� ������ ����

	// �޸� �߰��� ���� ������
	public MemoFrame(String userid, DiaryFrame df) {
		this.df = df;
		this.userId = userid;
		addMemeoUI();	// �� �޸� �߰��� ���� UI
	}
	// �޸� �����ϰų� ������ ���� ������
	public MemoFrame(String userid, String no, DiaryFrame df) {
		this.num = no;
		this.userId = userid;
		this.df = df;
		selectedMemoUI();	// ���õ� �޸� �����ϰų� ������ ���� UI
		
		DiaryManager dm = new DiaryManager();
		TextMemo tm = dm.getSelectedMemo(no);	// ���õ� �ο��� �۳ѹ��� �޸� ��ü�� ����
		viewData(tm);	// ���õ� �޸�ü�� ȭ�鿡 ǥ��
	}

	// TextMemo�� �޸������� ������ ȭ�鿡 �������ִ� �޼ҵ�
	private void viewData(TextMemo vMemo) {
		String userId = vMemo.getUserId();
		String date = vMemo.getDate();
		String title = vMemo.getTitle();
		String description = vMemo.getDescription();

		tfTitle.setText(title);
		lbDate.setText(date);
		taDescription.setText(description);
	}
	// ������ ���̺��� �� �ο츦 �������� ���� �޸� ������(����, ������)
	private void selectedMemoUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setTitle("�޸� ����");
		setSize(400, 500);
		setLayout(null);
		// ���� ���÷����� ����� ǥ��
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		// ������Ʈ ����
		lbTitle = new JLabel();
		lbDescription = new JLabel();
		lbDate = new JLabel();
		tfTitle = new JTextField();
		taDescription = new JTextArea();
		btnUpdate = new JButton();
		btnDelete = new JButton();
		btnCancel = new JButton();
		
		// ������Ʈ ���
		createJLabel(c, lbTitle, "���� : ", 50, 25, 10, 10);
		createJLabel(c, lbDate, "��¥ : ", 150, 15, 10, 35);
		createJLabel(c, lbDescription, "���� : ", 50, 25, 10, 50);
		createJTextField(c, tfTitle, "", 330, 25, 50, 10);
		createJTextArea(c, taDescription, "", 330, 280, 50, 55);
		createJButton(c, btnUpdate, "����", 100, 35, 25, 375);
		createJButton(c, btnDelete, "����", 100, 35, 150, 375);
		createJButton(c, btnCancel, "���", 100, 35, 275, 375);
		
		// ��ư�� �׼��̺�Ʈ������ ���
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		
		setVisible(true);
		setResizable(false);
	}
	// �� �޸� �߰��� ���� �޸�������(�� �޸� �߰���)
	private void addMemeoUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		setTitle("�޸� �߰�");
		setSize(400, 500);
		setLayout(null);
		// ���� ���÷����� ����� ǥ��
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		// ������Ʈ ����
		lbTitle = new JLabel();
		lbDescription = new JLabel();
		tfTitle = new JTextField();
		taDescription = new JTextArea();
		btnAdd = new JButton();
		btnCancel = new JButton();
		
		// ������Ʈ ���
		createJLabel(c, lbTitle, "���� : ", 50, 25, 10, 10);
		createJLabel(c, lbDescription, "���� : ", 50, 25, 10, 50);
		createJTextField(c, tfTitle, "", 330, 25, 50, 10);
		createJTextArea(c, taDescription, "", 330, 300, 50, 50);
		createJButton(c, btnAdd, "�߰�", 100, 35, 90, 375);
		createJButton(c, btnCancel, "���", 100, 35, 210, 375);
		
		// ��ư�� �׼��̺�Ʈ������ ���
		btnAdd.addActionListener(this);
		btnCancel.addActionListener(this);
		
		setVisible(true);
		setResizable(false);
	}
	
	// �׼��̺�Ʈ ó���� ���� �޼ҵ�
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnAdd) {	// �߰� ��ư�� ������ ��
			insertButton();	// �߰� ���� �޼ҵ带 ȣ��
			dispose();	// ���� �������� ����
		} else if (ae.getSource() == btnCancel) {	// ��� ��ư�� ������ ��
			dispose();	// ���� �������� ����
			new DiaryFrame(userId);	// �ٽ� DiaryFrame�� ȣ��
		} else if (ae.getSource() == btnUpdate) {	// ���� ��ư�� ������ ��
			int x = JOptionPane.showConfirmDialog(this, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				updateButton();	// ���� ���� �޼ҵ带 ȣ��
				dispose();	// ���� �������� ����
			} else {
				JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�.");
			}
		} else if (ae.getSource() == btnDelete) {	// ���� ��ư�� ������ ��
			int x = JOptionPane.showConfirmDialog(this, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.OK_OPTION) {
				deleteButton();	// ���� ���� �޼ҵ带 ȣ��
				dispose();	// ���� �������� ����
			} else {
				JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�.");
			}
		}
		df.jTableRefresh();	// ������ ���̺��� ���ΰ�ħ��
	}
	// ���� ��ư�� ������ ���� ���� �޼ҵ�
	private void deleteButton() {
		TextMemo tm = getViewData();	// �޸������ӿ� ��Ÿ�� ������ �޸�ü�� ����
		DiaryManager dm = new DiaryManager();
		boolean ok = dm.deleteMemo(tm);	// ���� DB���� �޸� ����
		
		if (ok) {	// ������ �����ߴٸ�
			JOptionPane.showMessageDialog(this, "�����Ϸ�");
			dispose(); // ���� ������ ����
			new DiaryFrame(userId);	// �ٽ� DiaryFrameȣ��
		} else {	// ������ �����ߴٸ�
			JOptionPane.showMessageDialog(this, "��������");
		}
	}
	// ���� ��ư�� ������ ���� ���� �޼ҵ�
	private void updateButton() {
		TextMemo tm = getViewData();	// �޸������ӿ� ��Ÿ�� ������ �޸�ü�� ����
		DiaryManager dm = new DiaryManager();
		boolean ok = dm.updateMemo(tm);	// ���� DB���� �޸� ����

		if (ok) {	// ������ �����ߴٸ�
			JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
			dispose(); // ���� ������ ����
			new DiaryFrame(userId);	// �ٽ� DiaryFrameȣ��
		} else {	// ������ �����ߴٸ�
			JOptionPane.showMessageDialog(this, "��������: ���� Ȯ���ϼ���");
		}
	}
	// �߰� ��ư�� ������ ���� ���� �޼ҵ�
	private void insertButton() {
		TextMemo tm = getViewData();	// �޸������ӿ� ��Ÿ�� ������ �޸�ü�� ����
		DiaryManager dm = new DiaryManager();
		boolean ok = dm.insertMemo(tm);	// ���� DB���� �޸� �߰�

		if (ok) {	// �߰��� �����ߴٸ�
			JOptionPane.showMessageDialog(this, "�޸� �߰��Ǿ����ϴ�.");
			dispose();	// ���� ������ ����
			new DiaryFrame(userId);	// �ٽ� DiaryFrameȣ��
		} else {	// �߰��� �����ߴٸ�
			JOptionPane.showMessageDialog(this, "�޸� ���������� �߰����� �ʾҽ��ϴ�.");
		}
	}
	// MemoFrame�� ����� data�� �޸� ��ü�� ����
	public TextMemo getViewData() {
		TextMemo tm = new TextMemo();
		// �޸������ӿ��� ������ ��� ���ڿ��� ����
		String userid = userId;
		String title = tfTitle.getText();
		String description = taDescription.getText();
		String no = num;
		
		LocalDate currentDate = LocalDate.now();	// ���� �ý����� ��¥ ����
		LocalTime currentTime = LocalTime.now(); 	// ���� �ý����� �ð� ����
		String date = currentDate.toString() +" | "+currentTime.toString();	// ��¥�� �ð� ������ ��ģ ���ڿ�
		
		// ����� ���ڿ��� �޸� ��ü�� ����
		tm.setUserId(userid);
		tm.setDate(date);
		tm.setTitle(title);
		tm.setDescription(description);
		tm.setNum(no);
		
		return tm;	// �޸�ü tm�� ����
	}
}
