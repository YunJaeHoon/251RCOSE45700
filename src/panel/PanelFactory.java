package panel;

import javax.swing.*;

public interface PanelFactory<T extends JPanel>
{
    // 패널 생성
    T createPanel();
}
