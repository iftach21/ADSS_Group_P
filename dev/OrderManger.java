import java.util.*;

public class OrderManger {

    private List<Order> pending_for_apporval;
    private List<Order> pending_on_approval;
    private List<Order> orders_history;

    public OrderManger() {

    }

    public void assing_Orders_to_Suppliers(Map<Item,Integer> itemlist, Supplier_Manger manger){
        //if there is no supplier do nothing
        if(manger == null){
            return;
        }
        //to find the min supplier(in case of one supplier have it all)
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
                    int new_amount=0;
                    //if the supplier give a discount on the item serch for the biggest amount
                    if(supplier.getContract().items_Map_discount.containsKey(item)){
                        //cheak how much max amount have a discount from the curr amount
                        for(int i=0; i<amount;i++){
                        if(supplier.getContract().items_Map_discount.get(item).containsKey(amount-i)){
                             discount = supplier.getContract().items_Map_discount.get(item).get(amount-i);
                             new_amount=amount-i;


                            break;
                        }


                        }
                    }
                    //add the item to the total price o
                    cost+=new_amount*base_price*discount+((amount-new_amount)*base_price);

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
                        int new_amount=0;
                            //serch for discount
                        for(int i=0; i<amount;i++){
                            if(sup.getContract().items_Map_discount.get(item).containsKey(amount-i)){
                                discount = sup.getContract().items_Map_discount.get(item).get(amount-i);
                            new_amount=amount-i;}}

                        cost+=new_amount*base_price*discount+((amount-new_amount)*base_price);

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
            //if there is all ready an open order that wait for the approvel
            for(Order order:this.pending_for_apporval){
                for(Item item: items_costs.keySet()){
                    if(order.getSupplier()==items_costs.get(item).getFirst()){
                        order.add_item(item,itemlist.get(item));
                        //add the cost of the item to the list
                        order.setCost(order.getCost()+items_costs.get(item).getSecond());
                        //remove form the holder list
                        items_costs.remove(item);
                    }



                }

            }
            List<Supplier> my_gang_supp = new ArrayList<>();
            for(Item item:items_costs.keySet()){
                my_gang_supp.add(items_costs.get(item).getFirst());
            }
            for(Supplier supplier:my_gang_supp){

            }




        }
        else{
            Order order=new Order("1",itemlist,false,min_sup,min_cost);
            this.pending_for_apporval.add(order);
        }



    }
}
