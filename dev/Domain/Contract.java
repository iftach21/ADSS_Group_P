package Domain;

import java.util.HashMap;
import java.util.Map;

public class Contract {
    private static int contractIdCounter = 0;

    public Map<Item,Map<Integer,Double>> itemsMapDiscount;
    public double total_discount;

    public int contractId;

    public String supplierId;


    public Contract(Map<Item, Map<Integer, Double>> itemsMapDiscount, double total_discount) {

        if(itemsMapDiscount ==null){
            Map<Integer, Double> item_map=new HashMap<Integer, Double>();
            this.itemsMapDiscount = new HashMap<Item,Map<Integer, Double>>();
        }
        else {
        this.itemsMapDiscount = itemsMapDiscount;
        }
        this.total_discount=total_discount;
        this.contractId = contractIdCounter;
        contractIdCounter ++;
    }

    public Contract()
    {
        Map<Integer, Double> item_map=new HashMap<Integer, Double>();
        this.itemsMapDiscount = new HashMap<Item,Map<Integer, Double>>();
        this.total_discount = 0;
        this.contractId = contractIdCounter;
        contractIdCounter ++;
    }

    public Map<Item, Map<Integer, Double>> getItemsMapDiscount() {
        return itemsMapDiscount;
    }

    public void setItemsMapDiscount(Map<Item, Map<Integer, Double>> itemsMapDiscount) {
        this.itemsMapDiscount = itemsMapDiscount;
    }

    //add an item to the contract
    public void add_to_contract(Item item, int amount, double discount){
        //if the item has a discount
        if(this.itemsMapDiscount.containsKey(item)){
            //if the amount is in the map
            if(this.itemsMapDiscount.get(item).containsKey(amount))
                this.itemsMapDiscount.get(item).replace(amount,discount);
            else{
                this.itemsMapDiscount.get(item).put(amount,discount);
            }}
        else{
            Map<Integer,Double> map =new HashMap<Integer, Double>() ;
            map.put(amount,discount);
            this.itemsMapDiscount.put(item,map);
            }



    }
    //remove an item from the contract
    public void remove_by_item(Item item) {
        this.itemsMapDiscount.remove(item);
    }
    //remove an amount from the items contract
    public void remove_by_amount(Item item ,int amount){
        this.itemsMapDiscount.get(item).remove(amount);
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }
    public void setTotalDiscount(double total_discount) {
        this.total_discount = total_discount;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public void setSupplierId(String supplierID)
    {
        this.supplierId = supplierID;
    }
    public void setContractIdCounter(int num)
    {
        contractIdCounter = num + 1;
    }

    public int getContractId() {
        return contractId;
    }

    public String getSupplierId() {
        return supplierId;
    }
}
