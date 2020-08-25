/* ���� : ��â�� 
 * �������� : June 19, 2020
 * ���α׷� ���� : ���ο� �޸� ���α׷� */
package cw;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/*	JComponent�� ����� �����̳ʿ� �������ִ� Ŭ����	 */
public class FrameManager extends JFrame{
	// JLabel ���� �޼ҵ�
	void createJLabel(Container c, JLabel obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JTextField ���� �޼ҵ�
	void createJTextField(Container c, JTextField obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JTextArea ���� �޼ҵ�
	void createJTextArea(Container c, JTextArea obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JPasswordField ���� �޼ҵ�
	void createJPasswordField(Container c, JPasswordField obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	// JButton ���� �޼ҵ�
	void createJButton(Container c, JButton obj, String name, int sx, int sy, int dx, int dy) {
		obj.setText(name);
		obj.setSize(sx, sy);
		obj.setLocation(dx, dy);
		c.add(obj);
	}
	
}
