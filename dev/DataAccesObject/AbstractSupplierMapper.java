package DataAccesObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Domain.*;

public abstract class AbstractSupplierMapper {
    protected Connection conn;
    protected Map<String, Supplier> cache;

    public AbstractSupplierMapper(Connection conn)
    {
        this.conn = conn;
        this.cache = new HashMap<>();
    }

    public abstract Supplier findBySupplierId(String supplierID) throws SQLException;
    public abstract List<Supplier> findAll() throws SQLException;
    public abstract void insert(Supplier supplier) throws SQLException;
    public abstract void update(Supplier supplier) throws SQLException;
    public abstract void delete(Supplier supplier) throws SQLException;




}
