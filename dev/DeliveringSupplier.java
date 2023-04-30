import java.util.Map;

public abstract class DeliveringSupplier extends Supplier {


    public DeliveringSupplier(String name, String business_id, int payment_method, String suplier_ID, ContactPerson person, Contract contract, Map<Item,Pair<Integer, Float>> items) {
        super(name, business_id, PaymentCreator.getpayment(payment_method), suplier_ID, person, contract, items);
    }



}
