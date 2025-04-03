package frame;

import panel.canvas.CanvasPanel;
import panel.canvas.CanvasPanelFactory;
import panel.toolbar.ToolbarPanelFactory;
import tool.ToolMode;
import tool.ToolSelectionListener;
import panel.toolbar.ToolbarPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ToolSelectionListener
{
    CanvasPanel canvasPanel;    // 캔버스 패널 (임시 JPanel, 후에 수정해야 함!!!)
    ToolbarPanel toolbarPanel;  // 도구 선택창 패널
    ToolMode currentToolMode;   // 현재 도구
    JLabel currentToolLabel;    // 현재 도구를 표시하는 레이블

    MainFrame()
    {
        // 컨텐츠 펜 설정
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // 현재 선택된 도구 초기화
        currentToolMode = ToolMode.SELECT;

        // 캔버스 패널 생성
        // TODO: 나중에 실제 그림을 그릴 Custom Canvas Panel 클래스로 교체 필요
        canvasPanel = CanvasPanelFactory.getInstance().createPanel();
        canvasPanel.setBackground(Color.WHITE);

        // 캔버스 패널에 현재 도구를 표시하는 레이블 추가
        currentToolLabel = new JLabel("Current Tool: " + currentToolMode);
        canvasPanel.add(currentToolLabel);

        // 툴바 패널 생성
        toolbarPanel = ToolbarPanelFactory.getInstance().createPanel();
        toolbarPanel.addToolSelectionListener(this);    // MainFrame을 도구 선택 이벤트 리스너로 등록

        // 컨텐츠 펜에 패널 추가
        contentPane.add(new JScrollPane(canvasPanel), BorderLayout.CENTER);
        contentPane.add(toolbarPanel, BorderLayout.WEST);

        // 속성 패널 등 다른 UI 요소 생성 및 추가 (필요 시)
        // JPanel propertiesPanel = new JPanel();
        // propertiesPanel.setPreferredSize(new Dimension(200, 0)); // 너비 지정
        // propertiesPanel.setBackground(Color.LIGHT_GRAY);
        // contentPane.add(propertiesPanel, BorderLayout.EAST);     // 동쪽에 추가
    }

    // 도구 선택 이벤트 발생 시의 동작
    @Override
    public void toolSelected(ToolMode selectedTool)
    {
        if(currentToolMode == selectedTool)
            return;

        currentToolMode = selectedTool;
        currentToolLabel.setText("Current Tool: " + currentToolMode);

        // TODO: 캔버스나 다른 컴포넌트에 변경 사항 알리기
        // 예: 캔버스의 상태 업데이트, 커서 변경 등
        // canvasPanel.setCurrentTool(selectedTool); // CanvasPanel에 메소드가 있다면
    }
}