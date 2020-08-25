/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
package cw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

// �α��� �������� ����, ȸ������, ȸ��Ż�� ����ϴ� Ŭ����
public class LoginManager {
	// DB�� ������ �õ��ϴ� �޼ҵ�
	public Connection getConn() {	
		String dbFileURL = "jdbc:sqlite:diary.db";	// ����� DB �̸�
		Connection conn = null;	// ���� ��ü
		try {
			Class.forName("org.sqlite.JDBC");	// sqlite ����̹��� �ε�
			conn = DriverManager.getConnection(dbFileURL);	// DB ����
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;	// ���� ��ü ��ȯ
	}
	// id, pwd ���ڿ��� �޾� �α����� �õ��ϴ� �޼ҵ�
	public String login(String id, String pwd) {
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		ResultSet rs = null;	// DB���� ���� ���ڵ带 �����ϴ� ��ü
		String sql = "";	// SQL ���ڿ�
		String userId = "";	// DB�� �ִ� userid�� ���� ���ڿ� ��ü
		try {
			conn = getConn();	// DB���� �õ�
			// id�� pwd�� ��ġ�ϴ� ���ڵ尡 ������ �� ���ڵ��� userid�� �޴� SQL�� ����
			sql = "SELECT userid FROM user WHERE userid=? AND pwd=?;";	// 
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			ps.setString(1, id);
			ps.setString(2, pwd);
			rs = ps.executeQuery();	// SQL���� ������� �޴� ��ü
				if(rs.next()) {	// ������� Ž��(�ߺ��� userid�� ������ ���� db�� ���������Ƿ� rs�� ����ִ� ���ڵ�� �׻� �Ѱ�)
					userId = rs.getString("userid");	// ��� ���ڵ忡�� userid�� ���� userID�� ����
					return userId;	// userID ����
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(rs != null)		
					rs.close();
				if(ps != null)		
					ps.close();
				if(conn != null)	
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userId;	// �α����� �����ϸ� ���ڿ��� ����ִ� userId�� ����
	}
	// id�� pwd���ڿ��� ������ DB�� user���̺� ����ϴ� �޼ҵ�
	public void join(String id, String pwd) {
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		String sql = "";	// SQL ���ڿ�
		try {
			conn = getConn();	// ���� �õ�
			// user ���̺� id�� pwd���� insert
			sql = "INSERT INTO user VALUES(?,?);";
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			ps.setString(1, id);
			ps.setString(2, pwd);
			
			if (!id.equals("") || !pwd.equals("")) {	// id, pwd�� ��� ���ڿ��� �ƴϸ�
				int r = ps.executeUpdate();	// sql���� ����(���� �� ��ȯ���� ���)
				if(r > 0) {	// sql���� ������ ��
					JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.");
				} else {	// sql���� ������ ��
					JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� Ȯ�����ּ���.");
				}
			} else {	// id, pwd�� �� �� �ϳ��� ���ڿ��� ���
				JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� �Է����ּ���.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "������ ���̵� �����մϴ�.");
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(ps != null)		
					ps.close();
				if(conn != null)	
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// id�� pwd���ڿ��� ������ DB�� user���̺� �����ϴ� ���ڵ带 �����ϴ� �޼ҵ�
	public void delete(String id, String pwd) {
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		String sql = "";	// SQL ���ڿ�
		try {
			conn = getConn();	// ���� �õ�
			// user ���̺� id�� pwd������ ���ڵ带 delete
			sql = "DELETE FROM user WHERE userid=? AND pwd=?;";
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			ps.setString(1, id);
			ps.setString(2, pwd);
			
			if (!id.equals("") || !pwd.equals("")) {	// id, pwd�� ��� ���ڿ��� �ƴϸ�
				int r = ps.executeUpdate();	// sql���� ����(���� �� ��ȯ���� ���)
				if(r > 0) {	// sql���� ������ ��
					JOptionPane.showMessageDialog(null, "Ż�� �Ϸ�Ǿ����ϴ�.");
				} else {	// sql���� ������ ��
					JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� Ȯ�����ּ���.");
				}
			} else {	// id, pwd�� �� �� �ϳ��� ���ڿ��� ���
				JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� Ȯ�����ּ���.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(ps != null)	
					ps.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
