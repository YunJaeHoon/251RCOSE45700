package panel.canvas;

import panel.color.ColorSelectionListener;
import panel.property.PropertyPanel;
import tool.Component;
import tool.Properties;
import tool.ToolMode;
import panel.toolbar.ToolSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class CanvasPanel extends JPanel implements ToolSelectionListener, ColorSelectionListener, MouseListener, MouseMotionListener
{
    private final ArrayList<Component> components = new ArrayList<>();  // 지금까지 그려진 컴포넌트

    private Component currentComponent;         // 현재 대상 컴포넌트 객체
    private ToolMode currentToolMode;           // 현재 도구 모드
    private Color currentColor;                 // 현재 색상
    // 여러 컴포넌트를 선택할 수 있도록 List 사용
    private final ArrayList<Component> selectedComponents = new ArrayList<>();

    private final JLabel currentToolLabel = new JLabel();   // 현재 도구를 표시하는 레이블
    private PropertyPanel propertyPanel;  // 속성창 패널 참조

    // 이동 시 기준 좌표 오프셋
    private final Point offset = new Point();

    public void setPropertyPanel(PropertyPanel propertyPanel) {
        this.propertyPanel = propertyPanel;
    }
    // 생성자
    CanvasPanel()
    {
        // 수동 배치 사용
        this.setLayout(null);

        // 마우스 이벤트 리스너 등록
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // 배경색 설정
        this.setBackground(Color.WHITE);

        // 현재 도구 레이블 설정
        currentToolLabel.setBounds(10, 10, 200, 20);
        this.add(currentToolLabel);
    }

    // 도구 선택 이벤트 처리 메서드
    @Override
    public void toolSelected(ToolMode selectedTool)
    {
        if(currentToolMode == selectedTool)
            return;

        // select 모드에서 다른 모드로 수정시
        if(selectedTool != ToolMode.SELECT) {
            selectedComponents.clear();
            if(propertyPanel != null) {
                propertyPanel.clearProperties();
            }
            repaint();
        }
        currentToolMode = selectedTool;
        currentToolLabel.setText("현재 도구: " + selectedTool);
    }

    // 색상 선택 이벤트 처리 메서드
    @Override
    public void colorSelected(Color color)
    {
        if(currentColor == color)
            return;

        currentColor = color;
        System.out.println(currentColor);

        // select 모드이고 선택된 컴포넌트가 있을 경우 색상 업데이트
        if (currentToolMode == ToolMode.SELECT && !selectedComponents.isEmpty()) {
            // 기존 속성값을 가져온 후 색상만 바꾸어 설정
            for (Component comp : selectedComponents) {
                Properties props = comp.getProperties();
                comp.setProperties(props.getX(), props.getY(), props.getWidth(), props.getHeight(), color);
            }
            // 속성창에 변경된 색상 즉시 반영
            if (propertyPanel != null) {
                propertyPanel.displayProperties(selectedComponents);
            }
            repaint();
        }
    }

    // 마우스 버튼을 눌렀을 때, 이벤트 처리 메서드
    @Override
    public void mousePressed(MouseEvent e)
    {
        // 선택 도구일 때 기존 객체 대상으로 hit testing 실행
        if (currentToolMode == ToolMode.SELECT) {
            // 클릭 위치 저장
            offset.setLocation(e.getX(), e.getY());
            // Ctrl 키가 눌렸는지 체크
            boolean ctrlDown = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
            boolean found = false;
            // 역순으로 hit test 수행
            for (int i = components.size() - 1; i >= 0; i--) {
                Component comp = components.get(i);
                if (comp.contains(e.getPoint())) {
                    found = true;
                    // Ctrl 키가 없으면 단일 선택으로 처리
                    if (!ctrlDown) {
                        selectedComponents.clear();
                    }
                    if (!selectedComponents.contains(comp)) {
                        selectedComponents.add(comp);
                    }
                    // 선택된 컴포넌트가 하나인지 여부와 상관없이 항상 속성창에 전달
                    if (propertyPanel != null) {
                        propertyPanel.displayProperties(selectedComponents);
                    }
                    repaint();
                    break;
                }
            }
            // 아무것도 hit되지 않았을 경우 선택 초기화
            if (!found) {
                selectedComponents.clear();
                if (propertyPanel != null) {
                    propertyPanel.clearProperties();
                }
                repaint();
            }
        } else {
            // 그리기 모드면 새 컴포넌트 생성
            currentComponent = currentToolMode.getComponentFactory().createComponent(e);
            currentComponent.onMousePressed(e, currentColor);
            repaint();
        }
    }

    // 마우스 버튼을 누른채로 드래그했을 때, 이벤트 처리 메서드
    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentToolMode == ToolMode.SELECT && !selectedComponents.isEmpty()) {
            // 각 선택된 컴포넌트에 대해 오프셋을 적용하여 이동
            for (Component comp : selectedComponents) {
                if (offset != null) {
                    Properties props = comp.getProperties();
                    int newX = e.getX() - offset.x + props.getX();
                    int newY = e.getY() - offset.y + props.getY();
                    comp.setProperties(newX, newY, props.getWidth(), props.getHeight(), props.getColor());
                }
            }
            if (offset != null) {
                offset.setLocation(e.getX(), e.getY());
            }
            // 단일 선택이면 속성창 업데이트
            if (propertyPanel != null && selectedComponents.size() == 1) {
                propertyPanel.displayProperties(selectedComponents);
            }
            repaint();
        } else if (currentToolMode != ToolMode.SELECT && currentComponent != null) {
            currentComponent.onMouseDragged(e);
            repaint();
        }
    }

    // 마우스 버튼에서 손을 뗐을 때, 이벤트 처리 메서드
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (currentComponent != null)
        {
            currentComponent.onMouseReleased(e);
            components.add(currentComponent);
            currentComponent = null;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // 완료된 컴포넌트들 먼저 그림
        for (Component component : components) {
            component.draw(g);
        }

        // 현재 그리는 중인 컴포넌트도 그림
        if (currentComponent != null) {
            currentComponent.draw(g);
        }
        g.setColor(Color.BLACK);
        for (Component comp : selectedComponents) {
            Rectangle bounds = comp.getBounds();
            g.drawRect(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
