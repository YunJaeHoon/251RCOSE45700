package panel.canvas;

import panel.PanelFactory;

import javax.swing.*;

public class CanvasPanelFactory implements PanelFactory<CanvasPanel>
{
    @Override
    public CanvasPanel createPanel() {
        return new CanvasPanel();
    }
}
