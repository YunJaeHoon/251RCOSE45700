package tool;

import panel.canvas.CanvasPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Text extends JTextField implements Component
{
    private int x, y;
    private Color color;

    @Override
    public void onMousePressed(MouseEvent e, Color color)
    {
        x = e.getX();
        y = e.getY();
        this.color = color;

        setBounds(x, y, 100, 20);
        setBorder(BorderFactory.createEmptyBorder());
        setForeground(color);
        setOpaque(false);

        java.awt.Component source = e.getComponent();
        if (source instanceof CanvasPanel) {
            ((CanvasPanel) source).add(this);
            requestFocus();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {}

    @Override
    public void onMouseReleased(MouseEvent e) {}

    @Override
    public void draw(Graphics g)
    {
        if (!isVisible())
        {
            g.setColor(color);
            FontMetrics metrics = g.getFontMetrics(getFont());
            int adjustedY = y + metrics.getAscent();
            g.drawString(getText(), x, adjustedY);
        }
    }
}