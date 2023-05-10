import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
//import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
public class FixedDaySupplierMapper{
    private Connection conn;
    private final Map<String, FixedDaySupplier> cache;

//    public FixedDaySupplierMapper(Connection conn) {
//        this.conn = conn;
//        this.cache = new HashMap<>();
//    }
    public FixedDaySupplierMapper() {
//        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public FixedDaySupplier findBySupplierId(String supplierID)
    {
        if (cache.containsKey(supplierID))
        {
            return cache.get(supplierID);
        }
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        try {
            stmt = conn.prepareStatement("SELECT * FROM FixedDaySuppliers WHERE supplier_ID = ?");
            stmt.setString(1, supplierID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Connection conn = null;
                try {
                    String url = "jdbc:sqlite:dev/res/SuperLeeDataBase.db.db";
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
//                ContractMapper contractMapper = new ContractMapper(conn);
                ContractMapper contractMapper = new ContractMapper();

                Contract contract;
                contract = contractMapper.findBySupplierId(supplierID);
                ContactPerson person = new ContactPerson(rs.getString("contract_person_name"), rs.getString("contract_phone_number"));
                String itemsMapJson = rs.getString("items");
                Type type = new TypeToken<Map<Item, Pair<Integer, Float>>>() {
                }.getType();
                int paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method")).getNumericValue();
                Map<Item, Pair<Integer, Float>> map = Parser.parse(itemsMapJson);
                String currentDay = rs.getString("currentDeliveryDay");
                WindowType currentDeliveryDay = WindowType.valueOf(currentDay);
                FixedDaySupplier fixedDaySupplier = new FixedDaySupplier(currentDeliveryDay, rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, map);
                cache.put(supplierID, fixedDaySupplier);
                try
                {
                    conn.close();
                }
                catch (SQLException e){}
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
        return null;
    }
    public List<FixedDaySupplier> findAll()
    {
        List<FixedDaySupplier> suppliers = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM FixedDaySuppliers");
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
                String currentDay = rs.getString("currentDeliveryDay");
                WindowType currentDeliveryDay = WindowType.valueOf(currentDay);
                FixedDaySupplier fixedDaySupplier = new FixedDaySupplier(currentDeliveryDay, rs.getString("name"), rs.getString("business_id"), paymentMethod, rs.getString("supplier_ID"), person, contract, map);
                cache.put(rs.getString("supplier_ID"), fixedDaySupplier);
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
        return suppliers;
    }

    public void insert(FixedDaySupplier fixedDaySupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("INSERT INTO FixedDaySuppliers (business_id, name, currentDeliveryDay, payment_method, supplier_ID, contract_person_name, contract_phone_number, items, contract_id) VALUES (?,?, ?, ?, ?,?,?,?,?)");
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
            if (rs.next()) {
                cache.put(fixedDaySupplier.getSupplierID(), fixedDaySupplier);
            }
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}
    }

    public void update(FixedDaySupplier fixedDaySupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("UPDATE FixedDaySuppliers SET business_id = ?,  name = ?, currentDeliveryDay = ? ,payment_method = ?, supplier_ID = ?, contract_person_name = ?, contract_phone_number = ?, items = ?, contract_id = ? WHERE supplier_ID = ?");
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
            stmt.setString(10, String.valueOf(fixedDaySupplier.getSupplierID()));
            stmt.executeUpdate();

            cache.put(fixedDaySupplier.getSupplierID(), fixedDaySupplier);
        }
        catch (SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    public void delete(FixedDaySupplier fixedDaySupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM FixedDaySuppliers WHERE supplier_ID = ?");
            stmt.setString(1, fixedDaySupplier.getBusinessId());
            stmt.executeUpdate();
            cache.remove(fixedDaySupplier.getSupplierID());
        }
        catch (SQLException e){}
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
