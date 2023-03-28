import java.util.ArrayList;
public class Shift {
    private String date;
//    private ArrayList<Workers> ArrWorkers;

    private ArrayList<Workers>[] workerInShift;

    private Workers shiftManager;   //todo: i need to add here somethingh
    private String log;

    public Shift(String date, Workers shiftManager) {
        this.date = date;
        this.shiftManager = shiftManager;
        this.log = " ";
        ArrayList<Workers>[] myArrayListArray = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            myArrayListArray[i] = new ArrayList<Workers>();
        }
    }

    public StringBuilder printShift() {

        StringBuilder pShift = new StringBuilder();
        int i;
        for (i = 0; i <= 7;i++){
            for (int j = 0; j < this.workerInShift[i].size(); j++) {
                System.out.println("  worker " + this.workerInShift[i].get(j));
            }
        }
        return pShift;
    }
    public boolean insertToShift(Workers newWorker, int profindx){
        if (newWorker==null|| !checkIfWorkerInShift(newWorker.getId())){
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
    public boolean checkIfWorkerInShift(int id){
        for (int i = 0; i <= 7;i++){
            for (int j = 0; j < this.workerInShift[i].size(); j++) {
                if( id==this.workerInShift[i].get(j).getId()){return true;}
            }
        }
        return false;
    }
    public boolean removalWorker(Workers currentWorker) {
        if (checkIfWorkerInShift(currentWorker.getId())) {
            return false;
        }
        for (int i = 0; i <= 7;i++){
            for (int j = 0; j < this.workerInShift[i].size(); j++) {
                if( currentWorker.getId()==this.workerInShift[i].get(j).getId()){
                    this.workerInShift[i].remove(j);
                    return true;}
            }
        }
        return true;
    }
}