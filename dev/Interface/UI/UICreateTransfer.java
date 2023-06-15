package Interface.UI;

import Service.TransferManagerService;
import javax.swing.*;
import javax.swing.Timer;
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


public class UICreateTransfer {
    private static final int DELAY = 3000;
    private JFrame Transferframe;
    private JFrame chooseDriverFrame;
    private JFrame TransferStartFrame;
    private TransferManagerService TransferManagerService = Service.TransferManagerService.getInstance();
    private JTextField dateSelected;
    private JButton nextButton;
    private JSpinner spinner;
    private JTextField siteIdField;
    private Map<Integer, String> sites;
    private LocalTime leavingTime;
    private LocalDate leavingDate;
    private int sourceId;
    private int transferId;
    JButton[] checkWeightButtons;
    JButton[] siteDetailsButtons;


    public UICreateTransfer() throws SQLException {
        sites = TransferManagerService.getOrderSitesNames();
        initializeUI();
    }

    private void initializeUI() {
        Transferframe = new JFrame("Create a transfer");
        Transferframe.setSize(500, 430);
        Transferframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Transferframe.setLayout(null);

        //Add components
        //Add headline
        final JLabel mainHeadline=new JLabel();
        mainHeadline.setText("Please enter the following details: ");
        mainHeadline.setBounds(20, 0, 650, 50);
        Transferframe.add(mainHeadline);

        //Add checkboxes for source sites
        final JLabel chooseSourceHeadline=new JLabel();
        chooseSourceHeadline.setText("Please choose source from the following options: ");
        chooseSourceHeadline.setBounds(20, 30, 650, 50);
        Transferframe.add(chooseSourceHeadline);

        JLabel[] sitesHeadlines = new JLabel[sites.size()];
        int index = 0;
        for (Integer siteId : sites.keySet())
        {
            sitesHeadlines[index] = new JLabel((index + 1) + ". ID: " + siteId + "   Site Name: " + sites.get(siteId));
            sitesHeadlines[index].setBounds(20, 80 + (index * 20), 650, 20);
            Transferframe.add(sitesHeadlines[index]);
            index++;
            if (index == 5)
                break;
        }

        siteIdField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Site ID:"));
        inputPanel.add(siteIdField);
        inputPanel.setBounds(20, 150, 100, 20);
        Transferframe.add(inputPanel);

        //Add date picker
        final JLabel selectDateLabel=new JLabel();
        selectDateLabel.setText("Please choose transfer leaving date: ");
        selectDateLabel.setBounds(20, 180, 650, 35);
        Transferframe.add(selectDateLabel);

        JLabel label = new JLabel("Selected Date:");
        dateSelected = new JTextField(20);
        JButton b = new JButton("Open Calender");
        JPanel p = new JPanel();
        p.add(label);
        p.add(dateSelected);
        p.add(b);
        p.setBounds(-110, 215, 650, 40);
        Transferframe.add(p);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateSelected.setText(new DatePicker(Transferframe).setPickedDate());
            }
        });

        //Add time picker
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
        spinnerDateModel.setCalendarField(Calendar.MINUTE); // Set the field to modify (in this case, minutes)
        spinner = new JSpinner(spinnerDateModel);

        // Set a custom date format for the spinner
        JSpinner.DateEditor spinnerEditor = new JSpinner.DateEditor(spinner, "HH:mm");
        SimpleDateFormat dateFormat = ((SimpleDateFormat) spinnerEditor.getFormat());
        dateFormat.setLenient(false); // Enforce strict parsing of time format
        spinner.setEditor(spinnerEditor);

        final JLabel selectTimeLabel=new JLabel();
        selectTimeLabel.setText("Please choose transfer leaving time: ");
        selectTimeLabel.setBounds(20, 255, 650, 35);
        Transferframe.add(selectTimeLabel);

        spinner.setBounds(20, 290, 100, 20);
        Transferframe.add(spinner);

        //Add button to check the desired details entered
        nextButton = new JButton("NEXT");
        nextButton.setBounds(200,310,100, 40);
        Transferframe.add(nextButton);

        //start transfer manager interface
        startCreateTransferUI();

    }

    private void startCreateTransferUI() {
        Transferframe.setVisible(true);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if Legal
                try {
                    //Get the siteId chosen
                    sourceId = Integer.parseInt(siteIdField.getText());

                    //Check if Date and time are legal
                    //Time parser to LocalTime
                    Date selectedDate = (Date) spinner.getValue();
                    java.time.Instant instant = selectedDate.toInstant();
                    // Convert Instant to LocalTime
                    leavingTime = instant.atZone(ZoneId.systemDefault()).toLocalTime();

                    //Date parser to LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    leavingDate = LocalDate.parse(dateSelected.getText(), formatter);
                    if (!sites.keySet().contains(sourceId)) {
                        JOptionPane.showMessageDialog(Transferframe, "Site ID entered is not legal",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else if (!TransferManagerService.checkIfDateIsLegal(leavingDate, leavingTime)) {
                        JOptionPane.showMessageDialog(Transferframe, "Your Date is not Legal! Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else if (!TransferManagerService.checkIfStoreKeeperIsThere(sourceId, leavingTime, leavingDate)) {
                        JOptionPane.showMessageDialog(Transferframe, "There is no storekeeper in the final destination. Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        Transferframe.dispose();
                        showDriverFrame();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Transferframe, "One of your inputs is illegal. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showDriverFrame() throws SQLException {
        chooseDriverFrame = new JFrame("Choose Driver");
        chooseDriverFrame.setSize(700, 430);
        chooseDriverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseDriverFrame.setLayout(null);
        chooseDriverFrame.setVisible(true);

        final JLabel mainHeadline=new JLabel();
        mainHeadline.setText("Here are the available drivers for these dates. Please choose one of them: ");
        mainHeadline.setBounds(20, 0, 650, 50);
        chooseDriverFrame.add(mainHeadline);

        Map<Integer, List<String>> availableDrivers;
        try {
            availableDrivers = TransferManagerService.findDriversDetailsForTransfer(leavingDate, leavingTime);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(Transferframe, "There are no drivers to this transfer. You'll be taken to the main menu",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            chooseDriverFrame.dispose();
            return;
        }

        JLabel[] driversHeadlines = new JLabel[availableDrivers.size()];
        int index = 0;
        for (Integer driverId : availableDrivers.keySet())
        {
            driversHeadlines[index] = new JLabel((index + 1) + ". ID: " + driverId + "   Driver Name: " + availableDrivers.get(driverId).get(0) + "   License Weight Capacity: " + availableDrivers.get(driverId).get(1) + "   License Temp Capacity: " + availableDrivers.get(driverId).get(2));
            driversHeadlines[index].setBounds(20, 80 + (index * 20), 650, 20);
            chooseDriverFrame.add(driversHeadlines[index]);
            index++;
            if (index == 5)
                break;
        }

        JButton selectButton = new JButton("Select Driver");
        selectButton.setBounds(250, 350, 150, 40);
        chooseDriverFrame.add(selectButton);
        JTextField driverIdField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Driver ID:"));
        inputPanel.add(driverIdField);
        inputPanel.setBounds(220, 300, 200, 20);
        chooseDriverFrame.add(inputPanel);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get the driverId
                try
                {
                    int driverId =  Integer.parseInt(driverIdField.getText());
                    if (availableDrivers.containsKey(driverId))
                    {
                        chooseDriverFrame.dispose();
                        transferId = TransferManagerService.initializeNewTransfer(leavingDate, leavingTime, driverId,sourceId);
                        showStartTransfer();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(Transferframe, "Driver Id doesnt exist. Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(Transferframe, "Your input is illegal. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showStartTransfer() {
        TransferStartFrame = new JFrame("Choose Weights");
        TransferStartFrame.setSize(700, 430);
        TransferStartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TransferStartFrame.setLayout(null);
        TransferStartFrame.setVisible(true);

        final JLabel mainHeadline=new JLabel();
        mainHeadline.setText("This is the truck chosen to this transfer: ");
        mainHeadline.setBounds(20, 0, 650, 50);
        TransferStartFrame.add(mainHeadline);

        //Print the details
        List<String> truckChosen = TransferManagerService.getChosenTruckDetails();
        final JLabel licenseNumHeadline=new JLabel();
        licenseNumHeadline.setText("License Number: " + truckChosen.get(0));
        licenseNumHeadline.setBounds(20, 50, 650, 20);
        TransferStartFrame.add(licenseNumHeadline);

        final JLabel modelNumHeadline=new JLabel();
        modelNumHeadline.setText("Truck model: " + truckChosen.get(1));
        modelNumHeadline.setBounds(20, 70, 650, 20);
        TransferStartFrame.add(modelNumHeadline);

        final JLabel maxWeightHeadline=new JLabel();
        maxWeightHeadline.setText("Truck max weight: " + truckChosen.get(2));
        maxWeightHeadline.setBounds(20, 90, 650, 20);
        TransferStartFrame.add(maxWeightHeadline);

        final JLabel tempCapacityHeadline=new JLabel();
        tempCapacityHeadline.setText("Truck temp capacity: " + truckChosen.get(3));
        tempCapacityHeadline.setBounds(20, 110, 650, 20);
        TransferStartFrame.add(tempCapacityHeadline);

        final JLabel weightAtSource=new JLabel();
        weightAtSource.setText("Your source site is: " + sites.get(sourceId) + ". Please enter the weight at this source: ");
        weightAtSource.setBounds(20, 150, 650, 20);
        TransferStartFrame.add(weightAtSource);

        //Add check weight for source button
        JButton checkWeightButton = new JButton("Check Weight");
        checkWeightButton.setBounds(230, 180, 150, 20);
        TransferStartFrame.add(checkWeightButton);

        //Add sourceDetails button
        JButton sourceDetailsButton = new JButton("Watch Items Picked");
        sourceDetailsButton.setBounds(390, 180, 150, 20);
        TransferStartFrame.add(sourceDetailsButton);

        //Add the source textfield for weight
        JTextField weightAtSourceField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));

        Map<Integer, Map<String, List<String>>> transferItems =  TransferManagerService.getAllSitesItemsDetails(transferId);

        //Create source items panel
        JPanel sourceDetailPanel = new JPanel(new GridLayout(0, 2));
        Map<String, List<String>> sourceItems = transferItems.get(sourceId);
        for (String catalogNum : sourceItems.keySet()) {
            String itemName = sourceItems.get(catalogNum).get(0);
            sourceDetailPanel.add(new JLabel("Item Name: " + itemName));
            String quantity = sourceItems.get(catalogNum).get(1);
            sourceDetailPanel.add(new JLabel("   Quantity: " + quantity));
        }

        inputPanel.add(new JLabel("Weight: "));
        inputPanel.add(weightAtSourceField);
        inputPanel.setBounds(20, 180, 180, 20);
        TransferStartFrame.add(inputPanel);

        //Get transfer destinations
        Map<Integer, String> destinations =  TransferManagerService.getTransferDestinationNames(transferId);
        //Create labels panels and buttons array for each destination
        JLabel[] destinationLabels = new JLabel[destinations.size()];
        checkWeightButtons = new JButton[destinations.size()];
        siteDetailsButtons = new JButton[destinations.size()];
        JTextField[] weightAtDestField = new JTextField[destinations.size()];
        JPanel[] inputPanels = new JPanel[destinations.size()];
        JPanel[] detailPanels = new JPanel[destinations.size()];
        int i = 0;
        List<Integer> destsList = destinations.keySet().stream().toList();
        for (Integer destId: destsList) {
            destinationLabels[i] = new JLabel();
            destinationLabels[i].setText("Please enter the weight at: " + destinations.get(destId));
            destinationLabels[i].setBounds(20, 210 + (i * 50), 650, 20);
            TransferStartFrame.add(destinationLabels[i]);
            checkWeightButtons[i] = new JButton("Check Weight");
            checkWeightButtons[i].setBounds(230, 240 + (i * 50), 150, 20);
            TransferStartFrame.add(checkWeightButtons[i]);
            siteDetailsButtons[i] = new JButton("Watch Items Picked");
            siteDetailsButtons[i].setBounds(390, 240 + (i * 50), 150, 20);
            TransferStartFrame.add(siteDetailsButtons[i]);
            weightAtDestField[i] = new JTextField();
            inputPanels[i] = new JPanel(new GridLayout(1, 2));
            inputPanels[i].add(new JLabel("Weight: "));
            inputPanels[i].add(weightAtDestField[i]);
            inputPanels[i].setBounds(20, 240 + (i * 50), 180, 20);
            TransferStartFrame.add(inputPanels[i]);

            //Create the details panel
            detailPanels[i] = new JPanel(new GridLayout(0, 2));
            Map<String, List<String>> destItems = transferItems.get(destId);
            for (String catalogNum : destItems.keySet()) {
                String itemName = destItems.get(catalogNum).get(0);
                detailPanels[i].add(new JLabel("Item Name: " + itemName));
                String quantity = destItems.get(catalogNum).get(1);
                detailPanels[i].add(new JLabel("   Quantity: " + quantity));
            }


            i++;
        }
        boolean[] allWeightsAreValid = new boolean[checkWeightButtons.length + 1];
        checkWeightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if Legal
                try {
                    //Get the siteId chosen
                    int weightAtSource = Integer.parseInt(weightAtSourceField.getText());

                    if (!TransferManagerService.updateTruckWeightAtSource(transferId, weightAtSource)) {
                        JOptionPane.showMessageDialog(TransferStartFrame, "Your truck is in overweight. You'll need to rearrange your transfer",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                        allWeightsAreValid[0] = false;
                        rearrangeTransferDialog();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(TransferStartFrame, "Weight is OK",
                                "Valid", JOptionPane.INFORMATION_MESSAGE);
                        allWeightsAreValid[0] = true;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TransferStartFrame, "One of your inputs is illegal. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sourceDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if Legal
                try {
                    String[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, sourceDetailPanel, "Items",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TransferStartFrame, "One of your inputs is illegal. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        for (int j = 0; j < checkWeightButtons.length; j++)
        {
            checkWeightButtons[j].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if Legal
                try {
                    // Get the button that was pressed
                    JButton sourceButton = (JButton) e.getSource();

                    // Find the index of the pressed button in the checkWeightButtons array
                    int pressedButtonIndex = -1;
                    for (int i = 0; i < checkWeightButtons.length; i++) {
                        if (checkWeightButtons[i] == sourceButton) {
                            pressedButtonIndex = i;
                            break;
                        }
                    }
                    //Get the siteId chosen
                    int weightAtDest = Integer.parseInt(weightAtDestField[pressedButtonIndex].getText());

                    if (!TransferManagerService.updatesWeightsAtDestination(transferId, weightAtDest, destsList.get(pressedButtonIndex))) {
                        JOptionPane.showMessageDialog(TransferStartFrame, "Your truck is in overweight. You'll need to rearrange your transfer",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                        allWeightsAreValid[pressedButtonIndex + 1] = false;
                        rearrangeTransferDialog();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(TransferStartFrame, "Weight is OK",
                                "Valid", JOptionPane.INFORMATION_MESSAGE);
                        allWeightsAreValid[pressedButtonIndex + 1] = true;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TransferStartFrame, "One of your inputs is illegal. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

            siteDetailsButtons[j].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Check if Legal
                    try {
                        // Get the button that was pressed
                        JButton sourceButton = (JButton) e.getSource();

                        // Find the index of the pressed button in the checkWeightButtons array
                        int pressedButtonIndex = -1;
                        for (int i = 0; i < siteDetailsButtons.length; i++) {
                            if (siteDetailsButtons[i] == sourceButton) {
                                pressedButtonIndex = i;
                                break;
                            }
                        }

                        String[] options = {"OK"};
                        JOptionPane.showOptionDialog(null, detailPanels[pressedButtonIndex], "Items",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TransferStartFrame, "One of your inputs is illegal. Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        JButton doneButton = new JButton("DONE");
        doneButton.setBounds(250, 350, 150, 40);
        TransferStartFrame.add(doneButton);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if Legal
                try {
                    for (int j = 0; j < allWeightsAreValid.length; j++)
                    {
                        if (!allWeightsAreValid[j])
                        {
                            JOptionPane.showMessageDialog(TransferStartFrame, "One of your weights is not good. Please try again",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(TransferStartFrame, "Transfer created successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    TransferStartFrame.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TransferStartFrame, "One of your inputs is illegal. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void rearrangeTransferDialog() throws SQLException {
        JCheckBox transferTruckCheckbox = new JCheckBox("Change transfer truck");
        JCheckBox destinationsCheckbox = new JCheckBox("Change transfer destinations");
        JCheckBox itemsCheckbox = new JCheckBox("Reduce transfer items");
        ButtonGroup bg = new ButtonGroup();
        bg.add(transferTruckCheckbox);
        bg.add(destinationsCheckbox);
        bg.add(itemsCheckbox);

        // Create a panel to hold the checkboxes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(transferTruckCheckbox);
        panel.add(destinationsCheckbox);
        panel.add(itemsCheckbox);
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
            if (transferTruckCheckbox.isSelected()) {
                TransferStartFrame.dispose();
                showChangeTruckDialog();
            }
            if (destinationsCheckbox.isSelected()) {
                TransferStartFrame.dispose();
                showChangeDestinationsDialog();
            }
            if (itemsCheckbox.isSelected()) {
                TransferStartFrame.dispose();
                showChangeItemsDialog();
            }
        }
    }
    private void showChangeTruckDialog() throws SQLException {
        Map<Integer, List<String>> availableTrucks = TransferManagerService.getAvailableTrucksDetails(transferId);
        JCheckBox[] truckCheckboxes = new JCheckBox[availableTrucks.size()];
        JPanel panel = new JPanel();
        panel.add(new JLabel("These are the available trucks. Please choose on of them: "));
        panel.setLayout(new GridLayout(0, 1));
        ButtonGroup bg = new ButtonGroup();
        int i = 0;
        //Create the checkboxes
        for (Integer licenseNum : availableTrucks.keySet()) {
            truckCheckboxes[i] = new JCheckBox("License Number: " + licenseNum + "   Temp Capacity: " + availableTrucks.get(licenseNum).get(0) + "   Weight Capacity: " + availableTrucks.get(licenseNum).get(1));
            panel.add(truckCheckboxes[i]);
            bg.add(truckCheckboxes[i]);
            i++;
        }

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
            int chosenLicenseNum = -1;
            for(int j = 0; j < truckCheckboxes.length; j++)
            {
                if (truckCheckboxes[j].isSelected())
                {
                    String input = truckCheckboxes[j].getText();
                    String prefix = "License Number: ";
                    int startIndex = input.indexOf(prefix);

                    if (startIndex != -1) {
                        int endIndex = input.indexOf(" ", startIndex + prefix.length());
                        if (endIndex != -1) {
                            chosenLicenseNum =  Integer.parseInt(input.substring(startIndex + prefix.length(), endIndex));
                        } else {
                            chosenLicenseNum = Integer.parseInt(input.substring(startIndex + prefix.length()));
                        }
                    }
                    break;
                }
            }
            TransferManagerService.updateNewChosenTruck(transferId, chosenLicenseNum);
            JOptionPane.showMessageDialog(TransferStartFrame, "Truck changed successfully. You need the insert some info again.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            showStartTransfer();
        }
    }

    private void showChangeDestinationsDialog() {
        Map<Integer, String> transferDestinations = TransferManagerService.getTransferDestinationNames(transferId);
        JCheckBox[] destCheckboxes = new JCheckBox[transferDestinations.size()];
        JPanel panel = new JPanel();
        panel.add(new JLabel("These are the transfer destinations. Please choose the destination you would like to remove: "));
        panel.setLayout(new GridLayout(0, 1));
        ButtonGroup bg = new ButtonGroup();
        int i = 0;
        //Create the checkboxes
        for (Integer destId : transferDestinations.keySet()) {
            destCheckboxes[i] = new JCheckBox("Destination ID: " + destId + "   Destination Name: " + transferDestinations.get(destId));
            panel.add(destCheckboxes[i]);
            bg.add(destCheckboxes[i]);
            i++;
        }

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
            int chosenDestId = -1;
            for(int j = 0; j < destCheckboxes.length; j++)
            {
                if (destCheckboxes[j].isSelected())
                {
                    String input = destCheckboxes[j].getText();
                    String prefix = "Destination ID: ";
                    int startIndex = input.indexOf(prefix);

                    if (startIndex != -1) {
                        int endIndex = input.indexOf(" ", startIndex + prefix.length());
                        if (endIndex != -1) {
                            chosenDestId =  Integer.parseInt(input.substring(startIndex + prefix.length(), endIndex));
                        } else {
                            chosenDestId = Integer.parseInt(input.substring(startIndex + prefix.length()));
                        }
                    }
                    break;
                }
            }
            TransferManagerService.removeDest(chosenDestId, transferId);
            JOptionPane.showMessageDialog(TransferStartFrame, "Destination removed successfully. You need the insert some info again.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            showStartTransfer();
        }
    }

    private void showChangeItemsDialog() {
        Map<Integer, String> transferDestinations = TransferManagerService.getTransferDestinationNames(transferId);
        JCheckBox[] destCheckboxes = new JCheckBox[transferDestinations.size()];
        JPanel panel = new JPanel();
        panel.add(new JLabel("These are the transfer destinations. Please choose the destination you would like to remove: "));
        panel.setLayout(new GridLayout(0, 1));
        int i = 0;
        //Create the checkboxes
        for (Integer destId : transferDestinations.keySet()) {
            destCheckboxes[i] = new JCheckBox("Destination ID: " + destId + "   Destination Name: " + transferDestinations.get(destId));
            panel.add(destCheckboxes[i]);
            i++;
        }

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
            List<Integer> chosenDests = new LinkedList<>();
            for(int j = 0; j < destCheckboxes.length; j++)
            {
                if (destCheckboxes[j].isSelected())
                {
                    String input = destCheckboxes[j].getText();
                    String prefix = "Destination ID: ";
                    int startIndex = input.indexOf(prefix);

                    if (startIndex != -1) {
                        int endIndex = input.indexOf(" ", startIndex + prefix.length());
                        if (endIndex != -1) {
                            chosenDests.add(Integer.parseInt(input.substring(startIndex + prefix.length(), endIndex)));
                        } else {
                            chosenDests.add(Integer.parseInt(input.substring(startIndex + prefix.length())));
                        }
                    }
                }
            }
            Map<Integer, Map<String, List<String>>> itemDetails = TransferManagerService.getDetailsOfItemsInDestsToRemove(chosenDests, transferId);
            for (Integer destId : itemDetails.keySet()) {
                showRemoveItemsDialog(destId, itemDetails.get(destId));
            }
            JOptionPane.showMessageDialog(TransferStartFrame, "Items removed successfully. You need the insert some info again.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            showStartTransfer();
        }
    }

    private void showRemoveItemsDialog(int destId, Map<String, List<String>> itemDetails) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4));

        for (String catalogNum : itemDetails.keySet()) {
            List<String> details = itemDetails.get(catalogNum);
            String itemName = details.get(0);
            String quantity = details.get(1);
            panel.add(new JLabel("Catalog Number: " + catalogNum));
            panel.add(new JLabel("   Item Name: " + itemName));
            panel.add(new JLabel("   Quantity To Reduce:"));
            JTextField quantityField = new JTextField(quantity);
            panel.add(quantityField);

        }

        int option = JOptionPane.showConfirmDialog(null, panel, "Change Quantities", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            for (int i = 0; i < panel.getComponentCount(); i += 4) {
                try {
                    JLabel catalogNumLabel = (JLabel) panel.getComponent(i);
                    JTextField quantityField = (JTextField) panel.getComponent(i + 3);

                    String catalogNum = catalogNumLabel.getText().replace("Catalog Number: ", "");
                    String quantity = quantityField.getText();
                    // Perform necessary actions with the updated quantity, such as updating the map or calling a method
                    if (!TransferManagerService.reduceItemQuantityFromDest(catalogNum, Integer.parseInt(quantity), destId, transferId))
                        JOptionPane.showMessageDialog(TransferStartFrame, "Item with catalog num" + catalogNum + " didn't reduced because the quantity entered is illegal",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(TransferStartFrame, "One of your inputs is illegal. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
