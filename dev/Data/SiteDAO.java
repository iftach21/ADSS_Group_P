package Data;

import Domain.Employee.Shift;
import Domain.Transfer.Site;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SiteDAO {
    private Connection conn = Database.Connection.getConnectionToDatabase();
    private ArrayList<Site> SiteList;

    private SiteDAO() throws SQLException {SiteList = new ArrayList<>();
    }
}
