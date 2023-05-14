import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;
import java.util.Date;

public class specificItemMapper {
    private Connection conn;
    private final Map<String, List<specificItem>> cache;
    public specificItemMapper() {
        this.cache = new HashMap<>();
    }

    public specificItem findSpecificItemBySerial(int serialNumber) {
        for (List<specificItem> itemList : cache.values()) {
            for (specificItem item : itemList) {
                if (item.getserialNumber() == serialNumber) {
                    return item;
                }
            }
        }
        return null;
    }

    public List<specificItem> findByCatalogNum(String catalogNum)
    {
        if(cache.containsKey(catalogNum))
        {
            return cache.get(catalogNum);
        }
        List<specificItem> specificItems = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        try
        {
            stmt = conn.prepareStatement("SELECT * FROM specific_items WHERE catalog_number = ?");
            stmt.setString(1, catalogNum);
            rs = stmt.executeQuery();
            while (rs.next()) {
                specificItem currentSpecific = new specificItem();
                currentSpecific.setName(rs.getString("name"));
                currentSpecific.setCatalogNum(rs.getString("catalog_number"));
                String locationString = rs.getString("location");
                if (locationString != null && !locationString.isEmpty()) {
                    locationString = locationString.substring(0, 1).toUpperCase() + locationString.substring(1).toLowerCase();
                }
                Location location = Location.valueOf(locationString);
                currentSpecific.setLocation(location);
                long currentDate = rs.getLong("expiration_date");
                currentSpecific.setDate(new Date(currentDate));
                currentSpecific.setDefected(rs.getBoolean("defected"));
                currentSpecific.setSerialNumber(rs.getInt("serial_number"));
                specificItems.add(currentSpecific);
            }
            cache.put(catalogNum, specificItems);
            return specificItems;
        }
        catch(SQLException e){}
        try
        {
            conn.close();
        }
        catch (SQLException e){}

        return null;
    }

    public List<specificItem> findAll()
    {
        if(!cache.isEmpty())
        {
            List<specificItem> allItems = new ArrayList<>();
            for (List<specificItem> items : cache.values()) {
                allItems.addAll(items);
            }
            return allItems;
        }

        List<specificItem> specificItems = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        try
        {
            stmt = conn.prepareStatement("SELECT * FROM specific_items");
            rs = stmt.executeQuery();
            while (rs.next()) {
                specificItem currentSpecific = new specificItem();
                currentSpecific.setName(rs.getString("name"));
                currentSpecific.setCatalogNum(rs.getString("catalog_number"));
                String locationString = rs.getString("location");
                if (locationString != null && !locationString.isEmpty()) {
                    locationString = locationString.substring(0, 1).toUpperCase() + locationString.substring(1).toLowerCase();
                }
                Location location = Location.valueOf(locationString);
                currentSpecific.setLocation(location);
                String currentDate = rs.getString("expiration_date");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(currentDate);
                currentSpecific.setDate(date);
                currentSpecific.setDefected(rs.getBoolean("defected"));
                currentSpecific.setSerialNumber(rs.getInt("serial_number"));
                specificItems.add(currentSpecific);
            }
            cache.put("", specificItems);
            return specificItems;
        }
        catch(SQLException e){}
        catch(ParseException e){}

        try
        {
            conn.close();
        }
        catch (SQLException e){}

        return null;
    }

    public void insert(specificItem item) {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("INSERT INTO specific_items (name, catalog_number, location, expiration_date, defected, serial_number) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, item.getSerialNumberString());
            stmt.setString(2, item.getCatalogNum());
            stmt.setString(3, item.getLocationString());
            if (item.getDate() != null) {
                stmt.setDate(4, new java.sql.Date(item.getDate().getTime()));
            } else {
                stmt.setDate(4, null);
            }
            stmt.setBoolean(5, item.getisDefected());
            stmt.setInt(6, item.getserialNumber());
            stmt.executeUpdate();
            cache.clear(); // clear cache since it's outdated
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

    public void update(specificItem item) {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("UPDATE specific_items SET location=?, expiration_date=?, defected=?, serial_number=? WHERE name=?");
            stmt.setString(1, item.getLocationString());
            if (item.getDate() != null) {
                stmt.setDate(2, new java.sql.Date(item.getDate().getTime()));
            } else {
                stmt.setDate(2, null);
            }
            stmt.setBoolean(3, item.getisDefected());
            stmt.setInt(4, item.getserialNumber());
            stmt.setString(5, item.getSerialNumberString());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Failed to update specific item: " + item.getSerialNumberString());
            } else {
                System.out.println("Successfully updated specific item: " + item.getSerialNumberString());
                cache.remove(item.getSerialNumberString()); // remove item from cache to refresh from database on next access
            }
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



    public void delete(specificItem item) {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("DELETE FROM specific_items WHERE serial_number = ?");
            stmt.setInt(1, item.getserialNumber());
            stmt.executeUpdate();
            cache.clear(); // clear cache since it's outdated
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

    private void getConnection()
    {
        try
        {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        }
        catch (SQLException e){}
    }

}
