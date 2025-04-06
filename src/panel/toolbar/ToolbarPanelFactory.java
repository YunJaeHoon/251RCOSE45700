package panel.toolbar;

import panel.PanelFactory;
import panel.canvas.CanvasPanelFactory;

import javax.swing.*;

public class ToolbarPanelFactory implements PanelFactory
{
    // 싱글톤
    private ToolbarPanelFactory() {}
    private static class SingleInstanceHolder { private static final ToolbarPanelFactory INSTANCE = new ToolbarPanelFactory(); }
    public static ToolbarPanelFactory getInstance() { return ToolbarPanelFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public ToolbarPanel createPanel() {
        return new ToolbarPanel();
    }
}
