package tool;

import component.*;

public enum ToolMode
{
    SELECT(new SelectEventHandler()),
    LINE(new LineEventHandler()),
    RECTANGLE(new RectangleEventHandler()),
    ELLIPSE(new EllipseEventHandler()),
    TEXT(new TextEventHandler()),;

    public final ToolEventHandler toolEventHandler;

    ToolMode(ToolEventHandler toolEventHandler) {
        this.toolEventHandler = toolEventHandler;
    }

    public ToolEventHandler getToolEventHandler() {
        return toolEventHandler;
    }
}