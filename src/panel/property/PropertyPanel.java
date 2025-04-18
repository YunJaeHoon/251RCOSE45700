package panel.property;

import panel.canvas.CanvasPanel;
import tool.Component;
import tool.Properties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private CanvasPanel canvasPanel;  // CanvasPanel 참조

    // z-order 변경 버튼 (다중 선택 지원)
    private final JButton bringToFrontButton = new JButton("맨 앞으로");
    private final JButton sendToBackButton = new JButton("맨 뒤로");

    public PropertyPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        // 첫 번째 행: x, y, w, h
        gbc.gridy = row;
        gbc.gridx = 0;
        add(new JLabel("x:"), gbc);
        gbc.gridx = 1;
        add(xField, gbc);
        gbc.gridx = 2;
        add(new JLabel("y:"), gbc);
        gbc.gridx = 3;
        add(yField, gbc);
        gbc.gridx = 4;
        add(new JLabel("w:"), gbc);
        gbc.gridx = 5;
        add(widthField, gbc);
        gbc.gridx = 6;
        add(new JLabel("h:"), gbc);
        gbc.gridx = 7;
        add(heightField, gbc);

        // 두 번째 행: R, G, B
        row++;
        gbc.gridy = row;
        gbc.gridx = 0;
        add(new JLabel("R:"), gbc);
        gbc.gridx = 1;
        add(redField, gbc);
        gbc.gridx = 2;
        add(new JLabel("G:"), gbc);
        gbc.gridx = 3;
        add(greenField, gbc);
        gbc.gridx = 4;
        add(new JLabel("B:"), gbc);
        gbc.gridx = 5;
        add(blueField, gbc);

        // 세 번째 행: z-order 버튼
        row++;
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        add(bringToFrontButton, gbc);
        gbc.gridx = 4;
        gbc.gridwidth = 4;
        add(sendToBackButton, gbc);

        // 빈 패널을 추가하여 남은 공간을 채워 상단 정렬
        row++;
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JPanel(), gbc);

        // 각 필드에 엔터키 입력 시 액션 리스너 추가
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProperties();
            }
        };
        xField.addActionListener(listener);
        yField.addActionListener(listener);
        widthField.addActionListener(listener);
        heightField.addActionListener(listener);
        redField.addActionListener(listener);
        greenField.addActionListener(listener);
        blueField.addActionListener(listener);

        // 다중 선택된 컴포넌트에 대해 z-order 변경 수행
        bringToFrontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedComponents != null && !selectedComponents.isEmpty() && canvasPanel != null) {
                    canvasPanel.bringToFront(selectedComponents);
                    canvasPanel.repaint();
                }
            }
        });

        sendToBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedComponents != null && !selectedComponents.isEmpty() && canvasPanel != null) {
                    canvasPanel.sendToBack(selectedComponents);
                    canvasPanel.repaint();
                }
            }
        });
    }

    public void setCanvasPanel(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;
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