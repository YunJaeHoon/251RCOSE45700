package panel.property;

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
	private List<Component> selectedComponents = new ArrayList<>();
	
	private final JTextField xField = new JTextField(5);
	private final JTextField yField = new JTextField(5);
	private final JTextField widthField = new JTextField(5);
	private final JTextField heightField = new JTextField(5);
	private final JTextField redField = new JTextField(3);
	private final JTextField greenField = new JTextField(3);
	private final JTextField blueField = new JTextField(3);
	
	// 컴포넌트 순서 변경 이벤트 리스너 리스트
	private final List<ChangeComponentPropertyListener> changeComponentPropertyListeners = new ArrayList<>();
	
	// 생성자
	public PropertyPanel()
	{
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		
		// 첫 번째 행: x, y, w, h
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
		
		// 두 번째 행: R, G, B
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
		
		// 세 번째 행: z-order 버튼
		gbc.gridy = 2;
		
		gbc.gridx = 0;
		gbc.gridwidth = 4;
		JButton bringToFrontButton = new JButton("맨 앞으로");
		add(bringToFrontButton, gbc);
		
		gbc.gridx = 4;
		gbc.gridwidth = 4;
		JButton sendToBackButton = new JButton("맨 뒤로");
		add(sendToBackButton, gbc);
		
		// 빈 패널을 추가하여 남은 공간을 채워 상단 정렬
		gbc.gridy = 3;
		
		gbc.gridx = 0;
		gbc.gridwidth = 8;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(new JPanel(), gbc);
		
		// 각 필드에 엔터키 입력 시 액션 리스너 추가
		xField.addActionListener(e -> changeX());
		yField.addActionListener(e -> changeY());
		widthField.addActionListener(e -> changeWidth());
		heightField.addActionListener(e -> changeHeight());
		redField.addActionListener(e -> changeRedCode());
		greenField.addActionListener(e -> changeGreenCode());
		blueField.addActionListener(e -> changeBlueCode());
		
		// 선택한 컴포넌트를 제일 앞으로 보내는 버튼 액션 리스너 추가 (내부 순서 유지)
		bringToFrontButton.addActionListener(e -> {
			for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
				listener.bringToFront();
			}
		});
		
		// 선택한 컴포넌트를 제일 뒤로 보내는 버튼 액션 리스너 추가 (내부 순서 유지)
		sendToBackButton.addActionListener(e -> {
			for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
				listener.bringToBack();
			}
		});
	}
	
	// 선택한 컴포넌트의 x 좌표 변경 메서드
	private void changeX()
	{
		// 속성값이 비어있으면 무시
		if(xField.getText().isEmpty()) {
			return;
		}
		
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeX(Integer.parseInt(xField.getText()));
		}
	}
	
	// 선택한 컴포넌트의 y 좌표 변경 메서드
	private void changeY()
	{
		// 속성값이 비어있으면 무시
		if(yField.getText().isEmpty()) {
			return;
		}
		
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeY(Integer.parseInt(yField.getText()));
		}
	}
	
	// 선택한 컴포넌트의 width 변경 메서드
	private void changeWidth()
	{
		// 속성값이 비어있으면 무시
		if(widthField.getText().isEmpty()) {
			return;
		}
		
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeWidth(Integer.parseInt(widthField.getText()));
		}
	}
	
	// 선택한 컴포넌트의 height 변경 메서드
	private void changeHeight()
	{
		// 속성값이 비어있으면 무시
		if(heightField.getText().isEmpty()) {
			return;
		}
		
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeHeight(Integer.parseInt(heightField.getText()));
		}
	}
	
	// 선택한 컴포넌트의 red 코드 메서드
	private void changeRedCode()
	{
		// 속성값이 비어있으면 무시
		if(redField.getText().isEmpty()) {
			return;
		}
		
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeRedCode(Integer.parseInt(redField.getText()));
		}
	}
	
	// 선택한 컴포넌트의 green 코드 메서드
	private void changeGreenCode()
	{
		// 속성값이 비어있으면 무시
		if(greenField.getText().isEmpty()) {
			return;
		}
		
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeGreenCode(Integer.parseInt(greenField.getText()));
		}
	}
	
	// 선택한 컴포넌트의 blue 코드 메서드
	private void changeBlueCode()
	{
		// 속성값이 비어있으면 무시
		if(blueField.getText().isEmpty()) {
			return;
		}
		
		for(ChangeComponentPropertyListener listener : changeComponentPropertyListeners) {
			listener.changeBlueCode(Integer.parseInt(blueField.getText()));
		}
	}
	
	// 컴포넌트 속성 변경 이벤트 리스너 추가 메서드
	public void addChangeComponentPropertyListener(ChangeComponentPropertyListener listener) {
		changeComponentPropertyListeners.add(listener);
	}
	
	// 선택한 컴포넌트 리스트 설정
	@Override
	public void selectComponents(List<Component> components) {
		selectedComponents = components;
		displayProperty();
		System.out.println(selectedComponents);
	}
	
	// 속성 출력
	@Override
	public void displayProperty()
	{
		// 선택된 컴포넌트가 없다면, 무시
		if(selectedComponents.isEmpty()) {
			return;
		}
		
		// 선택된 컴포넌트가 2개 이상이면, 속성을 출력하지 않음
		if (selectedComponents.size() > 1) {
			clearProperties();
			return;
		}
		
		// 속성 출력
		Component component = selectedComponents.getFirst();
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