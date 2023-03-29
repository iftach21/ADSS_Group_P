import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {


    private String orderNum;
    private Map<Item,Pair<Integer,Supplier>> itemList;

    private Boolean fulfilled;


    public Order(String orderNum, Map<Item, Pair<Integer, Supplier>> itemList, Boolean fulfilled) {
        this.orderNum = orderNum;
        if(itemList==null){
            this.itemList= new HashMap<Item,Pair<Integer,Supplier>>();
        }
        else {
            this.itemList= itemList;
        }
        this.itemList = itemList;
        this.fulfilled = fulfilled;

    }

    //This fuction is to remove an item to the order
    public void remove_item(Item item){
        this.itemList.remove(item);

    }
    public void add_item_to_Order(Item item,int amount,Supplier_Manger manger){
        if(manger == null){
            return;
        }
        float min_cost_for_amount_found=1000000000;
        Supplier min_sup=null;
        ///check who is the suppler  that sell the lowest cost
        for(Supplier sup :manger.suppliers){
            float cost=0;
            //if he has the item
            if(sup.getItems().containsKey(item)){
                //cheak if he have a discount on it
                float cost_of_item= sup.getItems().get(item);
                int amount_on_discount=0;
                if(sup.getContract().items_Map_discount.containsKey(item)){
                    for(int i=0;i<amount;i++){
                        if(sup.getContract().items_Map_discount.get(item).containsKey(amount -i)){
                            amount_on_discount =amount-i;
                            cost= (float) (cost_of_item*amount_on_discount*sup.getContract().items_Map_discount.get(item).get(amount -i));
                                    break;
                        }
                    }



                }
                cost+=amount-amount_on_discount*cost_of_item;
                if(cost<min_cost_for_amount_found){
                    min_sup=sup;
                    min_cost_for_amount_found=cost;
                }

            }
        }
        if(min_sup!=null){
            Pair<Integer, Supplier> pair=new Pair<>(amount,min_sup);
            this.itemList.put(item,pair);
            return;
        }
    }


}


