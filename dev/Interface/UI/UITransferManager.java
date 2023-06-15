package Interface.UI;

import Domain.Transfer.Transfer;
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
import java.util.List;
import java.util.Map;

public class UITransferManager extends JFrame{

    private JFrame frame;
    private TransferManagerService TransferManagerService = Service.TransferManagerService.getInstance();;

    private JButton viewTransferDocumentButton;
    private JButton createTransferButton;
    private JButton updateCurrentTransfersButton;
    private JButton addTruckButton;
    private JButton viewPlannedTransfersButton;
    private JButton exitTransferSystemButton;

    public UITransferManager() throws SQLException {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Transfer Manager");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        final JLabel tf=new JLabel();
        tf.setText("Hello transfer manager, nice to have you back. What would you like to do?");
        tf.setBounds(25, 0, 650, 100);
        frame.add(tf);

        viewTransferDocumentButton = new JButton("View and download a transfer document of your choice");
        viewTransferDocumentButton.setBounds(40,100,410, 50);
        createTransferButton = new JButton("Create transfer for pending order. You have " + TransferManagerService.numOfOrders() + " orders to handle");
        createTransferButton.setBounds(40,150,410, 50);
        updateCurrentTransfersButton = new JButton("Update current transfers");
        updateCurrentTransfersButton.setBounds(40,200,410, 50);
        addTruckButton = new JButton("Add a new truck to the system");
        addTruckButton.setBounds(40,250,410, 50);
        viewPlannedTransfersButton = new JButton("View planned transfers");
        viewPlannedTransfersButton.setBounds(40,300,410, 50);
        exitTransferSystemButton = new JButton("Exit the transfer system");
        exitTransferSystemButton.setBounds(40,350,410, 50);

        frame.add(viewTransferDocumentButton);
        frame.add(createTransferButton);
        frame.add(updateCurrentTransfersButton);
        frame.add(addTruckButton);
        frame.add(viewPlannedTransfersButton);
        frame.add(exitTransferSystemButton);

        //start transfer manager interface
        startTransferManagerUI();

        // Set the panel as the content pane
        //setContentPane(panel);
    }

    private void startTransferManagerUI() {
        // Update UI for employee interface
        frame.setVisible(true);

        viewTransferDocumentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField transferIdField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(1, 2));
                inputPanel.add(new JLabel("Transfer ID:"));
                inputPanel.add(transferIdField);

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Transfer ID:",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If the user clicked "OK"
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the input fields
                    int transferId = Integer.parseInt(transferIdField.getText());

                    // Create an instance of UIWeeklyShiftReq with the provided input values
                    try {
                        if (TransferManagerService.isValidTransfer(transferId))
                        {
                            TransferManagerService.createDocument(transferId);
                            JOptionPane.showMessageDialog(frame,"Transfer Document has been downloaded");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame, "Transfer ID is not Legal! You'll be taken to the main menu.",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        createTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UICreateTransfer uiCreateTransfer = new UICreateTransfer();
                    createTransferButton.setText("Create transfer for pending order. You have " + TransferManagerService.numOfOrders() + " orders to handle");
                }
                catch (Exception ex)
                {
                    throw new RuntimeException();
                }
            }
        });

        addTruckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField licenseNumberField = new JTextField();
                JTextField truckModelField = new JTextField();
                JTextField netWeightField = new JTextField();
                JTextField maxWeightField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(0, 2));
                inputPanel.add(new JLabel("License Number:"));
                inputPanel.add(licenseNumberField);
                inputPanel.add(new JLabel("Truck Model:"));
                inputPanel.add(truckModelField);
                inputPanel.add(new JLabel("Net Weight:"));
                inputPanel.add(netWeightField);
                inputPanel.add(new JLabel("Max Weight:"));
                inputPanel.add(maxWeightField);

                JCheckBox regularCheckBox = new JCheckBox("Regular");
                JCheckBox coldCheckBox = new JCheckBox("Cold");
                JCheckBox frozenCheckBox = new JCheckBox("Frozen");
                ButtonGroup buttonGroup = new ButtonGroup();
                buttonGroup.add(regularCheckBox);
                buttonGroup.add(coldCheckBox);
                buttonGroup.add(frozenCheckBox);
                JPanel headlinePanel = new JPanel();
                headlinePanel.setLayout(new BoxLayout(headlinePanel, BoxLayout.Y_AXIS));
                JLabel headlineLabel = new JLabel("Select Truck Temperature Level: ");
                headlinePanel.add(headlineLabel);
                headlinePanel.add(regularCheckBox);
                headlinePanel.add(coldCheckBox);
                headlinePanel.add(frozenCheckBox);

                inputPanel.add(headlinePanel);

                final Integer[] index = {0};

                // ActionListener for checkbox 1
                regularCheckBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (regularCheckBox.isSelected())
                            index[0] = 1;
                    }
                });

                // ActionListener for checkbox 2
                coldCheckBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (coldCheckBox.isSelected())
                            index[0] = 2;
                    }
                });

                // ActionListener for checkbox 3
                frozenCheckBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (coldCheckBox.isSelected())
                            index[0] = 3;
                    }
                });

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Truck Details: ",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                int licenseNumber = -1;
                String model = "";
                int netWeight = -1;
                int maxWeight = -1;

                // Create an instance of UIWeeklyShiftReq with the provided input values
                try {
                    // If the user clicked "OK"
                    if (result == JOptionPane.OK_OPTION) {
                        // Get the values from the input fields
                        licenseNumber = Integer.parseInt(licenseNumberField.getText());
                        model = truckModelField.getText();
                        netWeight = Integer.parseInt(netWeightField.getText());
                        maxWeight = Integer.parseInt(maxWeightField.getText());

                        if (licenseNumber < 0 || licenseNumber > 99999999)
                        {
                            JOptionPane.showMessageDialog(frame, "License Number is not Legal! You'll be taken to the main menu.",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (netWeight < 0 || netWeight > 60)
                        {
                            JOptionPane.showMessageDialog(frame, "Net Weight is not Legal! You'll be taken to the main menu.",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (maxWeight < 0 || maxWeight > 60 && maxWeight < netWeight)
                        {
                            JOptionPane.showMessageDialog(frame, "Max Weight is not Legal! You'll be taken to the main menu.",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            TransferManagerService.initializeAndAddNewTruck(licenseNumber, model, netWeight, maxWeight, index[0]);
                            JOptionPane.showMessageDialog(frame, "Truck added successfully to the system!");
                        }
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Your input is illegal! You'll be taken to the main menu.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);;
                }
            }
        });

        viewPlannedTransfersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame = new JFrame("Planned transfers table");
                Map<Integer, List<String>> plannedTransferDetails = TransferManagerService.getDetailsForPlannedTransfers();
                String data[][]= new String[plannedTransferDetails.size()][7];
                int i=0;
                for(Integer transferId: plannedTransferDetails.keySet())
                {
                    for(int j=0; j<7; j++)
                    {
                        if(j==0)
                        {
                            data[i][j] = transferId+"";
                        }
                        else {
                            data[i][j] = plannedTransferDetails.get(transferId).get(j);
                        }
                    }
                    i++;
                }
                String column[]={"ID","Source Site","Last Destination", "Leaving Date", "Leaving Time", "Arriving Date", "Arriving Time"};
                JTable jt=new JTable(data,column);
                JScrollPane sp=new JScrollPane(jt);
                tableFrame.add(sp);
                tableFrame.setSize(600,400);
                tableFrame.setVisible(true);
            }
        });

        exitTransferSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
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
            }
        });
    }
}
