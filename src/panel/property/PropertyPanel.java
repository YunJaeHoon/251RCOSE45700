package panel.property;

import tool.Component;
import tool.Properties;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PropertyPanel extends JPanel {
    private final JTextField xField = new JTextField(5);
    private final JTextField yField = new JTextField(5);
    private final JTextField widthField = new JTextField(5);
    private final JTextField heightField = new JTextField(5);
    private final JTextField redField = new JTextField(3);
    private final JTextField greenField = new JTextField(3);
    private final JTextField blueField = new JTextField(3);
    private Component selectedComponent;

    public PropertyPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel("x:"));
        add(xField);
        add(new JLabel("y:"));
        add(yField);
        add(new JLabel("w:"));
        add(widthField);
        add(new JLabel("h:"));
        add(heightField);
        add(new JLabel("R:"));
        add(redField);
        add(new JLabel("G:"));
        add(greenField);
        add(new JLabel("B:"));
        add(blueField);

        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateResizable();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateResizable();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateResizable();
            }
        };

        xField.getDocument().addDocumentListener(listener);
        yField.getDocument().addDocumentListener(listener);
        widthField.getDocument().addDocumentListener(listener);
        heightField.getDocument().addDocumentListener(listener);
        redField.getDocument().addDocumentListener(listener);
        greenField.getDocument().addDocumentListener(listener);
        blueField.getDocument().addDocumentListener(listener);
    }

    public void displayProperties(Component component) {
        selectedComponent = component;
        Properties properties = selectedComponent.getProperties();
        xField.setText(String.valueOf(properties.getX()));
        yField.setText(String.valueOf(properties.getY()));
        widthField.setText(String.valueOf(properties.getWidth()));
        heightField.setText(String.valueOf(properties.getHeight()));
        redField.setText(String.valueOf(properties.getColor().getRed()));
        greenField.setText(String.valueOf(properties.getColor().getGreen()));
        blueField.setText(String.valueOf(properties.getColor().getBlue()));
    }

    public void clearProperties() {
        selectedComponent = null;
        xField.setText("");
        yField.setText("");
        widthField.setText("");
        heightField.setText("");
        redField.setText("");
        greenField.setText("");
        blueField.setText("");
    }

    private void updateResizable() {
        if (selectedComponent == null) {
            return;
        }
        try {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            int w = Integer.parseInt(widthField.getText());
            int h = Integer.parseInt(heightField.getText());
            int r = Integer.parseInt(redField.getText());
            int g = Integer.parseInt(greenField.getText());
            int b = Integer.parseInt(blueField.getText());
            selectedComponent.setProperties(x, y, w, h, new Color(r, g, b));
            getParent().repaint();
        } catch (NumberFormatException ignored) {
        }
    }
}