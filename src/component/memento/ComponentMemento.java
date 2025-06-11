package component.memento;

import java.awt.*;

public class ComponentMemento
{
    public final int startX, startY, width, height;
    public final Color color;

    // 생성자
    public ComponentMemento(int startX, int startY, int width, int height, Color color)
    {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
