package Interface;
import Domain.*;
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
        // Create inner interfaces
        // -------------------------------------------------------

        //Interface.Interface.interfaceWorker Interface.Interface.interfaceWorker = new Interface.Interface.interfaceWorker();
        interfaceManager interfaceManager = new interfaceManager();

        // -------------------------------------------------------
        // Login confirmation
        // Check if the user's login information is valid, and prompts a menu for the correct user,
        // either a storage worker or the storage manager
        // -------------------------------------------------------

        //Scans the username and password

        System.out.println("Welcome to 'Super-Li's' storage management system! Please log-in.");
        String UserLogin;
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
                //If the username does not exists, ask the user to re-enter his credentials
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
        // Interface.Main Menu
        // -------------------------------------------------------

        // -------------------------------------------------------
        // Choose correct user interface (Manager / Worker)
        // -------------------------------------------------------

        //If the user is a manager
        if (userName.equals(Manager_Username)) {
            interfaceManager.interfaceStartup();
            interfaceManager.interfaceManagerLogin();
        }

        //Else if the user is a worker
        //else if (userName.equals(Worker_Username)) {
            //Interface.Interface.interfaceWorker.interfaceWorkerLogin();
        //}

    }

}