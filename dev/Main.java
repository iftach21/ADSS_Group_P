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
            option = scanner.nextLine();
            while(true)
            {
                try
                {
                    choice = Integer.parseInt(option);
                    if(choice < 1 || choice > 3)
                    {
                        System.out.println("Please enter a valid option");
                        System.out.println("1.Supplier manger");
                        System.out.println("2.Order manger");
                        System.out.println("3.Exit");
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

                    switch (option_1) {
                        case 1:
                            System.out.println("name:");
                            String name = scanner.next();
                            name = checkName(name);
                            System.out.println("business id:");
                            String business_id = scanner.next();
                            business_id = checkNumber(business_id);
                            //payment opiton
                            System.out.println("Payment method:");
                            System.out.println("1.SHOTEF");
                            System.out.println("2.SHOTEF+30");
                            System.out.println("3.SHOTEF+60");
                            String payment = scanner.next();
                            int paymentNum = 0;
                            while(true)
                            {

                                try
                                {
                                    paymentNum = Integer.parseInt(payment);
                                    if(paymentNum < 1 || paymentNum > 3)
                                    {
                                        System.out.println("Please enter a valid option");
                                        payment = scanner.next();
                                    }

                                }
                                catch(Exception ignored)
                                {
                                    System.out.println("Please enter a valid option");
                                    payment = scanner.next();
                                }
                                if(!(paymentNum < 1 || paymentNum > 3))
                                {break;}
                            }

                            System.out.println("Supplier ID");
                            String id = scanner.next();
                            id = checkNumber(id);
                            System.out.println("Contact name");
                            String contact_name = scanner.next();
                            contact_name = checkName(contact_name);
                            System.out.println("number");
                            String phone_number = scanner.next();
                            phone_number = checkNumber(phone_number);
                            Contact_Person con_person = new Contact_Person(contact_name, phone_number);

                            //delivery option
                            System.out.println("type:");
                            System.out.println("1.Non delivery supplier");
                            System.out.println("2.delivery supplier on fixed days");
                            System.out.println("3.delivery supplier on non fixed days");
                            String supType = scanner.next();
                            int option_4;
                            while(true)
                            {
                                try
                                {
                                    option_4 = Integer.parseInt(supType);
                                    if(option_4 < 1 || option_4 > 3)
                                    {
                                        System.out.println("Please enter a valid option");
                                        supType = scanner.next();
                                        continue;
                                    }
                                    break;
                                }
                                catch(Exception ignored)
                                {
                                    System.out.println("Please enter a valid option");
                                    supType = scanner.next();
                                }
                            }


                            if (option_4 == 1) {
                                NonDeliveringSupplier supplier = new NonDeliveringSupplier(name, business_id, paymentNum, id, con_person, null, null);
                                supplier_manger.getSuppliers().add(supplier);
                            } else if (option_4 == 2) {
                                System.out.println("day:");
                                String day = scanner.next();
                                int dayNum;
                                while(true)
                                {
                                    try
                                    {
                                        dayNum = Integer.parseInt(day);
                                        if(dayNum < 1 || dayNum > 7)
                                        {
                                            System.out.println("Please enter a valid day");
                                            day = scanner.next();
                                            continue;
                                        }
                                        break;
                                    }
                                    catch(Exception ignored)
                                    {
                                        System.out.println("Please enter a valid option");
                                        day = scanner.next();
                                    }
                                }
                                WindowType day_window;
                                day_window = WindowTypeCreater.getwindowtype(dayNum);
                                FixedDaySupplier supplier = new FixedDaySupplier(day_window, name, business_id, paymentNum, id, con_person, null, null);
                                supplier_manger.getSuppliers().add(supplier);

                            } else {
                                System.out.println("days to deliver:");
                                String day = scanner.next();
                                int numOfDeliveryDays = 0;
                                while(true)
                                {
                                    try
                                    {
                                        numOfDeliveryDays = Integer.parseInt(day);
                                        if(numOfDeliveryDays < 1 || numOfDeliveryDays > 7)
                                        {
                                            System.out.println("Please enter a valid day");
                                            day = scanner.next();
                                            continue;
                                        }
                                        break;
                                    }
                                    catch(Exception ignored)
                                    {
                                        System.out.println("Please enter a valid option");
                                        day = scanner.next();
                                    }
                                }
                                NonFixedDaySupplier supplier = new NonFixedDaySupplier(numOfDeliveryDays, name, business_id, paymentNum, id, con_person, null, null);
                                supplier_manger.getSuppliers().add(supplier);
                            }
                            break;


                        case 2:
                            System.out.println("name:");
                            String name_s = scanner.next();
                            name_s = checkName(name_s);
                            supplier_manger.remove_supplier(name_s);
                            break;


                        case 3:

                            System.out.println("name of the supplier:");
                            String name_s1 = scanner.next();
                            name_s1 = checkName(name_s1);
                            System.out.println("name of the new contact:");
                            String name_s2 = scanner.next();
                            name_s2 = checkName(name_s2);
                            System.out.println("his phone number :");
                            String phone_1 = scanner.next();
                            phone_1 = checkNumber(phone_1);
                            supplier_manger.update_contact_preson(name_s1, name_s2, phone_1);
                            break;

                        case 4:
                            System.out.println("name of the supplier:");
                            String name_s3 = scanner.next();
                            name_s3 = checkName(name_s3);
                            System.out.println("Item name:");
                            String item_name = scanner.next();
                            item_name = checkName(item_name);
                            System.out.println("Item catlog number:");
                            String catalogNum = scanner.next();
                            catalogNum = checkNumber(catalogNum);
                            System.out.println("Item weight:");
                            String weightInput = scanner.next();
                            weightInput = checkNumberWithDot(weightInput);
                            double weight = Double.parseDouble(weightInput);
                            System.out.println("catalog Name");
                            String catalogName = scanner.next();
                            catalogName = checkName(catalogName);
                            System.out.println("temperature");
                            String tempInput = scanner.next();
                            tempInput = checkNumberWithDot(tempInput);
                            double temp = Double.parseDouble(tempInput);
                            System.out.println("exprison date:");
                            String expirationDate_s = scanner.next();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date expirationDate = null;
                            while(true)
                            {
                            try {
                                expirationDate = dateFormat.parse(expirationDate_s);
                                break;
                            }
                            catch (ParseException e)
                            {
                                System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy");
                                expirationDate_s = scanner.next();
                            }
                            }
                            //create the new item
                            Item item = new Item(item_name, catalogName, expirationDate, weight, catalogNum, temp);
                            System.out.println("base price per unit");
                            String priceInput = scanner.next();
                            priceInput = checkNumberWithDot(priceInput);
                            float price = Float.parseFloat(priceInput);
                            System.out.println("amount that can be supplier");
                            String amountInput = scanner.next();
                            amountInput = checkNumber(amountInput);
                            int amount = Integer.parseInt(amountInput);
                            supplier_manger.add_item_to_supplier(name_s3, item, amount, price);
                            break;

                        case 5:
                            System.out.println("name of the supplier:");
                            String name_s4 = scanner.next();
                            name_s4 = checkName(name_s4);
                            System.out.println("name of the item");
                            String item_c5 = scanner.next();
                            item_c5 = checkName(item_c5);
                            supplier_manger.remove_item_to_supplier(name_s4, item_c5);
                            break;

                        case 6:
                            System.out.println("name of the supplier:");
                            String name_s6 = scanner.next();
                            name_s6 = checkName(name_s6);
                            System.out.println("name of the item");
                            String item_c6 = scanner.next();
                            item_c6 = checkName(item_c6);
                            System.out.println("amount:");
                            String amount_6Input = scanner.next();
                            amount_6Input = checkNumber(amount_6Input);
                            int amount_6 = Integer.parseInt(amount_6Input);
                            System.out.println("discount:");
                            String discount_6Input = scanner.next();
                            discount_6Input = checkNumberWithDot(discount_6Input);
                            double discount_6 = Double.parseDouble(discount_6Input);
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
                    System.out.println("5.get back to previous menu");
                    String choice_1 =scanner.nextLine();
                    option_1 = 0;

                    while(true)
                    {
                        try
                        {
                            option_1 = Integer.parseInt(choice_1);
                            if(option_1 < 1 || option_1 > 5)
                            {
                                System.out.println("Please enter a valid option");
                                choice_1 = scanner.nextLine();
                                continue;
                            }
                            break;
                        }
                        catch(Exception ignored)
                        {
                            System.out.println("Please enter a valid option");
                            choice_1 = scanner.nextLine();
                        }
                    }


                    switch (option_1) {
                        case 1:
                            Map<Item, Integer> itemlist = new HashMap<Item, Integer>();
                            System.out.println("store number:");
                            String store_numberInput = scanner.next();
                            store_numberInput = checkNumber(store_numberInput);
                            int store_number = Integer.parseInt(store_numberInput);
                            while (true) {
                                System.out.println("1.add item to list");
                                System.out.println("2.done and ready to go to orders");
                                store_numberInput = scanner.next();
                                store_numberInput = checkNumber(store_numberInput);
                                int option_11 = Integer.parseInt(store_numberInput);

                                //add item to the order list
                                if (option_11 == 1) {
                                    System.out.println("Item name:");
                                    String item_name = scanner.next();
                                    item_name = checkName(item_name);
                                    System.out.println("Item catalog number:");
                                    String catalogNum = scanner.next();
                                    catalogNum = checkNumber(catalogNum);
                                    System.out.println("Item weight:");
                                    String inputWeight = scanner.next();
                                    inputWeight = checkNumberWithDot(inputWeight);
                                    double weight = Double.parseDouble(inputWeight);
                                    System.out.println("catalog Name");
                                    String catalogName = scanner.next();
                                    catalogName = checkName(catalogName);
                                    System.out.println("temperature");
                                    String tempInput = scanner.next();
                                    tempInput = checkNumberWithDot(tempInput);
                                    double temp = Double.parseDouble(tempInput);

                                    System.out.println("exprison date:");
                                    String expirationDate_s = scanner.next();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date expirationDate = null;
                                    while(true){
                                    try {
                                        expirationDate = dateFormat.parse(expirationDate_s);
                                        break;
                                    } catch (ParseException e) {
                                        System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy");
                                        expirationDate_s = scanner.next();
                                    }}
                                    //create the new item
                                    Item item = new Item(item_name, catalogName, expirationDate, weight, catalogNum, temp);
                                    System.out.println("amount:");
                                    String amount_21Input = scanner.next();
                                    amount_21Input = checkNumberWithDot(amount_21Input);
                                    int amount_21 = Integer.parseInt(amount_21Input);
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
                            String order_2Input = scanner.next();
                            order_2Input = checkNumber(order_2Input);
                            int order_2 = Integer.parseInt(order_2Input);
                            orderManger.move_from_pending_to_approvel(order_2);
                            break;
                        case 3:
                            orderManger.print_all_orders_num();
                            break;
                        case 4:
                            System.out.println("Type supplier name :");
                            String supplier=scanner.next();
                            supplier = checkName(supplier);
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
                        case 5:
                            System.out.println("Redirecting back to main menu");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Thank you and goodbye");
                    System.exit(0);
                    break;



                default:
//                    System.out.println("Invalid option selected");
                    System.out.println("Please enter a number between 1 and 2");

            }

    }
}

public static String checkName(String input)
{
    Scanner scanner = new Scanner(System.in);
    while(true)
    {
        int counter = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                System.out.println("A name has to be letters only");
                break;
            }
            else {counter ++;}
        }
        if(counter == input.length())
        {
            return input;
        }
        else
        {
            System.out.println("name:");
            input = scanner.next();
        }
    }
}
    public static String checkNumber(String input)
    {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    System.out.println("has to be numbers only");
                    break;
                }
                else {counter ++;}
            }
            if(counter == input.length())
            {
                return input;
            }
            else
            {
                System.out.println("number:");
                input = scanner.next();
            }
        }
    }

    public static String checkNumberWithDot(String input)
    {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i)) && !(input.charAt(i) == '.')) {
                    System.out.println("has to be numbers only");
                    break;
                }
                else {counter ++;}
            }
            if(counter == input.length())
            {
                return input;
            }
            else
            {
                System.out.println("number:");
                input = scanner.next();
            }
        }
    }
}

