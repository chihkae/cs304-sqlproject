package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class ComboBoxPanel implements ItemListener {
    private JPanel panel;
    private JPanel card;
    private final static String PASSENGER = "Passenger";
    private final static String EMPLOYEE = "Employee";
    private final static String[] views = {PASSENGER, EMPLOYEE};
    private Object[] buttons;
    public ComboBoxPanel(JPanel panel){
        this.panel = panel;
        buttons = new Object[3];
        addComponentToPanel();
    }

    private void addComponentToPanel(){
        card = new JPanel(new CardLayout());

        JPanel passengerPanel = new JPanel();
        JPanel employeePanel = new JPanel();

        // JComboBox
        JPanel comboPanel = new JPanel();
        JComboBox cd = new JComboBox(views);
        cd.setEditable(false);
        cd.addItemListener(this);
        comboPanel.add(cd);

        // Employee card
        JButton login = new JButton("Login");
        buttons[0] = login;
        employeePanel.add(login);

        //Passenger card
        JButton loginForPassenger = new JButton("Login");
        buttons[1] = loginForPassenger;
        JTextField getID = new JTextField(8);
        buttons[2] = getID;

        passengerPanel.add(getID);
        passengerPanel.add(loginForPassenger);

        //String s = getID.getText();
//        loginForPassenger.addActionListener(new PassengerEnterListener(s));
//        getID.addActionListener(new PassengerEnterListener(s));

        card.add(passengerPanel, PASSENGER);
        card.add(employeePanel, EMPLOYEE);

        panel.add(comboPanel, BorderLayout.PAGE_START);
        panel.add(card, BorderLayout.CENTER);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout)(card.getLayout());
        cl.show(card, (String)e.getItem());
    }

    public Object[] getButtons() {
        return buttons;
    }
}
