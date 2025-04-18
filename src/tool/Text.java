package tool;

import panel.canvas.CanvasPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
// PropertyPanel에서 아직 구현 안됨
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

    @Override
    public Properties getProperties() {
//        int x = Math.min(startX, endX);
//        int y = Math.min(startY, endY);
//        int width = Math.abs(endX - startX);
//        int height = Math.abs(endY - startY);
//        return new Properties(x,y,width,height, this.color);
        return null;
    }

    @Override
    public void setProperties(int x, int y, int width, int height, Color color) {
    }
}