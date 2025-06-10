package command;

import panel.property.PropertyPanel;

public class ChangeBlueCodeCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // Blue 코드
    private final int blueCode;

    // 생성자
    public ChangeBlueCodeCommand(int blueCode) {
        this.blueCode = blueCode;
    }

    // Blue 코드
    @Override
    public void execute() {
        propertyPanel.changeBlueCode(blueCode);
    }
}
