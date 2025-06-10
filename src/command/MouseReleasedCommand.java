package command;

import panel.canvas.CanvasPanel;

import java.awt.event.MouseEvent;

public class MouseReleasedCommand implements Command
{
    // 캔버스 패널
    private final CanvasPanel canvasPanel = CanvasPanel.getInstance();

    // 마우스 이벤트
    MouseEvent mouseEvent;

    public MouseReleasedCommand(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    // 이벤트 실행
    @Override
    public void execute() {
        canvasPanel.executeMouseReleasedEvent(mouseEvent);
    }
}
