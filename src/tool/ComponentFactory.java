package tool;

import java.awt.event.MouseEvent;

public interface ComponentFactory
{
    // 컴포넌트 생성
    Component createComponent(MouseEvent e);
}
