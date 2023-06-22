package Domain;

import DataAccesObject.ItemMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Item {
    private String name;
    private String catalogNum;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
//    private String expirationDate;//
    private double weight;
    private String catalogName;
//    private double temperature;
    private TempLevel temperature;

    private List<PriceHistory> priceHistory;
    private String manufacturer;

    private int minimum_quantity;

    static ItemMapper itemMapper;


    public Item() {
        this.priceHistory = new ArrayList<>();
    }

    public Item(String name, String catalogNum, double weight, String catalogName, TempLevel temperature, String manufacturer) {
        this.name = name;
        this.catalogNum = catalogNum;
        this.weight = weight;
        this.catalogName = catalogName;
        this.temperature = temperature;
        this.priceHistory = new ArrayList<>();
        this.manufacturer = manufacturer;
        this.minimum_quantity = 0;

    }


    public Item(String name, String catalogNum, double weight, String catalogName, TempLevel temperature, String manufacturer, int minimumQuantity) {
        this.name = name;
        this.catalogNum = catalogNum;
        this.weight = weight;
        this.catalogName = catalogName;
        this.temperature = temperature;
        this.priceHistory = new ArrayList<>();
        this.manufacturer = manufacturer;
        this.minimum_quantity = minimumQuantity;
    }

    public Item(Item fatherItem) {
        this.name = fatherItem.name;
        this.catalogNum = fatherItem.catalogNum;
        this.weight = fatherItem.weight;
        this.catalogName = fatherItem.catalogName;
        this.temperature = fatherItem.temperature;
        this.priceHistory = fatherItem.priceHistory;
        this.manufacturer = fatherItem.manufacturer;
        this.minimum_quantity = fatherItem.minimum_quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCatalogNum() {
        return catalogNum;
    }

    public void setCatalogNum(String catalogNum) {
        this.catalogNum = catalogNum;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;

    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;

    }



    public TempLevel getTemperature() {
        return temperature;
    }


    public void setTemperature(TempLevel temperature) {
    this.temperature = temperature;

}

    public void print_item(){
        System.out.println("name:"+this.name);
        System.out.println("manufacturer :"+ this.manufacturer);
        System.out.println("catalog number : " +this.catalogNum);
        System.out.println("weight: "+this.weight );
        System.out.println("catalog Name :" +this.catalogName );
        System.out.println("temperature :"+ this.temperature);
        for(PriceHistory priceHistory1: this.priceHistory){
        System.out.println("Pricer History :" +priceHistory1.toString());}
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;

    }

    public String getManufacturer() {
        return manufacturer;
    }

    public List<PriceHistory> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceHistory> priceHistory) {
        this.priceHistory = priceHistory;

    }

    public int getMinimum_quantity() {
        return this.minimum_quantity;
    }

    public void setMinimum_quantity(int minimum_quantity) {
        this.minimum_quantity = minimum_quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", catalogNum='" + catalogNum + '\'' +
                ", weight=" + weight +
                ", catalogName='" + catalogName + '\'' +
                ", temperature=" + temperature +
                ", priceHistory=" + priceHistory.toString() +
                ", manufacturer='" + manufacturer + '\'' +
                ", minimum_quantity=" + minimum_quantity +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(catalogNum, item.catalogNum);
    }
    @Override
    public int hashCode() {
        return Objects.hash(catalogNum);
    }

    //This method updates the price, and adds it into the history
    public void addNewPrice(double buyPrice, double sellPrice){
        Date currentDate = new Date();
        PriceHistory currentPrice = new PriceHistory(buyPrice, sellPrice, currentDate);
        this.priceHistory.add(currentPrice);
    }

    public void addSpecificItem(specificItem s_Item) {
    }

    //TO DELETE
    public int getAmount() {
        return 0;
    }


    public int getMinQuantity() {
        return this.minimum_quantity;
    }

    public int getPriceHistorySize() {
        return this.priceHistory.size();
    }

    public Object getPriceHistorySpecific(int j) {
        return priceHistory.get(j);
    }

    public Discount getDiscount() {
        return null;
    }

    public double getBuyPrice() {
        int lastPriceHistory = this.priceHistory.size() - 1;
        return this.priceHistory.get(lastPriceHistory).getBuyPrice();
    }

    public double getSellPrice() {
        int lastPriceHistory = this.priceHistory.size() - 1;
        return this.priceHistory.get(lastPriceHistory).getSellPrice();
    }

    public Object getCategoryName() {
        return catalogName;
    }

    public void setMinQuantity(int minimum_quantity){
        this.minimum_quantity = minimum_quantity;
    }
}
