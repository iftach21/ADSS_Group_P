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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class UITransferManager extends JFrame{

    private JFrame frame;
    private TransferManagerService TransferManagerService = Service.TransferManagerService.getInstance();

    private JButton viewTransferDocumentButton;
    private JButton createTransferButton;
    private JButton updateCurrentTransfersButton;
    private JButton addTruckButton;
    private JButton viewPlannedTransfersButton;
    private JButton exitTransferSystemButton;

    private JCheckBox[] currTransfersCheckbox;

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

        updateCurrentTransfersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Map<Integer, List<String>> currTransfers = TransferManagerService.getDetailsOfCurrentTransfers();
                    currTransfersCheckbox = new JCheckBox[currTransfers.size()];
                    if (currTransfers.size() == 0)
                    {
                        JOptionPane.showMessageDialog(frame, "Sorry, there are no transfers that takes place currently.",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JPanel transfersPanel = new JPanel();
                    transfersPanel.add(new JLabel("These are the current transfer that takes place: "));
                    transfersPanel.setLayout(new GridLayout(0, 1));
                    int i = 0;
                    ButtonGroup bg = new ButtonGroup();
                    for (Integer transferId : currTransfers.keySet())
                    {
                        List<String> currTransferDetails = currTransfers.get(transferId);
                        currTransfersCheckbox[i] = new JCheckBox("Transfer ID: " + transferId + "   Source Site: " + currTransferDetails.get(0) + "   Last Destination: " + currTransferDetails.get(1));
                        transfersPanel.add(currTransfersCheckbox[i]);
                        bg.add(currTransfersCheckbox[i]);
                    }

                    transfersPanel.setVisible(true);
                    int option = JOptionPane.showOptionDialog(
                            null,
                            transfersPanel,
                            "Options",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            new Object[]{"OK"},
                            null
                    );

                    if (option == JOptionPane.OK_OPTION) {
                        int chosenTransferId = -1;
                        for(int j = 0; j < currTransfersCheckbox.length; j++)
                        {
                            if (currTransfersCheckbox[j].isSelected())
                            {
                                String input = currTransfersCheckbox[j].getText();
                                String prefix = "Transfer ID: ";
                                int startIndex = input.indexOf(prefix);

                                if (startIndex != -1) {
                                    int endIndex = input.indexOf(" ", startIndex + prefix.length());
                                    if (endIndex != -1) {
                                        chosenTransferId =  Integer.parseInt(input.substring(startIndex + prefix.length(), endIndex));
                                    } else {
                                        chosenTransferId = Integer.parseInt(input.substring(startIndex + prefix.length()));
                                    }
                                }
                                break;
                            }
                        }
                        showUpdateOptionsDialog(chosenTransferId);
                    }

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
                tableFrame.setSize(900,400);
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

    private void showUpdateOptionsDialog(int transferId) throws SQLException {
        JCheckBox updateWeightsCheckbox = new JCheckBox("Update truck weights");
        JCheckBox updateTimeCheckbox = new JCheckBox("Update arriving date and time");
        ButtonGroup bg = new ButtonGroup();
        bg.add(updateWeightsCheckbox);
        bg.add(updateTimeCheckbox);

        // Create a panel to hold the checkboxes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(updateWeightsCheckbox);
        panel.add(updateTimeCheckbox);
        panel.setVisible(true);
        int option = JOptionPane.showOptionDialog(
                null,
                panel,
                "Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"OK"},
                null
        );
        if (option == JOptionPane.OK_OPTION) {
            if (updateWeightsCheckbox.isSelected()) {
                UIUpdateWeights toUpdate = new UIUpdateWeights(transferId);
            }
            if (updateTimeCheckbox.isSelected()) {
                showChangeTimeDialog(transferId);
            }
        }
    }

    private void showChangeTimeDialog(int transferId) {
        JFrame dateFrame = new JFrame();
        dateFrame.setSize(570, 250);
        dateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dateFrame.setLayout(null);

        JLabel label = new JLabel("Choose Arriving Date:");
        JTextField dateSelected = new JTextField(20);
        JButton b = new JButton("Open Calender");
        JPanel p = new JPanel();
        p.add(label);
        p.add(dateSelected);
        p.add(b);
        p.setBounds(20, 50, 500, 40);
        dateFrame.add(p);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateSelected.setText(new DatePicker(dateFrame).setPickedDate());
            }
        });

        //Add time picker
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
        spinnerDateModel.setCalendarField(Calendar.MINUTE); // Set the field to modify (in this case, minutes)
        JSpinner spinner = new JSpinner(spinnerDateModel);

        // Set a custom date format for the spinner
        JSpinner.DateEditor spinnerEditor = new JSpinner.DateEditor(spinner, "HH:mm");
        SimpleDateFormat dateFormat = ((SimpleDateFormat) spinnerEditor.getFormat());
        dateFormat.setLenient(false); // Enforce strict parsing of time format
        spinner.setEditor(spinnerEditor);

        final JLabel selectTimeLabel=new JLabel();
        selectTimeLabel.setText("Please choose transfer arriving time: ");
        selectTimeLabel.setBounds(50, 100, 250, 35);
        dateFrame.add(selectTimeLabel);

        spinner.setBounds(330, 107, 100, 20);
        dateFrame.add(spinner);

        JButton okButton = new JButton("OK");
        okButton.setBounds(180, 160, 100, 20);
        dateFrame.add(okButton);

        dateFrame.setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if Legal
                try {

                    int lastDest = TransferManagerService.getLastDestinationId(transferId);
                    //Check if Date and time are legal
                    //Time parser to LocalTime
                    Date selectedDate = (Date) spinner.getValue();
                    java.time.Instant instant = selectedDate.toInstant();
                    // Convert Instant to LocalTime
                    LocalTime arrivingTime = instant.atZone(ZoneId.systemDefault()).toLocalTime();

                    //Date parser to LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate arrivingDate = LocalDate.parse(dateSelected.getText(), formatter);
                    if (!TransferManagerService.checkIfDateIsLegal(arrivingDate, arrivingTime)) {
                        JOptionPane.showMessageDialog(dateFrame, "Your Date is not Legal! Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (!TransferManagerService.checkIfStoreKeeperIsThereByArrivingTime(lastDest, arrivingTime, arrivingDate)) {
                        JOptionPane.showMessageDialog(dateFrame, "There is no storekeeper in the final destination. Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        dateFrame.dispose();
                        TransferManagerService.updateArrivingDateTime(transferId, arrivingTime, arrivingDate);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dateFrame, "One of your inputs is illegal or There is no storekeeper in the final destination. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
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
