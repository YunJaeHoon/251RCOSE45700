package panel.canvas;

import tool.Component;
import tool.ToolMode;
import panel.toolbar.ToolSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class CanvasPanel extends JPanel implements ToolSelectionListener, MouseListener, MouseMotionListener
{
    private final ArrayList<Component> components = new ArrayList<>();  // 지금까지 그려진 컴포넌트

    private Component currentComponent;         // 현재 대상 컴포넌트 객체
    private ToolMode currentToolMode;           // 현재 도구 모드
    JLabel currentToolLabel = new JLabel();     // 현재 도구를 표시하는 레이블

    // 생성자
    CanvasPanel()
    {
        // 마우스 이벤트 리스너 등록
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.setBackground(Color.WHITE);

        this.add(currentToolLabel, BorderLayout.NORTH);
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

    // 마우스 버튼을 눌렀을 때, 이벤트 처리 메서드
    @Override
    public void mousePressed(MouseEvent e)
    {
        currentComponent = currentToolMode.getComponentFactory().createComponent(e);
        currentComponent.onMousePressed(e);
        repaint();
    }

    // 마우스 버튼을 누른채로 드래그했을 때, 이벤트 처리 메서드
    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (currentComponent != null) {
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
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
