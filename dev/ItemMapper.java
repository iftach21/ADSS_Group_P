import java.sql.*;
import java.util.*;
import java.sql.Connection;
public class ItemMapper {
    private final Connection conn;
    private final Map<String,Item> cache;
    public ItemMapper(Connection conn)
    {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null.");
        }
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public Item findByCatalogNum(String catalogNum) throws SQLException
    {
        if(cache.containsKey(catalogNum))
        {
            return cache.get(catalogNum);
        }
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE catalog_number = ?");
        stmt.setString(1,catalogNum);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            Item item = new Item();
            item.setName(rs.getString("name"));
            item.setCatalogNum(rs.getString("catalog_number"));
            item.setWeight(rs.getDouble("weight"));
            item.setCatalogName(rs.getString("catalog_name"));
            String tempLevel = rs.getString("temperature");
            TempLevel tempValue = TempLevel.valueOf(tempLevel);
            item.setTemperature(tempValue);
            item.setManufacturer((rs.getString("manufacturer")));
            cache.put(catalogNum,item);
            return item;
        }
        return null;
    }

    public List<Item> findAll() throws SQLException
    {
        List<Item> items = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items");
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            Item item = new Item();
            item.setName(rs.getString("name"));
            item.setCatalogNum(rs.getString("catalog_number"));
            item.setWeight(rs.getDouble("weight"));
            item.setCatalogName(rs.getString("catalog_name"));
            String tempLevel = rs.getString("temperature");
            TempLevel tempValue = TempLevel.valueOf(tempLevel);
            item.setTemperature(tempValue);
            item.setManufacturer((rs.getString("manufacturer")));
            cache.put(item.getCatalogNum(),item);
            items.add(item);
        }
        return items;
    }

    public void insert(Item item) throws SQLException // TODO check what happens if i try to add an item that already exists in the database
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO items(catalog_number,name,weight,catalog_name,temperature,minimum_quantity,price_history,manufacturer)VALUES (?, ?, ?, ?,?,?,?,?)");
        stmt.setString(1,item.getCatalogNum());
        stmt.setString(2,item.getName());
        stmt.setString(3,Double.toString(item.getWeight()));
        stmt.setString(4,item.getCatalogName());
        stmt.setString(5,item.getTemperature().name());
        stmt.setString(6,Integer.toString(item.getMinimum_quantity()));
        stmt.setString(7,item.getPriceHistory().toString());
        stmt.setString(8,item.getManufacturer());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next())
        {
            item.setCatalogNum(rs.getString(1));
            cache.put(item.getCatalogNum(),item);
        }
    }

    public void update(Item item) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE items SET name = ?, weight = ?,  catalog_name = ?, temperature = ?, minimum_quantity = ?, price_history = ?, manufacturer = ? WHERE catalog_number = ?");
        stmt.setString(1,item.getCatalogNum());
        stmt.setString(2,item.getName());
        stmt.setString(3,Double.toString(item.getWeight()));
        stmt.setString(4,item.getCatalogName());
        stmt.setString(5,item.getTemperature().name());
        stmt.setString(6,Integer.toString(item.getMinimum_quantity()));
        stmt.setString(7,item.getPriceHistory().toString());
        stmt.setString(8,item.getManufacturer());
        stmt.executeUpdate();
        cache.put(item.getCatalogNum(),item);
    }

    public void delete(Item item) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM items WHERE catalog_number = ?");
        stmt.setString(1,item.getCatalogNum());
        stmt.executeUpdate();
        cache.remove(item.getCatalogNum());
    }
}
