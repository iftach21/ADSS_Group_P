import java.util.Map;

public class NonFixedDaySupplier extends DeliveringSupplier{
    int numOfDayToDeliver;


    public NonFixedDaySupplier(int numOfDayToDeliver,WindowType currentDeliveryDay,String name, String business_id, String payment_method, String suplier_ID, Contact_Person person, Contract contract, Map<Item, Float> items) {
        super(name, business_id, payment_method, suplier_ID, person, contract, items);
        this.numOfDayToDeliver = numOfDayToDeliver;
    }

    public int getNumOfDayToDeliver() {
        return numOfDayToDeliver;
    }

    public void setNumOfDayToDeliver(int numOfDayToDeliver) {
        this.numOfDayToDeliver = numOfDayToDeliver;
    }
}
