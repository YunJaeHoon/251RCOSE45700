package tool;

import java.awt.event.MouseEvent;

public class EllipseFactory implements ComponentFactory
{
    // 싱글톤
    private EllipseFactory() {}
    private static class SingleInstanceHolder { private static final EllipseFactory INSTANCE = new EllipseFactory(); }
    public static EllipseFactory getInstance() { return EllipseFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public Ellipse createComponent(MouseEvent e) {
        return new Ellipse();
    }
}
