package frame;

import javax.swing.*;

public interface FrameFactory
{
    // 프레임 생성
    JFrame createFrame(String title, int width, int height);

    // 프레임 출력
    void renderFrame(JFrame frame);
}
