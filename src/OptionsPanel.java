import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {

    private final JFrame frame;
    private final Panel mainPanel;
    private final JRadioButton wsadOption;
    private final JRadioButton arrowsOption;

    public OptionsPanel(JFrame frame, Panel mainPanel) {
        this.frame = frame;
        this.mainPanel = mainPanel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel label = new JLabel("Options", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);

        JButton backButton = new JButton("BACK");
        backButton.addActionListener(e -> {
            frame.getContentPane().remove(this);
            frame.getContentPane().add(mainPanel.getStartPanel());
            frame.revalidate();
            frame.repaint();
            mainPanel.getStartPanel().requestFocusInWindow();
        });

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);


        Icon radioIcon = new RadioIcon(Color.WHITE, false, 30);
        Icon radioSelectedIcon = new RadioIcon(Color.RED, true, 30);

        ButtonGroup buttonGroup = new ButtonGroup();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        wsadOption = new JRadioButton("WSAD", true);
        wsadOption.setForeground(Color.WHITE);
        wsadOption.setIcon(radioIcon);
        wsadOption.setSelectedIcon(radioSelectedIcon);
        wsadOption.setOpaque(false);
        buttonGroup.add(wsadOption);
        settingsPanel.add(wsadOption, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel imageWSAD = new JLabel();
        imageWSAD.setIcon(scaleImage("src/img/WASD.jpg"));
        imageWSAD.setSize(100,50);
        settingsPanel.add(imageWSAD, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        arrowsOption  = new JRadioButton("ARROWS");
        arrowsOption.setForeground(Color.WHITE);
        arrowsOption.setIcon(radioIcon);
        arrowsOption.setSelectedIcon(radioSelectedIcon);
        arrowsOption.setOpaque(false);
        buttonGroup.add(arrowsOption);
        settingsPanel.add(arrowsOption, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel imageArrows = new JLabel();
        imageArrows.setIcon(scaleImage("src/img/ARR.jpg"));
        settingsPanel.add(imageArrows, gbc);
        add(label, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    private ImageIcon scaleImage(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(320, 260, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public boolean isSetToWSAD() {
        return wsadOption.isSelected();
    }
}
