package frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ToolSelectionListener
{
    private final JPanel canvasPanel;                   // 캔버스 패널 멤버 변수 (임시 JPanel)
    private ToolMode currentToolMode = ToolMode.SELECT; // 현재 선택된 도구 저장

    public MainFrame()
    {
        // 프레임 제목
        setTitle("그림을 그려보아요~");

        // 프레임 크기
        setSize(800, 600);

        // 종료 버튼 동작 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();   // 프레임에서 컨텐츠 펜 받아오기
        contentPane.setLayout(new BorderLayout());  // BorderLayout 사용

        // 1. 툴바 패널 생성 및 추가
        // 툴바 패널 멤버 변수
        ToolbarPanel toolbarPanel = new ToolbarPanel();     // ToolbarPanel 인스턴스 생성
        toolbarPanel.addToolSelectionListener(this);        // MainFrame을 리스너로 등록
        contentPane.add(toolbarPanel, BorderLayout.WEST);   // 프레임 서쪽에 추가

        // 2. 캔버스 패널 생성 및 추가 (임시)
        // TODO: 나중에 실제 그림을 그릴 Custom Canvas Panel 클래스로 교체 필요
        canvasPanel = new JPanel();
        canvasPanel.setBackground(Color.WHITE);

        // 임시로 현재 도구를 표시하는 레이블 추가
        JLabel statusLabel = new JLabel("Current Tool: " + currentToolMode);
        canvasPanel.add(statusLabel); // 레이블을 캔버스에 추가
        contentPane.add(new JScrollPane(canvasPanel), BorderLayout.CENTER); // 중앙에 추가 (스크롤 가능하게)

        // 3. 속성 패널 등 다른 UI 요소 추가 (필요 시)
        // JPanel propertiesPanel = new JPanel();
        // propertiesPanel.setPreferredSize(new Dimension(200, 0)); // 너비 지정
        // propertiesPanel.setBackground(Color.LIGHT_GRAY);
        // contentPane.add(propertiesPanel, BorderLayout.EAST);     // 동쪽에 추가

        // 화면에 프레임 출력
        setVisible(true);
    }

    // ToolSelectionListener 인터페이스 구현 메소드
    @Override
    public void toolSelected(ToolMode selectedTool) {
        this.currentToolMode = selectedTool;
        System.out.println("MainFrame: Tool changed to " + selectedTool);

        // TODO: 캔버스나 다른 컴포넌트에 변경 사항 알리기
        // 예: 캔버스의 상태 업데이트, 커서 변경 등
        // canvasPanel.setCurrentTool(selectedTool); // CanvasPanel에 메소드가 있다면
        // canvasPanel.repaint();

        // 임시로 캔버스 위의 레이블 업데이트
        if (canvasPanel != null && canvasPanel.getComponentCount() > 0 && canvasPanel.getComponent(0) instanceof JLabel) {
            ((JLabel) canvasPanel.getComponent(0)).setText("Current Tool: " + currentToolMode);
        }
    }
}