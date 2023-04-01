public class OrderManger {

    private Order[] pending_for_apporval;
    private Order[] pending_on_approval;
    private  Order[] orders_history;

    public OrderManger(Order[] pending_for_apporval, Order[] pending_on_approval, Order[] orders_history) {
        this.pending_for_apporval = pending_for_apporval;
        this.pending_on_approval = pending_on_approval;
        this.orders_history = orders_history;
    }

    public void add_item_to_Order(Item item, int amount, Supplier_Manger manger){
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

            Pair<Integer, Supplier> pair = new Pair<>(amount,min_sup);
            for(Contract contract::)

            return;
        }
    }
}
