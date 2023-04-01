import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrderManger {

    private Order[] pending_for_apporval;
    private Order[] pending_on_approval;
    private  Order[] orders_history;

    public OrderManger(Order[] pending_for_apporval, Order[] pending_on_approval, Order[] orders_history) {
        this.pending_for_apporval = pending_for_apporval;
        this.pending_on_approval = pending_on_approval;
        this.orders_history = orders_history;
    }

    public void assing_Orders_to_Suppliers(Map<Item,Integer> itemlist, Supplier_Manger manger){
        if(manger == null){
            return;
        }

        Supplier min_sup=null;
        float min_cost=1000000000;


        for(Supplier supplier: manger.getSuppliers()){

            //cheak there is suppliers that have all the items
            if(supplier.getItems().keySet().containsAll(itemlist.keySet())){
                if(min_sup==null){
                    min_sup=supplier;
                }
                float cost=0;
                for(Item item :itemlist.keySet()){
                    double discount=1;

                    int amount=itemlist.get(item);

                    float base_price = supplier.getItems().get(item);
                    if(supplier.getContract().items_Map_discount.containsKey(item)){
                        //cheak how much max amount have a discount from the curr amount
                        for(int i=0; i<amount;i++){
                        if(supplier.getContract().items_Map_discount.get(item).containsKey(amount-i)){
                             discount = supplier.getContract().items_Map_discount.get(item).get(amount-i);


                            break;
                        }


                        }
                    }
                    cost+=amount*base_price*discount;

                }
                //if there a cheaps supplier
                if(cost<min_cost){
                    min_cost=cost;
                    min_sup=supplier;
                }





            }



        }
        if(min_sup==null){
            Map<Item,Pair<Supplier,Float>> items_costs =new HashMap<Item,Pair<Supplier,Float>>();
            for(Supplier sup: manger.getSuppliers()){
                for(Item item :itemlist.keySet()){
                    if (!items_costs.containsKey(item)) {
                        items_costs.put(item,null);
                    }
                    if(sup.getItems().containsKey(item)){
                        double discount=1;
                        float cost=0;

                        float base_price = sup.getItems().get(item);

                        int amount=itemlist.get(item);

                        for(int i=0; i<amount;i++){
                            if(sup.getContract().items_Map_discount.get(item).containsKey(amount-i)){
                                discount = sup.getContract().items_Map_discount.get(item).get(amount-i);}}

                        cost = (float) ((float)amount*base_price*discount);

                        if(items_costs.get(item)==null){
                           items_costs.remove(item);
                           Pair<Supplier,Float>new_sup=new Pair<>(sup,cost);
                           items_costs.put(item,new_sup);


                        }
                        else {
                            if(items_costs.get(item).getSecond()>cost){
                                items_costs.remove(item);
                                Pair<Supplier,Float>new_sup=new Pair<>(sup,cost);
                                items_costs.put(item,new_sup);
                            }
                        }
                    }

                }
            }



        }


    }
}
