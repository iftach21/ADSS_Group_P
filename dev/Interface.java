import java.util.Iterator;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
public class Interface {
    private static final String passward = "12345"; // the password for the system
    private static final String username = "theboss"; // the username for the system

    private List<WeeklyShift> weeklyShiftList; //holds all the weeklyshifts
    private List<Workers> allworkerslist; //holds all the workers available

    public Interface() {
        //this will just create the object
        //todo: create employees and shifts later
    }
    public void logIn(){
        //===============================
        //    login confirmation
        //===============================
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
        //=================================================
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
            System.out.println("7: get monthly wage for employee");
            System.out.println("8: change employees contract");
            System.out.println("9: update employees bank number");
            System.out.println("10: update available for employee");
            System.out.println("11: add new jobs for employee");
            System.out.println("12: remove jobs for employee");

            System.out.println("0: to exit the system ");

            //end of menu


            Scanner myObj = new Scanner(System.in);
            String ans = myObj.nextLine();  // Read user input

            switch (ans){
                //-----------------------
                //create new weekly shift
                //-----------------------
                case "1":{
                    System.out.println("please enter year");
                    int yearans = myObj.nextInt();  // Read user input
                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    this.createnewweeklyshift(weeknum,yearans);
                }
                //-----------------------
                //add to existing weekly shift
                //-----------------------
                case "2":{
                    //todo: complete later
                }
                //-----------------------
                //switch employees in a shift
                //-----------------------
                case "3":{
                    //todo: complete later
                }
                //-----------------------
                //fire employee
                //-----------------------
                case "4":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    this.fireemployee(idans);
                }
                //-----------------------
                //add new employee
                //-----------------------
                case "5":{}
                case "6":{}
                case "7":{}
                case "8":{}
                case "9":{}
                case "10":{}
                case "11":{}
                case "12":{}
                case "0":{}
                default:{}
            }



        }


    }//login


    //function for the "1" option in menu
    public void createnewweeklyshift(int weeknum,int year){
        //checks if allready exist, if not will create new one
        for(int i=0; i<weeklyShiftList.size();i++){
            if((weeklyShiftList.get(i).getYear()) == year &&
                    weeklyShiftList.get(i).getWeekNUm()==weeknum) {
                //allready exist:
                return;
            }
        }
        weeklyShiftList.add(new WeeklyShift(weeknum,year));

    }

    //function for the "2"
    public void addtoexistingweeklyshift(){
        //todo: needs more function for later.
    }

    //function for the "3"
    public void switchemployeeinashift(){
        //todo: needs more function for later.
    }

    //function for the "4"
    public void fireemployee(int id){
        for(int i=0; i<allworkerslist.size();i++){
            if(allworkerslist.get(i).getId()==id){
                allworkerslist.remove(i);
                break;
            }
        }
    }


    //function for the "5"
    public void addemployee(Workers w){
        allworkerslist.add(w);
    }

    //function for the "6"
    public void addwagetoemployee(int id,int addedwage){
        for(int i=0; i<allworkerslist.size();i++){
            if(allworkerslist.get(i).getId()==id){
                allworkerslist.get(i).setWage(addedwage + allworkerslist.get(i).getWage());

            }
        }
    }

    //function for the "7"
    public void getwageforemployee(int id, int fromweek, int toweek){
        Workers w;
        for(int i=0; i<allworkerslist.size();i++) {
            if (allworkerslist.get(i).getId() == id) {
                w = allworkerslist.get(i);
                break;
            }
        }
        //now w is the employee i need
        //todo: complete - go through all the weeklys and find the ones he worked in.
        //todo: function needed :  howmuchhoursdidthisworkerdid(int id) -> forweeklyshift
    }

    //function for the "8"
    public void changeemployeecontract(int id,String contract){
        for(int i=0; i<allworkerslist.size();i++) {
            if (allworkerslist.get(i).getId() == id) {
                allworkerslist.get(i).setContract(contract);
                break;
            }
        }
    }

    //function for the "9"
    public void updateemployeesbank(int id,int newbanknum) {
        for (int i = 0; i < allworkerslist.size(); i++) {
            if (allworkerslist.get(i).getId() == id) {
                allworkerslist.get(i).setBankNum(newbanknum);
                break;
            }
        }
    }


    //function for the "10"
    public void addavilableforemployee(int id, WindowType wt){
        for(int i=0; i<allworkerslist.size();i++) {
            if (allworkerslist.get(i).getId() == id) {
                allworkerslist.get(i).addwindow(wt);
                break;
            }
        }
    }
    public void removeavalbleforemployee(int id,WindowType wt){
        for(int i=0; i<allworkerslist.size();i++) {
            if (allworkerslist.get(i).getId() == id) {
                allworkerslist.get(i).removewindow(wt);
                break;
            }
        }
    }


    //function for the "11"
    public void addnewproforemployee(int id, String pro){

        //todo: complete accordingly
    }

    //function for the "12"
    public void removeprofforemployee(int id,String pro){}











}//class
