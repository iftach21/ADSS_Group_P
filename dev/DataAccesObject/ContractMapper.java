package DataAccesObject;
import Domain.*;

import java.io.StringReader;
import java.sql.*;
import java.util.*;

import Domain.Item;
import com.google.gson.reflect.TypeToken;
import org.json.*;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * This is the mapper class for the contract class
 **/
public class ContractMapper {
    private Connection conn;
    private final Map<Integer, Contract> cache;

    //This is the constructor for the class, it doesnt get any arguments and only initialize the cache
    public ContractMapper()
    {
        this.cache = new HashMap<>();
    }

    //This function finds a contract in the DB by the contract ID
    public Contract findByContractId(int contractId)
    {
        //First we check if we have this contract in the cache
        if(cache.containsKey(contractId))
        {
            return cache.get(contractId);
        }

        Map<String,Map<Integer,Double>> itemIdMap;
         //If it doesnt exist in the cache we check if it exists in the DB
        getConnection(); // The function that gets us the connection to the DB
        try
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contracts WHERE contract_id = ?");//The SQL query that we use to find the contract
            stmt.setInt(1,contractId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) // if we found the contract in the DB we build the contract class instance
            {
                Contract contract = new Contract();
                contract.setContractId(rs.getInt("contract_id"));
                contract.setSupplierId(rs.getString("supplier_id"));
                contract.setTotalDiscount(rs.getDouble("total_discount"));
                String itemsMapJson = rs.getString("items_Map_discount");
                itemIdMap = ParserForContractItemIntegerDouble.parse(itemsMapJson); // TODO build the needed mapper
//                contract.itemsMapDiscount = ParserForContractItemIntegerDouble.parse(itemsMapJson);
                conn.close();
                contract.itemsMapDiscount = getItems(itemIdMap);
                cache.put(contractId,contract);
                return contract;
            }
        }
        catch (Exception ignored){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return null; // if it wasnt found in the DB or in the cache
    }

    //This function lets us find a contract by its supplier ID
    public Contract findBySupplierId(String supplierID)
    {
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM Contracts WHERE supplier_id = ?");//The SQL query that we use to find the contract by the supplier ID
            stmt.setString(1, supplierID);
            rs = stmt.executeQuery();
            if (rs.next())// if we found the contract in the DB we build the contract class instance
            {
                //First we check if we have this contract in the cache
                if (cache.containsKey(rs.getInt("contract_id")))
                {
                    int contractId = rs.getInt("contract_id");
                    conn.close();
                    return cache.get(contractId);
                }
                Map<String,Map<Integer,Double>> itemIdMap;
                Contract contract = new Contract();
                contract.setContractId(rs.getInt("contract_id"));
                contract.setSupplierId(rs.getString("supplier_id"));
                contract.setTotalDiscount(rs.getDouble("total_discount"));
                String itemsMapJson = rs.getString("items_Map_discount");
                conn.close();
//                contract.itemsMapDiscount = ParserForContractItemIntegerDouble.parse(itemsMapJson);
                itemIdMap = ParserForContractItemIntegerDouble.parse(itemsMapJson); // TODO build the needed mapper
                contract.itemsMapDiscount = getItems(itemIdMap);
                cache.put(contract.contractId, contract);
                return contract;
            }
        }
        catch(SQLException ignored)
        {
//            System.out.println(ignored.getMessage());
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return null; // if it wasnt found in the DB or in the cache
    }


    //This function gives all the contracts that are currently in the DB
    public List<Contract> findAll()
    {
        List<Contract> contracts = new ArrayList<>(); // The list that will hold all the contracts that we will return
        List<Map<String,Map<Integer,Double>>> itemsIdMap = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM Contracts");//The SQL query that we use to get all the contracts in the DB
            rs = stmt.executeQuery();
            while (rs.next()) //If there are any contracts in the DB we create an instance for each and one of them
            {
                Contract contract = new Contract();
                contract.setContractId(rs.getInt("contract_id"));
                contract.setSupplierId(rs.getString("supplier_id"));
                contract.setTotalDiscount(rs.getDouble("total_discount"));
                String itemsMapJson = rs.getString("items_Map_discount");
                Map<String,Map<Integer,Double>>itemIdMapTEMP = ParserForContractItemIntegerDouble.parse(itemsMapJson);
                itemsIdMap.add(itemIdMapTEMP);

//                contract.itemsMapDiscount = ParserForContractItemIntegerDouble.parse(itemsMapJson);
//                cache.put(contract.contractId, contract);
                contracts.add(contract);
            }
        }
        catch(SQLException ignored)
        {}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

        for(int i = 0; i < contracts.size(); i++)
        {
            Map<Item,Map<Integer,Double>> itemsMap = getItems(itemsIdMap.get(i));
            contracts.get(i).itemsMapDiscount = itemsMap;
            cache.put(contracts.get(i).contractId, contracts.get(i));
        }

        return contracts; // we return all the contracts that we found
    }

    //This function lets us insert a new contract in the system
    public void insert(Contract contract)
    {
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        Map<String, Map<Integer,Double>> insertItem = new HashMap<>();

        for(Map.Entry<Item,Map<Integer,Double>> entry : contract.getItemsMapDiscount().entrySet())
        {
            String key = entry.getKey().getCatalogNum();
            Map<Integer,Double> map = entry.getValue();
            insertItem.put(key,map);
        }

        try {
            stmt = conn.prepareStatement("INSERT INTO contracts (contract_id, supplier_id, items_Map_discount,total_discount) VALUES (?, ?, ?, ?)"); //This is the SQL query that we use to insert a new contract into the DB
//            String itemsJson = new Gson().toJson(contract.getItemsMapDiscount()).toString();
            String itemsJson = new Gson().toJson(insertItem).toString();
            stmt.setString(1, Integer.toString(contract.contractId));
            stmt.setString(2, contract.supplierId);
            stmt.setString(3, itemsJson);
            stmt.setDouble(4, contract.getTotal_discount());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                cache.put(contract.contractId, contract);//We insert the new contract into the cache
            }
        }
        catch (SQLException e)
        {
//            System.out.println(e.getMessage() + " error");
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}
    }

    //This function lets us update a value that is already in the DB
    public void update(Contract contract)
    {
        PreparedStatement stmt;
        getConnection();

        Map<String, Map<Integer,Double>> insertItem = new HashMap<>();

        for(Map.Entry<Item,Map<Integer,Double>> entry : contract.getItemsMapDiscount().entrySet())
        {
            String key = entry.getKey().getCatalogNum();
            Map<Integer,Double> map = entry.getValue();
            insertItem.put(key,map);
        }

        try{
            stmt = conn.prepareStatement("UPDATE Contracts SET supplier_id = ?,  items_Map_discount = ?, total_discount = ? WHERE contract_id = ?");//This is the SQL query that we use for updating a value
            stmt.setInt(4,contract.contractId);
            stmt.setString(1,contract.supplierId);
//            String itemsJson = new JSONObject(contract.getItemsMapDiscount()).toString();
            String itemsJson = new JSONObject(insertItem).toString();
            stmt.setString(2, itemsJson);
            stmt.setDouble(3, contract.getTotal_discount());
            stmt.executeUpdate();
            cache.put(contract.contractId,contract);
        }
        catch (SQLException e)
        {
//            System.out.println(e.getMessage() + " error");
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}
    }
    //This function lets us delete a value from the DB
    public void delete(Contract contract)
    {
        PreparedStatement stmt;
        getConnection();
        try {
            stmt = conn.prepareStatement("DELETE FROM Contracts WHERE contract_id = ?");//This is the SQL query that we use for deleting a value
            stmt.setString(1, Integer.toString(contract.contractId));
            stmt.executeUpdate();
            cache.remove(contract.contractId);
        }
        catch (SQLException e)
        {}
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
            stmt = conn.prepareStatement("DELETE FROM Contracts");
            stmt.executeUpdate();
            cache.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    private Map<Item,Map<Integer,Double>> getItems(Map<String,Map<Integer,Double>> itemIdMap)
    {
        ItemMapper itemMapper = new ItemMapper();
        Map<Item,Map<Integer,Double>> itemList = new HashMap<>();
        for(Map.Entry<String,Map<Integer,Double>> entry : itemIdMap.entrySet())
        {
            String key = entry.getKey();
            Map<Integer,Double> value = entry.getValue();
            Item item = itemMapper.findByCatalogNum(key);
            itemList.put(item,value);
        }
        return itemList;
    }
}