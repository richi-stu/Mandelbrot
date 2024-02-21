import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class View {
    private Panel panel = new Panel();
    private int width;
    private int height;
    BufferedImage image;
    private Presenter presenter;

    View(Presenter presenter) {
        this.presenter = presenter;
    }

    public void UI(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(this.width, this.height, 1);
        JFrame f = new JFrame();
        f.add(this.panel, "Center");
        f.setSize(this.width, this.height);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void update(Color[][] c) {
        for(int y = 0; y < this.height; ++y) {
            for(int x = 0; x < this.width; ++x) {
                if (c[x][y] != null) {
                    this.image.setRGB(x, y, c[x][y].getRGB());
                }
            }
        }

        this.panel.repaint();
    }

    class Panel extends JPanel {
        Panel() {
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(View.this.image, 0, 0, (ImageObserver)null);
        }
    }
}