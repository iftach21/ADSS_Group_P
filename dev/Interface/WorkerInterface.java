package Interface;

import Domain.WeeklyShiftAndWorkersManager;
import Domain.WindowTypeCreater;

import java.util.Scanner;

public class WorkerInterface extends AInterface {
    private final WeeklyShiftAndWorkersManager controller;

    public WorkerInterface() {
        this.controller = WeeklyShiftAndWorkersManager.getInstance();
    }
    public void logIn() throws ExitExeption {
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

                System.out.println("please enter year");
                int yearans = myObj.nextInt();  // Read user input

                System.out.println("please enter week number");
                int weeknum = myObj.nextInt();  // Read user input

                System.out.println("please enter super number");
                int supernum = myObj.nextInt();  // Read user input

                controller.printweeklyreq(weeknum, yearans, supernum);

            }


            if (whattodo == 0) {
                throw new ExitExeption();
            }

        }
    }
}
