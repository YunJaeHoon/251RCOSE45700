package tool;

public enum ToolMode
{
    SELECT(SelectFactory.getInstance()),
    LINE(LineFactory.getInstance()),
    RECTANGLE(RectangleFactory.getInstance()),
    ELLIPSE(EllipseFactory.getInstance()),
    TEXT(TextFactory.getInstance()),;

    public final ComponentFactory componentFactory;

    ToolMode(ComponentFactory componentFactory) {
        this.componentFactory = componentFactory;
    }

    public ComponentFactory getComponentFactory() {
        return componentFactory;
    }
}