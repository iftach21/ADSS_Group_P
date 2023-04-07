import java.util.ArrayList;
public class Shift {
    private String date;

    private ArrayList<Workers>[] workerInShift;
    private ShiftRequirement shiftRequirement;

    private Workers shiftManager;
    private String log;
    private String startTime;

    //
    // Creating a shift
    //
    public Shift(String date) {
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
     * check If Worker In Shift
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
     * removal Worker from Shift
     */
    public boolean removalWorker(Workers currentWorker) {
        if (checkIfWorkerInShift(currentWorker.getId())) {
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
}