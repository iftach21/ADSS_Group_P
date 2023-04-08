import java.util.Map;

public class FixedDaySupplier extends DeliveringSupplier {
    private WindowType currentDeliveryDay;

    public WindowType getCurrentDeliveryDay() {
        return currentDeliveryDay;
    }

    public void setCurrentDeliveryDay(WindowType currentDeliveryDay) {
        this.currentDeliveryDay = currentDeliveryDay;
    }

    public FixedDaySupplier(WindowType currentDeliveryDay,String name, String business_id, int payment_method, String suplier_ID, Contact_Person person, Contract contract, Map<Item,Pair<Integer, Float>> items) {
        super(name, business_id, payment_method, suplier_ID, person, contract, items);
        this.currentDeliveryDay = currentDeliveryDay;}
}
