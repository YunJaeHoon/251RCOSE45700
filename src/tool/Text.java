package tool;

import panel.canvas.CanvasPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Text implements Component
{
    private int x, y;
    private Color color;
    private JTextField textField;

    @Override
    public void onMousePressed(MouseEvent e, Color color)
    {
        x = e.getX();
        y = e.getY();
        textField = new JTextField();
        this.color = color;

        textField.setBounds(x, y, 100, 20);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setForeground(color);
        textField.addActionListener(event -> {
            textField.setVisible(false);
        });

        java.awt.Component source = e.getComponent();
        if (source instanceof CanvasPanel) {
            ((CanvasPanel) source).add(textField);
            textField.requestFocus();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {}

    @Override
    public void onMouseReleased(MouseEvent e) {}

    @Override
    public void draw(Graphics g)
    {
        if (textField != null && !textField.isVisible())
        {
            g.setColor(color);
            g.drawString(textField.getText(), x, y);
        }
    }
}
