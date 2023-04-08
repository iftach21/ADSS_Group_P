import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Order {

    LocalDate currentDate;
    private int orderNum;
    private  Supplier supplier;
    private Map<Item,Pair<Integer,Float>> itemList;

    private Float cost;
    private int  Store_number;
    static int number =0;
    public int getStore_number() {
        return Store_number;
    }

    public void setStore_number(int store_number) {
        Store_number = store_number;
    }



    public Order( Map<Item,Pair<Integer,Float>>itemList,Supplier supplier,float cost,int store_number) {
        this.orderNum = number+1;
        if(itemList==null){
            this.itemList= new HashMap<Item,Pair<Integer,Float>>();
        }
        else {
            this.itemList= itemList;
        }
        this.cost=cost;
        this.supplier=supplier;
        this.Store_number=store_number;
        this.currentDate= LocalDate.now();

    }

    public Integer getOrderNum() {
        return orderNum;
    }



    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Map<Item,Pair<Integer,Float>> getItemList() {
        return itemList;
    }

    public void setItemList(Map<Item,Pair<Integer,Float>> itemList) {
        this.itemList = itemList;
    }

    public float getCost() {

        return (float) (cost*supplier.getContract().total_discount);
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    //This fuction is to remove an item to the order
    public void remove_item(Item item){
        this.cost-=this.itemList.get(item).getSecond();
        this.itemList.remove(item);

    }
    public void add_item(Item item ,int amount,float cost){
        Pair<Integer,Float> pair=new Pair(amount,cost);
        this.cost+=cost;
        this.itemList.put(item,pair);
    }
    public void print_items(){
        for(Item item :this.itemList.keySet()){
            System.out.println(item.getName());
        }
    }



}


