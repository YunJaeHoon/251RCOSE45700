package tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Ellipse implements Component {
    private int startX, startY, endX, endY;
    private Color color;

    @Override
    public void onMousePressed(MouseEvent e, Color color) {
        startX = e.getX();
        startY = e.getY();
        endX = startX;
        endY = startY;
        this.color = color;
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void draw(Graphics g) {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Properties getProperties() {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);
        return new Properties(x,y,width,height,this.color);
    }

    @Override
    public void setProperties(int x, int y, int width, int height, Color color) {
        startX = x;
        startY = y;
        endX = x + width;
        endY = y + height;
        this.color = color;
    }
}