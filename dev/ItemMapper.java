import java.sql.*;
import java.util.*;
public class ItemMapper {
    private Connection conn;
    private Map<Integer,Item> cache;

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

        }
    }
}
