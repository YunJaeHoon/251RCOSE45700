package panel.canvas;

import tool.Component;

import java.util.EventListener;
import java.util.List;

public interface ComponentSelectionListener extends EventListener
{
	// 선택한 컴포넌트 리스트 설정
	void selectComponents(List<Component> components);
	
	// 컴포넌트 속성 출력
	void displayProperty();
}
