package component.memento;

import java.awt.*;

public class ComponentMemento
{
    public final int startX, startY, endX, endY;
    public final Color color;

    // 생성자
    public ComponentMemento(int startX, int startY, int endX, int endY, Color color)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }
}
