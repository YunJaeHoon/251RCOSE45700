package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Ellipse extends Component
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
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillOval(
                Math.min(startX, endX),
                Math.min(startY, endY),
                Math.abs(endX - startX),
                Math.abs(endY - startY)
        );
    }
}