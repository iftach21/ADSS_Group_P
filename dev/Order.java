import java.util.List;

public class Order {
    private String orderNum;
    private List<Item> itemList;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    Boolean fulfilled;

    public Order(String orderNum, List<Item> itemList, Boolean fulfilled) {
        this.orderNum = orderNum;
        this.itemList = itemList;
        this.fulfilled = fulfilled;
    }
}
