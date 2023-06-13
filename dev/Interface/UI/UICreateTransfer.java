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
import java.util.*;
import java.util.List;

public class UICreateTransfer {
    private JFrame Transferframe;
    private TransferManagerService TransferManagerService = Service.TransferManagerService.getInstance();
    private JCheckBox[] checkboxes;
    private JTextField dateSelected;
    private JTextField timeSelected;
    private JButton nextButton;


    public UICreateTransfer() throws SQLException {
        initializeUI();
    }

    private void initializeUI() {
        Transferframe = new JFrame("Create a transfer");
        Transferframe.setSize(500, 400);
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
        List<String> sites = TransferManagerService.getOrderSitesNames();
        JPanel checkboxesPanel = new JPanel(new GridLayout(sites.size(), 1));
        checkboxes = new JCheckBox[sites.size()];
        for (int i = 0; i < sites.size(); i++) {
            String string = sites.get(i);
            checkboxes[i] = new JCheckBox(string);
            checkboxesPanel.add(checkboxes[i]);
        }
        ButtonGroup buttonGroup = new ButtonGroup();
        for (JCheckBox checkbox: checkboxes) {
            buttonGroup.add(checkbox);
        }
        checkboxesPanel.setBounds(20, 80, 650, 60);
        Transferframe.add(checkboxesPanel);

        //Add date picker
        final JLabel selectDateLabel=new JLabel();
        selectDateLabel.setText("Please choose transfer leaving date: ");
        selectDateLabel.setBounds(20, 150, 650, 35);
        Transferframe.add(selectDateLabel);

        JLabel label = new JLabel("Selected Date:");
        dateSelected = new JTextField(20);
        JButton b = new JButton("Select");
        JPanel p = new JPanel();
        p.add(label);
        p.add(dateSelected);
        p.add(b);
        p.setBounds(-130, 185, 650, 60);
        Transferframe.add(p);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateSelected.setText(new DatePicker(Transferframe).setPickedDate());
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
        selectTimeLabel.setText("Please choose transfer leaving time: ");
        selectTimeLabel.setBounds(20, 245, 650, 35);
        Transferframe.add(selectTimeLabel);

        spinner.setBounds(20, 280, 100, 20);
        Transferframe.add(spinner);

        //Add button to check the desired details entered
        nextButton = new JButton("NEXT");
        nextButton.setBounds(200,300,100, 40);
        Transferframe.add(nextButton);

        //start transfer manager interface
        startCreateTransferUI();

    }

    private void startCreateTransferUI() {
        Transferframe.setVisible(true);
        
    }
}
