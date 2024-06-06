import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Panel {
    public JFrame frame;
    private JPanel startPanel;
    private GamePanel gamePanel;
    private OptionsPanel optionsPanel;
    public final static int WIDTH = 1024;
    public final static int HEIGHT = 768;
    PlaySound playSound = new PlaySound();

    public void showFrame() {
        frame = new JFrame();
        frame.setTitle("Title");

        startPanel = createStartPanel();
        optionsPanel = createOptionsPanel();
        gamePanel = createGamePanel();
        gamePanel.setOptionsPanel(optionsPanel);


        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/img/snake-loading-screen2.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        BufferedImage image2 = null;
        try {
            image2 = ImageIO.read(new File("src/img/snake-loading-screen.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        LoadingScreen imagePanel = new LoadingScreen(image);
        LoadingScreen imagePanel2 = new LoadingScreen(image2);

        frame.getContentPane().add(imagePanel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        animateImageFadeIn(imagePanel, imagePanel2);
    }

    private void animateImageFadeIn(LoadingScreen imagePanel, LoadingScreen imagePanel2) {
        playSound.playSound("src/sounds/intro.wav");
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            private float opacity = 0.0f;
            private final float increment = 0.01f;
            private int phase = 0;
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (phase) {
                    case 0: // Fade in
                        opacity += increment;
                        if (opacity > 1.0f) {
                            opacity = 1.0f;
                            phase = 1;
                            count = 0;

                        }
                        imagePanel.setOpacity(opacity);
                        break;
                    case 1:
                        count++;
                        if (count >= 100) {
                            phase = 2;
                        }
                        break;
                    case 2: // Fade out
                        opacity -= increment;
                        if (opacity < 0.0f) {
                            opacity = 0.0f;
                            phase = 3;
                            frame.getContentPane().remove(imagePanel);
                            frame.getContentPane().add(imagePanel2);
                            frame.revalidate();
                            frame.repaint();
                        }
                        imagePanel.setOpacity(opacity);
                        break;
                    case 3: // Fade in
                        opacity += increment;
                        if (opacity > 1.0f) {
                            opacity = 1.0f;
                            phase = 4;
                            count = 0;
                            playSound.playSound("src/sounds/music.wav");
                        }
                        imagePanel2.setOpacity(opacity);
                        break;
                    case 4:
                        count++;
                        if (count >= 100) {
                            phase = 5;
                        }
                        break;
                    case 5: // Fade out
                        opacity -= increment;
                        if (opacity < 0.0f) {
                            opacity = 0.0f;
                            timer.stop();
                            frame.getContentPane().remove(imagePanel2);
                            frame.getContentPane().add(startPanel);
                            frame.revalidate();
                            frame.repaint();
                        }
                        imagePanel2.setOpacity(opacity);
                        break;

                }
            }
        });
        timer.start();
    }

    public JPanel createStartPanel() {
        ImageIcon backgroundIcon = new ImageIcon("src/img/ezgif-7-64259c6135.gif");
        BackgroundPanel panel = new BackgroundPanel(backgroundIcon);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, (HEIGHT / 2 - 40)));



        JButton playButton = new JButton("NEW GAME");
        playButton.addActionListener(e -> {
            frame.getContentPane().remove(startPanel);
            frame.getContentPane().remove(optionsPanel);
            frame.getContentPane().add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.activateGamePanel();
        });

        JButton continueButton = new JButton("CONTINUE");
        continueButton.addActionListener(e -> {
            frame.getContentPane().remove(startPanel);
            frame.getContentPane().remove(optionsPanel);
            frame.getContentPane().add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.activateContinueGamePanel();
        });

        JButton optionsButton = new JButton("OPTIONS");
        optionsButton.addActionListener(e -> {
            frame.getContentPane().remove(startPanel);
            frame.getContentPane().remove(gamePanel);
            frame.getContentPane().add(optionsPanel);
            frame.revalidate();
            frame.repaint();
            optionsPanel.requestFocusInWindow();
        });

        panel.add(playButton);
        panel.add(continueButton);
        panel.add(optionsButton);
        return panel;
    }

    private GamePanel createGamePanel() {
        GamePanel panel = new GamePanel(frame, this);
        panel.setBackground(Color.BLACK);
        panel.setFocusable(true);
        return panel;
    }

    private OptionsPanel createOptionsPanel() {
        OptionsPanel optionsPanel = new OptionsPanel(frame, this);
        optionsPanel.setBackground(Color.BLACK);
        optionsPanel.setFocusable(true);
        return optionsPanel;
    }

    public JPanel getStartPanel() {
        return startPanel;
    }
}



