/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
package cw;

// �ؽ�Ʈ �޸� ��ü Ŭ���� 
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
