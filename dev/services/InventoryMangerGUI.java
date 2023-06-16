package services;


import Domain.*;
import DataAccesObject.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;


public class InventoryMangerGUI implements ActionListener {

    private JFrame jframe;
    private  JButton ShortageReportButton;
    private  JButton UpdateInventoryButton;
    private  JButton CountingReportButton;
    private  JButton DefectiveReportButton;
    private  JButton UpdateDiscountButton;
    private  JButton PriceHistoryReportButton;
    private  JButton InsertDefectiveButton;
    private  JButton PrintFullInventoryButton;
    private  JPanel buttonPanel;
    private InventoryController inventoryController;


    public InventoryMangerGUI() {
        this.inventoryController = new InventoryController();
        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Inventory Manger");

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        ShortageReportButton = createStyledButton("Product Shortage Report");
        UpdateInventoryButton = createStyledButton("Update inventory");
        CountingReportButton = createStyledButton("Inventory counting report");
        DefectiveReportButton = createStyledButton("Defective products report");
        UpdateDiscountButton = createStyledButton("Update discount");
        PriceHistoryReportButton = createStyledButton("Price history report");
        InsertDefectiveButton = createStyledButton("Defective insertion");
        PrintFullInventoryButton = createStyledButton("Print full inventory");

        // Register the ActionListener for each button
        ShortageReportButton.addActionListener(this);
        UpdateInventoryButton.addActionListener(this);
        CountingReportButton.addActionListener(this);
        DefectiveReportButton.addActionListener(this);
        UpdateDiscountButton.addActionListener(this);
        PriceHistoryReportButton.addActionListener(this);
        InsertDefectiveButton.addActionListener(this);
        PrintFullInventoryButton.addActionListener(this);


        buttonPanel.add(ShortageReportButton);
        buttonPanel.add(UpdateInventoryButton);
        buttonPanel.add(CountingReportButton);
        buttonPanel.add(DefectiveReportButton);
        buttonPanel.add(UpdateDiscountButton);
        buttonPanel.add(PriceHistoryReportButton);
        buttonPanel.add(InsertDefectiveButton);
        buttonPanel.add(PrintFullInventoryButton);

        jframe.add(buttonPanel);
        jframe.setVisible(true);
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 60));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(235, 235, 235));
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //******************* this is the switch case *******************/
        if(e.getSource() == ShortageReportButton) {
            String[] options = {"For all products", "For Category", "For specific product", "Return"};
            // Create a custom panel with FlowLayout
            JLabel label = new JLabel();
            label.setText("Which report to provide ?");
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(label);

            int choiceNum = JOptionPane.showOptionDialog(null, panel, "Shortage Report", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choiceNum == -1) {
                return;
            }
            String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Amount"};
            Report report = null;
            String reportType;
            String reportNumber;
            String reportDate;
            String reportString = "";

            switch (choiceNum) {
                case 0:
                    // for all products
                    if (inventoryController.shortageReportFull() == null){
                        JOptionPane.showInternalMessageDialog(null,"No missing products", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(column,0);
                    JTable jtable = new JTable(tableModel);
                    jtable.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable.setFont(new Font("Arial", Font.PLAIN,10));

                    report = inventoryController.shortageReportFull();
                    reportType = report.getType().toString();
                    reportNumber = Integer.toString(report.getReportNum());
                    reportDate = report.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines[] = reportString.split("\n");
                    for (String line : lines) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel.addRow(new Object[]{reportType,reportNumber,reportDate,columns[0],columns[1]});;
                    }

                    JScrollPane sp = new JScrollPane(jtable);
                    jframe.add(sp);
                    sp.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp,"Shortage Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 1:
                    //TODO fix - the reportNumber jump by 2

                    // for category
                    String nameCategory =   JOptionPane.showInputDialog("For which category ?");

                    if (inventoryController.shortageReportCategory(nameCategory) == null){
                        JOptionPane.showInternalMessageDialog(null,"No missing products", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel2 = new DefaultTableModel(column,0);
                    JTable jtable2 = new JTable(tableModel2);
                    jtable2.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable2.setFont(new Font("Arial", Font.PLAIN,10));

                    Report report2 = inventoryController.shortageReportCategory(nameCategory);
                    String reportType2 = report2.getType().toString();
                    String reportNumber2 = Integer.toString(report2.getReportNum());
                    String reportDate2 = report2.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report2.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines2[] = reportString.split("\n");
                    for (String line : lines2) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel2.addRow(new Object[]{reportType2,reportNumber2,reportDate2,columns[0],columns[1]});;
                    }

                    JScrollPane sp2 = new JScrollPane(jtable2);
                    jframe.add(sp2);
                    sp2.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp2,"Shortage Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 2:
                    // for specific item
                    String nameSpecific =   JOptionPane.showInputDialog("What is the catalog number ?");

                    if (inventoryController.shortageReportGeneralItem(nameSpecific) == null){
                        JOptionPane.showInternalMessageDialog(null,"No missing products", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel3 = new DefaultTableModel(column,0);
                    JTable jtable3 = new JTable(tableModel3);
                    jtable3.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable3.setFont(new Font("Arial", Font.PLAIN,10));

                    Report report3 = inventoryController.shortageReportGeneralItem(nameSpecific);
                    String reportType3 = report3.getType().toString();
                    String reportNumber3 = Integer.toString(report3.getReportNum());
                    String reportDate3 = report3.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report3.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines3[] = reportString.split("\n");
                    for (String line : lines3) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel3.addRow(new Object[]{reportType3,reportNumber3,reportDate3,columns[0],columns[1]});;
                    }

                    JScrollPane sp3 = new JScrollPane(jtable3);
                    jframe.add(sp3);
                    sp3.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp3,"Shortage Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 3:
                    // return
                    JButton backButton = new JButton("Back");
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Handle the back button click event
                            // Add your code here to go back to the previous view or screen

                            // For example, you can close the current JFrame
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                            frame.dispose();
                        }
                    });
                    break;
            }


        }
        else if (e.getSource() == UpdateInventoryButton) {
            String[] options = {"Create new category", "Create new subcategory", "Create new general Item", "Add a new specific item", "Delete category", "Delete general item", "Delete specific item", "Move a specific item", "Return"};
            JLabel label = new JLabel();
            label.setText("which action you would like to do ?");
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(label);
            panel.setPreferredSize(new Dimension(300,80));
            int choiceNum = JOptionPane.showOptionDialog(null, panel, "Shortage Report", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choiceNum) {
                case 0:
                    // create new category
                    break;
                case 1:
                    // Create new subcategory
                    break;
                case 2:
                    // Create new general Item
                    break;
                case 3:
                    // Add a new specific item
                    break;
                case 4:
                    // Delete category
                    break;
                case 5:
                    // Delete general item
                    break;
                case 6:
                    // Delete specific item
                    break;
                case 7:
                    // Move a specific item
                    break;
                case 8:
                    // return
                    JButton backButton = new JButton("Back");
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Handle the back button click event
                            // Add your code here to go back to the previous view or screen

                            // For example, you can close the current JFrame
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                            frame.dispose();
                        }
                    });
                    break;
            }

        }
        //TODO - fix the amount, now is always 0
        //provide counting report
        else if (e.getSource() == CountingReportButton) {
            String[] options = {"For all products", "For Category", "For specific product", "Return"};
            // Create a custom panel with FlowLayout
            JLabel label = new JLabel();
            label.setText("Which report to provide ?");
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(label);

            int choiceNum = JOptionPane.showOptionDialog(null, panel, "Counting Report", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choiceNum == -1) {
                return;
            }
            String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Amount"};
            Report report = null;
            String reportType;
            String reportNumber;
            String reportDate;
            String reportString = "";

            switch (choiceNum) {
                case 0:
                    // for all products
                    if (inventoryController.FullCountingReport() == null){
                        JOptionPane.showInternalMessageDialog(null,"No products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(column,0);
                    JTable jtable = new JTable(tableModel);
                    jtable.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable.setFont(new Font("Arial", Font.PLAIN,10));

                    report = inventoryController.FullCountingReport();
                    reportType = report.getType().toString();
                    reportNumber = Integer.toString(report.getReportNum());
                    reportDate = report.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines[] = reportString.split("\n");
                    for (String line : lines) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel.addRow(new Object[]{reportType,reportNumber,reportDate,columns[0],columns[1]});;
                    }

                    JScrollPane sp = new JScrollPane(jtable);
                    jframe.add(sp);
                    sp.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp,"Counting Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 1:
                    //TODO fix - the reportNumber jump by 2

                    // for category
                    String nameCategory =   JOptionPane.showInputDialog("For which category ?");

                    if (inventoryController.CategoryCountingReport(nameCategory) == null){
                        JOptionPane.showInternalMessageDialog(null,"No products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel2 = new DefaultTableModel(column,0);
                    JTable jtable2 = new JTable(tableModel2);
                    jtable2.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable2.setFont(new Font("Arial", Font.PLAIN,10));

                    Report report2 = inventoryController.CategoryCountingReport(nameCategory);
                    String reportType2 = report2.getType().toString();
                    String reportNumber2 = Integer.toString(report2.getReportNum());
                    String reportDate2 = report2.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report2.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines2[] = reportString.split("\n");
                    for (String line : lines2) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel2.addRow(new Object[]{reportType2,reportNumber2,reportDate2,columns[0],columns[1]});;
                    }

                    JScrollPane sp2 = new JScrollPane(jtable2);
                    jframe.add(sp2);
                    sp2.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp2,"Counting Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 2:
                    // for specific item
                    String nameSpecific =   JOptionPane.showInputDialog("What is the catalog number ?");

                    if (inventoryController.ItemCountingReport(nameSpecific) == null){
                        JOptionPane.showInternalMessageDialog(null,"No products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel3 = new DefaultTableModel(column,0);
                    JTable jtable3 = new JTable(tableModel3);
                    jtable3.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable3.setFont(new Font("Arial", Font.PLAIN,10));

                    Report report3 = inventoryController.ItemCountingReport(nameSpecific);
                    String reportType3 = report3.getType().toString();
                    String reportNumber3 = Integer.toString(report3.getReportNum());
                    String reportDate3 = report3.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report3.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines3[] = reportString.split("\n");
                    for (String line : lines3) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel3.addRow(new Object[]{reportType3,reportNumber3,reportDate3,columns[0],columns[1]});;
                    }

                    JScrollPane sp3 = new JScrollPane(jtable3);
                    jframe.add(sp3);
                    sp3.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp3,"Counting Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 3:
                    // return
                    JButton backButton = new JButton("Back");
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Handle the back button click event
                            // Add your code here to go back to the previous view or screen

                            // For example, you can close the current JFrame
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                            frame.dispose();
                        }
                    });
                    break;
            }

        }



        //provide Defective Report
        else if (e.getSource() == DefectiveReportButton) {
            String[] options = {"For all products", "For Category", "For specific product", "Return"};
            // Create a custom panel with FlowLayout
            JLabel label = new JLabel();
            label.setText("Which report to provide ?");
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(label);

            int choiceNum = JOptionPane.showOptionDialog(null, panel, "Defective Report", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choiceNum == -1) {
                return;
            }

            String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Amount"};
            Report report = null;
            String reportType;
            String reportNumber;
            String reportDate;
            String reportString = "";

            switch (choiceNum) {
                case 0:
                    // for all products
                    if (inventoryController.FullDefectiveReport() == null){
                        JOptionPane.showInternalMessageDialog(null,"No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(column,0);
                    JTable jtable = new JTable(tableModel);
                    jtable.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable.setFont(new Font("Arial", Font.PLAIN,10));

                    report = inventoryController.FullDefectiveReport();
                    reportType = report.getType().toString();
                    reportNumber = Integer.toString(report.getReportNum());
                    reportDate = report.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines[] = reportString.split("\n");
                    for (String line : lines) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel.addRow(new Object[]{reportType,reportNumber,reportDate,columns[0],columns[1]});;
                    }

                    JScrollPane sp = new JScrollPane(jtable);
                    jframe.add(sp);
                    sp.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp,"Defective Report", JOptionPane.PLAIN_MESSAGE);
                    break;

                case 1:
                    //TODO fix - the reportNumber jump by 2

                    // for category
                    String nameCategory = JOptionPane.showInputDialog("For which category ?");

                    if (inventoryController.CategoryDefectiveReport(nameCategory) == null){
                        JOptionPane.showInternalMessageDialog(null,"No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel2 = new DefaultTableModel(column,0);
                    JTable jtable2 = new JTable(tableModel2);
                    jtable2.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable2.setFont(new Font("Arial", Font.PLAIN,10));

                    Report report2 = inventoryController.CategoryDefectiveReport(nameCategory);
                    String reportType2 = report2.getType().toString();
                    String reportNumber2 = Integer.toString(report2.getReportNum());
                    String reportDate2 = report2.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report2.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines2[] = reportString.split("\n");
                    for (String line : lines2) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel2.addRow(new Object[]{reportType2,reportNumber2,reportDate2,columns[0],columns[1]});;
                    }

                    JScrollPane sp2 = new JScrollPane(jtable2);
                    jframe.add(sp2);
                    sp2.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp2,"Defective Report", JOptionPane.PLAIN_MESSAGE);
                    break;

                case 2:
                    // for specific item
                    String nameSpecific = JOptionPane.showInputDialog("What is the catalog number ?");

                    if (inventoryController.ItemDefectiveReport(nameSpecific) == null){
                        JOptionPane.showInternalMessageDialog(null,"No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel3 = new DefaultTableModel(column,0);
                    JTable jtable3 = new JTable(tableModel3);
                    jtable3.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                    jtable3.setFont(new Font("Arial", Font.PLAIN,10));

                    Report report3 = inventoryController.ItemDefectiveReport(nameSpecific);
                    String reportType3 = report3.getType().toString();
                    String reportNumber3 = Integer.toString(report3.getReportNum());
                    String reportDate3 = report3.getReportDate().toString();

                    for (Map.Entry<Item, Integer> entry : report3.getReportItems().entrySet()) {
                        Item item = entry.getKey();
                        int amount = entry.getValue();
                        reportString += item.getCatalogNum() + ":" + amount + "\n";
                    }

                    String lines3[] = reportString.split("\n");
                    for (String line : lines3) {
                        // Split the line into columns
                        String[] columns = line.split(":");

                        // Add the columns as a new row to the table model
                        tableModel3.addRow(new Object[]{reportType3,reportNumber3,reportDate3,columns[0],columns[1]});;
                    }

                    JScrollPane sp3 = new JScrollPane(jtable3);
                    jframe.add(sp3);
                    sp3.setPreferredSize(new Dimension(1000,400));
                    JOptionPane.showMessageDialog(null,sp3,"Defective Report", JOptionPane.PLAIN_MESSAGE);
                    break;

                case 3:
                    // return
                    JButton backButton = new JButton("Back");
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Handle the back button click event
                            // Add your code here to go back to the previous view or screen

                            // For example, you can close the current JFrame
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                            frame.dispose();
                        }
                    });
                    break;

            }
        }


        //update discount for products
        else if (e.getSource() == UpdateDiscountButton) {

        }


        //provide price history report
        else if (e.getSource() == PriceHistoryReportButton) {
            String catalogNumber = JOptionPane.showInputDialog("What is the catalog number ?");

            if (inventoryController.priceHistoryReport(catalogNumber) == null)
            {
                JOptionPane.showInternalMessageDialog(null,"No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
            }
            else {

                String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Buy Price", "Sell Price"};
                Report report = null;
                String reportType;
                String reportNumber;
                String reportDate;
                String reportString = "";
                DefaultTableModel tableModel3 = new DefaultTableModel(column,0);
                JTable jtable = new JTable(tableModel3);
                jtable.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
                jtable.setFont(new Font("Arial", Font.PLAIN,10));


                report = inventoryController.priceHistoryReport(catalogNumber);
                reportType = report.getType().toString();
                reportNumber = Integer.toString(report.getReportNum());
                reportDate = report.getReportDate().toString();
                reportString = report.printPriceHistory();

                for (Map.Entry<Item, Integer> entry : report.getReportItems().entrySet()) {
                    Item item = entry.getKey();
                    int amount = entry.getValue();
                    reportString += item.getCatalogNum() + ":" + amount + "\n";
                }

                String lines3[] = reportString.split("\n");
                for (String line : lines3) {
                    // Split the line into columns
                    String[] columns = line.split(":");

                    // Add the columns as a new row to the table model
                    tableModel3.addRow(new Object[]{reportType,reportNumber,reportDate,columns[0],columns[1],columns[2]});
                }

                JScrollPane sp = new JScrollPane(jtable);
                jframe.add(sp);
                sp.setPreferredSize(new Dimension(1000,400));
                JOptionPane.showMessageDialog(null,sp,"Defective Report", JOptionPane.PLAIN_MESSAGE);

            }


        }


        //move defective product to storage and tag it as "defective"
        else if (e.getSource() == InsertDefectiveButton) {
            boolean validInput = false;
            String defectedSerialNumberInput = null;
            while (!validInput) {
                defectedSerialNumberInput = JOptionPane.showInputDialog("What is the serial number for the item to be set as defected ?");

                // Check if the input consists only of numeric characters
                if (defectedSerialNumberInput.matches("\\d+")) {
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }
            int defectedSerialNumber = Integer.parseInt(defectedSerialNumberInput);
            inventoryController.moveSpecificItemToDefectiveMapper(defectedSerialNumber);
            JOptionPane.showInternalMessageDialog(null,"Item numbered " + defectedSerialNumber + " has been set as defected and moved into the warehouse storage", "Alert", JOptionPane.INFORMATION_MESSAGE);
        }


        //print all the products in the inventory
        else if (e.getSource() == PrintFullInventoryButton){
            String column[] = {"Catalog Number", "Item ID", "Expiration Date", "isDefected", "location", "Buy Price", "Sell Price", "Minimum Qauntity"};

            String catalogNumber;
            Integer serialNumber;
            String expriryDate;
            String isDefected;
            String location;
            double buyPrice;
            double sellPrice;
            int minimumQantity;

            String reportString = "";
            DefaultTableModel tableModel = new DefaultTableModel(column,0);
            JTable jtable = new JTable(tableModel);
            jtable.setRowHeight(20);
//                    jtable.setBounds(30,100,200,300);
            jtable.setFont(new Font("Arial", Font.PLAIN,10));


            for(Item item: inventoryController.getItemMapper().findAll())
            {

                for(specificItem specificItem: inventoryController.getSpecificItemMapper().findByCatalogNum(item.getCatalogNum()))
                {
                    catalogNumber = specificItem.getCatalogNum();
                    serialNumber = specificItem.getSerialNumber();
                    expriryDate = String.valueOf(specificItem.getDate());
                    isDefected = String.valueOf(specificItem.isDefected());
                    location = String.valueOf(specificItem.getLocation());
                    buyPrice = 20;
                    sellPrice = 30;
                    minimumQantity = item.getMinQuantity();
//                    String lines[] = reportString.split("\n");

                    // Add all the variables as a new row to the table model
                    tableModel.addRow(new Object[]{catalogNumber,serialNumber,expriryDate,isDefected,location,buyPrice,sellPrice,minimumQantity });

                }
            }

            JScrollPane sp = new JScrollPane(jtable);
            jframe.add(sp);
            sp.setPreferredSize(new Dimension(1000,400));
            JOptionPane.showMessageDialog(null,sp,"Inventory Products", JOptionPane.PLAIN_MESSAGE);

        }


    }

    public static boolean isDoubleString(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /* //showConfirmDialog - ask if are you sure
    import javax.swing.*;
    import java.awt.event.*;
    public class OptionPaneExample extends WindowAdapter{
    JFrame f;
    OptionPaneExample(){
        f=new JFrame();
        f.addWindowListener(this);
        f.setSize(300, 300);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setVisible(true);
    }
    public void windowClosing(WindowEvent e) {
        int a=JOptionPane.showConfirmDialog(f,"Are you sure?");
    if(a==JOptionPane.YES_OPTION){
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    }
    public static void main(String[] args) {
        new  OptionPaneExample();
    }
}  */

}


