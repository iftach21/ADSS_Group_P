package Interface.Employee;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.WindowTypeCreater;
import Interface.AInterface;
import Interface.ExitExeption;
import Interface.Transfer.TransferManagerInterface;

import java.sql.SQLException;
import java.util.Scanner;

public class StoreManagerInterface extends AInterface{
    private final WeeklyShiftAndWorkersManager controller;
    private final WorkerInterface workerInterface = new WorkerInterface();
    private final BossInterface bossInterface = new BossInterface();

    private final TransferManagerInterface transferInterface = new TransferManagerInterface();



    public StoreManagerInterface() throws SQLException {
        this.controller = WeeklyShiftAndWorkersManager.getInstance();
    }

    public void logIn() throws ExitExeption, SQLException {
        int ans = 1;
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("What system would you like to log into?");
            System.out.println("1 = HR Manager's");
            System.out.println("2 = Employee's");
            System.out.println("3 = Transfer Manager's");
            System.out.println("0 = Exit system");

            //getting input from user
            ans = myObj.nextInt();


            switch (ans){

                //leave:
                case 0 : {throw new ExitExeption();}

                case 1: {
                    try{this.bossInterface.logIn();} catch (ExitExeption e) {
                        break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 2:{
                    try{this.workerInterface.logIn();} catch (ExitExeption e) {
                        break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 3:{
                    try{this.transferInterface.logIn();} catch (ExitExeption e) {
                        break;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
