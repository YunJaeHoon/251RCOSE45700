package panel.canvas;

import panel.color.ColorSelectionListener;
import panel.property.ChangeComponentPropertyListener;
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
import java.util.List;

public class CanvasPanel extends JPanel implements ToolSelectionListener, ColorSelectionListener, ChangeComponentPropertyListener, MouseListener, MouseMotionListener
{
	private final List<Component> components = new ArrayList<>();				// 지금까지 그려진 컴포넌트 리스트
	private final List<Component> selectedComponents = new ArrayList<>();		// 현재 선택한 컴포넌트 리스트
	
	private Component currentComponent;		// 현재 대상 컴포넌트 객체
	private ToolMode currentToolMode;		// 현재 도구 모드
	private final JLabel currentToolLabel;	// 현재 도구를 표시하는 레이블
	private Color currentColor;				// 현재 색상
	
	// 컴포넌트 선택 이벤트 리스너 리스트
	private final List<ComponentSelectionListener> componentSelectionListeners = new ArrayList<>();
	
	// 이동 시 기준 좌표 오프셋
	private final Point offset = new Point();
	
	// 추가된 크기 조절 관련 필드
	private boolean isResizing = false;
	private Component resizingComponent = null;
	private final int HANDLE_SIZE = 10;
	private final Point resizeStartPoint = new Point();

	// 이전에 만들어진 텍스트 컴포넌트
	private Text activeTextComponent = null;
	
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
	
	// 컴포넌트 선택 이벤트 리스너 추가 메서드
	public void addComponentSelectionListener(ComponentSelectionListener listener) {
		componentSelectionListeners.add(listener);
		listener.selectComponents(selectedComponents);
	}
	
	// 컴포넌트 선택 이벤트 리스너 제거 메서드
	public void removeComponentSelectionListener(ComponentSelectionListener listener) {
		componentSelectionListeners.remove(listener);
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
		// 선택 도구일 때 기존 객체 대상으로 hit testing 실행
		if (currentToolMode == ToolMode.SELECT)
		{
			// 클릭 위치 저장
			offset.setLocation(e.getX(), e.getY());
			
			// Ctrl 키를 눌렸는지 여부
			boolean ctrlDown = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
			
			// 클릭한 곳에 컴포넌트가 존재했는지 여부
			boolean found = false;
			
			// 먼저, 현재 하나의 컴포넌트가 선택되었고, 크기 조절 핸들러를 눌렀는지 확인
			if (selectedComponents.size() == 1)
			{
				Component component = selectedComponents.getFirst();
				
				Rectangle bounds = component.getBounds();
				Rectangle resizeHandle = new Rectangle(
						bounds.x + bounds.width,
						bounds.y + bounds.height,
						HANDLE_SIZE,
						HANDLE_SIZE
				);
				
				if (resizeHandle.contains(e.getPoint())) {
					isResizing = true;
					resizingComponent = component;
					resizeStartPoint.setLocation(e.getX(), e.getY());
					
					found = true;
				}
			}
			
			// 크기 조절 핸들러를 누른 것이 아니라면, 가장 위의 컴포넌트부터 hit test 수행
			if(!found)
			{
				for (int i = components.size() - 1; i >= 0; i--)
				{
					Component component = components.get(i);
					
					if (component.contains(e.getPoint()))
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
						if (!selectedComponents.contains(component)) {
							selectedComponents.add(component);
							notifyDisplayProperty();
						}
						
						repaint();
						break;
					}
				}
			}
			
			// 아무것도 hit 되지 않았을 경우 선택 초기화
			if (!found) {
				selectedComponents.clear();
				repaint();
			}
		} else {
			// 그리기 모드면 새 컴포넌트 생성
			currentComponent = currentToolMode.getComponentFactory().createComponent(e);
			currentComponent.onMousePressed(e, currentColor);

			if (currentComponent instanceof Text textComponent)
			{
				// 이전 텍스트 리스너 제거
				if (activeTextComponent != null) {
					removeKeyListener(activeTextComponent);
					activeTextComponent.setActive(false);
				}

				// 새 텍스트 등록
				requestFocusInWindow();
				addKeyListener(textComponent);
				activeTextComponent = textComponent;

				components.add(textComponent);
			}
			else
			{
				// 텍스트가 아닌 경우 active 텍스트 비활성화 및 리스너 제거
				if (activeTextComponent != null) {
					removeKeyListener(activeTextComponent);
					activeTextComponent.setActive(false);
					activeTextComponent = null;
				}

				components.add(currentComponent);
			}

			repaint();
		}
	}
	
	// 마우스 버튼을 누른채로 드래그했을 때, 이벤트 처리 메서드
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (currentToolMode == ToolMode.SELECT)
		{
			// 컴포넌트 크기 변경
			if (isResizing && resizingComponent != null)
			{
				int newWidth = Math.max(10, resizingComponent.getWidth() + (e.getX() - resizeStartPoint.x));
				int newHeight = Math.max(10, resizingComponent.getHeight() + (e.getY() - resizeStartPoint.y));
				
				resizingComponent.setWidth(newWidth);
				resizingComponent.setHeight(newHeight);
				
				resizeStartPoint.setLocation(e.getX(), e.getY());
				notifyDisplayProperty();
				
				repaint();
			}
			// 컴포넌트 위치 변경
			else if (!selectedComponents.isEmpty()) {
				for (Component component : selectedComponents)
				{
					if (offset != null) {
						int newStartX = component.getStartX() + e.getX() - offset.x;
						int newStartY = component.getStartY() + e.getY() - offset.y;
						int newEndX = component.getEndX() + e.getX() - offset.x;
						int newEndY = component.getEndY() + e.getY() - offset.y;
						
						component.setStartX(newStartX);
						component.setStartY(newStartY);
						component.setEndX(newEndX);
						component.setEndY(newEndY);
					}
				}
				
				if (offset != null) {
					offset.setLocation(e.getX(), e.getY());
				}
				
				notifyDisplayProperty();
				repaint();
			}
		}
		else {
			currentComponent.onMouseDragged(e);
			repaint();
		}
	}
	
	// 마우스 버튼에서 손을 뗐을 때, 이벤트 처리 메서드
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (isResizing) {
			isResizing = false;
			resizingComponent = null;
		}
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
			// 크기 조절 핸들을 그려서 표시
			g.fillRect(bounds.x + bounds.width, bounds.y + bounds.height, HANDLE_SIZE, HANDLE_SIZE);
			
		}
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {}
}
