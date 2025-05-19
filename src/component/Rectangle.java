package component;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Rectangle extends Component
{
	@Override
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(
				Math.min(startX, endX),
				Math.min(startY, endY),
				Math.abs(endX - startX),
				Math.abs(endY - startY)
		);
	}
}
