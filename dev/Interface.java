import java.util.List;
import java.util.Scanner;  // Import the Scanner class
public class Interface {
    private static final String passward = "12345"; // the password for the system
    private static final String username = "theboss"; // the username for the system

    private List<WeeklyShift> weeklyShiftList; //holds all the weeklyshifts
    private List<Workers> allworkerslist; //holds all the workers available

    public Interface() {
        //this will just create the object
        //todo: create employees and shifts later
    }
    public void logIn(){
        //===============================
        //    login confirmation
        //===============================
        //scans the username and password:
        while(true) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter username");
            String userName = myObj.nextLine();  // Read user input
            System.out.println("Enter password");  // Output user input
            String password = myObj.nextLine();  // Read user input
            if(userName.equals(username)&&password.equals(passward)){
                break;
            }
            else{
                System.out.println("invalid input, please try again");  // Output user input
            }
        }
        //=================================================
        //
        //                      main menu
        //
        //==================================================
        while(true){
            System.out.println("please choose what you would like to do:");
            System.out.println("and type the number accordingly");
            System.out.println("1: create new weekly shift");
            System.out.println("2: add to existing weekly shift");
            System.out.println("3: switch employees in a shift");
            System.out.println("4: fire employee");
            System.out.println("5: add new employee");
            System.out.println("6: change wage to employee");
            System.out.println("7: get monthly wage for employee");
            System.out.println("8: change employees contract");
            System.out.println("9: update employees bank number");
            System.out.println("10: update take availble for employee");


        }


    }//login












}//class
