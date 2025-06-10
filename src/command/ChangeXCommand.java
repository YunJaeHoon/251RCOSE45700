package command;

import panel.property.PropertyPanel;

public class ChangeXCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // x 좌표
    private final int x;

    // 생성자
    public ChangeXCommand(int x) {
        this.x = x;
    }

    // x 좌표 변경
    @Override
    public void execute() {
        propertyPanel.changeX(x);
    }

    // 되돌리기
    @Override
    public void undo() {}
}
