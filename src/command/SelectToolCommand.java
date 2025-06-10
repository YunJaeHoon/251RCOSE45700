package command;

import panel.toolbar.ToolbarPanel;
import tool.ToolMode;

public class SelectToolCommand implements Command
{
    // 도구 패널
    private final ToolbarPanel toolbarPanel = ToolbarPanel.getInstance();

    // 도구
    private final ToolMode toolMode;

    // 생성자
    public SelectToolCommand(ToolMode toolMode) {
        this.toolMode = toolMode;
    }

    // 도구 변경
    @Override
    public void execute() {
        toolbarPanel.setCurrentToolMode(toolMode);
    }
}
