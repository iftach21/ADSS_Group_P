package DataAccesObject;
import Domain.*;
import DataAccesObject.FixedDaySupplierMapper;
import DataAccesObject.NonFixedDaySupplierMapper;

import org.json.JSONObject;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import Domain.*;
/**
 * This is the mapper class for the Order class
 **/
public class OrderMapper
{
    private Connection conn;
    private final Map<Integer, Order> cache;

    //This is the constructor for the class, it doesnt get any arguments and only initialize the cache

    public OrderMapper()
    {
        this.cache = new HashMap<>();
    }

    //This function finds a Order in the DB by the orderNum
    public Order findByOrderNum(String orderNum)
    {
        //First we check if we have this FixedDaySupplier in the cache
        if(cache.containsKey(orderNum))
        {
            return cache.get(orderNum);
        }
        PreparedStatement stmt;
        ResultSet rs;
        String itemsFromTable;
        Map<String, Pair<Integer,Float>> itemIdMap;
        getConnection(); // The function that gets us the connection to the DB

        //If it doesnt exist in the cache we check if it exists in the DB
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM Orders WHERE order_num = ?"); //The SQL query that we use to find the Order
            stmt.setString(1, orderNum);
            rs = stmt.executeQuery();
            if (rs.next()) // if we found the Order in the DB we build the Order class instance
            {
                Order order = new Order();
                int orderNumCache = rs.getInt("order_num");
                order.setOrderNum(orderNumCache);
//                order.setItemList(Parser.parse(rs.getString("item_list")));
                itemsFromTable = rs.getString("item_list");
                itemIdMap = Parser.parse(itemsFromTable); // TODO build the needed mapper
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("statusOrder")));
                conn.close();
                order.setItemList(getItems(itemIdMap));
                order.setSupplier(findSupplier(supplierId));

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



    //This function gives all the Order that are currently in the DB
    public List<Order> findAll()
    {
        List<Order> orders = new ArrayList<>();// The list that will hold all the Order that we will return
        List<Map<String, Pair<Integer,Float>>> itemsIdMap = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        List<String> suppliersId = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT * FROM Orders"); //The SQL query that we use to get all the NonDeliveringSuppliers in the DB
            rs = stmt.executeQuery();
            while (rs.next()) //If there are any Orders in the DB we create an instance for each and one of them
            {
                Order order = new Order();
                order.setOrderNum(rs.getInt("order_num"));
//                order.setItemList(Parser.parse(rs.getString("item_list")));
                String itemsFromTable = rs.getString("item_list");
                itemsIdMap.add(Parser.parse(itemsFromTable)); // TODO build needed parser
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                suppliersId.add(supplierId);
//                order.setSupplier(findSupplier(supplierId));
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("statusOrder")));
//                cache.put(rs.getInt("order_num"), order);
                orders.add(order);
            }
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

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

        return orders; // we return all the Order that we found
    }

    //This function gives all the Orders with a specific status that are currently in the DB

        public List <Order> findAllOrderWithStatus(String status)
    {
        List<Order> orders = new ArrayList<>();// The list that will hold all the Order that we will return
        List<String> suppliersId = new ArrayList<>();
        List<Map<String, Pair<Integer,Float>>> itemsIdMap = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM Orders WHERE statusOrder = ?"); //The SQL query that we use to get all the Order in the DB with the matching status
            stmt.setString(1, status);
            rs = stmt.executeQuery();
            while (rs.next()) //If there are any Order in the DB we create an instance for each and one of them
            {
                Order order = new Order();
                order.setOrderNum(rs.getInt("order_num"));
//                order.setItemList(Parser.parse(rs.getString("item_list")));
                String itemsFromTable = rs.getString("item_list");
                itemsIdMap.add(Parser.parse(itemsFromTable)); // TODO build needed parser
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                suppliersId.add(supplierId);
//                order.setSupplier(findSupplier(supplierId));
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("statusOrder")));
                orders.add(order);
            }

        }
        catch (SQLException e){}

        try
        {
            conn.close();
        }
        catch (SQLException e){}

        for(int i = 0; i < orders.size(); i++)
        {
            Map<Item, Pair<Integer,Float>> itemsMap = getItems(itemsIdMap.get(i));
            orders.get(i).setItemList(itemsMap);
        }

        for(int i = 0; i < orders.size(); i++)
        {
            orders.get(i).setSupplier(findSupplier(suppliersId.get(i)));
        }

        return orders; // we return all the Orders that we found
    }

    //This function lets us insert a new Order in the system
    public void insert(Order order)
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
            stmt = conn.prepareStatement("INSERT INTO Orders(order_num,supplier_id,item_list,cost,store_number,statusOrder)VALUES (?,?, ?, ?, ?, ?)");//This is the SQL query that we use to insert a new Order into the DB
            stmt.setInt(1, order.getOrderNum());
            Supplier supplier = order.getSupplier();
            stmt.setString(2, supplier.getSupplierID());
//            String itemsJson = new JSONObject(order.getItemList()).toString();
            String itemsJson = new JSONObject(insertItem).toString();
            stmt.setString(3, itemsJson);
            stmt.setFloat(4, order.getCost());
            stmt.setInt(5, order.getStore_number());
            stmt.setString(6, order.getStatusOrder().toString());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {

                cache.put(order.getOrderNum(), order);//We insert the new Order into the cache
            }
        }
        catch (SQLException e)
        {


        }

        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This function lets us update a value that is already in the DB
    public void update(Order order)
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

        try {
            stmt = conn.prepareStatement("UPDATE Orders SET supplier_id = ?,  item_list = ?, cost = ?, store_number = ?, statusOrder = ? WHERE order_num = ?");//This is the SQL query that we use for updating a value
            stmt.setString(1, order.getSupplier().getSupplierID());
//            String itemsJson = new JSONObject(order.getItemList()).toString();
            String itemsJson = new JSONObject(insertItem).toString();
            stmt.setString(2, itemsJson);
            stmt.setFloat(3, order.getCost());
            stmt.setInt(4, order.getStore_number());
            stmt.setString(5, order.getStatusOrder().toString());
            stmt.setInt(6, order.getOrderNum());

            stmt.executeUpdate();
            cache.remove(order.getOrderNum());
            cache.put(order.getOrderNum(), order);// TODO check if there is no duplication after update in the cache
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This function lets us delete a value from the DB
    public void delete(Order order)
    {
        PreparedStatement stmt;
        getConnection();
        try {
            stmt = conn.prepareStatement("DELETE FROM Orders WHERE order_num = ?");//This is the SQL query that we use for deleting a value
            stmt.setInt(1, order.getOrderNum());
            stmt.executeUpdate();
            cache.remove(order.getOrderNum());
        }
        catch (SQLException e)
        {

        }
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
                return nonDeliveringSupplier;
            }
            NonFixedDaySupplierMapper nonFixedDaySupplierMapper = new NonFixedDaySupplierMapper();

            if (flag == 0) {
                NonFixedDaySupplier nonFixedDaySupplier = nonFixedDaySupplierMapper.findBySupplierId(supplierId);
                if (nonFixedDaySupplier == null)
                {
                    FixedDaySupplierMapper fixedDaySupplierMapper = new FixedDaySupplierMapper();

                    FixedDaySupplier fixedDaySupplier = fixedDaySupplierMapper.findBySupplierId(supplierId);
                    return fixedDaySupplier;
                }
                else
                {
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
            stmt = conn.prepareStatement("DELETE FROM Orders"); // SQL query to delete all rows from the table
            stmt.executeUpdate();
            cache.clear(); // Clear the cache
        } catch (SQLException e) {

        }

        try {
            conn.close();
        } catch (SQLException e) {

        }
    }


    public String getAllOrdersAsString() {
        List<Order> orders = findAll(); // Get all the orders from the database

        // Create a StringBuilder to build the string representation
        StringBuilder sb = new StringBuilder();

        // Append column headers


        // Append each order's details
        for (Order order : orders) {
            sb.append(order.getOrderNum()).append("\t");
            sb.append(order.getSupplier().getSupplierID()).append("\t");
            sb.append(order.getCost()).append("\t");
            sb.append(order.getStore_number()).append("\t");
            sb.append(order.getStatusOrder()).append("\n");
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}

        return sb.toString();
    }
}
