import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Item(String name, String catalogNum, double weight, String catalogName, TempLevel temperature, String manufacturer) {
        this.name = name;
        this.catalogNum = catalogNum;
        this.weight = weight;
        this.catalogName = catalogName;
        this.temperature = temperature;
        this.priceHistory = new ArrayList<>();
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Item(String name, String catalogNum, String expirationDate, double weight, String catalogName, double temperature)
//    {
//        this.name = name;
//        this.catalogNum = catalogNum;
//        this.expirationDate = expirationDate;
//        this.weight = weight;
//        this.catalogName = catalogName;
//        this.temperature = temperature;
//    }

    public String getCatalogNum() {
        return catalogNum;
    }

    public void setCatalogNum(String catalogNum) {
        this.catalogNum = catalogNum;
    }

//    public String getDateStr() {
//        return this.expirationDate;
//    }
//    public Date getDate() throws Exception {
//        return dateFormat.parse(expirationDate);
//    }

//    public void setDate(Date date) {
//        this.expirationDate = dateFormat.format(date);
//    }

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

//    public double getTemperature() {
//        return temperature;
//    }

    public TempLevel getTemperature() {
        return temperature;
    }

//    public void setTemperature(double temperature) {
//        this.temperature = temperature;
//    }
    public void setTemperature(TempLevel temperature) {
    this.temperature = temperature;
}

    public void print_item(){
        System.out.println("name:"+this.name);
        System.out.println("manufacturer :"+ this.manufacturer);
        System.out.println("catalog number : " +this.catalogNum);
//        System.out.println("expirationDate :" +this.expirationDate);
        System.out.println("weight: "+this.weight );
        System.out.println("catalog Name :" +this.catalogName );
        System.out.println("temperature :"+ this.temperature);

    }
}
