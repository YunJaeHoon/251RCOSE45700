package panel.canvas;

import component.Composite;

import java.util.EventListener;

public interface ComponentSelectionListener extends EventListener
{
	// 선택한 컴포넌트 리스트 설정
	void selectComponents(Composite composite);
	
	// 컴포넌트 속성 출력
	void displayProperty();
}
