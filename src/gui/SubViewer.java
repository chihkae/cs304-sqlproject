package gui;

import database.Query;
import database.QueryResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubViewer {
    protected JFrame frame;
    protected JPanel oldPanel;
    protected JScrollPane scrollPane;
    protected JPanel displayPanel;
    protected Object[][] resultSet;
    protected int successInt;
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
        ArrayList<Integer> resultIntegers = new ArrayList<>();
        ArrayList<String> resultStrings = new ArrayList<>();
        //Query q = new Query();
        public EnterListener(JTextField[] tArr, Flag[] fArr, MethodFlag mf){
            this.fArr = fArr;
            this.tArr = tArr;
            this.mf = mf;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i = 0; i < tArr.length; i++){
                String str = tArr[i].getText();
                switch (fArr[i]){
                    case CHAR:
                        break;
                    case INTEGER:
                        int c = checkInteger(str);
                        resultIntegers.add(c);
                        //System.out.println(c);
                        break;
                    case DATE:
                        String d = checkDate(str);
                        resultStrings.add(d);
                        //System.out.println(d);
                        break;
                    case TIME:
                        String t = checkTime(str);
                        resultStrings.add(t);
                        //System.out.println(t);
                        break;
                }
            }

            switch (mf){
                case LOUNGE:
                    //System.out.println("get");
                    getVIPLounge(resultIntegers.get(0));
                    break;
                case FINDLOSTBAGGAGE:
                case REMOVEPASSENGER:
                case RATING:
                case RESTAURANT:
                case CHANGEAIRINE:
                case NONENGSERVICE:
                case UPDATEARRIVAL:
                case ADDNEWPASSENGER:
                case UPDATEDEPARTURE:
            }
        }

        private void getVIPLounge(int pid){
            try {
                resultSet = QueryResult.parseResultSet(Query.vipLoungAvailable(pid));
                //System.out.println(resultSet.length);
                //System.out.println(resultSet[0][0]);
                scrollPane.setViewportView(new JTable(copyArray(resultSet), resultSet[0]));
                displayPanel.add(scrollPane, BorderLayout.CENTER);
                frame.revalidate();
                //        scrollPane.setViewportView(createTable());
//        displayPanel.add(scrollPane, BorderLayout.CENTER);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        private int checkInteger(String s){
            try{
                int i = Integer.parseInt(s);
                return i;
            }catch(Exception e){
                popOutWindow("You should input Integers!", "Error Code 101");
                return -1;
            }
        }

        private String checkDate(String s){
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
                            popOutWindow("Please input valid date!", "Error Code 103");
                            return s;
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
                    return s;
                }
            }catch(Exception e){
                popOutWindow("Please input valid date!", "Error Code 106");
                return null;
            }
            popOutWindow("Please input valid date!", "Error Code 107");
            return null;
        }

        private String checkTime(String s){
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
                            minuteTwo >= 0 && minuteTwo <= 59)
                        return s;
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
                        second >= 0 && second <= 59)
                    return s;
            }catch (Exception e){
                popOutWindow("Please input valid time!","Error Code 110");
                return null;
            }
            popOutWindow("Please input valid time!","Error Code 111");
            return null;
        }
    }

    private void popOutWindow(String message, String error) {
//        ImageIcon icon = new ImageIcon(PATH);
//        Image image = icon.getImage();
//        Image scaled = image.getScaledInstance(80,80, java.awt.Image.SCALE_SMOOTH);
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

    private Object[][] copyArray(Object[][] o){
        Object[][] subcopy = new Object[o.length][o[0].length];
        for(int i = 1; i < o.length; i++){
           subcopy[i] = o[i];
        }
        return subcopy;
    }
}
