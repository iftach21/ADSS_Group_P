import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

//This is the class that hold all the suppliers that work with the company
public class Supplier_Manger {
    private List<Supplier> suppliers;
    private Map<Item,Integer> itemslist;

    public Map<Item,Integer> getItemslist() {
        return itemslist;
    }

    public void setItemslist(Map<Item,Integer>itemslist) {
        this.itemslist = itemslist ;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }
    private ItemMapper mapper ;
    private NonDeliveringSupplierMapper nonDeliveringSupplierMapper;
    private NonFixedDaySupplierMapper nonFixedDaySupplierMapper;
    private FixedDaySupplierMapper fixedDaySupplierMapper;




    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }




    public Supplier_Manger() {
        this.suppliers=new ArrayList<Supplier>();
        this.itemslist=new HashMap<Item,Integer>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
            System.out.println("Connection to SuperLeeDataBase.db has been established.");
            this.mapper=new ItemMapper(conn);
            this.fixedDaySupplierMapper=new FixedDaySupplierMapper(conn);
            this.nonDeliveringSupplierMapper=new NonDeliveringSupplierMapper(conn);
            this.nonFixedDaySupplierMapper=new NonFixedDaySupplierMapper(conn);
            ///
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public boolean add_supplier(Supplier supplier) {
        if (!this.suppliers.contains(supplier)) {
            this.suppliers.add(supplier);
            for (Item item : supplier.getItems().keySet()) {
                if (itemslist.containsKey(item)) {
                    itemslist.replace(item, itemslist.get(item), itemslist.get(item) + 1);
                } else {
                    itemslist.put(item, 1);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean  remove_supplier(String name){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name)){
                for(Item item :suppleir.getItems().keySet()){
                    if(itemslist.containsKey(item)){
                        itemslist.replace(item,itemslist.get(item)-1);
                    }
                }
                this.suppliers.remove(suppleir);
                return true;
            }

        }
        return false;
    }
    public void update_contact_preson(String name_1,String name_2,String phone_number) {
        boolean flag = false;
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.update_contact_person(name_2,phone_number);
                flag = true;
    }}
    if(!flag)
    {
        System.out.println("There is no supplier with that name");
    }}
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
    public void add_item_to_supplier(String name_1 ,Item item,int amount,float price) {
        for (Supplier suppleir : this.suppliers) {
            if (suppleir.getName().equals(name_1)) {
                if (itemslist.containsKey(item)) {
                    itemslist.replace(item, itemslist.get(item),itemslist.get(item)+1);
                } else {
                    itemslist.put(item, 1);
                }

                suppleir.add_Items(item, amount, price);
            }
        }
    }
    public boolean remove_item_to_supplier(String name_1 ,String item_S) {
        for (Supplier suppleir : this.suppliers) {
            if (suppleir.getName().equals(name_1)) {
                suppleir.remove_item(item_S);
                for (Item item : suppleir.getItems().keySet()) {
                    if (item.getName().equals(item_S)) {
                        if (itemslist.containsKey(item)) {
                            itemslist.replace(item, itemslist.get(item) - 1);
                        }
                    }
                }

                return true;
            }
        }
        return false;
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
}}


    //Thiis fuction sort the supplier array by the days that each on can deliver
    public void sort_supplier_by_deliver_days(){
                int n = suppliers.size();
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        int days1 = suppliers.get(j).get_days();
                        int days2 = suppliers.get(j + 1).get_days();
                        if (days1 > days2) {
                            // Swap suppliers[j] and suppliers[j+1]
                            Supplier temp = suppliers.get(j);
                            suppliers.set(j, suppliers.get(j + 1));
                            suppliers.set(j + 1, temp);
                        }
                    }
                }
            }

        }


