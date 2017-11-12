package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PassengerViewer extends SubViewer{
    private JPanel passengerPanel;
    private JPanel buttonPanel;
    private JPanel displayPanel;

    private static PassengerViewer instance;

    public PassengerViewer()
    {
        super();
        setFrameTitle("Passenger Page");
        createPanel();
        frame.add(passengerPanel);
    }

    public static PassengerViewer getInstance() {
        if(instance == null)
            instance = new PassengerViewer();
        return instance;
    }

    private void createPanel(){
        passengerPanel = new JPanel();
        passengerPanel.setLayout(new BorderLayout());
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(900, MainPageViewer.HEIGHT));
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(100, MainPageViewer.HEIGHT));
        buttonPanel.setLayout(new GridLayout(4, 1));


        addButtonToPanel();

        JButton back = new JButton("<Back");
        JPanel backPanel = new JPanel();
        backPanel.add(back);
        back.addActionListener(new BackListener());

        passengerPanel.add(buttonPanel, BorderLayout.WEST);
        passengerPanel.add(displayPanel, BorderLayout.CENTER);
        passengerPanel.add(backPanel, BorderLayout.SOUTH);
    }

    private void addButtonToPanel(){
        String[] nameList = {"<html>VIP<br />lounge?</html>", "<html>Foreign<br />exchange</html>",
                "<html>Favourite<br />restaurant<br />location</html>", "<html>Ate<br />here?</html>"};
        for(int i = 0; i < nameList.length; i++){
            ActionListener actionListener;
            switch (i){
                case 0:
                case 1:
                case 2:
                default:
                    break;
            }
            JButton button = new JButton(nameList[i]);
            //button.addActionListener(actionListener);
            buttonPanel.add(button);
        }
    }

    private void addPanel(String label){}
}
