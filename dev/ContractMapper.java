import java.sql.*;
import java.util.*;

import com.google.gson.reflect.TypeToken;
import org.json.*;
import com.google.gson.*;
import java.lang.reflect.Type;


public class ContractMapper {
    private Connection conn;
    private final Map<Integer,Contract> cache;

//    public ContractMapper(Connection conn)
//    {
//        this.conn = conn;
//        this.cache = new HashMap<>();
//    }

    public ContractMapper()
    {
        this.cache = new HashMap<>();
    }
    public Contract findByContractId(int contractId)
    {
        if(cache.containsKey(contractId))
        {
            return cache.get(contractId);
        }

        getConnection();
        try
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contracts WHERE contract_id = ?");
            stmt.setInt(1,contractId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                Contract contract = new Contract();
                contract.setContractId(rs.getInt("contract_id"));
                contract.setSupplierId(rs.getString("supplier_id"));
                contract.setTotalDiscount(rs.getDouble("total_discount"));
                String itemsMapJson = rs.getString("items_Map_discount");
//                Type type = new TypeToken<Map<Item, Map<Integer, Double>>>(){}.getType();
                contract.itemsMapDiscount = ParserForContractItemIntegerDouble.parse(itemsMapJson);
                cache.put(contractId,contract);
                conn.close();
                return contract;
            }
        }
        catch (Exception ignored){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return null;
    }

    public Contract findBySupplierId(String supplierID)
    {
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM Contracts WHERE supplier_id = ?");
            stmt.setString(1, supplierID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (cache.containsKey(rs.getInt("contract_id"))) {
                    return cache.get(rs.getInt("contract_id"));
                }
                Contract contract = new Contract();
                contract.setContractId(rs.getInt("contract_id"));
                contract.setSupplierId(rs.getString("supplier_id"));
                contract.setTotalDiscount(rs.getDouble("total_discount"));
                String itemsMapJson = rs.getString("items_Map_discount");
//            Type type = new TypeToken<Map<Item, Map<Integer, Double>>>(){}.getType();
                contract.itemsMapDiscount = ParserForContractItemIntegerDouble.parse(itemsMapJson);
                cache.put(rs.getInt("contract_id"), contract);
                conn.close();
                return contract;
            }
        }
        catch(SQLException ignored)
        {}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return null;
    }


    public List<Contract> findAll()
    {
        List<Contract> contracts = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM Contracts");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                contract.setContractId(rs.getInt("contract_id"));
                contract.setSupplierId(rs.getString("supplier_id"));
                contract.setTotalDiscount(rs.getDouble("total_discount"));
                String itemsMapJson = rs.getString("items_Map_discount");
                contract.itemsMapDiscount = ParserForContractItemIntegerDouble.parse(itemsMapJson);
//            Type type = new TypeToken<Map<Item, Map<Integer, Double>>>(){}.getType();
//
//            contract.itemsMapDiscount = new Gson().fromJson(itemsMapJson, type);

                cache.put(contract.contractId, contract);
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
        return contracts;
    }

    public void insert(Contract contract)
    {
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("INSERT INTO contracts (contract_id, supplier_id, items_Map_discount,total_discount) VALUES (?, ?, ?, ?)");
            String itemsJson = new Gson().toJson(contract.getItemsMapDiscount()).toString();
            stmt.setString(1, Integer.toString(contract.contractId));
            stmt.setString(2, contract.supplierId);
            stmt.setString(3, itemsJson);
            stmt.setDouble(4, contract.getTotal_discount());

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                contract.contractId = rs.getInt(1);
                cache.put(contract.contractId, contract);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage() + " error");
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}
    }

    public void update(Contract contract)
    {
        PreparedStatement stmt;
        getConnection();
        try{

            stmt = conn.prepareStatement("UPDATE Contracts SET supplier_id = ?,  items_Map_discount = ?, total_discount = ? WHERE contract_id = ?");
            stmt.setInt(4,contract.contractId);
            stmt.setString(1,contract.supplierId);
            String itemsJson = new JSONObject(contract.getItemsMapDiscount()).toString();
            stmt.setString(2, itemsJson);
            stmt.setDouble(3, contract.getTotal_discount());
            stmt.executeUpdate();
            cache.put(contract.contractId,contract);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage() + " error");
        }
        try
        {
            conn.close();
        }
        catch (SQLException e){}
    }
    public void delete(Contract contract)
    {
        PreparedStatement stmt;
        getConnection();
        try {
            stmt = conn.prepareStatement("DELETE FROM Contracts WHERE contract_id = ?");
            stmt.setString(1, Integer.toString(contract.contractId));
            stmt.executeUpdate();
            cache.remove(contract.contractId);
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

    private void getConnection()
    {
        try
        {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        }
        catch (SQLException e){}
    }


}