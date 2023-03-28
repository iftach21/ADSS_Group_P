import java.util.Iterator;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
public class Interface {
    //TODO need to make sure we have shiftmamanger
    //todo: change personal info
    private static final String passward = "12345"; // the password for the system
    private static final String username = "theboss"; // the username for the system

    private List<WeeklyShift> weeklyShiftList; //holds all the weeklyshifts
    private List<Workers> allworkerslist; //holds all the workers available

    public Interface() {
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
            System.out.println("14: show the weekly shift");

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
                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    this.createnewweeklyshift(weeknum,yearans,supernum);
                    break;
                }
                //-----------------------
                //add to existing weekly shift
                //-----------------------
                case "2":{
                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("please enter day number in the week");
                    int daynum = myObj.nextInt();  // Read user input



                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    System.out.println("please enter night or day");
                    String don = myObj.nextLine();  // Read user input


                    System.out.println("please enter int for the persons prof");
                    System.out.println("0=manager");
                    System.out.println("1=cashier");
                    System.out.println("2=stoke");
                    System.out.println("3=security");
                    System.out.println("4=cleaning");
                    System.out.println("5=shelf-stoking");
                    System.out.println("6= general-worker");
                    int prof = myObj.nextInt();  // Read user input


                    //shows the user all of the people whom can work
                    WindowTypeCreater wc = new WindowTypeCreater();
                    for(int i=0;i<this.allworkerslist.size();i++){
                        if(this.allworkerslist.get(i).caniworkatprofindx(prof) && this.allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum,don))){
                            this.allworkerslist.get(i).print();
                        }
                    }
                    System.out.println("please enter id");
                    int id = myObj.nextInt();  // Read user input

                    //checking if allready has shift at other super:
                    for(int i =0;i<this.weeklyShiftList.size();i++){
                        WeeklyShift w = this.weeklyShiftList.get(i);
                        if(w.getYear()==year && w.getWeekNUm()==weeknum){
                            if(w.checkifworkallready(id,wc.getwidowtype(daynum,don) )){
                                System.out.println("the employee cant work at this time, pick some one else plz");
                                return;
                            }
                        }
                    }

                    this.addtoexistingweeklyshift(weeknum,year,supernum,wc.getwidowtype(daynum,don),this.getworkerbyid(id),prof);

                    break;
                }


                //-----------------------
                //switch employees in a shift
                //-----------------------
                case "3":{
                    System.out.println("please enter year");
                    int year = myObj.nextInt();  // Read user input

                    System.out.println("please enter week number");
                    int weeknum = myObj.nextInt();  // Read user input

                    System.out.println("please enter super number");
                    int supernum = myObj.nextInt();  // Read user input

                    System.out.println("please enter day number in the week");
                    int daynum = myObj.nextInt();  // Read user input

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
                    int prof = myObj.nextInt();  // Read user input

                    System.out.println("please enter id for the employee you want to remove");
                    int id1 = myObj.nextInt();  // Read user input

                    System.out.println("please enter id for the employee you want to put in");
                    int id2 = myObj.nextInt();  // Read user input

                    this.switchemployeeinashift(weeknum,year,supernum,wt.getwidowtype(daynum,don),prof,id1,id2);
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

                    System.out.println("what year num do you need?");
                    int year = myObj.nextInt();  // Read user input

                    int answage = this.getwageforemployee(id,weeknum,year);
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

                    this.printweeklyshift(weeknum,year,supernum);
                }

                //exiting
                case "0":{ exit = false;break;}
                default:{ System.out.println("please enter valid input");}
            }

        }

    }//login


    //function for the "1" option in menu
    public void createnewweeklyshift(int weeknum,int year,int supernum){
        //checks if allready exist, if not will create new one
        for(int i=0; i<weeklyShiftList.size();i++){
            if((weeklyShiftList.get(i).getYear()) == year &&
                    weeklyShiftList.get(i).getWeekNUm()==weeknum) {
                //allready exist:
                return;
            }
        }
        weeklyShiftList.add(new WeeklyShift(weeknum,year,supernum));

    }

    //function for the "2"
    public void addtoexistingweeklyshift(int weeknum,int year,int supernum, WindowType wt,Workers w,int profindx){
            this.getweeklyshift(weeknum,year,supernum).addworkertoshift(w,wt,profindx);
            }


    //function for the "3"
    public void switchemployeeinashift(int weeknum,int yearnum,int supernum, WindowType wt,int prof, int id1, int id2){
        this.getweeklyshift(weeknum,yearnum,supernum).changeWorker(this.getworkerbyid(id1),this.getworkerbyid(id2),prof,wt);
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
        this.getworkerbyid(id).setWage(addedwage + this.getworkerbyid(id).getWage());
        }

    //function for the "7"
    public int getwageforemployee(int id, int week,int year){

        for(int i=0; i<weeklyShiftList.size();i++) {
            if(weeklyShiftList.get(i).getWeekNUm()==week && weeklyShiftList.get(i).getYear()==year){
               int hm =  weeklyShiftList.get(i).howMuchShiftWorkerDid(id);
                for(int j=0; i<allworkerslist.size();j++) {
                    if (allworkerslist.get(j).getId() == id) {
                        return hm * allworkerslist.get(j).getWage();
                    }
                }
            }
        }

        return 0;
    }

    //function for the "8"
    public void changeemployeecontract(int id,String contract){
        this.getworkerbyid(id).setContract(contract);
        }

    //function for the "9"
    public void updateemployeesbank(int id,int newbanknum) {
        this.getworkerbyid(id).setBankNum(newbanknum);
        }


    //function for the "10"
    public void addavilableforemployee(int id, WindowType wt){
        this.getworkerbyid(id).addwindow(wt);
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

        this.getworkerbyid(id).addprof(indx);
    }

    //function for the "12"
    public void removeprofforemployee(int id,int pros){
        this.getworkerbyid(id).removePro(pros);
    }

    //function for the "13"
    public void removeavalbleforemployee(int id,WindowType wt){
            this.getworkerbyid(id).removewindow(wt);
            }


    //for function "14"
    public void printweeklyshift(int weeknum,int year,int supernum){
        System.out.println(this.getweeklyshift(weeknum,year,supernum).printSpesific());
    }

    public Workers getworkerbyid(int id) {
        for (int i = 0; i < allworkerslist.size(); i++) {
            if (allworkerslist.get(i).getId() == id) {
                return allworkerslist.get(i);
            }
        }
        return null;
    }

    public WeeklyShift getweeklyshift(int week,int year,int supernum){
        for(int i=0; i<weeklyShiftList.size();i++) {
            if(weeklyShiftList.get(i).getWeekNUm()==week && weeklyShiftList.get(i).getYear()==year &&supernum == weeklyShiftList.get(i).getSupernum()) {
                return weeklyShiftList.get(i);}

        }
        return null;
    }

}//class
