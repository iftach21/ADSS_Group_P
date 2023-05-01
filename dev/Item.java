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
        Connection conn = null;
        try
        {
            String url = "jdbc:sqlite:dev/res/SuperLeeDataBase.db";
            conn = DriverManager.getConnection(url);
        }

        catch(SQLException ignored){}

        finally
        {
            try
            {
                if(conn != null)
                {
                    conn.close();
                }
            }
            catch(SQLException ignored){}
        }

        if (itemMapper == null){
            itemMapper = new ItemMapper(conn);}

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
    }


    public String getCatalogNum() {
        return catalogNum;
    }

    public void setCatalogNum(String catalogNum) {
        this.catalogNum = catalogNum;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
    }



    public TempLevel getTemperature() {
        return temperature;
    }


    public void setTemperature(TempLevel temperature) {
    this.temperature = temperature;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
}

    public void print_item(){
        System.out.println("name:"+this.name);
        System.out.println("manufacturer :"+ this.manufacturer);
        System.out.println("catalog number : " +this.catalogNum);
        System.out.println("weight: "+this.weight );
        System.out.println("catalog Name :" +this.catalogName );
        System.out.println("temperature :"+ this.temperature);
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public List<PriceHistory> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceHistory> priceHistory) {
        this.priceHistory = priceHistory;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
    }

    public int getMinimum_quantity() {
        return minimum_quantity;
    }

    public void setMinimum_quantity(int minimum_quantity) {
        this.minimum_quantity = minimum_quantity;
        try {
            itemMapper.update(this);
        }
        catch(SQLException ignored){}
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", catalogNum='" + catalogNum + '\'' +
                ", weight=" + weight +
                ", catalogName='" + catalogName + '\'' +
                ", temperature=" + temperature +
                ", priceHistory=" + priceHistory +
                ", manufacturer='" + manufacturer + '\'' +
                ", minimum_quantity=" + minimum_quantity +
                '}';
    }
}
