import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.util.Date;

public class specificItemMapper {
    private Connection conn;
    private final Map<String, List<specificItem>> cache;
    public specificItemMapper() {
        this.cache = new HashMap<>();
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
                Location location = Location.valueOf(locationString);
                currentSpecific.setLocation(location);
                String currentDate = rs.getString("expiration_date");
                Date date = new Date(currentDate);
                currentSpecific.setDate(date);
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

    public List<specificItem> findAll() {
        List<specificItem> items = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        try {
            stmt = conn.prepareStatement("SELECT * FROM specific_items");
            rs = stmt.executeQuery();
            while (rs.next()) {
                specificItem currentSpecific = new specificItem();
                currentSpecific.setName(rs.getString("name"));
                currentSpecific.setCatalogNum(rs.getString("catalog_number"));
                String locationString = rs.getString("location");
                Location location = Location.valueOf(locationString);
                currentSpecific.setLocation(location);
                String currentDate = rs.getString("expiration_date");
                Date date = new Date(currentDate);
                currentSpecific.setDate(date);
                currentSpecific.setDefected(rs.getBoolean("defected"));
                currentSpecific.setSerialNumber(rs.getInt("serial_number"));
                items.add(currentSpecific);
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

        return items;
    }

    public void insert(specificItem item) {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("INSERT INTO specific_items (name, catalog_number, location, expiration_date, defected, serial_number) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCatalogNum());
            stmt.setString(3, item.getLocation().toString());
            stmt.setString(4, item.getDate().toString());
            stmt.setBoolean(5, item.isDefected());
            stmt.setInt(6, item.getSerialNumber());
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
            stmt = conn.prepareStatement("UPDATE specific_items SET name = ?, location = ?, expiration_date = ?, defected = ?, serial_number = ? WHERE catalog_number = ?");
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getLocation().toString());
            stmt.setString(3, item.getDate().toString());
            stmt.setBoolean(4, item.isDefected());
            stmt.setInt(5, item.getSerialNumber());
            stmt.setString(6, item.getCatalogNum());
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

    public void delete(specificItem item) {
        PreparedStatement stmt;
        getConnection();

        try {
            stmt = conn.prepareStatement("DELETE FROM specific_items WHERE catalog_number = ?");
            stmt.setString(1, item.getCatalogNum());
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
