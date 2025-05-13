package tool;

import component.Ellipse;
import panel.canvas.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EllipseEventHandler implements ToolEventHandler
{
    private Ellipse ellipse;

    @Override
    public void onMousePressed(CanvasPanel canvasPanel, MouseEvent e, Color color)
    {
        // 타원 생성
        this.ellipse = new Ellipse();

        // 타원 필드 변수 설정
        ellipse.setStartX(e.getX());
        ellipse.setStartY(e.getY());
        ellipse.setEndX(e.getX());
        ellipse.setEndY(e.getY());
        ellipse.setColor(color);

        // 캔버스 패널의 컴포넌트 리스트에 타원 추가
        canvasPanel.getComponentList().add(ellipse);
    }

    @Override
    public void onMouseDragged(CanvasPanel canvasPanel, MouseEvent e)
    {
        ellipse.setEndX(e.getX());
        ellipse.setEndY(e.getY());
    }

    @Override
    public void onMouseReleased(CanvasPanel canvasPanel, MouseEvent e)
    {
        ellipse.setEndX(e.getX());
        ellipse.setEndY(e.getY());
    }
}
