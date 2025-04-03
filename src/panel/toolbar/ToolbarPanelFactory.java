package panel.toolbar;

import panel.PanelFactory;

import javax.swing.*;

public class ToolbarPanelFactory implements PanelFactory<ToolbarPanel>
{
    @Override
    public ToolbarPanel createPanel() {
        return new ToolbarPanel();
    }
}
