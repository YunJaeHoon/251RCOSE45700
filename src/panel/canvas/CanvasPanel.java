package panel.canvas;

import panel.color.ColorSelectionListener;
import panel.property.ChangeComponentPropertyListener;
import component.Component;
import component.Text;
import tool.ToolMode;
import panel.toolbar.ToolSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel implements ToolSelectionListener, ColorSelectionListener, ChangeComponentPropertyListener, MouseListener, MouseMotionListener
{
	private final List<Component> components = new ArrayList<>();				// 지금까지 그려진 컴포넌트 리스트
	private final List<Component> selectedComponents = new ArrayList<>();		// 현재 선택한 컴포넌트 리스트

	private ToolMode currentToolMode;		// 현재 도구 모드
	private final JLabel currentToolLabel;	// 현재 도구를 표시하는 레이블
	private Color currentColor;				// 현재 색상
	
	// 컴포넌트 선택 이벤트 리스너 리스트
	private final List<ComponentSelectionListener> componentSelectionListeners = new ArrayList<>();
	
	// 추가된 크기 조절 관련 필드


	private final int HANDLE_SIZE = 10;
	
	// 생성자
	public CanvasPanel()
	{
		// 수동 배치 사용
		setLayout(null);
		
		// 마우스 이벤트 리스너 등록
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// 배경색 설정
		setBackground(Color.WHITE);
		
		// 현재 도구 레이블 설정
		currentToolLabel = new JLabel();
		currentToolLabel.setBounds(10, 10, 200, 20);
		add(currentToolLabel);
	}

	// 컴포넌트 리스트 얻기
	public List<Component> getComponentList() {
		return components;
	}

	// 선택된 컴포넌트 리스트 얻기
	public List<Component> getSelectedComponentList() {
		return selectedComponents;
	}
	
	// 컴포넌트 선택 이벤트 리스너 추가 메서드
	public void addComponentSelectionListener(ComponentSelectionListener listener) {
		componentSelectionListeners.add(listener);
		listener.selectComponents(selectedComponents);
	}
	
	// 등록된 모든 리스너에게 컴포넌트 속성 출력 이벤트 알림
	public void notifyDisplayProperty() {
		for(ComponentSelectionListener listener : componentSelectionListeners) {
			listener.displayProperty();
		}
	}
	
	// 선택한 컴포넌트의 x 좌표 변경
	@Override
	public void changeX(int x) {
		for (Component component : selectedComponents) {
			int width = component.getWidth();
			
			component.setStartX(x);
			component.setEndX(width + x);
		}
		
		repaint();
	}
	
	// 선택한 컴포넌트의 y 좌표 변경
	@Override
	public void changeY(int y) {
		for (Component component : selectedComponents) {
			int height = component.getHeight();
			
			component.setStartY(y);
			component.setEndY(y + height);
		}
		
		repaint();
	}
	
	// 선택한 컴포넌트의 width 변경
	@Override
	public void changeWidth(int width) {
		for (Component component : selectedComponents) {
			component.setWidth(width);
		}
		
		repaint();
	}
	
	// 선택한 컴포넌트의 height 변경
	@Override
	public void changeHeight(int height) {
		for (Component component : selectedComponents) {
			component.setHeight(height);
		}
		
		repaint();
	}
	
	// 선택한 컴포넌트의 red 코드 변경
	@Override
	public void changeRedCode(int redCode) {
		for (Component component : selectedComponents) {
			component.setRedCode(redCode);
		}
		
		repaint();
	}
	
	// 선택한 컴포넌트의 green 코드 변경
	@Override
	public void changeGreenCode(int greenCode) {
		for (Component component : selectedComponents) {
			component.setGreenCode(greenCode);
		}
		
		repaint();
	}
	
	// 선택한 컴포넌트의 blue 코드 변경
	@Override
	public void changeBlueCode(int blueCode) {
		for (Component component : selectedComponents) {
			component.setBlueCode(blueCode);
		}
		
		repaint();
	}
	
	// 선택한 컴포넌트를 제일 앞으로 (내부 순서 유지)
	@Override
	public void bringToFront() {
		for (Component component : selectedComponents) {
			components.remove(component);
		}
		components.addAll(selectedComponents);
		
		repaint();
	}
	
	// 선택한 컴포넌트를 제일 뒤로 (내부 순서 유지)
	@Override
	public void bringToBack() {
		for (Component component : selectedComponents) {
			components.remove(component);
		}
		components.addAll(0, selectedComponents);
		
		repaint();
	}
	
	// 도구 선택 이벤트 처리 메서드
	@Override
	public void toolSelected(ToolMode selectedTool)
	{
		// 이미 선택된 도구라면 아무 처리도 하지 않음
		if(currentToolMode == selectedTool)
			return;
		
		// 전체 컴포넌트 선택 취소
		selectedComponents.clear();
		
		// 현재 도구 변경
		currentToolMode = selectedTool;
		currentToolLabel.setText("현재 도구: " + selectedTool);
		
		repaint();
	}
	
	// 색상 선택 이벤트 처리 메서드
	@Override
	public void colorSelected(Color color)
	{
		// 이미 선택된 색상이라면 아무 처리도 하지 않음
		if(currentColor == color)
			return;
		
		// 현재 색상 변경
		currentColor = color;
		
		// 선택한 컴포넌트 색상 변경
		for (Component component : selectedComponents) {
			component.setColor(color);
		}
		
		repaint();
	}
	
	// 마우스 버튼을 눌렀을 때, 이벤트 처리 메서드
	@Override
	public void mousePressed(MouseEvent e)
	{
		// 그리기 모드면 새 컴포넌트 생성
		currentToolMode.getToolEventHandler().onMousePressed(this, e, currentColor);
		repaint();
	}
	
	// 마우스 버튼을 누른채로 드래그했을 때, 이벤트 처리 메서드
	@Override
	public void mouseDragged(MouseEvent e)
	{
		currentToolMode.getToolEventHandler().onMouseDragged(this, e);
		repaint();
	}
	
	// 마우스 버튼에서 손을 뗐을 때, 이벤트 처리 메서드
	@Override
	public void mouseReleased(MouseEvent e)
	{
		currentToolMode.getToolEventHandler().onMouseReleased(e);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// 모든 컴포넌트 그리기
		for (Component component : components) {
			component.draw(g);
		}
		
		g.setColor(Color.BLACK);
		
		for (Component comp : selectedComponents) {
			Rectangle bounds = comp.getBounds();
			g.drawRect(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4);
			// 크기 조절 핸들을 그려서 표시
			g.fillRect(bounds.x + bounds.width, bounds.y + bounds.height, HANDLE_SIZE, HANDLE_SIZE);
			
		}
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {}
}
