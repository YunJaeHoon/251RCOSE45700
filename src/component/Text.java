package component;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;

public class Text extends Component
{
	private String textContent = "";								// 텍스트 내용
	private boolean dragging = false;								// 드래그 중인지 여부
	private final JTextArea editor = new JTextArea(textContent);	// 편집기

	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2)
	{
		startX = x1;
		startY = y1;
		endX = x2;
		endY = y2;

		Rectangle bounds = getBounds();
		editor.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	@Override
	public void draw(Graphics g)
	{
		if (dragging)
		{
			g.setColor(Color.GRAY);
			g.drawRect(Math.min(startX, endX), Math.min(startY, endY), getWidth(), getHeight());
			return;
		}

		g.setColor(color);
		FontMetrics fm = g.getFontMetrics();

		int x = Math.min(startX, endX);
		int y = Math.min(startY, endY) + fm.getAscent();
		int maxWidth = Math.abs(endX - startX);

		// 텍스트 내용을 '\n'으로 분리하여 각 줄 처리
		String[] lines = textContent.split("\n");

		for (String line : lines)
		{
			// 한 줄이 영역의 폭 안에 들어오면 그대로 그리기
			if (fm.stringWidth(line) <= maxWidth)
			{
				g.drawString(line, x, y);
				y += fm.getHeight();
			}
			else
			{
				// 영역의 폭을 넘어가는 경우 수동 줄바꿈 처리
				int current = 0;

				while (current < line.length())
				{
					int wrapIndex = line.length();

					// wrapIndex를 줄여서 영역의 폭에 맞는 부분찾기
					while (wrapIndex > current && fm.stringWidth(line.substring(current, wrapIndex)) > maxWidth) {
						wrapIndex--;
					}

					// 무한루프 방지를 위해 최소 하나라도 그리기
					if (wrapIndex == current) {
						wrapIndex++;
					}

					g.drawString(line.substring(current, wrapIndex), x, y);
					y += fm.getHeight();
					current = wrapIndex;
				}
			}
		}
	}

	// 텍스트 컴포넌트 자체에서 편집 기능을 제공하는 메서드
	@Override
	public void enableEditing(final JPanel parent)
	{
		Rectangle bounds = getBounds();

		editor.setLineWrap(true);
		editor.setWrapStyleWord(true);
		editor.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		editor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		editor.setMargin(new Insets(0, 0, 0, 0));
		editor.setForeground(color);

		parent.add(editor);
		editor.requestFocusInWindow();

		// 포커스를 잃으면 텍스트 업데이트 후 편집 컴포넌트 제거
		editor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textContent = editor.getText();
				parent.remove(editor);
			}
		});
	}
}