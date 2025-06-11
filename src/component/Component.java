package component;

import component.memento.ComponentMemento;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;

public abstract class Component
{
	// 현재 속성
	protected int startX, startY, endX, endY;
	protected Color color;

	public abstract void draw(Graphics g);

	public Rectangle getBounds()
	{
		return new Rectangle(
				Math.min(startX, endX),
				Math.min(startY, endY),
				Math.abs(endX - startX),
				Math.abs(endY - startY)
		);
	}
	
	public boolean contains(Point p) {
		return getBounds().contains(p);
	}
	
	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getEndX() {
		return endX;
	}
	
	public int getEndY() {
		return endY;
	}
	
	public int getWidth() {
		return Math.abs(endX - startX);
	}
	
	public int getHeight() {
		return Math.abs(endY - startY);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setStartX(int x) {
		startX = x;
	}
	
	public void setStartY(int y) {
		startY = y;
	}
	
	public void setEndX(int x) {
		endX = x;
	}
	
	public void setEndY(int y) {
		endY = y;
	}

	public void resize(int x1, int y1, int x2, int y2)
	{
		startX = x1;
		startY = y1;
		endX = x2;
		endY = y2;
	}
	
	public void setWidth(int width) {
		if(startX >= endX)
			startX = endX + width;
		else
			endX = startX + width;
	}
	
	public void setHeight(int height) {
		if(startY >= endY)
			startY = endY + height;
		else
			endY = startY + height;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setRedCode(int redCode) {
		color = new Color(redCode, color.getGreen(), color.getBlue());
	}
	
	public void setGreenCode(int greenCode) {
		color = new Color(color.getRed(), greenCode, color.getBlue());
	}
	
	public void setBlueCode(int blueCode) {
		color = new Color(color.getRed(), color.getGreen(), blueCode);
	}

    public void enableEditing(JPanel parent) {
		// 결합도 하락을 위한 메소드 추가
    }
	public void changeX(int x) {
		int width = this.getWidth();

		this.setStartX(x);
		this.setEndX(width + x);
	}
	public void changeY(int y) {
		int width = this.getHeight();

		this.setStartY(y);
		this.setEndY(width + y);
	}
	public void drawBounds(Graphics g) {
		Rectangle bounds = this.getBounds();
		g.drawRect(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4);

		// 크기 조절 핸들 표시
		g.fillRect(bounds.x + bounds.width, bounds.y + bounds.height, 10, 10);
	}

	/// 스냅샷

	// 기록 저장
	public ComponentMemento saveState() {
		return new ComponentMemento(startX, startY, endX, endY, color);
	}

	// 기록 복원
	public void restoreState(ComponentMemento memento) {
		this.startX = memento.startX;
		this.startY = memento.startY;
		this.endX = memento.endX;
		this.endY = memento.endY;
		this.color = memento.color;
	}
}
