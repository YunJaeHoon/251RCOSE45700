package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Line extends Component
{
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(startX, startY, endX, endY);
    }
}