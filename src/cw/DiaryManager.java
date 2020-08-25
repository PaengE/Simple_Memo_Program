/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

// 다이어리를 관리하는 매니저 클래스
@SuppressWarnings({"rawtypes", "unchecked"})
public class DiaryManager {
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
	
	// 뷰 테이블에서 선택된 행에 대한 정보를 DB에서 찾아 메모객체를 생성
	public TextMemo getSelectedMemo(String no) {
		TextMemo tm = new TextMemo();	// 선택된 행의 정보를 담을 메모 객체
		
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		ResultSet rs = null;	// SQL문의 결과값을 받는 객체
		
		try {
			conn = getConn();	// 연결 시도
			// 선택된 행의 글넘버와 매칭 되는 레코드를 검색
			String sql = "SELECT * FROM text_memo WHERE idx = ?;";
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			ps.setString(1, no);
			
			rs = ps.executeQuery();	// SQL문의 결과값 저장
			
			if(rs.next()) {	// 모든 결과값에 대해(하나밖에 없음)
				// 메모객체의 정보 초기화
				tm.setUserId(rs.getString("userid"));
				tm.setTitle(rs.getString("title"));
				tm.setDescription(rs.getString("description"));
				tm.setDate(rs.getString("date"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
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
		
		return tm;	// 메모객체 리턴
	}
	// DB에서 userid 소유의 모든 메모를 검색하여 벡터에 저장하는 메소드
	public Vector getMemosList(String userid) {
		Vector data = new Vector();	// sql결과값을 저장할 벡터
		
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		ResultSet rs = null;	// SQL문의 결과값을 받는 객체
		
		try {
			conn = getConn();	// 연결 시도
			// userid와 매칭 되는 모든 레코드를 date내림차순으로 검색
			String sql = "SELECT * FROM text_memo WHERE userid = ? ORDER BY date DESC;";
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			ps.setString(1, userid);
			rs = ps.executeQuery();	// SQL문의 결과값 저장
			
			while(rs.next()) {	// 모든 결과값에 대해
				//	모든 결과값을 하나씩 탐색하여 문자열변수에 저장
				String title = rs.getString("title");
				String description = rs.getString("description");
				String date = rs.getString("date");
				String no = rs.getString("idx");
				
				// row벡터에 하나씩 저장
				Vector row = new Vector();	
				row.add(no);
				row.add(date);
				row.add(title);
				row.add(description);
				
				data.add(row);	// data벡터에 각row벡터를 저장
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
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
		return data;	// data벡터 리턴
	}
	// DB에 새로운 메모를 추가하는 메소드
	public boolean insertMemo(TextMemo tm) {
		boolean ok = false;	// 해당 메소드 성공 실패 여부를 저장한 boolean값
		
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		
		try {
			conn = getConn();	// 연결 시도
			String sql = "INSERT INTO text_memo(userid, date, title, description)"
					+ " VALUES(?,?,?,?);";	// memo객체의 userid, date, title, description을 DB에 저장
			
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			ps.setString(1, tm.getUserId());
			ps.setString(2, tm.getDate());
			ps.setString(3, tm.getTitle());
			ps.setString(4, tm.getDescription());
			
			int r = ps.executeUpdate();	// sql문을 실행(성공 시 반환값이 양수)
			if(r > 0) {	
				ok = true;	// 메소드가 성공했음을 저장
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
			try {
				if(conn != null)
					conn.close();
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;	// 메소드 성공 여부를 반환
	}
	// DB의 특정 메모를 업데이트하는 메소드
	public boolean updateMemo(TextMemo tm) {
		boolean ok = false;	// 해당 메소드 성공 실패 여부를 저장한 boolean값
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		String sql = "";	// SQL 문자열
		
		try {
			conn = getConn();	// 연결 시도
			sql = "UPDATE text_memo SET title=?, description=?, date=?"
					+ "WHERE idx=?";	// 텍스트메모 객체의 num과 같은 레코드의 title, description, date를 수정
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			
			LocalDate currentDate = LocalDate.now();	// 현재 시스템의 날짜 정보
			LocalTime currentTime = LocalTime.now(); 	// 현재 시스템의 시간 정보
			String date = currentDate.toString() +" | "+currentTime.toString();	// 날짜와 시간 정보를 합친 문자열
			
			ps.setString(1, tm.getTitle());
			ps.setString(2, tm.getDescription());
			ps.setString(3, date);
			ps.setString(4, tm.getNum());
			
			int r = ps.executeUpdate();	// sql문을 실행(성공 시 반환값이 양수)
			if(r > 0) {	
				ok = true;	// 메소드가 성공했음을 저장
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
			try {
				if(conn != null)
					conn.close();
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;	// 메소드 성공 여부를 반환
	}
	// DB에서 메모를 삭제하는 메소드
	public boolean deleteMemo(TextMemo tm) {
		boolean ok = false;	// 해당 메소드 성공 실패 여부를 저장한 boolean값
		Connection conn = null;	// 연결 객체
		PreparedStatement ps = null;	// DB에 쿼리문을 담을 객체
		String sql = "";	// SQL 문자열
		try {
			conn = getConn();	// 연결 시도
			sql = "DELETE FROM text_memo WHERE idx = ?";	// 텍스트메모 객체의 num과 같은 레코드를 삭제
			
			ps = conn.prepareStatement(sql);	// DB에 넘길 객체로 초기화
			ps.setString(1, tm.getNum());
			
			int r = ps.executeUpdate();	// sql문을 실행(성공 시 반환값이 양수)
			if(r > 0) {
				ok = true;	// 메소드가 성공했음을 저장
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	// 사용이 끝난 DB 변수들을 닫음(초기화)
			try {
				if(conn != null)
					conn.close();
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ok;	// 메소드 성공 여부를 반환
	}
	
}
