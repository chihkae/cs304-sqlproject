package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MainPagePanel {

    private JPanel panel;
    private JFrame mainFrame;
    private static MainPagePanel instance;
    private JTextField textField;
//    private final static String PASSENGER = "Passenger";
//    private final static String EMPLOYEE = "Employee";
//    private final static String[] views = {PASSENGER, EMPLOYEE};
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

//    private void addAButton(String text) {
//        ActionListener actionListener;
//        if(text.equals("Passenger"))
//            actionListener = new PassengerEnterListener();
//        else
//            actionListener = new EmployeeEnterListener();
//        JButton button = new JButton(text);
//        button.addActionListener(actionListener);
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
//        panel.add(button);
//    }

    private void addComponentsToPane() {
        ComboBoxPanel comboBoxPanel = new ComboBoxPanel(panel);
        Object[] buttons = comboBoxPanel.getButtons();

        JButton employeeButton = (JButton)buttons[0];
        JButton passengerButton = (JButton)buttons[1];
        textField = (JTextField)buttons[2];

        employeeButton.addActionListener(new EmployeeEnterListener());
        passengerButton.addActionListener(new PassengerEnterListener());
//        card = new JPanel();
//        card.setLayout(new CardLayout());
//
//        JPanel passengerPanel = new JPanel();
//        JPanel employeePanel = new JPanel();
//
//        // JComboBox
//        JPanel comboPanel = new JPanel();
//        JComboBox viewList = new JComboBox(views);
//        viewList.setEditable(false);
//        viewList.addItemListener(new ComboBoxListener());
//        comboPanel.add(viewList);
//
//        // Employee card
//        JButton login = new JButton("Login");
//        login.addActionListener(new EmployeeEnterListener());
//        employeePanel.add(login);
//
//        //Passenger card
//        JButton loginForPassenger = new JButton("Login");
//        JTextField getID = new JTextField(8);
//
//        passengerPanel.add(getID);
//        passengerPanel.add(loginForPassenger);
//
//        //String s = getID.getText();
////        loginForPassenger.addActionListener(new PassengerEnterListener(s));
////        getID.addActionListener(new PassengerEnterListener(s));
//
//        card.add(passengerPanel, PASSENGER);
//        card.add(employeePanel, EMPLOYEE);
//        panel.add(comboPanel, BorderLayout.PAGE_START);
//        panel.add(card, BorderLayout.CENTER);


//        addAButton("Passenger");
//        addAButton("Employee");
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

//    class ComboBoxListener implements ItemListener{
//        @Override
//        public void itemStateChanged(ItemEvent e) {
//            CardLayout cl = (CardLayout)(card.getLayout());
//            cl.show(card, (String)e.getItem());
//        }
//    }
}
