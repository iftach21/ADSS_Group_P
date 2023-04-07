import java.util.Arrays;

public class ShiftRequirement {
    public ShiftRequirement() {
        this.reqarray = new int[7];
        Arrays.fill(reqarray, 0);
    }

    private int[] reqarray;
    public void setReq(int profindex,int hm){
        this.reqarray[profindex] = hm;
    }
    public int getreqbyprof(int profindex){
        return reqarray[profindex];
    }

}//endclass
