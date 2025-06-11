package component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Composite extends Component{
    private final List<Component> children = new ArrayList<>();

    public void add(Component component) {
        children.add(component);
    }
    public void add(int idx,Component component) {
        children.add(idx, component);
    }
    public void remove(Component component) {
        children.remove(component);
    }
    public void clear() {
        children.clear();
    }
    public List<Component> getChildren() {
        return children;
    }
    public boolean isEmpty() {
        return children.isEmpty();
    }
    public int size() {
        return children.size();
    }
    public Component get(int idx) {
        return children.get(idx);
    }
    public boolean contains(Component component) {
        return children.contains(component);
    }
    @Override
    public void draw(Graphics g) {
        for (Component child : children) {
            child.draw(g);
        }
    }
    @Override
    public void changeX(int x) {
        for (Component component : children) {
            component.changeX(x);
        }
    }
    @Override
    public void changeY(int y) {
        for (Component component : children) {
            component.changeY(y);
        }
    }
    @Override
    public void setWidth(int width) {
        for (Component component : children) {
            component.setWidth(width);
        }
    }
    @Override
    public void setHeight(int height) {
        for (Component component : children) {
            component.setHeight(height);
        }
    }
    @Override
    public void setRedCode(int redCode) {
        for (Component component : children) {
            component.setRedCode(redCode);
        }
    }
    @Override
    public void setGreenCode(int greenCode) {
        for (Component component : children) {
            component.setGreenCode(greenCode);
        }
    }
    @Override
    public void setBlueCode(int blueCode) {
        for (Component component : children) {
            component.setBlueCode(blueCode);
        }
    }
    @Override
    public void setColor(Color color) {
        for (Component component : children) {
            component.setColor(color);
        }
    }
    @Override
    public void drawBounds(Graphics g) {
        for (Component component : children) {
            component.drawBounds(g);
        }
    }
}
