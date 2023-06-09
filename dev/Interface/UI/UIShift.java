package Interface.UI;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UIShift {
    private JFrame frame;
    private JPanel panel;

    public UIShift(int WeekNum,int yearNum,int superNum, int day){
        frame = new JFrame("Shift");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel = new JPanel(new BorderLayout());

        frame.add(panel);
        frame.setSize(600, 300);
        frame.setVisible(true);
    }
    public static void main(String[] args) throws SQLException {
        UIShift shift = new UIShift(10,1997,0,1);
    }
}
