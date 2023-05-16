package Interface;
import Domain.*;
import java.util.Scanner;

public class SupplierInterface extends AInterface
{
    private Supplier_Manger supplier_manger;
    private OrderManger orderManger;

    public SupplierInterface()
    {
        this.supplier_manger = new Supplier_Manger();
    }
    @Override
    public void interfaceStartup() {
        Scanner scanner = new Scanner(System.in);
        int option_1 =0;
        {
            while (true) {
                String option;
                int choice = 0;
                //supplier menu to manipulate them and their items
                System.out.println("You selected Supplier manger");
                System.out.println("1.add new supplier");
                System.out.println("2.remove supplier");
                System.out.println("3.Update contact to supplier");
                System.out.println("4.add Item to Supplier");
                System.out.println("5.remove item from Supplier");
                System.out.println("6.Update item on Contract");
                System.out.println("7.print all suppliers");
                System.out.println("8.print supplier  by id's");
                System.out.println("9.get back to previous menu");


                while (true) {
                    try {
                        option = scanner.nextLine();
                        option_1 = Integer.parseInt(option);
                        if (option_1 < 1 || option_1 > 9) {
                            System.out.println("Please enter a valid option");
                            continue;
                        }
                        break;
                    } catch (Exception ignored) {
                        System.out.println("Please enter a valid option");
                        //option = scanner.nextLine();
                    }
                }

                switch (option_1) {
                    case 1:
                        System.out.println("name:");
                        String name = scanner.nextLine();
                        name = checkSupplierNameExistInTheSystemForInserting(name);
                        System.out.println("business id:");
                        String business_id = scanner.next();
                        business_id = checkBusinessId(business_id);
                        //payment opiton
                        System.out.println("Payment method:");
                        System.out.println("1.SHOTEF");
                        System.out.println("2.SHOTEF+30");
                        System.out.println("3.SHOTEF+60");
                        String payment = scanner.next();
                        int paymentNum = 0;
                        while (true) {

                            try {
                                paymentNum = Integer.parseInt(payment);
                                if (paymentNum < 1 || paymentNum > 3) {
                                    System.out.println("Please enter a valid option");
                                    payment = scanner.next();
                                }

                            } catch (Exception ignored) {
                                System.out.println("Please enter a valid option");
                                payment = scanner.next();
                            }
                            if (!(paymentNum < 1 || paymentNum > 3)) {
                                break;
                            }
                        }

                        System.out.println("Supplier ID");
                        String id = scanner.next();
                        id = checkSupplierId(id);
                        System.out.println("Contact name");
                        String contact_name = scanner.next();
                        contact_name = checkName(contact_name);
                        System.out.println("number");
                        String phone_number = scanner.next();
                        phone_number = checkNumber(phone_number);
                        ContactPerson con_person = new ContactPerson(contact_name, phone_number);

                        //delivery option
                        System.out.println("type:");
                        System.out.println("1.Non delivery supplier");
                        System.out.println("2.delivery supplier on fixed days");
                        System.out.println("3.delivery supplier on non fixed days");
                        String supType = scanner.next();
                        int option_4;
                        while (true) {
                            try {
                                option_4 = Integer.parseInt(supType);
                                if (option_4 < 1 || option_4 > 3) {
                                    System.out.println("Please enter a valid option");
                                    supType = scanner.next();
                                    continue;
                                }
                                break;
                            } catch (Exception ignored) {
                                System.out.println("Please enter a valid option");
                                supType = scanner.next();
                            }
                        }


                        if (option_4 == 1) {
                            NonDeliveringSupplier supplier = new NonDeliveringSupplier(name, business_id, paymentNum - 1, id, con_person, null, null);
                            supplier_manger.add_supplier(supplier);
                        } else if (option_4 == 2) {
                            System.out.println("day:");
                            String day = scanner.next();
                            int dayNum;
                            while (true) {
                                try {
                                    dayNum = Integer.parseInt(day);
                                    if (dayNum < 1 || dayNum > 7) {
                                        System.out.println("Please enter a valid day");
                                        day = scanner.next();
                                        continue;
                                    }
                                    break;
                                } catch (Exception ignored) {
                                    System.out.println("Please enter a valid option");
                                    day = scanner.next();
                                }
                            }
                            WindowType day_window;
                            day_window = WindowTypeCreater.getwindowtype(dayNum);
                            FixedDaySupplier supplier = new FixedDaySupplier(day_window, name, business_id, paymentNum - 1, id, con_person, null, null);
                            supplier_manger.add_supplier(supplier);

                        } else {
                            System.out.println("days to deliver:");
                            String day = scanner.next();
                            int numOfDeliveryDays = 0;
                            while (true) {
                                try {
                                    numOfDeliveryDays = Integer.parseInt(day);
                                    if (numOfDeliveryDays < 1 || numOfDeliveryDays > 7) {
                                        System.out.println("Please enter a valid day");
                                        day = scanner.next();
                                        continue;
                                    }
                                    break;
                                } catch (Exception ignored) {
                                    System.out.println("Please enter a valid option");
                                    day = scanner.next();
                                }
                            }
                            NonFixedDaySupplier supplier = new NonFixedDaySupplier(numOfDeliveryDays, name, business_id, paymentNum - 1, id, con_person, null, null);
                            supplier_manger.add_supplier(supplier);
                        }
                        break;


                    case 2:
                        System.out.println("name:");
                        String name_s = scanner.next();
                        name_s = checkSupplierNameExistInTheSystemForDeleting(name_s);
                        if (!supplier_manger.remove_supplier(name_s)) {
                            System.out.println("No supplier like this exist");
                        }

                        break;


                    case 3:

                        System.out.println("name of the supplier:");
                        String name_s1 = scanner.next();
//                        name_s1 = checkSupplierNameExistInTheSystemForInserting(name_s1);
                        name_s1 = checkSupplierNameExistInTheSystemForDeleting(name_s1);
                        System.out.println("name of the new contact:");
                        String name_s2 = scanner.next();
                        name_s2 = checkName(name_s2);
                        System.out.println("his phone number :");
                        String phone_1 = scanner.next();
                        phone_1 = checkNumber(phone_1);
                        //give an output if the supplier not exist
                        supplier_manger.update_contact_preson(name_s1, name_s2, phone_1);
                        break;

                    case 4:
                        System.out.println("name of the supplier:");
                        String name_s3 = scanner.nextLine();
//                        name_s3 = checkSupplierNameExistInTheSystemForInserting(name_s3);
                        name_s3 = checkSupplierNameExistInTheSystemForDeleting(name_s3);
                        System.out.println("Item name:");
                        String item_name = scanner.next();
//                        item_name = checkName(item_name); // TODO maybe its needed
                        System.out.println("Item catlog number:");
                        String catalogNum = scanner.next();
                        catalogNum = checkItemCatalogNumber(catalogNum); // TODO check that the catalogNumber is uniq
                        System.out.println("Item weight:");
                        String weightInput = scanner.next();
                        weightInput = checkNumberWithDot(weightInput);
                        double weight = Double.parseDouble(weightInput);
                        System.out.println("catalog Name");
                        String catalogName = scanner.next();
//                        catalogName = checkName(catalogName);// TODO maybe its needed

                        System.out.println("temperature ((0)regular,(1)frozen,(2)cold)");// TODO there are only 3 options : regular, frozen, cold
                        String tempInput;
//                        int tempLevelInt = -1;
                        while(true)
                        {
                            tempInput = scanner.next();
//                            try {
//                                tempLevelInt = Integer.parseInt(tempInput);
                                if (tempInput.equals("regular") || tempInput.equals("cold") || tempInput.equals("frozen"))
                                {
                                    break;
                                }
                                else
                                {
                                    System.out.println("Please enter a valid option");
//                                    tempInput = scanner.next();
                                }
//                            }
//                            catch (Exception ignored)
//                            {
//                                System.out.println("Please enter a valid option");
//                                tempInput = scanner.next();
//                            }
                        }
                        TempLevel tempLevel = TempLevel.valueOf(tempInput);
//                            tempInput = checkNumberWithDot(tempInput);
//                        double temp = Double.parseDouble(tempInput);
                        System.out.println("manufacturer:");
                        String manufacturer = scanner.next();
                        manufacturer = checkName(manufacturer);

                        //create the new item
//                            Item item = new Item(item_name, catalogName, expirationDate_s, weight, catalogNum, temp);
                        Item item = new Item(item_name, catalogNum, weight, catalogName, tempLevel, manufacturer);

//                            try {
//                                Date date = item.getDate();
//                                // Do something with the date
//                            } catch (ParseException e) {
//                                System.out.println("Invalid date format: " + item.getDateStr());
//                                e.printStackTrace();
//                                break;
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
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
                        String name_s4 = scanner.nextLine();
                        name_s4 = checkSupplierNameExistInTheSystemForDeleting(name_s4);
                        System.out.println("catalog number of the item");
                        String item_c5 = scanner.next();
//                        item_c5 = checkName(item_c5);
                        if (!supplier_manger.remove_item_to_supplier(name_s4, item_c5)) {
                            System.out.println("Supplier or item does not exist");
                        }
                        supplier_manger.remove_item_to_supplier(name_s4,item_c5);
                        break;

                    case 6:
                        System.out.println("name of the supplier:");
                        String name_s6 = scanner.nextLine();
                        name_s6 = checkSupplierNameExistInTheSystemForDeleting(name_s6);
                        System.out.println("catalog number of the item");
                        String item_c6 = scanner.next();
//                        item_c6 = checkName(item_c6);
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
                        System.out.println("supplier id:");
                        String id_sup1 = scanner.next();
//                        int amount_7 = Integer.parseInt(id_sup1);
                        supplier_manger.get_supplier_by_id(id_sup1).print();

                    case 9:
                        System.out.println("Redirecting back to main menu");
//                        break;
                }
                break;
            }
        }
    }
    public String checkName (String input)
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
            if (counter == input.length())
            {
                return input;
            }
            else
            {
                System.out.println("name:");
                input = scanner.nextLine();
            }
        }
    }

    public String checkSupplierNameExistInTheSystemForInserting (String input)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            if (this.supplier_manger.checkExistingSupplierName(input))
            {
                return input;
            }
            else
            {
                System.out.println("this name exists in the system already");
                System.out.println("name:");
                input = scanner.nextLine();
            }
        }
    }

    public String checkSupplierNameExistInTheSystemForDeleting (String input)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            if (!this.supplier_manger.checkExistingSupplierName(input))
            {
                return input;
            }
            else
            {
//                System.out.println("this name exists in the system already");
                System.out.println("name:");
                input = scanner.nextLine();
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

    public String checkBusinessId (String input)
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
            if (counter == input.length() && this.supplier_manger.checkExistingBusinessId(input)) {
                return input;
            } else {
                System.out.println("BusinessId number:");
                input = scanner.next();
            }
        }
    }

    public String checkItemCatalogNumber (String input)
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
            if (counter == input.length() && this.supplier_manger.checkExistingItemCatalogNumber(input)) {
                return input;
            } else {
                System.out.println(" catalog number:");
                input = scanner.next();
            }
        }
    }

    public String checkSupplierId(String input)
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
            if (counter == input.length() && this.supplier_manger.checkExistingSupplierId(input)) {
                return input;
            } else {
                System.out.println("SupplierId number:");
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


