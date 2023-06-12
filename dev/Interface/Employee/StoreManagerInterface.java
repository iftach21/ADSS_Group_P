package Interface.Employee;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.WindowTypeCreater;
import Interface.AInterface;
import Interface.ExitExeption;

import java.sql.SQLException;
import java.util.Scanner;

public class StoreManagerInterface extends AInterface{
    private final WeeklyShiftAndWorkersManager controller;

    public StoreManagerInterface() throws SQLException {
        this.controller = WeeklyShiftAndWorkersManager.getInstance();
    }

    public void logIn() throws ExitExeption, SQLException {
        int ans = 1;
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("What information would you like to receive?");
            System.out.println("1 = List of all employees");
            System.out.println("2 = write event to Shift");
            System.out.println("3 = Get events from Shifts");
            System.out.println("4 = Get all Employees that worked in specific weeklyShift");
            System.out.println("0 = leave");

            //getting input from user
            ans = myObj.nextInt();


            switch (ans){

                //leave:
                case 0 : {throw new ExitExeption();}

                case 1: {
                    //List of all employees:
                    System.out.println(controller.getAllworkersString());
                    break;
                }
                case 2:{
                    //write event to weeklyShift
                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    System.out.println("please enter day number in the week");
                    int daynum = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter night or day");
                    String don = myObj.nextLine();  // Read user input

                    System.out.println("please enter the event you want to add");
                    String messege = myObj.nextLine();  // Read user input

                    controller.writeToEventOfShift(weeknum,year,supernum,daynum,don,messege);
                    break;
                }
                case 3:{
                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    System.out.println("please enter day number in the week");
                    int daynum = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter night or day");
                    String don = myObj.nextLine();  // Read user input


                    System.out.println(controller.getEventOfShift(weeknum,year,supernum,daynum,don));
                    break;
                }

                case 4:{
                    //getAllWorkersThatWorkedInSpecificWeek(int weeknum, int year,int supernum)
                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    System.out.println(controller.getAllWorkersThatWorkedInSpecificWeek(weeknum, year, supernum));

                    break;
                }
                default:{System.out.println("Invalid input, try again\n");}

            }

        }//end of while:
    }
    public static void main(String[] args) throws Exception {
        StoreManagerInterface storeManagerInterface= new StoreManagerInterface();
        storeManagerInterface.logIn();
    }
}
