import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

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
            System.out.println("Hello this is the manger suppliers board press on the right option");
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

                    Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");

                    Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");

                    Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");

                    item1.setMinQuantity(10);
                    item2.setMinQuantity(50);

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
                    WindowType currentDeliveryDay =WindowType.day2;
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

    }
}

