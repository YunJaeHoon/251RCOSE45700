package panel.property;

import panel.PanelFactory;
import panel.color.ColorPanel;

public class PropertyPanelFactory implements PanelFactory
{
    // 싱글톤
    private PropertyPanelFactory() {}
    private static class SingleInstanceHolder { private static final PropertyPanelFactory INSTANCE = new PropertyPanelFactory(); }
    public static PropertyPanelFactory getInstance() { return SingleInstanceHolder.INSTANCE; }

    @Override
    public PropertyPanel createPanel() {
        return new PropertyPanel();
    }
}
