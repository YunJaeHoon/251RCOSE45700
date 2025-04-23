package panel.toolbar;

import tool.ToolMode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ToolbarPanel extends JPanel
{
	private ToolMode currentToolMode = ToolMode.SELECT;				// 현재 도구 모드
	private final JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);   	// 도구 선택창
	private final ButtonGroup buttonGroup = new ButtonGroup();          	// 도구 단일 선택을 위한 버튼 그룹
	
	// 도구 선택 이벤트 리스너 리스트
	private final List<ToolSelectionListener> toolSelectionListeners = new ArrayList<>();
	
	// 생성자
	ToolbarPanel()
	{
		// 객체 생성
		super(new BorderLayout());
		
		// 툴바 설정
		toolBar.setFloatable(false);
		
		// 버튼 생성
		JToggleButton initialButton = createToolButton("Select", "/icons/select.png", ToolMode.SELECT);
		initialButton.setSelected(true);
		
		createToolButton("Line", "/icons/line.png", ToolMode.LINE);
		createToolButton("Rectangle", "/icons/rectangle.png", ToolMode.RECTANGLE);
		createToolButton("Ellipse", "/icons/ellipse.png", ToolMode.ELLIPSE);
		createToolButton("Text", "/icons/text.png", ToolMode.TEXT);
		
		// 툴바 추가
		add(toolBar, BorderLayout.CENTER);
	}
	
	// 버튼 생성 메서드
	private JToggleButton createToolButton(String name, String relativeIconPath, ToolMode selectedToolMode)
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
			if (button.isSelected() && currentToolMode != selectedToolMode)
			{
				currentToolMode = selectedToolMode;
				notifyToolSelection(currentToolMode);
			}
		});
		
		// 도구 선택창에 버튼 추가
		toolBar.add(button);
		
		return button;
	}
	
	// 도구 선택 이벤트 리스너 추가 메서드
	public void addToolSelectionListener(ToolSelectionListener listener) {
		toolSelectionListeners.add(listener);
		listener.toolSelected(currentToolMode);
	}
	
	// 도구 선택 이벤트 리스너 제거 메서드
	public void removeToolSelectionListener(ToolSelectionListener listener) {
		toolSelectionListeners.remove(listener);
	}
	
	// 등록된 모든 리스너에게 도구 선택 이벤트 알림
	private void notifyToolSelection(ToolMode selectedToolMode) {
		for (ToolSelectionListener listener : toolSelectionListeners) {
			listener.toolSelected(selectedToolMode);
		}
	}
}