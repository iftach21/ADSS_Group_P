package Domain.Employee;

import java.util.Arrays;

public class ShiftRequirement {
    public ShiftRequirement() {
        this.reqarray = new int[8];
        Arrays.fill(reqarray, 0);
    }

    private int[] reqarray;
    public void setReq(int profindex,int hm){
        this.reqarray[profindex] = hm;
    }
    public int getreqbyprof(int profindex){
        return reqarray[profindex];
    }

    public void printreq(){
        System.out.println("req list:");
        System.out.println("managers: " + reqarray[0]);
        System.out.println("cashier " + reqarray[1]);
        System.out.println("stoke " + reqarray[2]);
        System.out.println("security " + reqarray[3]);
        System.out.println("cleaning " + reqarray[4]);
        System.out.println("shelf-stoking " + reqarray[5]);
        System.out.println("general-worker " + reqarray[6]);
        System.out.println("Drivers " + reqarray[7]);
    }

}//endclass
