package Data;

import Domain.Transfer.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferDestinationsDAO {
    private Connection conn = Data.Connection.getConnectionToDatabase();
    private Map<Integer, List<Site>> destinationsList;  //the key in the first map is transferId
    private static TransferDestinationsDAO instance = null;

    private TransferDestinationsDAO() throws SQLException
    {
        destinationsList = new HashMap<>();
    }

    public static TransferDestinationsDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new TransferDestinationsDAO();
        }
        return instance;
    }


    public List<Site> get(int transferId){
        List<Site> destsToReturn = this.getFromCache(transferId);
        if(destsToReturn != null){return destsToReturn;}

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TransferDestinationsDAO WHERE transferId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Site siteToReturn = SiteDAO.getInstance().get(rs.getInt("siteId"));
                destsToReturn.add(siteToReturn);
            }
            destinationsList.put(transferId, destsToReturn);
            if (destsToReturn == null){
                System.out.println("No transfer found with ID " + transferId);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return destsToReturn;
    }

    public void add(int transferId, List<Site> destinations){
        try {
            String sql = "INSERT INTO TransferDestinations (transferId, siteId) " +
                    "VALUES (?, ?)";
            for (Site dest: destinations) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, transferId);
                stmt.setInt(2, dest.getSiteId());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void delete(int transferId, int siteId) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TransferDestinations WHERE transferId = ? AND siteId = ?");
            stmt.setInt(1, transferId);
            stmt.setInt(1, siteId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No transfer destination found with transfer Id " + transferId + "and site Id" + siteId);
            } else {
                System.out.println("transfer destination with transfer Id " + transferId + "and site Id" + siteId +" deleted successfully");
                deleteFromCache(transferId, siteId);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Site> getFromCache(int transferId){
        for (Integer Id : this.destinationsList.keySet()) {
            if (Id == transferId) {
                return destinationsList.get(Id);
            }
        }
        return null;
    }

    public void deleteFromCache(int transferId, int siteId){
        for (Site site : this.destinationsList.get(transferId)) {
            if (site.getSiteId() == siteId) {
                this.destinationsList.get(transferId).remove(site.getSiteId());
            }
        }
    }
}
