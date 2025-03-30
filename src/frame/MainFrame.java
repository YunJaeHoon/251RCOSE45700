package frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    public MainFrame()
    {
        setTitle("그림을 그려보아요~");    // 프레임 제목
        setSize(500, 300);              // 프레임 크기

        // 종료 버튼 동작 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();   // 프레임에서 컨텐츠 펜 받아오기
        contentPane.setLayout(null);                // AbsoluteLayout 사용

        setVisible(true);   // 화면에 프레임 출력
    }
}
