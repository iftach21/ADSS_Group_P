package Interface.UI;

import Service.TransferManagerService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UICreateTransfer {
    private JFrame Transferframe;
    private TransferManagerService TransferManagerService;

    public UICreateTransfer() throws SQLException {
        TransferManagerService = new TransferManagerService();
        initializeUI();
    }

    private void initializeUI() {
        Transferframe = new JFrame("Transfer Manager");
        Transferframe.setSize(500, 500);
        Transferframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Transferframe.setLayout(null);
        final JTextField tf=new JTextField();
        // Create components
        tf.setText("Please Enter the following details for the transfer:");
        tf.setBounds(0, 0, 650, 100);
        Transferframe.add(tf);






        //start transfer manager interface
        //startTransferManagerUI();

        // Set the panel as the content pane
        //setContentPane(panel);
    }
}
