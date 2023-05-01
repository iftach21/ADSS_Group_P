import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
//import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
public class NonFixedDaySupplierMapper {
    private final Connection conn;
    private final Map<String, Supplier> cache;

    public NonFixedDaySupplierMapper(Connection conn) {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public Supplier findBySupplierId(String supplierID) throws SQLException {
        if (cache.containsKey(supplierID)) {
            return cache.get(supplierID);
        }

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM NonFixedDaySuppliers WHERE supplier_ID = ?");
        stmt.setString(1, supplierID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Connection conn = null;
            try {
                String url = "jdbc:sqlite:res/SuperLeeDataBase";
                conn = DriverManager.getConnection(url);
            } catch (SQLException ignored) {
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ignored) {
                }
            }
            ContractMapper contractMapper = new ContractMapper(conn);
            Contract contract;
            contract = contractMapper.findBySupplierId(supplierID);
            ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
            String itemsMapJson = rs.getString("items");
            Type type = new TypeToken<Map<Item, Pair<Integer, Float>>>() {}.getType();
            int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
            Map<Item, Pair<Integer, Float>> items = new Gson().fromJson(itemsMapJson, type);
            NonFixedDaySupplier nonFixedDaySupplier = new NonFixedDaySupplier(rs.getInt("numOfDayToDeliver"), rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, items);
            cache.put(supplierID, nonFixedDaySupplier);
            return nonFixedDaySupplier;
        }
        return null;
    }

    public List<NonDeliveringSupplier> findAll() throws SQLException {
        List<NonFixedDaySupplier> suppliers = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM NonFixedDaySuppliers");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ContractMapper contractMapper = new ContractMapper(conn);
            Contract contract;
            contract = contractMapper.findBySupplierId(rs.getString("supplier_ID"));
            ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
            String itemsMapJson = rs.getString("items");
            Type type = new TypeToken<Map<Item, Pair<Integer, Float>>>() {
            }.getType();
            int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
            Map<Item, Pair<Integer, Float>> items = new Gson().fromJson(itemsMapJson, type);
            NonFixedDaySupplier nonFixedDaySupplier = new NonFixedDaySupplier(rs.getInt("numOfDayToDeliver"), rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, items);
            cache.put(rs.getString("supplier_ID"), nonFixedDaySupplier);
            suppliers.add(nonFixedDaySupplier);
        }
        return null;
    }

    public void insert(NonFixedDaySupplier nonFixedDaySupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO NonFixedDaySuppliers (business_id, name, payment_method,supplier_ID,contract_person_name,contract_phone_number,items,numOfDayToDeliver,contract_id) VALUES (?, ?, ?, ?,?,?,?,?,?)");
        String itemsJson = new JSONObject(nonFixedDaySupplier.getItems()).toString();
        stmt.setString(1, nonFixedDaySupplier.getBusinessId());
        stmt.setString(2, nonFixedDaySupplier.getName());
        stmt.setString(3, (nonFixedDaySupplier.getPaymentMethod()).toString());
        stmt.setString(4, nonFixedDaySupplier.getSupplierID());
        stmt.setString(5, nonFixedDaySupplier.getPerson().getName());
        stmt.setString(6, nonFixedDaySupplier.getPerson().getPhoneNumber());
        stmt.setString(7, itemsJson);
        stmt.setString(8, String.valueOf(nonFixedDaySupplier.getContract().contractId));
        stmt.setString(9, String.valueOf(nonFixedDaySupplier.getContract().contractId));
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next())
        {
            cache.put(nonFixedDaySupplier.getSupplierID(),nonFixedDaySupplier);
        }
    }
    public void update(NonFixedDaySupplier nonFixedDaySupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE NonFixedDaySuppliers SET business_id = ?,  name = ?, payment_method = ?, supplier_ID = ?, contract_person_name = ?, contract_phone_number = ?, items = ?, numOfDayToDeliver = ?, contract_id = ? WHERE supplier_ID = ?");
        String itemsJson = new JSONObject(nonFixedDaySupplier.getItems()).toString();
        stmt.setString(1, nonFixedDaySupplier.getBusinessId());
        stmt.setString(2, nonFixedDaySupplier.getName());
        stmt.setString(3, (nonFixedDaySupplier.getPaymentMethod()).toString());
        stmt.setString(4, nonFixedDaySupplier.getSupplierID());
        stmt.setString(5, nonFixedDaySupplier.getPerson().getName());
        stmt.setString(6, nonFixedDaySupplier.getPerson().getPhoneNumber());
        stmt.setString(7, itemsJson);
        stmt.setString(8, String.valueOf(nonFixedDaySupplier.getContract().contractId));
        stmt.setString(9, String.valueOf(nonFixedDaySupplier.getContract().contractId));
        cache.put(nonFixedDaySupplier.getSupplierID(),nonFixedDaySupplier);
    }
    public void delete(NonFixedDaySupplier nonFixedDaySupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM NonFixedDaySuppliers WHERE supplier_ID = ?");
        stmt.setString(1, nonFixedDaySupplier.getBusinessId());
        stmt.executeUpdate();
        cache.remove(nonFixedDaySupplier.getSupplierID());
    }


}
