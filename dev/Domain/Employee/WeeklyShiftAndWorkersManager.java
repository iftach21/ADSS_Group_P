package Domain.Employee;

import DataAccesObjects.Connection;
import DataAccesObjects.Employee.ShiftWorkerDAO;
import DataAccesObjects.Employee.WeeklyShiftDAO;
import DataAccesObjects.Employee.WorkersDAO;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.WindowTypeCreater;
import Domain.Enums.weightType;

import java.sql.SQLException;
import java.util.*;

public class WeeklyShiftAndWorkersManager {
    private static WeeklyShiftAndWorkersManager Instance = null;
    private final WorkersDAO workersDAO;
    private final WeeklyShiftDAO weeklyShiftDAO;
    private final ShiftWorkerDAO shiftWorkerDAO;



    private WeeklyShiftAndWorkersManager() throws SQLException {
        workersDAO = WorkersDAO.getInstance();
        weeklyShiftDAO = WeeklyShiftDAO.getInstance();
        shiftWorkerDAO = ShiftWorkerDAO.getInstance();
    }

    public static WeeklyShiftAndWorkersManager getInstance() throws SQLException {
        if(Instance==null){Instance = new WeeklyShiftAndWorkersManager();}
        return Instance;
    }
    //function for the "1" option in menu
    public void createnewweeklyshift(int weeknum,int year,int supernum) throws SQLException {
        weeklyShiftDAO.add(new WeeklyShift(weeknum,year,supernum));
    }

    //function for the "2"
    public void addtoexistingweeklyshift(int weeknum, int year, int supernum, WindowType wt, int id , int profindx) throws SQLException {
        if(profindx==7){
            WeeklyShift weeklyShift = this.getweeklyshift(weeknum,year,0);
            weeklyShift.addDriverToShift((Driver) this.getworkerbyid(id),wt);
            this.weeklyShiftDAO.update(weeklyShift);
            return;
        }
        WeeklyShift weeklyShift = this.getweeklyshift(weeknum,year,supernum);
        weeklyShift.addworkertoshift(this.getworkerbyid(id),wt,profindx);
        this.weeklyShiftDAO.update(weeklyShift);

    }

    //function for the "3"
    public void switchemployeeinashift(int weeknum,int yearnum,int supernum, WindowType wt,int prof, int id1, int id2) throws SQLException {
        WeeklyShift weeklyShift = this.getweeklyshift(weeknum,yearnum,supernum);
        weeklyShift.changeWorker(this.getworkerbyid(id1),this.getworkerbyid(id2),prof,wt);
        this.weeklyShiftDAO.update(weeklyShift);
    }
    //function for the "4"
    public void fireemployee(int id){
        this.workersDAO.delete(id);
    }

    //function for the "5"
    public void addemployee(int id,String name,String contract,String start_date,int wage,int phoneNUM, String personalinfo,int bankNum){
        Workers w = new Workers(id,name,contract,start_date,wage,phoneNUM,personalinfo,bankNum);
        workersDAO.add(w);
    }
    //function for the "6"
    public void addwagetoemployee(int id,int addedwage){
        Workers worker = this.getworkerbyid(id);
        worker.setWage(addedwage + this.getworkerbyid(id).getWage());
        this.workersDAO.update(worker);
    }
    //function for the "7"
    public int getwageforemployee(int id, int week,int year){
        Workers worker = this.getworkerbyid(id);
        int sumToReturn = 0;
        List <WeeklyShift> weeklyShiftList = this.weeklyShiftDAO.getWeeklyShiftList(week,year);
        for(int i=0; i<weeklyShiftList.size();i++) {
            if(weeklyShiftList.get(i).getWeekNUm()==week && weeklyShiftList.get(i).getYear()==year){
                int hm =  weeklyShiftList.get(i).howMuchShiftWorkerDid(id);
                sumToReturn+= hm * worker.getWage();
                }
            }
        return sumToReturn;
        }

    //function for the "8"
    public void changeemployeecontract(int id,String contract){
        Workers worker = this.getworkerbyid(id);
        worker.setContract(contract);
        this.workersDAO.update(worker);
    }

    //function for the "9"
    public void updateemployeesbank(int id,int newbanknum) {
        Workers worker = this.getworkerbyid(id);
        worker.setBankNum(newbanknum);
        this.workersDAO.update(worker);
    }

    //function for the "10"
    public void addavilableforemployee(int id, WindowType wt){
        Workers worker = this.getworkerbyid(id);
        worker.addwindow(wt);
        this.workersDAO.update(worker);
    }

    //function for the "11"
    public void addnewproforemployee(int id, int indx) {
        Workers worker = this.getworkerbyid(id);
        if(worker.amIDriver()){return;}

        //0=manager
        //1=cashier
        //2=stoke
        //3=security
        //4=cleaning
        //5=shelfstoking
        //6= generalworker

        worker.addprof(indx);
        this.workersDAO.update(worker);
    }
    //function for the "12"
    public void removeprofforemployee(int id,int pros){
        Workers worker = this.getworkerbyid(id);
        if(worker.amIDriver()){return;}
        worker.removePro(pros);
        this.workersDAO.update(worker);
    }

    //function for the "13"
    public void removeavalbleforemployee(int id,WindowType wt){
        Workers worker = this.getworkerbyid(id);
        worker.removewindow(wt);
        this.workersDAO.update(worker);
    }
    //for function "14"
    public void printweeklyshift(int weeknum,int year,int supernum) throws SQLException {
        System.out.println(this.getweeklyshift(weeknum,year,supernum).printSpesific());
    }
    public Workers getworkerbyid(int id) {
            return this.workersDAO.get(id);
    }
    public WeeklyShift getweeklyshift(int week,int year,int supernum) throws SQLException {
        return this.weeklyShiftDAO.get(week,year,supernum);
    }
    public void printall(){
        List<Workers> list = this.workersDAO.getAllworkerslist();
        for(int i=0;i<list.size();i++){
            list.get(i).print();
        }
    }

    public void printAllWorkersWhoCanWork(int prof,int daynum, String don) {

        WindowTypeCreater wc = new WindowTypeCreater();
        List<Workers> allworkerslist = this.workersDAO.getAllworkerslist();
        if (prof != 7) {
            for (int i = 0; i < allworkerslist.size(); i++) {
                if (allworkerslist.get(i).caniworkatprofindx(prof) && allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum, don))) {
                    allworkerslist.get(i).print();
                }
            }
        } else {
            for (int i = 0; i < allworkerslist.size(); i++) {
                if (allworkerslist.get(i).amIDriver() && allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum, don))) {
                    allworkerslist.get(i).print();
                }
            }
        }
    }
    public ArrayList<Integer> AllWorkersWhoCanWorkList(int prof,int daynum, String don) {

        WindowTypeCreater wc = new WindowTypeCreater();
        List<Workers> allworkerslist = this.workersDAO.getAllworkerslist();
        ArrayList<Integer> allworkers= new ArrayList<>();
        if (prof != 7) {
            for (int i = 0; i < allworkerslist.size(); i++) {
                if (allworkerslist.get(i).caniworkatprofindx(prof) && allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum, don))) {
                    allworkers.add(allworkerslist.get(i).getId());
                }
            }
        } else {
            for (int i = 0; i < allworkerslist.size(); i++) {
                if (allworkerslist.get(i).amIDriver() && allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum, don))) {

                    allworkers.add(allworkerslist.get(i).getId());
                }
            }
        }
        return allworkers;
    }
    public String AllWorkersWhoCanWorkString(int prof,int daynum, String don) {

        WindowTypeCreater wc = new WindowTypeCreater();
        List<Workers> allworkerslist = this.workersDAO.getAllworkerslist();

        StringBuilder StringWorkers = new StringBuilder();

        if (prof != 7) {
            for (int i = 0; i < allworkerslist.size(); i++) {
                if (allworkerslist.get(i).caniworkatprofindx(prof) && allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum, don))) {
                    StringWorkers.append(allworkerslist.get(i).getName()+" "+allworkerslist.get(i).getId()+" |");
                }
            }
        } else {
            for (int i = 0; i < allworkerslist.size(); i++) {
                if (allworkerslist.get(i).amIDriver() && allworkerslist.get(i).canIworkat(wc.getwidowtype(daynum, don))) {

                    StringWorkers.append(allworkerslist.get(i).getName()+" "+allworkerslist.get(i).getId()+" |");
                }
            }
        }
        return StringWorkers.toString();
    }
    public String AllWorkersWhoCanWorkWeekString(int prof) {

        WindowTypeCreater wc = new WindowTypeCreater();
        List<Workers> allworkerslist = this.workersDAO.getAllworkerslist();

        StringBuilder StringWorkers = new StringBuilder();

        if (prof != 7) {
            for (int i = 0; i < allworkerslist.size(); i++) {
                for (int daynum=0; daynum<=7;daynum++){
                    if (allworkerslist.get(i).caniworkatprofindx(prof)) {
                        StringWorkers.append(allworkerslist.get(i).getName()+" "+allworkerslist.get(i).getId()+" in day num:"+daynum +" |");
                    }
                }
            }
        } else {
            for (int i = 0; i < allworkerslist.size(); i++) {
                for (int daynum=0; daynum<=7;daynum++){
                if (allworkerslist.get(i).caniworkatprofindx(prof)) {
                    StringWorkers.append(allworkerslist.get(i).getName()+" "+allworkerslist.get(i).getId()+" in day num:"+daynum +" |");
                }
                }
            }
        }
        return StringWorkers.toString();
    }
    public void addreqtoweeklyshift(int weeknum, int year,int supernum, int daynum, String don,int prof,int hm) throws SQLException {
        WindowTypeCreater wc = new WindowTypeCreater();
        WeeklyShift weeklyShift;

        if(prof != 7) {
            weeklyShift = this.getweeklyshift(weeknum, year, supernum);
        }
        else{
            weeklyShift = this.getweeklyshift(weeknum, year, 0);
        }

        weeklyShift.addreqtoshift(wc.getwidowtype(daynum,don),prof,hm);
        this.weeklyShiftDAO.update(weeklyShift);
    }

    public void settimeforweeklyshift(int weeknum, int year,int supernum, int daynum,String don, String startime) throws SQLException {
        WindowTypeCreater wc = new WindowTypeCreater();
        WeeklyShift weeklyShift = this.getweeklyshift(weeknum,year,supernum);
        weeklyShift.setTimeForShift(startime,wc.getwidowtype(daynum,don));
        this.weeklyShiftDAO.update(weeklyShift);
    }
    public void printweeklyreq(int weeknum, int year,int supernum) throws SQLException {
        getweeklyshift(weeknum,year,supernum).printweeklyreq();

    }

    //while giving the right info giving back if there is a stock viable.
    public List<WindowType> doIHaveStokeForTheShipment(int weeknum, int year,int supernum) throws SQLException {
        List<WindowType> ans = new ArrayList<>();
        WeeklyShift weeklyShift = this.weeklyShiftDAO.get(weeknum,year,supernum);
        for(WindowType windowType: WindowTypeCreater.getAllWindowTypes()) {
            if (weeklyShift.doIhaveAStoke(windowType)) {
                ans.add(windowType);
            }
        }

        return ans;
    }

    public List<Driver> giveMeViableDrivers(int weekNum, int yearNum, WindowType wt) throws SQLException {
        return this.weeklyShiftDAO.get(weekNum,yearNum,0).giveAllDrivers(wt);
    }

    //adding a new driver:
    public void addNewDriver(int id, String name, String contract, String start_date, int wage, int phoneNUM, String personalinfo, int bankNum, TempLevel tl, weightType wt){
        Driver new_w = new Driver(id, name, contract, start_date, wage, phoneNUM, personalinfo, bankNum, tl, wt);
        this.workersDAO.add(new_w);
    }

    //setting the personal info
    public void setPersonalinfo(int id,String setPersonalinfo ){
        Workers workers = getworkerbyid(id);
        workers.setPersonalinfo(setPersonalinfo);
        this.workersDAO.update(workers);
    }

    public void checkIfShiftsFor24HourIsOkay(int weeknum,int day,int year){
        List<WeeklyShift> weeklyShiftlist = this.weeklyShiftDAO.getWeeklyShiftList(weeknum,year);
        for(WeeklyShift ws : weeklyShiftlist){
           ws.checkIfMissingSomeThing(day + 1);
        }
    }

    public void deleteAllRows() throws SQLException {
        Connection.DeleteRows();
    }

    public Map<String, Integer> getProfessionCounts(int day, String shiftType, int WeekNum, int yearNum, int superNum, String[] professions) throws SQLException {

        WeeklyShift ws = this.weeklyShiftDAO.get(WeekNum,yearNum,superNum);
        Map<String, Integer> professionCounts = new HashMap<>();
        int i=0;
        for (String profession : professions) {
            if(Objects.equals(shiftType, "Day Shift")) {
                int count = ws.getDayShift()[day].getShiftRequirement().getreqbyprof(i++);
                professionCounts.put(profession, count);
            }
            else{
                int count = ws.getNightShift()[day].getShiftRequirement().getreqbyprof(i++);
                professionCounts.put(profession, count);
            }
        }
        return professionCounts;
    }
    public  Map<String, ArrayList<String>> getWorkersByProDay(int day, String shiftType, int WeekNum, int yearNum, int superNum, String[] professions) throws SQLException {

        WeeklyShift ws = this.weeklyShiftDAO.get(WeekNum,yearNum,superNum);
        Map<String, ArrayList<String>> WorkerProMap = new HashMap<>();
        int i=0;
        for (String profession : professions) {
            WorkerProMap.put(profession,  ws.getDayShift()[day].workerByPro(i));
            i++;
        }
        return WorkerProMap;
    }
    public  Map<String, ArrayList<String>> getWorkersByProNight(int day, String shiftType, int WeekNum, int yearNum, int superNum, String[] professions) throws SQLException {

        WeeklyShift ws = this.weeklyShiftDAO.get(WeekNum,yearNum,superNum);
        Map<String, ArrayList<String>> WorkerProMap = new HashMap<>();
        int i=0;
        for (String profession : professions) {

            WorkerProMap.put(profession,  ws.getNightShift()[day].workerByPro(i));
            i++;
        }
        return WorkerProMap;
    }


    public void addWorkerToShift(int weeknum, int year,int supernum, int daynum, String don,int prof,int workerID) throws SQLException {
        WindowTypeCreater wc = new WindowTypeCreater();
        WeeklyShift weeklyShift;
        Workers worker= this.getworkerbyid(workerID);
        if(prof != 7) {
            weeklyShift = this.getweeklyshift(weeknum, year, supernum);
        }
        else{
            weeklyShift = this.getweeklyshift(weeknum, year, 0);
        }
        weeklyShift.addworkertoshift(worker,wc.getwidowtype(daynum,don),prof);
        this.weeklyShiftDAO.update(weeklyShift);
    }
    public String getAllworkersString(){
        StringBuilder StringWorkers = new StringBuilder();
         for(Workers worker: this.workersDAO.getAllworkerslist()){
             StringWorkers.append(worker.getName()+" "+worker.getId()+ "\n");
         }
         return StringWorkers.toString();
    }

    public void writeToEventOfShift(int weeknum, int year,int supernum, int daynum, String don,String messege) throws SQLException {
        WeeklyShift weeklyShift;
        WindowTypeCreater wc = new WindowTypeCreater();
        weeklyShift = this.getweeklyshift(weeknum,year,supernum);
        weeklyShift.writeEventToShift(wc.getwidowtype(daynum,don),messege);
    }

    public String getEventOfShift(int weeknum, int year,int supernum, int daynum, String don) throws SQLException {
        WeeklyShift weeklyShift;
        WindowTypeCreater wc = new WindowTypeCreater();
        weeklyShift = this.getweeklyshift(weeknum,year,supernum);
        return weeklyShift.getEventToShift(wc.getwidowtype(daynum,don));
    }

    public String getAllWorkersThatWorkedInSpecificWeek(int weeknum, int year,int supernum) throws SQLException {
        WeeklyShift weeklyShift = this.getweeklyshift(weeknum,year,supernum);
        ArrayList<Workers> allWorkers = new ArrayList<>();
        allWorkers = weeklyShift.getAllWorkers();
        ArrayList<Workers> allWorkersNoDup = new ArrayList<>();
        for(Workers worker: allWorkers){
            int flag = 0;
            for(Workers w_d : allWorkersNoDup){
                if(w_d.getId() == worker.getId()){
                    flag =1;
                    break;
                }
            }
            if(flag==0){allWorkersNoDup.add(worker);}
        }


        StringBuilder StringWorkers = new StringBuilder();
        for(Workers worker: allWorkersNoDup){
            StringWorkers.append(worker.getName()+" "+worker.getId()+ "\n");
        }
        return StringWorkers.toString();

    }

    public ArrayList<String> getAllDrivers(int day, int WeekNum, int yearNum, int superNum) throws SQLException {
        WeeklyShift ws = this.weeklyShiftDAO.get(WeekNum,yearNum,superNum);
        return ws.getDayShift()[day].giveDriversString();
    }

    public  ArrayList<String> getDriverByNight(int day, String shiftType, int WeekNum, int yearNum, int superNum, String[] professions) throws SQLException {

        WeeklyShift ws = this.weeklyShiftDAO.get(WeekNum,yearNum,superNum);

        return ws.getNightShift()[day].giveDriversString();
    }
    public  ArrayList<String> getDriverByDay(int day, String shiftType, int WeekNum, int yearNum, int superNum, String[] professions) throws SQLException {

        WeeklyShift ws = this.weeklyShiftDAO.get(WeekNum,yearNum,superNum);

        return ws.getDayShift()[day].giveDriversString();
    }

}

