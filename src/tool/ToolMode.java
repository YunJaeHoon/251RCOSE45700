package tool;

import component.*;

public enum ToolMode
{
    SELECT(SelectEventHandler.getInstance()),
    LINE(LineEventHandler.getInstance()),
    RECTANGLE(RectangleEventHandler.getInstance()),
    ELLIPSE(EllipseEventHandler.getInstance()),
    TEXT(TextEventHandler.getInstance());

    public final ToolEventHandler toolEventHandler;

    ToolMode(ToolEventHandler toolEventHandler) {
        this.toolEventHandler = toolEventHandler;
    }

    public ToolEventHandler getToolEventHandler() {
        return toolEventHandler;
    }
}