package command;

import panel.property.PropertyPanel;

public class ChangeWidthCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // 너비
    private final int width;

    // 생성자
    public ChangeWidthCommand(int width) {
        this.width = width;
    }

    // 너비 변경
    @Override
    public void execute() {
        propertyPanel.changeWidth(width);
    }

    // 되돌리기
    @Override
    public void undo() {}
}
