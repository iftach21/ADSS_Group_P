import java.util.Date;
import java.time.Instant;
import java.util.List;

public class Item {
    private String name;
    private String catalogNum;
    private double weight;
    private String catalogName;
    private int minQuantity;
    private int amount = 0;
    private TempLevel temperature;

    private List<specificItem> specificItemList;


    public Item(String name, String catalogNum, double weight, String catalogName, TempLevel temperature, int minQuantity)
    {
        this.name = name;
        this.catalogNum = catalogNum;
        this.weight = weight;
        this.catalogName = catalogName;
        this.temperature = temperature;
        this.minQuantity = minQuantity;
        this.specificItemList = specificItemList;

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


}