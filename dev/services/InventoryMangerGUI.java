package services;


import Domain.*;
import DataAccesObject.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Inventory Manger");

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        ShortageReportButton = createStyledButton("Product Shortage Report");
        UpdateInventoryButton = createStyledButton("Remove Supplier");
        CountingReportButton = createStyledButton("Update Contact to Supplier");
        DefectiveReportButton = createStyledButton("Add Item to Supplier");
        UpdateDiscountButton = createStyledButton("Remove Item from Supplier");
        PriceHistoryReportButton = createStyledButton("Update Item on Contract");
        InsertDefectiveButton = createStyledButton("Present All Suppliers");
        PrintFullInventoryButton = createStyledButton("Get Supplier by ID");

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
        this.inventoryController= new InventoryController();
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
        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //******************* this is the switch case *******************/
        if(e.getSource() == ShortageReportButton) {
            JLabel label = new JLabel("Which report to provide:");

//            ShortageReportButton = createStyledButton("For all products");
//            UpdateInventoryButton = createStyledButton("For Category");
//            CountingReportButton = createStyledButton("For specific product ");
//            DefectiveReportButton = createStyledButton("Return to the main manu");

            String headline = "Which report to provide:";
            String[] options = {"1. For all products", "2. For Category", "3. For specific product"};
            int choiceNum = JOptionPane.showOptionDialog(null, headline, "Shortage Report", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choiceNum == -1) {
                return;
            }

            //TODO consider to show alert if the list is empty
            switch (choiceNum) {
                case 0:
                    // for all products

                case 1:
                    // for category

                case 2:
                    // for specific item

            }


        } else if (e.getSource() == UpdateInventoryButton) {



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


