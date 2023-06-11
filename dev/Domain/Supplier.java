package Domain;

import java.util.HashMap;
import java.util.Map;



//This is the supplier class the represent a supplier abstract that ecah type of supplier take his field and fuction
public abstract class Supplier {
    private String name;
    private String business_id;
//    private String Payment_method;
    private PaymentMethod paymentMethod;
    private String Supplier_ID;
    private ContactPerson person;
    private Contract contract;
    private Map<Item,Pair<Integer,Float>>items;


    public Supplier(String name, String business_id, PaymentMethod paymentMethod, String supplier_ID, ContactPerson person, Contract contract, Map<Item, Pair<Integer,Float>> items) {
        this.name = name;
        this.business_id = business_id;
        this.paymentMethod = paymentMethod;
        Supplier_ID = supplier_ID;
        this.person = person;
        //in case there is no given contract or a list of item and the user can create them himsself
        if(contract==null){
            this.contract = new Contract(null,1);
            this.contract.setSupplierId(this.Supplier_ID);
        }
        else {
            this.contract=contract;
            this.contract.setSupplierId(this.Supplier_ID);
        }
        if(items==null){
            this.items=new HashMap<Item,Pair<Integer,Float>>();
        }
        else {
        this.items = items;}
    }


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessId() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

//    public String getPayment_method() {
//        return Payment_method;
//    }
//    public PaymentMethod getPayment_method() {return paymentMethod;}


//    public void setPayment_method(String payment_method) {
//        Payment_method = payment_method;
//    }
//    public void setPayment_method(int payment_method) {
//        paymentMethod = PaymentCreator.getpayment(payment_method);
//    }


    public String getSupplierID() {
        return Supplier_ID;
    }

    public void setSupplier_ID(String supplier_ID) {
        Supplier_ID = supplier_ID;
    }

    public ContactPerson getPerson() {
        return person;
    }

    public void setPerson(ContactPerson person) {
        this.person = person;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Map<Item,Pair<Integer,Float>> getItems() {
        return items;
    }

    public void setItems(Map<Item, Pair<Integer,Float>> items) {
        this.items = items;
    }
    public void add_Items(Item item,int num,float cost) {
        if(item!=null){
        Pair<Integer,Float> pair=new Pair<Integer,Float>(num,cost);
        items.put(item,pair);}
    }
//    public  void add_item_to_contract(String name,int num,double discount){
//        for(Item item : items.keySet()){
//            if(item.getName().equals(name)){
//
//            this.contract.add_to_contract(item,num,discount);}
//    }}

    public  void add_item_to_contract(String catalogNum,int num,double discount){
        for(Item item : items.keySet()){
            if(item.getCatalogNum().equals(catalogNum)){
                this.contract.add_to_contract(item,num,discount);}
        }}
    public void update_contact_person(String name ,String number){
        ContactPerson contact_person=new ContactPerson(name,number);
        this.person=contact_person;
    }
    public void add_total_discount(double discount){
        this.contract.total_discount=discount;
    }

//    public  void remove_item(String item_to_remove){
//        Item item1 =null;
//        for(Item item :items.keySet())
//        {
//            if(item.getName().equals(item_to_remove))
//            {
//            this.contract.remove_by_item(item);
//            item1 = item;
//            }
//        }
//        items.remove(item1);
//    }

    public  void remove_item(String item_to_remove){
        Item item1 =null;
        for(Item item :items.keySet())
        {
            if(item.getCatalogNum().equals(item_to_remove))
            {
                this.contract.remove_by_item(item);
                item1 = item;
            }
        }
        items.remove(item1);
    }

   public void print_items(){
        for(Item item :items.keySet()){
            System.out.println(item.getName());
        }

   }
    public void print() {
        System.out.println("Supplier Details:");
        System.out.println("Name: " + name);
        System.out.println("Business ID: " + business_id);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Supplier ID: " + Supplier_ID);

        System.out.println("\nContact Person Details:");
        System.out.println("Name: " + person.getName());
        System.out.println("Phone Number: " + person.getPhoneNumber());

        System.out.println("\nContract Details:");
        System.out.println("Contract ID: " + contract.contractId);
        System.out.println("Supplier ID: " + contract.supplierId);
        System.out.println("Total Discount: " + contract.getTotal_discount());

        System.out.println("\nItems:");
        for (Item item : items.keySet()) {
            Pair<Integer, Float> itemDetails = items.get(item);
            System.out.println("Item Name: " + item.getName());
            System.out.println("Quantity: " + itemDetails.getFirst());
            System.out.println("Cost: " + itemDetails.getSecond());
            System.out.println("-------------------");
        }
    }
   public int get_days(){
        return 0;
   }

   public int getType(){
        return 0;
   }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Supplier Details:\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Business ID: ").append(business_id).append("\n");
        sb.append("Payment Method: ").append(paymentMethod).append("\n");
        sb.append("Supplier ID: ").append(Supplier_ID).append("\n");

        sb.append("\nContact Person Details:\n");
        sb.append("Name: ").append(person.getName()).append("\n");
        sb.append("Phone Number: ").append(person.getPhoneNumber()).append("\n");

        sb.append("\nContract Details:\n");
        sb.append("Contract ID: ").append(contract.getContractId()).append("\n");
        sb.append("Supplier ID: ").append(contract.getSupplierId()).append("\n");
        sb.append("Total Discount: ").append(contract.getTotal_discount()).append("\n");

        sb.append("\nItems:\n");
        for (Item item : items.keySet()) {
            Pair<Integer, Float> itemDetails = items.get(item);
            sb.append("Item Name: ").append(item.getName()).append("\n");
            sb.append("Quantity: ").append(itemDetails.getFirst()).append("\n");
            sb.append("Cost: ").append(itemDetails.getSecond()).append("\n");
            sb.append("-------------------\n");
        }

        return sb.toString();
    }

}