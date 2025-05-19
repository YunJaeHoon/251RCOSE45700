package frame;

import panel.canvas.CanvasPanel;
import panel.color.ColorPanel;
import panel.property.PropertyPanel;
import panel.toolbar.ToolbarPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
	CanvasPanel canvasPanel;		// 캔버스 패널
	ToolbarPanel toolbarPanel;		// 도구 선택 패널
	ColorPanel colorPanel;          // 색상 선택 패널
	PropertyPanel propertyPanel;	// 속성 패널

	// 싱글톤
	public static MainFrame getInstance() { return MainFrame.SingleInstanceHolder.INSTANCE; }
	private static class SingleInstanceHolder { private static final MainFrame INSTANCE = new MainFrame(); }

	// 생성자
	private MainFrame()
	{
		/// 기본 설정

		// 메인 프레임 설정
		setTitle("그림을 그려보아요!");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 컨텐츠 펜 설정
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		/// 패널 생성

		canvasPanel = CanvasPanel.getInstance();		// 캔버스 패널
		propertyPanel = PropertyPanel.getInstance();	// 속성 패널
		toolbarPanel = ToolbarPanel.getInstance();		// 툴바 패널
		colorPanel = ColorPanel.getInstance();			// 컬러 패널

		/// 이벤트 리스너 등록

		// 도구 선택 이벤트
		toolbarPanel.addToolSelectionListener(canvasPanel);

		// 색상 선택 이벤트
		colorPanel.addColorSelectionListener(canvasPanel);
		colorPanel.addColorSelectionListener(propertyPanel);

		// 컴포넌트 속성 변경 이벤트
		propertyPanel.addChangeComponentPropertyListener(canvasPanel);

		// 컴포넌트 선택 이벤트
		canvasPanel.addComponentSelectionListener(propertyPanel);

		/// 패널 추가
		
		// 컨텐츠 펜에 패널 추가
		contentPane.add(new JScrollPane(canvasPanel), BorderLayout.CENTER);
		contentPane.add(toolbarPanel, BorderLayout.WEST);
		contentPane.add(colorPanel, BorderLayout.SOUTH);
		contentPane.add(propertyPanel, BorderLayout.EAST);
	}
}