import java.sql.*;
import java.util.*;
import org.json.*;

public class ContractMapper {
    private final Connection conn;
    private final Map<Integer,Contract> cache;

    public ContractMapper(Connection conn)
    {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public Contract findByContractId(int contractId) throws SQLException
    {
        if(cache.containsKey(contractId))
        {
            return cache.get(contractId);
        }
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contracts WHERE contract_id = ?");
        stmt.setInt(1,contractId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            Contract contract = new Contract();
            contract.setContractId(rs.getInt("contract_id"));
            contract.setSupplierId(rs.getString("supplier_id"));
            contract.setTotalDiscount(rs.getDouble("total_discount"));
            String itemsMapJson = rs.getString("items_Map_discount");

            Type itemsMapType = new TypeToken<Map<Item, Map<Integer, Double>>>(){}.getType();
            Map<Item, Map<Integer, Double>> itemsMapDiscount = new Gson().fromJson(itemsJson, itemsMapType);


        }
    }
}
