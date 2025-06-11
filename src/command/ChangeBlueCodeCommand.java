package command;

import component.Component;
import component.Composite;
import component.memento.ComponentMemento;
import panel.canvas.CanvasPanel;
import panel.property.PropertyPanel;

import java.util.HashMap;
import java.util.Map;

public class ChangeBlueCodeCommand implements Command
{
    // 패널
    private final CanvasPanel canvasPanel = CanvasPanel.getInstance();          // 캔버스 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();    // 속성 패널

    // 스냅샷
    private final Map<Component, ComponentMemento> mementoMap = new HashMap<>();
    private final Composite composite = canvasPanel.getSelectedComponentList();

    // Blue 코드
    private final int newBlueCode;

    // 생성자
    public ChangeBlueCodeCommand(int newBlueCode) {
        this.newBlueCode = newBlueCode;
    }

    // 커맨드 실행
    @Override
    public void execute()
    {
        // 상태 기록
        for (Component component : composite.getChildren()) {
            mementoMap.put(component, component.saveState());
        }

        // x 좌표 변경
        propertyPanel.changeBlueCode(newBlueCode);
    }

    // 커맨드 Undo
    @Override
    public void undo()
    {
        // 상대 복원
        for (Map.Entry<Component, ComponentMemento> entry : mementoMap.entrySet())
        {
            Component component = entry.getKey();
            ComponentMemento memento = entry.getValue();
            component.restoreState(memento);
        }

        CanvasPanel.getInstance().repaint();
    }
}
