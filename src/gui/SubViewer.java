package gui;

import database.Query;
import database.QueryResult;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SubViewer {
    protected JFrame frame;
    protected JPanel oldPanel;
    protected JScrollPane scrollPane;
    protected JPanel displayPanel;
    protected Object[][] resultSet;
    public static final float SIZE = 15f;
    //private static final String PATH = "doge.png";
    public SubViewer()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        scrollPane = new JScrollPane();
        oldPanel = new JPanel();
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setPreferredSize(new Dimension(900, MainPageViewer.HEIGHT));

        //Set up the content pane.

        //Display the window.
        frame.setSize(MainPageViewer.WIDTH, MainPageViewer.HEIGHT);
        frame.add(panel);
        //frame.pack();
        //frame.setVisible(false);
    }

    public void viewThisFrame(){
        frame.setVisible(true);
    }

    public void setFrameTitle(String text){
        frame.setTitle(text);
    }

    protected void removePanels() {
        if(oldPanel != null){
            displayPanel.remove(oldPanel);
        }
        if(scrollPane != null){
            displayPanel.remove(scrollPane);
        }
    }

    class EnterListener implements ActionListener{
        JTextField[] tArr;
        //Object o;

        /**
         * checkFLag:
         * 0: character
         * 1: integer
         * 2: date
         * 3: time
         */
        Flag[] fArr;
        MethodFlag mf;
        ArrayList<Integer> resultIntegers;
        ArrayList<String> resultStrings;
        java.sql.Date d;
        java.sql.Time t;
        //Query q = new Query();
        public EnterListener(JTextField[] tArr, Flag[] fArr, MethodFlag mf){
            this.fArr = fArr;
            this.tArr = tArr;
            this.mf = mf;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            resultIntegers = new ArrayList<>();
            resultStrings = new ArrayList<>();
//            resultIntegers.add(-1);
//            resultStrings.add(null);

            for(int i = 0; i < tArr.length; i++){
                String str = tArr[i].getText();
                switch (fArr[i]){
                    case CHAR:
                        resultStrings.add(str);
                        break;
                    case INTEGER:
                        int c = checkInteger(str);
                        resultIntegers.add(c);
                        //System.out.println(c);
                        break;
                    case DATE:
                        d = checkDate(str);
                        //System.out.println(d);
                        break;
                    case TIME:
                        t = checkTime(str);
                        //System.out.println(t);
                        break;
                }
            }

            if(scrollPane != null)
                displayPanel.remove(scrollPane);

            //Integer pid = resultIntegers.get(0);
            //String name = resultStrings.get(0);

            switch (mf){
                case LOUNGE:
                    //System.out.println("get");
                    if(resultIntegers.get(0) != -1)
                        getVIPLounge(resultIntegers.get(0));
                    break;
                case FINDLOSTBAGGAGE:
                    if(resultIntegers.get(0) != -1)
                        getLostBaggage(resultIntegers.get(0));
                    break;
                case REMOVEPASSENGER:
                    if(resultIntegers.get(0) != -1)
                        removePassenger(resultIntegers.get(0));
                    break;
                case RATING:
                    if(resultIntegers.get(0) != -1)
                        getHereRating(resultIntegers.get(0));
                    break;
                case RESTAURANT:
                    if(resultStrings.get(0) != null)
                        getFavouriteRestaurant(resultStrings.get(0));
                    break;
                case CHANGEAIRINE:
                    if(resultIntegers.get(0) != -1 && resultStrings.get(0) != null)
                        changeAirline(resultIntegers.get(0), resultStrings.get(0));
                    break;
                case NONENGSERVICE:
                    if(resultIntegers.get(0) != -1)
                        getNonEnglishService(resultIntegers.get(0));
                    break;
                case UPDATEARRIVAL:
                    if(resultStrings.get(0) != null &&
                            d != null && t != null)
                        updateArrival(resultStrings.get(0), d, t);
                    break;
                case ADDNEWPASSENGER:
                    if(resultIntegers.get(0) != -1 && resultStrings.get(0) != null &&
                            d != null && resultStrings.get(1) != null &&
                            resultStrings.get(2) != null && resultStrings.get(3) != null)
                        addPassenger(resultIntegers.get(0), resultStrings.get(0),
                            d, resultStrings.get(1), resultStrings.get(2), resultStrings.get(3));
                    break;
                case UPDATEDEPARTURE:
                    if(resultStrings.get(0) != null &&
                            d != null && t != null)
                        updateDeparture(resultStrings.get(0), d, t);
                    break;
            }
        }

        private void getVIPLounge(int pid){
            try {
                JTextArea a = new JTextArea(Query.vipLoungeAvailable(pid));
                a.setFont(a.getFont().deriveFont(17f));
                //resultSet = QueryResult.parseResultSet(Query.vipLoungeAvailable(pid));
                scrollPane = new JScrollPane(a);
//                scrollPane.setViewportView(new JTable(copyArray(resultSet), resultSet[0]));
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            } catch (SQLException e) {
                popOutWindow(e.getMessage(), "Error Code 120");
                //popOutWindow(e.getMessage(), "Error Code 117");
            }
        }

        private void removePassenger(int pid){
            try {
                JTextArea a = new JTextArea(Query.removePassenger(pid));
                a.setFont(a.getFont().deriveFont(17f));
                scrollPane = new JScrollPane(a);
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            } catch (SQLException e) {
                popOutWindow(e.getMessage(), "Error Code 127");
            }
        }

        private void updateDeparture(String flightNumber, java.sql.Date departureDate,
                                     java.sql.Time newDepartureTime){
            try {
                JTextArea a = new JTextArea(Query.updateDepartureFlightTime(flightNumber, departureDate, newDepartureTime));
                a.setFont(a.getFont().deriveFont(17f));
                scrollPane.setViewportView(a);
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            } catch (SQLException e) {
                popOutWindow(e.getMessage(), "Error Code 128");
            }
        }

        private void changeAirline(int passengerId, String newAirlineName){
            try {
                JTextArea a = new JTextArea(Query.changeAirline(passengerId, newAirlineName));
                a.setFont(a.getFont().deriveFont(17f));
                scrollPane = new JScrollPane(a);
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            } catch (SQLException e) {
                popOutWindow(e.getMessage(), "Error Code 126");
            }
        }

        private void addPassenger(int passengerId, String departureFlightNumber, Date departureFlightDate,
                                  String passengerName, String phoneNumber, String address){
            try{
                JTextArea a = new JTextArea(Query.addNewPassenger(passengerId,
                    departureFlightNumber, departureFlightDate, passengerName, phoneNumber, address));
                a.setFont(a.getFont().deriveFont(17f));
                scrollPane = new JScrollPane(a);
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            }catch(SQLException e){
                popOutWindow(e.getMessage(), "Error Code 124");
            }
        }

        private void getNonEnglishService(int pid){
            try {
                JTextArea a = new JTextArea(Query.nonEnglish_exch(pid));
                a.setFont(a.getFont().deriveFont(17f));
                scrollPane = new JScrollPane(a);
//                scrollPane.setViewportView(new JTable(copyArray(resultSet), resultSet[0]));
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            } catch (SQLException e) {
                //System.out.println(e);
                popOutWindow(e.getMessage(), "Error Code 119");
            }
        }

        private void updateArrival(String flightNumber, java.sql.Date arrivalDate,
                                   java.sql.Time newArrivalTime){
            try {
                JTextArea a = new JTextArea(Query.updateArrivalFlightTime(flightNumber, arrivalDate, newArrivalTime));
                a.setFont(a.getFont().deriveFont(17f));
                scrollPane.setViewportView(a);
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            } catch (SQLException e) {
                popOutWindow(e.getMessage(), "Error Code 123");
            }
        }

        private void getHereRating(int pid){
            try{
                resultSet = QueryResult.parseResultSet(Query.ateHereStars(pid));
                JTable t = new JTable(copyArray(resultSet), resultSet[0]);
                t.setFont(t.getFont().deriveFont(17f));
                scrollPane = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//                scrollPane.setViewportView(new JTable(copyArray(resultSet), resultSet[0]));
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            }catch(SQLException e){
                popOutWindow(e.getMessage(), "Error Code 118");
            }
        }

        private void getLostBaggage(int pid){
            try{
                resultSet = QueryResult.parseResultSet(Query.findLostBaggage(pid));
                JTable t = new JTable(copyArray(resultSet), resultSet[0]);
                t.setFont(t.getFont().deriveFont(17f));
                scrollPane = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

//                scrollPane.setViewportView(new JTable(copyArray(resultSet), resultSet[0]));
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            }catch(SQLException e){
                popOutWindow(e.getMessage(), "Error Code 125");
            }
        }

        private void getFavouriteRestaurant(String name){
            try{
                if(name.length() == 0)
                    resultSet = QueryResult.parseResultSet(Query.showAllRestaurants());
                else
                    resultSet = QueryResult.parseResultSet(Query.favoriteLocation(name));
                JTable t = new JTable(copyArray(resultSet), resultSet[0]);
                t.setFont(t.getFont().deriveFont(17f));
                scrollPane = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
            }catch(SQLException e){
                popOutWindow(e.getMessage(), "Error Code 121");
            }
        }

        private int checkInteger(String s){
            try{
                int i = Integer.parseInt(s);
                if(i > 0)
                    return i;
                else{
                    popOutWindow("You should input positive integers!", "Error Code 101");
                    return -1;
                }
            }catch(Exception e){
                popOutWindow("You should input integers!", "Error Code 101");
                return -1;
            }
        }

        private java.sql.Date checkDate(String s){
            try{
                String[] dateParts = s.split("\\-");
                if(dateParts.length != 3){
                    try{
                        String[] dateWithSlash = s.split("\\/");
                        if(dateWithSlash.length != 3){
                            popOutWindow("The format of input is yyyy-mm-dd or yyyy/mm/dd.", "Error Code 102");
                            return null;
                        }
                        int year = Integer.parseInt(dateWithSlash[0]);
                        int month = Integer.parseInt(dateWithSlash[1]);
                        int day = Integer.parseInt(dateWithSlash[2]);
                        if(year > 0 && month >= 1 && month <= 12 &&
                                day > 0 && day <= 31){
                            //popOutWindow("Please input valid date!", "Error Code 103");
                            java.util.Date date = new SimpleDateFormat("yyyy/mm/dd").parse(s);
                            //System.out.println(date.getTime());
                            return new java.sql.Date(date.getTime());
                        }
                    }catch(Exception exception){
                        popOutWindow("Please input valid date!", "Error Code 104");
                        return null;
                    }
                    popOutWindow("Please input valid date!", "Error Code 105");
                    return null;
                }
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);
                if(year > 0 && month >= 1 && month <= 12 &&
                        day > 0 && day <= 31){
                    java.util.Date date = new SimpleDateFormat("yyyy-mm-dd").parse(s);
                    return new java.sql.Date(date.getTime());
                }
            }catch(Exception e){
                popOutWindow(e.getMessage(), "Error Code 106");
                return null;
            }
            popOutWindow("Please input valid date!", "Error Code 107");
            return null;
        }

        private java.sql.Time checkTime(String s){
            try{
                String[] timeParts = s.split("\\:");
                if(timeParts.length != 3){
                    if(timeParts.length != 2){
                        popOutWindow("The format of time is hh:mm:ss or hh:mm", "Error Code 108");
                        return null;
                    }
                    int hourTwo = Integer.parseInt(timeParts[0]);
                    int minuteTwo = Integer.parseInt(timeParts[1]);
                    if(hourTwo >= 0 && hourTwo <= 24 &&
                            minuteTwo >= 0 && minuteTwo <= 59){
                        java.util.Date date = new SimpleDateFormat("hh:mm").parse(s);
                        return new java.sql.Time(date.getTime());
                    }
                    else{
                        popOutWindow("Please input valid time!","Error Code 109");
                        return null;
                    }
                }
                int hour = Integer.parseInt(timeParts[0]);
                int minute = Integer.parseInt(timeParts[1]);
                int second = Integer.parseInt(timeParts[2]);
                if(hour >= 0 && hour <= 24 &&
                        minute >= 0 && minute <= 59 &&
                        second >= 0 && second <= 59){
                    java.util.Date date = new SimpleDateFormat("hh:mm:ss").parse(s);
                    return new java.sql.Time(date.getTime());
                }
            }catch (Exception e){
                popOutWindow(e.getMessage(),"Error Code 110");
                return null;
            }
            popOutWindow("Please input valid time!","Error Code 111");
            return null;
        }
    }

    protected void popOutWindow(String message, String error) {
        JOptionPane.showMessageDialog(null, message, error, JOptionPane.ERROR_MESSAGE);
    }

    class BackListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            MainPagePanel.getInstance().setInvisible(true);
        }
    }

    class ExitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    protected Object[][] copyArray(Object[][] o){
        Object[][] subcopy = new Object[o.length-1][o[0].length];
        //System.out.println(o.length);
        for(int i = 1; i < o.length; i++){
            //System.out.println(o[i]);
           subcopy[i-1] = o[i];
        }
        return subcopy;
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 150; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
}
