package Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static java.sql.Connection conn = null;
    private Connection(){}

    public static java.sql.Connection getConnectionToDatabase() throws SQLException {
        if(conn == null){
            //todo:mabe i need to get it somehow else???
            conn = DriverManager.getConnection("C:\\Users\\97254\\Documents\\GitHub\\ADSS_Group_P\\ADSS_Group_P\\dev\\Database\\Transfer_Employee");
        }
        return conn;
    }
}
