package Data;

import Domain.Transfer.Transfer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransferDAO {
    private Connection conn = Database.Connection.getConnectionToDatabase();
    private ArrayList<Transfer> TransferList;

    private TransferDAO() throws SQLException {TransferList = new ArrayList<>();
    }
}