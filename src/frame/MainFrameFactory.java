package frame;

import javax.swing.*;

public class MainFrameFactory implements FrameFactory
{
    // 싱글톤
    private MainFrameFactory() {}
    private static class SingleInstanceHolder { private static final MainFrameFactory INSTANCE = new MainFrameFactory(); }
    public static MainFrameFactory getInstance() { return SingleInstanceHolder.INSTANCE; }

    @Override
    public JFrame createFrame(String title, int width, int height)
    {
        // 프레임 생성
        MainFrame frame = new MainFrame();

        // 프레임 설정
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    @Override
    public void renderFrame(JFrame frame) {
        frame.setVisible(true);
    }
}
