package DataAccesObject;
import Domain.*;
import DataAccesObject.FixedDaySupplierMapper;
import DataAccesObject.NonFixedDaySupplierMapper;

import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Domain.*;
import Domain.*;

/**
 * This is the mapper class for the PeriodicOrder class
 **/
public class PeriodicOrderMapper {
    private Connection conn;
    private Map<Integer, Period_Order> cache;

    //This is the constructor for the class, it doesnt get any arguments and only initialize the cache

    public PeriodicOrderMapper()
    {
        this.cache = new HashMap<>();
    }

    //This function finds a Period_Order in the DB by the orderNum

    public Period_Order findByContractId(String orderNum)
    {
        //First we check if we have this Period_Order in the cache

        if(cache.containsKey(orderNum))
        {
            return cache.get(orderNum);
        }

        PreparedStatement stmt;
        ResultSet rs;
        String itemsFromTable;
        Map<String, Pair<Integer,Float>> itemIdMap;
        getConnection();// The function that gets us the connection to the DB

        //If it doesnt exist in the cache we check if it exists in the DB

        try
        {
            stmt = conn.prepareStatement("SELECT * FROM PeriodicOrders WHERE order_num = ?");//The SQL query that we use to find the Order
            stmt.setString(1, orderNum);
            rs = stmt.executeQuery();
            if (rs.next())// if we found the Order in the DB we build the Period_Order class instance
            {
                Period_Order order = new Period_Order();
                int orderNumCache = rs.getInt("order_num");
                order.setOrderNum(orderNumCache);
//                order.setItemList(Parser.parse(rs.getString("item_list")));
                itemsFromTable = rs.getString("item_list");
                itemIdMap = Parser.parse(itemsFromTable); // TODO build the needed mapper
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                order.setSupplier(findSupplier(supplierId));
                order.setDays_to_cycle(rs.getInt("days_to_cycle"));
                order.setDay_left(rs.getInt("day_left"));
                conn.close();
                order.setSupplier(findSupplier(supplierId));
                order.setItemList(getItems(itemIdMap));

                cache.put(orderNumCache, order);

                return order;
            }
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return null;  // if it wasnt found in the DB or in the cache
    }

    //This function gives all the Period_Orders that are currently in the DB
    public List<Period_Order> findAll()
    {
        List<Period_Order> orders = new ArrayList<>();// The list that will hold all the Period_Orders that we will return
        List<String> suppliersId = new ArrayList<>();
        List<Map<String, Pair<Integer,Float>>> itemsIdMap = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM PeriodicOrders");//The SQL query that we use to get all the Period_Orders in the DB
            rs = stmt.executeQuery();
            while (rs.next()) //If there are any Period_Order in the DB we create an instance for each and one of them
            {
                Period_Order order = new Period_Order();
                order.setOrderNum(rs.getInt("order_num"));
//                order.setItemList(Parser.parse(rs.getString("item_list")));
                String itemsFromTable = rs.getString("item_list");
                itemsIdMap.add(Parser.parse(itemsFromTable)); // TODO build needed parser
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                suppliersId.add(supplierId);
//                order.setSupplier(findSupplier(supplierId));
                order.setDays_to_cycle(rs.getInt("days_to_cycle"));
                order.setDay_left(rs.getInt("day_left"));

//                cache.put(rs.getInt("order_num"), order);
                orders.add(order);
            }
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){

        }

        for(int i = 0; i < orders.size(); i++)
        {
            Map<Item, Pair<Integer,Float>> itemsMap = getItems(itemsIdMap.get(i));
            orders.get(i).setItemList(itemsMap);
        }
        for(int i = 0; i < orders.size(); i++)
        {
            orders.get(i).setSupplier(findSupplier(suppliersId.get(i)));
            cache.put(orders.get(i).getOrderNum(),orders.get(i));

        }
        return orders;
    }

    //This function lets us insert a new Order in the system

    public void insert(Period_Order order)
    {
        PreparedStatement stmt;
        getConnection();

        Map<String, Pair<Integer,Float>> insertItem = new HashMap<>();

        for(Map.Entry<Item, Pair<Integer,Float>> entry : order.getItemList().entrySet())
        {
            String key = entry.getKey().getCatalogNum();
            Pair<Integer,Float> pair = entry.getValue();
            insertItem.put(key,pair);
        }

        try
        {
            stmt = conn.prepareStatement("INSERT INTO PeriodicOrders(order_num, supplier_id, item_list, cost, store_number, days_to_cycle, day_left)VALUES (?, ?, ?, ?, ?, ?, ?)");//This is the SQL query that we use to insert a new Period_Order into the DB
            stmt.setInt(1, order.getOrderNum());
            Supplier supplier = order.getSupplier();
            stmt.setString(2, supplier.getSupplierID());
//            String itemsJson = new  JSONObject(order.getItemList()).toString();
            String itemsJson = new JSONObject(insertItem).toString();
            stmt.setString(3, itemsJson);
            stmt.setFloat(4, order.getCost());
            stmt.setInt(5, order.getStore_number());
            stmt.setInt(6, order.getDays_to_cycle());
            stmt.setInt(7, order.getDay_left());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                order.setOrderNum(rs.getInt(1));
                cache.put(order.getOrderNum(), order); //We insert the new Order into the cache
            }
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This function lets us update a value that is already in the DB
    public void update(Period_Order order)
    {
        PreparedStatement stmt;
        getConnection();
        Map<String, Pair<Integer,Float>> insertItem = new HashMap<>();

        for(Map.Entry<Item, Pair<Integer,Float>> entry : order.getItemList().entrySet())
        {
            String key = entry.getKey().getCatalogNum();
            Pair<Integer,Float> pair = entry.getValue();
            insertItem.put(key,pair);
        }
        try
        {
            stmt = conn.prepareStatement("UPDATE PeriodicOrders SET supplier_id = ?,  item_list = ?, cost = ?, store_number = ?, days_to_cycle = ?, day_left = ? WHERE order_num = ?");//This is the SQL query that we use for updating a value
            stmt.setString(1, order.getSupplier().getSupplierID());
//            String itemsJson = new JSONObject(order.getItemList()).toString();
            String itemsJson = new JSONObject(insertItem).toString();
            stmt.setString(2, itemsJson);
            stmt.setFloat(3, order.getCost());
            stmt.setInt(4, order.getStore_number());
            stmt.setInt(5, order.getDays_to_cycle());
            stmt.setInt(6, order.getDay_left());
            stmt.setInt(7, order.getOrderNum());
            cache.remove(order.getOrderNum());
            cache.put(order.getOrderNum(), order);
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This function lets us delete a value from the DB

    public void delete(Period_Order order)
    {
        PreparedStatement stmt;
        getConnection();
        try
        {
            stmt = conn.prepareStatement("DELETE FROM PeriodicOrders WHERE order_num = ?"); //This is the SQL query that we use for deleting a value
            stmt.setInt(1, order.getOrderNum());
            stmt.executeUpdate();
            cache.remove(order.getOrderNum());
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This helper function lets us find the supplier of the contract using the supplier ID
    // we check for each mapper if the id exist in their table and if it does we get that supplier and return it
    private Supplier findSupplier(String supplierId)
    {
        try {
            int flag = 0;
            NonDeliveringSupplierMapper nonMapper = new NonDeliveringSupplierMapper();
            NonDeliveringSupplier nonDeliveringSupplier = nonMapper.findBySupplierId(supplierId);
            if (nonDeliveringSupplier != null) {
                flag++;
                return nonDeliveringSupplier;
            }
            NonFixedDaySupplierMapper nonFixedDaySupplierMapper = new NonFixedDaySupplierMapper();

            if (flag == 0) {
                NonFixedDaySupplier nonFixedDaySupplier = nonFixedDaySupplierMapper.findBySupplierId(supplierId);
                if (nonFixedDaySupplier == null) {
                    FixedDaySupplierMapper fixedDaySupplierMapper = new FixedDaySupplierMapper();

                    FixedDaySupplier fixedDaySupplier = fixedDaySupplierMapper.findBySupplierId(supplierId);
                    return fixedDaySupplier;
                } else {
                    return nonFixedDaySupplier;

                }
            }
        }
        catch (Exception e){}
        return null;
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

    private Map<Item, Pair<Integer,Float>> getItems(Map<String, Pair<Integer,Float>> itemIdMap)
    {
        ItemMapper itemMapper = new ItemMapper();
        Map<Item, Pair<Integer,Float>> itemList = new HashMap<>();
        for(Map.Entry<String, Pair<Integer,Float>> entry : itemIdMap.entrySet())
        {
            String key = entry.getKey();
            Pair<Integer,Float> value = entry.getValue();
            Item item = itemMapper.findByCatalogNum(key);
            itemList.put(item,value);
        }
        return itemList;
    }
    public void deleteAll() {
        PreparedStatement stmt;
        getConnection();
        try {
            stmt = conn.prepareStatement("DELETE FROM PeriodicOrders");
            // This is the SQL query that we use for deleting all values
            stmt.executeUpdate();
            cache.clear();
        } catch (SQLException e) {

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
    }


    public String getTableString() {
        List<Period_Order> orders = findAll();
        StringBuilder tableString = new StringBuilder();

        // Append table headers


        // Append order data
        for (Period_Order order : orders) {
            tableString.append(order.getOrderNum()).append("\t")
                    .append(order.getSupplier().getSupplierID()).append("\t")
                    .append(order.getCost()).append("\t")
                    .append(order.getStore_number()).append("\t")
                    .append(order.getDays_to_cycle()).append("\t")
                    .append(order.getDay_left()).append("\n");
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}

        return tableString.toString();
    }
}
