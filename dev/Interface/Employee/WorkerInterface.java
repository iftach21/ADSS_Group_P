package Interface.Employee;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.WindowTypeCreater;
import Interface.AInterface;
import Interface.ExitExeption;

import java.sql.SQLException;
import java.util.Scanner;

public class WorkerInterface extends AInterface {
    private final WeeklyShiftAndWorkersManager controller;

    public WorkerInterface() throws SQLException {
        this.controller = WeeklyShiftAndWorkersManager.getInstance();
    }
    public void logIn() throws ExitExeption, SQLException {
        int ans = 1;
        Scanner myObj = new Scanner(System.in);
        while (ans == 1) {
            System.out.println("what would you like to do?");
            System.out.println("1 = assign to shift");
            System.out.println("2 = see the weekly requirements");
            System.out.println("0 = leave");

            int whattodo = myObj.nextInt();  // Read user input

            if (whattodo == 1) {


                System.out.println("Please insert ID:");

                int id = myObj.nextInt();  // Read user input
                System.out.println("Press 1 to remove or 2 to add  available ");
                int c = myObj.nextInt();  // Read user input
                if (c == 1) {

                    System.out.println("please enter day num");
                    int daynum = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter day/night shift");
                    String nord = myObj.nextLine();  // Read user input
                    WindowTypeCreater wdc = new WindowTypeCreater();

                    controller.removeavalbleforemployee(id, wdc.getwidowtype(daynum, nord));
                } else {
                    System.out.println("please enter day num");
                    int daynum = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter day/night shift");
                    String nord = myObj.nextLine();  // Read user input
                    WindowTypeCreater wdc = new WindowTypeCreater();

                    controller.addavilableforemployee(id, wdc.getwidowtype(daynum, nord));
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
}
