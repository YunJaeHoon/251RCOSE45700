package frame;

import tool.ToolMode;
import tool.ToolSelectionListener;
import tool.ToolbarPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ToolSelectionListener
{
    JPanel panel;               // 캔버스 패널 (임시 JPanel)
    ToolMode currentToolMode;   // 현재 선택된 도구

    public MainFrame()
    {
        // 캔버스 패널 생성
        // TODO: 나중에 실제 그림을 그릴 Custom Canvas Panel 클래스로 교체 필요
        JPanel panel = new JPanel();

        // 필드 변수 초기화
        this.panel = panel;
        currentToolMode = ToolMode.SELECT;

        // 캔버스 패널 색상
        panel.setBackground(Color.WHITE);

        // 캔버스 패널에 현재 도구를 표시하는 레이블 추가
        JLabel statusLabel = new JLabel("Current Tool: " + this.currentToolMode);
        panel.add(statusLabel);

        // 컨텐츠 펜 불러오기
        Container contentPane = this.getContentPane();

        // 컨텐츠 펜 설정
        contentPane.setLayout(new BorderLayout());

        // 컨텐츠 펜에 패널 추가
        contentPane.add(new JScrollPane(panel), BorderLayout.CENTER);

        // 툴바 패널 생성 및 컨텐츠 펜에 추가
        ToolbarPanel toolbarPanel = new ToolbarPanel();     // ToolbarPanel 인스턴스 생성
        toolbarPanel.addToolSelectionListener(this);        // MainFrame을 리스너로 등록
        contentPane.add(toolbarPanel, BorderLayout.WEST);   // 프레임 서쪽에 추가

        // 3. 속성 패널 등 다른 UI 요소 추가 (필요 시)
        // JPanel propertiesPanel = new JPanel();
        // propertiesPanel.setPreferredSize(new Dimension(200, 0)); // 너비 지정
        // propertiesPanel.setBackground(Color.LIGHT_GRAY);
        // contentPane.add(propertiesPanel, BorderLayout.EAST);     // 동쪽에 추가
    }

    // ToolSelectionListener 인터페이스 구현 메소드
    @Override
    public void toolSelected(ToolMode selectedTool)
    {
        this.currentToolMode = selectedTool;
        System.out.println("MainFrame: Tool changed to " + selectedTool);

        // TODO: 캔버스나 다른 컴포넌트에 변경 사항 알리기
        // 예: 캔버스의 상태 업데이트, 커서 변경 등
        // canvasPanel.setCurrentTool(selectedTool); // CanvasPanel에 메소드가 있다면
        // canvasPanel.repaint();

        // 임시로 캔버스 위의 레이블 업데이트
        if (panel != null && panel.getComponentCount() > 0 && panel.getComponent(0) instanceof JLabel) {
            ((JLabel) panel.getComponent(0)).setText("Current Tool: " + currentToolMode);
        }
    }
}