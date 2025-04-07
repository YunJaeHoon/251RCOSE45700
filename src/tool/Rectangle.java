package tool;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Rectangle implements Component
{
    private int startX, startY, endX, endY;

    @Override
    public void onMousePressed(MouseEvent e)
    {
        startX = e.getX();
        startY = e.getY();
        endX = startX;
        endY = startY;
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

        g.drawRect(x, y, width, height);
    }
}
