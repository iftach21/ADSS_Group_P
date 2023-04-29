import java.sql.*;
import java.util.*;
public class ItemMapper {
    private final Connection conn;
    private final Map<Integer,Item> cache;

    public ItemMapper(Connection conn)
    {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public Item findByCatalogNum(int catalogNum) throws SQLException
    {
        if(cache.containsKey(catalogNum))
        {
            return cache.get(catalogNum);
        }
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE catalogNum = ?");
        stmt.setInt(1,catalogNum);
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
        
    }
}
