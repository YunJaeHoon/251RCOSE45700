package component;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;

public abstract class Component
{
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
}
