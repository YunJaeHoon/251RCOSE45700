package tool;

import component.Line;
import component.Rectangle;
import panel.canvas.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LineEventHandler implements ToolEventHandler
{
    private Line line;

    @Override
    public void onMousePressed(CanvasPanel canvasPanel, MouseEvent e, Color color)
    {
        // 선 생성
        this.line = new Line();

        // 선 필드 변수 설정
        line.setStartX(e.getX());
        line.setStartY(e.getY());
        line.setEndX(e.getX());
        line.setEndY(e.getY());
        line.setColor(color);

        // 캔버스 패널의 컴포넌트 리스트에 선 추가
        canvasPanel.getComponentList().add(line);
    }

    @Override
    public void onMouseDragged(CanvasPanel canvasPanel, MouseEvent e)
    {
        line.setEndX(e.getX());
        line.setEndY(e.getY());
    }

    @Override
    public void onMouseReleased(MouseEvent e)
    {
        line.setEndX(e.getX());
        line.setEndY(e.getY());
    }
}
