import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class NonDeliveringSupplierMapper {
    private final Connection conn;
    private final Map<String,NonDeliveringSupplier> cache;

    public NonDeliveringSupplierMapper(Connection conn) {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public NonDeliveringSupplier findBySupplierId(String supplierID) throws SQLException
    {
        if(cache.containsKey(supplierID))
        {
            return cache.get(supplierID);
        }
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM NonDeliveringSuppliers WHERE supplier_ID = ?");
        stmt.setString(1,supplierID);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            Connection conn = null;
            try
            {
                String url = "jdbc:sqlite:dev/res/SuperLeeDataBase.db.db";
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

            ContractMapper contractMapper = new ContractMapper(conn);
            Contract contract;
            contract = contractMapper.findBySupplierId(supplierID);
            ContactPerson person = new ContactPerson(rs.getString("contract_person_name"),rs.getString("contract_phone_number"));
            String itemsMapJson = rs.getString("items");
            Type type = new TypeToken<Map<Item,Pair<Integer, Float>>>(){}.getType();
            int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
            Map<Item,Pair<Integer, Float>> items = new Gson().fromJson(itemsMapJson, type);
            NonDeliveringSupplier nonDeliveringSupplier = new NonDeliveringSupplier(rs.getString("name"),rs.getString("business_id"),paymentMethod,rs.getString("supplier_ID"),person,contract,items);

            cache.put(supplierID,nonDeliveringSupplier);
            return nonDeliveringSupplier;
        }
        return null;
    }

    public List<NonDeliveringSupplier> findAll() throws SQLException
    {
        List<NonDeliveringSupplier> suppliers = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM NonDeliveringSuppliers");
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            ContractMapper contractMapper = new ContractMapper(conn);
            Contract contract;
            contract = contractMapper.findBySupplierId(rs.getString("supplier_ID"));
            ContactPerson person = new ContactPerson(rs.getString("contract_person_name"),rs.getString("contract_phone_number"));
            String itemsMapJson = rs.getString("items");
            Type type = new TypeToken<Map<Item,Pair<Integer, Float>>>(){}.getType();
            int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
            Map<Item,Pair<Integer, Float>> items = new Gson().fromJson(itemsMapJson, type);
            NonDeliveringSupplier nonDeliveringSupplier = new NonDeliveringSupplier(rs.getString("name"),rs.getString("business_id"),paymentMethod,rs.getString("supplier_ID"),person,contract,items);
            cache.put(rs.getString("supplier_ID"),nonDeliveringSupplier);
            suppliers.add(nonDeliveringSupplier);
        }
        return suppliers;
    }



    public void insert(NonDeliveringSupplier nonDeliveringSupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO NonDeliveringSuppliers (business_id, name, payment_method,supplier_ID,contract_person_name,contract_phone_number,items,contract_id) VALUES (?, ?, ?, ?,?,?,?,?)");
        String itemsJson = new JSONObject(nonDeliveringSupplier.getItems()).toString();
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
        if(rs.next())
        {
            cache.put(nonDeliveringSupplier.getSupplierID(),nonDeliveringSupplier);
        }
    }

    public void update(NonDeliveringSupplier nonDeliveringSupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE NonDeliveringSuppliers SET business_id = ?,  name = ?, payment_method = ?, supplier_ID = ?, contract_person_name = ?, contract_phone_number = ?, items = ?, contract_id = ? WHERE supplier_ID = ?");
        String itemsJson = new JSONObject(nonDeliveringSupplier.getItems()).toString();
        stmt.setString(1, nonDeliveringSupplier.getBusinessId());
        stmt.setString(2, nonDeliveringSupplier.getName());
        stmt.setString(3, (nonDeliveringSupplier.getPaymentMethod()).toString());
        stmt.setString(4, nonDeliveringSupplier.getSupplierID());
        stmt.setString(5, nonDeliveringSupplier.getPerson().getName());
        stmt.setString(6, nonDeliveringSupplier.getPerson().getPhoneNumber());
        stmt.setString(7, itemsJson);
        stmt.setString(8, String.valueOf(nonDeliveringSupplier.getContract().contractId));
        cache.put(nonDeliveringSupplier.getSupplierID(),nonDeliveringSupplier);
    }

    public void delete(NonDeliveringSupplier nonDeliveringSupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM NonDeliveringSuppliers WHERE supplier_ID = ?");
        stmt.setString(1, nonDeliveringSupplier.getBusinessId());
        stmt.executeUpdate();
        cache.remove(nonDeliveringSupplier.getSupplierID());
    }
}

