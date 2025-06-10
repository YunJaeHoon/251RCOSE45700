package command;

import panel.color.ColorPanel;

import java.awt.*;

public class SelectColorCommand implements Command
{
    // 색상 패널
    private final ColorPanel colorPanel = ColorPanel.getInstance();

    // 색상
    private final Color color;

    // 생성자
    public SelectColorCommand(Color color) {
        this.color = color;
    }

    // 색상 변경
    @Override
    public void execute() {
        colorPanel.setCurrentColor(color);
    }
}
