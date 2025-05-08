package tool;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface ToolEventHandler
{
    void onMousePressed(MouseEvent e, Color color);
    void onMouseDragged(MouseEvent e);
    void onMouseReleased(MouseEvent e);
}
