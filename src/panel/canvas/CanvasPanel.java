package panel.canvas;

import command.Command;
import command.MouseDraggedCommand;
import command.MousePressedCommand;
import command.MouseReleasedCommand;
import panel.color.ColorSelectionListener;
import panel.property.ChangeComponentPropertyListener;
import component.Component;
import component.Composite;
import tool.ToolMode;
import panel.toolbar.ToolSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CanvasPanel extends JPanel implements ToolSelectionListener, ColorSelectionListener, ChangeComponentPropertyListener, MouseListener, MouseMotionListener
{
	private final Composite composite = new Composite();            // 지금까지 그려진 컴포넌트 리스트
	private final Composite selectedComposite = new Composite();		// 현재 선택한 컴포넌트 리스트

	private ToolMode currentToolMode;		// 현재 도구 모드
	private final JLabel currentToolLabel;	// 현재 도구를 표시하는 레이블
	private Color currentColor;				// 현재 색상
	
	// 컴포넌트 선택 이벤트 리스너 리스트
	private final List<ComponentSelectionListener> componentSelectionListeners = new ArrayList<>();

	// 커맨드 기록
	private final Deque<Command> commandHistory = new ArrayDeque<>();	// 커맨드 기록 스택
	private final Deque<Command> redoStack = new ArrayDeque<>();		// 커맨드 Redo 스택

	// 싱글톤
	public static CanvasPanel getInstance() { return CanvasPanel.SingleInstanceHolder.INSTANCE; }
	private static class SingleInstanceHolder { private static final CanvasPanel INSTANCE = new CanvasPanel(); }

	/// 생성자

	private CanvasPanel()
	{
		// 수동 배치 사용
		setLayout(null);

		// 캔버스 패널을 포커스 가능하도록 설정
		setFocusable(true);
		requestFocusInWindow();

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

	/// Getter

	// 컴포넌트 리스트 얻기
	public Composite getComponentList() {
		return composite;
	}

	// 선택된 컴포넌트 리스트 얻기
	public Composite getSelectedComponentList() {
		return selectedComposite;
	}

	/// 컴포넌트 선택 이벤트 관리
	
	// 컴포넌트 선택 이벤트 리스너 추가 메서드
	public void addComponentSelectionListener(ComponentSelectionListener listener) {
		componentSelectionListeners.add(listener);
		listener.selectComponents(selectedComposite);
	}
	
	// 등록된 모든 리스너에게 컴포넌트 속성 출력 이벤트 알림
	public void notifyDisplayProperty()
	{
		// 리스너 알림
		for(ComponentSelectionListener listener : componentSelectionListeners) {
			listener.displayProperty();
		}
	}

	/// 속성 변경 이벤트 처리
	
	// 선택한 컴포넌트의 x 좌표 변경
	@Override
	public void changeX(int x) {
		selectedComposite.changeX(x);
		repaint();
	}
	
	// 선택한 컴포넌트의 y 좌표 변경
	@Override
	public void changeY(int y) {
		selectedComposite.changeY(y);
		
		repaint();
	}
	
	// 선택한 컴포넌트의 width 변경
	@Override
	public void changeWidth(int width) {
		selectedComposite.setWidth(width);
		
		repaint();
	}
	
	// 선택한 컴포넌트의 height 변경
	@Override
	public void changeHeight(int height) {
		selectedComposite.setHeight(height);
		
		repaint();
	}
	
	// 선택한 컴포넌트의 red 코드 변경
	@Override
	public void changeRedCode(int redCode) {
		selectedComposite.setRedCode(redCode);
		
		repaint();
	}
	
	// 선택한 컴포넌트의 green 코드 변경
	@Override
	public void changeGreenCode(int greenCode) {
		selectedComposite.setGreenCode(greenCode);
		
		repaint();
	}
	
	// 선택한 컴포넌트의 blue 코드 변경
	@Override
	public void changeBlueCode(int blueCode) {
		selectedComposite.setBlueCode(blueCode);
		
		repaint();
	}
	
	// 선택한 컴포넌트를 제일 앞으로 (내부 순서 유지)
	@Override
	public void bringToFront() {
		List<Component> selected = selectedComposite.getChildren();
		for (Component component : selected) {
			composite.remove(component);
		}
		for (Component component : selected) {
			composite.add(component);
		}
		repaint();
	}
	
	// 선택한 컴포넌트를 제일 뒤로 (내부 순서 유지)
	@Override
	public void sendToBack() {
		List<Component> selected = selectedComposite.getChildren();
		for (Component component : selected) {
			composite.remove(component);
		}
		for (Component component : selected) {
			composite.add(0, component);
		}
		repaint();
	}

	/// 도구 선택 이벤트 처리
	
	// 도구 선택 이벤트 처리 메서드
	@Override
	public void toolSelected(ToolMode selectedTool)
	{
		// 이미 선택된 도구라면 아무 처리도 하지 않음
		if(currentToolMode == selectedTool)
			return;
		
		// 전체 컴포넌트 선택 취소
		selectedComposite.clear();
		
		// 현재 도구 변경
		currentToolMode = selectedTool;
		currentToolLabel.setText("현재 도구: " + selectedTool);
		
		repaint();
	}

	/// 색상 선택 이벤트 처리
	
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
		selectedComposite.setColor(color);
		
		repaint();
	}
	
	/// 마우스 버튼을 눌렀을 때, 이벤트 처리 메서드

	@Override
	public void mousePressed(MouseEvent e)
	{
		Command command = new MousePressedCommand(e);
		executeCommand(command);
	}
	
	/// 마우스 버튼을 누른채로 드래그했을 때, 이벤트 처리 메서드

	@Override
	public void mouseDragged(MouseEvent e)
	{
		Command command = new MouseDraggedCommand(e);
		command.execute();
		redoStack.clear();
	}
	
	/// 마우스 버튼에서 손을 뗐을 때, 이벤트 처리 메서드

	@Override
	public void mouseReleased(MouseEvent e)
	{
		Command command = new MouseReleasedCommand(e);
		executeCommand(command);
	}

	/// 커맨드 실행

	public void executeCommand(Command command)
	{
		command.execute();				// 커맨드 실행
		commandHistory.push(command);	// 커맨드 기록
		redoStack.clear();				// Redo 스택 초기화
	}

	/// 커맨드 Undo

	public void undo()
	{
		if(!commandHistory.isEmpty())
		{
			Command lastCommand = commandHistory.pop();		// 가장 최근 기록의 커맨드 pop
			lastCommand.undo();								// 커맨드 Undo
			redoStack.push(lastCommand);					// Redo 스택에 push
			repaint();
		}
	}

	/// 커맨드 Redo

	public void redo()
	{
		if(!redoStack.isEmpty())
		{
			Command command = redoStack.pop();	// 가장 최근 Undo된 커맨드 pop
			command.execute();					// 커맨드 실행(Redo)
			commandHistory.push(command);		// 커맨드 기록 스택에 push
			repaint();
		}
	}

	/// 컴포넌트 그리기

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// 모든 컴포넌트 그리기
		composite.draw(g);
		
		g.setColor(Color.BLACK);
		
		selectedComposite.drawBounds(g);
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {}
}
