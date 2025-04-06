package tool;

import java.awt.event.MouseEvent;

public class SelectFactory implements ComponentFactory
{
    // 싱글톤
    private SelectFactory() {}
    private static class SingleInstanceHolder { private static final SelectFactory INSTANCE = new SelectFactory(); }
    public static SelectFactory getInstance() { return SelectFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public Select createComponent(MouseEvent e) {
        return null;
    }
}
