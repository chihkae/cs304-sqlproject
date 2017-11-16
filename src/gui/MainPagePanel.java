package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPagePanel {

    private JPanel panel;
    private JFrame mainFrame;
    private static MainPagePanel instance;
    private JTextField textField;
    private int passengerID;
    private JPanel card;

    private MainPagePanel(){
        panel = new JPanel(new BorderLayout());
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
        JPanel exitPanel = new JPanel();
        exitPanel.add(exit);
        panel.add(exitPanel, BorderLayout.SOUTH);
    }

    private void addComponentsToPane() {
        ComboBoxPanel comboBoxPanel = new ComboBoxPanel(panel);
        Object[] buttons = comboBoxPanel.getButtons();

        JButton employeeButton = (JButton)buttons[0];
        JButton passengerButton = (JButton)buttons[1];
        textField = (JTextField)buttons[2];

        employeeButton.addActionListener(new EmployeeEnterListener());
        passengerButton.addActionListener(new PassengerEnterListener());
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
            try{
                String id = textField.getText();
                passengerID = Integer.parseInt(id);

                PassengerViewer passengerViewer = PassengerViewer.getInstance(passengerID);
                mainFrame.setVisible(false);
                passengerViewer.viewThisFrame();
            }catch(Exception exception){
                JOptionPane.showMessageDialog(null, "Passenger ID Invalid", "Error Code 100", JOptionPane.ERROR_MESSAGE);
            }
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
