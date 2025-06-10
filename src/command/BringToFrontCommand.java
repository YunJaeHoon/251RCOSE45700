package command;

import panel.property.PropertyPanel;

public class BringToFrontCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // 맨 앞으로 이동
    @Override
    public void execute() {
        propertyPanel.bringToFront();
    }
}
