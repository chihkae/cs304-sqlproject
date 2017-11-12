package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPagePanel {

    private JPanel panel;
    private JFrame mainFrame;
    private static MainPagePanel instance;

    private MainPagePanel(){
        panel = new JPanel();
        addComponentsToPane();
        exitButton();
    }

    public static MainPagePanel getInstance() {
        if(instance == null)
            instance = new MainPagePanel();
        return instance;
    }

    private void exitButton() {
        JButton exit = new JButton("EXIT");
        exit.addActionListener(new ExitListener());
        panel.add(exit);
    }

    private void addAButton(String text) {
        ActionListener actionListener;
        if(text.equals("Passenger"))
            actionListener = new PassengerEnterListener();
        else
            actionListener = new EmployeeEnterListener();
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(button);
    }

    private void addComponentsToPane() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addAButton("Passenger");
        addAButton("Employee");
    }

    public JPanel getPanel(){
        return panel;
    }

    public void setFrame(JFrame frame){
        this.mainFrame = frame;
    }

    public void setInvisible(boolean set){
        mainFrame.setVisible(set);
    }

    class PassengerEnterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            PassengerViewer passengerViewer = PassengerViewer.getInstance();
            mainFrame.setVisible(false);
            passengerViewer.viewThisFrame();
        }
    }

    class EmployeeEnterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            EmployeeViewer employeeViewer = EmployeeViewer.getInstance();
            mainFrame.setVisible(false);
            employeeViewer.viewThisFrame();
        }
    }

    class ExitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
