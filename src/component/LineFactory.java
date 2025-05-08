package component;

import java.awt.event.MouseEvent;

public class LineFactory implements ComponentFactory
{
    // 싱글톤
    private LineFactory() {}
    private static class SingleInstanceHolder { private static final LineFactory INSTANCE = new LineFactory(); }
    public static LineFactory getInstance() { return LineFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public Line createComponent(MouseEvent e) {
        return new Line();
    }
}
