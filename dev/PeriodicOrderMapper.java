import com.google.gson.Gson;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                order.setDays_to_cycle(rs.getInt("days_to_cycle"));
                order.setDay_left(rs.getInt("day_left"));
                cache.put(rs.getInt("order_num"), order);
                return order;
            }
        }
        catch (SQLException e){}
        return null;
    }

    public List<Period_Order> findAll()
    {
        List<Period_Order> orders = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM PeriodicOrders");
            rs = stmt.executeQuery();
            while (rs.next())
            {
                Period_Order order = new Period_Order();
                order.setOrderNum(rs.getInt("order_num"));
                order.setItemList(Parser.parse(rs.getString("item_list")));
                order.setCost(rs.getFloat("cost"));
                order.setStore_number(rs.getInt("store_number"));
                String supplierId = rs.getString("supplier_id");
                order.setSupplier(findSupplier(supplierId));
                order.setDays_to_cycle(rs.getInt("days_to_cycle"));
                order.setDay_left(rs.getInt("day_left"));
                cache.put(rs.getInt("order_num"), order);
                orders.add(order);
            }
        }
        catch (SQLException e){}
        return orders;
    }

    public void insert(Period_Order order)
    {
        PreparedStatement stmt;
        try
        {
            stmt = conn.prepareStatement("INSERT INTO PeriodicOrders(order_num,supplier_id,item_list,cost,store_number,days_to_cycle,day_left)VALUES (?, ?, ?, ?, ?,?,?)");
            stmt.setInt(1, order.getOrderNum());
            Supplier supplier = order.getSupplier();
            stmt.setString(2, supplier.getSupplierID());
            String itemsJson = new Gson().toJson(order.getItemList()).toString();
            stmt.setString(3, itemsJson);
            stmt.setFloat(4, order.getCost());
            stmt.setInt(5, order.getStore_number());
            stmt.setInt(6, order.getDays_to_cycle());
            stmt.setInt(7, order.getDay_left());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                order.setOrderNum(rs.getInt(1));
                cache.put(order.getOrderNum(), order);
            }
        }
        catch (SQLException e){}

    }

    public void update(Period_Order order)
    {
        PreparedStatement stmt;
        try
        {
            stmt = conn.prepareStatement("UPDATE PeriodicOrders SET supplier_id = ?,  item_list = ?, cost = ?, store_number = ?, days_to_cycle = ?, day_left = ? WHERE order_num = ?");
            stmt.setString(1, order.getSupplier().getSupplierID());
            String itemsJson = new JSONObject(order.getItemList()).toString();
            stmt.setString(2, itemsJson);
            stmt.setFloat(3, order.getCost());
            stmt.setInt(4, order.getStore_number());
            stmt.setInt(5, order.getDays_to_cycle());
            stmt.setInt(6, order.getDay_left());
            stmt.setInt(7, order.getOrderNum());
            cache.remove(order.getOrderNum());
            cache.put(order.getOrderNum(), order);
        }
        catch (SQLException e){}

    }

    public void delete(Period_Order order)
    {
        PreparedStatement stmt;
        try
        {
            stmt = conn.prepareStatement("DELETE FROM PeriodicOrders WHERE order_num = ?");
            stmt.setInt(1, order.getOrderNum());
            stmt.executeUpdate();
            cache.remove(order.getOrderNum());
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
