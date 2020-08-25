/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

// 사용자 객체 클래스
public class User {
	private String id;	// 사용자 객체의 아이디를 저장
	private String pwd;	// 사용자 객체의 비밀번호를 저장
	
	public String getId() {	// 사용자 객체의 id 반환
		return id;
	}
	public void setId(String id) {	// 사용자 객체의 id 초기화
		this.id = id;
	}
	public String getPwd() {	// 사용자 객체의 pwd 반환
		return pwd;
	}
	public void setPwd(String pwd) {	// 사용자 객체의 pwd 초기화
		this.pwd = pwd;
	}
}
