package DataAccesObject;

import DataAccesObject.ContractMapper;
import org.json.JSONObject;
//import org.json.JSONObject;

import java.sql.*;
import java.util.*;
import Domain.*;

/**
 * This is the mapper class for the FixedDaySupplier class
 **/
public class FixedDaySupplierMapper{
    private Connection conn;
    private final Map<String, FixedDaySupplier> cache;

    //This is the constructor for the class, it doesnt get any arguments and only initialize the cache
    public FixedDaySupplierMapper() {
        this.cache = new HashMap<>();
    }
    //This function finds a FixedDaySupplier in the DB by the supplierID
    public FixedDaySupplier findBySupplierId(String supplierID)
    {
        //First we check if we have this FixedDaySupplier in the cache

        if (cache.containsKey(supplierID))
        {
            return cache.get(supplierID);
        }

        PreparedStatement stmt;
        ResultSet rs;
        Map<String, Pair<Integer,Float>> itemIdMap;
        getConnection();// The function that gets us the connection to the DB

        //If it doesnt exist in the cache we check if it exists in the DB
        try {
            stmt = conn.prepareStatement("SELECT * FROM FixedDaySuppliers WHERE supplier_ID = ?");//The SQL query that we use to find the FixedDaySupplier
            stmt.setString(1, supplierID);
            rs = stmt.executeQuery();
            if (rs.next()) // if we found the FixedDaySupplier in the DB we build the FixedDaySupplier class instance
            {
                ContractMapper contractMapper = new ContractMapper();
                Contract contract;
                contract = contractMapper.findBySupplierId(supplierID);
                ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
                String itemsMapJson = rs.getString("items");
                itemIdMap = Parser.parse(itemsMapJson); // TODO build the needed mapper
                int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
//                Map<Item, Pair<Integer, Float>> map = Parser.parse(itemsMapJson);
                String currentDay = rs.getString("currentDeliveryDay");
                WindowType currentDeliveryDay = WindowType.valueOf(currentDay);
                FixedDaySupplier fixedDaySupplier = new FixedDaySupplier(currentDeliveryDay, rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, null);
                try
                {
                    conn.close();
                }
                catch (SQLException e){}
                fixedDaySupplier.setItems(getItems(itemIdMap));
                cache.put(supplierID, fixedDaySupplier);
                return fixedDaySupplier;
            }
        }
        catch (SQLException e)
        {}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return null;  // if it wasnt found in the DB or in the cache
    }

    //This function gives all the FixedDaySupplier that are currently in the DB
    public List<FixedDaySupplier> findAll()
    {
        List<FixedDaySupplier> suppliers = new ArrayList<>();// The list that will hold all the FixedDaySupplier that we will return
        List<Map<String, Pair<Integer,Float>>> itemsIdMap = new ArrayList<>();
        List<String> suppliersId = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM FixedDaySuppliers");//The SQL query that we use to get all the FixedDaySupplier in the DB
            rs = stmt.executeQuery();
            while (rs.next())//If there are any FixedDaySuppliers in the DB we create an instance for each and one of them
            {
                ContractMapper contractMapper = new ContractMapper();
                Contract contract;
                contract = contractMapper.findBySupplierId(rs.getString("supplier_ID"));
                suppliersId.add(rs.getString("supplier_ID"));

                ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
                String itemsMapJson = rs.getString("items");
                itemsIdMap.add(Parser.parse(itemsMapJson)); // TODO build needed parser

                int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
//                Map<Item, Pair<Integer, Float>> map = Parser.parse(itemsMapJson);
                String currentDay = rs.getString("currentDeliveryDay");
                WindowType currentDeliveryDay = WindowType.valueOf(currentDay);
                FixedDaySupplier fixedDaySupplier = new FixedDaySupplier(currentDeliveryDay, rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, null);
//                cache.put(rs.getString("supplier_ID"), fixedDaySupplier);
                suppliers.add(fixedDaySupplier);
            }
        }
        catch (SQLException e)
        {}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

        for(int i = 0; i < suppliers.size(); i++)
        {
            Map<Item, Pair<Integer,Float>> itemsMap = getItems(itemsIdMap.get(i));
            suppliers.get(i).setItems(itemsMap);
            cache.put(suppliers.get(i).getSupplierID(), suppliers.get(i));
        }

        return suppliers;// we return all the FixedDaySupplier that we found
    }

    //This function lets us insert a new FixedDaySupplier in the system
    public void insert(FixedDaySupplier fixedDaySupplier)
    {
        getConnection();
        PreparedStatement stmt;
        Map<String, Pair<Integer,Float>> insertItem = new HashMap<>();
        for(Map.Entry<Item, Pair<Integer,Float>> entry : fixedDaySupplier.getItems().entrySet())
        {
            String key = entry.getKey().getCatalogNum();
            Pair<Integer,Float> pair = entry.getValue();
            insertItem.put(key,pair);
        }

        try
        {
            stmt = conn.prepareStatement("INSERT INTO FixedDaySuppliers (business_id, name, currentDeliveryDay, payment_method, supplier_ID, contract_person_name, contract_phone_number, items, contract_id) VALUES (?,?, ?, ?, ?,?,?,?,?)");//This is the SQL query that we use to insert a new FixedDaySupplier into the DB
//            String itemsJson = new JSONObject(fixedDaySupplier.getItems()).toString();
            String itemsJson = new JSONObject(insertItem).toString();
            stmt.setString(1, fixedDaySupplier.getBusinessId());
            stmt.setString(2, fixedDaySupplier.getName());
            stmt.setString(3, fixedDaySupplier.getCurrentDeliveryDay().name());
            stmt.setString(4, (fixedDaySupplier.getPaymentMethod()).toString());
            stmt.setString(5, fixedDaySupplier.getSupplierID());
            stmt.setString(6, fixedDaySupplier.getPerson().getName());
            stmt.setString(7, fixedDaySupplier.getPerson().getPhoneNumber());
            stmt.setString(8, itemsJson);
            stmt.setString(9, String.valueOf(fixedDaySupplier.getContract().contractId));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cache.put(fixedDaySupplier.getSupplierID(), fixedDaySupplier);//We insert the new FixedDaySupplier into the cache
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
    public void update(FixedDaySupplier fixedDaySupplier)
    {
        getConnection();
        PreparedStatement stmt;
        Map<String, Pair<Integer,Float>> insertItem = new HashMap<>();

        for(Map.Entry<Item, Pair<Integer,Float>> entry : fixedDaySupplier.getItems().entrySet())
        {
            String key = entry.getKey().getCatalogNum();
            Pair<Integer,Float> pair = entry.getValue();
            insertItem.put(key,pair);
        }
        try {
            stmt = conn.prepareStatement("UPDATE FixedDaySuppliers SET business_id = ?,  name = ?, currentDeliveryDay = ? ,payment_method = ?, supplier_ID = ?, contract_person_name = ?, contract_phone_number = ?, items = ?, contract_id = ? WHERE supplier_ID = ?");//This is the SQL query that we use for updating a value
//            String itemsJson = new JSONObject(fixedDaySupplier.getItems()).toString();
            String itemsJson = new JSONObject(insertItem).toString();
            stmt.setString(1, fixedDaySupplier.getBusinessId());
            stmt.setString(2, fixedDaySupplier.getName());
            stmt.setString(3, fixedDaySupplier.getCurrentDeliveryDay().name());
            stmt.setString(4, (fixedDaySupplier.getPaymentMethod()).toString());
            stmt.setString(5, fixedDaySupplier.getSupplierID());
            stmt.setString(6, fixedDaySupplier.getPerson().getName());
            stmt.setString(7, fixedDaySupplier.getPerson().getPhoneNumber());
            stmt.setString(8, itemsJson);
            stmt.setString(9, String.valueOf(fixedDaySupplier.getContract().contractId));
            stmt.setString(10, String.valueOf(fixedDaySupplier.getSupplierID()));
            stmt.executeUpdate();

            cache.remove(fixedDaySupplier.getSupplierID());
            cache.put(fixedDaySupplier.getSupplierID(), fixedDaySupplier);
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This function lets us delete a value from the DB
    public void delete(FixedDaySupplier fixedDaySupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM FixedDaySuppliers WHERE supplier_ID = ?");//This is the SQL query that we use for deleting a value
            stmt.setString(1, fixedDaySupplier.getSupplierID());
            stmt.executeUpdate();
            cache.remove(fixedDaySupplier.getSupplierID());
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}
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
        getConnection();
        PreparedStatement stmt;

        try {
            // Delete all rows from NonDeliveringSuppliers table
            stmt = conn.prepareStatement("DELETE FROM FixedDaySuppliers");
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Clear cache
        cache.clear();
    }
    public String getAllSuppliersForJTable() {
        List<FixedDaySupplier> suppliers = findAll();
        StringBuilder stringBuilder = new StringBuilder();

        // Append column names

        // Append data for each supplier
        for (FixedDaySupplier supplier : suppliers) {
            stringBuilder.append(supplier.getSupplierID()).append(", ");
            stringBuilder.append(supplier.getName()).append(", ");
            stringBuilder.append(supplier.getBusinessId()).append(", ");
            stringBuilder.append(supplier.getPaymentMethod()).append(", ");
            stringBuilder.append(supplier.getPerson().getName()).append(", ");
            stringBuilder.append(supplier.getPerson().getPhoneNumber()).append(", ");
            stringBuilder.append(supplier.getCurrentDeliveryDay().name()).append("\n");
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}

        return stringBuilder.toString();
    }



}
