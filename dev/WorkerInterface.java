import java.util.Scanner;

public class WorkerInterface {
    private WeeklyShiftAndWorkersManager controller;

    public WorkerInterface() {
        this.controller = WeeklyShiftAndWorkersManager.getInstance();
    }
    public void logInWorker() {
        System.out.println("Please insert ID:");
        Scanner myObj = new Scanner(System.in);
        int id = myObj.nextInt();  // Read user input
        System.out.println("Press 1 to remove or 2 to add  available ");
        int c = myObj.nextInt();  // Read user input
        if (c==1){

            System.out.println("please enter day num");
            int daynum = myObj.nextInt();  // Read user input
            myObj.nextLine();

            System.out.println("please enter day/night shift");
            String nord = myObj.nextLine();  // Read user input
            WindowTypeCreater wdc = new WindowTypeCreater();

            controller.removeavalbleforemployee(id,wdc.getwidowtype(daynum,nord));
        }
        else{
            System.out.println("please enter day num");
            int daynum = myObj.nextInt();  // Read user input
            myObj.nextLine();

            System.out.println("please enter day/night shift");
            String nord = myObj.nextLine();  // Read user input
            WindowTypeCreater wdc = new WindowTypeCreater();

            controller.addavilableforemployee(id,wdc.getwidowtype(daynum,nord));
        }

    }
}
