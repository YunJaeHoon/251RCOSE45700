package tool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

public interface Component
{
    void onMousePressed(MouseEvent e, Color color);
    void onMouseDragged(MouseEvent e);
    void onMouseReleased(MouseEvent e);
    void draw(Graphics g);
    Rectangle getBounds();
    Properties getProperties();
    void setProperties(int x, int y, int width, int height, Color color);
    default boolean contains(Point p) {
        return getBounds().contains(p);
    }
}
