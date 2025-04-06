package frame;

import panel.canvas.CanvasPanel;
import panel.canvas.CanvasPanelFactory;
import panel.toolbar.ToolbarPanelFactory;
import panel.toolbar.ToolbarPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    CanvasPanel canvasPanel;    // 캔버스 패널
    ToolbarPanel toolbarPanel;  // 도구 선택창 패널

    MainFrame()
    {
        // 컨텐츠 펜 설정
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // 캔버스 패널 생성
        canvasPanel = CanvasPanelFactory.getInstance().createPanel();

        // 툴바 패널 생성
        toolbarPanel = ToolbarPanelFactory.getInstance().createPanel();
        toolbarPanel.addToolSelectionListener(canvasPanel);

        // 컨텐츠 펜에 패널 추가
        contentPane.add(new JScrollPane(canvasPanel), BorderLayout.CENTER);
        contentPane.add(toolbarPanel, BorderLayout.WEST);
    }
}