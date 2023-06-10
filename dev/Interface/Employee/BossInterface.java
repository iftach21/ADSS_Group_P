package Interface.Employee;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.*;
import Interface.AInterface;
import Interface.ExitExeption;

import java.util.Calendar;
import java.sql.SQLException;
import java.util.Scanner;  // Import the Scanner class
public class BossInterface extends AInterface {
    /**
     * This class allows the users interact with the internal classes.
     * allows creating weeklyShift and workers to update when they can work.
     */

    private static final String passward = "12345"; // the password for the system
    private static final String username = "theboss"; // the username for the system
    private final WeeklyShiftAndWorkersManager controller;



    public BossInterface() throws SQLException {
        this.controller = WeeklyShiftAndWorkersManager.getInstance();
    }
    public void logIn() throws Exception {
        //===============================
        //    login confirmation
        //===============================


        //checks for 24 next 24 hrs if its ok:
        Calendar cal = Calendar.getInstance();
        int todayweek = cal.get(Calendar.WEEK_OF_YEAR);
        int todayyear = cal.get(Calendar.YEAR);
        int todaydayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        controller.checkIfShiftsFor24HourIsOkay(todayweek,todaydayOfWeek,todayyear);



        //==================================================
        //
        //                      main menu
        //
        //==================================================
        boolean exit = true;
        while(exit){
            System.out.println("please choose what you would like to do:");
            System.out.println("and type the number accordingly");
            System.out.println("1: create new weekly shift");
            System.out.println("2: add to existing weekly shift");
            System.out.println("3: switch employees in a shift");
            System.out.println("4: fire employee");
            System.out.println("5: add new employee");
            System.out.println("6: change wage to employee");
            System.out.println("7: get weekly wage for employee");
            System.out.println("8: change employees contract");
            System.out.println("9: update employees bank number");
            System.out.println("10: add available for employee");
            System.out.println("11: add new jobs for employee");
            System.out.println("12: remove jobs for employee");
            System.out.println("13: remove available for employee");
            System.out.println("14: show the weekly shift");
            System.out.println("15: change personal info for employee");
            System.out.println("16: show all employees");

            System.out.println("0: to exit the system ");

            //end of menu


            Scanner myObj = new Scanner(System.in);
            String ans = myObj.nextLine();  // Read user input

            switch (ans){
                //-----------------------
                //create new weekly shift
                //-----------------------
                case "1":{

                    System.out.println("is it weekly shift for drivers?\n 1=yes, 2=no");
                    int driversflag = myObj.nextInt();  // Read user input

                    System.out.println("please enter year");
                    int yearans = myObj.nextInt();  // Read user input
                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input
                    int supernum = 0;

                    if (driversflag != 1) {
                        System.out.println("please enter super number");
                        supernum = myObj.nextInt();  // Read user input
                    }

                    controller.createnewweeklyshift(weeknum,yearans,supernum);

                    //now letting you add some:
                    System.out.println("now you can create shift req");
                    int innerans = 1;
                    WindowTypeCreater wc = new WindowTypeCreater();



                    while(innerans==1){

                        System.out.println("please enter day number in the week");
                        int daynum = myObj.nextInt();  // Read user input
                        myObj.nextLine();

                        System.out.println("please enter night or day");
                        String don = myObj.nextLine();  // Read user input

                        System.out.println("what time would you like it to start?");
                        String starttime = myObj.nextLine();  // Read user input

                        //set the time for the shift:
                        controller.settimeforweeklyshift(weeknum,yearans,supernum,daynum,don,starttime);

                        int prof = 7;
                        if(driversflag != 1) {
                            System.out.println("please enter int for the persons prof");
                            System.out.println("0=manager");
                            System.out.println("1=cashier");
                            System.out.println("2=stoke");
                            System.out.println("3=security");
                            System.out.println("4=cleaning");
                            System.out.println("5=shelf-stoking");
                            System.out.println("6= general-worker");
                            prof = myObj.nextInt();  // Read user input
                        }

                        System.out.println("how much would you like from that type?");
                        int hm = myObj.nextInt();  // Read user input

                        controller.addreqtoweeklyshift(weeknum,yearans,supernum,daynum,don,prof,hm);


                        System.out.println("would you like to keep going? \n 1= yes, 0= no");
                        innerans = myObj.nextInt();  // Read user input
                    }


                    break;
                }


                //---------------------------
                //add to existing weekly shift
                //----------------------------
                case "2":{

                    System.out.println("is it weekly shift for drivers?\n 1=yes, 2=no");
                    int driversflag = myObj.nextInt();  // Read user input


                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    int supernum = 0;
                    if(driversflag != 1) {
                        System.out.println("please enter super number");
                        supernum = myObj.nextInt();  // Read user input
                    }


                    while(true) {

                        System.out.println("please enter day number in the week");
                        int daynum = myObj.nextInt();  // Read user input
                        myObj.nextLine();

                        System.out.println("please enter night or day");
                        String don = myObj.nextLine();  // Read user input
                        int changer = 1;
                        while (changer == 1) {
                            //stayes on same shift:
                            int prof = 7;
                            WindowTypeCreater wc = new WindowTypeCreater();
                            if(driversflag != 1) {
                                System.out.println("please enter int for the persons prof");
                                System.out.println("0=manager");
                                System.out.println("1=cashier");
                                System.out.println("2=stoke");
                                System.out.println("3=security");
                                System.out.println("4=cleaning");
                                System.out.println("5=shelf-stoking");
                                System.out.println("6= general-worker");

                                prof = myObj.nextInt();  // Read user input
                            }



                            while(true) {
                                try{
                                //shows the user all the people whom can work
                                controller.printAllWorkersWhoCanWork(prof, daynum, don);
                                System.out.println("please enter id");
                                int id = myObj.nextInt();  // Read user input
                                controller.addtoexistingweeklyshift(weeknum, year, supernum, wc.getwidowtype(daynum, don), id, prof);
                                break;}
                                catch (Exception e){}

                            }


                            System.out.println("would you like to change day and shift type? y=0 no = 1");
                            changer = myObj.nextInt();  // Read user input
                        }
                        System.out.println("would you like to keep going? y=0 no = 1");
                        int ender = myObj.nextInt();  // Read user input
                        if(ender==1){break;}
                    }
                    break;
                }


                //--------------------------
                //switch employees in a shift
                //---------------------------
                case "3":{
                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    System.out.println("please enter day number in the week");
                    int daynum = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter night or day");
                    String don = myObj.nextLine();  // Read user input
                    WindowTypeCreater wt = new WindowTypeCreater();

                    System.out.println("please enter int for the persons prof");
                    System.out.println("0=manager");
                    System.out.println("1=cashier");
                    System.out.println("2=stoke");
                    System.out.println("3=security");
                    System.out.println("4=cleaning");
                    System.out.println("5=shelf-stoking");
                    System.out.println("6= general-worker");
                    System.out.println("7= Driver");
                    int prof = myObj.nextInt();  // Read user input

                    System.out.println("please enter id for the employee you want to remove");
                    int id1 = myObj.nextInt();  // Read user input

                    System.out.println("please enter id for the employee you want to put in");
                    int id2 = myObj.nextInt();  // Read user input

                    controller.switchemployeeinashift(weeknum,year,supernum,wt.getwidowtype(daynum,don),prof,id1,id2);
                }

                //-----------------------
                //fire employee
                //-----------------------
                case "4":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    controller.fireemployee(idans);
                    break;
                }

                //-----------------------
                //add new employee
                //-----------------------
                case "5":{
                    //Domain.Employee.Workers(int id, String name, String contract,
                    // String start_date, int wage, int phoneNUM, String personalinfo, int bankNum)

                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter name for the employee");
                    String name = myObj.nextLine();  // Read user input

                    System.out.println("please enter contract for the employee");
                    String contract = myObj.nextLine();  // Read user input

                    System.out.println("please enter start_date for the employee");
                    String start_date = myObj.nextLine();  // Read user input

                    System.out.println("please enter wage for the employee");
                    int wage = myObj.nextInt();  // Read user input

                    System.out.println("please enter phoneNUM for the employee");
                    int phoneNUM = myObj.nextInt();  // Read user input5
                    myObj.nextLine();

                    System.out.println("please enter personalinfo for the employee");
                    String personalinfo = myObj.nextLine();  // Read user input

                    System.out.println("please enter bankNum for the employee");
                    int bankNum = myObj.nextInt();  // Read user input

                    System.out.println("is he a driver?\n 1 = yes, 2 = no");
                    int isdriver = myObj.nextInt();  // Read user input

                    if(isdriver == 1){
                        System.out.println("what is his weightType?\n 1=lightWeight,\n" +
                                "    2= mediumWeight,\n" +
                                "    3= heavyWeight;");
                        int wt = myObj.nextInt();  // Read user input
                        System.out.println("what is his TempType?\n1= regular, 2= cold,3= frozen");
                        int tt = myObj.nextInt();  // Read user input
                        controller.addNewDriver(id,name,contract,start_date,wage,phoneNUM,personalinfo,bankNum,
                                TempTypeFactory.TempLevel(tt), WeightTypeFactory.weightType(wt));

                    }
                    else {
                        controller.addemployee(id, name, contract, start_date, wage, phoneNUM, personalinfo, bankNum);
                    }
                    break;

                }


                //-----------------------
                //change wage to employee
                //-----------------------
                case "6":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    System.out.println("how much would you like to add/deduct?");
                    int wageadd = myObj.nextInt();  // Read user input
                    controller.addwagetoemployee(idans,wageadd);
                    break;
                }


                //-----------------------
                //get weekly wage for employee
                //-----------------------
                case "7":{
                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input

                    System.out.println("what week num do you need?");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("what year num do you need?");
                    int year = myObj.nextInt();  // Read user input

                    int answage = controller.getwageforemployee(id,weeknum,year);
                    System.out.println(answage);
                    break;
                }


                //-----------------------
                //change employees contract
                //-----------------------
                case "8":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter new contract");
                    String newcontract = myObj.nextLine();  // Read user input
                    controller.changeemployeecontract(idans,newcontract);
                    break;
                }


                //----------------------------
                //update employees bank number
                //----------------------------
                case "9":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    System.out.println("please enter new bank num");
                    int banknum = myObj.nextInt();  // Read user input
                    controller.updateemployeesbank(idans,banknum);
                    break;
                }

                //-----------------------------
                //update available for employee
                //-----------------------------
                case "10":{
                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input

                    System.out.println("please enter day num");
                    int daynum = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter day/night shift");
                    String nord = myObj.nextLine();  // Read user input
                    WindowTypeCreater wdc = new WindowTypeCreater();

                    controller.addavilableforemployee(id,wdc.getwidowtype(daynum,nord));
                    break;

                }

                //------------------------
                //add new jobs for employee
                //------------------------
                case "11":{
                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input

                    System.out.println("please enter int for the persons prof");
                    System.out.println("0=manager");
                    System.out.println("1=cashier");
                    System.out.println("2=stoke");
                    System.out.println("3=security");
                    System.out.println("4=cleaning");
                    System.out.println("5=shelf-stoking");
                    System.out.println("6= general-worker");
                    int prof = myObj.nextInt();  // Read user input

                    controller.addnewproforemployee(id,prof);

                    break;
                }


                //------------------------
                //remove jobs for employee
                //------------------------

                case "12":{
                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input

                    System.out.println("please enter int for the persons prof");
                    System.out.println("0=manager");
                    System.out.println("1=cashier");
                    System.out.println("2=stoke");
                    System.out.println("3=security");
                    System.out.println("4=cleaning");
                    System.out.println("5=shelf-stoking");
                    System.out.println("6= general-worker");
                    int prof = myObj.nextInt();  // Read user input

                    controller.removeprofforemployee(id,prof);

                    break;
                }

                //-----------------------
                //remove window for employee
                //-----------------------
                case "13":{
                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input

                    System.out.println("please enter day num");
                    int daynum = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter day/night shift");
                    String nord = myObj.nextLine();  // Read user input
                    WindowTypeCreater wdc = new WindowTypeCreater();

                    controller.removeavalbleforemployee(id,wdc.getwidowtype(daynum,nord));
                    break;
                }
                //-------------------------
                //14: show the weekly shift"
                //-------------------------
                case "14":{
                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    controller.printweeklyshift(weeknum,year,supernum);
                    break;
                }
                case "15":{
                    System.out.println("please enter id");
                    int id = myObj.nextInt();  // Read user input
                    myObj.nextLine();

                    System.out.println("please enter the new personal info");
                    String persoinfo = myObj.nextLine();  // Read user input

                    controller.setPersonalinfo(id,persoinfo);
                    break;
                }
                case "16":{
                    controller.printall();
                    break;
                }


                //exiting
                case "0":{ exit = false;

                    throw new ExitExeption();
                    }
                default:{ System.out.println("please enter valid input");}
            }

        }

    }//login



}//class


