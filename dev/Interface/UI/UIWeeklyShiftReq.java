package Interface.UI;

import Service.HRManagerService;

import javax.swing.*;
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

    public UIWeeklyShiftReq(int WeekNum,int yearNum,int superNum, boolean viewOnly) throws SQLException {
        frame = new JFrame("Weekly Shift");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();

        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new WeeklyShiftCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(900, 450);
        frame.setVisible(true);

        createWeeklyShift(WeekNum,yearNum,superNum,viewOnly);
    }

    public UIWeeklyShiftReq(int WeekNum, int yearNum, int superNum) throws SQLException {
        this(WeekNum,yearNum,superNum,false);
    }

    private void createWeeklyShift(int WeekNum, int yearNum, int superNum, boolean viewOnly) throws SQLException {
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
            int day = i-1;

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

        // Create the edit button
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            // Get the selected cell coordinates
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            // Check if a valid cell is selected
            if (selectedRow != -1 && selectedColumn != -1) {
                // Create the input fields for arguments
                JComboBox<String> professionComboBox = new JComboBox<>(professions);
                JTextField requirementField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(2, 2));
                inputPanel.add(new JLabel("Profession:"));
                inputPanel.add(professionComboBox);
                inputPanel.add(new JLabel("Requirement Count:"));
                inputPanel.add(requirementField);

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Edit Requirement",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If the user clicked "OK"
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the input fields
                    String profession = (String) professionComboBox.getSelectedItem();
                    String requirementStr = requirementField.getText();
                    int requirement = Integer.parseInt(requirementStr);

                    // Update the table model with the new requirement count
                    tableModel.setValueAt(requirement, selectedRow, selectedColumn);
                    String shiftType = selectedRow == 0 ? "Day Shift" : "Night Shift";

                    // Update the requirement in your data using the selected day, shiftType, and profession
                    try {
                        updateRequirement(selectedColumn, shiftType, profession, requirementStr, WeekNum, yearNum, superNum);
                        JOptionPane.showMessageDialog(frame, "Requirement updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Reload the screen to see the updated data
                        reloadScreen(WeekNum, yearNum, superNum);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        // Add the edit button to the panel only if it's not view only
        if(! viewOnly) {
            panel.add(editButton, BorderLayout.SOUTH);
        }
    }
    private void reloadScreen(int WeekNum, int yearNum, int superNum) throws SQLException {
        // Clear the table model
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Call the createWeeklyShift method again to populate the table with updated data
        createWeeklyShift(WeekNum, yearNum, superNum,false);
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
        if(shiftType.equals("Day Shift")){
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
        UIWeeklyShiftReq ws = new UIWeeklyShiftReq(1,2024,4);
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
