//This is the class that hold all the suppliers that work with the company
public class Supplier_Manger {
    public Supplier[] getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Supplier[] suppliers) {
        this.suppliers = suppliers;
    }


    Supplier[] suppliers;

    public Supplier_Manger(Supplier[] suppliers) {
        this.suppliers = suppliers;
    }
}
