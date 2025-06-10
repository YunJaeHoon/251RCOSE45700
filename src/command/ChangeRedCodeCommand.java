package command;

import panel.property.PropertyPanel;

public class ChangeRedCodeCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // Red 코드
    private final int redCode;

    // 생성자
    public ChangeRedCodeCommand(int redCode) {
        this.redCode = redCode;
    }

    // Red 코드 변경
    @Override
    public void execute() {
        propertyPanel.changeRedCode(redCode);
    }

    // 되돌리기
    @Override
    public void undo() {}
}
