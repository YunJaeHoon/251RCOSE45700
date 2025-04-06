package tool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public interface Component
{
    void onMousePressed(MouseEvent e);
    void onMouseDragged(MouseEvent e);
    void onMouseReleased(MouseEvent e);
    void draw(Graphics g);
}
