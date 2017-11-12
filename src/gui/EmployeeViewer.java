package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;

public class EmployeeViewer extends SubViewer {
    private JPanel employeePanel;
    private JPanel buttonPanel;
    private String test;

    private static EmployeeViewer instance;

    public EmployeeViewer(){
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
        setFrameTitle("Employee Page");
        createPanel();

        frame.add(employeePanel);
    }

    public static EmployeeViewer getInstance() {
        if(instance == null)
            instance = new EmployeeViewer();
        return instance;
    }

    private void createPanel(){
        employeePanel = new JPanel();
        employeePanel.setLayout(new BorderLayout());
//        scrollPane = new JScrollPane();
//        displayPanel = new JPanel();
//        displayPanel.setLayout(new BorderLayout());
//        displayPanel.setPreferredSize(new Dimension(900, MainPageViewer.HEIGHT));
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(100, MainPageViewer.HEIGHT));
        buttonPanel.setLayout(new GridLayout(7, 1));


        addButtonIntoPanel();

        JButton back = new JButton("<Back");
        JPanel backPanel = new JPanel();
        backPanel.add(back);
        back.addActionListener(new BackListener());

        employeePanel.add(buttonPanel, BorderLayout.WEST);
        employeePanel.add(displayPanel, BorderLayout.CENTER);
        employeePanel.add(backPanel, BorderLayout.SOUTH);

    }

    private void addButtonIntoPanel(){
        String[] nameList = {"<html>Find<br />lost<br />baggage</html>", "<html>Add new<br />passenger</html>",
                "<html>Change<br />airline</html>", "<html>Update<br />arrival<br />flight</html>",
                "<html>Remove<br />passenger</html>", "<html>Show<br /># of<br />passengers<br />on each<br />flight</html>",
                "<html>Update<br />departure<br />flight</html>"};
        for(int i = 0; i < nameList.length; i++){
            ActionListener actionListener;
            switch (i){
                case 0:
                    actionListener = new BaggageListener();
                    break;
                case 1:
                    actionListener = new AddPassengerListener();
                    break;
                case 2:
                    actionListener = new ChangeAirlineListener();
                    break;
                case 3:
                    actionListener = new UpdateArrivalFlightListener();
                    break;
                case 4:
                    actionListener = new RemovePassengerListener();
                    break;
                case 5:
                    actionListener = new ShowNumOfPassengerListener();
                    break;
                default:
                    actionListener = new UpdateDepartureFlight();
                    break;
            }
            JButton button = new JButton(nameList[i]);
            button.addActionListener(actionListener);
            buttonPanel.add(button);
        }
    }

    protected void findLostBaggagePanel(){

        JPanel searchPanel = new JPanel();
        JLabel searchName = new JLabel("Baggage ID: ");
        JTextField searchText = new JTextField(10);
        JButton submit = new JButton("Submit");
        JTextField[] tArr = {searchText};
        Flag[] fArr = {Flag.INTEGER};

        // data can be enter in two ways: either hit Enter or press button
        searchText.addActionListener(new EnterListener(tArr, fArr));
        submit.addActionListener(new EnterListener(tArr, fArr));
        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    protected void addNewPassengerPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        mainPanel.setPreferredSize(new Dimension(MainPageViewer.WIDTH, 160));

        JPanel subPanel1 = new JPanel();
        //subPanel1.setPreferredSize(new Dimension(500, 40));
        JPanel subPanel2 = new JPanel();
       // subPanel2.setPreferredSize(new Dimension(500, 40));
        JPanel subPanel3 = new JPanel();
       // subPanel3.setPreferredSize(new Dimension(500, 40));
        JPanel subPanel4 = new JPanel();

        JLabel flightNum = new JLabel("Flight number: ");
        JLabel departureDate = new JLabel("Departure Date: ");
        JLabel name = new JLabel("Name: ");
        JLabel phone = new JLabel("Phone: ");
        JLabel address = new JLabel("Address: ");

        JTextField flightNumT = new JTextField(8);
        JTextField departureT = new JTextField(10);
        JTextField nameT = new JTextField(10);
        JTextField phoneT = new JTextField(10);
        JTextField addressT = new JTextField(20);

        JTextField[] tArr = {flightNumT, departureT, nameT, phoneT, addressT};
        Flag[] fArr = {Flag.CHAR, Flag.DATE, Flag.CHAR, Flag.CHAR, Flag.CHAR};
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EnterListener(tArr, fArr));

        subPanel1.add(flightNum);
        subPanel1.add(flightNumT);
        subPanel1.add(departureDate);
        subPanel1.add(departureT);

        subPanel2.add(name);
        subPanel2.add(nameT);
        subPanel2.add(phone);
        subPanel2.add(phoneT);

        subPanel3.add(address);
        subPanel3.add(addressT);

        subPanel4.add(submit);

        c.gridy = 0;
        mainPanel.add(subPanel1, c);
        c.gridy = 1;
        mainPanel.add(subPanel2, c);
        c.gridy = 2;
        mainPanel.add(subPanel3, c);
        c.gridy = 3;
        mainPanel.add(subPanel4, c);

        displayPanel.add(mainPanel, BorderLayout.NORTH);
        oldPanel = mainPanel;

        // TODO: gui
        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    protected void changeAirline(){
        JPanel searchPanel = new JPanel();
        JLabel searchName = new JLabel("Passenger ID: ");
        JTextField searchText = new JTextField(8);
        JLabel newAirline = new JLabel("New airline: ");
        JTextField newAirlineT = new JTextField(8);

        JTextField[] tArr = {searchText, newAirlineT};
        Flag[] fArr = {Flag.INTEGER, Flag.CHAR};
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EnterListener(tArr, fArr));

        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(newAirline);
        searchPanel.add(newAirlineT);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    protected void updateArrivalFlight(){
        JPanel searchPanel = new JPanel();
        JLabel searchName = new JLabel("Flight number: ");
        JTextField searchText = new JTextField(8);
        JLabel arrivalDate = new JLabel("Arrival date: ");
        JTextField arrivalDateT = new JTextField(10);
        JLabel newTime = new JLabel("New arrival time: : ");
        JTextField newTimeT = new JTextField(8);

        JTextField[] tArr = {searchText, arrivalDateT, newTimeT};
        Flag[] fArr = {Flag.CHAR, Flag.DATE, Flag.TIME};
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EnterListener(tArr, fArr));

        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(arrivalDate);
        searchPanel.add(arrivalDateT);
        searchPanel.add(newTime);
        searchPanel.add(newTimeT);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    protected void removePassenger(){
        JPanel searchPanel = new JPanel();
        JLabel searchName = new JLabel("ID: ");
        JTextField searchText = new JTextField(10);

        JButton submit = new JButton("Submit");
        JTextField[] tArr = {searchText};
        Flag[] fArr = {Flag.INTEGER};

        // data can be enter in two ways: either hit Enter or press button
        searchText.addActionListener(new EnterListener(tArr, fArr));
        submit.addActionListener(new EnterListener(tArr, fArr));

        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    protected void updateDepartureFlight(){
        JPanel searchPanel = new JPanel();
        JLabel searchName = new JLabel("Flight number: ");
        JTextField searchText = new JTextField(8);
        JLabel arrivalDate = new JLabel("Departure date: ");
        JTextField arrivalDateT = new JTextField(10);
        JLabel newTime = new JLabel("New departure time: : ");
        JTextField newTimeT = new JTextField(8);

        JTextField[] tArr = {searchText, arrivalDateT, newTimeT};
        Flag[] fArr = {Flag.CHAR, Flag.DATE, Flag.TIME};
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EnterListener(tArr, fArr));

        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(arrivalDate);
        searchPanel.add(arrivalDateT);
        searchPanel.add(newTime);
        searchPanel.add(newTimeT);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    protected void showNumOfPassenger(){
//        JPanel searchPanel = new JPanel();
//        JScrollPane scrollPane = new JScrollPane();
//        searchPanel.add(scrollPane);
//        displayPanel.add(searchPanel, BorderLayout.CENTER);
//        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea(test);
        scrollPane.setViewportView(area);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
    }

    class BaggageListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            findLostBaggagePanel();
            frame.revalidate();
        }
    }

//    private void removePanels() {
//        if(oldPanel != null){
//            displayPanel.remove(oldPanel);
//        }
//        if(scrollPane != null){
//            displayPanel.remove(scrollPane);
//        }
//    }

    class AddPassengerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            addNewPassengerPanel();
            frame.revalidate();
        }
    }

    class ChangeAirlineListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            changeAirline();
            frame.revalidate();
        }
    }
    class UpdateArrivalFlightListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            updateArrivalFlight();
            frame.revalidate();
        }
    }

    class RemovePassengerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            removePassenger();
            frame.revalidate();
        }
    }

    class ShowNumOfPassengerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            showNumOfPassenger();
            frame.revalidate();
        }
    }

    class UpdateDepartureFlight implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            updateDepartureFlight();
            frame.revalidate();
        }
    }
}
