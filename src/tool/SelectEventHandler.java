package tool;

import component.Component;
import panel.canvas.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class SelectEventHandler implements ToolEventHandler
{
    private Integer x = null;   // 마우스를 누르기 시작한 X 좌표
    private Integer y = null;   // 마우스를 누르기 시작한 Y 좌표

    private boolean isResizing = false;     // 크기 조절 중인지에 대한 여부
    private Component resizingComponent;    // 크기 조절 중인 컴포넌트
    private Integer resizingStartX;         // 크기 조절을 시작한 X 좌표
    private Integer resizingStartY;         // 크기 조절을 시작한 Y 좌표

    // 싱글톤
    public static SelectEventHandler getInstance() { return SelectEventHandler.SingleInstanceHolder.INSTANCE; }
    private static class SingleInstanceHolder { private static final SelectEventHandler INSTANCE = new SelectEventHandler(); }
    private SelectEventHandler() {}

    @Override
    public void onMousePressed(CanvasPanel canvasPanel, MouseEvent e, Color color)
    {
        // 커서 좌표 설정
        x = e.getX();
        y = e.getY();

        // Ctrl 키를 눌렸는지 여부
        boolean ctrlDown = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;

        // 클릭한 곳에 컴포넌트가 존재했는지 여부
        boolean found = false;

        // 선택된 컴포넌트 리스트
        List<Component> selectedComponents = canvasPanel.getSelectedComponentList();

        // 먼저, 현재 하나의 컴포넌트가 선택된 상태이고, 크기 조절 버튼을 눌렀는지 확인
        if (selectedComponents.size() == 1)
        {
            // 선택된 컴포넌트
            Component component = selectedComponents.getFirst();

            // 컴포넌트 경계선
            Rectangle bounds = component.getBounds();

            // 컴포넌트 크기 조절 버튼
            Rectangle resizeHandle = new Rectangle(
                    bounds.x + bounds.width,
                    bounds.y + bounds.height,
                    10,
                    10
            );

            // 컴포넌트의 크기 조절 버튼이 눌렸을 때
            if (resizeHandle.contains(e.getPoint())) {
                isResizing = true;
                resizingComponent = component;
                resizingStartX = e.getX();
                resizingStartY = e.getY();

                found = true;
            }
        }

        // 크기 조절 핸들러를 누른 것이 아니라면, 가장 위의 컴포넌트부터 hit test 수행
        if(!found)
        {
            // 전체 컴포넌트 리스트
            List<Component> components = canvasPanel.getComponentList();

            for(int i = components.size() - 1; i >= 0; i--)
            {
                Component component = components.get(i);

                if(component.contains(e.getPoint()))
                {
                    found = true;

                    // 이미 선택한 컴포넌트일 경우 아무 처리도 하지 않고 종료
                    if(selectedComponents.contains(component))
                        break;

                    // Ctrl 키가 눌리지 않았으면 단일 선택으로 처리
                    if (!ctrlDown) {
                        selectedComponents.clear();
                    }

                    // 이미 선택된 컴포넌트가 아니라면 선택
                    if (!selectedComponents.contains(component))
                    {
                        selectedComponents.add(component);
                        component.enableEditing(canvasPanel);

                        canvasPanel.notifyDisplayProperty();
                    }

                    break;
                }
            }
        }

        // 아무것도 hit 되지 않았을 경우 선택 초기화
        if (!found) {
            selectedComponents.clear();
        }
    }

    @Override
    public void onMouseDragged(CanvasPanel canvasPanel, MouseEvent e)
    {
        // 선택된 컴포넌트 리스트
        List<Component> selectedComponents = canvasPanel.getSelectedComponentList();

        // 컴포넌트 크기 변경
        if (isResizing && resizingComponent != null)
        {
            int newWidth = Math.max(10, resizingComponent.getWidth() + (e.getX() - resizingStartX));
            int newHeight = Math.max(10, resizingComponent.getHeight() + (e.getY() - resizingStartY));

            resizingComponent.setWidth(newWidth);
            resizingComponent.setHeight(newHeight);

            resizingStartX = e.getX();
            resizingStartY = e.getY();

            canvasPanel.notifyDisplayProperty();
        }
        // 컴포넌트 위치 변경
        else if (!selectedComponents.isEmpty())
        {
            for (Component component : selectedComponents)
            {
                if(x != null && y != null)
                {
                    int newStartX = (component.getStartX() + e.getX()) - x;
                    int newStartY = component.getStartY() + e.getY() - y;
                    int newEndX = component.getEndX() + e.getX() - x;
                    int newEndY = component.getEndY() + e.getY() - y;

                    component.resize(newStartX, newStartY, newEndX, newEndY);
                }
            }

            if(x != null && y != null)
            {
                x = e.getX();
                y = e.getY();
            }

            canvasPanel.notifyDisplayProperty();
        }
    }

    @Override
    public void onMouseReleased(CanvasPanel canvasPanel, MouseEvent e)
    {
        if (isResizing) {
            isResizing = false;
            resizingComponent = null;
        }
    }
}
