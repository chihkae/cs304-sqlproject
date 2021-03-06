package gui;

import database.Query;
import database.QueryResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PassengerViewer extends SubViewer{
    //public static final float SIZE = 17f;
    private JPanel passengerPanel;
    private JPanel buttonPanel;
    private static int id;
    private static JLabel title;

    private static PassengerViewer instance;

    public PassengerViewer(int id)
    {
        super();

        this.id = id;
        setFrameTitle("Passenger Page");
        createPanel();
        frame.add(passengerPanel);
    }

    public static PassengerViewer getInstance(int id) {
        if(instance == null)
            instance = new PassengerViewer(id);
        else
            changeID(id);
        return instance;
    }

    private static void changeID(int pid){
        id = pid;
        title.setText("Passenger "+id);
        title.setFont(title.getFont().deriveFont(20f));
    }

    private void createPanel(){
        passengerPanel = new JPanel();
        passengerPanel.setLayout(new BorderLayout());
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(880, MainPageViewer.HEIGHT));
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(120, MainPageViewer.HEIGHT));
        buttonPanel.setLayout(new GridLayout(5, 1));


        addButtonToPanel();

        JButton back = new JButton("<Back");
        JPanel backPanel = new JPanel();

        JButton exit = new JButton("EXIT");
        exit.addActionListener(new ExitListener());

        backPanel.add(back);
        backPanel.add(exit);
        back.addActionListener(new BackListener());

        //JPanel titlePanel = new JPanel();
        title = new JLabel(" Passenger "+id);
        title.setFont(title.getFont().deriveFont(20f));
        //titlePanel.add(title);

        passengerPanel.add(buttonPanel, BorderLayout.WEST);
        passengerPanel.add(displayPanel, BorderLayout.CENTER);
        passengerPanel.add(backPanel, BorderLayout.SOUTH);
        passengerPanel.add(title, BorderLayout.NORTH);
    }

    private void addButtonToPanel(){
        String[] nameList = {"<html>VIP<br />lounge?</html>", "<html>Foreign<br />exchange</html>",
                "<html>Favourite<br />restaurant<br />location</html>", "<html>Ate<br />here?</html>",
                "<html>Information</html>"};
        for(int i = 0; i < nameList.length; i++){
            ActionListener actionListener;
            switch (i){
                case 0:
                    actionListener = new ButtonListener("Lounge available for flight? ", "Passenger ID: ", Flag.INTEGER, MethodFlag.LOUNGE);
                    break;
                case 1:
                    actionListener = new ButtonListener("Non-english service at terminal for foreign exchange? ", "Passenger ID: ", Flag.INTEGER, MethodFlag.NONENGSERVICE);
                    break;
                case 2:
                    actionListener = new ButtonListener("Find the location of your favorite restaurant? ", "Restaurant name: ", Flag.CHAR, MethodFlag.RESTAURANT);
                    break;
                case 3:
                    actionListener = new ButtonListener("Find out ratings of where you ate at? ", "Passenger ID: ", Flag.INTEGER, MethodFlag.RATING);
                    break;
                default:
                    actionListener = new InformationListener();
                    break;
            }
            JButton button = new JButton(nameList[i]);
            button.addActionListener(actionListener);
            //button.addActionListener(actionListener);
            buttonPanel.add(button);
        }
    }

    private void addPanel(String text, String label, Flag f, MethodFlag mf){

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
        answer.addActionListener(new EnterListener(tArr, fArr, mf));
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EnterListener(tArr, fArr, mf));

        subPanel.add(question);
        subPanel.add(answer);
        subPanel.add(submit);
        textPanel.add(message);
        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(subPanel, BorderLayout.SOUTH);

        displayPanel.add(mainPanel, BorderLayout.NORTH);
        oldPanel = mainPanel;

        if(mf == MethodFlag.RESTAURANT){
            showRestaurants();
        }else{
            JTextArea area = new JTextArea();
            scrollPane.setViewportView(area);
            displayPanel.add(scrollPane, BorderLayout.CENTER);
        }
    }

    class ButtonListener implements ActionListener{
        String text;
        String label;
        Flag f;
        MethodFlag mf;
        private ButtonListener(String text, String label, Flag f, MethodFlag mf){
            this.text = text;
            this.label = label;
            this.f = f;
            this.mf = mf;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            addPanel(text, label, f, mf);
            frame.revalidate();
        }
    }

    class InformationListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            //oldPanel = null;
            try {
                resultSet = QueryResult.parseResultSet(Query.showView(id));
                JTable t = new JTable(copyArray(resultSet), resultSet[0]);

                t.setFont(t.getFont().deriveFont(SIZE));
                resizeColumnWidth(t);
                scrollPane = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            } catch (SQLException e1) {
                popOutWindow(e1.getMessage(), "Error Code 122");
            }
        }
    }
}
