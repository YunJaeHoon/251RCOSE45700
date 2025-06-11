package command;

import component.Component;
import component.Composite;
import component.memento.ComponentMemento;
import panel.canvas.CanvasPanel;
import panel.property.PropertyPanel;

import java.util.HashMap;
import java.util.Map;

public class ChangeRedCodeCommand implements Command
{
    // 패널
    private final CanvasPanel canvasPanel = CanvasPanel.getInstance();          // 캔버스 패널
    private final PropertyPanel propertyPanel = PropertyPanel.getInstance();    // 속성 패널

    // 스냅샷
    private final Map<Component, ComponentMemento> mementoMap = new HashMap<>();
    private final Composite composite = canvasPanel.getSelectedComponentList();

    // Red 코드
    private final int newRedCode;

    // 생성자
    public ChangeRedCodeCommand(int newRedCode) {
        this.newRedCode = newRedCode;
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
        propertyPanel.changeRedCode(newRedCode);
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
