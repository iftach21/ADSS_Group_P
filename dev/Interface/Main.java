package Interface;

import Interface.OrderInterface;
import Interface.interfaceManager;
import  Domain.*;
import services.*;
import DataAccesObject.*;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        if(args[0].equals("GUI")) {

        //welcome page
        JFrame welcomeFrame = new JFrame("Welcome Page");
        welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //welcome page size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);

        JLabel imageLabel = new JLabel();
        Path path = Paths.get("docs/LogoWelcome.jpg");
        Path absolutePath = path.toAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(absolutePath.toString());
        Image image = imageIcon.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        imageLabel.setIcon(scaledImageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        welcomeFrame.setContentPane(imageLabel);

        welcomeFrame.setVisible(true);

        // Hide the frame for 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        welcomeFrame.setVisible(false);


            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    if(args[1].equals("SupplierManger")){
                    new Supplier_Manger_GUI(false);
                    }
                    else if(args[1].equals("InventoryManger")){
                        new InventoryMangerGUI(false);
                    }
                    else if (args[1].equals("StoreManger")) {
                        new Manger();

                    }
                }

            });}
        else if(args[0].equals("CLI")){




            AInterface[] interfaces = new AInterface[4];
        interfaces[0] = new SupplierInterface();
        interfaces[1] = new OrderInterface(interfaces[0].getSupplier_manger());
        interfaces[2] = new interfaceManager();
        interfaces[3] = new interfaceWorker();

        String option;
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            if(args[1].equals("StoreManger")){
            System.out.println("Hello this is the manger  board press on the right option");
            System.out.println("1.Supplier manger");
            System.out.println("2.Order manger");
            System.out.println("3.Stock manger");
            System.out.println("4.Stock worker");
            System.out.println("5.insert demo data to the system for test and playing ");
            System.out.println("6.delete the db ");

            System.out.println("7.Exit");
            option = scanner.nextLine();

            while(true)
            {
                try
                {
                    choice = Integer.parseInt(option);
                    if(choice < 1 || choice > 7)
                    {
                        //the main menu there he can choose what to do
                        System.out.println("Please enter a valid option");
                        System.out.println("1.Supplier manger");
                        System.out.println("2.Order manger");
                        System.out.println("3.Stock manger");
                        System.out.println("4.Stock worker");
                        System.out.println("5.insert demo data to the system for test and playing ");
                        System.out.println("6.delete the db ");
                        System.out.println("7.Exit");



                        option = scanner.nextLine();
                        continue;
                    }
                    break;
                }
                catch(Exception ignored)
                {
                    System.out.println("Please enter a valid option");
                    option = scanner.nextLine();
                }
            }

            switch (choice)
            {
                case 1:
                    interfaces[0].interfaceStartup();
                    break;

                case 2:
                    interfaces[1].interfaceStartup();
                    break;

                case 3:
                    //interfaces[2].interfaceStartup();
                    interfaces[2].interfaceManagerLogin();
                    break;

                case 4:
                    //interfaces[3].interfaceStartup();
                    interfaces[3].interfaceWorkerLogin();
                    break;

                case 5:
                    //4 items to be add to the db
                    Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
                    item1.addNewPrice(20, 40);
                    item1.addNewPrice(20, 30);

                    Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
                    item2.addNewPrice(20, 40);
                    item2.addNewPrice(20, 30);

                    Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");

                    Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");

                    item1.setMinQuantity(10);
                    item2.setMinQuantity(50);

                    //Dates
                    Date date1 = new Date(2023, 10, 7);
                    Date date2 = new Date(2024, 1, 9);
                    Date date3 = new Date(2025, 5, 11);
                    Date date4 = new Date(2026, 6, 13);

                    //Categories
                    Category dairyCat = new Category("Dairy");
                    Category fruitsCat = new Category("Fruits");

                    //Sub-Categories
                    subCategory milksSub = new subCategory("Milks");
                    subCategory applesSub = new subCategory("Apples");

                    //Specific Items
                    specificItem apple1 = new specificItem(date1, false, Location.Store, item1);
                    specificItem apple2 = new specificItem(date2, true, Location.Storage, item1);
                    specificItem apple3 = new specificItem(date3, true, Location.Storage, item1);
                    specificItem apple4 = new specificItem(date4, true, Location.Storage, item1);
                    specificItem milk1 = new specificItem(date1, false, Location.Store, item2);
                    specificItem milk2 = new specificItem(date2, true, Location.Storage, item2);
                    specificItem milk3 = new specificItem(date3, true, Location.Storage, item2);
                    specificItem milk4 = new specificItem(date4, true, Location.Storage, item2);

                    //Stock Manager interface to insert
                    interfaceManager interfaceManagerInsert = new interfaceManager();
                    interfaceManagerInsert.insertSpecificItem(apple1);
                    interfaceManagerInsert.insertSpecificItem(apple2);
                    interfaceManagerInsert.insertSpecificItem(apple3);
                    interfaceManagerInsert.insertSpecificItem(apple4);
                    interfaceManagerInsert.insertSpecificItem(milk1);
                    interfaceManagerInsert.insertSpecificItem(milk2);
                    interfaceManagerInsert.insertSpecificItem(milk3);
                    interfaceManagerInsert.insertSpecificItem(milk4);

                    interfaceManagerInsert.insertCategory(dairyCat);
                    interfaceManagerInsert.insertCategory(fruitsCat);

                    interfaceManagerInsert.insertSubCat(dairyCat, milksSub);
                    interfaceManagerInsert.insertSubCat(fruitsCat, applesSub);



                    //supplier manger to insert the supplier
                    Supplier_Manger masupplier=new Supplier_Manger();


                    //the supplier and his coract person
                    ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
                    NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);


                    //add the supplier to the supplier manger
                    masupplier.add_supplier(supplier_1);

                    //update the supplier list
                    masupplier.update_suppliers();


                    //add an item to the supplier
                    masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
                    //add a discount to the supplier
                    masupplier.add_item_discount_to_supplier("Supplier1 Inc.","Apple",10,0.8);
                    masupplier.update_suppliers();
                    //add a second supplier and an item to him
                    WindowType currentDeliveryDay = WindowType.day2;
                    FixedDaySupplier supplier_2 = new FixedDaySupplier(currentDeliveryDay,"Supplier2 Inc.", "123446789", 1, "S0056", contactPerson, null, null);
                    masupplier.add_supplier(supplier_2);
                    masupplier.add_item_to_supplier(supplier_2.getName(),item2,100,10);
                    break;


                ///add more demo data here(*nati and ofir)


                case 6:
                    masupplier=new Supplier_Manger();
                    masupplier.delte_all();
                    OrderManger orderManger=new OrderManger();
                    orderManger.delte_db();
                    interfaceManager interfaceManager = new interfaceManager();
                    interfaceManager.delete_db();
                    break;


                case 7:
                    System.out.println("Thank you and goodbye");
                    System.exit(0);
                    break;


            }

        }
            else if (args[1].equals("SupplierManger")) {
                interfaces[0].interfaceStartup();

                
            } else if (args[1].equals("InventoryManger")) {
                interfaces[2].interfaceStartup();



            }
        }

    }
}}

