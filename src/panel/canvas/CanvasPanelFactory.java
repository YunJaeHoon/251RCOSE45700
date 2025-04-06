package panel.canvas;

import panel.PanelFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasPanelFactory implements PanelFactory
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
