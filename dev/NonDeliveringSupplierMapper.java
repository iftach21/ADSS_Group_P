import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

/**
 * This is the mapper class for the NonDeliveringSupplier class
 **/
public class NonDeliveringSupplierMapper {
    private Connection conn;
    private final Map<String,NonDeliveringSupplier> cache;

    //This is the constructor for the class, it doesnt get any arguments and only initialize the cache
    public NonDeliveringSupplierMapper()
    {
        this.cache = new HashMap<>();
    }

    //This function finds a NonDeliveringSupplier in the DB by the supplierID
    public NonDeliveringSupplier findBySupplierId(String supplierID)
    {
        //First we check if we have this FixedDaySupplier in the cache
        if(cache.containsKey(supplierID))
        {
            return cache.get(supplierID);
        }
        PreparedStatement stmt;
        ResultSet rs;
        getConnection(); // The function that gets us the connection to the DB

        //If it doesnt exist in the cache we check if it exists in the DB
        try {
            stmt = conn.prepareStatement("SELECT * FROM NonDeliveringSuppliers WHERE supplier_ID = ?");//The SQL query that we use to find the FixedDaySupplier
            stmt.setString(1, supplierID);
            rs = stmt.executeQuery();
            if (rs.next())// if we found the NonDeliveringSupplier in the DB we build the NonDeliveringSupplier class instance
            {
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
        return null; // if it wasnt found in the DB or in the cache
    }

    //This function gives all the FixedDaySupplier that are currently in the DB
    public List<NonDeliveringSupplier> findAll()
    {
        getConnection();
        List<NonDeliveringSupplier> suppliers = new ArrayList<>();// The list that will hold all the NonDeliveringSupplier that we will return
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement("SELECT * FROM NonDeliveringSuppliers");//The SQL query that we use to get all the NonDeliveringSuppliers in the DB
            rs = stmt.executeQuery();
            while (rs.next()) //If there are any FixedDaySuppliers in the DB we create an instance for each and one of them
            {
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
        return suppliers; // we return all the FixedDaySupplier that we found
    }

    //This function lets us insert a new NonDeliveringSupplier in the system
    public void insert(NonDeliveringSupplier nonDeliveringSupplier)
    {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("INSERT INTO NonDeliveringSuppliers (business_id, name, payment_method, supplier_ID, contract_person_name, contract_phone_number, items, contract_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");//This is the SQL query that we use to insert a new NonDeliveringSupplier into the DB
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
                cache.put(nonDeliveringSupplier.getSupplierID(), nonDeliveringSupplier);//We insert the new FixedDaySupplier into the cache
            }
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

    }

    //This function lets us update a value that is already in the DB
    public void update(NonDeliveringSupplier nonDeliveringSupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("UPDATE NonDeliveringSuppliers SET business_id = ?,  name = ?, payment_method = ?, supplier_ID = ?, contract_person_name = ?, contract_phone_number = ?, items = ?, contract_id = ? WHERE supplier_ID = ?");//This is the SQL query that we use for updating a value
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


    //This function lets us delete a value from the DB
    public void delete(NonDeliveringSupplier nonDeliveringSupplier)
    {
        getConnection();
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM NonDeliveringSuppliers WHERE supplier_ID = ?");//This is the SQL query that we use for deleting a value
            stmt.setString(1, nonDeliveringSupplier.getSupplierID());
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

    //This helper function gives us a connection to the DB
    private void getConnection()
    {
        try
        {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        }
        catch (SQLException e){}
    }
}

