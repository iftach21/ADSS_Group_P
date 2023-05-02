import java.sql.*;
import java.util.*;
import java.sql.Connection;
public class OrderMapper
{
    private final Connection conn;
    private final Map<String,Order> cache;

    public OrderMapper(Connection conn) {
        this.conn = conn;
        this.cache = new HashMap<>();
    }
    public Order findByContractId(String orderNum) throws SQLException
    {
        if(cache.containsKey(orderNum))
        {
            return cache.get(orderNum);
        }
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Orders WHERE order_num = ?");

    }
}
