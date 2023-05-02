import com.google.gson.Gson;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
public class OrderMapper
{
    private final Connection conn;
    private final Map<Integer,Order> cache;

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
        stmt.setString(1,orderNum);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            Order order = new Order();
            order.setOrderNum(rs.getInt("order_num"));
            order.setItemList(Parser.parse(rs.getString("item_list")));
            order.setCost(rs.getFloat("cost"));
            order.setStore_number(rs.getInt("store_number"));
            String supplierId = rs.getString("supplier_id");
            order.setSupplier(findSupplier(supplierId));
            cache.put(rs.getInt("order_num"),order);
            return order;
        }
        return null;
    }

    public List<Order> findAll() throws SQLException
    {
        List<Order> orders = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Orders");
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            Order order = new Order();
            order.setOrderNum(rs.getInt("order_num"));
            order.setItemList(Parser.parse(rs.getString("item_list")));
            order.setCost(rs.getFloat("cost"));
            order.setStore_number(rs.getInt("store_number"));
            String supplierId = rs.getString("supplier_id");
            order.setSupplier(findSupplier(supplierId));
            cache.put(rs.getInt("order_num"),order);
            orders.add(order);
        }
        return orders;
    }

    public void insert(Order order) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Orders(order_num,supplier_id,item_list,cost,store_number)VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1,order.getOrderNum());
        Supplier supplier = order.getSupplier();
        stmt.setString(2,supplier.getSupplierID());
        String itemsJson = new Gson().toJson(order.getItemList()).toString();
        stmt.setString(3,itemsJson);
        stmt.setFloat(4,order.getCost());
        stmt.setInt(5,order.getStore_number());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();

        if(rs.next())
        {
            order.setOrderNum(rs.getInt(1));
            cache.put(order.getOrderNum(),order);
        }
    }

    public void update(Order order) throws SQLException
    {

    }

    public Supplier findSupplier(String supplierId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        int flag = 0;
        NonDeliveringSupplierMapper nonMapper = new NonDeliveringSupplierMapper(conn);
        NonDeliveringSupplier nonDeliveringSupplier = nonMapper.findBySupplierId(supplierId);
        if(nonDeliveringSupplier != null)
        {
            flag ++;
            return nonDeliveringSupplier;
//            order.setSupplier(nonDeliveringSupplier);
        }
        conn.close();
        conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        NonFixedDaySupplierMapper nonFixedDaySupplierMapper = new NonFixedDaySupplierMapper(conn);
        if(flag == 0)
        {
            NonFixedDaySupplier nonFixedDaySupplier = nonFixedDaySupplierMapper.findBySupplierId(supplierId);
            if(nonFixedDaySupplier == null)
            {
                conn.close();
                conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
                FixedDaySupplierMapper fixedDaySupplierMapper = new FixedDaySupplierMapper(conn);
                FixedDaySupplier fixedDaySupplier = fixedDaySupplierMapper.findBySupplierId(supplierId);
                return fixedDaySupplier;
//                order.setSupplier(fixedDaySupplier);
            }
            else
            {
                return nonFixedDaySupplier;
//                order.setSupplier(nonFixedDaySupplier);

            }
        }
        return null;
    }
}
