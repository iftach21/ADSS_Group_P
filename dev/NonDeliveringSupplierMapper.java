import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class NonDeliveringSupplierMapper {
    private Connection conn;
    private final Map<String,NonDeliveringSupplier> cache;

    public NonDeliveringSupplierMapper(Connection conn) {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public NonDeliveringSupplier findBySupplierId(String supplierID)
    {
        getConnection();
        if(cache.containsKey(supplierID))
        {
            return cache.get(supplierID);
        }
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement("SELECT * FROM NonDeliveringSuppliers WHERE supplier_ID = ?");
            stmt.setString(1, supplierID);
            rs = stmt.executeQuery();
            if (rs.next())
            {
//                ContractMapper contractMapper = new ContractMapper(conn);
                ContractMapper contractMapper = new ContractMapper();
                Contract contract;
                contract = contractMapper.findBySupplierId(supplierID);
                ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
                String itemsMapJson = rs.getString("items");
                Map<Item, Pair<Integer, Float>> map = Parser.parse(itemsMapJson);

                int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
                NonDeliveringSupplier nonDeliveringSupplier = new NonDeliveringSupplier(rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, map);

                cache.put(supplierID, nonDeliveringSupplier);
                try
                {
                    conn.close();
                }
                catch (SQLException e){}
                return nonDeliveringSupplier;
            }
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return null;
    }

    public List<NonDeliveringSupplier> findAll()
    {
        getConnection();
        List<NonDeliveringSupplier> suppliers = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement("SELECT * FROM NonDeliveringSuppliers");
            rs = stmt.executeQuery();
            while (rs.next()) {
//                ContractMapper contractMapper = new ContractMapper(conn);
                ContractMapper contractMapper = new ContractMapper();
                Contract contract;
                contract = contractMapper.findBySupplierId(rs.getString("supplier_ID"));
                ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
                String itemsMapJson = rs.getString("items");
                Type type = new TypeToken<Map<Item, Pair<Integer, Float>>>() {
                }.getType();
                int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
                Map<Item, Pair<Integer, Float>> map = Parser.parse(itemsMapJson);
                NonDeliveringSupplier nonDeliveringSupplier = new NonDeliveringSupplier(rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, map);
                cache.put(rs.getString("supplier_ID"), nonDeliveringSupplier);
                suppliers.add(nonDeliveringSupplier);
            }
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
        return suppliers;
    }



    public void insert(NonDeliveringSupplier nonDeliveringSupplier)
    {
        PreparedStatement stmt;
        try {
            getConnection();
            stmt = conn.prepareStatement("INSERT INTO NonDeliveringSuppliers (business_id, name, payment_method, supplier_ID, contract_person_name, contract_phone_number, items, contract_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            String itemsJson = new Gson().toJson(nonDeliveringSupplier.getItems());

            stmt.setString(1, nonDeliveringSupplier.getBusinessId());
            stmt.setString(2, nonDeliveringSupplier.getName());
            stmt.setString(3, (nonDeliveringSupplier.getPaymentMethod()).toString());
            stmt.setString(4, nonDeliveringSupplier.getSupplierID());
            stmt.setString(5, nonDeliveringSupplier.getPerson().getName());
            stmt.setString(6, nonDeliveringSupplier.getPerson().getPhoneNumber());
            stmt.setString(7, itemsJson);
            stmt.setString(8, String.valueOf(nonDeliveringSupplier.getContract().contractId));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cache.put(nonDeliveringSupplier.getSupplierID(), nonDeliveringSupplier);
            }
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    public void update(NonDeliveringSupplier nonDeliveringSupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("UPDATE NonDeliveringSuppliers SET business_id = ?,  name = ?, payment_method = ?, supplier_ID = ?, contract_person_name = ?, contract_phone_number = ?, items = ?, contract_id = ? WHERE supplier_ID = ?");
            String itemsJson = new JSONObject(nonDeliveringSupplier.getItems()).toString();
            stmt.setString(1, nonDeliveringSupplier.getBusinessId());
            stmt.setString(2, nonDeliveringSupplier.getName());
            stmt.setString(3, (nonDeliveringSupplier.getPaymentMethod()).toString());
            stmt.setString(4, nonDeliveringSupplier.getSupplierID());
            stmt.setString(5, nonDeliveringSupplier.getPerson().getName());
            stmt.setString(6, nonDeliveringSupplier.getPerson().getPhoneNumber());
            stmt.setString(7, itemsJson);
            stmt.setString(8, String.valueOf(nonDeliveringSupplier.getContract().contractId));
            stmt.setString(9, String.valueOf(nonDeliveringSupplier.getSupplierID()));

            stmt.executeUpdate();

            cache.put(nonDeliveringSupplier.getSupplierID(), nonDeliveringSupplier);
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
    }

    public void delete(NonDeliveringSupplier nonDeliveringSupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM NonDeliveringSuppliers WHERE supplier_ID = ?");
            stmt.setString(1, nonDeliveringSupplier.getBusinessId());
            stmt.executeUpdate();
            cache.remove(nonDeliveringSupplier.getSupplierID());
        }
        catch(SQLException e){}
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

