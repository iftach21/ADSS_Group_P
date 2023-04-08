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
    public void add_item_to_supplier(String name_1 ,Item item,int amount,float price){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.add_Items(item,amount,price);
            }
    }}
    public void remove_item_to_supplier(String name_1 ,String item){
            for(Supplier suppleir :this.suppliers){
                if(suppleir.getName().equals(name_1)){
                    suppleir.remove_item(item);
                }
            }

}
    public void print_all_suppliers_names(){
        for(Supplier supplier: suppliers){
            System.out.println("" + supplier.getName());
        }
    }

    public void add_item_discount_to_supplier(String name_1, String item, int amount ,double discount){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.add_item_to_contract(item,amount,discount);
    }
}}}
