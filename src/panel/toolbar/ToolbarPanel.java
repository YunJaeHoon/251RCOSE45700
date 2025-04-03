package panel.toolbar;

import tool.ToolMode;
import tool.ToolSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ToolbarPanel extends JPanel
{
    private final List<ToolSelectionListener> listeners;    // 도구 선택 이벤트 리스너 리스트
    private final JToolBar toolBar;                         // 도구 선택창
    private final ButtonGroup buttonGroup;                  // 도구 단일 선택을 위한 버튼 그룹
    private ToolMode currentToolMode;                       // 현재 도구

    // 생성자 내부에서 버튼 생성 호출 시 경로 수정
    ToolbarPanel()
    {
        // 객체 생성
        super(new BorderLayout());

        // 필드 변수 초기화
        this.listeners = new ArrayList<>();
        this.toolBar = new JToolBar(JToolBar.VERTICAL);
        this.buttonGroup = new ButtonGroup();
        this.currentToolMode = ToolMode.SELECT;

        // 툴바 설정
        this.toolBar.setFloatable(false);

        // 버튼 생성
        JToggleButton selectButton = createToolButton("Select", "/icons/select.png", ToolMode.SELECT);
        createToolButton("Line", "/icons/line.png", ToolMode.LINE);
        createToolButton("Rectangle", "/icons/rectangle.png", ToolMode.RECTANGLE);
        createToolButton("Ellipse", "/icons/ellipse.png", ToolMode.ELLIPSE);
        createToolButton("Text", "/icons/text.png", ToolMode.TEXT);

        // SELECT 버튼을 기본으로 선택
        selectButton.setSelected(true);

        // 툴바 추가
        add(toolBar, BorderLayout.CENTER);
    }

    private JToggleButton createToolButton(String name, String relativeIconPath, ToolMode mode)
    {
        // 버튼 생성
        JToggleButton button = new JToggleButton();

        // 버튼 아이콘 설정
        try {
            java.net.URL imgURL = getClass().getResource(relativeIconPath);
            button.setIcon(new ImageIcon(imgURL));
        } catch (Exception e) {
            button.setText(name);
        }

        // 버튼 텍스트 설정
        button.setToolTipText(name);

        // 버튼 그룹에 버튼 추가 (버튼 단일 선택)
        buttonGroup.add(button);

        // 액션 리스너: 버튼 클릭 시 모드 변경 및 리스너 알림
        button.addActionListener(e -> {
            if (button.isSelected() && currentToolMode != mode)
            {
                currentToolMode = mode;
                notifyListeners(currentToolMode);
            }
        });

        // 도구 선택창에 버튼 추가
        toolBar.add(button);

        return button;
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
}