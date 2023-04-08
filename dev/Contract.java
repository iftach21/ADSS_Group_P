import java.util.HashMap;
import java.util.Map;

public class Contract {
    public Map<Item,Map<Integer,Double>> items_Map_discount;
    public double total_discount;

    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public Contract(Map<Item, Map<Integer, Double>> items_Map_discount, double total_discount) {

        if(items_Map_discount==null){
            Map<Integer, Double> item_map=new HashMap<Integer, Double>();
            this.items_Map_discount = new HashMap<Item,Map<Integer, Double>>();
        }
        else {
        this.items_Map_discount = items_Map_discount;
        }
        this.total_discount=total_discount;

    }



    public Map<Item, Map<Integer, Double>> getItems_Map_discount() {
        return items_Map_discount;
    }

    public void setItems_Map_discount(Map<Item, Map<Integer, Double>> items_Map_discount) {
        this.items_Map_discount = items_Map_discount;
    }

    //add an item to the contract
    public void add_to_contract(Item item, int amount, double discount){
        //if the item has a discount
        if(this.items_Map_discount.containsKey(item)){
            //if the amount is in the map
            if(this.items_Map_discount.get(item).containsKey(amount))
                this.items_Map_discount.get(item).replace(amount,discount);
            else{
                this.items_Map_discount.get(item).put(amount,discount);
            }}
        else{
            Map<Integer,Double> map =new HashMap<Integer, Double>() ;
            map.put(amount,discount);
            this.items_Map_discount.put(item,map);
            }



    }
    //remove an item from the contract
    public void remove_by_item(Item item) {
        this.items_Map_discount.remove(item);
    }
    //remove an amount from the items contract
    public void remove_by_amount(Item item ,int amount){
        this.items_Map_discount.get(item).remove(amount);
    }
}
