/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
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

// Diary�������� �����ϴ� Ŭ����
@SuppressWarnings("serial")
public class DiaryFrame extends JFrame implements MouseListener, ActionListener{
	// ������ ������ ���̴� ����
	@SuppressWarnings("rawtypes")
	Vector v, cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane scroll;
	JPanel panel;
	JButton btnAddMemo, btnLogout;
	String userId;
	
	static String dbFileURL = "jdbc:sqlite:diary.db";	// ����� DB �̸�
	
	// ������
	public DiaryFrame(String userId) {
		super("���� ���̾");
		this.userId = userId;
		setSize(1000, 400);
		// ���� ���÷����� ����� ǥ��
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenX = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int screenY = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		setLocation(screenX, screenY);
		
		DiaryManager dm = new DiaryManager();
		v = dm.getMemosList(userId);	// userId�� �˻��� ���ڵ���� ���Ϳ� ����
		cols = getColumn();	// ������ ���̺� ������ �Ӽ��� ���� ����
		
		model = new DefaultTableModel(v, cols) {	// ���̺�� ����
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jTable = new JTable(model);	// �� JTable ����
		jTable.getTableHeader().setReorderingAllowed(false);	// ������ ���̺� �÷��� �̵� ����
		jTable.getTableHeader().setResizingAllowed(false);	// ������ ���̺� �÷��� ����� ����
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	// ������ ���̺� ���� �ϳ��� �����ϰԲ� ����
		// �� �Ӽ����� �÷��ʺ� ����
		jTable.getColumn("�Խù��ѹ�").setPreferredWidth(1);
		jTable.getColumn("��¥").setPreferredWidth(40);
		jTable.getColumn("����").setPreferredWidth(50);
		jTable.getColumn("����").setPreferredWidth(500);
		// ��ũ�� ���� �� �߰�
		scroll = new JScrollPane(jTable);
		add(scroll);
		
		// ��ư �г� ���� �� �߰�
		panel = new JPanel();
		btnAddMemo = new JButton("�޸� �߰�");
		btnLogout = new JButton("�α׾ƿ�");
		panel.add(btnAddMemo);
		panel.add(btnLogout);
		add(panel, BorderLayout.SOUTH);
		
		// ������ ���̺�� ��ư�� ���콺�̺�Ʈ�����ʿ� �׼��̺�Ʈ�����ʸ� ���� ���
		jTable.addMouseListener(this);
		btnAddMemo.addActionListener(this);
		btnLogout.addActionListener(this);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Connection conn = null;	// ���� ��ü
		Statement stmt = null;	// DB�� �������� ���� ��ü
		String sql = "";	// sql���� ���� ���ڿ�
		try {
			Class.forName("org.sqlite.JDBC");	// sqlite ����̹��� �ε�
			conn = DriverManager.getConnection(dbFileURL);	// DB ����
			
			// text_memo ���̺��� ������ ����
			// text_memo���̺��� userid�� user���̺��� userid�� ������(�ܷ�Ű ����)
			sql = "CREATE TABLE IF NOT EXISTS text_memo(\r\n" + 
					"	idx INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
					"	userid VARCHAR(25) NOT NULL,\r\n" + 
					"	date VARCHAR(25),\r\n" + 
					"	title VARCHAR(100),\r\n" + 
					"	description VARCHAR(3000),\r\n" + 
					"	FOREIGN KEY(userid)" + 
					"	REFERENCES user(userid) ON DELETE CASCADE" + 
					");";
			stmt = conn.createStatement();	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			int count = stmt.executeUpdate(sql);	// sql ����
			if(count == 0) {
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
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
	// ������ ���̺��� 1�࿡ ��ġ�� ������ ����
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector getColumn() {	
		// 1�࿡ ��ġ�� �Ӽ� ����� ���Ϳ� ����
		Vector col = new Vector();
		col.add("�Խù��ѹ�");
		col.add("��¥");
		col.add("����");
		col.add("����");
		
		return col;	// col ���� ����
	}
	// ������ ���̺��� ���ΰ�ħ(�ٽ� ����)
	public void jTableRefresh() {
		DiaryManager dm = new DiaryManager();
		DefaultTableModel model = new DefaultTableModel(dm.getMemosList(userId), getColumn());	// �� ���̺�� ����
		jTable.setModel(model);	// JTable�� ���� ����
	}

	// ���̺� ���� Ŭ���������� �̺�Ʈ ó�� �޼ҵ�
	@Override
	public void mouseClicked(MouseEvent e) {
		int r = jTable.getSelectedRow();	// ������ ���� ���° �������� ���
		String no = (String)jTable.getValueAt(r, 0);	// r��° ���� 0��° �Ӽ���(�۳ѹ�)�� ����
		dispose();	// ���� ������ ����
		new MemoFrame(userId, no, this);	// �α����� userid, ���õ� ���� �۳ѹ�, ���� DiaryFrame�� MemoFrame�� �ѱ�
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
	// ��ư �׼Ǹ����� �̺�Ʈó�� �޼ҵ�
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAddMemo) {	// �޸��߰� ��ư�� ������ ��
			dispose();	// ���� �������� ����
			new MemoFrame(userId, this);	// �α����� userid�� ���� DiaryFrame�� MemoFrame�� �ѱ�
		} else if(e.getSource() == btnLogout) {	// �α׾ƿ� ��ư�� ������ ��
			dispose();	// ���� �������� ����
			new LoginFrame();	// �ٽ� �α����������� ���
		}
	}
}
