import javax.swing.*;
import java.awt.*;

public class RadioIcon implements Icon {
    private final Color color;
    private final boolean selected;
    private final int size;

    public RadioIcon(Color color, boolean selected, int size) {
        this.color = color;
        this.selected = selected;
        this.size = size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.drawOval(x, y, size, size);
        if (selected) {
            g.fillOval(x, y, size, size);
        }
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
