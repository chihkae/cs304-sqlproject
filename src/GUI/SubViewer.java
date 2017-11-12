package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SubViewer {
    protected JFrame frame;
    protected JPanel oldPanel;
    protected JScrollPane scrollPane;
    protected JPanel displayPanel;
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

        /**
         * checkFLag:
         * 0: character
         * 1: integer
         * 2: date
         * 3: time
         */
        Flag[] fArr;
        public EnterListener(JTextField[] tArr, Flag[] fArr){
            this.fArr = fArr;
            this.tArr = tArr;
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
                        System.out.println(c);
                        break;
                    case DATE:
                        String d = checkDate(str);
                        System.out.println(d);
                        break;
                    case TIME:
                        String t = checkTime(str);
                        System.out.println(t);
                        break;
                }
            }
        }

        private int checkInteger(String s){
            try{
                int i = Integer.parseInt(s);
                return i;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "You should input Integers!", "Error Code 101", JOptionPane.ERROR_MESSAGE);
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
                            JOptionPane.showMessageDialog(null, "The format of input is yyyy-mm-dd or yyyy/mm/dd.", "Error Code 102", JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                        int year = Integer.parseInt(dateWithSlash[0]);
                        int month = Integer.parseInt(dateWithSlash[1]);
                        int day = Integer.parseInt(dateWithSlash[2]);
                        if(year > 0 && month >= 1 && month <= 12 &&
                                day > 0 && day <= 31){
                            JOptionPane.showMessageDialog(null, "Please input valid date!", "Error Code 103", JOptionPane.ERROR_MESSAGE);
                            return s;
                        }
                    }catch(Exception exception){
                        JOptionPane.showMessageDialog(null, "Please input valid date!", "Error Code 104", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    JOptionPane.showMessageDialog(null, "Please input valid date!", "Error Code 105", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "Please input valid date!", "Error Code 106", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            JOptionPane.showMessageDialog(null, "Please input valid date!", "Error Code 107", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        private String checkTime(String s){
            try{
                String[] timeParts = s.split("\\:");
                if(timeParts.length != 3){
                    if(timeParts.length != 2){
                        JOptionPane.showMessageDialog(null, "The format of time is hh:mm:ss or hh:mm", "Error Code 108", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                    int hourTwo = Integer.parseInt(timeParts[0]);
                    int minuteTwo = Integer.parseInt(timeParts[1]);
                    if(hourTwo >= 0 && hourTwo <= 24 &&
                            minuteTwo >= 0 && minuteTwo <= 59)
                        return s;
                    else{
                        JOptionPane.showMessageDialog(null, "Please input valid time!", "Error Code 109", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "Please input valid time!", "Error Code 110", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            JOptionPane.showMessageDialog(null, "Please input valid time!", "Error Code 111", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    class BackListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            MainPagePanel.getInstance().setInvisible(true);
        }
    }
}
