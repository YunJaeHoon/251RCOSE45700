package tool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Line implements Component
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
    public void draw(Graphics g) {
        g.drawLine(startX, startY, endX, endY);
    }
}
