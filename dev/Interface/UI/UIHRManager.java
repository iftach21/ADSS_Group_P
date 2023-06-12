package Interface.UI;

import Service.HRManagerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UIHRManager {
    static HRManagerService HRManagerService;

    static {
        try {
            HRManagerService = new HRManagerService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UIHRManager(){

        JFrame frame = new JFrame("HR - Manager");
        frame.setSize(900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Creating the Employees menu
        JMenu employeesMenu = new JMenu("Employees");

        //all of the drops for it:
        //------------------------------------------------------------------------------------------------------------------------------------------------------------

        //adding new emp:
        JMenuItem addEmployeeItem = new JMenuItem("Add Employee");
        addEmployeeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddEmployeeDialog();
            }
        });
        employeesMenu.add(addEmployeeItem);


        //add new driver:
        JMenuItem addNewDriverEmployee = new JMenuItem("Add Driver");
        addNewDriverEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewDriverDialog();
            }
        });
        employeesMenu.add(addNewDriverEmployee);


        //fire employee
        JMenuItem removeEmployeeItem = new JMenuItem("Remove Employee");
        removeEmployeeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showfireemployeeDialog();
            }
        });
        employeesMenu.add(removeEmployeeItem);

        //change wage
        JMenuItem changeWageForEmployee = new JMenuItem("Change Wage");
        changeWageForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddwagetoemployeeDialog();
            }
        });
        employeesMenu.add(changeWageForEmployee);

        //change contract
        JMenuItem changeContractForEmployee = new JMenuItem("Change Contract");
        changeContractForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChangeemployeecontractDialog();
            }
        });
        employeesMenu.add(changeContractForEmployee);


        //update bank Number
        JMenuItem changeBankNumForEmployee = new JMenuItem("Change Bank number");
        changeBankNumForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateemployeesbankDialog();
            }
        });
        employeesMenu.add(changeBankNumForEmployee);

        //change personal info
        JMenuItem changePersonalInfoForEmployee = new JMenuItem("Change personal Info");
        changePersonalInfoForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showsetPersonalinfoDialog();
            }
        });
        employeesMenu.add(changePersonalInfoForEmployee);


        //add Available for employee
        JMenuItem AddAvailableForEmployee = new JMenuItem("Add when can work");
        AddAvailableForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddavilableforemployeeDialog();
            }
        });
        employeesMenu.add(AddAvailableForEmployee);

        //remove Available for employee
        JMenuItem removeAvailableForEmployee = new JMenuItem("Remove when can work");
        removeAvailableForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showremoveavalbleforemployeeDialog();
            }
        });
        employeesMenu.add(removeAvailableForEmployee);


        //add prof for employee
        JMenuItem addProfForEmployee = new JMenuItem("Add prof for Employee");
        addProfForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddnewproforemployeeDialog();
            }
        });
        employeesMenu.add(addProfForEmployee);

        //remove prof for employee
        JMenuItem removeProfForEmployee = new JMenuItem("Remove prof for Employee");
        removeProfForEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showremoveprofforemployeeDialog();
            }
        });
        employeesMenu.add(removeProfForEmployee);




        //-----------------------------------------------------------------------------------------------------------------------------------------------------------


        // Creating the Weekly Shift menu
        //--------------------------------------------------------------------------------------------------------------
        JMenu weeklyShiftMenu = new JMenu("Weekly Shift");


        //---------------------------
        //actions for weekly shift:
        //---------------------------



        //creation of new Weekly shift:
        JMenuItem CreateNewWeeklyShift = new JMenuItem("Create new Weekly Shift");
        CreateNewWeeklyShift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField weekNumberField = new JTextField();
                JTextField yearNumberField = new JTextField();
                JTextField superNumberField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Week Number:"));
                inputPanel.add(weekNumberField);
                inputPanel.add(new JLabel("Year Number:"));
                inputPanel.add(yearNumberField);
                inputPanel.add(new JLabel("Super Number:"));
                inputPanel.add(superNumberField);

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Weekly Shift Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If the user clicked "OK"
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the input fields
                    int weekNum = Integer.parseInt(weekNumberField.getText());
                    int yearNum = Integer.parseInt(yearNumberField.getText());
                    int superNum = Integer.parseInt(superNumberField.getText());

                    // Create an instance of new weekly shift
                    try {
                        HRManagerService.createNewWeeklyShift(weekNum,yearNum,superNum);
                        JOptionPane.showMessageDialog(null, "Weekly Shift was created successfully!");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "something went wrong," +
                                " Maybe it's already exist");
                    }
                }
            }
        });

        weeklyShiftMenu.add(CreateNewWeeklyShift);



        //weekly shift req:
        JMenuItem createShiftReq = new JMenuItem("Edit and view Weekly Shift Req");
        createShiftReq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField weekNumberField = new JTextField();
                JTextField yearNumberField = new JTextField();
                JTextField superNumberField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Week Number:"));
                inputPanel.add(weekNumberField);
                inputPanel.add(new JLabel("Year Number:"));
                inputPanel.add(yearNumberField);
                inputPanel.add(new JLabel("Super Number:"));
                inputPanel.add(superNumberField);

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Weekly Shift Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If the user clicked "OK"
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the input fields
                    int weekNum = Integer.parseInt(weekNumberField.getText());
                    int yearNum = Integer.parseInt(yearNumberField.getText());
                    int superNum = Integer.parseInt(superNumberField.getText());

                    // Create an instance of UIWeeklyShiftReq with the provided input values
                    try {
                        UIWeeklyShiftReq weeklyShiftReq = new UIWeeklyShiftReq(weekNum, yearNum, superNum);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        weeklyShiftMenu.add(createShiftReq);

        JMenuItem editShiftItem = new JMenuItem("Edit Shift");
        editShiftItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField weekNumberField = new JTextField();
                JTextField yearNumberField = new JTextField();
                JTextField superNumberField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Week Number:"));
                inputPanel.add(weekNumberField);
                inputPanel.add(new JLabel("Year Number:"));
                inputPanel.add(yearNumberField);
                inputPanel.add(new JLabel("Super Number:"));
                inputPanel.add(superNumberField);

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Weekly Shift Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If the user clicked "OK"
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the input fields
                    int weekNum = Integer.parseInt(weekNumberField.getText());
                    int yearNum = Integer.parseInt(yearNumberField.getText());
                    int superNum = Integer.parseInt(superNumberField.getText());

                    // Create an instance of UIWeeklyShiftReq with the provided input values
                    try {
                        UIShift weeklyShiftReq = new UIShift(weekNum, yearNum, superNum);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        weeklyShiftMenu.add(editShiftItem);

        JMenuItem editShiftItemDrivers = new JMenuItem("Edit Shift for drivers");
        editShiftItemDrivers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields
                JTextField weekNumberField = new JTextField();
                JTextField yearNumberField = new JTextField();

                // Create the panel to hold the input fields
                JPanel inputPanel = new JPanel(new GridLayout(2, 2));
                inputPanel.add(new JLabel("Week Number:"));
                inputPanel.add(weekNumberField);
                inputPanel.add(new JLabel("Year Number:"));
                inputPanel.add(yearNumberField);

                // Show the input dialog and get the user's input
                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Weekly Shift Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If the user clicked "OK"
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the input fields
                    int weekNum = Integer.parseInt(weekNumberField.getText());
                    int yearNum = Integer.parseInt(yearNumberField.getText());

                    // Create an instance of UIWeeklyShiftReq with the provided input values
                    try {
                        new UIShiftDriver(weekNum, yearNum, 0);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        weeklyShiftMenu.add(editShiftItemDrivers);

        //-------------------------------------------------------------------------------------------------------------



        // Adding the menus to the menu bar
        menuBar.add(employeesMenu);
        menuBar.add(weeklyShiftMenu);

        // Setting the menu bar on the frame
        frame.setJMenuBar(menuBar);

        // Create a JPanel to hold the task box components
        UITaskBox taskBox = new UITaskBox();
        JPanel taskPanel = taskBox.createPanel();

        // Add the task panel to the main frame
        frame.add(taskPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        frame.setVisible(true);
    }
    private  void showAddNewDriverDialog() {
        final String[] TEMP_TYPES = {"lightWeight", "mediumWeight", "heavyWeight"};
        final String[] WEIGHT_TYPES = {"regular", "cold", "frozen"};

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contractField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField wageField = new JTextField();
        JTextField phoneNumField = new JTextField();
        JTextField personalInfoField = new JTextField();
        JTextField bankNumField = new JTextField();
        JComboBox<String> tempTypeCombo = new JComboBox<>(TEMP_TYPES);
        JComboBox<String> weightTypeCombo = new JComboBox<>(WEIGHT_TYPES);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contract:"));
        panel.add(contractField);
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateField);
        panel.add(new JLabel("Wage:"));
        panel.add(wageField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumField);
        panel.add(new JLabel("Personal Info:"));
        panel.add(personalInfoField);
        panel.add(new JLabel("Bank Number:"));
        panel.add(bankNumField);
        panel.add(new JLabel("Temp Type:"));
        panel.add(tempTypeCombo);
        panel.add(new JLabel("Weight Type:"));
        panel.add(weightTypeCombo);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Driver", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Added Driver");
            // Get the values from the text fields and combo boxes
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String contract = contractField.getText();
            String startDate = startDateField.getText();
            int wage = Integer.parseInt(wageField.getText());
            int phoneNum = Integer.parseInt(phoneNumField.getText());
            String personalInfo = personalInfoField.getText();
            int bankNum = Integer.parseInt(bankNumField.getText());
            int tempType = tempTypeCombo.getSelectedIndex();
            int weightType = weightTypeCombo.getSelectedIndex();

            // Call the addNewDriver function with the gathered input
            HRManagerService.addNewDriver(id,name,contract,startDate,wage,phoneNum,personalInfo,bankNum,tempType+1,weightType+1);
        }
    }
    private void showfireemployeeDialog() {
        JTextField idField = new JTextField();


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        int result = JOptionPane.showConfirmDialog(null, panel, "fire employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "fired employee");
            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            // Call the addEmployee function with the gathered input
            HRManagerService.fireemployee(id);
        }
    }
    private void showAddEmployeeDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contractField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField wageField = new JTextField();
        JTextField phoneNumField = new JTextField();
        JTextField personalInfoField = new JTextField();
        JTextField bankNumField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contract:"));
        panel.add(contractField);
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateField);
        panel.add(new JLabel("Wage:"));
        panel.add(wageField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumField);
        panel.add(new JLabel("Personal Info:"));
        panel.add(personalInfoField);
        panel.add(new JLabel("Bank Number:"));
        panel.add(bankNumField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Added Employee!");
            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String contract = contractField.getText();
            String startDate = startDateField.getText();
            int wage = Integer.parseInt(wageField.getText());
            int phoneNum = Integer.parseInt(phoneNumField.getText());
            String personalInfo = personalInfoField.getText();
            int bankNum = Integer.parseInt(bankNumField.getText());
        // Call the addEmployee function with the gathered input
        HRManagerService.addemployee(id,name,contract,startDate,wage,phoneNum,personalInfo,bankNum);
    }
}

    private void showAddwagetoemployeeDialog() {
        JTextField idField = new JTextField();
        JTextField wageField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Wage:"));
        panel.add(wageField);

        int result = JOptionPane.showConfirmDialog(null, panel, "add wage to employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            int wage = Integer.parseInt(wageField.getText());
            JOptionPane.showMessageDialog(null, "added " +wage+" wage to employee!");
            HRManagerService.addwagetoemployee(id,wage);
        }
    }
    private void showChangeemployeecontractDialog() {
        JTextField idField = new JTextField();
        JTextField contractField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Contract:"));
        panel.add(contractField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Change Employee Contract", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Changed Employee Contract!");
            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            String contract = contractField.getText();
            // Call the addEmployee function with the gathered input
            HRManagerService.changeemployeecontract(id,contract);
        }
    }
    private void showUpdateemployeesbankDialog() {
        JTextField idField = new JTextField();
        JTextField bankNumField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Bank Number:"));
        panel.add(bankNumField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Update Employee Bank num", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Updated Employee Bank num!");
            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            int bankNum = Integer.parseInt(bankNumField.getText());
            // Call the addEmployee function with the gathered input
            HRManagerService.updateemployeesbank(id,bankNum);
        }
    }
    private void showAddavilableforemployeeDialog() {
        final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        final String[] NORD_OPTIONS = {"night", "day"};
        JTextField idField = new JTextField();
        JComboBox<String> daynumCombo = new JComboBox<>(DAYS_OF_WEEK);
        JComboBox<String> nordCombo = new JComboBox<>(NORD_OPTIONS);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Day of the week:"));
        panel.add(daynumCombo);
        panel.add(new JLabel("Night or Day:"));
        panel.add(nordCombo);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Available for Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Added Available for Employee!");
            // Get the values from the text field and combo boxes
            int id = Integer.parseInt(idField.getText());
            // Use the index of the selected day
            int daynum = daynumCombo.getSelectedIndex() + 1;
            String nordInfo = nordCombo.getSelectedItem().toString();

            // Call the addEmployee function with the gathered input
            HRManagerService.addavilableforemployee(id,daynum,nordInfo);
        }
    }
    private void showAddnewproforemployeeDialog() {
        JTextField idField = new JTextField();
        JComboBox<Object> proComboBox=new JComboBox<>();
        proComboBox.addItem("manager");
        proComboBox.addItem("cashier");
        proComboBox.addItem("stoke");
        proComboBox.addItem("cleaning");
        proComboBox.addItem("shelf-stoking");
        proComboBox.addItem("general-worker");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("prof Number:"));
        panel.add(proComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add new pro for employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            int prof = proComboBox.getSelectedIndex();

            // Call the addEmployee function with the gathered input
            HRManagerService.addnewproforemployee(id,prof);
            JOptionPane.showMessageDialog(null, "Added new pro for employee!");

        }
    }
    private void showremoveprofforemployeeDialog() {
        JTextField idField = new JTextField();
        JComboBox<Object> proComboBox=new JComboBox<>();
        proComboBox.addItem("manager");
        proComboBox.addItem("cashier");
        proComboBox.addItem("stoke");
        proComboBox.addItem("cleaning");
        proComboBox.addItem("shelf-stoking");
        proComboBox.addItem("general-worker");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("prof Number:"));
        panel.add(proComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "remove pro for employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "removed pro for employee!");
            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            int prof = proComboBox.getSelectedIndex();
            // Call the addEmployee function with the gathered input
            HRManagerService.removeprofforemployee(id,prof);
        }
    }
    private void showremoveavalbleforemployeeDialog() {
        final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        final String[] NORD_OPTIONS = {"night", "day"};
        JTextField idField = new JTextField();
        JComboBox<String> daynumCombo = new JComboBox<>(DAYS_OF_WEEK);
        JComboBox<String> nordCombo = new JComboBox<>(NORD_OPTIONS);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Day of the week:"));
        panel.add(daynumCombo);
        panel.add(new JLabel("Night or Day:"));
        panel.add(nordCombo);
        int result = JOptionPane.showConfirmDialog(null, panel, "remove available for employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "remove available for employee!");
            // Get the values from the text field and combo boxes
            int id = Integer.parseInt(idField.getText());
            // Use the index of the selected day
            int daynum = daynumCombo.getSelectedIndex() + 1;
            String nordInfo = nordCombo.getSelectedItem().toString();
            // Call the addEmployee function with the gathered input
            HRManagerService.removeavalbleforemployee(id,daynum,nordInfo);
        }
    }

    private void showsetPersonalinfoDialog() {
        JTextField idField = new JTextField();

        JTextField persoinfoField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID:"));
        panel.add(idField);

        panel.add(new JLabel("persoinfo:"));
        panel.add(persoinfoField);
        int result = JOptionPane.showConfirmDialog(null, panel, "set Personal info", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "set Personal info for employee!");
            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            String persoinfo = persoinfoField.getText();
            // Call the addEmployee function with the gathered input
            HRManagerService.setPersonalinfo(id,persoinfo);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UIHRManager::new);
    }
}
