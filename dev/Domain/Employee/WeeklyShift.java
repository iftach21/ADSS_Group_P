package Domain.Employee;

import Domain.Enums.WindowType;
import Domain.Enums.WindowTypeCreater;

import java.util.ArrayList;
import java.util.List;

public class WeeklyShift {
    private  int weekNUm;
    private int year;
    private int supernum;

    private Shift[] dayShift;
    private Shift[] nightShift;

    public int getWeekNUm() {
        return weekNUm;
    }

    public int getYear() {
        return year;
    }

    public Shift[] getDayShift() {
        return dayShift;
    }

    public Shift[] getNightShift() {
        return nightShift;
    }

    public void setDayShift(Shift[] dayShift) {
        this.dayShift = dayShift;
    }

    public void setNightShift(Shift[] nightShift) {
        this.nightShift = nightShift;
    }

    /**
     * Creating a weekly shift
     */
    public WeeklyShift(int weekNUm, int year,int supernum) {
        this.weekNUm = weekNUm;
        this.year = year;
        this.supernum = supernum;
        this.dayShift = new Shift[7];

        this.nightShift = new Shift[7];
        for(int i=0;i< 7;i++){
            dayShift[i] = new Shift();
            nightShift[i] = new Shift();
        }

    }
    public int getSupernum(){return this.supernum;}

    /**
     * Replacing an employee on shift with another employee
     */
    public  boolean changeWorker(Workers worker1,Workers worker2,int profindx, WindowType shiftTime){
        if(shiftTime.ordinal()<7){
            if(!this.dayShift[shiftTime.ordinal()].checkIfWorkerInShift(worker1.getId())){
                return false;}
            else {
                this.dayShift[shiftTime.ordinal()].removalWorker(worker1);
                this.dayShift[shiftTime.ordinal()].insertToShift(worker2,profindx);
            }
        }
        else {
            if(!this.nightShift[shiftTime.ordinal()-7].checkIfWorkerInShift(worker1.getId())){
                return false;}
            else {
                this.nightShift[shiftTime.ordinal()-7].removalWorker(worker1);
                this.nightShift[shiftTime.ordinal()-7].insertToShift(worker2,profindx);
            }
        }

        return true;
    }
    /**
     * Returns a string of all employees when employed
     */
    public  StringBuilder printSpesific(){
        StringBuilder pWeelShift= new StringBuilder();
        for (int i = 0; i < 7; i++){
            pWeelShift.append(" Day ").append(i+1).append(": ").append(dayShift[i].printShift()).append(",");
            pWeelShift.append(" Night ").append(i+1).append(": ").append(nightShift[i].printShift());
        }
        return pWeelShift;
    }

    /**
     * Returns the amount of shifts an employee has do
     */
    public int howMuchShiftWorkerDid(int id){
        //given id tells hm shift did the worker did
        int countShift=0;
        for(Shift dShift:this.dayShift){
            if(dShift.checkIfWorkerInShift(id)){
                countShift++;
            }
        }
        for(Shift nShift:this.nightShift) {
            if (nShift.checkIfWorkerInShift(id)) {
                countShift++;
            }
        }
        return countShift;

    }
    /**
     * Add an employee to a shift
     */
    public void addworkertoshift(Workers newWorker, WindowType shiftTime, int profindx) {
        if(shiftTime.ordinal()<7){
            this.dayShift[shiftTime.ordinal()].insertToShift(newWorker,profindx);
        }
        else {
            this.nightShift[shiftTime.ordinal()-7].insertToShift(newWorker,profindx);
        }
    }
    public void addreqtoshift(WindowType shiftTime, int profindx,int hm){
        if(shiftTime.ordinal()<7){
            this.dayShift[shiftTime.ordinal()].addreq(profindx,hm);
        }
        else {
            this.nightShift[shiftTime.ordinal()-7].addreq(profindx,hm);
        }
    }


    /**
     * Checks if a worker is on shift
     */
    public boolean checkifworkallready(int id, WindowType shiftTime){
        if(shiftTime.ordinal()<7){
           return this.dayShift[shiftTime.ordinal()].checkIfWorkerInShift(id);
        }
        else {
            return this.nightShift[shiftTime.ordinal()-7].checkIfWorkerInShift(id);
        }
    }
    public void setTimeForShift(String newtime, WindowType st){
        if(st.ordinal()<7){
             this.dayShift[st.ordinal()].setStartTime(newtime);
        }
        else {
             this.nightShift[st.ordinal()-7].setStartTime(newtime);
        }
    }

    public void printweeklyreq(){
        for (int i = 0; i < 7; i++){
            System.out.println("day: "+ i);
            dayShift[i].printreq();
            System.out.println("night: "+ i);
            nightShift[i].printreq();
        }
    }
    public boolean doIhaveAStoke(WindowType st){
        if(st.ordinal()<7){
            return this.dayShift[st.ordinal()].doIHaveStoke();
        }
        else {
           return this.nightShift[st.ordinal()-7].doIHaveStoke();
        }
    }

    public List<Driver> giveAllDrivers(WindowType st){
        if(st.ordinal()<7){
            return this.dayShift[st.ordinal()].giveDrivers();
        }
        else {
            return this.nightShift[st.ordinal()-7].giveDrivers();
        }
    }
    public void addDriverToShift(Driver newWorker, WindowType shiftTime) {
        if(shiftTime.ordinal()<7){
            this.dayShift[shiftTime.ordinal()].addDriver(newWorker);
        }
        else {
            this.nightShift[shiftTime.ordinal()-7].addDriver(newWorker);
        }
    }
    public void checkIfMissingSomeThing(int daynum){
        WindowTypeCreater wt = new WindowTypeCreater();
        WindowType shiftTime = wt.getwidowtype(daynum,"day");
        if(shiftTime.ordinal()<7){
            if(this.dayShift[shiftTime.ordinal()].getShiftManager()==null){
                System.out.println("there is no shift manager on week: " + this.weekNUm + " on year " + this.year + " on the day shift of day num" + daynum);
            }
        }
        else {
            if (this.nightShift[shiftTime.ordinal() - 7].getShiftManager() == null) {
                System.out.println("there is no shift manager on week: " + this.weekNUm + " on year " + this.year + " on the day shift of day num" + daynum);
            }
        }
    }

    public void writeEventToShift(WindowType shiftTime,String massage){
        if(shiftTime.ordinal()<7){
            this.dayShift[shiftTime.ordinal()].setLog(this.dayShift[shiftTime.ordinal()].getLog() +"\n" + massage);
        }
        else {
            this.nightShift[shiftTime.ordinal()-7].setLog(this.dayShift[shiftTime.ordinal()].getLog() +"\n" + massage);
        }

    }

    public String getEventToShift(WindowType shiftTime){
        if(shiftTime.ordinal()<7){
            return this.dayShift[shiftTime.ordinal()].getLog();
        }
        else {
            return this.nightShift[shiftTime.ordinal()].getLog();
        }
    }
    public ArrayList<Workers> getAllWorkers(){
        ArrayList<Workers> ans = new ArrayList<Workers>();
        for (int i = 0; i < 7; i++){
            ans.addAll(dayShift[i].getAllWorkers());
            ans.addAll(dayShift[i].getDrivers());
            ans.addAll(nightShift[i].getAllWorkers());
            ans.addAll(nightShift[i].getDrivers());
        }
        return ans;

    }



}//end class