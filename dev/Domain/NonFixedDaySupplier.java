package Domain;

import java.util.Map;

public class NonFixedDaySupplier extends DeliveringSupplier{
    int numOfDayToDeliver;


    public NonFixedDaySupplier(int numOfDayToDeliver, String name, String business_id, int payment_method, String suplier_ID, ContactPerson person, Contract contract, Map<Item,Pair<Integer, Float>> items) {
        super(name, business_id, payment_method, suplier_ID, person, contract, items);
        this.numOfDayToDeliver=numOfDayToDeliver;
    }

    public int getNumOfDayToDeliver() {
        return numOfDayToDeliver;
    }
    @Override
    public int get_days(){
       return numOfDayToDeliver;
    }

    public void setNumOfDayToDeliver(int numOfDayToDeliver) {
        this.numOfDayToDeliver = numOfDayToDeliver;
    }
    @Override
    public int getType(){
        return 1;
    }
}


