import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
    private String name;
    private String catalogNum;
    private SimpleDateFormat expirationDate;
    private double weight;
    private String catalogName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private double temperature;

    public Item(String name, String catalogNum, SimpleDateFormat expirationDate, double weight, String catalogName, double temperature)
    {
        this.name = name;
        this.catalogNum = catalogNum;
        this.expirationDate = expirationDate;
        this.weight = weight;
        this.catalogName = catalogName;
        this.temperature = temperature;
    }

    public String getCatalogNum() {
        return catalogNum;
    }

    public void setCatalogNum(String catalogNum) {
        this.catalogNum = catalogNum;
    }

    public SimpleDateFormat getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(SimpleDateFormat expirationDate) {
        this.expirationDate = expirationDate;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
