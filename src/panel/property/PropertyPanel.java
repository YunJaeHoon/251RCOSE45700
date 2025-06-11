package panel.property;

import command.*;
import component.Composite;
import panel.canvas.CanvasPanel;
import panel.canvas.ComponentSelectionListener;
import panel.color.ColorSelectionListener;
import component.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyPanel extends JPanel implements ComponentSelectionListener, ColorSelectionListener
{
	// 현재 선택한 컴포넌트 리스트
	private Composite selectedComposite = new Composite();
	
	private final JTextField xField = new JTextField(5);
	private final JTextField yField = new JTextField(5);
	private final JTextField widthField = new JTextField(5);
	private final JTextField heightField = new JTextField(5);
	private final JTextField redField = new JTextField(3);
	private final JTextField greenField = new JTextField(3);
	private final JTextField blueField = new JTextField(3);
	
	// 컴포넌트 순서 변경 이벤트 리스너 리스트
	private final List<ChangeComponentPropertyListener> changeComponentPropertyListeners = new ArrayList<>();

	// 싱글톤
	public static PropertyPanel getInstance() { return PropertyPanel.SingleInstanceHolder.INSTANCE; }
	private static class SingleInstanceHolder { private static final PropertyPanel INSTANCE = new PropertyPanel(); }

	// 캔버스 패널
	private final CanvasPanel canvasPanel = CanvasPanel.getInstance();

	// 생성자
	private PropertyPanel()
	{
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		
		/// 첫 번째 행: x, y, width, height

		gbc.gridy = 0;
		
		gbc.gridx = 0;
		add(new JLabel("x:"), gbc);
		gbc.gridx = 1;
		add(xField, gbc);
		
		gbc.gridx = 2;
		add(new JLabel("y:"), gbc);
		gbc.gridx = 3;
		add(yField, gbc);
		
		gbc.gridx = 4;
		add(new JLabel("w:"), gbc);
		gbc.gridx = 5;
		add(widthField, gbc);
		
		gbc.gridx = 6;
		add(new JLabel("h:"), gbc);
		gbc.gridx = 7;
		add(heightField, gbc);
		
		/// 두 번째 행: R, G, B

		gbc.gridy = 1;
		
		gbc.gridx = 0;
		add(new JLabel("R:"), gbc);
		gbc.gridx = 1;
		add(redField, gbc);
		
		gbc.gridx = 2;
		add(new JLabel("G:"), gbc);
		gbc.gridx = 3;
		add(greenField, gbc);
		
		gbc.gridx = 4;
		add(new JLabel("B:"), gbc);
		gbc.gridx = 5;
		add(blueField, gbc);

		/// 세 번째 행: Undo/Redo 버튼

		gbc.gridy = 2;

		gbc.gridx = 0;
		gbc.gridwidth = 4;
		JButton undoButton = new JButton("←");
		undoButton.addActionListener(e -> canvasPanel.undo());
		add(undoButton, gbc);

		gbc.gridx = 4;
		gbc.gridwidth = 4;
		JButton redoButton = new JButton("→");
		redoButton.addActionListener(e -> canvasPanel.redo());
		add(redoButton, gbc);

		// 빈 패널을 추가하여 남은 공간을 채워 상단 정렬
		gbc.gridy = 3;

		gbc.gridx = 0;
		gbc.gridwidth = 8;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(new JPanel(), gbc);
		
		/// 네 번째 행: z-order 버튼

		gbc.gridy = 4;
		
		gbc.gridx = 0;
		gbc.gridwidth = 4;
		JButton bringToFrontButton = new JButton("맨 앞으로");
		add(bringToFrontButton, gbc);
		
		gbc.gridx = 4;
		gbc.gridwidth = 4;
		JButton sendToBackButton = new JButton("맨 뒤로");
		add(sendToBackButton, gbc);
		
		// 빈 패널을 추가하여 남은 공간을 채워 상단 정렬
		gbc.gridy = 5;
		
		gbc.gridx = 0;
		gbc.gridwidth = 8;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(new JPanel(), gbc);

		/// 액션 리스너 설정
		
		// 각 필드에 엔터키 입력 시 액션 리스너 추가
		xField.addActionListener(e -> {
			Command command = new ChangeXCommand(Integer.parseInt(xField.getText()));
			canvasPanel.executeCommand(command);
		});
		yField.addActionListener(e -> {
			Command command = new ChangeYCommand(Integer.parseInt(yField.getText()));
			canvasPanel.executeCommand(command);
		});
		widthField.addActionListener(e -> {
			Command command = new ChangeWidthCommand(Integer.parseInt(widthField.getText()));
			canvasPanel.executeCommand(command);
		});
		heightField.addActionListener(e ->{
			Command command = new ChangeHeightCommand(Integer.parseInt(heightField.getText()));
			canvasPanel.executeCommand(command);
		});
		redField.addActionListener(e -> {
			Command command = new ChangeRedCodeCommand(Integer.parseInt(redField.getText()));
			canvasPanel.executeCommand(command);
		});
		greenField.addActionListener(e -> {
			Command command = new ChangeGreenCodeCommand(Integer.parseInt(greenField.getText()));
			canvasPanel.executeCommand(command);
		});
		blueField.addActionListener(e -> {
			Command command = new ChangeBlueCodeCommand(Integer.parseInt(blueField.getText()));
			canvasPanel.executeCommand(command);
		});
		
		// 선택한 컴포넌트를 제일 앞으로 보내는 버튼 액션 리스너 추가 (내부 순서 유지)
		bringToFrontButton.addActionListener(e -> {
			Command command = new BringToFrontCommand();
			canvasPanel.executeCommand(command);
		});
		
		// 선택한 컴포넌트를 제일 뒤로 보내는 버튼 액션 리스너 추가 (내부 순서 유지)
		sendToBackButton.addActionListener(e -> {
			Command command = new SendToBackCommand();
			canvasPanel.executeCommand(command);
		});
	}
	
	// 선택한 컴포넌트의 x 좌표 변경 메서드
	public void changeX(int x)
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeX(x);
		}
	}
	
	// 선택한 컴포넌트의 y 좌표 변경 메서드
	public void changeY(int y)
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeY(y);
		}
	}
	
	// 선택한 컴포넌트의 width 변경 메서드
	public void changeWidth(int width)
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeWidth(width);
		}
	}
	
	// 선택한 컴포넌트의 height 변경 메서드
	public void changeHeight(int height)
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeHeight(height);
		}
	}
	
	// 선택한 컴포넌트의 red 코드 변경 메서드
	public void changeRedCode(int redCode)
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeRedCode(redCode);
		}
	}
	
	// 선택한 컴포넌트의 green 코드 변경 메서드
	public void changeGreenCode(int greenCode)
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeGreenCode(greenCode);
		}
	}
	
	// 선택한 컴포넌트의 blue 코드 변경 메서드
	public void changeBlueCode(int blueCode)
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeBlueCode(blueCode);
		}
	}

	// 선택한 컴포넌트를 맨 앞으로 이동 메서드
	public void bringToFront()
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.bringToFront();
		}
	}

	// 선택한 컴포넌트를 맨 뒤로 이동 메서드
	public void sendToBack()
	{
		// 리스너 알림
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.sendToBack();
		}
	}
	
	// 컴포넌트 속성 변경 이벤트 리스너 추가 메서드
	public void addChangeComponentPropertyListener(ChangeComponentPropertyListener listener) {
		changeComponentPropertyListeners.add(listener);
	}
	
	// 선택한 컴포넌트 리스트 설정
	@Override
	public void selectComponents(Composite composite) {
		selectedComposite = composite;
	}
	
	// 속성 출력
	@Override
	public void displayProperty()
	{
		// 선택된 컴포넌트가 없다면, 무시
		if(selectedComposite.isEmpty()) {
			return;
		}
		
		// 선택된 컴포넌트가 2개 이상이면, 속성을 출력하지 않음
		if (selectedComposite.size() > 1) {
			clearProperties();
			return;
		}
		
		// 속성 출력
		Component component = selectedComposite.get(0);
		xField.setText(String.valueOf(Math.min(component.getStartX(), component.getEndX())));
		yField.setText(String.valueOf(Math.min(component.getStartY(), component.getEndY())));
		widthField.setText(String.valueOf(component.getWidth()));
		heightField.setText(String.valueOf(component.getHeight()));
		redField.setText(String.valueOf(component.getColor().getRed()));
		greenField.setText(String.valueOf(component.getColor().getGreen()));
		blueField.setText(String.valueOf(component.getColor().getBlue()));
	}
	
	// 속성 출력 중단
	public void clearProperties() {
		xField.setText("");
		yField.setText("");
		widthField.setText("");
		heightField.setText("");
		redField.setText("");
		greenField.setText("");
		blueField.setText("");
	}
	
	// 색상 선택 이벤트 처리 메서드
	@Override
	public void colorSelected(Color color) {
		redField.setText(String.valueOf(color.getRed()));
		greenField.setText(String.valueOf(color.getGreen()));
		blueField.setText(String.valueOf(color.getBlue()));
	}
}