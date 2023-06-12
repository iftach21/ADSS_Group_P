package Interface.UI;

import Service.TransferManagerService;

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

public class UITransferManager extends JFrame{

    private JTextField idField;
    private JButton loginButton;
    private TransferManagerService TransferManagerService;

    private JButton viewTransferDocumentButton;
    private JButton createTransferButton;
    private JButton updateCurrentTransfersButton;
    private JButton addTruckButton;
    private JButton viewPlannedTransfersButton;
    private JButton exitTransferSystemButton;

    private String loggedInId;

    public UITransferManager() throws SQLException {
        TransferManagerService = new TransferManagerService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Transfer Manager Interface");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create components
        //JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(6, 1));

        viewTransferDocumentButton = new JButton("View and download a transfer document of your choice");
        createTransferButton = new JButton("Create transfer for pending orders"); //todo : add pending orders number
        updateCurrentTransfersButton = new JButton("Update current transfers");
        addTruckButton = new JButton("Add a new truck to the system");
        viewPlannedTransfersButton = new JButton("View planned transfers");
        exitTransferSystemButton = new JButton("Exit the transfer system");

        //start transfer manager interface
        startTransferManagerUI();

        // Set the panel as the content pane
        //setContentPane(panel);
    }

    private void startTransferManagerUI() {
        // Update UI for employee interface
        getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        panel.add(viewTransferDocumentButton);
        panel.add(createTransferButton);
        panel.add(updateCurrentTransfersButton);
        panel.add(addTruckButton);
        panel.add(viewPlannedTransfersButton);
        panel.add(exitTransferSystemButton);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UITransferManager transferManagerUI = null;
                try {
                    transferManagerUI = new UITransferManager();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                transferManagerUI.setVisible(true);
            }
        });
    }
}
