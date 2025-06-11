package command;

import panel.property.PropertyPanel;

public class SendToBackCommand implements Command
{
    // 속성 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();

    // 맨 뒤로 이동
    @Override
    public void execute() {
        propertyPanel.sendToBack();
    }

    // 되돌리기
    @Override
    public void undo() {}
}
