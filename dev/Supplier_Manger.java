import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

//The Supplier_Manger class represents a manager for a company's suppliers.
// It contains various methods for managing suppliers, contracts, and items.
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
    private ItemMapper itemMapper;
    private  ContractMapper contractMapper;
    private NonDeliveringSupplierMapper nonDeliveringSupplierMapper;
    private NonFixedDaySupplierMapper nonFixedDaySupplierMapper;
    private FixedDaySupplierMapper fixedDaySupplierMapper;




    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }




    public Supplier_Manger() {
        this.suppliers = new ArrayList<Supplier>();
        this.itemslist = new HashMap<Item,Integer>();
        try
        {
//            Connection conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
            System.out.println("Connection to SuperLeeDataBase.db has been established.");
//            this.itemMapper = new ItemMapper(conn);
            this.itemMapper = new ItemMapper();

//            this.fixedDaySupplierMapper=new FixedDaySupplierMapper(conn);
            this.fixedDaySupplierMapper=new FixedDaySupplierMapper();

//            this.nonDeliveringSupplierMapper=new NonDeliveringSupplierMapper(conn);
            this.nonDeliveringSupplierMapper=new NonDeliveringSupplierMapper();

//            this.nonFixedDaySupplierMapper=new NonFixedDaySupplierMapper(conn);
            this.nonFixedDaySupplierMapper=new NonFixedDaySupplierMapper();

//            this.contractMapper = new ContractMapper(conn);
            this.contractMapper = new ContractMapper();

            this.update_suppliers();
            Contract contract = new Contract();
            if(this.contractMapper.findAll().size() == 0)
            {
                contract.setContractIdCounter(0);
                return;
            }
            contract.setContractIdCounter(this.contractMapper.findAll().get(this.contractMapper.findAll().size() - 1 ).contractId);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }


    //add supplier to the data-base and also to a list of all supplier so i can play with it when i want and i always love to play
    public boolean add_supplier(Supplier supplier) {
        if(supplier.getType()==0){
            this.fixedDaySupplierMapper.insert((FixedDaySupplier) supplier);
        } else if (supplier.getType()==2) {
            this.nonDeliveringSupplierMapper.insert((NonDeliveringSupplier) supplier);

        } else if (supplier.getType()==1) {
            this.nonFixedDaySupplierMapper.insert((NonFixedDaySupplier) supplier);

        }

        if (!this.suppliers.contains(supplier)) {
            this.suppliers.add(supplier);
            for (Item item : supplier.getItems().keySet()) {
                if(this.itemMapper.findAllByCatalogNum(item.getCatalogNum())==null){
                    itemMapper.insert(item);
                }
            }
            contractMapper.insert(supplier.getContract());
            this.update_suppliers();
            return true;
        } else {
            return false;
        }
    }

            //remove a supploer form the data base base on his string
    public boolean  remove_supplier(String name){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name)){
                if(suppleir .getType()==0){
                    this.fixedDaySupplierMapper.delete((FixedDaySupplier) suppleir );
                } else if (suppleir .getType()==2) {
                    this.nonDeliveringSupplierMapper.delete((NonDeliveringSupplier) suppleir );

                } else if (suppleir .getType()==1) {
                    this.nonFixedDaySupplierMapper.delete((NonFixedDaySupplier) suppleir );

                }
                this.update_suppliers();
                return true;
            }

        }
        return false;
    }


    //Update the contrace person of a supplier and update the data base to.
    public void update_contact_preson(String name_1,String name_2,String phone_number) {
        boolean flag = false;
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.update_contact_person(name_2,phone_number);
                if(suppleir .getType()==0){
                    this.fixedDaySupplierMapper.update((FixedDaySupplier) suppleir );
                } else if (suppleir .getType()==2) {
                    this.nonDeliveringSupplierMapper.update((NonDeliveringSupplier) suppleir );

                } else if (suppleir .getType()==1) {
                    this.nonFixedDaySupplierMapper.update((NonFixedDaySupplier) suppleir );

                }
                flag = true;
    }}
    if(!flag)
    {
        System.out.println("There is no supplier with that name");
    }}

    //add item to  from supplier from the manager and updates the corresponding mapper and database.
    public void add_item_to_contract(Item item,int amount,float price ,String name_1) {
        for (Supplier suppleir : this.suppliers) {
            if (suppleir.getName().equals(name_1)) {
                suppleir.add_Items(item, amount, price);
                if (suppleir.getType() == 0) {
                    this.fixedDaySupplierMapper.update((FixedDaySupplier) suppleir);
                } else if (suppleir.getType() == 2) {
                    this.nonDeliveringSupplierMapper.update((NonDeliveringSupplier) suppleir);

                } else if (suppleir.getType() == 1) {
                    this.nonFixedDaySupplierMapper.update((NonFixedDaySupplier) suppleir);

                }
                contractMapper.update(suppleir.getContract());
            }
        }
    }


    //add total discount to a supplier and update the data base
    public void add_total_dsicount(double discount,String name_1){
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.add_total_discount(discount);
                contractMapper.update(suppleir.getContract());
            }
        }
    }


    //add item from supplier from the manager and updates the corresponding mapper and database.
    public void add_item_to_supplier(String name_1 ,Item item,int amount,float price) {
        for (Supplier suppleir : this.suppliers) {
            if (suppleir.getName().equals(name_1)) {
                suppleir.add_Items(item, amount, price);
                if (suppleir.getType() == 0) {
                    this.fixedDaySupplierMapper.update((FixedDaySupplier) suppleir);
                } else if (suppleir.getType() == 2) {
                    this.nonDeliveringSupplierMapper.update((NonDeliveringSupplier) suppleir);

                } else if (suppleir.getType() == 1) {
                    this.nonFixedDaySupplierMapper.update((NonFixedDaySupplier) suppleir);

                }
            }
        }
        this.update_suppliers();
    }

    //Removes item from supplier from the manager and updates the corresponding mapper and database.
    public boolean remove_item_to_supplier(String name_1 ,String item_S) {
        for (Supplier suppleir : this.suppliers) {
            if (suppleir.getName().equals(name_1)) {
                suppleir.remove_item(item_S);
                for (Item item : suppleir.getItems().keySet()) {
                    if (item.getName().equals(item_S)) {
                        Contract con=suppleir.getContract();
                        con.remove_by_item(item);
                        suppleir.setContract(con);
                        contractMapper.update(suppleir.getContract());
                    }
                }
                if (suppleir.getType() == 0) {
                    this.fixedDaySupplierMapper.update((FixedDaySupplier) suppleir);
                } else if (suppleir.getType() == 2) {
                    this.nonDeliveringSupplierMapper.update((NonDeliveringSupplier) suppleir);

                } else if (suppleir.getType() == 1) {
                    this.nonFixedDaySupplierMapper.update((NonFixedDaySupplier) suppleir);

                }
                return true;
            }
        }
        return false;
    }


    // Prints the names of all suppliers in the manager.
    public void print_all_suppliers_names(){
        this.update_suppliers();
        for(Supplier supplier: suppliers){
            System.out.println("" + supplier.getName());
        }
    }


    //Adds a discount to a specific item for a specific supplier and updates the corresponding contract and database.
    public void add_item_discount_to_supplier(String name_1, String item, int amount ,double discount){
        this.update_suppliers();
        for(Supplier suppleir :this.suppliers){
            if(suppleir.getName().equals(name_1)){
                suppleir.add_item_to_contract(item,amount,discount);
                contractMapper.update(suppleir.getContract());

    }
}}


    //This fuction sort the supplier array by the days that each on can deliver
    public void sort_supplier_by_deliver_days(){
            this.update_suppliers();
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


    //Retrieves a supplier based on their ID.
    public Supplier get_supplier_by_id(String id){
        for(Supplier suppleir :this.suppliers) {
            if (suppleir.getSupplierID().equals(id)) {
                return suppleir;

            }
        }
        return null;
    }

    //Returns a set of all items associated with the suppliers.
    public Set<Item> get_itemlist_ofallsuppliers() {
        Set<Item> items = new HashSet<>();
        for (Supplier supplier : suppliers) {
            items.addAll(supplier.getItems().keySet());
        }
        return items;
    }





    public void update_suppliers()
    {
        this.suppliers = new ArrayList<Supplier>();
        if(this.fixedDaySupplierMapper.findAll().size()!=0)
        {
            this.suppliers.addAll( this.fixedDaySupplierMapper.findAll());
        }
        if(this.nonDeliveringSupplierMapper.findAll().size()!=0)
        {
            this.suppliers.addAll(this.nonDeliveringSupplierMapper.findAll());
        }
        if(this.nonFixedDaySupplierMapper.findAll().size()!=0)
        {
            this.suppliers.addAll(this.nonFixedDaySupplierMapper.findAll());
        }
    }


}


