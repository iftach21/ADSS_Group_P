import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {


    private String orderNum;
    private  Supplier supplier;
    private Map<Item,Pair<Integer,Supplier>> itemList;

    private Boolean fulfilled;


    public Order(String orderNum, Map<Item, Pair<Integer, Supplier>> itemList, Boolean fulfilled,Supplier supplier) {
        this.orderNum = orderNum;
        if(itemList==null){
            this.itemList= new HashMap<Item,Pair<Integer,Supplier>>();
        }
        else {
            this.itemList= itemList;
        }
        this.supplier=supplier;
        this.itemList = itemList;
        this.fulfilled = fulfilled;

    }

    //This fuction is to remove an item to the order
    public void remove_item(Item item){
        this.itemList.remove(item);

    }



}


