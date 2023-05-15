package Interface;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.weightType;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminInterface extends AInterface {
    private static final String passward = "12345"; // the password for the system
    private static final String username = "admin"; // the username for the system
    private final WeeklyShiftAndWorkersManager controller = WeeklyShiftAndWorkersManager.getInstance();

    public AdminInterface() throws SQLException {
    }

    @Override
    public void logIn() throws Exception {

        //login confirmation:
        //-----------------------------------------------------
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
        //-----------------------------------------------------------------------


        boolean exit = true;
        while(exit){
            System.out.println("please choose what you would like to do:");
            System.out.println("and type the number accordingly");
            System.out.println("1: reboot Database:");
            System.out.println("2: Delete all database:");
            System.out.println("0: go back");



            //end of menu


            Scanner myObj = new Scanner(System.in);
            int ans = myObj.nextInt();  // Read user input

            if(ans==1){
                this.startup();
            }
            if(ans==2){
                this.controller.deleteAllRows();
            }
            if(ans==0){
                throw new ExitExeption();
            }
        }

    }
    public void startup() throws SQLException {

        //drivers:
        controller.addNewDriver(111,"Moshe","lotsofmoney","23.2.23",90,12345,"student",1234, TempLevel.cold, weightType.lightWeight);

        controller.addNewDriver(222,"Yaakov","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.mediumWeight);

        controller.addNewDriver(333,"Gilad","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.frozen,weightType.heavyWeight);

        controller.addNewDriver(444,"Shimon","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.lightWeight);

        controller.addNewDriver(888,"Bracha","lotsofmoney","23.2.23",90,12345,"student",1234, TempLevel.cold, weightType.lightWeight);

        controller.addNewDriver(999,"Tomer","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.mediumWeight);

        controller.addNewDriver(1212,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.frozen,weightType.heavyWeight);

        controller.addNewDriver(1313,"shani","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.lightWeight);


        //stoke workers:
        controller.addemployee(555,"Posa","lotsofmoney", "23.2.23",90,12345,"student",1234);

        controller.addemployee(666,"Yishai","lotsofmoney", "23.2.23",90,12345,"student",1234);

        controller.addemployee(1919,"Nimi","lotsofmoney", "23.2.23",90,12345,"student",1234);

        controller.addemployee(2020,"Tooli","lotsofmoney", "23.2.23",90,12345,"student",1234);


        //creating weeklyshift:
        controller.createnewweeklyshift(1,2024,4);

        controller.createnewweeklyshift(1,2024,5);

        controller.createnewweeklyshift(1,2024,0);

        controller.createnewweeklyshift(1,2024,9);

        controller.createnewweeklyshift(1,2024,10);

        controller.createnewweeklyshift(1,2024,14);

        controller.createnewweeklyshift(1,2024,15);



        // all of the data is for week num 1 and year 1:
        //stokes will be available for shipment:
        //super 4:
        controller.addtoexistingweeklyshift(1,2024,4, WindowType.day1,555,2);
        controller.addtoexistingweeklyshift(1,2024,4,WindowType.day1,666,2);

        controller.addtoexistingweeklyshift(1,2024,4, WindowType.day2,555,2);
        controller.addtoexistingweeklyshift(1,2024,4,WindowType.day2,666,2);

        //super 5:
        controller.addtoexistingweeklyshift(1,2024,5, WindowType.day3,555,2);
        controller.addtoexistingweeklyshift(1,2024,5,WindowType.day3,666,2);

        controller.addtoexistingweeklyshift(1,2024,5, WindowType.day4,555,2);
        controller.addtoexistingweeklyshift(1,2024,5,WindowType.day4,666,2);

        //super 9:
        controller.addtoexistingweeklyshift(1,2024,9, WindowType.day4,1919,2);
        controller.addtoexistingweeklyshift(1,2024,9,WindowType.day3,2020,2);

        //super 10:
        controller.addtoexistingweeklyshift(1,2024,10,WindowType.day4,2020,2);
        controller.addtoexistingweeklyshift(1,2024,10, WindowType.day1,1919,2);

        //super 14:
        controller.addtoexistingweeklyshift(1,2024,14, WindowType.day2,2020,2);
        controller.addtoexistingweeklyshift(1,2024,14,WindowType.day2,1919,2);

        //super 15:
        controller.addtoexistingweeklyshift(1,2024,15,WindowType.day1,2020,2);
        controller.addtoexistingweeklyshift(1,2024,15, WindowType.day3,1919,2);






        //available for drivers:
        //day 1:
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day1,111,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day1,222,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day1,1212,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day1,1313,7);

        //day 2
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day2,888,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day2,999,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day2,1212,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day2,1313,7);

        //day 3:
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,111,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,222,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,333,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,444,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,888,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,999,7);

        //day 4
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day4,1212,7);
        controller.addtoexistingweeklyshift(1,2024,0, WindowType.day4,1313,7);





    }
}
