package tool;

import component.Rectangle;
import component.Text;
import panel.canvas.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TextEventHandler implements ToolEventHandler
{
    private Text text;

    @Override
    public void onMousePressed(CanvasPanel canvasPanel, MouseEvent e, Color color)
    {
        this.text = new Text();

        // 텍스트 필드 변수 설정
        text.setStartX(e.getX());
        text.setStartY(e.getY());
        text.setEndX(e.getX());
        text.setEndY(e.getY());
        text.setColor(color);
        text.setDragging(true);

        canvasPanel.getComponentList().add(text);
    }

    @Override
    public void onMouseDragged(CanvasPanel canvasPanel, MouseEvent e)
    {
        text.setEndX(e.getX());
        text.setEndY(e.getY());
    }

    @Override
    public void onMouseReleased(CanvasPanel canvasPanel, MouseEvent e)
    {
        text.setEndX(e.getX());
        text.setEndY(e.getY());
        text.setDragging(false);
        text.enableEditing(canvasPanel);
    }
}
