/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
package cw;

// ����� ��ü Ŭ����
public class User {
	private String id;	// ����� ��ü�� ���̵� ����
	private String pwd;	// ����� ��ü�� ��й�ȣ�� ����
	
	public String getId() {	// ����� ��ü�� id ��ȯ
		return id;
	}
	public void setId(String id) {	// ����� ��ü�� id �ʱ�ȭ
		this.id = id;
	}
	public String getPwd() {	// ����� ��ü�� pwd ��ȯ
		return pwd;
	}
	public void setPwd(String pwd) {	// ����� ��ü�� pwd �ʱ�ȭ
		this.pwd = pwd;
	}
}
