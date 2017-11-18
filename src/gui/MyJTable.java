package gui;

import javax.swing.*;
import java.awt.event.*;

public class MyJTable extends JTable{
    public MyJTable(Object[][] a, Object[] b){
        super(a,b);
    }
    public String getToolTipText( MouseEvent e )
    {
        int row = rowAtPoint( e.getPoint() );
        int column = columnAtPoint( e.getPoint() );

        Object value = getValueAt(row, column);
        return value == null ? null : value.toString();
    }
}
