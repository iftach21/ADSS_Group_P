package Interface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static BossInterface bossInterface;

    static {
        try {
            bossInterface = new BossInterface();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            transferInterface = new TransferManagerInterface();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            workerInterface = new WorkerInterface();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//

    static WorkerInterface workerInterface;
    static TransferManagerInterface transferInterface;
    public static void main(String[] args) {
        List<AInterface> intefaceList = new ArrayList<>();
        intefaceList.add(bossInterface);
        intefaceList.add(workerInterface);
        intefaceList.add(transferInterface);

        int option = 1;
        while(option != 0){
            System.out.println("What system would you like to log into?");
            System.out.println("1 = HR Manager's");
            System.out.println("2 = Employee's");
            System.out.println("3 = Transfer Manager's");
            System.out.println("0 = Exit system");


            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            option = myObj.nextInt();  // Read user input

            //checking if the option works
            if(option > 3 || option < 0){
                System.out.println("Invalid Input");
                continue;
            }

            try{
                intefaceList.get(option -1).logIn();
          }
            catch (ExitExeption e){
                   continue;
                 }

             catch (Exception e){
                System.out.println("Seems to be a problem, please contact IT");
            }
        }

    }
}
