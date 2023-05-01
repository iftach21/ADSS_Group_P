import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
//import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
public class FixedDaySupplierMapper{
    private final Connection conn;
    private final Map<String, Supplier> cache;

    public FixedDaySupplierMapper(Connection conn) {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public Supplier findBySupplierId(String supplierID) throws SQLException {
        if (cache.containsKey(supplierID)) {
            return cache.get(supplierID);
        }
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM FixedDaySuppliers WHERE supplier_ID = ?");
        stmt.setString(1, supplierID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next())
        {
            Connection conn = null;
            try
            {
                String url = "jdbc:sqlite:dev/res/SuperLeeDataBase.db.db";
                conn = DriverManager.getConnection(url);
            }
            catch (SQLException ignored) {}
            finally
            {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                }
                catch (SQLException ignored) {}
            }
            ContractMapper contractMapper = new ContractMapper(conn);
            Contract contract;
            contract = contractMapper.findBySupplierId(supplierID);
            ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
            String itemsMapJson = rs.getString("items");
            Type type = new TypeToken<Map<Item, Pair<Integer, Float>>>() {}.getType();
            int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
            Map<Item, Pair<Integer, Float>> items = new Gson().fromJson(itemsMapJson, type);
            String currentDay = rs.getString("currentDeliveryDay");
            WindowType currentDeliveryDay = WindowType.valueOf(currentDay);
            FixedDaySupplier fixedDaySupplier = new FixedDaySupplier(currentDeliveryDay,rs.getString("name"),rs.getString("business_id"),paymentMethod,rs.getString("supplier_ID"),person,contract,items);
            cache.put(supplierID,fixedDaySupplier);
            return fixedDaySupplier;
        }
        return null;
    }
    public List<FixedDaySupplier> findAll() throws SQLException
    {
        List<FixedDaySupplier> suppliers = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM FixedDaySuppliers");
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            ContractMapper contractMapper = new ContractMapper(conn);
            Contract contract;
            contract = contractMapper.findBySupplierId(rs.getString("supplier_ID"));
            ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
            String itemsMapJson = rs.getString("items");
            Type type = new TypeToken<Map<Item, Pair<Integer, Float>>>() {}.getType();
            int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
            Map<Item, Pair<Integer, Float>> items = new Gson().fromJson(itemsMapJson, type);
            String currentDay = rs.getString("currentDeliveryDay");
            WindowType currentDeliveryDay = WindowType.valueOf(currentDay);
            FixedDaySupplier fixedDaySupplier = new FixedDaySupplier(currentDeliveryDay,rs.getString("name"),rs.getString("business_id"),paymentMethod,rs.getString("supplier_ID"),person,contract,items);
            cache.put(rs.getString("supplier_ID"),fixedDaySupplier);
            suppliers.add(fixedDaySupplier);
        }
        return suppliers;
    }

    public void insert(FixedDaySupplier fixedDaySupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO FixedDaySuppliers (business_id, name, currentDeliveryDay, payment_method, supplier_ID, contract_person_name, contract_phone_number, items, contract_id) VALUES (?,?, ?, ?, ?,?,?,?,?)");
        String itemsJson = new JSONObject(fixedDaySupplier.getItems()).toString();
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
        if(rs.next())
        {
            cache.put(fixedDaySupplier.getSupplierID(),fixedDaySupplier);
        }
    }

    public void update(FixedDaySupplier fixedDaySupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE FixedDaySuppliers SET business_id = ?,  name = ?, currentDeliveryDay = ? ,payment_method = ?, supplier_ID = ?, contract_person_name = ?, contract_phone_number = ?, items = ?, contract_id = ? WHERE supplier_ID = ?");
        String itemsJson = new JSONObject(fixedDaySupplier.getItems()).toString();
        stmt.setString(1, fixedDaySupplier.getBusinessId());
        stmt.setString(2, fixedDaySupplier.getName());
        stmt.setString(3, fixedDaySupplier.getCurrentDeliveryDay().name());
        stmt.setString(4, (fixedDaySupplier.getPaymentMethod()).toString());
        stmt.setString(5, fixedDaySupplier.getSupplierID());
        stmt.setString(6, fixedDaySupplier.getPerson().getName());
        stmt.setString(7, fixedDaySupplier.getPerson().getPhoneNumber());
        stmt.setString(8, itemsJson);
        stmt.setString(9, String.valueOf(fixedDaySupplier.getContract().contractId));
        cache.put(fixedDaySupplier.getSupplierID(),fixedDaySupplier);
    }

    public void delete(FixedDaySupplier fixedDaySupplier) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM FixedDaySuppliers WHERE supplier_ID = ?");
        stmt.setString(1, fixedDaySupplier.getBusinessId());
        stmt.executeUpdate();
        cache.remove(fixedDaySupplier.getSupplierID());
    }
}
