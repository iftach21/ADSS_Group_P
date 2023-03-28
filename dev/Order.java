import java.util.List;
import java.util.Map;

public class Order {


    private String orderNum;
    private Map<Item,Float> itemList;

    private Boolean fulfilled;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Map<Item, Float> getItemList() {
        return itemList;
    }

    public void setItemList(Map<Item, Float> itemList) {
        this.itemList = itemList;
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }



    public Order(String orderNum, Map<Item, Float> itemList, Boolean fulfilled) {
        this.orderNum = orderNum;
        this.itemList = itemList;
        this.fulfilled = fulfilled;
    }
}
