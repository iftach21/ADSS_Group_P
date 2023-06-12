package Interface.UI;

import Service.HRManagerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class UIStoreManager extends JFrame {
    private HRManagerService controller = new HRManagerService();
    private JButton btnListOfEmployees;
    private JButton btnWriteEventToShift;
    private JButton btnGetEventsFromShifts;
    private JButton btnGetEmployeesForWeeklyShift;

    public UIStoreManager() throws SQLException {
        setTitle("Store Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); // Use GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some spacing

        btnListOfEmployees = new JButton("List of all employees");
        btnWriteEventToShift = new JButton("Write event to Shift");
        btnGetEventsFromShifts = new JButton("Get events from Shifts");
        btnGetEmployeesForWeeklyShift = new JButton("Get employees for WeeklyShift");

        btnListOfEmployees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listOfEmployeesClicked();
            }
        });

        btnWriteEventToShift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    writeEventToShiftClicked();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnGetEventsFromShifts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    getEventsFromShiftsClicked();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnGetEmployeesForWeeklyShift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    getEmployeesForWeeklyShiftClicked();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        gbc.gridy = 0;
        add(btnListOfEmployees, gbc);

        gbc.gridy = 1;
        add(btnWriteEventToShift, gbc);

        gbc.gridy = 2;
        add(btnGetEventsFromShifts, gbc);

        gbc.gridy = 3;
        add(btnGetEmployeesForWeeklyShift, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void listOfEmployeesClicked() {
        String text = "This is the list of all employees:\n"; // Replace with your actual text
        text += controller.getAllworkersString();

        // Define the buttons
        Object[] options = { "OK", "Save to file" };

        // Show option dialog box with the text and buttons
        int choice = JOptionPane.showOptionDialog(this, text, "List of Employees", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 1) { // User selected "Save"
            // Save text to a file
            JFileChooser fileChooser = new JFileChooser();
            int userOption = fileChooser.showSaveDialog(this);

            if (userOption == JFileChooser.APPROVE_OPTION) {
                try {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(text);
                    fileWriter.close();
                    JOptionPane.showMessageDialog(this, "File saved successfully!", "File Saved",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }



    private void writeEventToShiftClicked() throws SQLException {
        String weeknumStr = JOptionPane.showInputDialog(this, "Enter week number:");
        String yearStr = JOptionPane.showInputDialog(this, "Enter year:");
        String supernumStr = JOptionPane.showInputDialog(this, "Enter super number:");
        String daynumStr = JOptionPane.showInputDialog(this, "Enter day number:");
        String dayornight = JOptionPane.showInputDialog(this, "Enter day or night:");
        String report = JOptionPane.showInputDialog(this, "Enter report:");

        // Parse the input to integers
        int weeknum = Integer.parseInt(weeknumStr);
        int year = Integer.parseInt(yearStr);
        int supernum = Integer.parseInt(supernumStr);
        int daynum = Integer.parseInt(daynumStr);


        controller.writeToEventOfShift(weeknum, year, supernum, daynum, dayornight,report);

        JOptionPane.showMessageDialog(this, "report saved successfully!", "report adding",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void getEventsFromShiftsClicked() throws SQLException {
        String text = "This is the events in the shift:\n"; // Replace with your actual text
        // Prompt the user for input
        String weeknumStr = JOptionPane.showInputDialog(this, "Enter week number:");
        String yearStr = JOptionPane.showInputDialog(this, "Enter year:");
        String supernumStr = JOptionPane.showInputDialog(this, "Enter super number:");
        String daynumStr = JOptionPane.showInputDialog(this, "Enter day number:");
        String dayornight = JOptionPane.showInputDialog(this, "Enter day or night:");

        // Parse the input to integers
        int weeknum = Integer.parseInt(weeknumStr);
        int year = Integer.parseInt(yearStr);
        int supernum = Integer.parseInt(supernumStr);
        int daynum = Integer.parseInt(daynumStr);

        // Call the function and get the result
        String result = controller.getEventOfShift(weeknum, year, supernum, daynum, dayornight);

        text += result;

        // Define the buttons
        Object[] options = { "OK", "Save to file" };

        // Show option dialog box with the text and buttons
        int choice = JOptionPane.showOptionDialog(this, text, "This is the events in the shift:", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 1) { // User selected "Save"
            // Save text to a file
            JFileChooser fileChooser = new JFileChooser();
            int userOption = fileChooser.showSaveDialog(this);

            if (userOption == JFileChooser.APPROVE_OPTION) {
                try {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(text);
                    fileWriter.close();
                    JOptionPane.showMessageDialog(this, "File saved successfully!", "File Saved",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void getEmployeesForWeeklyShiftClicked() throws SQLException {
        String text = "This is the events in the shift:\n"; // Replace with your actual text
        // Prompt the user for input
        String weeknumStr = JOptionPane.showInputDialog(this, "Enter week number:");
        String yearStr = JOptionPane.showInputDialog(this, "Enter year:");
        String supernumStr = JOptionPane.showInputDialog(this, "Enter super number:");


        // Parse the input to integers
        int weeknum = Integer.parseInt(weeknumStr);
        int year = Integer.parseInt(yearStr);
        int supernum = Integer.parseInt(supernumStr);

        // Call the function and get the result
        String result = controller.getAllWorkersThatWorkedInSpecificWeek(weeknum, year, supernum);

        text += result;

        // Define the buttons
        Object[] options = { "OK", "Save to file" };

        // Show option dialog box with the text and buttons
        int choice = JOptionPane.showOptionDialog(this, text, "This is the events in the shift:", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 1) { // User selected "Save"
            // Save text to a file
            JFileChooser fileChooser = new JFileChooser();
            int userOption = fileChooser.showSaveDialog(this);

            if (userOption == JFileChooser.APPROVE_OPTION) {
                try {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(text);
                    fileWriter.close();
                    JOptionPane.showMessageDialog(this, "File saved successfully!", "File Saved",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UIStoreManager();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
