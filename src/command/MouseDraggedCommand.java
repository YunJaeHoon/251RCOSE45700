package command;

import panel.canvas.CanvasPanel;
import panel.toolbar.ToolbarPanel;

import java.awt.event.MouseEvent;

public class MouseDraggedCommand implements Command
{
    private final CanvasPanel canvasPanel = CanvasPanel.getInstance();      // 캔버스 패널
    private final ToolbarPanel toolbarPanel = ToolbarPanel.getInstance();   // 도구 패널

    // 마우스 이벤트
    MouseEvent mouseEvent;

    public MouseDraggedCommand(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    // 이벤트 실행
    @Override
    public void execute()
    {
        // 모드에 따른 동작 수행
        toolbarPanel.getCurrentToolMode().getToolEventHandler().onMouseDragged(canvasPanel, mouseEvent);
        canvasPanel.repaint();
    }

    // 되돌리기
    @Override
    public void undo() {}
}
