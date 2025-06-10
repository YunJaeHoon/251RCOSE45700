package command;

import panel.property.PropertyPanel;

public class ChangeGreenCodeCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // Green 코드
    private final int greenCode;

    // 생성자
    public ChangeGreenCodeCommand(int greenCode) {
        this.greenCode = greenCode;
    }

    // Green 코드 변경
    @Override
    public void execute() {
        propertyPanel.changeGreenCode(greenCode);
    }

    // 되돌리기
    @Override
    public void undo() {}
}
