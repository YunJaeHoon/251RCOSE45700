package command;

import panel.property.PropertyPanel;

public class ChangeHeightCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // 높이
    private final int height;

    // 생성자
    public ChangeHeightCommand(int height) {
        this.height = height;
    }

    // 높이 변경
    @Override
    public void execute() {
        propertyPanel.changeHeight(height);
    }
}
