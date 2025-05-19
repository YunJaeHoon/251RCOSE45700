package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Ellipse extends Component
{
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