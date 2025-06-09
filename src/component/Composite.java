package component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Composite extends Component{
    private final List<Component> children = new ArrayList<>();

    public void add(Component component) {
        children.add(component);
    }

    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public void draw(Graphics g) {
        for (Component child : children) {
            child.draw(g);
        }
    }

}
