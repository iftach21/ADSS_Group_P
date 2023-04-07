import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.util.List;

public class Item {
    private String name;
    private String catalogNum;
    private double weight;
    private String manufacturer;
    private int minQuantity;
    private int amount = 0;
    private TempLevel temperature;

    private List<specificItem> specificItemList;


    public Item(String name, String catalogNum, double weight, String manufacturer, TempLevel temperature, int minQuantity)
    {
        this.name = name;
        this.catalogNum = catalogNum;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.temperature = temperature;
        this.minQuantity = minQuantity;
        this.specificItemList = new ArrayList<specificItem>();
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public int getAmount() {
        return amount;
    }

    //Method 1: toString
    //This method prints the general items list
    @Override
    public String toString() {
        String generalItem = "General Item : " +
                "name: " + name + '\'' +
                ", catalogNum: '" + catalogNum + '\'' +
                ", weight: " + weight +
                ", manufacturer: '" + manufacturer + '\'' +
                ", minQuantity: " + minQuantity +
                ", amount: " + amount +
                ", temperature: " + temperature;
        for (int i = 0; i < this.specificItemList.size(); i++){
            generalItem += "\n" + this.specificItemList.get(i);
        }
        return generalItem;
    }

    //Method 2: addSpecificItem
    //This method receives a specific item, and adds it into the list of general items.
    //I.e. The general item is 'Milk 3%', and the specific item is one specific bottle
    public void addSpecificItem(specificItem item) {
        this.amount ++;
        specificItemList.add(item);
    }

    //Method 3: removeSpecificItem
    //This method removes a specific item from the specificItemsList
    public void removeSpecificItem(specificItem item) {
        amount --;
        specificItemList.remove(item);
    }
}