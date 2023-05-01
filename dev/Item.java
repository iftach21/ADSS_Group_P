import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
    private String name;
    private String catalogNum;
    private String categoryName;
    private double weight;
    private String manufacturer;
    private String catalogName; // MFMF Supplier
    private int minQuantity;
    private int amount = 0;
    private TempLevel temperature;
    private Discount discount;
    private List<specificItem> specificItemList;
    private List<PriceHistory> priceHistoryList;
    //static ItemMapper itemMapper; // MFMF

    public Item(String name, String catalogNum, double weight, String manufacturer, TempLevel temperature, int minQuantity, String catalogName)
    {
        this.name = name;
        this.catalogNum = catalogNum;
        this.weight = weight;
        // this.catalogName = catalogName; // MFMF
        this.manufacturer = manufacturer;
        this.temperature = temperature;
        this.minQuantity = minQuantity;
        this.specificItemList = new ArrayList<specificItem>();
        this.priceHistoryList = new ArrayList<PriceHistory>();
        this.discount = new Discount();
        this.categoryName = categoryName;
    }

    public Item(Item fatherItem) {
        this.name = fatherItem.name;
        this.catalogName = fatherItem.catalogName;
        this.manufacturer = fatherItem.manufacturer;
        this.temperature = fatherItem.temperature;
        this.minQuantity = fatherItem.minQuantity;
        this.specificItemList = fatherItem.specificItemList;
        this.priceHistoryList = fatherItem.priceHistoryList;
        this.categoryName = fatherItem.categoryName;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Discount getDiscount() {
        return discount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCatalogNum(String catalogNum) {
        this.catalogNum = catalogNum;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setTemperature(TempLevel temperature) {
        this.temperature = temperature;
    }

    public String getCatalogNum() {
        return catalogNum;
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

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public int getAmount() {
        return amount;
    }


    public int getPriceHistorySize() {
        return priceHistoryList.size();
    }

    public PriceHistory getPriceHistorySpecific(int index) {
        return priceHistoryList.get(index);
    }

    public specificItem getSpecificItemList(int index) {
        specificItem currentSpecificItem;
        return this.specificItemList.get(index);
    }

    // MFMF
    public void print_item(){
        System.out.println("name:"+this.name);
        System.out.println("manufacturer :"+ this.manufacturer);
        System.out.println("catalog number : " +this.catalogNum);
        System.out.println("weight: "+this.weight );
        System.out.println("catalog Name :" +this.catalogName );
        System.out.println("temperature :"+ this.temperature);
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
                ", temperature: " + temperature + " ----";

        int lastPriceHistory = this.priceHistoryList.size() - 1;

        generalItem += "\n" + "Sold for: " + this.priceHistoryList.get(lastPriceHistory).getSellPrice() +
                " Bought for: " + this.priceHistoryList.get(lastPriceHistory).getBuyPrice() +
        " Last price update: " + this.priceHistoryList.get(lastPriceHistory).getCurrentDate();
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

    //Method 4: addNewPrice
    //This method updates the price, and adds it into the history
    public void addNewPrice(double buyPrice, double sellPrice){
        Date currentDate = new Date();
        PriceHistory currentPrice = new PriceHistory(buyPrice, sellPrice, currentDate);
        this.priceHistoryList.add(currentPrice);
    }

    public double getSellPrice(){
        int lastPriceHistory = this.priceHistoryList.size() - 1;
        return this.priceHistoryList.get(lastPriceHistory).getSellPrice();
    }

    public double getBuyPrice()
    {
        int lastPriceHistory = this.priceHistoryList.size() - 1;
        return this.priceHistoryList.get(lastPriceHistory).getBuyPrice();
    }
}