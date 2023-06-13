package Interface.UI;

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

public class UICreateTransfer {
    private JFrame Transferframe;
    private TransferManagerService TransferManagerService = Service.TransferManagerService.getInstance();
    private JCheckBox[] checkboxes;
    private JTextField dateSelected;
    private JButton nextButton;
    private JSpinner spinner;


    public UICreateTransfer() throws SQLException {
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
        Map<Integer, String> sites = TransferManagerService.getOrderSitesNames();

        JLabel[] sitesHeadlines = new JLabel[sites.size()];
        int index = 0;
        for (Integer siteId : sites.keySet())
        {
            sitesHeadlines[index] = new JLabel((index + 1) + ". ID: " + siteId + "   Site Name: " + sites.get(siteId));
            sitesHeadlines[index].setBounds(20, 80 + (index * 20), 650, 20);
            Transferframe.add(sitesHeadlines[index]);
            index++;
            if (index == 3)
                break;
        }

        JTextField siteIdField = new JTextField();
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
        JButton b = new JButton("Select");
        JPanel p = new JPanel();
        p.add(label);
        p.add(dateSelected);
        p.add(b);
        p.setBounds(-130, 215, 650, 40);
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
        //TODO : check if date and time are legal and checkedbox. if true open choose driver dialog
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Check if Date and time are legal
                //Time parser to LocalTime
                Date selectedDate = (Date) spinner.getValue();
                java.time.Instant instant = selectedDate.toInstant();
                // Convert Instant to LocalTime
                LocalTime leavingTime = instant.atZone(ZoneId.systemDefault()).toLocalTime();

                //Date parser to LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate leavingDate = LocalDate.parse(dateSelected.getText(), formatter);

                //Check if Legal
                try {
                    if (!TransferManagerService.checkIfDateIsLegal(leavingDate, leavingTime))
                    {
                        JOptionPane.showMessageDialog(Transferframe, "Your Date is not Legal! Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(!TransferManagerService.checkIfStoreKeeperIsThere(0, leavingTime, leavingDate))
                    {
                        JOptionPane.showMessageDialog(Transferframe, "There is no storekeeper in the final destination. Please try again",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
