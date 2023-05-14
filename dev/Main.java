import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Interface log = new Interface();
        log.logIn();
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
            System.out.println("5.Exit");
            option = scanner.nextLine();

            while(true)
            {
                try
                {
                    choice = Integer.parseInt(option);
                    if(choice < 1 || choice > 5)
                    {
                        //the main menu there he can choose what to do
                        System.out.println("Please enter a valid option");
                        System.out.println("1.Supplier manger");
                        System.out.println("2.Order manger");
//                        System.out.println("3.insert demo data to the system for test and playing ");

                        System.out.println("4.Exit");
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
                case 2:
                    interfaces[1].interfaceStartup();
                case 3:
                    //interfaces[2].interfaceStartup();
                    interfaces[2].interfaceManagerLogin();
                case 4:
                    //interfaces[3].interfaceStartup();
                    interfaces[3].interfaceWorkerLogin();
                case 5:
                    System.out.println("Thank you and goodbye");
                    System.exit(0);
                    break;
            }

        }

    }
}

