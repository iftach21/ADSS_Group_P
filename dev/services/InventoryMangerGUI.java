package services;


import Domain.*;
import DataAccesObject.*;


import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static Interface.OrderInterface.checkNumberWithDot;


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
    private JButton addPeriodOrderButton;
    private JButton UpdatePeriodOrder;
    private  JButton PrintAllPeriodOrder;
    private  JButton PrintAllShortageOrder;
    private JButton ReturnToManger;
    private  JPanel buttonPanel;

    private InventoryController inventoryController;
    private  OrderManger orderManger;
    private  Supplier_Manger supplier_manger;





    public InventoryMangerGUI(boolean pulse) {
        this.inventoryController = new InventoryController();
        this.orderManger =new OrderManger();
        this.supplier_manger =new Supplier_Manger();


        // dialog box style
        UIManager.put("OptionPane.background", new Color(173, 216, 230));
        UIManager.put("Panel.background", new Color(173, 216, 230));
        UIManager.put("Label.font", new Font("Haettenschweiler", Font.PLAIN, 20));
        UIManager.put("OptionPane.messageFont", new Font("Haettenschweiler", Font.PLAIN, 16));
        UIManager.put("OptionPane.messageForeground", Color.DARK_GRAY);
        UIManager.put("OptionPane.font", new Font("Haettenschweiler", Font.PLAIN, 16));
        UIManager.put("Button.background",  new Color(255, 212, 121));
        UIManager.put("Button.foreground", Color.DARK_GRAY);
        UIManager.put("Button.font", new Font("Haettenschweiler", Font.PLAIN, 16));
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("ToggleButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

        UIManager.put("CheckBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("TabbedPane.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("RadioButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("Slider.focus", new ColorUIResource(new Color(0, 0, 0, 0)));



        // main frame
        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Inventory Manger");

        JLabel label = new JLabel("Inventory Menu");
        label.setFont(new Font("Bauhaus 93", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        jframe.add(label, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(173, 216, 230));

        ShortageReportButton = createStyledButton("Product Shortage Report");
        UpdateInventoryButton = createStyledButton("Update inventory");
        CountingReportButton = createStyledButton("Inventory counting report");
        DefectiveReportButton = createStyledButton("Defective products report");
        UpdateDiscountButton = createStyledButton("Update discount");
        PriceHistoryReportButton = createStyledButton("Price history report");
        InsertDefectiveButton = createStyledButton("Defective insertion");
        PrintFullInventoryButton = createStyledButton("Print full inventory");
        PrintAllShortageOrder =createStyledButton("Print all Order's");
        PrintAllPeriodOrder =createStyledButton("Print all Period's Order's");
        addPeriodOrderButton =createStyledButton("Add Period Order");
        UpdatePeriodOrder =createStyledButton("Update a Period Order");
        ReturnToManger = createStyledButton("Return to Manger Manu");

        // Register the ActionListener for each button
        ShortageReportButton.addActionListener(this);
        UpdateInventoryButton.addActionListener(this);
        CountingReportButton.addActionListener(this);
        DefectiveReportButton.addActionListener(this);
        UpdateDiscountButton.addActionListener(this);
        PriceHistoryReportButton.addActionListener(this);
        InsertDefectiveButton.addActionListener(this);
        PrintFullInventoryButton.addActionListener(this);
        PrintAllShortageOrder.addActionListener(this);
        PrintAllPeriodOrder.addActionListener(this);
        addPeriodOrderButton.addActionListener(this);
        UpdatePeriodOrder.addActionListener(this);
        ReturnToManger.addActionListener(this);


        buttonPanel.add(ShortageReportButton);
        buttonPanel.add(UpdateInventoryButton);
        buttonPanel.add(CountingReportButton);
        buttonPanel.add(DefectiveReportButton);
        buttonPanel.add(UpdateDiscountButton);
        buttonPanel.add(PriceHistoryReportButton);
        buttonPanel.add(InsertDefectiveButton);
        buttonPanel.add(PrintFullInventoryButton);
        buttonPanel.add(PrintAllPeriodOrder);
        buttonPanel.add(PrintAllShortageOrder);
        buttonPanel.add(addPeriodOrderButton);
        buttonPanel.add(UpdatePeriodOrder);
        if(pulse == true){
            buttonPanel.add(ReturnToManger);}

        jframe.add(buttonPanel);
        jframe.setVisible(true);


    }



    private JButton createStyledButton(String text) {
        JButton button = new JButton("<html><center>" + text.replaceAll("\\n", "<br>") + "</center></html>");
        button.setPreferredSize(new Dimension(180, 60));
        button.setFont(new Font("Haettenschweiler", Font.BOLD, 26));

        Color buttonColor = new Color(255, 212, 121);
        button.setBackground(buttonColor);
        button.setForeground(Color.DARK_GRAY);

        int cornerRadius = 20;
        Border roundedBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
        Border compoundBorder = BorderFactory.createCompoundBorder(roundedBorder, emptyBorder);

        // Create a rounded border using the corner radius
        Border roundedCornerBorder = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);
                g2d.dispose();
            }
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
            }
            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        };

        // apply the rounded corner border and compound border to the button
        button.setBorder(BorderFactory.createCompoundBorder(roundedCornerBorder, compoundBorder));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);


        // mark the button when the mouse is on
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor);
            }
        });

        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //******************* this is the switch case *******************/
        if (e.getSource() == ShortageReportButton) {
            //Report issuance options
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

            //titles of the Columns in the table
            String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Amount"};
            Report report = null;
            String reportType;
            String reportNumber;
            String reportDate;
            String reportString = "";

            switch (choiceNum) {
                case 0:
                    // for all products
                    report = inventoryController.shortageReportFull();
                    if (report == null) {
                        JOptionPane.showInternalMessageDialog(null, "No missing products", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(column, 0);
                    JTable jtable = new JTable(tableModel);
                    jtable.setRowHeight(20);
                    jtable.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable = new TableRowSorter<>(tableModel);
                    jtable.setRowSorter(sortTable);

                    DefaultTableCellRenderer rowRenderer = new DefaultTableCellRenderer();
                    rowRenderer.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable.setDefaultRenderer(Object.class, rowRenderer);

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
                        tableModel.addRow(new Object[]{reportType, reportNumber, reportDate, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp = new JScrollPane(jtable);
                    jframe.add(sp);
                    sp.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp, "Shortage Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 1:
                    // for category
                    String nameCategory = JOptionPane.showInputDialog("For which category ?");

                    // if clicked "cancel"
                    if (nameCategory == null) {
                        Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                        return;
                    }

                    Report report2 = inventoryController.shortageReportCategory(nameCategory);
                    if (report2 == null) {
                        JOptionPane.showInternalMessageDialog(null, "No missing products", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel2 = new DefaultTableModel(column, 0);
                    JTable jtable2 = new JTable(tableModel2);
                    jtable2.setRowHeight(20);
                    jtable2.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable2 = new TableRowSorter<>(tableModel2);
                    jtable2.setRowSorter(sortTable2);

                    DefaultTableCellRenderer rowRenderer2 = new DefaultTableCellRenderer();
                    rowRenderer2.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable2.setDefaultRenderer(Object.class, rowRenderer2);

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
                        tableModel2.addRow(new Object[]{reportType2, reportNumber2, reportDate2, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp2 = new JScrollPane(jtable2);
                    jframe.add(sp2);
                    sp2.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp2, "Shortage Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 2:
                    // for specific item
                    String nameSpecific = JOptionPane.showInputDialog("What is the catalog number ?");

                    // if clicked "cancel"
                    if (nameSpecific == null) {
                        Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                        return;
                    }

                    Report report3 = inventoryController.shortageReportGeneralItem(nameSpecific);
                    if (report3 == null) {
                        JOptionPane.showInternalMessageDialog(null, "No missing products", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel3 = new DefaultTableModel(column, 0);
                    JTable jtable3 = new JTable(tableModel3);
                    jtable3.setRowHeight(20);
                    jtable3.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable3 = new TableRowSorter<>(tableModel3);
                    jtable3.setRowSorter(sortTable3);

                    DefaultTableCellRenderer rowRenderer3 = new DefaultTableCellRenderer();
                    rowRenderer3.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable3.setDefaultRenderer(Object.class, rowRenderer3);

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
                        tableModel3.addRow(new Object[]{reportType3, reportNumber3, reportDate3, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp3 = new JScrollPane(jtable3);
                    jframe.add(sp3);
                    sp3.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp3, "Shortage Report", JOptionPane.PLAIN_MESSAGE);

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


        // update the inventory - creation and deletion
        else if (e.getSource() == UpdateInventoryButton) {
            //Report issuance options
            String[] options = {"Create new category", "Create new subcategory", "Create new general Item", "Add new specific item", "Delete category", "Delete general item", "Delete specific item", "Move a specific item", "Return"};
            JLabel label = new JLabel("Which action would you like to do?");
            JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));
            panel.setPreferredSize(new Dimension(600, 300));

            final boolean[] validInput = {false};

            for (String option : options) {
                JButton button = new JButton(option);
                button.setFocusable(false);
                button.setPreferredSize(new Dimension(80, 40));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Perform the specific action based on the selected option
                        String selectedOption = option;


                        // create new category
                        if (selectedOption.equals("Create new category")) {
                            String categoryInput = null;
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("How would you like to name the new category ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    categoryInput = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (categoryInput == null)
                                return;

                            inventoryController.addCategoryToMapper(categoryInput);
                            JOptionPane.showInternalMessageDialog(null, "Category added", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        }


                        // create new subcategory
                        else if (selectedOption.equals("Create new subcategory")) {
                            String subCategoryInput = null;
                            String categoryInput = null;
                            validInput[0] = false;

                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("How would you like to name the new sub-category ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    subCategoryInput = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (subCategoryInput == null)
                                return;

                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("Which category will store this sub-category ?");
                                // Check if the input consists only of alphabetic characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    categoryInput = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (categoryInput == null)
                                return;
                            subCategory subCategory = new subCategory(subCategoryInput);
                            inventoryController.addSubCatToMapper(categoryInput, subCategory);
                            JOptionPane.showInternalMessageDialog(null, "Sub-Category added", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        }


                        // create new general Item
                        else if (selectedOption.equals("Create new general Item")) {
                            String itemName = null;
                            String catalogNumber = null;
                            String categoryName = null;
                            String subCategoryName = null;
                            double itemWeight = 0;
                            String itemManufacturer = null;
                            TempLevel itemTempeture = null;
                            int itemMinimumQantity = 0;
                            double itemBuyPrice = 0;
                            double itemSellPrice = 0;
                            Item currentItem = null;
                            validInput[0] = false;


                            // item name
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("How would you like to name the new general item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    itemName = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (itemName == null)
                                return;


                            // catelog number
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("What is the catalog number for the new general item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("\\d+")) {
                                    catalogNumber = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (catalogNumber == null)
                                return;


                            // category name
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("Which category will store the new item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    categoryName = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (categoryName == null)
                                return;


                            // sub-category name
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("Which sub-category will store the new general item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    subCategoryName = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (subCategoryName == null)
                                return;


                            // item weight
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("What is the weight for the new general item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters
                                if (input.matches("^\\s*\\d+(\\.\\d+)?\\s*$")) {
                                    itemWeight = Double.parseDouble(input.trim());
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                                }
                            }
                            if (itemWeight == 0)
                                return;


                            // item manufacturer
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("Who is the manufacturer of the general item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    itemManufacturer = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (itemManufacturer == null)
                                return;


                            // item temperature
                            String[] options = {"Regular", "Cold", "Frozen"};
                            // Create a custom panel with FlowLayout
                            JLabel label = new JLabel();
                            label.setText("What is the recommended temperature for the product ?");
                            JPanel panel = new JPanel(new FlowLayout());
                            panel.add(label);

                            int choiceNum = JOptionPane.showOptionDialog(null, panel, "Temperature", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            if (choiceNum == -1) {
                                return;
                            }
                            switch (choiceNum) {
                                case 0:
                                    // regular
                                    itemTempeture = inventoryController.returnTempLevel("A");
                                    break;

                                case 1:
                                    // cold
                                    itemTempeture = inventoryController.returnTempLevel("B");
                                    break;

                                case 2:
                                    // frozen
                                    itemTempeture = inventoryController.returnTempLevel("C");
                                    break;
                            }

                            // item creation
                            currentItem = new Item(itemName, catalogNumber, itemWeight, categoryName, itemTempeture, itemManufacturer);
                            if (currentItem == null)
                                return;


                            // minimum quantity
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("What is the minimum quantity of the general item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters
                                if (input.matches("\\d+")) {
                                    itemMinimumQantity = Integer.parseInt(input);
                                    currentItem.setMinQuantity(itemMinimumQantity);
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                                }
                            }
                            if (itemMinimumQantity == 0)
                                return;


                            // price
                            // buying price
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("what is the buying price of the item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters
                                if (input.matches("^\\s*\\d+(\\.\\d+)?\\s*$")) {
                                    itemBuyPrice = Double.parseDouble(input.trim());
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                                }
                            }
                            if (itemBuyPrice == 0)
                                return;

                            // selling price
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("what is the selling price of the item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters
                                if (input.matches("^\\s*\\d+(\\.\\d+)?\\s*$")) {
                                    itemSellPrice = Double.parseDouble(input.trim());
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                                }
                            }
                            if (itemSellPrice == 0)
                                return;

                            // add the prices
                            currentItem.addNewPrice(itemBuyPrice, itemSellPrice);
                            currentItem.setCatalogName(categoryName);

                            // add the new item
                            inventoryController.insertNewItemToMapper(currentItem);
                            JOptionPane.showInternalMessageDialog(null, "New general item added", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        }


                        // add new specific item
                        else if (selectedOption.equals("Add new specific item")) {
                            String catalogNumber = null;
                            Item currentItem = null;
                            specificItem specificItemAddition = null;
                            Date currentDate = new Date();
                            currentDate = null;
                            validInput[0] = false;


                            // catelog number
                            validInput[0] = false;
                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("What is the catalog number of the general item ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of alphabetic characters
                                if (input.matches("\\d+")) {
                                    catalogNumber = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (catalogNumber == null)
                                return;

                            currentItem = inventoryController.findItemByCatalogNum(catalogNumber);


                            // has expiry date
                            int expiryInput = JOptionPane.showConfirmDialog(null, "Does the product have an expiration date", "Expiration Date", JOptionPane.YES_NO_OPTION);

                            // if the product have expiration date
                            if (expiryInput == 0) {
                                String yearInput = JOptionPane.showInputDialog("What is the expiration year ?");
                                int expiryYear = Integer.parseInt(yearInput);
                                String monthInput = JOptionPane.showInputDialog("What is the expiration month ?");
                                int expiryMonth = Integer.parseInt(monthInput);
                                String dayInput = JOptionPane.showInputDialog("What is the expiration day ?");
                                int expiryDay = Integer.parseInt(dayInput);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, expiryYear);
                                calendar.set(Calendar.MONTH, expiryMonth - 1);
                                calendar.set(Calendar.DAY_OF_MONTH, expiryDay);
                                currentDate = calendar.getTime();

                            }

                            // create new specific item and then add it to the inventory
                            specificItemAddition = new specificItem(currentDate, false, Location.Storage, currentItem);
                            inventoryController.addSpecificItem(currentItem, specificItemAddition);
                            inventoryController.insertNewSpecificToMapper(specificItemAddition);
                            JOptionPane.showInternalMessageDialog(null, "The item has been added", "Alert", JOptionPane.INFORMATION_MESSAGE);

                        }


                        // delete category
                        else if (selectedOption.equals("Delete category")) {

                            String categoryName = null;
                            validInput[0] = false;

                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("Which category would you like to delete ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters
                                if (input.matches("[a-zA-Z\\s]+")) {
                                    categoryName = input;
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                                }
                            }
                            if (categoryName == null)
                                return;

                            inventoryController.removeCategoryFromMapper(categoryName);
                            JOptionPane.showInternalMessageDialog(null, "The category has been removed", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        }


                        // delete general item
                        else if (selectedOption.equals("Delete general item")) {
                            String catalogNumber = null;
                            Item tempItem = null;
                            validInput[0] = false;

                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("What is the Catalog Number of the general item you wish to remove ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }
                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters and if exist
                                if (input.matches("\\d+")) {
                                    catalogNumber = input;
                                    tempItem = inventoryController.findItemByCatalogNum(input);
                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                                }
                            }
                            if (catalogNumber == null)
                                return;

                            if (tempItem == null) {
                                JOptionPane.showMessageDialog(null, "Could not find such an item");
                                return;
                            } else {
                                inventoryController.deleteItemFromMapper(tempItem);
                                JOptionPane.showInternalMessageDialog(null, "Item: " + catalogNumber + " has been removed", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }


                        // delete specific item
                        else if (selectedOption.equals("Delete specific item")) {
                            int specificSerialNumber = -1;
                            specificItem tempItem = null;

                            Item currItem = null;
                            validInput[0] = false;

                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("What is the serial number of the specific item you wish to remove ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters and if exist
                                if (input.matches("\\d+")) {
                                    specificSerialNumber = Integer.parseInt(input);

                                    tempItem = inventoryController.findSpecificItemBySerialNumber(specificSerialNumber);
                                    // alert if the item is not in the store
                                    if (tempItem == null){
                                        JOptionPane.showInternalMessageDialog(null, "Item " + specificSerialNumber + " has not fount in the inventory", "Alert", JOptionPane.INFORMATION_MESSAGE);
                                        return;
                                    }
                                    currItem = inventoryController.getItemByCatalogNumber(tempItem.getCatalogNum());

                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                                }
                            }
                            if (specificSerialNumber == -1)
                                return;


                            inventoryController.deleteSpecificFromMapper(specificSerialNumber);
                            // alert if there is Shortage
                            if (inventoryController.getItemsAmount(tempItem.getCatalogNum()) <= currItem.getMinQuantity())
                                JOptionPane.showInternalMessageDialog(null, "There is a shortage!\nMinmum quantity: "+currItem.getMinQuantity() +"\nCurrent amount: " + inventoryController.getItemsAmount(tempItem.getCatalogNum()), "Alert", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showInternalMessageDialog(null, "Item " + specificSerialNumber + " has been removed", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        }


                        // change item location - from the storage to the store and from the store to storage
                        else if (selectedOption.equals("Move a specific item")) {
                            int specificSerialNumber = 0;
                            specificItem tempItem;
                            validInput[0] = false;

                            while (!validInput[0]) {
                                String input = JOptionPane.showInputDialog("What is the serial number of the specific item you wish to remove ?");

                                // if clicked "cancel"
                                if (input == null) {
                                    Window window = SwingUtilities.getWindowAncestor(button);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                // Check if the input consists only of numeric characters and if exist
                                if (input.matches("\\d+")) {
                                    specificSerialNumber = Integer.parseInt(input);

                                    validInput[0] = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                                }
                            }
                            if (specificSerialNumber == 0)
                                return;

                            inventoryController.moveSpecificItemMapper(specificSerialNumber);
                            JOptionPane.showInternalMessageDialog(null, "Item: " + specificSerialNumber + " has been moved", "Alert", JOptionPane.INFORMATION_MESSAGE);

                        }

                        // return to the main page
                        else if (selectedOption.equals("Return")) {
                        }
                        // Close the dialog box after performing the action
                        Window window = SwingUtilities.getWindowAncestor(button);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                    }
                });
                panel.add(button);
            }
            JOptionPane.showOptionDialog(null, panel, "Update Inventory", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
        }


        //provide counting report
        else if (e.getSource() == CountingReportButton) {
            //Report issuance options
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

            //titles of the Columns in the table
            String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Amount"};
            Report report = null;
            String reportType;
            String reportNumber;
            String reportDate;
            String reportString = "";

            switch (choiceNum) {
                case 0:
                    // for all products
                    report = inventoryController.FullCountingReport();
                    if (report == null) {
                        JOptionPane.showInternalMessageDialog(null, "No products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(column, 0);
                    JTable jtable = new JTable(tableModel);
                    jtable.setRowHeight(20);
                    jtable.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable = new TableRowSorter<>(tableModel);
                    jtable.setRowSorter(sortTable);

                    DefaultTableCellRenderer rowRenderer = new DefaultTableCellRenderer();
                    rowRenderer.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable.setDefaultRenderer(Object.class, rowRenderer);

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
                        tableModel.addRow(new Object[]{reportType, reportNumber, reportDate, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp = new JScrollPane(jtable);
                    jframe.add(sp);
                    sp.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp, "Counting Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 1:

                    // for category
                    String nameCategory = JOptionPane.showInputDialog("For which category ?");

                    // if clicked "cancel"
                    if (nameCategory == null) {
                        Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                        return;
                    }

                    Report report2 = inventoryController.CategoryCountingReport(nameCategory);
                    if (report2 == null) {
                        JOptionPane.showInternalMessageDialog(null, "No products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel2 = new DefaultTableModel(column, 0);
                    JTable jtable2 = new JTable(tableModel2);
                    jtable2.setRowHeight(20);
                    jtable2.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable2 = new TableRowSorter<>(tableModel2);
                    jtable2.setRowSorter(sortTable2);

                    DefaultTableCellRenderer rowRenderer2 = new DefaultTableCellRenderer();
                    rowRenderer2.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable2.setDefaultRenderer(Object.class, rowRenderer2);

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
                        tableModel2.addRow(new Object[]{reportType2, reportNumber2, reportDate2, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp2 = new JScrollPane(jtable2);
                    jframe.add(sp2);
                    sp2.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp2, "Counting Report", JOptionPane.PLAIN_MESSAGE);

                    break;
                case 2:
                    // for specific item
                    String nameSpecific = JOptionPane.showInputDialog("What is the catalog number ?");

                    // if clicked "cancel"
                    if (nameSpecific == null) {
                        Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                        return;
                    }

                    Report report3 = inventoryController.ItemCountingReport(nameSpecific);
                    if (report3 == null) {
                        JOptionPane.showInternalMessageDialog(null, "No products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel3 = new DefaultTableModel(column, 0);
                    JTable jtable3 = new JTable(tableModel3);
                    jtable3.setRowHeight(20);
                    jtable3.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable3 = new TableRowSorter<>(tableModel3);
                    jtable3.setRowSorter(sortTable3);

                    DefaultTableCellRenderer rowRenderer3 = new DefaultTableCellRenderer();
                    rowRenderer3.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable3.setDefaultRenderer(Object.class, rowRenderer3);

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
                        tableModel3.addRow(new Object[]{reportType3, reportNumber3, reportDate3, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp3 = new JScrollPane(jtable3);
                    jframe.add(sp3);
                    sp3.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp3, "Counting Report", JOptionPane.PLAIN_MESSAGE);

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
            //Report issuance options
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

            //titles of the Columns in the table
            String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Amount"};
            Report report = null;
            String reportType;
            String reportNumber;
            String reportDate;
            String reportString = "";

            switch (choiceNum) {
                case 0:
                    // for all products
                    report = inventoryController.FullDefectiveReport();
                    if (report == null) {
                        JOptionPane.showInternalMessageDialog(null, "No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(column, 0);
                    JTable jtable = new JTable(tableModel);
                    jtable.setRowHeight(20);
                    jtable.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable = new TableRowSorter<>(tableModel);
                    jtable.setRowSorter(sortTable);

                    DefaultTableCellRenderer rowRenderer = new DefaultTableCellRenderer();
                    rowRenderer.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable.setDefaultRenderer(Object.class, rowRenderer);

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
                        tableModel.addRow(new Object[]{reportType, reportNumber, reportDate, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp = new JScrollPane(jtable);
                    jframe.add(sp);
                    sp.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp, "Defective Report", JOptionPane.PLAIN_MESSAGE);
                    break;

                case 1:
                    // for category
                    String nameCategory = JOptionPane.showInputDialog("For which category ?");

                    // if clicked "cancel"
                    if (nameCategory == null) {
                        Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                        return;
                    }

                    Report report2 = inventoryController.CategoryDefectiveReport(nameCategory);
                    if (report2 == null) {
                        JOptionPane.showInternalMessageDialog(null, "No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel2 = new DefaultTableModel(column, 0);
                    JTable jtable2 = new JTable(tableModel2);
                    jtable2.setRowHeight(20);
                    jtable2.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable2 = new TableRowSorter<>(tableModel2);
                    jtable2.setRowSorter(sortTable2);

                    DefaultTableCellRenderer rowRenderer2 = new DefaultTableCellRenderer();
                    rowRenderer2.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable2.setDefaultRenderer(Object.class, rowRenderer2);

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
                        tableModel2.addRow(new Object[]{reportType2, reportNumber2, reportDate2, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp2 = new JScrollPane(jtable2);
                    jframe.add(sp2);
                    sp2.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp2, "Defective Report", JOptionPane.PLAIN_MESSAGE);
                    break;

                case 2:
                    // for specific item
                    String nameSpecific = JOptionPane.showInputDialog("What is the catalog number ?");

                    // if clicked "cancel"
                    if (nameSpecific == null) {
                        Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                        return;
                    }

                    Report report3 = inventoryController.ItemDefectiveReport(nameSpecific);
                    if (report3 == null) {
                        JOptionPane.showInternalMessageDialog(null, "No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }

                    DefaultTableModel tableModel3 = new DefaultTableModel(column, 0);
                    JTable jtable3 = new JTable(tableModel3);
                    jtable3.setRowHeight(20);
                    jtable3.setFont(new Font("Arial", Font.PLAIN, 10));

                    TableRowSorter<TableModel> sortTable3 = new TableRowSorter<>(tableModel3);
                    jtable3.setRowSorter(sortTable3);

                    DefaultTableCellRenderer rowRenderer3 = new DefaultTableCellRenderer();
                    rowRenderer3.setBackground(Color.LIGHT_GRAY.brighter());
                    jtable3.setDefaultRenderer(Object.class, rowRenderer3);

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
                        tableModel3.addRow(new Object[]{reportType3, reportNumber3, reportDate3, columns[0], columns[1]});
                        ;
                    }

                    JScrollPane sp3 = new JScrollPane(jtable3);
                    jframe.add(sp3);
                    sp3.setPreferredSize(new Dimension(1000, 400));
                    JOptionPane.showMessageDialog(null, sp3, "Defective Report", JOptionPane.PLAIN_MESSAGE);
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
            //Discount update options
            String[] options = {"For all products", "For Category", "For specific product", "Return"};
            // Create a custom panel with FlowLayout
            JLabel label = new JLabel();
            label.setText("Which discount to provide ?");
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(label);

            int choiceNum = JOptionPane.showOptionDialog(null, panel, "Discount Update", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choiceNum == -1) {
                return;
            }

            String[] updateDiscountOptions = {"Percentage Discount", "Standard discount", "Return"};
            // Create a custom panel with FlowLayout
            JLabel updateDiscountLabel = new JLabel();
            updateDiscountLabel.setText("Discount Type ?");
            JPanel UpdateDiscountPanel = new JPanel(new FlowLayout());
            UpdateDiscountPanel.add(updateDiscountLabel);

            boolean validInput = false;
            Integer discountSize = null;
            String categoryName = null;
            String catalonNumber = null;


            switch (choiceNum) {
                // for all the products
                case 0:
                    int choiceNum1 = JOptionPane.showOptionDialog(null, UpdateDiscountPanel, "Discount Update", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, updateDiscountOptions, updateDiscountOptions[0]);
                    if (choiceNum1 == -1) {
                        return;
                    }
                    switch (choiceNum1) {

                        case 0:
                            validInput = false;
                            discountSize = null;
                            while (!validInput) {
                                String discountSizeInput = JOptionPane.showInputDialog("What is the size of the discount ?");

                                // if clicked "cancel"
                                if (discountSizeInput == null) {
                                    Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                discountSize = Integer.parseInt(discountSizeInput);

                                // Check if the input consists only of numeric characters
                                if ((discountSize < 100) && (discountSize > 0)) {
                                    validInput = true;
                                } else {
                                    JOptionPane.showInternalMessageDialog(null, "Invalid input! Please enter a number between 1-99 ", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            inventoryController.FullPercentageDiscount(discountSize);
                            JOptionPane.showInternalMessageDialog(null, "Discount updated", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case 1:
                            validInput = false;
                            discountSize = null;
                            while (!validInput) {
                                String discountSizeInput = JOptionPane.showInputDialog("What is the size of the discount ?");

                                // if clicked "cancel"
                                if (discountSizeInput == null) {
                                    Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                discountSize = Integer.parseInt(discountSizeInput);

                                // Check if the input consists only of numeric characters
                                if ((discountSize < 100) && (discountSize > 0)) {
                                    validInput = true;
                                } else {
                                    JOptionPane.showInternalMessageDialog(null, "Invalid input! Please enter a number between 1-99 ", "Error", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                            inventoryController.FullStandardDiscount(discountSize);
                            JOptionPane.showInternalMessageDialog(null, "Discount updated", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case 2:
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
                    break;


                case 1:
                    // for category
                    int choiceNum2 = JOptionPane.showOptionDialog(null, UpdateDiscountPanel, "Discount Update", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, updateDiscountOptions, updateDiscountOptions[0]);
                    if (choiceNum2 == -1) {
                        return;
                    }
                    switch (choiceNum2) {
                        case 0:
                            //Percentage Discount
                            validInput = false;
                            discountSize = null;
                            while (!validInput) {
                                String discountSizeInput = JOptionPane.showInputDialog("What is the size of the discount ?");

                                // if clicked "cancel"
                                if (discountSizeInput == null) {
                                    Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                discountSize = Integer.parseInt(discountSizeInput);

                                // Check if the input consists only of numeric characters
                                if ((discountSize < 100) && (discountSize > 0)) {
                                    validInput = true;
                                } else {
                                    JOptionPane.showInternalMessageDialog(null, "Invalid input! Please enter a number between 1-99 ", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            categoryName = JOptionPane.showInputDialog("What category to update discount for ?");

                            // if clicked "cancel"
                            if (categoryName == null) {
                                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                if (window instanceof JDialog) {
                                    JDialog dialog = (JDialog) window;
                                    dialog.dispose();
                                }
                                return;
                            }

                            inventoryController.CategoryPercentageDiscount(discountSize, categoryName);
                            JOptionPane.showInternalMessageDialog(null, "Discount updated", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case 1:
                            //Standard Discount
                            validInput = false;
                            discountSize = null;
                            while (!validInput) {
                                String discountSizeInput = JOptionPane.showInputDialog("What is the size of the discount ?");

                                // if clicked "cancel"
                                if (discountSizeInput == null) {
                                    Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                discountSize = Integer.parseInt(discountSizeInput);

                                // Check if the input consists only of numeric characters
                                if ((discountSize < 100) && (discountSize > 0)) {
                                    validInput = true;
                                } else {
                                    JOptionPane.showInternalMessageDialog(null, "Invalid input! Please enter a number between 1-99 ", "Error", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                            categoryName = JOptionPane.showInputDialog("What category to update discount for ?");

                            // if clicked "cancel"
                            if (categoryName == null) {
                                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                if (window instanceof JDialog) {
                                    JDialog dialog = (JDialog) window;
                                    dialog.dispose();
                                }
                                return;
                            }

                            inventoryController.CategoryStandardDiscount(discountSize, categoryName);
                            JOptionPane.showInternalMessageDialog(null, "Discount updated", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case 2:
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
                    break;


                case 2:
                    // for specific product
                    int choiceNum3 = JOptionPane.showOptionDialog(null, UpdateDiscountPanel, "Discount Update", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, updateDiscountOptions, updateDiscountOptions[0]);
                    if (choiceNum3 == -1) {
                        return;
                    }
                    switch (choiceNum3) {
                        case 0:
                            //Percentage Discount
                            validInput = false;
                            discountSize = null;
                            while (!validInput) {
                                String discountSizeInput = JOptionPane.showInputDialog("What is the size of the discount ?");

                                // if clicked "cancel"
                                if (discountSizeInput == null) {
                                    Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                discountSize = Integer.parseInt(discountSizeInput);

                                // Check if the input consists only of numeric characters
                                if ((discountSize < 100) && (discountSize > 0)) {
                                    validInput = true;
                                } else {
                                    JOptionPane.showInternalMessageDialog(null, "Invalid input! Please enter a number between 1-99 ", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            catalonNumber = JOptionPane.showInputDialog("What is the catalog number of the product to update the discount for ?");

                            // if clicked "cancel"
                            if (catalonNumber == null) {
                                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                if (window instanceof JDialog) {
                                    JDialog dialog = (JDialog) window;
                                    dialog.dispose();
                                }
                                return;
                            }

                            inventoryController.SpecificPercentageDiscount(discountSize, catalonNumber);
                            JOptionPane.showInternalMessageDialog(null, "Discount updated", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case 1:
                            //Standard Discount
                            validInput = false;
                            discountSize = null;
                            while (!validInput) {
                                String discountSizeInput = JOptionPane.showInputDialog("What is the size of the discount ?");

                                // if clicked "cancel"
                                if (discountSizeInput == null) {
                                    Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                    if (window instanceof JDialog) {
                                        JDialog dialog = (JDialog) window;
                                        dialog.dispose();
                                    }
                                    return;
                                }

                                discountSize = Integer.parseInt(discountSizeInput);

                                // Check if the input consists only of numeric characters
                                if ((discountSize < 100) && (discountSize > 0)) {
                                    validInput = true;
                                } else {
                                    JOptionPane.showInternalMessageDialog(null, "Invalid input! Please enter a number between 1-99 ", "Error", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                            catalonNumber = JOptionPane.showInputDialog("What is the catalog number of the product to update the discount for ?");

                            // if clicked "cancel"
                            if (catalonNumber == null) {
                                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                if (window instanceof JDialog) {
                                    JDialog dialog = (JDialog) window;
                                    dialog.dispose();
                                }
                                return;
                            }

                            inventoryController.SpecificStandardDiscount(discountSize, catalonNumber);
                            JOptionPane.showInternalMessageDialog(null, "Discount updated", "Alert", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case 2:
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


        //provide price history report
        else if (e.getSource() == PriceHistoryReportButton) {
            Report report = null;
            String catalogNumber = JOptionPane.showInputDialog("What is the catalog number ?");

            // if clicked "cancel"
            if (catalogNumber == null) {
                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                if (window instanceof JDialog) {
                    JDialog dialog = (JDialog) window;
                    dialog.dispose();
                }
                return;
            }

            report = inventoryController.priceHistoryReport(catalogNumber);
            if (report == null)
                JOptionPane.showInternalMessageDialog(null, "No defective products in the store", "Alert", JOptionPane.INFORMATION_MESSAGE);

            else {
                String column[] = {"Report Type", "Report Number", "Report Date", "Catalog Number", "Buy Price", "Sell Price"};
                String reportType;
                String reportNumber;
                String reportDate;
                String reportString = "";
                Item reportItem = null;
                double buyPrice = 0;
                double sellPrice = 0;

                DefaultTableModel tableModel = new DefaultTableModel(column, 0);
                JTable jtable = new JTable(tableModel);
                jtable.setRowHeight(20);
                jtable.setFont(new Font("Arial", Font.PLAIN, 10));

                TableRowSorter<TableModel> sortTable = new TableRowSorter<>(tableModel);
                jtable.setRowSorter(sortTable);

                DefaultTableCellRenderer rowRenderer = new DefaultTableCellRenderer();
                rowRenderer.setBackground(Color.LIGHT_GRAY.brighter());
                jtable.setDefaultRenderer(Object.class, rowRenderer);

                reportType = report.getType().toString();
                reportNumber = Integer.toString(report.getReportNum());
                reportDate = report.getReportDate().toString();

                reportItem = inventoryController.findItemByCatalogNum(catalogNumber);
                reportString = reportItem.getPriceHistory().toString();


                String lines3[] = reportString.split("\n");
                for (String line : lines3) {
                    if (line.contains("Buy price:")) {
                        int start = line.indexOf("Buy price:") + 11;
                        int end = line.indexOf(",", start);
                        String buyPriceStr = line.substring(start, end).trim();
                        buyPrice = Double.parseDouble(buyPriceStr);
                    }
                    if (line.contains("sell price:")) {
                        int start = line.indexOf("sell price:") + 12;
                        int end = line.indexOf(",", start);
                        String sellPriceStr = line.substring(start, end).trim();
                        sellPrice = Double.parseDouble(sellPriceStr);
                    }

                    // Add the columns as a new row to the table model
                    tableModel.addRow(new Object[]{reportType, reportNumber, reportDate, catalogNumber, buyPrice, sellPrice});

                }
                JScrollPane sp = new JScrollPane(jtable);
                jframe.add(sp);
                sp.setPreferredSize(new Dimension(1000, 400));
                JOptionPane.showMessageDialog(null, sp, "Price History", JOptionPane.PLAIN_MESSAGE);
            }
        }


        //move defective product to storage and tag it as "defective"
        else if (e.getSource() == InsertDefectiveButton) {
            boolean validInput = false;
            String defectedSerialNumberInput = null;
            while (!validInput) {
                defectedSerialNumberInput = JOptionPane.showInputDialog("What is the serial number for the item to be set as defected ?");

                // if clicked "cancel"
                if (defectedSerialNumberInput == null) {
                    Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog) window;
                        dialog.dispose();
                    }
                    return;
                }

                // Check if the input consists only of numeric characters
                if (defectedSerialNumberInput.matches("\\d+")) {
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }
            int defectedSerialNumber = Integer.parseInt(defectedSerialNumberInput);
            inventoryController.moveSpecificItemToDefectiveMapper(defectedSerialNumber);
            JOptionPane.showInternalMessageDialog(null, "Item numbered " + defectedSerialNumber + " has been set as defected and moved into the warehouse storage", "Alert", JOptionPane.INFORMATION_MESSAGE);
        }


        //print all the products in the inventory
        else if (e.getSource() == PrintFullInventoryButton) {
            String column[] = {"Catalog Number", "Item ID", "Expiration Date", "isDefected", "location", "Buy Price", "Sell Price", "Minimum Qauntity"};

            String catalogNumber;
            Integer serialNumber;
            String expriryDate;
            String isDefected;
            String location;
            double buyPrice;
            double sellPrice;
            int minimumQantity;


            DefaultTableModel tableModel = new DefaultTableModel(column, 0);
            JTable jtable = new JTable(tableModel);
            jtable.setRowHeight(20);
            jtable.setFont(new Font("Arial", Font.PLAIN, 10));

            TableRowSorter<TableModel> sortTable = new TableRowSorter<>(tableModel);
            jtable.setRowSorter(sortTable);

            DefaultTableCellRenderer rowRenderer = new DefaultTableCellRenderer();
            rowRenderer.setBackground(Color.LIGHT_GRAY.brighter());
            jtable.setDefaultRenderer(Object.class, rowRenderer);


            for (Item item : inventoryController.getItemMapper().findAll()) {

                for (specificItem specificItem : inventoryController.getSpecificItemMapper().findByCatalogNum(item.getCatalogNum())) {
                    catalogNumber = specificItem.getCatalogNum();
                    serialNumber = specificItem.getSerialNumber();
                    expriryDate = String.valueOf(specificItem.getDate());
                    isDefected = String.valueOf(specificItem.isDefected());
                    location = String.valueOf(specificItem.getLocation());
                    buyPrice = item.getBuyPrice();
                    sellPrice = item.getSellPrice();
                    minimumQantity = item.getMinQuantity();
//                    String lines[] = reportString.split("\n");

                    // Add all the variables as a new row to the table model
                    tableModel.addRow(new Object[]{catalogNumber, serialNumber, expriryDate, isDefected, location, buyPrice, sellPrice, minimumQantity});

                }
            }

            JScrollPane sp = new JScrollPane(jtable);
            jframe.add(sp);
            sp.setPreferredSize(new Dimension(1000, 400));
            JOptionPane.showMessageDialog(null, sp, "Inventory Products", JOptionPane.PLAIN_MESSAGE);

        }
        else if (e.getSource() == this.PrintAllShortageOrder) {
            OrderMapper pb = new OrderMapper();


            // Create an instance of FixedDaySupplierMapper


            // Get the data for the jTable
            String data = pb.getAllOrdersAsString();

            // Create a DefaultTableModel with empty rows and column headers
            DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Order Number", "Supplier", "Cost", "Store Number", "Status"});

            // Split the data into lines
            String[] lines = data.split("\n");

            // Iterate over the lines and add each row to the table model
            for (String line : lines) {
                // Split the line into columns
                String[] columns = line.split("\t");

                // Add the columns as a new row to the table model
                tableModel.addRow(columns);
            }


            // Create a JTable using the table model
            JTable jTable = new JTable(tableModel);
            jTable = ad_table(jTable);

            TableRowSorter<TableModel> sortTable = new TableRowSorter<>(tableModel);
            jTable.setRowSorter(sortTable);

            DefaultTableCellRenderer rowRenderer = new DefaultTableCellRenderer();
            rowRenderer.setBackground(Color.LIGHT_GRAY.brighter());
            jTable.setDefaultRenderer(Object.class, rowRenderer);


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
            // Create a JPanel to hold the JTable and the back button
            JPanel panel = new JPanel();
            panel.add(new JScrollPane(jTable));

            panel.add(backButton);

            // Display the JPanel in a JFrame
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel);
            frame.pack();

            frame.setVisible(true);

        }
        else if (e.getSource() == PrintAllPeriodOrder) {
            PeriodicOrderMapper pb = new PeriodicOrderMapper();


            // Create an instance of FixedDaySupplierMapper


            // Get the data for the jTable
            String data = pb.getTableString();


            // Create a DefaultTableModel with empty rows and column headers
            DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Order Number", "Supplier", "Cost", "Days to Cycle", "Days Left until append new order"});


            // Split the data into lines
            String[] lines = data.split("\n");


            // Iterate over the lines and add each row to the table model
            for (String line : lines) {
                // Split the line into columns
                String[] columns = line.split(", ");

                // Add the columns as a new row to the table model
                tableModel.addRow(columns);
            }


            // Create a JTable using the table model
            JTable jTable = new JTable(tableModel);
            jTable = this.ad_table(jTable);

            TableRowSorter<TableModel> sortTable = new TableRowSorter<>(tableModel);
            jTable.setRowSorter(sortTable);

            DefaultTableCellRenderer rowRenderer = new DefaultTableCellRenderer();
            rowRenderer.setBackground(Color.LIGHT_GRAY.brighter());
            jTable.setDefaultRenderer(Object.class, rowRenderer);


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
            // Create a JPanel to hold the JTable and the back button
            JPanel panel = new JPanel();
            panel.add(new JScrollPane(jTable));

            panel.add(backButton);

            // Display the JPanel in a JFrame
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel);
            frame.pack();

            frame.setVisible(true);


        }
        else if (e.getSource() == this.addPeriodOrderButton) {


            System.out.println("add new Period Order:");

            Map<Item, Integer> itemlist1 = new HashMap<Item, Integer>();

            String store_numberInput1 = JOptionPane.showInputDialog("store number:");

            // if clicked "cancel"
            if (store_numberInput1 == null) {
                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                if (window instanceof JDialog) {
                    JDialog dialog = (JDialog) window;
                    dialog.dispose();
                }
                return;
            }

            store_numberInput1 = checkNumber(store_numberInput1);
            int store_number1 = Integer.parseInt(store_numberInput1);

            String days = JOptionPane.showInputDialog("number of days until new cycle:");

            // if clicked "cancel"
            if (days == null) {
                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                if (window instanceof JDialog) {
                    JDialog dialog = (JDialog) window;
                    dialog.dispose();
                }
                return;
            }

            days = checkNumber(days);
            int days1 = Integer.parseInt(days);

            String supplier_id = JOptionPane.showInputDialog("Supplier id:");

            // if clicked "cancel"
            if (supplier_id == null) {
                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                if (window instanceof JDialog) {
                    JDialog dialog = (JDialog) window;
                    dialog.dispose();
                }
                return;
            }

            supplier_manger.update_suppliers();

            Supplier supplier1 = supplier_manger.get_supplier_by_id(supplier_id);

            while (true) {
                String optionInput = JOptionPane.showInputDialog(
                        "1. add item to list\n" +
                                "2. done and ready to go to orders"
                );
                optionInput = checkNumber(optionInput);
                int option_11 = Integer.parseInt(optionInput);

                // Option 1: add item to the order list
                if (option_11 == 1) {
                    List<Item> itemList_sub= new ArrayList<>();
                    int op = 1;
                    for (Item item : supplier1.getItems().keySet()) {
                        if (supplier1.getItems().keySet().size() > 0) {
                            itemList_sub.add(item);
                            op++;
                        }
                    }
                    StringBuilder itemsPrompt = new StringBuilder();
                    for (int i = 0; i < itemList_sub.size(); i++) {
                        itemsPrompt.append((i + 1)).append(". ").append(itemList_sub.get(i)).append("\n");
                    }
                    String item_numberInput = JOptionPane.showInputDialog(
                            "Enter the number of the item you want to add:\n" + itemsPrompt.toString()
                    );

                    // if clicked "cancel"
                    if (item_numberInput == null) {
                        Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();
                        }
                        return;
                    }

                    item_numberInput = checkNumber(item_numberInput);
                    int item_number = Integer.parseInt(item_numberInput);

                    int count = 1;
                    for (Item item : itemList_sub) {
                        if (count == item_number) {
                            String quantityInput = JOptionPane.showInputDialog("Enter the quantity:");
                            quantityInput = checkNumberWithDot(quantityInput);
                            int quantity = Integer.parseInt(quantityInput);
                            itemlist1.put(item, quantity);
                            break;
                        }
                        count++;
                    }
                }

                if (option_11 == 2) {
                    if (!orderManger.period_order(supplier1, itemlist1, store_number1, days1)) {
                        System.out.println("failed to make an order make sure that the items can be provided");
                    }
                    break;
                }
            }
        }
        else if (e.getSource() == this.UpdatePeriodOrder) {
            String id = JOptionPane.showInputDialog("Enter a period id:");
            if (!orderManger.contain_Period_order(id)) {
                JOptionPane.showMessageDialog(null, "ID not in the system");
            } else {
                Period_Order periodOrder = orderManger.get_period_order_by_id(id);
                if (!orderManger.can_update_period_order(periodOrder)) {
                    JOptionPane.showMessageDialog(null, "Can't update a day before");
                } else {
                    String choice_14 = JOptionPane.showInputDialog("Choose what you want to do:\n" +
                            "1. Delete period order\n" +
                            "2. Add item to order");
                    int option_14 = 0;
                    while (true) {
                        try {
                            option_14 = Integer.parseInt(choice_14);
                            if (option_14 < 1 || option_14 > 2) {
                                JOptionPane.showMessageDialog(null, "Please enter a valid option");
                                choice_14 = JOptionPane.showInputDialog("Choose what you want to do:\n" +
                                        "1. Delete period order\n" +
                                        "2. Add item to order");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException ignored) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid option");
                            choice_14 = JOptionPane.showInputDialog("Choose what you want to do:\n" +
                                    "1. Delete period order\n" +
                                    "2. Add item to order");
                        }
                    }

                    switch (option_14) {
                        case 1:
                            this.orderManger.delete_a_period_order(id);
                            break;
                        case 2:
                            int op = 0;
                            List<Item> itemList2 = new ArrayList<>();
                            Supplier supplier2 = supplier_manger.get_supplier_by_id(periodOrder.getSupplier().getSupplierID());
                            for (Item item : supplier2.getItems().keySet()) {
                                if (supplier2.getItems().keySet().size() > 0) {
                                    op++;
                                    String itemDetails = op + ". " + item.toString();
                                    itemList2.add(item);
                                    JOptionPane.showMessageDialog(null, itemDetails);
                                }
                            }

                            String item_numberInput = JOptionPane.showInputDialog("Enter the number of the item you want to add:");

                            // if clicked "cancel"
                            if (item_numberInput == null) {
                                Window window = SwingUtilities.getWindowAncestor(buttonPanel);
                                if (window instanceof JDialog) {
                                    JDialog dialog = (JDialog) window;
                                    dialog.dispose();
                                }
                                return;
                            }

                            item_numberInput = checkNumber(item_numberInput);
                            int item_number = Integer.parseInt(item_numberInput);
                            int count = 1;
                            for (Item item : itemList2) {
                                if (count == item_number) {
                                    String quantityInput = JOptionPane.showInputDialog("Enter the quantity:");
                                    quantityInput = checkNumberWithDot(quantityInput);
                                    int quantity = Integer.parseInt(quantityInput);
                                    String id40 = "" + periodOrder.getOrderNum();
                                    orderManger.update_add_to_period_order(id40, item, quantity, supplier2.getItems().get(item).getSecond());
                                    break;
                                }
                                count++;
                            }
                    }
                }
            }


        }
        else if (e.getSource() == ReturnToManger) {
            jframe.dispose();
            new Manger();
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


    private JTable ad_table(JTable jTable){
        TableColumnModel columnModel = jTable.getColumnModel();


        for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
            TableColumn column = columnModel.getColumn(columnIndex);
            int preferredWidth = column.getPreferredWidth();
            int maxWidth = column.getMaxWidth();

            // Get the renderer for the column
            TableCellRenderer headerRenderer = column.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = jTable.getTableHeader().getDefaultRenderer();
            }
            Component headerComponent = headerRenderer.getTableCellRendererComponent(
                    jTable, column.getHeaderValue(), false, false, -1, columnIndex);

            // Determine the maximum width needed for the column
            int headerWidth = headerComponent.getPreferredSize().width;
            int cellWidth = jTable.getCellRenderer(0, columnIndex)
                    .getTableCellRendererComponent(jTable,
                            jTable.getValueAt(0, columnIndex), false, false, 0, columnIndex)
                    .getPreferredSize().width;
            int maxWidthNeeded = Math.max(headerWidth, cellWidth) + 10; // Add some padding

            // Adjust the preferred width and maximum width of the column
            column.setPreferredWidth(Math.min(maxWidthNeeded, preferredWidth));
            column.setMaxWidth(maxWidth > 0 ? Math.min(maxWidthNeeded, maxWidth) : maxWidthNeeded);
        }
        return jTable;


    }



    public static String checkName(String input) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (Character.isDigit(input.charAt(i))) {
                    System.out.println("A name has to be letters only");
                    break;
                } else {
                    counter++;
                }
            }
            if (counter == input.length()) {
                return input;
            } else {
                System.out.println("name:");
                input = scanner.next();
            }
        }
    }

    public static String checkNumber(String input) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    System.out.println("has to be numbers only");
                    break;
                } else {
                    counter++;
                }
            }
            if (counter == input.length()) {
                return input;
            } else {
                System.out.println("number:");
                input = scanner.next();
            }
        }
    }





}


