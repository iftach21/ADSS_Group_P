
import java.util.ArrayList;
public class Shift {
    private String date;
    private ArrayList<Workers> ArrWorkers ;
    private Workers shiftManager;   //todo: i need to add here somethingh
    private String log;

    public Shift(String date, ArrayList<Workers> workers, Workers shiftManager, String log) {
        this.date = date;
        this.ArrWorkers = workers;
        this.shiftManager = shiftManager;
        this.log = log;
    }

    public StringBuilder printShift(){

        StringBuilder pShift= new StringBuilder();
        for (Workers worker: this.ArrWorkers){
            pShift.append("name:").append(worker.getName()).append(" ");
        }
        return pShift;
    }
    public boolean insertToShift(Workers newWorker){
        if (newWorker==null|| !checkIfWorkerInShift(newWorker.getID())){
            return false;}
        this.ArrWorkers.add(newWorker);
        return true;
    }
    public boolean checkIfWorkerInShift(int id){
        for (Workers worker:ArrWorkers){
            if(worker.getID()==id){return false;}
        }
        return true;
    }
    public boolean removalWorker(Workers currentWorker){
        int i=0;
        for (Workers worker:ArrWorkers){
            if(worker.getID()==currentWorker.getID()){
                ArrWorkers.remove(i);
            return true;}
            i++;
        }
        return false;

    }
}
