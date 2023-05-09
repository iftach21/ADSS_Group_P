package Data;

import Domain.Transfer.Truck;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrucksDAO {
    private Connection conn = Database.Connection.getConnectionToDatabase();
    private ArrayList<Truck> TruckList;

    private TrucksDAO() throws SQLException {TruckList = new ArrayList<>();
    }
}