/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

// 로그인 성공실패 여부, 회원가입, 회원탈퇴를 담당하는 클래스
public class LoginManager {
	// DB에 연결을 시도하는 메소드
	public Connection getConn() {	
		String dbFileURL = "jdbc:sqlite:diary.db";	// 사용할 DB 이름
		Connection conn = null;	// 연결 객체
		try {
			Class.forName("org.sqlite.JDBC");	// sqlite 드라이버를 로드
			conn = DriverManager.getConnection(dbFileURL);	// DB 연결
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;	// 연결 객체 반환
	}
	// id, pwd 문자열을 받아 로그인을 시도하는 메소드
	public String login(String id, String pwd) {
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		ResultSet rs = null;	// DB에서 받은 레코드를 저장하는 객체
		String sql = "";	// SQL 문자열
		String userId = "";	// DB에 있는 userid를 받을 문자열 객체
		try {
			conn = getConn();	// DB연결 시도
			// id와 pwd로 일치하는 레코드가 있으면 그 레코드의 userid를 받는 SQL문 실행
			sql = "SELECT userid FROM user WHERE userid=? AND pwd=?;";	// 
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			ps.setString(1, id);
			ps.setString(2, pwd);
			rs = ps.executeQuery();	// SQL문의 결과값을 받는 객체
				if(rs.next()) {	// 결과값을 탐색(중복된 userid는 있을수 없게 db를 설계했으므로 rs에 들어있는 레코드는 항상 한개)
					userId = rs.getString("userid");	// 결과 레코드에서 userid의 값을 userID에 저장
					return userId;	// userID 리턴
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
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
		return userId;	// 로그인이 실패하면 빈문자열이 담겨있는 userId를 리턴
	}
	// id와 pwd문자열을 가지고 DB의 user테이블에 등록하는 메소드
	public void join(String id, String pwd) {
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		String sql = "";	// SQL 문자열
		try {
			conn = getConn();	// 연결 시도
			// user 테이블에 id와 pwd값을 insert
			sql = "INSERT INTO user VALUES(?,?);";
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			ps.setString(1, id);
			ps.setString(2, pwd);
			
			if (!id.equals("") || !pwd.equals("")) {	// id, pwd가 모두 빈문자열이 아니면
				int r = ps.executeUpdate();	// sql문을 실행(성공 시 반환값이 양수)
				if(r > 0) {	// sql문이 성공할 때
					JOptionPane.showMessageDialog(null, "가입이 완료되었습니다.");
				} else {	// sql문이 실패할 때
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요.");
				}
			} else {	// id, pwd중 둘 중 하나라도 빈문자열일 경우
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력해주세요.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "동일한 아이디가 존재합니다.");
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
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
	// id와 pwd문자열을 가지고 DB의 user테이블에 존재하는 레코드를 삭제하는 메소드
	public void delete(String id, String pwd) {
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		String sql = "";	// SQL 문자열
		try {
			conn = getConn();	// 연결 시도
			// user 테이블에 id와 pwd값으로 레코드를 delete
			sql = "DELETE FROM user WHERE userid=? AND pwd=?;";
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			ps.setString(1, id);
			ps.setString(2, pwd);
			
			if (!id.equals("") || !pwd.equals("")) {	// id, pwd가 모두 빈문자열이 아니면
				int r = ps.executeUpdate();	// sql문을 실행(성공 시 반환값이 양수)
				if(r > 0) {	// sql문이 성공할 때
					JOptionPane.showMessageDialog(null, "탈퇴가 완료되었습니다.");
				} else {	// sql문이 실패할 때
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요.");
				}
			} else {	// id, pwd중 둘 중 하나라도 빈문자열일 경우
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
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
