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
//
            String[] options = {"For all products", "For Category", "For specific product", "Return"};
            // Create a custom panel with FlowLayout
            JLabel label = new JLabel();
            label.setText("Which report to provide ?");
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(label);
//            for (String option : options) {
//                JButton button = new JButton(option);
//                panel.add(button);
//            }
            int choiceNum = JOptionPane.showOptionDialog(null, panel, "Shortage Report", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choiceNum == -1) {
                return;
            }
            String column[] = {"Report Type", "Report Number", "Report Date", "Report Information"};
            Report report = null;
            String reportType;
            String reportNumber;
            String reportDate;
            String reportInformation;

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
                    reportInformation = report.toString();

                    tableModel.addRow(new Object[]{reportType,reportNumber,reportDate,reportInformation});

                    JScrollPane sp = new JScrollPane(jtable);
                    jframe.add(sp);

                    sp.setPreferredSize(new Dimension(1000,400));

                    JOptionPane.showMessageDialog(null,sp,"Shortage Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 1:
                    //TODO fix - the shortageReportCategory not check if the category really exist

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
                    String reportInformation2 = report2.toString();

                    tableModel2.addRow(new Object[]{reportType2,reportNumber2,reportDate2,reportInformation2});

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
                    String reportInformation3 = report3.toString();

                    tableModel3.addRow(new Object[]{reportType3,reportNumber3,reportDate3,reportInformation3});

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


        } else if (e.getSource() == UpdateInventoryButton) {
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




        } else if (e.getSource() == CountingReportButton) {



        } else if (e.getSource() == DefectiveReportButton) {


        } else if (e.getSource() == UpdateDiscountButton) {

        } else if (  e.getSource() == PriceHistoryReportButton) {

        } else if (e.getSource() == InsertDefectiveButton) {

        } else if (e.getSource() == PrintFullInventoryButton){

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


