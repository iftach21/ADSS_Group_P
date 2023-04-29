import java.sql.*;
import java.util.*;
public class SupplierMapper {
    private final Connection conn;
    private final Map<String,Supplier> cache;

    public SupplierMapper(Connection conn) {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public Supplier findBySupplierId()
}
