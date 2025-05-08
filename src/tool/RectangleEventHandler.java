package tool;

import component.Ellipse;
import component.Rectangle;
import panel.canvas.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RectangleEventHandler implements ToolEventHandler
{
    private Rectangle rectangle;

    @Override
    public void onMousePressed(CanvasPanel canvasPanel, MouseEvent e, Color color)
    {
        // 직사각형 생성
        this.rectangle = new Rectangle();

        // 직사각형 필드 변수 설정
        rectangle.setStartX(e.getX());
        rectangle.setStartY(e.getY());
        rectangle.setEndX(e.getX());
        rectangle.setEndY(e.getY());
        rectangle.setColor(color);

        // 캔버스 패널의 컴포넌트 리스트에 직사각형 추가
        canvasPanel.getComponentList().add(rectangle);
    }

    @Override
    public void onMouseDragged(CanvasPanel canvasPanel, MouseEvent e)
    {
        rectangle.setEndX(e.getX());
        rectangle.setEndY(e.getY());
    }

    @Override
    public void onMouseReleased(MouseEvent e)
    {
        rectangle.setEndX(e.getX());
        rectangle.setEndY(e.getY());
    }
}
