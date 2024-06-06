import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class LoadingScreen extends JPanel {
    private final BufferedImage image;
    private float opacity;

    public LoadingScreen(BufferedImage image) {
        this.image = image;
        this.opacity = 0.0f;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        int x = (getWidth() - image.getWidth()) / 2;
        int y = (getHeight() - image.getHeight()) / 2;
        g2d.drawImage(image, x, y, this);
    }
}
