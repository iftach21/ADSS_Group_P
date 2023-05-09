import org.junit.internal.requests.OrderingRequest;

import java.util.Date;
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
    static int number = 0;

    private StatusOrder statusOrder;



    public Order() {
        this.itemList= new HashMap<Item,Pair<Integer,Float>>();
        this.currentDate= LocalDate.now();
        this.cost = 0.0F;
    }

    public Order(Map<Item,Pair<Integer,Float>>itemList, Supplier supplier, float cost, int store_number) {
        this.orderNum = number;
        number+=1;
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
        this.statusOrder=StatusOrder.Waiting;

    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public int getStore_number() {
        return Store_number;
    }

    public void setStore_number(int store_number) {
        Store_number = store_number;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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

    //fixed cost
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
        Pair<Integer,Float> pair = new Pair(amount,cost);
        this.cost+=cost;
        this.itemList.put(item,pair);
    }
    public void print_items(){
        for(Item item :this.itemList.keySet()){
            System.out.println(item.getName());
        }
    }
    public void print_order_detail(){
        System.out.println("Order number: " +this.orderNum);
        System.out.println("Store number: " + this.Store_number);
        System.out.println("cost of the order: " + this.getCost());
        System.out.println("date: " + this.currentDate);
        System.out.println("Supplier name: " + this.supplier.getName());
        System.out.println("items:");

        for(Item item: this.itemList.keySet()){
            System.out.println("-------------------------------------------------------------");
            item.print_item();
            System.out.println("base price per unit no discount's:"+this.supplier.getItems().get(item).getSecond());
            System.out.println("amount :" +this.itemList.get(item).getFirst());
            System.out.println("price :" +this.itemList.get(item).getSecond());
            System.out.println("-------------------------------------------------------------");
        }
    }



}


