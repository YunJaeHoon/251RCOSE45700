package panel.canvas;

import panel.PanelFactory;

public class CanvasPanelFactory implements PanelFactory<CanvasPanel>
{
    // 싱글톤
    private CanvasPanelFactory() {}
    private static class SingleInstanceHolder { private static final CanvasPanelFactory INSTANCE = new CanvasPanelFactory(); }
    public static CanvasPanelFactory getInstance() { return CanvasPanelFactory.SingleInstanceHolder.INSTANCE; }

    @Override
    public CanvasPanel createPanel() {
        return new CanvasPanel();
    }
}
