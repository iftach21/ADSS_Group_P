package Interface.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UIStoreManager extends JFrame implements ActionListener {
    private JButton employeeButton;
    private JButton hrManagerButton;
    private JButton transferManagerButton;

    public UIStoreManager() {
        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("UIStoreManager");
        setLayout(new GridLayout(3, 1));

        // Create the buttons
        employeeButton = new JButton("Employee");
        hrManagerButton = new JButton("HRManager");
        transferManagerButton = new JButton("TransferManager");

        // Set button properties
        employeeButton.addActionListener(this);
        hrManagerButton.addActionListener(this);
        transferManagerButton.addActionListener(this);
        employeeButton.setPreferredSize(new Dimension(200, 50));
        hrManagerButton.setPreferredSize(new Dimension(200, 50));
        transferManagerButton.setPreferredSize(new Dimension(200, 50));

        // Add buttons to the JFrame
        add(employeeButton);
        add(hrManagerButton);
        add(transferManagerButton);

        // Set JFrame properties
        pack();
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == employeeButton) {
            // Call the function for Employee
            try {
                handleEmployee();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == hrManagerButton) {
            // Call the function for HRManager
            try {
                handleHRManager();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == transferManagerButton) {
            // Call the function for TransferManager
            try {
                handleTransferManager();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Insert your function implementations here
    private void handleEmployee() throws SQLException {
        UIEmployee employeeUI = new UIEmployee();
        employeeUI.setVisible(true);
    }

    private void handleHRManager() throws SQLException{
        UIHRManager ui = new UIHRManager();
    }

    private void handleTransferManager() throws SQLException {
        UITransferManager ui = new UITransferManager();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UIStoreManager());
    }
}

