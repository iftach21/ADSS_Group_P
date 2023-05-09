package Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static java.sql.Connection conn = null;
    private Connection(){}

    public static java.sql.Connection getConnectionToDatabase() throws SQLException {
        if(conn == null){

            String currentPath  = System.getProperty("user.dir");
            String dbName = "Transfer_Employee.db";
            String dbPath = currentPath + "\\" + dbName;

            conn = DriverManager.getConnection(dbPath);
        }
        return conn;

    }

}
