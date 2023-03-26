import java.util.ArrayList;

public class WeeklyShift {
    private  int weekNUm;
    private int year;

    private Shift[] dayShift;
    private Shift[] nightShift;

    public int getWeekNUm() {
        return weekNUm;
    }

    public int getYear() {
        return year;
    }

    public WeeklyShift(int weekNUm, int year) {
        this.weekNUm = weekNUm;
        this.year = year;
        this.dayShift = new Shift[7];
        this.nightShift = new Shift[7];
    }

    public  boolean changeWorker(Workers worker1,Workers worker2, WindowType shiftTime){
        if(shiftTime.ordinal()<7){
            if(!this.dayShift[shiftTime.ordinal()].checkIfWorkerInShift(worker1.getID())){
                return false;}
            else {
                this.dayShift[shiftTime.ordinal()].removalWorker(worker1);
                this.nightShift[shiftTime.ordinal()].insertToShift(worker2);
            }
        }
        else {
            if(!this.dayShift[shiftTime.ordinal()].checkIfWorkerInShift(worker1.getID())){
                return false;}
            else {
                this.dayShift[shiftTime.ordinal()].removalWorker(worker1);
                this.dayShift[shiftTime.ordinal()].insertToShift(worker2);
            }
        }

        return true;
    }

    public  StringBuilder printSpesific(){
        StringBuilder pWeelShift= new StringBuilder();
        for (int i = 0; i < 7; i++){
            pWeelShift.append("Day:").append(i).append(dayShift[i].printShift());
            pWeelShift.append("Night:").append(i).append(dayShift[i].printShift());
        }
        return pWeelShift;
    }


    public int howMuchShiftWorkerDid(int id){
        //given id tells hm shift did the worker did
        int countShift=0;
        for(Shift dShift:this.dayShift){
            if(dShift.checkIfWorkerInShift(id)){
                countShift++;
            }
        }
        for(Shift nShift:this.dayShift) {
            if (nShift.checkIfWorkerInShift(id)) {
                countShift++;
            }
        }
        return countShift;

    }



}
