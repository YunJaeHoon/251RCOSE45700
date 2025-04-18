package panel.property;

import tool.Component;
import tool.Properties;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class PropertyPanel extends JPanel {
    private final JTextField xField = new JTextField(5);
    private final JTextField yField = new JTextField(5);
    private final JTextField widthField = new JTextField(5);
    private final JTextField heightField = new JTextField(5);
    private final JTextField redField = new JTextField(3);
    private final JTextField greenField = new JTextField(3);
    private final JTextField blueField = new JTextField(3);
    private List<Component> selectedComponents;

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
                updateProperties();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateProperties();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateProperties();
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

    public void displayProperties(List<Component> components) {
        selectedComponents = components;
        if (selectedComponents.size() > 1) {
            clearProperties();
            selectedComponents = components;
        }
        else {
        Properties properties = selectedComponents.get(0).getProperties();
        xField.setText(String.valueOf(properties.getX()));
        yField.setText(String.valueOf(properties.getY()));
        widthField.setText(String.valueOf(properties.getWidth()));
        heightField.setText(String.valueOf(properties.getHeight()));
        redField.setText(String.valueOf(properties.getColor().getRed()));
        greenField.setText(String.valueOf(properties.getColor().getGreen()));
        blueField.setText(String.valueOf(properties.getColor().getBlue()));
        }
    }

    public void clearProperties() {
        selectedComponents = null;
        xField.setText("");
        yField.setText("");
        widthField.setText("");
        heightField.setText("");
        redField.setText("");
        greenField.setText("");
        blueField.setText("");
    }

    private void updateProperties() {
        if (selectedComponents == null || selectedComponents.isEmpty()) {
            return;
        }
        for (Component comp : selectedComponents) {
            // 기존 값들을 가져옵니다.
            Properties p = comp.getProperties();
            int x = p.getX();
            int y = p.getY();
            int w = p.getWidth();
            int h = p.getHeight();
            int r = p.getColor().getRed();
            int g = p.getColor().getGreen();
            int b = p.getColor().getBlue();

            try {
                if (!xField.getText().isEmpty()) {
                    x = Integer.parseInt(xField.getText());
                }
                if (!yField.getText().isEmpty()) {
                    y = Integer.parseInt(yField.getText());
                }
                if (!widthField.getText().isEmpty()) {
                    w = Integer.parseInt(widthField.getText());
                }
                if (!heightField.getText().isEmpty()) {
                    h = Integer.parseInt(heightField.getText());
                }
                if (!redField.getText().isEmpty()) {
                    r = Integer.parseInt(redField.getText());
                }
                if (!greenField.getText().isEmpty()) {
                    g = Integer.parseInt(greenField.getText());
                }
                if (!blueField.getText().isEmpty()) {
                    b = Integer.parseInt(blueField.getText());
                }
                comp.setProperties(x, y, w, h, new Color(r, g, b));
                getParent().repaint();
            } catch (NumberFormatException ignored) {
                // 필드에 잘못된 값이 입력된 경우 예외 처리
            }
        }
    }
}