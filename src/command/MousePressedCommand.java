package command;

import panel.canvas.CanvasPanel;
import panel.color.ColorPanel;
import panel.toolbar.ToolbarPanel;

import java.awt.event.MouseEvent;

public class MousePressedCommand implements Command
{
    private final CanvasPanel canvasPanel = CanvasPanel.getInstance();      // 캔버스 패널
    private final ToolbarPanel toolbarPanel = ToolbarPanel.getInstance();   // 도구 패널
    private final ColorPanel colorPanel = ColorPanel.getInstance();         // 색상 패널

    // 마우스 이벤트
    MouseEvent mouseEvent;

    public MousePressedCommand(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    // 이벤트 실행
    @Override
    public void execute()
    {
        // 캔버스 영역 클릭 시 포커스를 캔버스 패널로 이동하여 JTextArea가 focusLost 이벤트를 발생하게 함
        canvasPanel.requestFocusInWindow();

        // 모드에 따른 동작 수행
        toolbarPanel.getCurrentToolMode().getToolEventHandler().onMousePressed(canvasPanel, mouseEvent, colorPanel.getCurrentColor());
        canvasPanel.repaint();
    }

    // 되돌리기
    @Override
    public void undo() {}
}
