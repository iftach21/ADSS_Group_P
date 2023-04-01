import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {


    private String orderNum;
    private  Supplier supplier;
    private Map<Item,Integer> itemList;

    private Boolean fulfilled;
    private Float cost;


    public Order(String orderNum, Map<Item,Integer> itemList, Boolean fulfilled,Supplier supplier,float cost) {
        this.orderNum = orderNum;
        if(itemList==null){
            this.itemList= new HashMap<Item,Integer>();
        }
        else {
            this.itemList= itemList;
        }
        this.cost=cost;
        this.supplier=supplier;
        this.itemList = itemList;
        this.fulfilled = fulfilled;

    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Map<Item, Integer> getItemList() {
        return itemList;
    }

    public void setItemList(Map<Item, Integer> itemList) {
        this.itemList = itemList;
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    //This fuction is to remove an item to the order
    public void remove_item(Item item){
        this.itemList.remove(item);

    }
    public void add_item(Item item ,int amount){
        this.itemList.put(item,amount);
    }



}


