/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

// 텍스트 메모 객체 클래스 
public class TextMemo {
	private String date;	
	private String title;	
	private String description;	
	private String userId;	
	private String num;		
	
	public String getNum() {	
		return num;
	}
	public void setNum(String num) {	
		this.num = num;
	}
	public String getUserId() {	
		return userId;
	}
	public void setUserId(String userId) {	
		this.userId = userId;
	}
	public String getDate() {	
		return date;
	}
	public void setDate(String date) {	
		this.date = date;
	}
	public String getTitle() {	
		return title;
	}
	public void setTitle(String title) {	
		this.title = title;
	}
	public String getDescription() {	
		return description;
	}
	public void setDescription(String description) {	
		this.description = description;
	}
}
