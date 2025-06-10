package panel.color;

import command.Command;
import command.SelectColorCommand;
import panel.canvas.CanvasPanel;
import panel.toolbar.ToolSelectionListener;
import tool.ToolMode;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ColorPanel extends JPanel
{
	private Color currentColor = Color.BLACK;									// 현재 색깔
	private final Map<Color, JToggleButton> colorButtonMap = new HashMap<>();	// 버튼 맵
	
	// 색상 선택 이벤트 리스너 리스트
	private final List<ColorSelectionListener> colorSelectionListeners = new ArrayList<>();

	// 싱글톤
	public static ColorPanel getInstance() { return ColorPanel.SingleInstanceHolder.INSTANCE; }
	private static class SingleInstanceHolder { private static final ColorPanel INSTANCE = new ColorPanel(); }

	// 생성자
	private ColorPanel()
	{
		setLayout(new FlowLayout());
		
		// 버튼 생성
		JToggleButton initialButton = createColorButton(Color.BLACK);
		initialButton.setSelected(true);
		initialButton.setPreferredSize(new Dimension(40, 40));
		
		createColorButton(Color.RED);
		createColorButton(Color.ORANGE);
		createColorButton(Color.YELLOW);
		createColorButton(Color.GREEN);
		createColorButton(Color.BLUE);
		createColorButton(Color.MAGENTA);
	}
	
	// 버튼 생성 메서드
	private JToggleButton createColorButton(Color selectedColor)
	{
		// 버튼 생성
		JToggleButton button = new JToggleButton();
		
		// 버튼 설정
		button.setBackground(selectedColor);
		button.setPreferredSize(new Dimension(30, 30));
		
		// 버튼 맵에 버튼 추가
		colorButtonMap.put(selectedColor, button);

		// 버튼이 눌렸을 때의 기본 스타일 제거
		button.setUI(new BasicButtonUI() {
			@Override
			public void update(Graphics g, JComponent c) {
				if (c.isOpaque()) {
					g.setColor(c.getBackground());
					g.fillRect(0, 0, c.getWidth(), c.getHeight());
				}
				paint(g, c);
			}
		});
		
		// 액션 리스너: 버튼 클릭 시 색상 변경 명령 실행
		button.addActionListener(e -> {
			if(button.isSelected() && currentColor != selectedColor)
			{
				Command command = new SelectColorCommand(selectedColor);
				command.execute();
			}
		});
		
		// 버튼 추가
		this.add(button);
		
		return button;
	}
	
	// 색상 선택 이벤트 리스너 추가 메서드
	public void addColorSelectionListener(ColorSelectionListener listener) {
		colorSelectionListeners.add(listener);
		listener.colorSelected(currentColor);
	}

	// 등록된 모든 리스너에게 색상 선택 이벤트 알림
	private void notifyColorSelection(Color selectedColor) {
		for (ColorSelectionListener listener : colorSelectionListeners) {
			listener.colorSelected(selectedColor);
		}
	}

	// 색상 변경 메서드
	public void setCurrentColor(Color color)
	{
		// 새로운 색상이 선택된 경우
		if(!color.equals(this.currentColor))
		{
			// 색상 변경
			this.currentColor = color;

			// 선택된 색상 버튼 스타일 조정
			JToggleButton selectedButton = colorButtonMap.get(color);
			if (selectedButton != null) {
				selectedButton.setPreferredSize(new Dimension(40, 40));
				selectedButton.revalidate();
				selectedButton.repaint();
			}

			// 다른 버튼 스타일 조정
			for(Map.Entry<Color, JToggleButton> entry : colorButtonMap.entrySet())
			{
				if(!entry.getKey().equals(color))
				{
					JToggleButton otherButton = entry.getValue();
					otherButton.setPreferredSize(new Dimension(30, 30));
					otherButton.revalidate();
					otherButton.repaint();
				}
			}

			// 리스너 알림
			notifyColorSelection(color);
		}
	}

}
