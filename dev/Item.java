import java.util.Date;

public class Item {
    private String name;
    private String catalogNum;
    private Date expirationDate;
    private double weight;
    private String catalogName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private double temperature;

    public Item(String name, String catalogNum, Date expirationDate, double weight, String catalogName, double temperature)
    {
        this.name = name;
        this.catalogNum = catalogNum;
        this.expirationDate = expirationDate;
        this.weight = weight;
        this.catalogName = catalogName;
        this.temperature = temperature;
    }
}
