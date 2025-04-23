package panel.property;

import java.util.EventListener;

public interface ChangeComponentPropertyListener extends EventListener
{
	// 선택한 컴포넌트의 x 좌표 변경
	void changeX(int x);
	
	// 선택한 컴포넌트의 y 좌표 변경
	void changeY(int y);
	
	// 선택한 컴포넌트의 width 변경
	void changeWidth(int width);
	
	// 선택한 컴포넌트의 height 변경
	void changeHeight(int height);
	
	// 선택한 컴포넌트의 red 코드 변경
	void changeRedCode(int redCode);
	
	// 선택한 컴포넌트의 green 코드 변경
	void changeGreenCode(int greenCode);
	
	// 선택한 컴포넌트의 blue 코드 변경
	void changeBlueCode(int blueCode);
	
	// 선택한 컴포넌트를 제일 앞으로
	void bringToFront();
	
	// 선택한 컴포넌트를 제일 뒤로
	void bringToBack();
}
