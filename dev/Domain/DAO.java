package Domain;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public List <T> getAll() throws SQLException;
    public T getById(String id) throws SQLException;
    public void insert(T t) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(T t) throws SQLException;
}
