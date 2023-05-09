package Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static java.sql.Connection conn = null;
    private Connection(){}

    public static java.sql.Connection getConnectionToDatabase() throws SQLException {
        if(conn == null){
            conn = DriverManager.getConnection("jdbc:sqlite:Transfer_Employee.db");
        }
        return conn;

    }

}
