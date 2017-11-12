package gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SubViewer {
    protected JFrame frame;
    public SubViewer()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

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
                return -1;
            }
        }

        private String checkDate(String s){
            try{
                String[] dateParts = s.split("\\-");
                if(dateParts.length != 3){
                    return null;
                }
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);
                if(year >= 0 && month >= 1 && month <= 12 &&
                        day >= 0 && day <= 31){
                    return s;
                }
            }catch(Exception e){
                return null;
            }
            return null;
        }

        private String checkTime(String s){
            try{
                String[] timeParts = s.split("\\:");
                if(timeParts.length != 3)
                    return null;
                int hour = Integer.parseInt(timeParts[0]);
                int minute = Integer.parseInt(timeParts[1]);
                int second = Integer.parseInt(timeParts[2]);
                if(hour >= 0 && hour <= 24 &&
                        minute >= 0 && minute <= 59 &&
                        second >= 0 && second <= 59)
                    return s;
            }catch (Exception e){
                return null;
            }
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
