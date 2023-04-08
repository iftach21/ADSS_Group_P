import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
public class Interface {
    // -------------------------------------------------------
    // Users login information
    // -------------------------------------------------------
    private static final String Manager_Passward = "M12345"; // the password for manager the system
    private static final String Manager_Username = "Manager"; // the username for manager the system

    private static final String Worker_Passward = "W12345"; // the password for worker in the system
    private static final String Worker_Username = "Worker"; // the username for worker in the system



    public Interface() {

    }
    public void logIn() {
        // -------------------------------------------------------
        // Login confirmation
        // Check if the user's login information is valid, and prompts a menu for the correct user,
        // either a storage worker or the storage manager
        // -------------------------------------------------------

        //Scans the username and password

        System.out.println("Welcome to 'Super-Li's' storage management system! Please log-in.");

        String userName;
        while (true) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter username");
            userName = myObj.nextLine();
            System.out.println("Enter password");
            String password = myObj.nextLine();
            //Checks the login information to be matched with any of the users
            if (userName.equals(Manager_Username) && password.equals(Manager_Passward) || userName.equals(Worker_Username) && password.equals(Worker_Passward)) {
                break;
                //If the user name does not exists, ask the user to re-enter his credentials
            }
            //If such a username does not exist
            else if (!userName.equals(Manager_Username) && !userName.equals(Worker_Username)){
                System.out.println("Such a user does not exist. Please try again.");
            }
            //If the password is incorrect
            else if (!password.equals(Manager_Passward) && !password.equals(Worker_Passward)){
                System.out.println("Incorrect password. Please try again.");
            }
            //Else
            else {
                System.out.println("Invalid input. please try again.");
            }
        }


        // -------------------------------------------------------
        // Main Menu
        // -------------------------------------------------------

        // -------------------------------------------------------
        // Manager's Main Menu
        // The full warehouse main menu, including all of the available options
        // -------------------------------------------------------
        if (userName.equals(Manager_Username)) {

            boolean exit = true;
            while (exit) {
                System.out.println("Please choose what you would like to do:");
                System.out.println("1: Provide a product shortage report");
                System.out.println("2: Update inventory");
                System.out.println("3: Provide an inventory counting report");
                System.out.println("4: Provide a defective products report");
                System.out.println("5: Update discount on products");
                System.out.println("6: Place an order from a supplier");
                System.out.println("7: Update product details");
                System.out.println("8: Provide a price history report for a product");
                System.out.println("9: Insert a defective product into the defective inventory");

                System.out.println("0: Exit the system ");

                //end of menu

                Scanner myObj = new Scanner(System.in);
                String ans = myObj.nextLine();  // Read user input

                switch (ans) {
                    //-----------------------
                    //Provide a product shortage report
                    //-----------------------
                    case "1": {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("which report to provide:");
                            System.out.println("1: To all products");
                            System.out.println("2: By Category");
                            System.out.println("3: For a specific product");
                            System.out.println("0: Return to the Main Menu");

                            Scanner Second_obj = new Scanner(System.in);
                            String Sub_Ans = Second_obj.nextLine();  // Read user input

                            switch (Sub_Ans) {
                                case "1":
//                                    this.Shortge_Report("All");
                                    break;

                                case "2":
                                    System.out.println("Please enter the category you would like to provide a shortage report for:");
                                    Scanner Category = new Scanner(System.in);
                                    String Cat_ans = Category.nextLine();
//                                    this.Shortge_Report(Cat_ans);
                                    break;

                                case "3":
                                    System.out.println("Please enter the product you would like to provide a shortage report for:");
                                    Scanner Product = new Scanner(System.in);
                                    String Pro_ans = Product.nextLine();
//                                    this.Shortge_Report(Pro_ans);
                                    break;

                                case "0":
                                    System.out.println("Going back to the Main Menu");
                                    flag = false;
                                    break;

                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                        break;
                    }

                    //-----------------------
                    //Update inventory
                    //-----------------------
                    case "2": {
//                        this.Update_Stock();
                        break;
                    }

                    //-----------------------
                    //Provide an inventory counting report
                    //-----------------------
                    case "3": {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("which report to provide:");
                            System.out.println("1: To all products");
                            System.out.println("2: By Category");
                            System.out.println("3: For a specific product");
                            System.out.println("0: Return to the Main Menu");

                            Scanner Second_obj = new Scanner(System.in);
                            String Sub_Ans = Second_obj.nextLine();  // Read user input

                            switch (Sub_Ans) {
                                case "1":
//                                    this.Inventory_Report("All");
                                    break;

                                case "2":
                                    System.out.println("Please enter the category you would like to provide a inventory counting report for:");
                                    Scanner Category = new Scanner(System.in);
                                    String Cat_ans = Category.nextLine();
//                                    this.Inventory_Report(Cat_ans);
                                    break;

                                case "3":
                                    System.out.println("Please enter the product you would like to provide a inventory counting report for:");
                                    Scanner Product = new Scanner(System.in);
                                    String Pro_ans = Product.nextLine();
//                                    this.Inventory_Report(Pro_ans);
                                    break;

                                case "0":
                                    System.out.println("Going back to the Main Menu");
                                    flag = false;
                                    break;

                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                        break;
                    }

                    //-----------------------
                    //Provide a defective products report
                    //-----------------------
                    case "4": {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("which report to provide:");
                            System.out.println("1: To all products");
                            System.out.println("2: By Category");
                            System.out.println("3: For a specific product");
                            System.out.println("0: Return to the Main Menu");

                            Scanner Second_obj = new Scanner(System.in);
                            String Sub_Ans = Second_obj.nextLine();  // Read user input

                            switch (Sub_Ans) {
                                case "1":
//                                    this.Defective_Report("All");
                                    break;

                                case "2":
                                    System.out.println("Please enter the category you would like to provide a defective products report for:");
                                    Scanner Category = new Scanner(System.in);
                                    String Cat_ans = Category.nextLine();
//                                    this.Defective_Report(Cat_ans);
                                    break;

                                case "3":
                                    System.out.println("Please enter the product you would like to provide a defective products report for:");
                                    Scanner Product = new Scanner(System.in);
                                    String Pro_ans = Product.nextLine();
//                                    this.Defective_Report(Pro_ans);
                                    break;

                                case "0":
                                    System.out.println("Going back to the Main Menu");
                                    flag = false;
                                    break;

                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                        break;
                    }

                    //-----------------------
                    //Update discount on products
                    //-----------------------
                    case "5": {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("For which products to update:");
                            System.out.println("1: To all products");
                            System.out.println("2: By Category");
                            System.out.println("3: For a specific product");
                            System.out.println("0: Return to the Main Menu");

                            Scanner Second_obj = new Scanner(System.in);
                            String Sub_Ans = Second_obj.nextLine();  // Read user input

                            switch (Sub_Ans) {
                                case "1":
//                                    this.Update_discount("All");
                                    break;

                                case "2":
                                    System.out.println("Please enter the category you would like to provide a defective products report for:");
                                    Scanner Category = new Scanner(System.in);
                                    String Cat_ans = Category.nextLine();
//                                    this.Update_discount(Cat_ans);
                                    break;

                                case "3":
                                    System.out.println("Please enter the product you would like to provide a defective products report for:");
                                    Scanner Product = new Scanner(System.in);
                                    String Pro_ans = Product.nextLine();
//                                    this.Update_discount(Pro_ans);
                                    break;

                                case "0":
                                    System.out.println("Going back to the Main Menu");
                                    flag = false;
                                    break;

                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                        break;
                    }

                    //-----------------------
                    //Place an order from a supplier
                    //-----------------------
                    case "6": {
//                        this.Place_Order();
                        break;
                    }

                    //-----------------------
                    //Update product details
                    //-----------------------
                    case "7": {
                        System.out.println("please enter catalog number:");
                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input
//                        this.Update_Details();
                        break;
                    }

                    //-----------------------
                    //Provide a price history report for a product
                    //-----------------------
                    case "8": {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("which history report to provide:");
                            System.out.println("1: To all products");
                            System.out.println("2: By Category");
                            System.out.println("3: For a specific product");
                            System.out.println("0: Return to the Main Menu");

                            Scanner Second_obj = new Scanner(System.in);
                            String Sub_Ans = Second_obj.nextLine();  // Read user input

                            switch (Sub_Ans) {
                                case "1":
//                                    this.History_Report("All");
                                    break;

                                case "2":
                                    System.out.println("Please enter the category you would like to provide a history report for:");
                                    Scanner Category = new Scanner(System.in);
                                    String Cat_ans = Category.nextLine();
//                                    this.History_Report(Cat_ans);
                                    break;

                                case "3":
                                    System.out.println("Please enter the product you would like to provide a history report for:");
                                    Scanner Product = new Scanner(System.in);
                                    String Pro_ans = Product.nextLine();
//                                    this.History_Report(Pro_ans);
                                    break;

                                case "0":
                                    System.out.println("Going back to the Main Menu");
                                    flag = false;
                                    break;

                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                        break;
                    }

                    //-----------------------
                    //Insert a defective product into the defective inventory
                    //-----------------------
                    case "9": {
                        System.out.println("please enter catalog number:");
                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input
//                        this.Insert_Defective();
                        break;
                    }

                    //-----------------------
                    //Exit the system
                    //-----------------------
                    case "0": {
                        exit = false;
                        break;
                    }

                    default: {
                        System.out.println("please enter valid input");
                    }
                }
            }
        }

        if (userName.equals(Worker_Username)) {
            System.out.println("Worker interface");
        }

    }}

    //login






 //class