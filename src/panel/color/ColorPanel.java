package panel.color;

import panel.toolbar.ToolSelectionListener;
import tool.ToolMode;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColorPanel  extends JPanel
{
    private Color currentColor = Color.BLACK;                   // 현재 색깔
    private final ButtonGroup buttonGroup = new ButtonGroup();  // 색상 단일 선택을 위한 버튼 그룹

    // 색상 선택 이벤트 리스너 리스트
    private final List<ColorSelectionListener> colorSelectionListeners = new ArrayList<>();

    // 생성자
    ColorPanel()
    {
        setLayout(new FlowLayout());

        // 버튼 생성
        JToggleButton initialButton = createColorButton(Color.BLACK);
        initialButton.setSelected(true);
        initialButton.setPreferredSize(new Dimension(40, 40));

        createColorButton(Color.RED);
        createColorButton(Color.ORANGE);
        createColorButton(Color.YELLOW);
        createColorButton(Color.GREEN);
        createColorButton(Color.BLUE);
        createColorButton(Color.MAGENTA);
    }

    // 버튼 생성 메서드
    private JToggleButton createColorButton(Color selectedColor)
    {
        // 버튼 생성
        JToggleButton button = new JToggleButton();

        // 버튼 설정
        button.setBackground(selectedColor);
        button.setPreferredSize(new Dimension(30, 30));

        // 버튼 그룹에 버튼 추가 (버튼 단일 선택)
        buttonGroup.add(button);

        // 버튼이 눌렸을 때의 기본 스타일 제거
        button.setUI(new BasicButtonUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                if (c.isOpaque()) {
                    g.setColor(c.getBackground());
                    g.fillRect(0, 0, c.getWidth(), c.getHeight());
                }
                paint(g, c);
            }
        });

        // 액션 리스너: 버튼 클릭 시 모드 변경 및 리스너 알림
        button.addActionListener(e -> {
            if (button.isSelected() && currentColor != selectedColor)
            {
                // 현재 선택된 색상 변경
                currentColor = selectedColor;

                // 선택된 색상 버튼 크기 키우기
                button.setPreferredSize(new Dimension(40, 40));
                button.revalidate();

                // 리스너에게 색상 선택 이벤트 알림
                notifyToolSelection(selectedColor);

                // 다른 버튼의 크기는 원래대로 되돌리기
                for (AbstractButton otherButton : Collections.list(buttonGroup.getElements())) {
                    if (otherButton != button) {
                        otherButton.setPreferredSize(new Dimension(30, 30));
                        otherButton.revalidate();
                    }
                }
            }
        });

        // 버튼 추가
        this.add(button);

        return button;
    }

    // 색상 선택 이벤트 리스너 추가 메서드
    public void addToolSelectionListener(ColorSelectionListener listener) {
        colorSelectionListeners.add(listener);
        listener.colorSelected(currentColor);
    }

    // 색상 선택 이벤트 리스너 제거 메서드
    public void removeToolSelectionListener(ColorSelectionListener listener) {
        colorSelectionListeners.remove(listener);
    }

    // 등록된 모든 리스너에게 색상 선택 이벤트 알림
    private void notifyToolSelection(Color selectedColor) {
        for (ColorSelectionListener listener : colorSelectionListeners) {
            listener.colorSelected(selectedColor);
        }
    }
}
