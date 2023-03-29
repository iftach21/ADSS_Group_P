import java.util.List;
import java.util.Map;

public class Order {


    private String orderNum;
    private Map<Item,Integer> itemList;

    private Boolean fulfilled;

    public Order(String orderNum, Map<Item, Integer> itemList, Boolean fulfilled) {
        this.orderNum = orderNum;
        this.itemList = itemList;
        this.fulfilled = fulfilled;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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
    //This fuction is to remove an item to the order
    public void remove_item(Item item){
        this.itemList.remove(item);

    }
    //This fuction is to remove an item from a order
    public  void add_item(Item item, int amount){
        this.itemList.put(item,amount);

    }

}
