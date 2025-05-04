package tool;

import panel.canvas.CanvasPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class Text extends Component implements KeyListener
{
	private String text = "";			// 사용자가 입력한 텍스트
	private boolean isActive = false;	// 입력 활성화 여부

	@Override
	public void onMousePressed(MouseEvent e, Color color)
	{
		startX = e.getX();
		startY = e.getY() + 10;
		endX = e.getX() + 50;
		endY = e.getY() + 20;
		isActive = true;
		this.color = color;
	}

	@Override
	public void onMouseDragged(MouseEvent e) {}

	@Override
	public void onMouseReleased(MouseEvent e) {}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawString(text, startX, startY);
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (!isActive) return;

		char c = e.getKeyChar();

		if (Character.isISOControl(c)) {
			return;
		}

		text += c;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (!isActive) return;

		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !text.isEmpty()) {
			text = text.substring(0, text.length() - 1);
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			isActive = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}