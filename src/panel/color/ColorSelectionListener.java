package panel.color;

import java.awt.*;
import java.util.EventListener;

public interface ColorSelectionListener  extends EventListener {
    void colorSelected(Color color);
}
