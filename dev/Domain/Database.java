package Domain;

import java.sql.*;
public class Database {
    private static final String url = "jdbc:sqlite:res/SuperLeeDataBase.db";

    private Database(){}

    public static Connection connect() throws SQLException{
//        Connection connection = null;
//        connection = DriverManager.getConnection(url);
        return DriverManager.getConnection(url);
    }

    public static void closeConnection(Connection connection){
        if (connection != null){
            try{
                connection.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
