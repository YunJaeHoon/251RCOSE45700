package command;

import panel.property.PropertyPanel;

public class ChangeYCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // y 좌표
    private final int y;

    // 생성자
    public ChangeYCommand(int y) {
        this.y = y;
    }

    // y 좌표 변경
    @Override
    public void execute() {
        propertyPanel.changeY(y);
    }
}
