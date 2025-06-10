package command;

import panel.color.ColorPanel;

import java.awt.*;

public class SelectColorCommand implements Command
{
    private final ColorPanel colorPanel;    // 색상 패널
    private final Color color;              // 현재 색상

    // 생성자
    public SelectColorCommand(ColorPanel colorPanel, Color color) {
        this.colorPanel = colorPanel;
        this.color = color;
    }

    // 색상 변경
    @Override
    public void execute() {
        colorPanel.setCurrentColor(color);
    }
}
