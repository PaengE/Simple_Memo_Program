/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
package cw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

// ���̾�� �����ϴ� �Ŵ��� Ŭ����
@SuppressWarnings({"rawtypes", "unchecked"})
public class DiaryManager {
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
	
	// �� ���̺��� ���õ� �࿡ ���� ������ DB���� ã�� �޸�ü�� ����
	public TextMemo getSelectedMemo(String no) {
		TextMemo tm = new TextMemo();	// ���õ� ���� ������ ���� �޸� ��ü
		
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		ResultSet rs = null;	// SQL���� ������� �޴� ��ü
		
		try {
			conn = getConn();	// ���� �õ�
			// ���õ� ���� �۳ѹ��� ��Ī �Ǵ� ���ڵ带 �˻�
			String sql = "SELECT * FROM text_memo WHERE idx = ?;";
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			ps.setString(1, no);
			
			rs = ps.executeQuery();	// SQL���� ����� ����
			
			if(rs.next()) {	// ��� ������� ����(�ϳ��ۿ� ����)
				// �޸�ü�� ���� �ʱ�ȭ
				tm.setUserId(rs.getString("userid"));
				tm.setTitle(rs.getString("title"));
				tm.setDescription(rs.getString("description"));
				tm.setDate(rs.getString("date"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(conn != null)
				conn.close();
				if(ps != null)
					ps.close();
				if(rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tm;	// �޸�ü ����
	}
	// DB���� userid ������ ��� �޸� �˻��Ͽ� ���Ϳ� �����ϴ� �޼ҵ�
	public Vector getMemosList(String userid) {
		Vector data = new Vector();	// sql������� ������ ����
		
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		ResultSet rs = null;	// SQL���� ������� �޴� ��ü
		
		try {
			conn = getConn();	// ���� �õ�
			// userid�� ��Ī �Ǵ� ��� ���ڵ带 date������������ �˻�
			String sql = "SELECT * FROM text_memo WHERE userid = ? ORDER BY date DESC;";
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			ps.setString(1, userid);
			rs = ps.executeQuery();	// SQL���� ����� ����
			
			while(rs.next()) {	// ��� ������� ����
				//	��� ������� �ϳ��� Ž���Ͽ� ���ڿ������� ����
				String title = rs.getString("title");
				String description = rs.getString("description");
				String date = rs.getString("date");
				String no = rs.getString("idx");
				
				// row���Ϳ� �ϳ��� ����
				Vector row = new Vector();	
				row.add(no);
				row.add(date);
				row.add(title);
				row.add(description);
				
				data.add(row);	// data���Ϳ� ��row���͸� ����
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(conn != null)
					conn.close();
				if(ps != null)
					ps.close();
				if(rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;	// data���� ����
	}
	// DB�� ���ο� �޸� �߰��ϴ� �޼ҵ�
	public boolean insertMemo(TextMemo tm) {
		boolean ok = false;	// �ش� �޼ҵ� ���� ���� ���θ� ������ boolean��
		
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		
		try {
			conn = getConn();	// ���� �õ�
			String sql = "INSERT INTO text_memo(userid, date, title, description)"
					+ " VALUES(?,?,?,?);";	// memo��ü�� userid, date, title, description�� DB�� ����
			
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			ps.setString(1, tm.getUserId());
			ps.setString(2, tm.getDate());
			ps.setString(3, tm.getTitle());
			ps.setString(4, tm.getDescription());
			
			int r = ps.executeUpdate();	// sql���� ����(���� �� ��ȯ���� ���)
			if(r > 0) {	
				ok = true;	// �޼ҵ尡 ���������� ����
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(conn != null)
					conn.close();
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;	// �޼ҵ� ���� ���θ� ��ȯ
	}
	// DB�� Ư�� �޸� ������Ʈ�ϴ� �޼ҵ�
	public boolean updateMemo(TextMemo tm) {
		boolean ok = false;	// �ش� �޼ҵ� ���� ���� ���θ� ������ boolean��
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		String sql = "";	// SQL ���ڿ�
		
		try {
			conn = getConn();	// ���� �õ�
			sql = "UPDATE text_memo SET title=?, description=?, date=?"
					+ "WHERE idx=?";	// �ؽ�Ʈ�޸� ��ü�� num�� ���� ���ڵ��� title, description, date�� ����
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			
			LocalDate currentDate = LocalDate.now();	// ���� �ý����� ��¥ ����
			LocalTime currentTime = LocalTime.now(); 	// ���� �ý����� �ð� ����
			String date = currentDate.toString() +" | "+currentTime.toString();	// ��¥�� �ð� ������ ��ģ ���ڿ�
			
			ps.setString(1, tm.getTitle());
			ps.setString(2, tm.getDescription());
			ps.setString(3, date);
			ps.setString(4, tm.getNum());
			
			int r = ps.executeUpdate();	// sql���� ����(���� �� ��ȯ���� ���)
			if(r > 0) {	
				ok = true;	// �޼ҵ尡 ���������� ����
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(conn != null)
					conn.close();
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;	// �޼ҵ� ���� ���θ� ��ȯ
	}
	// DB���� �޸� �����ϴ� �޼ҵ�
	public boolean deleteMemo(TextMemo tm) {
		boolean ok = false;	// �ش� �޼ҵ� ���� ���� ���θ� ������ boolean��
		Connection conn = null;	// ���� ��ü
		PreparedStatement ps = null;	// DB�� �������� ���� ��ü
		String sql = "";	// SQL ���ڿ�
		try {
			conn = getConn();	// ���� �õ�
			sql = "DELETE FROM text_memo WHERE idx = ?";	// �ؽ�Ʈ�޸� ��ü�� num�� ���� ���ڵ带 ����
			
			ps = conn.prepareStatement(sql);	// DB�� �ѱ� ��ü�� �ʱ�ȭ
			ps.setString(1, tm.getNum());
			
			int r = ps.executeUpdate();	// sql���� ����(���� �� ��ȯ���� ���)
			if(r > 0) {
				ok = true;	// �޼ҵ尡 ���������� ����
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// ����� ���� DB �������� ����(�ʱ�ȭ)
			try {
				if(conn != null)
					conn.close();
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;	// �޼ҵ� ���� ���θ� ��ȯ
	}
	
}
