import frame.FrameFactory;
import frame.MainFrame;
import frame.MainFrameFactory;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        FrameFactory mainFrameFactory = new MainFrameFactory();

        JFrame mainFrame = mainFrameFactory.createFrame("그림을 그려보아요!", 800, 600);
        mainFrameFactory.renderFrame(mainFrame);
    }
}