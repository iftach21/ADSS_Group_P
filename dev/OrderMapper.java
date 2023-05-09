import com.google.gson.Gson;
import org.json.JSONObject;

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
    public Order findByOrderNum(String orderNum)
    {
        if(cache.containsKey(orderNum))
        {
            return cache.get(orderNum);
        }
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement("SELECT * FROM Orders WHERE order_num = ?");
            stmt.setString(1, orderNum);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderNum(rs.getInt("order_num"));
                order.setItemList(Parser.parse(rs.getString("item_list")));
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                order.setSupplier(findSupplier(supplierId));
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("statusOrder")));
                cache.put(rs.getInt("order_num"), order);
                return order;
            }
        }
        catch (SQLException e){}
        return null;
    }

    public List<Order> findAll()
    {
        List<Order> orders = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement("SELECT * FROM Orders");
            rs = stmt.executeQuery();
            while (rs.next())
            {
                Order order = new Order();
                order.setOrderNum(rs.getInt("order_num"));
                order.setItemList(Parser.parse(rs.getString("item_list")));
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                order.setSupplier(findSupplier(supplierId));
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("statusOrder")));
                cache.put(rs.getInt("order_num"), order);
                orders.add(order);
            }
        }
        catch (SQLException e){}
        return orders;
    }

    public List <Order> findAllOrderWithStatus(String status)
    {
        List<Order> orders = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
//        String waiting = "Waiting";
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM Orders WHERE statusOrder = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                Order order = new Order();
                order.setOrderNum(rs.getInt("order_num"));
                order.setItemList(Parser.parse(rs.getString("item_list")));
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                order.setSupplier(findSupplier(supplierId));
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("statusOrder")));
                orders.add(order);
            }

        }
        catch (SQLException e){}
        return orders;
    }


    public void insert(Order order)
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("INSERT INTO Orders(order_num,supplier_id,item_list,cost,store_number,statusOrder)VALUES (?,?, ?, ?, ?, ?)");
            stmt.setInt(1, order.getOrderNum());
            Supplier supplier = order.getSupplier();
            stmt.setString(2, supplier.getSupplierID());
            String itemsJson = new JSONObject(order.getItemList()).toString();
            stmt.setString(3, itemsJson);
            stmt.setFloat(4, order.getCost());
            stmt.setInt(5, order.getStore_number());
            stmt.setString(6, order.getStatusOrder().toString());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                order.setOrderNum(rs.getInt(1));
                cache.put(order.getOrderNum(), order);
            }
        }
        catch (SQLException e){}

    }

    public void update(Order order)
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("UPDATE Orders SET supplier_id = ?,  item_list = ?, cost = ?, store_number = ?, statusOrder = ? WHERE order_num = ?");
            stmt.setString(1, order.getSupplier().getSupplierID());
            String itemsJson = new JSONObject(order.getItemList()).toString();
            stmt.setString(2, itemsJson);
            stmt.setFloat(3, order.getCost());
            stmt.setInt(4, order.getStore_number());
            stmt.setString(5, order.getStatusOrder().toString());
            stmt.setInt(6, order.getOrderNum());

            stmt.executeUpdate();
            cache.remove(order.getOrderNum());
            cache.put(order.getOrderNum(), order);// TODO check if there is no duplication after update in the cache
        }
        catch (SQLException e){}

    }

    public void delete(Order order)
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM Orders WHERE order_num = ?");
            stmt.setInt(1, order.getOrderNum());
            stmt.executeUpdate();
            cache.remove(order.getOrderNum());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

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
//            order.setSupplier(nonDeliveringSupplier);
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
//                order.setSupplier(fixedDaySupplier);
                } else {
                    return nonFixedDaySupplier;
//                order.setSupplier(nonFixedDaySupplier);

                }
            }
        }
        catch (SQLException e){}
        return null;
    }
}
