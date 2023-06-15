package Interface.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            handleEmployee();
        } else if (e.getSource() == hrManagerButton) {
            // Call the function for HRManager
            handleHRManager();
        } else if (e.getSource() == transferManagerButton) {
            // Call the function for TransferManager
            handleTransferManager();
        }
    }

    // Insert your function implementations here
    private void handleEmployee() {
        // Function implementation for Employee
        System.out.println("Employee selected");
    }

    private void handleHRManager() {
        // Function implementation for HRManager
        System.out.println("HRManager selected");
    }

    private void handleTransferManager() {
        // Function implementation for TransferManager
        System.out.println("TransferManager selected");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UIStoreManager());
    }
}

