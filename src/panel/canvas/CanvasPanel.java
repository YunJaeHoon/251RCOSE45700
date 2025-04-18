package panel.canvas;

import panel.color.ColorSelectionListener;
import panel.property.PropertyPanel;
import tool.Component;
import tool.Text;
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
    private Component selectedComponent;    // 선택된 컴포넌트

    private final JLabel currentToolLabel = new JLabel();   // 현재 도구를 표시하는 레이블
    private PropertyPanel propertyPanel;  // 속성창 패널 참조

    public void setPropertiesPanel(PropertyPanel propertyPanel) {
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
    }

    // 마우스 버튼을 눌렀을 때, 이벤트 처리 메서드
    @Override
    public void mousePressed(MouseEvent e)
    {
        // 선택 도구일 때 기존 객체 대상으로 hit testing 실행
        if (currentToolMode == ToolMode.SELECT) {
            for (int i = components.size() - 1; i >= 0; i--) {
                Component comp = components.get(i);
                if (comp.contains(e.getPoint())) {
                    selectedComponent = comp;
                    if (propertyPanel != null) {
                        propertyPanel.displayProperties(comp);
                    }
                    repaint();
                    return;
                }
            }
            selectedComponent = null;
            if (propertyPanel != null) {
                propertyPanel.clearProperties();
            }
            repaint();
        } else {
            // 그리기 모드면 새 컴포넌트 생성
            currentComponent = currentToolMode.getComponentFactory().createComponent(e);
            currentComponent.onMousePressed(e, currentColor);
            repaint();
        }
    }

    // 마우스 버튼을 누른채로 드래그했을 때, 이벤트 처리 메서드
    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (currentToolMode != ToolMode.SELECT && currentComponent != null) {
            currentComponent.onMouseDragged(e);
            repaint();
        }
    }

    // 마우스 버튼에서 손을 뗐을 때, 이벤트 처리 메서드
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (currentToolMode != ToolMode.SELECT && currentComponent != null)
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
        // 선택된 객체의 외곽선 표시
        if (selectedComponent != null) {
            Rectangle bounds = selectedComponent.getBounds();
            g.setColor(Color.BLACK);
            g.drawRect(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
