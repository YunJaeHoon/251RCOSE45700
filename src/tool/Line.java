package tool;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Line implements Component
{
    private int startX, startY, endX, endY;
    private Color color;

    @Override
    public void onMousePressed(MouseEvent e, Color color)
    {
        startX = e.getX();
        startY = e.getY();
        endX = startX;
        endY = startY;
        this.color = color;
    }

    @Override
    public void onMouseDragged(MouseEvent e)
    {
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void onMouseReleased(MouseEvent e)
    {
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.drawLine(startX, startY, endX, endY);
    }
}
