import java.util.List;

//This is the class that hold all the suppliers that work with the company
public class Supplier_Manger {
    private List<Supplier> suppliers;
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }




    public Supplier_Manger() {
    }
    public void  remove_supplier(String name){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name)){
                this.suppliers.remove(suppleir);
            }
        }
    }
    public void update_contact_preson(String name_1,String name_2,String phone_number) {
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.update_contact_person(name_2,phone_number);
    }}}
    public void add_item_to_contract(Item item,int amount,float price ,String name_1){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
               suppleir.add_Items(item,amount,price);

            }
    }}
    public void add_total_dsicount(double discount,String name_1){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.add_total_discount(discount);
            }
        }
    }
}
