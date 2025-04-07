package tool;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Rectangle implements Component
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
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
