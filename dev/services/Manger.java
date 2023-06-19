package services;

import DataAccesObject.*;
import Domain.Order;
import Domain.OrderManger;
import Domain.Supplier_Manger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Manger implements ActionListener {


    private JFrame jframe;


    private JButton print_all_suppliers;
    private JButton print_supplierby_id;
    private JButton get_Shortage_order_by_id;
    private JButton getPeriod_Order_by_id;

    private JButton print_all_orders_shortage;
    private JButton print_all_Period_Orders;


    private JPanel buttonPanel;

    private Supplier_Manger supplier_manger;
    private OrderManger orderManger;

    public Manger() {


        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Supplier Manager");




        JLabel label = new JLabel("Manager Menu");
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Customize the font and size as desired
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        jframe.add(label, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.WHITE);


        print_all_suppliers = createStyledButton("Present All Suppliers");
        print_supplierby_id = createStyledButton("Get Supplier by ID");


        print_all_orders_shortage = createStyledButton("Get all  Order's");
        print_all_Period_Orders = createStyledButton("Period order's");

        getPeriod_Order_by_id = createStyledButton("Get Period order by id's");
        get_Shortage_order_by_id = createStyledButton("Get Order by id's");


        // Register the ActionListener for each button

        print_all_suppliers.addActionListener(this);
        print_supplierby_id.addActionListener(this);
        print_all_orders_shortage.addActionListener(this);
        print_all_Period_Orders.addActionListener(this);
        getPeriod_Order_by_id.addActionListener(this);
        get_Shortage_order_by_id.addActionListener(this);


        buttonPanel.add(print_all_suppliers);
        buttonPanel.add(print_supplierby_id);
        buttonPanel.add(print_all_orders_shortage);
        buttonPanel.add(print_all_Period_Orders);
        buttonPanel.add(getPeriod_Order_by_id);
        buttonPanel.add(get_Shortage_order_by_id);
        buttonPanel.setBackground(new Color(135, 0, 0));

        jframe.add(buttonPanel);
        jframe.setVisible(true);
        this.supplier_manger = new Supplier_Manger();
        this.orderManger = new OrderManger();

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == print_all_suppliers) {

            // Create an instance of FixedDaySupplierMapper
            FixedDaySupplierMapper mapper = new FixedDaySupplierMapper();
            NonDeliveringSupplierMapper mapper2 = new NonDeliveringSupplierMapper();
            NonFixedDaySupplierMapper mapper3 = new NonFixedDaySupplierMapper();

            // Get the data for the jTable
            String data = mapper.getAllSuppliersForJTable();
            String data2 = mapper2.getAllSuppliersForJTable();
            String data3 = mapper3.getAllSuppliersForJTable();

            // Create a DefaultTableModel with empty rows and column headers
            DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Supplier ID", "Name", "Business ID", "Payment Method", "Contract Person Name", "Contract Phone Number", "Current Delivery Day"});
            DefaultTableModel tableModel2 = new DefaultTableModel(new Object[][]{}, new String[]{"Supplier ID", "Name", "Business ID", "Payment Method", "Contract Person Name", "Contract Phone Number"});
            DefaultTableModel tableModel3 = new DefaultTableModel(new Object[][]{}, new String[]{"Supplier ID", "Name", "Business ID", "Payment Method", "Contract Person Name", "Contract Phone Number", "Days to delivery"});


            // Split the data into lines
            String[] lines = data.split("\n");
            String[] lines2 = data2.split("\n");
            String[] lines3 = data3.split("\n");

            // Iterate over the lines and add each row to the table model
            for (String line : lines) {
                // Split the line into columns
                String[] columns = line.split(", ");

                // Add the columns as a new row to the table model
                tableModel.addRow(columns);
            }
            for (String line : lines2) {
                // Split the line into columns
                String[] columns = line.split(", ");

                // Add the columns as a new row to the table model
                tableModel2.addRow(columns);
            }
            for (String line : lines3) {
                // Split the line into columns
                String[] columns = line.split(", ");

                // Add the columns as a new row to the table model
                tableModel3.addRow(columns);
            }

            // Create a JTable using the table model
            JTable jTable = new JTable(tableModel);
            jTable =this.ad_table(jTable);
            JTable jTable2 = new JTable(tableModel2);
            jTable2 =this.ad_table(jTable2);
            JTable jTable3 = new JTable(tableModel3);
            jTable3 =this.ad_table(jTable3);


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
            panel.add(new JScrollPane(jTable2));
            panel.add(new JScrollPane(jTable3));
            panel.add(backButton);

            // Display the JPanel in a JFrame
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel);
            frame.pack();

            frame.setVisible(true);


        } else if (e.getSource() == print_supplierby_id) {
            boolean validInteger = false;
            int integerValue = 0;
            String input = null;

            while (!validInteger) {
                input = JOptionPane.showInputDialog("Enter an integer value fot he amount:");

                try {

                    validInteger = true;
                } catch (NumberFormatException ek) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
                }

            }
            JFrame jFrame2 = new JFrame();
            String str = supplier_manger.get_supplier_by_id(input).toString();
            JTextArea detailsTextArea = new JTextArea();
            detailsTextArea.setEditable(false);

            // Set the supplier details in the text area
            detailsTextArea.setText(str);

            // Set up the layout
            jFrame2.setLayout(new BorderLayout());


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
            jFrame2.add(backButton, BorderLayout.SOUTH);
            jFrame2.add(detailsTextArea, BorderLayout.CENTER);


            // Set window properties
            jFrame2.setTitle("Supplier Details");
            jFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame2.pack();
            jFrame2.setLocationRelativeTo(null);
            jFrame2.setVisible(true);


        } else if (e.getSource() == print_all_Period_Orders) {
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
            jTable =this.ad_table(jTable);






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


        } else if (e.getSource() == print_all_orders_shortage) {
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
            JTable jTable =new JTable(tableModel);
            jTable = ad_table(jTable);






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



    } else if(e.getSource()==get_Shortage_order_by_id)

    {

        boolean validInteger = false;
        int integerValue = 0;
        String input = null;

        while (!validInteger) {
            input = JOptionPane.showInputDialog("Enter an intger as the id of the order you want:");

            try {

                validInteger = true;
            } catch (NumberFormatException ek) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
            }


        }
        if (integerValue == -1) {
            return;
        }

        Order order = orderManger.get_order(input);


        JFrame jFrame2 = new JFrame();

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);

        // Set the supplier details in the text area
        detailsTextArea.setText(order.orderString());

        // Set up the layout
        jFrame2.setLayout(new BorderLayout());


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
        jFrame2.add(backButton, BorderLayout.SOUTH);
        jFrame2.add(detailsTextArea, BorderLayout.CENTER);


        // Set window properties
        jFrame2.setTitle("Supplier Details");
        jFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame2.pack();
        jFrame2.setLocationRelativeTo(null);
        jFrame2.setVisible(true);


    }
        else if(e.getSource()==getPeriod_Order_by_id)

        {

            boolean validInteger = false;
            int integerValue = 0;
            String input = null;

            while (!validInteger) {
                input = JOptionPane.showInputDialog("Enter an intger as the id of the order you want:");

                try {

                    validInteger = true;
                } catch (NumberFormatException ek) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
                }


            }
            if (integerValue == -1) {
                return;
            }

            Order order = orderManger.get_period_order_by_id(input);


            JFrame jFrame2 = new JFrame();

            JTextArea detailsTextArea = new JTextArea();
            detailsTextArea.setEditable(false);

            // Set the supplier details in the text area
            detailsTextArea.setText(order.orderString());

            // Set up the layout
            jFrame2.setLayout(new BorderLayout());


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
            jFrame2.add(backButton, BorderLayout.SOUTH);
            jFrame2.add(detailsTextArea, BorderLayout.CENTER);


            // Set window properties
            jFrame2.setTitle("Supplier Details");
            jFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame2.pack();
            jFrame2.setLocationRelativeTo(null);
            jFrame2.setVisible(true);


        }



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
}
