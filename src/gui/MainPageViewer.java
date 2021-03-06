package gui;
import javax.swing.*;

public class MainPageViewer {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    public static void startPageViewer()
    {
        JFrame frame = new JFrame("Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        MainPagePanel panel = MainPagePanel.getInstance();
        panel.setFrame(frame);

        //Display the window.
        frame.setSize(300, 150);
        frame.add(panel.getPanel());
        //frame.pack();
        frame.setVisible(true);
    }
}
