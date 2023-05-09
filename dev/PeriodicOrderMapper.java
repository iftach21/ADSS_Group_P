import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PeriodicOrderMapper {
    private final Connection conn;
    private final Map<Integer,Period_Order> cache;

    public PeriodicOrderMapper(Connection conn) {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public Period_Order findByContractId(String orderNum)
    {
        if(cache.containsKey(orderNum))
        {
            return cache.get(orderNum);
        }
        PreparedStatement stmt;
        ResultSet rs;
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM PeriodicOrders WHERE order_num = ?");
            stmt.setString(1, orderNum);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Period_Order order = new Period_Order();
                order.setOrderNum(rs.getInt("order_num"));
                order.setItemList(Parser.parse(rs.getString("item_list")));
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                order.setSupplier(findSupplier(supplierId));
                cache.put(rs.getInt("order_num"), order);
                return order;
            }
        }
        catch (SQLException e){}
    }





    private Supplier findSupplier(String supplierId)
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
            int flag = 0;
            NonDeliveringSupplierMapper nonMapper = new NonDeliveringSupplierMapper(conn);
            NonDeliveringSupplier nonDeliveringSupplier = nonMapper.findBySupplierId(supplierId);
            if (nonDeliveringSupplier != null) {
                flag++;
                return nonDeliveringSupplier;
            }
            conn.close();
            conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
            NonFixedDaySupplierMapper nonFixedDaySupplierMapper = new NonFixedDaySupplierMapper(conn);
            if (flag == 0) {
                NonFixedDaySupplier nonFixedDaySupplier = nonFixedDaySupplierMapper.findBySupplierId(supplierId);
                if (nonFixedDaySupplier == null) {
                    conn.close();
                    conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
                    FixedDaySupplierMapper fixedDaySupplierMapper = new FixedDaySupplierMapper(conn);
                    FixedDaySupplier fixedDaySupplier = fixedDaySupplierMapper.findBySupplierId(supplierId);
                    return fixedDaySupplier;
                } else {
                    return nonFixedDaySupplier;

                }
            }
        }
        catch (SQLException e){}
        return null;
    }
}
