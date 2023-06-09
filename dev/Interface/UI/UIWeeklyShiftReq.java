package Interface.UI;

import Service.HRManagerService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class UIWeeklyShiftReq {
    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public UIWeeklyShiftReq(int WeekNum, int yearNum, int superNum) throws SQLException {
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

    private void createWeeklyShift(int WeekNum, int yearNum, int superNum) throws SQLException {
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

        // Iterate over the days and populate the cells with profession requirements
        for (int i = 1; i < 8; i++) {
            int day = i - 1;

            Map<String, Integer> dayShiftRequirements = getProfessionCounts(day, "Day Shift", WeekNum, yearNum, superNum, professions);
            Map<String, Integer> nightShiftRequirements = getProfessionCounts(day, "Night Shift", WeekNum, yearNum, superNum, professions);

            JPanel dayShiftPanel = new JPanel(new GridLayout(0, 1));
            JPanel nightShiftPanel = new JPanel(new GridLayout(0, 1));

            // Add the profession requirements text fields for the day shift
            for (String profession : professions) {
                int count = dayShiftRequirements.getOrDefault(profession, 0);
                JLabel professionLabel = new JLabel(profession);
                JTextField requirementField = new JTextField(String.valueOf(count));

                dayShiftPanel.add(professionLabel);
                dayShiftPanel.add(requirementField);

                // Update the requirement when the text field value changes
                requirementField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        try {
                            updateRequirement(day, "Day Shift", profession, requirementField.getText(), WeekNum, yearNum, superNum);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        try {
                            updateRequirement(day, "Day Shift", profession, requirementField.getText(), WeekNum, yearNum, superNum);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        try {
                            updateRequirement(day, "Day Shift", profession, requirementField.getText(), WeekNum, yearNum, superNum);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }

            // Add the profession requirements text fields for the night shift
            for (String profession : professions) {
                int count = nightShiftRequirements.getOrDefault(profession, 0);
                JLabel professionLabel = new JLabel(profession);
                JTextField requirementField = new JTextField(String.valueOf(count));

                nightShiftPanel.add(professionLabel);
                nightShiftPanel.add(requirementField);

                // Update the requirement when the text field value changes
                requirementField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        try {
                            updateRequirement(day, "Night Shift", profession, requirementField.getText(), WeekNum, yearNum, superNum);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        try {
                            updateRequirement(day, "Night Shift", profession, requirementField.getText(), WeekNum, yearNum, superNum);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        try {
                            updateRequirement(day, "Night Shift", profession, requirementField.getText(), WeekNum, yearNum, superNum);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }

            dayShiftRow[i] = dayShiftPanel;
            nightShiftRow[i] = nightShiftPanel;
        }

        // Add the rows to the table model
        tableModel.addRow(dayShiftRow);
        tableModel.addRow(nightShiftRow);
    }

    // Update the requirement in your data using the provided day, shiftType, profession, count, WeekNum, yearNum, and superNum
    private void updateRequirement(int day, String shiftType, String profession, String requirement, int WeekNum, int yearNum, int superNum) throws SQLException {
        HRManagerService hr = new HRManagerService();
        String[] professions = {"manager", "cashier", "stock", "security", "cleaning", "shelf-stocking", "general-worker"};
        int index = -1;  // Default index if string is not found

        for (int i = 0; i < professions.length; i++) {
            if (professions[i].equals(profession)) {
                index = i;  // Update the index if the string is found
                break;
            }
        }
        String shift = "";
        if(shiftType.equals("Day shift")){
            shift = "day";
        }
        else{
            shift = "night";
        }

        hr.addreqtoweeklyshift(WeekNum,yearNum,superNum,day,shift,index, Integer.parseInt(requirement));
    }



    private Map<String, Integer> getProfessionCounts(int day,String shiftType, int WeekNum,int yearNum,int superNum, String[] professions) throws SQLException {
        HRManagerService hr = new HRManagerService();
        return hr.getProfessionCounts(day,shiftType,WeekNum,yearNum,superNum,professions);
    }

    public static void main(String[] args) throws SQLException {
        //just for my test:
        UIWeeklyShiftReq ws = new UIWeeklyShiftReq(10,1997,0);
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
