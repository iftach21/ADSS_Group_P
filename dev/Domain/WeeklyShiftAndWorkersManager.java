package Domain;

import java.util.ArrayList;
import java.util.List;

public class WeeklyShiftAndWorkersManager {
    private static WeeklyShiftAndWorkersManager Instance = null;
    private List<WeeklyShift> weeklyShiftList; //holds all the weeklyshifts
    private List<Workers> allworkerslist; //holds all the workers available
    private WeeklyShiftAndWorkersManager(){
        this.weeklyShiftList = new ArrayList<WeeklyShift>();
        this.allworkerslist = new ArrayList<Workers>();
    }
    public static WeeklyShiftAndWorkersManager getInstance(){
        if(Instance==null){Instance = new WeeklyShiftAndWorkersManager();}
        return Instance;
    }
    //function for the "1" option in menu
    public void createnewweeklyshift(int weeknum,int year,int supernum){
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
    public void printall(){
        for(int i =0;i<this.allworkerslist.size();i++){
            this.allworkerslist.get(i).print();
        }
    }

    public void printAllWorkersWhoCanWork(int prof,int daynum, String don){
        WindowTypeCreater wc = new WindowTypeCreater();

        for(int i=0;i<this.allworkerslist.size();i++){
            if(this.allworkerslist.get(i).caniworkatprofindx(prof) && this.allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum,don))){
                this.allworkerslist.get(i).print();
            }
        }
    }
    //checking if allready has shift at other super:
    public boolean IsWorkingAllready(int id,int weeknum, int year,int daynum, String don){
        WindowTypeCreater wc = new WindowTypeCreater();

        for(int i =0;i<this.weeklyShiftList.size();i++){
            WeeklyShift w = this.weeklyShiftList.get(i);
            if(w.getYear()==year && w.getWeekNUm()==weeknum){
                if(w.checkifworkallready(id,wc.getwidowtype(daynum,don) )){
                    return true;
                }
            }
        }
        return false;
    }

    public void addreqtoweeklyshift(int weeknum, int year,int supernum, int daynum, String don,int prof,int hm){
        WindowTypeCreater wc = new WindowTypeCreater();
        getweeklyshift(weeknum,year,supernum).addreqtoshift(wc.getwidowtype(daynum,don),prof,hm);
    }

    public void settimeforweeklyshift(int weeknum, int year,int supernum, int daynum,String don, String startime){
        WindowTypeCreater wc = new WindowTypeCreater();
        getweeklyshift(weeknum,year,supernum).setTimeForShift(startime,wc.getwidowtype(daynum,don));
    }
    public void printweeklyreq(int weeknum, int year,int supernum){
        getweeklyshift(weeknum,year,supernum).printweeklyreq();

    }



}
