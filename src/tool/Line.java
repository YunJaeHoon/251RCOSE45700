package tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Line extends Component
{
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
        g.setColor(color);
        g.drawLine(startX, startY, endX, endY);
    }

//    @Override
//    public void setBounds(Rectangle bounds) {
//        Rectangle old = getBounds();
//        // start 좌표를 bounds의 좌표로 이동하고, 기존 도형의 크기를 유지 혹은 새 크기로 변경
//        startX = bounds.x;
//        startY = bounds.y;
//        endX = startX + bounds.width;
//        endY = startY + bounds.height;
//    }
}