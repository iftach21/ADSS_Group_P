public abstract class Supplier {
    private String name;
    private String business_id;
    private String Payment_method ;
    private String Suplier_ID;
    private Contact_Person person;
    private Contract contract;

    public Supplier(String name, String business_id, String payment_method, String suplier_ID, Contact_Person person, Contract contract) {
        this.name = name;
        this.business_id = business_id;
        Payment_method = payment_method;
        Suplier_ID = suplier_ID;
        this.person = person;
        this.contract = contract;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getPayment_method() {
        return Payment_method;
    }

    public void setPayment_method(String payment_method) {
        Payment_method = payment_method;
    }

    public String getSuplier_ID() {
        return Suplier_ID;
    }

    public void setSuplier_ID(String suplier_ID) {
        Suplier_ID = suplier_ID;
    }

    public Contact_Person getPerson() {
        return person;
    }

    public void setPerson(Contact_Person person) {
        this.person = person;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
