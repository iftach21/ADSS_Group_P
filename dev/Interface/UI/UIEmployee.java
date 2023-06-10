package Interface.UI;

import Service.HRManagerService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UIEmployee extends JFrame {
    private JTextField idField;
    private JButton loginButton;
    private HRManagerService HRManagerService;

    private JButton viewWeeklyReqButton;
    private JButton updateButton;
    private JButton showWeeklyIncomeButton;

    private Map<String, Double> weeklyIncomeMap;
    private String loggedInId;

    public UIEmployee() throws SQLException {
        HRManagerService = new HRManagerService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Employee Interface");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, idField.getPreferredSize().height)); // Set preferred width
        loginButton = new JButton("Log In");
        loginButton.setPreferredSize(new Dimension(100, loginButton.getPreferredSize().height)); // Set preferred size for the button

        // Add padding to the button
        int padding = 10; // Adjust the padding value as needed
        loginButton.setBorder(new EmptyBorder(padding, padding, padding, padding));

        viewWeeklyReqButton = new JButton("View Weekly Requests");
        updateButton = new JButton("Update When Available");
        showWeeklyIncomeButton = new JButton("Show My Weekly Income");

        // Create ID panel
        JPanel idPanel = new JPanel();
        idPanel.add(new JLabel("Enter ID:"));
        idPanel.add(idField);

        // Add components to the main panel
        panel.add(idPanel);
        panel.add(loginButton);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                if (!id.isEmpty()) {
                    loggedInId = id;
                    showEmployeeInterface();
                } else {
                    JOptionPane.showMessageDialog(UIEmployee.this, "Please enter your ID.");
                }
            }
        });

        // Set the panel as the content pane
        setContentPane(panel);
    }

    private void showEmployeeInterface() {
        // Update UI for employee interface
        getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        panel.add(viewWeeklyReqButton);
        panel.add(updateButton);
        panel.add(showWeeklyIncomeButton);

        viewWeeklyReqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField weekNumberField = new JTextField();
                JTextField yearNumberField = new JTextField();
                JTextField superNumberField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Week Number:"));
                inputPanel.add(weekNumberField);
                inputPanel.add(new JLabel("Year Number:"));
                inputPanel.add(yearNumberField);
                inputPanel.add(new JLabel("Super Number:"));
                inputPanel.add(superNumberField);

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(panel, inputPanel, "Enter Weekly Shift Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If the user clicked "OK"
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the input fields
                    int weekNum = Integer.parseInt(weekNumberField.getText());
                    int yearNum = Integer.parseInt(yearNumberField.getText());
                    int superNum = Integer.parseInt(superNumberField.getText());

                    // Create an instance of UIWeeklyShiftReq with the provided input values
                    try {
                        UIWeeklyShiftReq weeklyShiftReq = new UIWeeklyShiftReq(weekNum, yearNum, superNum, true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddavilableforemployeeDialog();
            }
        });

        showWeeklyIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a dialog to get the week number, year number, and super number
                JTextField weekField = new JTextField();
                JTextField yearField = new JTextField();

                JPanel panel = new JPanel(new GridLayout(3, 2));
                panel.add(new JLabel("Week Number:"));
                panel.add(weekField);
                panel.add(new JLabel("Year Number:"));
                panel.add(yearField);

                int result = JOptionPane.showConfirmDialog(UIEmployee.this, panel, "Enter Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Get the input values
                    int weekNumber = Integer.parseInt(weekField.getText().trim());
                    int yearNumber = Integer.parseInt(yearField.getText().trim());

                    // Call the function with the input values
                    int income = getWeeklyIncome(weekNumber, yearNumber);
                    JOptionPane.showMessageDialog(UIEmployee.this, "Your weekly income is: $" + income);
                }
            }
        });

        setContentPane(panel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIEmployee employeeUI = null;
                try {
                    employeeUI = new UIEmployee();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employeeUI.setVisible(true);
            }
        });
    }

    private void showAddavilableforemployeeDialog() {
        final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        final String[] NORD_OPTIONS = {"night", "day"};
        JTextField idField = new JTextField();
        JComboBox<String> daynumCombo = new JComboBox<>(DAYS_OF_WEEK);
        JComboBox<String> nordCombo = new JComboBox<>(NORD_OPTIONS);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Day of the week:"));
        panel.add(daynumCombo);
        panel.add(new JLabel("Night or Day:"));
        panel.add(nordCombo);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Available for Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Added Available for Employee!");
            // Get the values from the text field and combo boxes
            int id = Integer.parseInt(this.loggedInId);
            // Use the index of the selected day
            int daynum = daynumCombo.getSelectedIndex() + 1;
            String nordInfo = nordCombo.getSelectedItem().toString();

            // Call the addEmployee function with the gathered input
            HRManagerService.addavilableforemployee(id, daynum, nordInfo);
        }
    }

    private int getWeeklyIncome(int weekNum, int yearNum) {
        return HRManagerService.getwageforemployee(Integer.parseInt(this.loggedInId), weekNum, yearNum);
    }

}
