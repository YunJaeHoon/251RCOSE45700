package component;

import java.awt.event.MouseEvent;

public class RectangleFactory implements ComponentFactory
{
    // 싱글톤
    private RectangleFactory() {}
    private static class SingleInstanceHolder { private static final RectangleFactory INSTANCE = new RectangleFactory(); }
    public static RectangleFactory getInstance() { return RectangleFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public Rectangle createComponent(MouseEvent e) {
        return new Rectangle();
    }
}
