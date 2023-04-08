import java.util.Scanner;

public class SupplierCard {
    public Supplier createSupplier ()
    {
        Scanner scanner = new Scanner(System.in);
        String contactName;
        String phoneNumber;
        System.out.println("Enter the contact name");
        contactName = scanner.nextLine();
        System.out.println("Enter the contact phone number");
        phoneNumber = scanner.nextLine();
        Contact_Person contact = new Contact_Person(contactName,phoneNumber);

        String supName;
        String id;
        String payment;
        Contact_Person person;
        boolean isDelivering;
        System.out.println("Enter the supplier name");
        supName = scanner.nextLine();
        System.out.println("Enter the id of the supplier");
        id = scanner.nextLine();
        System.out.println("Enter the payment method");
        payment = scanner.next();
        System.out.println("Is the supplier delivering by itself?");
        String input = scanner.nextLine();
        isDelivering = Boolean.parseBoolean(input);


    }
}
