/* 저자 : 송창우 
 * 만든일자 : June 19, 2020
 * 프로그램 내용 : 개인용 메모 프로그램 */
package cw;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/*	JComponent를 만들고 컨테이너에 부착해주는 클래스	 */
public class FrameManager extends JFrame{
	// JLabel 생성 메소드
	void createJLabel(Container c, JLabel obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JTextField 생성 메소드
	void createJTextField(Container c, JTextField obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JTextArea 생성 메소드
	void createJTextArea(Container c, JTextArea obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JPasswordField 생성 메소드
	void createJPasswordField(Container c, JPasswordField obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JButton 생성 메소드
	void createJButton(Container c, JButton obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	
}
