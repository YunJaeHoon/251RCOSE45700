package tool;

import java.awt.event.MouseEvent;

public class TextFactory  implements ComponentFactory
{
    // 싱글톤
    private TextFactory() {}
    private static class SingleInstanceHolder { private static final TextFactory INSTANCE = new TextFactory(); }
    public static TextFactory getInstance() { return TextFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public Text createComponent(MouseEvent e) {
        return new Text();
    }
}
