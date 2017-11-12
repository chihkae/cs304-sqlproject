package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class PassengerViewer extends SubViewer{
    private JPanel passengerPanel;
    private JPanel buttonPanel;
    private String test;

    private static PassengerViewer instance;

    public PassengerViewer()
    {
        super();

        try {
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            test = sb.toString();
            br.close();
        } catch(Exception e) {
            test = "Cannot solve";
        }

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
                    actionListener = new ButtonListener("Lounge available for flight? ", "Passenger ID: ", Flag.INTEGER);
                    break;
                case 1:
                    actionListener = new ButtonListener("Non-english service at terminal for foreign exchange? ", "Passenger ID: ", Flag.INTEGER);
                    break;
                case 2:
                    actionListener = new ButtonListener("Find the location of your favorite restaurant? ", "Restaurant name: ", Flag.CHAR);
                    break;
                default:
                    actionListener = new ButtonListener("Find out ratings of whre you ate at? ", "Passenger ID: ", Flag.INTEGER);
                    break;
            }
            JButton button = new JButton(nameList[i]);
            button.addActionListener(actionListener);
            //button.addActionListener(actionListener);
            buttonPanel.add(button);
        }
    }

    private void addPanel(String text, String label, Flag f){

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800, 70));
        mainPanel.setLayout(new BorderLayout());

        JPanel subPanel = new JPanel();
        JPanel textPanel = new JPanel();

        JLabel message = new JLabel(text);
        JLabel question = new JLabel(label);
        JTextField answer = new JTextField(10);

        JTextField[] tArr = {answer};
        Flag[] fArr = {f};
        answer.addActionListener(new EnterListener(tArr, fArr));
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EnterListener(tArr, fArr));

        subPanel.add(question);
        subPanel.add(answer);
        subPanel.add(submit);
        textPanel.add(message);
        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(subPanel, BorderLayout.SOUTH);

        displayPanel.add(mainPanel, BorderLayout.NORTH);
        oldPanel = mainPanel;

        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    class ButtonListener implements ActionListener{
        String text;
        String label;
        Flag f;
        private ButtonListener(String text, String label, Flag f){
            this.text = text;
            this.label = label;
            this.f = f;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            addPanel(text, label, f);
            frame.revalidate();
        }
    }
}
