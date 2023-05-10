import java.util.*;

public class OrderInterface extends AInterface {
    private OrderManger orderManger;
    private Supplier_Manger supplier_manger;
    public OrderInterface(Supplier_Manger supplier_manger){
        this.orderManger = new OrderManger();
        this.supplier_manger = supplier_manger;
    }

    @Override
    public void interfaceStartup() {
        Scanner scanner = new Scanner(System.in);
        int option_1;


        while (true) {
             //orders menu to add new and to approve them
             System.out.println("You selected Order manger");
             System.out.println("1.add new Order");
             System.out.println("2.approve order");
             System.out.println("3.print all order number's");
             System.out.println("4.print all order's from supplier");
             System.out.println("5.print all order's from all supplier with all the details");
             System.out.println("6.get back to previous menu");
             String choice_1 = scanner.nextLine();
             option_1 = 0;
             while (true) {
                 try {
                     option_1 = Integer.parseInt(choice_1);
                     if (option_1 < 1 || option_1 > 6) {
                         System.out.println("Please enter a valid option");
                         choice_1 = scanner.nextLine();
                         continue;
                     }
                     break;
                 } catch (Exception ignored) {
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
                             List<Item> itemList_sub = new ArrayList<>();
                             int op = 1;
                             for (Item item : supplier_manger.getItemslist().keySet()) {
                                 if (supplier_manger.getItemslist().get(item) > 0) {
                                     System.out.print(op + ".");
                                     item.print_item();
                                     itemList_sub.add(item);
                                     op++;
                                 }
                             }
                             System.out.println("Enter the number of the item you want to add:");
                             String item_numberInput = scanner.next();
                             item_numberInput = checkNumber(item_numberInput);
                             int item_number = Integer.parseInt(item_numberInput);
                             int count = 1;
                             for (Item item : itemList_sub) {
                                 if (supplier_manger.getItemslist().get(item) > 0) {
                                     if (count == item_number) {
                                         System.out.println("Enter the quantity:");
                                         String quantityInput = scanner.next();
                                         quantityInput = checkNumberWithDot(quantityInput);
                                         int quantity = Integer.parseInt(quantityInput);
                                         itemlist.put(item, quantity);
                                         break;
                                     }
                                     count++;
                                 }
                             }
                         }

                         if (option_11 == 2) {
                             if (!orderManger.assing_Orders_to_Suppliers(itemlist, supplier_manger, store_number)) {
                                 System.out.println("failed to make an order make sure that the items can be provied");
                             }
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
                     orderManger.move_from_pending_to_approved(order_2);
                     break;
                 case 3:
                     orderManger.print_all_orders_num();
                     break;
                 case 4:
                     System.out.println("Type supplier name :");
                     String supplier = scanner.nextLine();
                     supplier = checkName(supplier);
                     System.out.println("order waiting for approvel :");
                     for (Order order : orderManger.getPendingForApproval()) {
                         if (order.getSupplier().getName().equals(supplier)) {
                             System.out.println(order.getOrderNum());
                         }
                     }
                     System.out.println("order approved and waiting for shipment :");
                     for (Order order : orderManger.getApprovedOrders()) {
                         if (order.getSupplier().getName().equals(supplier)) {
                             System.out.println(order.getOrderNum());
                         }
                     }
                     System.out.println("order history");
                     for (Order order : orderManger.getApprovedOrders()) {
                         if (order.getSupplier().getName().equals(supplier)) {
                             System.out.println(order.getOrderNum());
                         }
                     }
                     break;
                 case 5:
                     orderManger.print_all_orders_details();
                 case 6:
                     System.out.println("Redirecting back to main menu");
                     break;
             }
             break;

         }
     }
    public static String checkName (String input)
    {
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
    public static String checkNumber (String input)
    {
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

    public static String checkNumberWithDot (String input)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i)) && !(input.charAt(i) == '.')) {
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
