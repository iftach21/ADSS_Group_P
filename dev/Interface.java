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
            System.out.println("7: get weekly wage for employee");
            System.out.println("8: change employees contract");
            System.out.println("9: update employees bank number");
            System.out.println("10: add available for employee");
            System.out.println("11: add new jobs for employee");
            System.out.println("12: remove jobs for employee");
            System.out.println("13: remove available for employee");

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
                    break;
                }
                //-----------------------
                //add to existing weekly shift
                //-----------------------
                case "2":{
                    //todo: complete later
                    break;
                }
                //-----------------------
                //switch employees in a shift
                //-----------------------
                case "3":{
                    //todo: complete later
                    break;
                }

                //-----------------------
                //fire employee
                //-----------------------
                case "4":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    this.fireemployee(idans);
                    break;
                }

                //-----------------------
                //add new employee
                //-----------------------
                case "5":{
                    //Workers(int id, String name, String contract,
                    // String start_date, int wage, int phoneNUM, String personalinfo, int bankNum)

                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input

                    System.out.println("please enter name for the employee");
                    String name = myObj.nextLine();  // Read user input

                    System.out.println("please enter contract for the employee");
                    String contract = myObj.nextLine();  // Read user input

                    System.out.println("please enter start_date for the employee");
                    String start_date = myObj.nextLine();  // Read user input

                    System.out.println("please enter wage for the employee");
                    int wage = myObj.nextInt();  // Read user input

                    System.out.println("please enter phoneNUM for the employee");
                    int phoneNUM = myObj.nextInt();  // Read user input

                    System.out.println("please enter personalinfo for the employee");
                    String personalinfo = myObj.nextLine();  // Read user input

                    System.out.println("please enter bankNum for the employee");
                    int bankNum = myObj.nextInt();  // Read user input

                    Workers w = new Workers(id,name,contract,start_date,wage,phoneNUM,personalinfo,bankNum);

                    this.addemployee(w);
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
                    this.addwagetoemployee(idans,wageadd);
                    break;
                }


                //-----------------------
                //get monthly wage for employee
                //-----------------------
                case "7":{
                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input
                    System.out.println("what week num do you need?");
                    int weeknum = myObj.nextInt();  // Read user input
                    int answage = this.getwageforemployee(id,weeknum);
                    System.out.println(answage);
                    break;
                }


                //-----------------------
                //change employees contract
                //-----------------------
                case "8":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    System.out.println("please enter new contract");
                    String newcontract = myObj.nextLine();  // Read user input
                    this.changeemployeecontract(idans,newcontract);
                    break;
                }


                //-----------------------
                //update employees bank number
                //-----------------------
                case "9":{
                    System.out.println("please enter id for the employee");
                    int idans = myObj.nextInt();  // Read user input
                    System.out.println("please enter new bank num");
                    int banknum = myObj.nextInt();  // Read user input
                    this.updateemployeesbank(idans,banknum);
                    break;
                }

                //-----------------------
                //update available for employee
                //-----------------------
                case "10":{
                    System.out.println("please enter id for the employee");
                    int id = myObj.nextInt();  // Read user input
                    System.out.println("please enter day num");
                    int daynum = myObj.nextInt();  // Read user input
                    System.out.println("please enter day/night shift");
                    String nord = myObj.nextLine();  // Read user input
                    WindowTypeCreater wdc = new WindowTypeCreater();

                    this.addavilableforemployee(id,wdc.getwidowtype(daynum,nord));
                    break;

                }

                //-----------------------
                //add new jobs for employee
                //-----------------------
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
                    //0=manager
                    //1=cashier
                    //2=stoke
                    //3=security
                    //4=cleaning
                    //5=shelfstoking
                    //6= generalworker

                    this.addnewproforemployee(id,prof);

                    break;
                }


                //-----------------------
                //remove jobs for employee
                //-----------------------
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

                    this.removeprofforemployee(id,prof);

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
                    System.out.println("please enter day/night shift");
                    String nord = myObj.nextLine();  // Read user input
                    WindowTypeCreater wdc = new WindowTypeCreater();

                    this.removeavalbleforemployee(id,wdc.getwidowtype(daynum,nord));
                    break;
                }

                //exiting
                case "0":{ exit = false;break;}
                default:{ System.out.println("please enter valid input");}
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
    public int getwageforemployee(int id, int week){

        for(int i=0; i<weeklyShiftList.size();i++) {
            if(weeklyShiftList.get(i).getWeekNUm()==week){
               int hm =  weeklyShiftList.get(i).howMuchShiftWorkerDid(id);
                for(int j=0; i<allworkerslist.size();j++) {
                    if (allworkerslist.get(j).getId() == id) {
                        return hm * allworkerslist.get(j).getWage();
                    }
                }
            }
        }


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



    //function for the "11"
    public void addnewproforemployee(int id, int indx) {

        //0=manager
        //1=cashier
        //2=stoke
        //3=security
        //4=cleaning
        //5=shelfstoking
        //6= generalworker

        for (int i = 0; i < allworkerslist.size(); i++) {
            if (allworkerslist.get(i).getId() == id) {
                allworkerslist.get(i).addprof(indx);
                break;
            }
        }
    }

    //function for the "12"
    public void removeprofforemployee(int id,int pros){
        for (int i = 0; i < allworkerslist.size(); i++) {
            if (allworkerslist.get(i).getId() == id) {
                allworkerslist.get(i).removePro(pros);
                break;
            }
        }
    }

    //function for the "13"
    public void removeavalbleforemployee(int id,WindowType wt){
        for(int i=0; i<allworkerslist.size();i++) {
            if (allworkerslist.get(i).getId() == id) {
                allworkerslist.get(i).removewindow(wt);
                break;
            }
        }
    }











}//class
