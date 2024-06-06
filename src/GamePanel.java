import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel{
    private final JFrame frame;
    private Panel mainPanel;
    private static ArrayList<Point> body;
    private final int bodySize = 20;
    private final Timer timer;
    private int directionX = 1;
    private int directionY = 0;
    private int positionXHead = 120;
    private int positionYHead = 120;
    private Point applePosition;
    private int positionXHeadAfterMove;
    private int positionYHeadAfterMove;
    private boolean isActive = false;
    private OptionsPanel optionsPanel;
    PlaySound playSound = new PlaySound();

    public GamePanel(JFrame frame, Panel mainPanel) {
        this.frame = frame;
        this.mainPanel = mainPanel;
        body = new ArrayList<>();
        body.add(new Point(positionXHead, positionYHead));
        body.add(new Point(positionXHead, positionYHead));
        body.add(new Point(positionXHead, positionYHead));
        generateApplePosition();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSnake(directionX, directionY);
            }
        });
    }

    public void setOptionsPanel(OptionsPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }

    private void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (optionsPanel != null && optionsPanel.isSetToWSAD()) {
            switch (keyCode) {
                case KeyEvent.VK_W:
                    directionX = 0;
                    directionY = -1;
                    break;
                case KeyEvent.VK_S:
                    directionX = 0;
                    directionY = 1;
                    break;
                case KeyEvent.VK_A:
                    directionX = -1;
                    directionY = 0;
                    break;
                case KeyEvent.VK_D:
                    directionX = 1;
                    directionY = 0;
                    break;
                case KeyEvent.VK_ESCAPE:
                    exitToMainMenu();
                    break;
            }
        } else {

            switch (keyCode) {
                case KeyEvent.VK_UP:
                    directionX = 0;
                    directionY = -1;
                    break;
                case KeyEvent.VK_DOWN:
                    directionX = 0;
                    directionY = 1;
                    break;
                case KeyEvent.VK_LEFT:
                    directionX = -1;
                    directionY = 0;
                    break;
                case KeyEvent.VK_RIGHT:
                    directionX = 1;
                    directionY = 0;
                    break;
                case KeyEvent.VK_ESCAPE:
                    exitToMainMenu();
                    break;
            }
        }
    }

    private void exitToMainMenu() {
        frame.getContentPane().remove(this);
        frame.getContentPane().add(mainPanel.getStartPanel());
        frame.revalidate();
        frame.repaint();
        isActive = false;
        timer.stop();
        mainPanel.getStartPanel().requestFocusInWindow();
    }

    public void activateGamePanel() {
        resetGame();
        isActive = true;
        requestFocusInWindow();
    }

    public void activateContinueGamePanel() {
        isActive = true;
        timer.start();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSnake(g);
        drawApple(g);
    }

    private void drawSnake(Graphics g) {
        if (body != null) {
            for (Point point : body) {
                g.setColor(Color.GREEN);
                g.fillRect(point.x, point.y, bodySize, bodySize);
            }
            g.setColor(Color.RED);
            g.fillRect(positionXHead, positionYHead, bodySize, bodySize);
        }
    }

    private void moveSnake(int x, int y) {
        for (int i = body.size() - 1; i > 0; i--) {
            body.get(i).setLocation(body.get(i-1));
            repaint();
        }
        positionXHeadAfterMove = positionXHead += x * (bodySize + 5);
        positionYHeadAfterMove = positionYHead += y * (bodySize + 5);
        body.get(0).setLocation(positionXHeadAfterMove, positionYHeadAfterMove);
        collision(positionXHeadAfterMove, positionYHeadAfterMove);
        eat();
    }

    private void collision(int positionXHeadAfterMove, int positionYHeadAfterMove) {
        if (!isActive) return;
        if(
                positionXHeadAfterMove >= frame.getWidth() - bodySize * 2 ||
                        positionXHeadAfterMove <= 5 ||
                        positionYHeadAfterMove >= frame.getHeight() - bodySize * 3 ||
                        positionYHeadAfterMove <= 5
        ){
            playSound.playSound("src/sounds/gameover.wav");
            timer.setDelay(100);
            timer.stop();
            showGameOverDialog();
            return;
        }

        for (int i = 1; i < body.size() - 1; i++) {
            Point segment = body.get(i);
            if (
                    positionXHeadAfterMove == segment.x &&
                            positionYHeadAfterMove == segment.y ||
                            positionXHeadAfterMove == body.get(1).getX() &&
                                    positionYHeadAfterMove == body.get(1).getY()
            ) {
                playSound.playSound("src/sounds/gameover.wav");
                timer.setDelay(100);
                timer.stop();
                showGameOverDialog();
                return;
            }

            int firstBodyElementX = body.get(1).getLocation().x;
            int firstBodyElementY = body.get(1).getLocation().y;
            int secondBodyElementX = body.get(2).getLocation().x;
            int secondBodyElementY = body.get(2).getLocation().y;
            if (
                    positionXHeadAfterMove == firstBodyElementX &&
                            positionYHeadAfterMove == firstBodyElementY ||
                            positionXHeadAfterMove == secondBodyElementX &&
                                    positionYHeadAfterMove == secondBodyElementY
            ){
                playSound.playSound("src/sounds/gameover.wav");
                timer.setDelay(100);
                timer.stop();
                showGameOverDialog();
                return;
            }
        }
    }

    private void showGameOverDialog() {
        int opcja = JOptionPane.showConfirmDialog(
                frame,
                "Wyjechałeś poza krawędź. \n " +
                        "Czy chcesz zagrać jeszcze raz? \n " +
                        " Twój wynik to: " + (body.size() - 3),
                "GAME OVER",
                JOptionPane.YES_NO_OPTION
        );
        if (opcja == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(1);
        }
    }

    private void resetGame() {
        positionXHead = 120;
        positionYHead = 120;
        generateApplePosition();
        body.clear();
        body.add(new Point(positionXHead, positionYHead));
        body.add(new Point(positionXHead, positionYHead));
        body.add(new Point(positionXHead, positionYHead));
        moveSnake(directionX = 1, directionY = 0);
        isActive = true;
        timer.setDelay(100);
        timer.start();
        repaint();
    }

    private void generateApplePosition() {
        int minX = bodySize + 5;
        int maxX = frame.getWidth() - bodySize * 3;
        int minY = bodySize + 5;
        int maxY = frame.getHeight() - bodySize * 4;
        int rangeX = (maxX - minX) / 25;
        int rangeY = (maxY - minY) / 25;
        int randomIndexX = (int) (Math.random() * rangeX);
        int randomIndexY = (int) (Math.random() * rangeY);
        int appleElementPositionX = minX + randomIndexX * 25 - 5;
        int appleElementPositionY = minY + randomIndexY * 25 - 5;
        applePosition = new Point(appleElementPositionX, appleElementPositionY);
    }

    private void drawApple(Graphics g) {
        while (applePosition.x < bodySize + 5 || applePosition.y < bodySize + 5) {
            generateApplePosition();
        }
        g.setColor(Color.ORANGE);
        g.fillRect(applePosition.x, applePosition.y, bodySize, bodySize);
    }

    private void eat() {
        if (positionXHeadAfterMove == applePosition.x && positionYHeadAfterMove == applePosition.y) {
            playSound.playSound("src/sounds/eat.wav");
            generateApplePosition();
            body.add(new Point(positionXHead, positionYHead));
            repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
}
