package services;

import DataAccesObject.FixedDaySupplierMapper;
import DataAccesObject.NonDeliveringSupplierMapper;
import DataAccesObject.NonFixedDaySupplierMapper;
import Domain.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Supplier_Manger_GUI   implements ActionListener {

    private JFrame jframe;

    private  JButton addnewSupplierButton;
    private  JButton removeSupplierButton;

    private  JButton UpdateContactToSupplier;
    private  JButton addItemtoSupplier;
    private  JButton removeItemFromSupplier;
    private  JButton UpdateitemonContract;
    private  JButton print_all_suppliers;
    private  JButton print_supplierby_id;

    private  JPanel buttonPanel;

    private Supplier_Manger supplier_manger;



    public Supplier_Manger_GUI() {
        jframe = new JFrame();
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("Supplier Manager");
        JLabel label = new JLabel("Supplier  Menu");
        label.setFont(new Font("Arial", Font.BOLD, 40)); // Customize the font and size as desired
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(new Color(220, 20, 60));
        jframe.add(label, BorderLayout.NORTH);





        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(3, 3, 3));

        addnewSupplierButton = createStyledButton("Add new Supplier");
        removeSupplierButton = createStyledButton("Remove Supplier");
        UpdateContactToSupplier = createStyledButton("Update Contact to Supplier");
        addItemtoSupplier = createStyledButton("Add Item to Supplier");
        removeItemFromSupplier = createStyledButton("Remove Item from Supplier");
        UpdateitemonContract = createStyledButton("Update Item on Contract");
        print_all_suppliers = createStyledButton("Present All Suppliers");
        print_supplierby_id = createStyledButton("Get Supplier by ID");

        // Register the ActionListener for each button
        addnewSupplierButton.addActionListener(this);
        removeSupplierButton.addActionListener(this);
        UpdateContactToSupplier.addActionListener(this);
        addItemtoSupplier.addActionListener(this);
        removeItemFromSupplier.addActionListener(this);
        UpdateitemonContract.addActionListener(this);
        print_all_suppliers.addActionListener(this);
        print_supplierby_id.addActionListener(this);

        buttonPanel.add(addnewSupplierButton);
        buttonPanel.add(removeSupplierButton);
        buttonPanel.add(UpdateContactToSupplier);
        buttonPanel.add(addItemtoSupplier);
        buttonPanel.add(removeItemFromSupplier);
        buttonPanel.add(UpdateitemonContract);
        buttonPanel.add(print_all_suppliers);
        buttonPanel.add(print_supplierby_id);

        jframe.add(buttonPanel);
        jframe.setVisible(true);
        this.supplier_manger=new Supplier_Manger();





    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(220, 20, 60)); // Red button background
        button.setForeground(Color.WHITE); // White text color
        button.setFocusPainted(false); // Remove focus border

        // Set font style
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        button.setFont(buttonFont);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 99, 71)); // Orange hover background
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 20, 60)); // Red button background
            }
        });

        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //**************this is the option case switch*****************************************************************/
        if(e.getSource()==addnewSupplierButton){

            String name_suppleir=   JOptionPane.showInputDialog("What is the Supplier name:");

            if (name_suppleir == null) {
                return; // exit the method if the user cancels
            }


            String business_id = null;
            boolean validInput = false;


            //this cheak that the user give a good input
            while (!validInput) {
                String input = JOptionPane.showInputDialog("business_id:");

                // Check if the input consists only of numeric characters
                if (input.matches("\\d+")) {
                    business_id = input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }



            if (business_id  == null) {
                return; // exit the method if the user cancels
            }




            String[] options = {"1. SHOTEF", "2. SHOTEF+30", "3. SHOTEF+60"};
            String message = "Payment method:";
            int paymentNum = JOptionPane.showOptionDialog(null, message, "Payment Method", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);



            if(paymentNum ==-1){
                return;
            }



            String supplier_ID=null;
            validInput=false;

            while (!validInput) {
                String input = JOptionPane.showInputDialog("Supplier_ID:");

                // Check if the input consists only of numeric characters
                if (input.matches("\\d+")) {
                    supplier_ID = input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }
            if(supplier_ID==null){
                return;
            }



            String contact_name =null;
            validInput=false;
            while (!validInput) {
                String input = JOptionPane.showInputDialog("Contact Name:");

                // Check if the input consists only of alphabetic characters
                if (input.matches("[a-zA-Z]+")) {
                    contact_name= input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                }
            }

            if(contact_name==null){
                return;
            }



            String phone_number =null;
            validInput=false;

            while (!validInput) {
                String input = JOptionPane.showInputDialog("phone_number : ");

                // Check if the input consists only of numeric characters
                if (input.matches("\\d+")) {
                    phone_number= input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }

            if(phone_number==null){
                return;
            }






            String message1 = "Type of the Supplier:";
            String[] options2 = {"1. Non delivery supplier", "2. Delivery supplier on fixed days", "3. Delivery supplier on non fixed days"};

            int type_number = JOptionPane.showOptionDialog(null, message1, "Supplier Type", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);
            ContactPerson con_person = new ContactPerson(contact_name, phone_number);
            switch (type_number) {
                case 0:
                    // Code for option 1 - Non delivery supplier
                    NonDeliveringSupplier supplier = new NonDeliveringSupplier(name_suppleir, business_id, paymentNum , supplier_ID, con_person, null, null);
                    supplier_manger.add_supplier(supplier);
                    break;


                case 1:

                    int number_day = 0;
                    validInput = false;


                    while (!validInput) {
                        String input = JOptionPane.showInputDialog("Enter the day of delivery 1-7:");

                        try {
                            number_day = Integer.parseInt(input);
                            if (number_day >= 1 && number_day <= 7) {
                                validInput = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number between 1 and 7.");
                            }
                        } catch (NumberFormatException eq) {
                            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
                        }
                    }


                    WindowType day_window;
                    day_window = WindowTypeCreater.getwindowtype(number_day);
                    FixedDaySupplier supplier2 = new FixedDaySupplier(day_window, name_suppleir, business_id, paymentNum , supplier_ID, con_person, null, null);
                    supplier_manger.add_supplier(supplier2);
                    break;
                case 2:
                    // Code for option 3 - Delivery supplier on non fixed days



                    String numOfDeliveryDays =null;
                    validInput=false;

                    while (!validInput) {
                        String input = JOptionPane.showInputDialog("Days to delivery:");

                        // Check if the input consists only of numeric characters
                        if (input.matches("\\d+")) {
                            supplier_ID = input;
                            validInput = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                        }
                    }


                    int number = Integer.parseInt(numOfDeliveryDays);



                    NonFixedDaySupplier supplier3 = new NonFixedDaySupplier(number, name_suppleir, business_id, paymentNum - 1, supplier_ID, con_person, null, null);
                    supplier_manger.add_supplier(supplier3);
                    break;
                default:


                    // Code for handling other cases or no selection



                    break;
            }













        } else if (e.getSource()==removeSupplierButton) {
            boolean valid =false;
            while ((!valid)) {
                String input = JOptionPane.showInputDialog("Name of the Supplier");

                if (input == null) {
                    return;
                }


                if (!this.supplier_manger.checkExistingSupplierName(input)) {
                    supplier_manger.remove_supplier(input);
                    valid = true;

                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not in the System");
                }
            }



        } else if (e.getSource() ==UpdateContactToSupplier) {

            //first cheak the supplier name
            boolean valid = false;
            String input1 = null;
            while ((!valid)) {
                 input1 = JOptionPane.showInputDialog("Name of the Supplier");

                if (input1 == null) {
                    return;
                }


                if (!this.supplier_manger.checkExistingSupplierName(input1)) {

                    valid = true;

                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not in the System");
                }
            }

            //after cheak that the contact name is a string
            String contact_name = null;
            boolean validInput = false;
            while (!validInput) {
                String input = JOptionPane.showInputDialog("Contact Name:");

                // Check if the input consists only of alphabetic characters
                if (input.matches("[a-zA-Z]+")) {
                    contact_name = input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                }
            }
            if (contact_name == null) {
                return;
            }

            //after cheak that the phone number is a numeric
            String phone_number = null;
            validInput = false;

            while (!validInput) {
                String input = JOptionPane.showInputDialog("phone_number : ");

                // Check if the input consists only of numeric characters
                if (input.matches("\\d+")) {
                    phone_number = input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }

            if (phone_number == null) {
                return;
            }

            //in the end insert it to the system

            supplier_manger.update_contact_preson(input1, contact_name, phone_number);











        } else if (e.getSource()==addItemtoSupplier) {

            //supplier aname cheak
            boolean valid =false;
            String input1 =null;
            while ((!valid)) {
                input1 = JOptionPane.showInputDialog("Name of the Supplier");

                if (input1 == null) {
                    return;
                }


                if (!this.supplier_manger.checkExistingSupplierName(input1)) {

                    valid = true;

                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not in the System");
                }
            }

            //item name cheak


            String item_name = null;
            boolean validInput = false;
            while (!validInput) {
                String input = JOptionPane.showInputDialog("Contact Name:");

                // Check if the input consists only of alphabetic characters
                if (input.matches("[a-zA-Z]+")) {
                    item_name = input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a string value.");
                }
            }

            //catalog number cheak


            String catlog_number = null;
            validInput = false;

            while (!validInput) {
                String input = JOptionPane.showInputDialog("catlog number : ");

                // Check if the input consists only of numeric characters
                if (input.matches("\\d+")) {
                    catlog_number = input;
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }

            if (catlog_number == null) {
                return;
            }



            boolean isDoubleString =false;
            String weight_s=null;
            while(!isDoubleString) {
                weight_s = JOptionPane.showInputDialog("Item weight: ");
                if(weight_s ==null){
                    return;
                }
                isDoubleString = isDoubleString(weight_s);
            }

            double weight = Double.parseDouble(weight_s);




            String catalog = JOptionPane.showInputDialog("catalog name");

            if(catalog ==null){
                return;
            }


            String[] options = {"Regular", "Frozen", "Cold"};
            int selectedOption = JOptionPane.showOptionDialog(null, "Select an option:", "Temperature",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            String temp =String.valueOf(selectedOption);

            TempLevel tempLevel = TempLevel.valueOf(temp);

            String manufacturer= JOptionPane.showInputDialog("manufacturer name:");









            Item item = new Item(item_name, catlog_number, weight, catalog, tempLevel, manufacturer);





            boolean validFloat = false;
            float floatValue = 0;

            while (!validFloat) {
                String input = JOptionPane.showInputDialog("Enter a float value:");

                try {
                    floatValue = Float.parseFloat(input);
                    validFloat = true;
                } catch (NumberFormatException ed) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid float.");
                }
            }


            boolean validInteger = false;
            int integerValue = 0;

            while (!validInteger) {
                String input = JOptionPane.showInputDialog("Enter an integer value fot he amount:");

                try {
                    integerValue = Integer.parseInt(input);
                    validInteger = true;
                } catch (NumberFormatException ek) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
                }
            }
            supplier_manger.add_item_to_supplier( input1, item, integerValue, floatValue);






















        } else if (e.getSource()==removeItemFromSupplier) {
            //supplier aname cheak
            boolean valid =false;
            String input1 =null;
            while ((!valid)) {
                input1 = JOptionPane.showInputDialog("Name of the Supplier");

                if (input1 == null) {
                    return;
                }


                if (!this.supplier_manger.checkExistingSupplierName(input1)) {

                    valid = true;

                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not in the System");
                }
            }




            String catlog_number = null;
            boolean validInput2 = false;

            while (!validInput2) {
                String input = JOptionPane.showInputDialog("catlog number : ");

                // Check if the input consists only of numeric characters
                if (input.matches("\\d+")) {
                    catlog_number = input;
                    validInput2 = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a numeric value.");
                }
            }

            if (catlog_number == null) {
                return;
            }



            //the amount
            boolean validInteger = false;
            int integerValue = 0;

            while (!validInteger) {
                String input = JOptionPane.showInputDialog("Enter an integer value fot he amount:");

                try {
                    integerValue = Integer.parseInt(input);
                    validInteger = true;
                } catch (NumberFormatException ek) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid integer.");
                }
            }


            boolean validDouble = false;
            double doubleValue = 0.0;

            while (!validDouble) {
                String input = JOptionPane.showInputDialog("Enter a double value for the discount:");

                try {
                    doubleValue = Double.parseDouble(input);
                    validDouble = true;
                } catch (NumberFormatException es) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid double.");
                }
            }
            supplier_manger.add_item_discount_to_supplier(input1, catlog_number, integerValue , doubleValue);








        } else if (  e.getSource()==print_all_suppliers) {

                // Create an instance of FixedDaySupplierMapper
                FixedDaySupplierMapper mapper = new FixedDaySupplierMapper();
                NonDeliveringSupplierMapper mapper2 =new NonDeliveringSupplierMapper();
                NonFixedDaySupplierMapper mapper3 =new NonFixedDaySupplierMapper();

                // Get the data for the jTable
                String data = mapper.getAllSuppliersForJTable();
                String data2 =mapper2.getAllSuppliersForJTable();
                String data3 =mapper3.getAllSuppliersForJTable();

                // Create a DefaultTableModel with empty rows and column headers
                DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Supplier ID", "Name", "Business ID", "Payment Method", "Contract Person Name", "Contract Phone Number", "Current Delivery Day"});
                DefaultTableModel tableModel2 = new DefaultTableModel(new Object[][]{}, new String[]{"Supplier ID", "Name", "Business ID", "Payment Method", "Contract Person Name", "Contract Phone Number"});
                DefaultTableModel tableModel3 = new DefaultTableModel(new Object[][]{}, new String[]{"Supplier ID", "Name", "Business ID", "Payment Method", "Contract Person Name", "Contract Phone Number","Days to delivery"});


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
                JTable jTable2 = new JTable(tableModel2);
                JTable jTable3 = new JTable(tableModel2);





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



        } else if (e.getSource()==print_supplierby_id) {
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



            JFrame jFrame2=new JFrame();
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
                }});
            jFrame2.add(backButton, BorderLayout.SOUTH);
            jFrame2.add(detailsTextArea, BorderLayout.CENTER);






            // Set window properties
            jFrame2.setTitle("Supplier Details");
            jFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame2.pack();
            jFrame2.setLocationRelativeTo(null);
            jFrame2.setVisible(true);


        } else if (e.getSource()==UpdateitemonContract) {
            String name_s6 = getInputWithValidation("Name of the supplier:");
            String item_c6 = getInputWithValidation("Catalog number of the item:");
            int amount_6 = Integer.parseInt(getInputWithValidation("Amount:", "integer"));
            double discount_6 = Double.parseDouble(getInputWithValidation("Discount:", "double"));
            supplier_manger.add_item_discount_to_supplier(name_s6, item_c6, amount_6, discount_6);










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
    public static String getInputWithValidation(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message);
            if (input != null && !input.isEmpty()) {
                return input;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
            }
        }
    }

    public static String getInputWithValidation(String message, String type) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message);
            if (input != null && !input.isEmpty()) {
                try {
                    if (type.equalsIgnoreCase("integer")) {
                        Integer.parseInt(input);
                        return input;
                    } else if (type.equalsIgnoreCase("double")) {
                        Double.parseDouble(input);
                        return input;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid " + type + " input. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
            }
        }
    }




}



