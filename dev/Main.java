import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderManger orderManger =new OrderManger();
        Supplier_Manger supplier_manger=new Supplier_Manger();

        while (true){
            System.out.println("Hello this is the manger_suppliers board press on the right option");
            System.out.println("1.Supplier manger");
            System.out.println("2.Order manger");
            int option = scanner.nextInt();

            switch (option) {
                case 1:

                    System.out.println("You selected Supplier manger");
                    System.out.println("1.add new supplier");
                    System.out.println("2.remove supplier");
                    System.out.println("3.Update contact to supplier");
                    System.out.println("4.add Item to Supplier");
                    System.out.println("5.remove item from Supplier");
                    System.out.println("6.Update item on Contract");
                    System.out.println("7.print all suppliers");

                    int option_1 = scanner.nextInt();
                    switch (option_1){
                        case 1:
                            System.out.println("name:");
                            String name = scanner.next();
                            System.out.println("business id:");
                            String business_id= scanner.next();
                            System.out.println("Payment method:");
                            String payment = scanner.next();
                            System.out.println("Supplier ID");
                            String id = scanner.next();
                            System.out.println("Contact name");
                            String contact_name=scanner.next();
                            System.out.println("number");
                            String phone_number=scanner.next();
                            Contact_Person con_person= new Contact_Person(contact_name,phone_number);
                            System.out.println("type:");
                            System.out.println("1.Non delivery supplier");
                            System.out.println("2.delivery supplier on fixed days");
                            System.out.println("3.delivery supplier on non fixed days");
                            int option_4 =scanner.nextInt();
                            if(option_4==1){
                                NonDeliveringSupplier supplier =new NonDeliveringSupplier(name,business_id,payment,id,con_person,null,null);
                                supplier_manger.getSuppliers().add(supplier);
                            }
                            else if (option_4==2){
                                System.out.println("day:");
                                String day= scanner.next();
                                WindowType day_window =WindowType.valueOf(day.toUpperCase());
                                FixedDaySupplier supplier =new FixedDaySupplier(day_window,name,business_id,payment,id,con_person,null,null);
                                supplier_manger.getSuppliers().add(supplier);

                            }
                            else if(option_4==3){
                                System.out.println("days to deliver:");
                                int day= scanner.nextInt();
                                NonFixedDaySupplier supplier =new NonFixedDaySupplier(day,name,business_id,payment,id,con_person,null,null);
                                supplier_manger.getSuppliers().add(supplier);
                            }
                            break;



                        case 2:
                            System.out.println("name:");
                            String name_s= scanner.next();
                            supplier_manger.remove_supplier(name_s);
                            break;



                        case 3:

                            System.out.println("name of the supplier:");
                            String name_s1= scanner.next();
                            System.out.println("name of the new contact:");
                            String name_s2= scanner.next();
                            System.out.println("his phone number :");
                            String phone_1= scanner.next();
                            supplier_manger.update_contact_preson(name_s1,name_s2,phone_1);
                            break;

                        case 4:

                            System.out.println("name of the supplier:");
                            String name_s3 =scanner.next();
                            System.out.println("Item name:");
                            String item_name =scanner.next();
                            System.out.println("Item catlog number:");
                            String catalogNum = scanner.next();
                            System.out.println("Item weight:");
                            double weight =scanner.nextDouble();
                            System.out.println("catlog Name");
                            String catalogName=scanner.next();
                            System.out.println("temperature");
                            double temp=scanner.nextDouble();

                            System.out.println("exprison date:");
                            String expirationDate_s =scanner.next();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date expirationDate = null;
                            try {
                                 expirationDate = dateFormat.parse(expirationDate_s);
                            } catch (ParseException e) {
                                System.out.println("Invalid date format. Please enter a date in the format dd/MM/yyyy");
                            }
                            //create the new item
                            Item item=new Item(item_name,catalogNum,expirationDate,weight,catalogNum,temp);
                            System.out.println("base price per unit");
                            float price=scanner.nextFloat();
                            System.out.println("amount that can be supplier");
                            int amount =scanner.nextInt();
                            supplier_manger.add_item_to_supplier(name_s3,item,amount,price);
                            break;

                        case 5:
                            System.out.println("name of the supplier:");
                            String name_s4 =scanner.next();
                            System.out.println("name of the item");
                            String item_c5 =scanner.next();
                            supplier_manger.remove_item_to_supplier(name_s4,item_c5);
                            break;

                        case 6:
                            System.out.println("name of the supplier:");
                            String name_s6 =scanner.next();
                            System.out.println("name of the item");
                            String item_c6 =scanner.next();
                            System.out.println("amount:");
                            int amount_6= scanner.nextInt();
                            System.out.println("discount:");
                            double discount_6=scanner.nextDouble();
                            supplier_manger.add_item_discount_to_supplier(name_s6,item_c6,amount_6,discount_6);
                            break;
                        case 7:
                            supplier_manger.print_all_suppliers_names();


                    }
                    break;







                    // TODO: Add code to handle the Supplier manager option
                case 2:
                    System.out.println("You selected Order manger");
                    System.out.println("1.add new Order");
                    System.out.println("2.approve order");
                    System.out.println("2.print all order number");

                    option_1 = scanner.nextInt();
                    switch (option_1){
                        case 1:
                            break;

                    }
                    break;


                    // TODO: Add code to handle the Order manager option
                default:
                    System.out.println("Invalid option selected");
        }
    }
}}

