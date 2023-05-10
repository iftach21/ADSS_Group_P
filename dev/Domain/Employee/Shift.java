package Domain.Employee;

import java.util.ArrayList;
import java.util.List;

public class Shift {
    private int ShiftID;
    private String date;
    private int id = -1;



    private List<Driver> drivers;

    private ArrayList<Workers>[] workerInShift;
    private ShiftRequirement shiftRequirement;
    private Workers shiftManager;
    private String log;
    private String startTime;

    //
    // Creating a shift
    //
    public Shift(String date) {
        this.ShiftID=0;
        this.drivers = new ArrayList<>();
        this.date = date;
        this.shiftManager = shiftManager;
        this.log = " ";
        this.workerInShift = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            workerInShift[i] = new ArrayList<Workers>();
        }
        startTime = "8:00";
        shiftRequirement = new ShiftRequirement();
    }

    public Shift() {
        this.ShiftID=0;
        this.drivers = new ArrayList<>();
        this.date = "";
        this.shiftManager = shiftManager;
        this.log = " ";
        this.workerInShift = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            workerInShift[i] = new ArrayList<Workers>();
        }
        startTime = "8:00";
        shiftRequirement = new ShiftRequirement();
    }
    /**
     *Prints the shift
     */
    public StringBuilder printShift() {

        StringBuilder pShift = new StringBuilder();

        int i;
        for (i = 0; i < 7;i++){
            for (int j = 0; j < this.workerInShift[i].size(); j++) {
                pShift.append("name: ").append(this.workerInShift[i].get(j).getName()).append(" id: ").append(this.workerInShift[i].get(j).getId());
            }
        }
        return pShift;
    }

    public void setShiftID(int shiftID) {
        ShiftID = shiftID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void setWorkerInShift(ArrayList<Workers>[] workerInShift) {
        this.workerInShift = workerInShift;
    }

    public void setShiftRequirement(ShiftRequirement shiftRequirement) {
        this.shiftRequirement = shiftRequirement;
    }

    public void setShiftManager(Workers shiftManager) {
        this.shiftManager = shiftManager;
    }

    public void setLog(String log) {
        this.log = log;
    }

    /**
     * insert an employee on shift
     */
    public boolean insertToShift(Workers newWorker, int profindx){
        if (newWorker==null|| checkIfWorkerInShift(newWorker.getId())){
            return false;}
        if (checkIfWorkerInShift(newWorker.getId())){
            System.out.println("he is allready exist");
            return false;
        }
        this.workerInShift[profindx].add(newWorker);
        if(profindx==0){this.shiftManager=newWorker;}
        if(this.shiftManager==null){
            System.out.println("this shift doesnt have shift manager");
        }
        return true;
    }
    /**
     * check If Worker In Domain.Employee.Shift
     */
    public boolean checkIfWorkerInShift(int id){
        for (int i = 0; i < 7;i++){
            for (int j = 0; j < this.workerInShift[i].size(); j++) {
                if( id==this.workerInShift[i].get(j).getId()){return true;}
            }
        }
        return false;
    }
    /**
     * removal Worker from Domain.Employee.Shift
     */
    public boolean removalWorker(Workers currentWorker) {
        if (!checkIfWorkerInShift(currentWorker.getId())) {
            return false;
        }
        for (int i = 0; i < 7;i++){
            for (int j = 0; j < this.workerInShift[i].size(); j++) {
                if( currentWorker.getId()==this.workerInShift[i].get(j).getId()){
                    this.workerInShift[i].remove(j);
                    return true;}
            }
        }
        return true;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public void addreq(int profindx,int req){
        shiftRequirement.setReq(profindx,req);
    }
    public void printreq(){
        System.out.println("Start time for the shift: " + this.startTime);
        shiftRequirement.printreq();
    }
    public boolean doIHaveStoke(){
        return ! workerInShift[2].isEmpty();
    }
    public List<Driver> giveDrivers(){return this.drivers;}

    public void addDriver(Driver driver){this.drivers.add(driver);}

    public String getDate() {
        return date;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public ArrayList<Workers>[] getWorkerInShift() {
        return workerInShift;
    }

    public ShiftRequirement getShiftRequirement() {
        return shiftRequirement;
    }

    public Workers getShiftManager() {
        return shiftManager;
    }
    public int getShiftManagerID() {
        return shiftManager.getId();
    }

    public String getLog() {
        return log;
    }

    public String getStartTime() {
        return startTime;
    }
    public int getShiftID() {
        return ShiftID;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<Workers> getDiffWorkers(Shift shiftToCompare){
        List<Workers> ans = new ArrayList<>();

        List<Workers> inThisWorkers = this.getAllWorkers();
        List<Workers> otherWorkers = shiftToCompare.getAllWorkers();

        for(Workers w1: inThisWorkers){
            for(Workers w2: otherWorkers){
                if(w1.getId() == w2.getId()){ans.add(w1);}
            }
        }
        return ans;
    }

    private ArrayList<Workers> getAllWorkers(){
        // Create a new ArrayList to store all the workers
        ArrayList<Workers> allWorkers = new ArrayList<>();

        // Iterate over each ArrayList in the array
        for (ArrayList<Workers> workers : this.workerInShift) {
            // Add all the workers in the current ArrayList to the allWorkers list
            allWorkers.addAll(workers);
        }
        return allWorkers;
    }
    public int workerPro(Workers worker){
        int count=0;
        for (ArrayList<Workers> workerLisr: workerInShift){
            for(Workers w: workerLisr) {
                if(worker.getId()==w.getId()){
                    return count;
                }
            }
            count++;
            }
            return -1;
        }

}