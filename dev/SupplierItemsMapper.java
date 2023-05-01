/*import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierItemsMapper {
    private final Connection conn;
    public SupplierItemsMapper(Connection conn) {
        this.conn = conn;
    }
    public Map<Item, Pair<Integer, Float>> findAllSupplierId(String SupplierId) throws SQLException {
        ItemMapper itemMapper = new ItemMapper(conn);

        List<Item> items = itemMapper.findAllByCatalogNum(catalogNum);
        List<Integer> amount = new ArrayList<>();
        List<Float> prices = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT amount, price FROM SupplierItems WHERE item_catalog_number = ?");
        ResultSet rs = stmt.executeQuery();
        int counter = 0;
        while (rs.next())
        {

        }
        return null;
    }
}*/
