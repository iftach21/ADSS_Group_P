import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderManger orderManger =new OrderManger();
        Supplier_Manger supplier_manger=new Supplier_Manger();
        String option;
        int choice = 0;

        while (true) {
            //main menu
            System.out.println("Hello this is the manger suppliers board press on the right option");
            System.out.println("1.Supplier manger");
            System.out.println("2.Order manger");
            System.out.println("3.Exit");
            while(true)
            {
                try
                {
                    option = scanner.nextLine();
                    choice = Integer.parseInt(option);
                    if(choice < 1 || choice > 3)
                    {
                        System.out.println("Please enter a valid option");
                        System.out.println("1.Supplier manger");
                        System.out.println("2.Order manger");
                        System.out.println("3.Exit");
                        continue;
                    }
                    break;
                }
                catch(Exception ignored)
                {
                }
            }
            switch (choice) {
                case 1:
                    //supplier menu to manipulate them and their items
                    System.out.println("You selected Supplier manger");
                    System.out.println("1.add new supplier");
                    System.out.println("2.remove supplier");
                    System.out.println("3.Update contact to supplier");
                    System.out.println("4.add Item to Supplier");
                    System.out.println("5.remove item from Supplier");
                    System.out.println("6.Update item on Contract");
                    System.out.println("7.print all suppliers");
                    System.out.println("8.get back to previous menu");

                    int option_1;
                    while(true)
                    {
                        try
                        {
                            option = scanner.nextLine();
                            option_1 = Integer.parseInt(option);
                            if(option_1 < 1 || option_1 > 8)
                            {
                                System.out.println("Please enter a valid option");
                                System.out.println("1.add new supplier");
                                System.out.println("2.remove supplier");
                                System.out.println("3.Update contact to supplier");
                                System.out.println("4.add Item to Supplier");
                                System.out.println("5.remove item from Supplier");
                                System.out.println("6.Update item on Contract");
                                System.out.println("7.print all suppliers");
                                System.out.println("8.get back to previous menu");

                                continue;
                            }
                            break;
                        }
                        catch(Exception ignored)
                        {
                        }
                    }

                    switch (option_1) {
                        case 1:
                            System.out.println("name:");
                            String name = scanner.next();
                            while(true)
                            {
                                try
                                {
                                    double number = Double.parseDouble(name);
                                    System.out.println("A supplier name cannot be a number");
                                    System.out.println("name:");
                                    name = scanner.next();
                                }
                                catch (NumberFormatException e)
                                {
                                    break;
                                }
                            }
                            System.out.println("business id:");
                            String business_id = scanner.next();
                            while(true)
                            {
                                int counter = 0;
                                for (int i = 0; i < business_id.length(); i++) {
                                    if (!Character.isDigit(business_id.charAt(i))) {
                                        System.out.println("A business id has to be numbers only");
                                        break;
                                    }
                                    else {counter ++;}
                                }
                                if(counter == business_id.length()){break;}
                                else
                                {
                                    System.out.println("business id:");
                                    business_id = scanner.next();
                                }
                            }
                            System.out.println("Payment method:");
                            System.out.println("1.SHOTEF");
                            System.out.println("2.SHOTEF+30");
                            System.out.println("3.SHOTEF+60");
                            String payment = scanner.next();
                            int paymentNum;
                            while(true)
                            {

                                try
                                {
                                    paymentNum = Integer.parseInt(payment);
                                    if(choice < 1 || choice > 3)
                                    {
                                        System.out.println("Please enter a valid option");
                                        System.out.println("1.SHOTEF");
                                        System.out.println("2.SHOTEF+30");
                                        System.out.println("3.SHOTEF+60");
                                        continue;
                                    }
                                    break;
                                }
                                catch(Exception ignored)
                                {}
                            }

                            System.out.println("Supplier ID");
                            String id = scanner.next();
                            System.out.println("Contact name");
                            String contact_name = scanner.next();
                            System.out.println("number");
                            String phone_number = scanner.next();
                            Contact_Person con_person = new Contact_Person(contact_name, phone_number);
                            System.out.println("type:");
                            System.out.println("1.Non delivery supplier");
                            System.out.println("2.delivery supplier on fixed days");
                            System.out.println("3.delivery supplier on non fixed days");

                            int option_4 = scanner.nextInt();
                            if (option_4 == 1) {
                                NonDeliveringSupplier supplier = new NonDeliveringSupplier(name, business_id, paymentNum, id, con_person, null, null);
                                supplier_manger.getSuppliers().add(supplier);
                            } else if (option_4 == 2) {
                                System.out.println("day:");
                                String day = scanner.next();
                                WindowType day_window = WindowType.valueOf(day.toUpperCase());
                                FixedDaySupplier supplier = new FixedDaySupplier(day_window, name, business_id, paymentNum, id, con_person, null, null);
                                supplier_manger.getSuppliers().add(supplier);

                            } else if (option_4 == 3) {
                                System.out.println("days to deliver:");
                                int day = scanner.nextInt();
                                NonFixedDaySupplier supplier = new NonFixedDaySupplier(day, name, business_id, paymentNum, id, con_person, null, null);
                                supplier_manger.getSuppliers().add(supplier);
                            }
                            break;


                        case 2:
                            System.out.println("name:");
                            String name_s = scanner.next();
                            supplier_manger.remove_supplier(name_s);
                            break;


                        case 3:

                            System.out.println("name of the supplier:");
                            String name_s1 = scanner.next();
                            System.out.println("name of the new contact:");
                            String name_s2 = scanner.next();
                            System.out.println("his phone number :");
                            String phone_1 = scanner.next();
                            supplier_manger.update_contact_preson(name_s1, name_s2, phone_1);
                            break;

                        case 4:

                            System.out.println("name of the supplier:");
                            String name_s3 = scanner.next();
                            System.out.println("Item name:");
                            String item_name = scanner.next();
                            System.out.println("Item catlog number:");
                            String catalogNum = scanner.next();
                            System.out.println("Item weight:");
                            double weight = scanner.nextDouble();
                            System.out.println("catlog Name");
                            String catalogName = scanner.next();
                            System.out.println("temperature");
                            double temp = scanner.nextDouble();

                            System.out.println("exprison date:");
                            String expirationDate_s = scanner.next();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date expirationDate = null;
                            try {
                                expirationDate = dateFormat.parse(expirationDate_s);
                            } catch (ParseException e) {
                                System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy");
                            }
                            //create the new item
                            Item item = new Item(item_name, catalogNum, expirationDate, weight, catalogNum, temp);
                            System.out.println("base price per unit");
                            float price = scanner.nextFloat();
                            System.out.println("amount that can be supplier");
                            int amount = scanner.nextInt();
                            supplier_manger.add_item_to_supplier(name_s3, item, amount, price);
                            break;

                        case 5:
                            System.out.println("name of the supplier:");
                            String name_s4 = scanner.next();
                            System.out.println("name of the item");
                            String item_c5 = scanner.next();
                            supplier_manger.remove_item_to_supplier(name_s4, item_c5);
                            break;

                        case 6:
                            System.out.println("name of the supplier:");
                            String name_s6 = scanner.next();
                            System.out.println("name of the item");
                            String item_c6 = scanner.next();
                            System.out.println("amount:");
                            int amount_6 = scanner.nextInt();
                            System.out.println("discount:");
                            double discount_6 = scanner.nextDouble();
                            supplier_manger.add_item_discount_to_supplier(name_s6, item_c6, amount_6, discount_6);
                            break;
                        case 7:
                            supplier_manger.print_all_suppliers_names();
                            break;
                        case 8:
                            System.out.println("Redirecting back to main menu");
                            break;


                    }
                    break;


                // TODO: Add code to handle the Supplier manager option
                case 2:
                    //orders menu to add new and to approve them
                    System.out.println("You selected Order manger");
                    System.out.println("1.add new Order");
                    System.out.println("2.approve order");
                    System.out.println("3.print all order number");
                    System.out.println("4.print all order's from supplier");

                    option_1 = scanner.nextInt();
                    switch (option_1) {
                        case 1:
                            Map<Item, Integer> itemlist = new HashMap<Item, Integer>();
                            System.out.println("store number:");
                            int store_number = scanner.nextInt();


                            while (true) {
                                System.out.println("1.add item to list");
                                System.out.println("2.done and ready to go to orders");
                                int option_11 = scanner.nextInt();

                                //add item to the order list
                                if (option_11 == 1) {
                                    System.out.println("Item name:");
                                    String item_name = scanner.next();
                                    System.out.println("Item catlog number:");
                                    String catalogNum = scanner.next();
                                    System.out.println("Item weight:");
                                    double weight = scanner.nextDouble();
                                    System.out.println("catlog Name");
                                    String catalogName = scanner.next();
                                    System.out.println("temperature");
                                    double temp = scanner.nextDouble();

                                    System.out.println("exprison date:");
                                    String expirationDate_s = scanner.next();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date expirationDate = null;
                                    try {
                                        expirationDate = dateFormat.parse(expirationDate_s);
                                    } catch (ParseException e) {
                                        System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy");
                                    }
                                    //create the new item
                                    Item item = new Item(item_name, catalogNum, expirationDate, weight, catalogNum, temp);
                                    System.out.println("amount:");
                                    int amount_21 = scanner.nextInt();
                                    itemlist.put(item, amount_21);

                                }
                                if (option_11 == 2) {
                                    orderManger.assing_Orders_to_Suppliers(itemlist, supplier_manger, store_number);
                                    break;
                                }
                            }
                            break;
                        case 2:
                            orderManger.print_all_orders_num();
                            System.out.println("Type order number from the option :");
                            int order_2 = scanner.nextInt();
                            orderManger.move_from_pending_to_approvel(order_2);
                            break;
                        case 3:
                            orderManger.print_all_orders_num();
                            break;
                        case 4:
                            System.out.println("Type supplier name :");
                            String supplier=scanner.next();
                            System.out.println("order waiting for approvel :");
                            for(Order order:orderManger.getPending_for_apporval()){
                                if(order.getSupplier().getName().equals(supplier)){
                                 System.out.println(order.getOrderNum());}
                            }
                            System.out.println("order approved and waiting for shipment :");
                            for(Order order:orderManger.getApproval()){
                                if(order.getSupplier().getName().equals(supplier)){
                                    System.out.println(order.getOrderNum());}
                            }
                            System.out.println("order history");
                            for(Order order:orderManger.getApproval()){
                                if(order.getSupplier().getName().equals(supplier)){
                                    System.out.println(order.getOrderNum());}
                            }
                            break;




                    }
                case 3:
                    System.out.println("Thank you and goodbye");
                    System.exit(0);
                    break;


                // TODO: Add code to handle the Order manager option
                default:
//                    System.out.println("Invalid option selected");
                    System.out.println("Please enter a number between 1 and 2");

            }

    }
}
}

