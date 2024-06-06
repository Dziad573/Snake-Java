import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Panel panel = new Panel();
                panel.showFrame();
            }
        });

        //        Panel panel = new Panel();
        //        SwingUtilities.invokeLater(panel::showFrame);
    }
}