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

        //todo: complete.... currently isn't correct.

        int ans = 1;
        Scanner myObj = new Scanner(System.in);
        while (ans == 1) {
            System.out.println("What information would you like to receive?");
            System.out.println("1 = reports");
            System.out.println("2 = data");
            System.out.println("0 = leave");

            int whattodo = myObj.nextInt();  // Read user input

            if (whattodo == 1) {

                System.out.println("What report would you like?");
                System.out.println("1 = List of all employees in the store");
                System.out.println("2 = ??????");
                int c = myObj.nextInt();  // Read user input

                if (c == 1) {
                    System.out.println(controller.getAllworkersString());
                }
            }
            if (whattodo == 2) {
                System.out.println("are you a Driver?\n1=yes, 2=no");
                int driverFlag = myObj.nextInt();  // Read user input

                System.out.println("please enter year");
                int yearans = myObj.nextInt();  // Read user input

                System.out.println("please enter week number");
                int weeknum = myObj.nextInt();  // Read user input
                int supernum = 0;
                if(driverFlag != 1) {
                    System.out.println("please enter super number");
                    supernum = myObj.nextInt();  // Read user input
                }

                controller.printweeklyreq(weeknum, yearans, supernum);

            }


            if (whattodo == 0) {
                throw new ExitExeption();
            }

        }
    }
    public static void main(String[] args) throws Exception {
        StoreManagerInterface storeManagerInterface= new StoreManagerInterface();
        storeManagerInterface.logIn();
    }
}
