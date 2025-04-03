package tool;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ToolbarPanel extends JPanel
{
    private final List<ToolSelectionListener> listeners = new ArrayList<>();
    private final JToolBar toolBar;
    private final ButtonGroup toolGroup;
    private ToolMode currentToolMode = ToolMode.SELECT; // 기본 도구

    // 생성자 내부에서 버튼 생성 호출 시 경로 수정
    public ToolbarPanel()
    {
        super(new BorderLayout());
        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);
        toolGroup = new ButtonGroup();

        // 아이콘 경로 예시 (프로젝트 내 icons 폴더가 있다고 가정)
        createToolButton("Select", "/icons/select.png", ToolMode.SELECT);
        createToolButton("Line", "/icons/line.png", ToolMode.LINE);
        createToolButton("Rectangle", "/icons/rectangle.png", ToolMode.RECTANGLE);
        createToolButton("Ellipse", "/icons/ellipse.png", ToolMode.ELLIPSE);
        createToolButton("Text", "/icons/text.png", ToolMode.TEXT);

        if (toolBar.getComponentCount() > 0 && toolBar.getComponent(0) instanceof JToggleButton)
        {
            ((JToggleButton) toolBar.getComponent(0)).setSelected(true);
            currentToolMode = ToolMode.SELECT;  // 초기 모드 설정
        }

        add(toolBar, BorderLayout.CENTER);
    }

    private void createToolButton(String toolTip, String relativeIconPath, ToolMode mode)
    {
        JToggleButton button = new JToggleButton();
        try {
            // 클래스 로더를 통해 리소스 로딩 시도
            java.net.URL imgURL = getClass().getResource(relativeIconPath);
            if (imgURL != null) {
                button.setIcon(new ImageIcon(imgURL));
            } else {
                button.setText(toolTip); // 아이콘 없을 때 텍스트
                System.err.println("Icon not found (relative): " + relativeIconPath);
                // 절대 경로 시도 (선택 사항, 권장되지 않음)
                // ImageIcon icon = new ImageIcon("icons/" + relativeIconPath); // 프로젝트 루트 기준
                // if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                //     button.setIcon(icon);
                // } else { ... }
            }
        } catch (Exception e) {
            button.setText(toolTip);
            System.err.println("Error loading icon: " + relativeIconPath + " - " + e.getMessage());
        }
        button.setToolTipText(toolTip);

        // 액션 리스너: 버튼 클릭 시 모드 변경 및 리스너 알림
        button.addActionListener(e -> {
            if (button.isSelected()) {
                currentToolMode = mode;
                System.out.println("Toolbar: Tool selected: " + currentToolMode);
                notifyListeners(currentToolMode);
            }
        });

        toolGroup.add(button);
        toolBar.add(button);

    }

    // 리스너 추가 메소드
    public void addToolSelectionListener(ToolSelectionListener listener) {
        listeners.add(listener);
    }

    // 리스너 제거 메소드
    public void removeToolSelectionListener(ToolSelectionListener listener) {
        listeners.remove(listener);
    }

    // 등록된 모든 리스너에게 알림
    private void notifyListeners(ToolMode selectedTool) {
        for (ToolSelectionListener listener : listeners) {
            listener.toolSelected(selectedTool);
        }
    }

    // 현재 선택된 도구를 외부에서 알 필요가 있을 경우 (선택 사항)
    public ToolMode getSelectedTool() {
        return currentToolMode;
    }
}