package panel.color;

import panel.PanelFactory;

public class ColorPanelFactory implements PanelFactory
{
    // 싱글톤
    private ColorPanelFactory() {}
    private static class SingleInstanceHolder { private static final ColorPanelFactory INSTANCE = new ColorPanelFactory(); }
    public static ColorPanelFactory getInstance() { return ColorPanelFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public ColorPanel createPanel() {
        return new ColorPanel();
    }
}
