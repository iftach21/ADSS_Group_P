package DataAccesObject;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import Domain.*;

/**
 * This is the mapper class for the Item class
 **/
public class ItemMapper {
    private Connection conn;
    private final Map<String, Item> cache;
    //This is the constructor for the class, it doesnt get any arguments and only initialize the cache

    public ItemMapper()
    {

        this.cache = new HashMap<>();
    }
    //This function finds a Item in the DB by the catalogNum

    public Item findByCatalogNum(String catalogNum)
    {
        //First we check if we have this Item in the cache

        if(cache.containsKey(catalogNum))
        {
            return cache.get(catalogNum);
        }
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();// The function that gets us the connection to the DB
        //If it doesnt exist in the cache we check if it exists in the DB
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM items WHERE catalog_number = ?");//The SQL query that we use to find the Item
            stmt.setString(1, catalogNum);
            rs = stmt.executeQuery();
            if (rs.next()) // if we found the FixedDaySupplier in the DB we build the Item class instance
            {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setCatalogNum(rs.getString("catalog_number"));
                item.setWeight(rs.getDouble("weight"));
                item.setCatalogName(rs.getString("catalog_name"));
                String tempLevel = rs.getString("temperature");
                TempLevel tempValue = TempLevel.valueOf(tempLevel);
                item.setTemperature(tempValue);
                item.setManufacturer((rs.getString("manufacturer")));
                String price_his= ((rs.getString("price_history")));
                item.setMinQuantity(rs.getInt("minimum_quantity"));
                PriceHistoryParser parser=new PriceHistoryParser();
                if(!price_his.equals("[]")){
                    item.setPriceHistory(parser.parse(price_his));}
                cache.put(catalogNum, item);
                conn.close();
                return item;
            }
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

        return null; // if it wasnt found in the DB or in the cache
    }

    //This function gives all the Item that are currently in the DB
    public List<Item> findAll()
    {
        List<Item> items = new ArrayList<>(); // The list that will hold all the Items that we will return
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM items"); //The SQL query that we use to get all the Item in the DB
            rs = stmt.executeQuery();
            while (rs.next())//If there are any Item in the DB we create an instance for each and one of them
            {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setCatalogNum(rs.getString("catalog_number"));
                item.setWeight(rs.getDouble("weight"));
                item.setCatalogName(rs.getString("catalog_name"));
                String tempLevel = rs.getString("temperature");
                TempLevel tempValue = TempLevel.valueOf(tempLevel);
                item.setTemperature(tempValue);
                item.setMinQuantity(rs.getInt("minimum_quantity"));
                item.setManufacturer((rs.getString("manufacturer")));
                String price_his= ((rs.getString("price_history")));
                PriceHistoryParser parser=new PriceHistoryParser();
                if(!price_his.equals("[]")){
                item.setPriceHistory(parser.parse(price_his));}
                cache.put(item.getCatalogNum(), item);
                items.add(item);
            }
        }
        catch(SQLException e){}

        try
        {
            conn.close();
        }

        catch (SQLException e){}
        return items; // we return all the items that we found
    }

    //This function lets us insert a new Item in the system
    public void insert(Item item)
    {
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        try {
            stmt = conn.prepareStatement("INSERT INTO items(catalog_number,name,weight,catalog_name,temperature,minimum_quantity,price_history,manufacturer)VALUES (?, ?, ?, ?, ?, ?, ?, ?)");//This is the SQL query that we use to insert a new item into the DB
            stmt.setString(1, item.getCatalogNum());
            stmt.setString(2, item.getName());
            stmt.setString(3, Double.toString(item.getWeight()));
            stmt.setString(4, item.getCatalogName());
            stmt.setString(5, item.getTemperature().name());
            stmt.setString(6, Integer.toString(item.getMinimum_quantity()));
            stmt.setString(7, item.getPriceHistory().toString());
            stmt.setString(8, item.getManufacturer());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                cache.put(item.getCatalogNum(), item); //We insert the new item into the cache
           }
        }
        catch(SQLException e){}

        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This function lets us update a value that is already in the DB

    public void update(Item item)
    {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("UPDATE items SET name = ?, weight = ?,  catalog_name = ?, temperature = ?, minimum_quantity = ?, price_history = ?, manufacturer = ? WHERE catalog_number = ?");//This is the SQL query that we use for updating a value
            stmt.setString(1, item.getName());
            stmt.setString(2, Double.toString(item.getWeight()));
            stmt.setString(3, item.getCatalogName());
            stmt.setString(4, item.getTemperature().name());
            stmt.setString(5, Integer.toString(item.getMinimum_quantity()));
            stmt.setString(6, item.getPriceHistory().toString());
            stmt.setString(7, item.getManufacturer());
            stmt.setString(8, item.getCatalogNum());

            stmt.executeUpdate();

            cache.remove(item.getCatalogNum());
            cache.put(item.getCatalogNum(), item); // TODO check if there is no duplication after update in the cache
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
    }

    //This function lets us delete a value from the DB

    public void delete(Item item)
    {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("DELETE FROM items WHERE catalog_number = ?");//This is the SQL query that we use for deleting a value
            stmt.setString(1, item.getCatalogNum());
            stmt.executeUpdate();
            cache.remove(item.getCatalogNum());
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }
    public void deleteAll() {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("DELETE FROM items");
            stmt.executeUpdate();
            cache.clear();
        } catch (SQLException e) {
//            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
    }

    public List<Item> findAllByCatalogNum(String catalogNum)
    {
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        List<Item> items = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT * FROM items WHERE catalog_number = ?");
            stmt.setString(1, catalogNum);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setCatalogNum(rs.getString("catalog_number"));
                item.setWeight(rs.getDouble("weight"));
                item.setCatalogName(rs.getString("catalog_name"));
                String tempLevel = rs.getString("temperature");
                TempLevel tempValue = TempLevel.valueOf(tempLevel);
                item.setTemperature(tempValue);
                item.setManufacturer((rs.getString("manufacturer")));
                items.add(item);

            }
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return items;
    }

    //This helper function gives us a connection to the DB
    private void getConnection()
    {
        try
        {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        }
        catch (SQLException e){}
    }

}
