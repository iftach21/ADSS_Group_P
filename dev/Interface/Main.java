package Interface;

import Interface.Employee.BossInterface;
import Interface.Employee.StoreManagerInterface;
import Interface.Employee.WorkerInterface;
import Interface.Transfer.TransferManagerInterface;
import Interface.UI.UIEmployee;
import Interface.UI.UIHRManager;
import Interface.UI.UIStoreManager;
import Interface.UI.UITransferManager;

import java.net.StandardSocketOptions;
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
        try {
            adminInterface = new AdminInterface();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            storeManagerInterface = new StoreManagerInterface();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }//

    static WorkerInterface workerInterface;
    static TransferManagerInterface transferInterface;
    static AdminInterface adminInterface;
    static StoreManagerInterface storeManagerInterface;
    public static void main(String[] args) throws Exception {
        if(args.length==1 && args[0].compareTo("admin")==0){
            try{
                adminInterface.logIn();}
            catch (ExitExeption e){
                System.out.print("come back again!\n");
                return;
            }

        }

        if(args.length !=2){
            System.out.print("the number of arg are incorrect, please try again:\nfor example: java -jar adss2023_v03.jar CLI HRManager\n");
        }

        if("CLI".compareTo(args[0])==0){
            if("HRManager".compareTo(args[1])==0){
                CLIHRManager();
            }
            else if("Employee".compareTo(args[1])==0){
                CLIEmployee();
            }
            else if("TransferManager".compareTo(args[1])==0){
                CLITransferManager();
            }
            else if("StoreManager".compareTo(args[1])==0){
                CLIStoreManager();
            }
            else{System.out.print("the second arg is incorrect\n");}
        }
        else if("GUI".compareTo(args[0])==0) {
            if ("HRManager".compareTo(args[1]) == 0) {
                GUIHRManager();
            } else if ("Employee".compareTo(args[1]) == 0) {
                GUIEmployee();
            } else if ("TransferManager".compareTo(args[1]) == 0) {
                GUITransferManager();
            } else if ("StoreManager".compareTo(args[1]) == 0) {
                GUIStoreManager();
            } else {
                System.out.print("the second arg is incorrect\n");
            }
        }
        else{System.out.print("the first arg is incorrect\n");}

    }

    private static void CLIHRManager() throws Exception {
        try{
        bossInterface.logIn();}
        catch (ExitExeption e){
         System.out.print("come back again!\n");
        }
    }
    private static void CLIEmployee() throws Exception {
        try{
            workerInterface.logIn();}
        catch (ExitExeption e){
            System.out.print("come back again!\n");
        }
    }
    private static void CLITransferManager() throws Exception {
        try{
            transferInterface.logIn();}
        catch (ExitExeption e){
            System.out.print("come back again!\n");
        }
    }
    private static void CLIStoreManager() throws Exception {
        try{
            storeManagerInterface.logIn();}
        catch (ExitExeption e){
            System.out.print("come back again!\n");
        }
    }
    private static void GUIHRManager() throws Exception {
        UIHRManager UIHRManager = new UIHRManager();
    }


    private static void GUIEmployee() throws Exception {
        UIEmployee employeeUI = new UIEmployee();
        employeeUI.setVisible(true);
    }

    private static void GUITransferManager() throws Exception {
        transferInterface.loadData();
        UITransferManager ui = new UITransferManager();
    }
    private static void GUIStoreManager() throws Exception {
        transferInterface.loadData();
        UIStoreManager storeManager= new UIStoreManager();
    }


}
