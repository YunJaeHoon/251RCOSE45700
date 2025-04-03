import frame.FrameFactory;
import frame.MainFrameFactory;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        FrameFactory mainFrameFactory = MainFrameFactory.getInstance();

        JFrame mainFrame = mainFrameFactory.createFrame("그림을 그려보아요!", 800, 600);
        mainFrameFactory.renderFrame(mainFrame);
    }
}