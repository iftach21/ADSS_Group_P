package Interface.UI;

import Service.TransferManagerService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class UICreateTransfer {
    private JFrame Transferframe;
    private JFrame chooseDriverFrame;
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


    public UICreateTransfer() throws SQLException {
        sites = TransferManagerService.getOrderSitesNames();
        initializeUI();
    }

    private void initializeUI() {
        Transferframe = new JFrame("Create a transfer");
        Transferframe.setSize(500, 430);
        Transferframe.addWindowListener(exitSystemOnX());
        Transferframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
                    JOptionPane.showMessageDialog(Transferframe, "One of your inputs is illegal or there is no storekeeper in the last destination. Please try again",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showDriverFrame() throws SQLException {
        chooseDriverFrame = new JFrame("Choose Driver");
        chooseDriverFrame.setSize(700, 430);
        chooseDriverFrame.addWindowListener(exitSystemOnX());
        chooseDriverFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
                        UIUpdateWeights startTransfer = new UIUpdateWeights(transferId);
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

    public static WindowListener exitSystemOnX() {
        WindowListener exitListener = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure you want to exit? Note that if you exit during creating a transfer the system will close.",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        };
        return exitListener;
    }
}
