package gui;

import database.Query;
import database.QueryResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class EmployeeViewer extends SubViewer {
    private JPanel employeePanel;
    private JPanel buttonPanel;

    private static EmployeeViewer instance;

    public EmployeeViewer(){
        super();
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
        employeePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(120, MainPageViewer.HEIGHT));
        buttonPanel.setLayout(new GridLayout(8, 1));


        addButtonIntoPanel();

        JButton back = new JButton("<Back");
        JPanel backPanel = new JPanel();

        JButton exit = new JButton("EXIT");
        exit.addActionListener(new ExitListener());

        backPanel.add(back);
        backPanel.add(exit);
        back.addActionListener(new BackListener());

        //JPanel titlePanel = new JPanel();
        JLabel title = new JLabel(" Employee");
        title.setFont(title.getFont().deriveFont(20f));
        //titlePanel.add(title);

        employeePanel.add(buttonPanel, BorderLayout.WEST);
        employeePanel.add(displayPanel, BorderLayout.CENTER);
        employeePanel.add(backPanel, BorderLayout.SOUTH);
        employeePanel.add(title, BorderLayout.NORTH);

    }

    private void addButtonIntoPanel(){
        String[] nameList = {"<html>Find<br />lost<br />baggage</html>", "<html>Add new<br />passenger</html>",
                "<html>Change<br />airline</html>", "<html>Update<br />arrival<br />flight</html>",
                "<html>Remove<br />passenger</html>", "<html>Show<br /># of<br />passengers<br />on each<br />flight</html>",
                "<html>Update<br />departure<br />flight</html>", "<html>Show<br />passengers</html>"};
        for(int i = 0; i < nameList.length; i++){
            ActionListener actionListener;
            switch (i){
                case 0:
                    actionListener = new AddListener(MethodFlag.FINDLOSTBAGGAGE);
                    break;
                case 1:
                    actionListener = new AddListener(MethodFlag.ADDNEWPASSENGER);
                    break;
                case 2:
                    actionListener = new AddListener(MethodFlag.CHANGEAIRINE);
                    break;
                case 3:
                    actionListener = new AddListener(MethodFlag.UPDATEARRIVAL);
                    break;
                case 4:
                    actionListener = new AddListener(MethodFlag.REMOVEPASSENGER);
                    break;
                case 5:
                    actionListener = new ShowNumOfPassengerListener();
                    break;
                case 6:
                    actionListener = new AddListener(MethodFlag.UPDATEDEPARTURE);
                    break;
                default:
                    actionListener = new ShowPassengerInfo();
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
        searchText.addActionListener(new EnterListener(tArr, fArr, MethodFlag.FINDLOSTBAGGAGE));
        submit.addActionListener(new EnterListener(tArr, fArr, MethodFlag.FINDLOSTBAGGAGE));
        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea();
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

        JLabel passengerID = new JLabel("Passenger ID");
        JLabel flightNum = new JLabel("Flight number: ");
        JLabel departureDate = new JLabel("Departure Date: ");
        JLabel name = new JLabel("Name: ");
        JLabel phone = new JLabel("Phone: ");
        JLabel address = new JLabel("Address: ");

        JTextField passengerT = new JTextField(8);
        JTextField flightNumT = new JTextField(8);
        JTextField departureT = new JTextField(10);
        JTextField nameT = new JTextField(10);
        JTextField phoneT = new JTextField(10);
        JTextField addressT = new JTextField(20);

        JTextField[] tArr = {passengerT, flightNumT, departureT, nameT, phoneT, addressT};
        Flag[] fArr = {Flag.INTEGER, Flag.CHAR, Flag.DATE, Flag.CHAR, Flag.CHAR, Flag.CHAR};
        JButton submit = new JButton("Submit");
        submit.addActionListener(new EnterListener(tArr, fArr, MethodFlag.ADDNEWPASSENGER));

        subPanel1.add(passengerID);
        subPanel1.add(passengerT);
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
        //JTextArea area = new JTextArea();
        try {
            Object[][] resultSet = QueryResult.parseResultSet(Query.showAllPassengers());
            scrollPane.setViewportView(new JTable(copyArray(resultSet), resultSet[0]));
            displayPanel.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            popOutWindow(e.getMessage(), "Error Code 112");
        }
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
        submit.addActionListener(new EnterListener(tArr, fArr, MethodFlag.CHANGEAIRINE));

        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(newAirline);
        searchPanel.add(newAirlineT);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        JTextArea area = new JTextArea();
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
        submit.addActionListener(new EnterListener(tArr, fArr, MethodFlag.UPDATEARRIVAL));

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

        //JTextArea area = new JTextArea();
        try {
            Object[][] resultSet = QueryResult.parseResultSet(Query.showAllArrivalFlights());
            scrollPane.setViewportView(new JTable(copyArray(resultSet),resultSet[0]));
            displayPanel.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            popOutWindow(e.getMessage(), "Error Code 113");
        }
    }

    protected void removePassenger(){
        JPanel searchPanel = new JPanel();
        JLabel searchName = new JLabel("ID: ");
        JTextField searchText = new JTextField(10);

        JButton submit = new JButton("Submit");
        JTextField[] tArr = {searchText};
        Flag[] fArr = {Flag.INTEGER};

        // data can be enter in two ways: either hit Enter or press button
        searchText.addActionListener(new EnterListener(tArr, fArr, MethodFlag.REMOVEPASSENGER));
        submit.addActionListener(new EnterListener(tArr, fArr, MethodFlag.REMOVEPASSENGER));

        searchPanel.add(searchName);
        searchPanel.add(searchText);
        searchPanel.add(submit);
        displayPanel.add(searchPanel, BorderLayout.NORTH);
        oldPanel = searchPanel;
        // TODO: scroll panel for table

        try {
            Object[][] resultSet = QueryResult.parseResultSet(Query.showAllPassengers());
            scrollPane.setViewportView(new JTable(copyArray(resultSet), resultSet[0]));
            displayPanel.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            popOutWindow(e.getMessage(), "Error Code 114");
        }
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
        submit.addActionListener(new EnterListener(tArr, fArr, MethodFlag.UPDATEDEPARTURE));

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

        try {
            Object[][] resultSet = QueryResult.parseResultSet(Query.showAllDepartureFlights());
            scrollPane.setViewportView(new JTable(copyArray(resultSet),resultSet[0]));
            displayPanel.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            popOutWindow(e.getMessage(), "Error Code 115");
        }
    }

    protected void showNumOfPassenger(){
        // TODO: scroll panel for table
        try {
            Object[][] resultSet = QueryResult.parseResultSet(Query.showPassengersCountOnEachDepartureFlight());
            scrollPane.setViewportView(new JTable(copyArray(resultSet),resultSet[0]));
            displayPanel.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            popOutWindow(e.getMessage(), "Error Code 116");
        }
    }

    protected void showPassengers(){
        try {
            Object[][] resultSet = QueryResult.parseResultSet(Query.showAllPassengers());
            scrollPane.setViewportView(new JTable(copyArray(resultSet),resultSet[0]));
            displayPanel.add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            popOutWindow(e.getMessage(), "Error Code 116");
        }
    }

    class AddListener implements ActionListener{
        private MethodFlag mf;
        public AddListener(MethodFlag mf){
            this.mf = mf;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            switch (mf){
                case FINDLOSTBAGGAGE:
                    findLostBaggagePanel();
                    break;
                case ADDNEWPASSENGER:
                    addNewPassengerPanel();
                    break;
                case CHANGEAIRINE:
                    changeAirline();
                    break;
                case UPDATEARRIVAL:
                    updateArrivalFlight();
                    break;
                case UPDATEDEPARTURE:
                    updateDepartureFlight();
                    break;
                case REMOVEPASSENGER:
                    removePassenger();
                    break;
            }
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

    class ShowPassengerInfo implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            removePanels();
            showPassengers();
            frame.revalidate();
        }
    }
}
