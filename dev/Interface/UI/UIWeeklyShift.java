package Interface.UI;

import Service.HRManagerService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UIWeeklyShift {
    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public UIWeeklyShift(int WeekNum,int yearNum,int superNum) throws SQLException {
        frame = new JFrame("Weekly Shift");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();

        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new WeeklyShiftCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(600, 300);
        frame.setVisible(true);

        createWeeklyShift(WeekNum,yearNum,superNum);
    }

    private void createWeeklyShift(int WeekNum,int yearNum,int superNum) throws SQLException {
        // Create the professions
        String[] professions = {"manager", "cashier", "stock", "security", "cleaning", "shelf-stocking", "general-worker"};

        // Iterate over the days of the week
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        // Set the column names
        tableModel.addColumn("Shift");
        for (String day : daysOfWeek) {
            tableModel.addColumn(day);
        }

        // Create the rows for day shift and night shift
        Object[] dayShiftRow = new Object[8];
        Object[] nightShiftRow = new Object[8];
        dayShiftRow[0] = "<html><body><b>Day Shift</b></body></html>";
        nightShiftRow[0] = "<html><body><b>Night Shift</b></body></html>";

        // Iterate over the days and populate the cells with profession counts
        for (int i = 1; i < 8; i++) {
            String day = daysOfWeek[i - 1];

            Map<String, Integer> dayShiftProfessions = getProfessionCounts(day, "Day Shift",WeekNum,yearNum,superNum, professions);
            Map<String, Integer> nightShiftProfessions = getProfessionCounts(day, "Night Shift",WeekNum,yearNum,superNum, professions);

            StringBuilder dayShiftBuilder = new StringBuilder("<html><body>");
            StringBuilder nightShiftBuilder = new StringBuilder("<html><body>");

            // Build the profession count strings for the day shift
            for (String profession : professions) {
                int count = dayShiftProfessions.getOrDefault(profession, 0);
                dayShiftBuilder.append(profession).append(": ").append(count).append("<br>");
            }
            dayShiftBuilder.append("</body></html>");
            dayShiftRow[i] = dayShiftBuilder.toString();

            // Build the profession count strings for the night shift
            for (String profession : professions) {
                int count = nightShiftProfessions.getOrDefault(profession, 0);
                nightShiftBuilder.append(profession).append(": ").append(count).append("<br>");
            }
            nightShiftBuilder.append("</body></html>");
            nightShiftRow[i] = nightShiftBuilder.toString();
        }

        // Add the rows to the table model
        tableModel.addRow(dayShiftRow);
        tableModel.addRow(nightShiftRow);

        // Set row height explicitly
        int lineHeight = table.getFontMetrics(table.getFont()).getHeight();
        table.setRowHeight(lineHeight * (professions.length + 1));
    }




    private Map<String, Integer> getProfessionCounts(String day,String shiftType, int WeekNum,int yearNum,int superNum, String[] professions) throws SQLException {
        HRManagerService hr = new HRManagerService();
        return getProfessionCounts(day,shiftType,WeekNum,yearNum,superNum,professions);
    }

    public static void main(String[] args) throws SQLException {
        UIWeeklyShift ws = new UIWeeklyShift(1,1,1);
    }

    private class WeeklyShiftCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Align the cell values to the left
            setHorizontalAlignment(SwingConstants.LEFT);

            // Set preferred size to accommodate the content
            Dimension preferredSize = component.getPreferredSize();
            int lineHeight = table.getFontMetrics(component.getFont()).getHeight();
            int lineCount = value.toString().split("\n").length;
            preferredSize.height = lineHeight * lineCount;
            component.setPreferredSize(preferredSize);

            return component;
        }
    }
}
