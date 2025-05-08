package tool;

import panel.canvas.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface ToolEventHandler
{
    void onMousePressed(CanvasPanel canvasPanel, MouseEvent e, Color color);
    void onMouseDragged(CanvasPanel canvasPanel, MouseEvent e);
    void onMouseReleased(MouseEvent e);
}
