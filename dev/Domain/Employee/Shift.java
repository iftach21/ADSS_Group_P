package Domain.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shift {
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

    public Shift(Workers shiftManager){
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

    public Shift() {
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
        for( Driver driver:this.drivers){
            pShift.append("name: ").append(driver.getName()).append(" id: ").append(driver.getId());
        }
        return pShift;
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
        if(profindx == 7){this.drivers.add((Driver)newWorker);return true;}
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
    public ArrayList<String> giveDriversString(){
        ArrayList<String> listDtiver=new ArrayList<>();
        for(Driver driver:this.drivers){
            listDtiver.add(driver.getName());
        }
        return listDtiver;
    }

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
        if(shiftManager == null){return 0;}
        return shiftManager.getId();
    }

    public String getLog() {
        return log;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public List<Workers> getDiffWorkers(Shift shiftToCompare){

        List<Workers> ans = new ArrayList<>();
        if(shiftToCompare==null){return ans;}

        List<Workers> inThisWorkers = this.getAllWorkers();
        inThisWorkers.addAll(this.drivers);
        List<Workers> otherWorkers = shiftToCompare != null ? shiftToCompare.getAllWorkers()  : new ArrayList<>() ;
        otherWorkers.addAll(shiftToCompare.drivers);

        for(Workers w1: inThisWorkers){
            int flag = 0;
            for(Workers w2: otherWorkers){
                if(w1.getId() == w2.getId()){flag=1;}
            }
            if(flag==0){ans.add(w1);}
        }
        return ans;
    }

    public ArrayList<Workers> getAllWorkers(){
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
    public ArrayList<String> workerByPro(int pro){
        ArrayList<String> listWorkerBypro= new ArrayList<>();
        for (Workers worker:this.workerInShift[pro]){
            listWorkerBypro.add(worker.getName());
        }
        return listWorkerBypro;
    }

}