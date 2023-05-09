package Data;

import Domain.Transfer.Item_mock;
import Domain.Transfer.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferDestinationsDAO {
    private Connection conn = Database.Connection.getConnectionToDatabase();
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
                
                siteToReturn = this.gettingNewSite(rs,id);
                this.SiteList.add(siteToReturn);
            }
            if (destsToReturn == null){
                System.out.println("No site found with ID " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return siteToReturn;



    }


    public void update(int transferId, List<Site> destinations, Map<Site, Map<Item_mock, Integer>> orderItems) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE TransferItems SET transferId=?, catalogNum=?, siteId=?, quantity=? WHERE transferId=? AND ";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, site.getSiteId());
            stmt.setString(2, site.getSiteName());
            stmt.setString(3, site.getSiteAddress());
            stmt.setString(4, site.get_phoneNumber());
            stmt.setString(5, site.get_contactName());
            stmt.setDouble(6, site.getX_coordinate());
            stmt.setDouble(7, site.getY_coordinate());
            stmt.setInt(8, site.getSiteId());


            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No worker found with ID " + site.getSiteId() + " to update.");
            } else {
                System.out.println("Worker with ID " + site.getSiteId() + " updated successfully.");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public void add(Site site){
        try {
            String sql = "INSERT INTO Site (siteId, siteName, address, phoneNumber, contactName, x_Coordinate, y_Coordinate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, site.getSiteId());
            stmt.setString(2, site.getSiteName());
            stmt.setString(3, site.getSiteAddress());
            stmt.setString(4, site.get_phoneNumber());
            stmt.setString(5, site.get_contactName());
            stmt.setDouble(6, site.getX_coordinate());
            stmt.setDouble(7, site.getX_coordinate());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Site WHERE siteId = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No site found with ID " + id);
            } else {
                System.out.println("Site with ID " + id + " deleted successfully");
                deleteFromCache(id);
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

    private Site gettingNewSite(ResultSet rs, int id) throws SQLException {
        int siteId = rs.getInt("siteId");
        String siteName = rs.getString("siteName");
        String address = rs.getString("address");
        String phoneNumber = rs.getString("phoneNumber");
        String contactName = rs.getString("contactName");
        double x_Coordinate = rs.getDouble("x_Coordinate");
        double y_Coordinate = rs.getDouble("x_Coordinate");

        Site siteToReturn = new Site(siteId, siteName, address, phoneNumber, contactName, x_Coordinate, y_Coordinate);
        return siteToReturn;
    }

    public void deleteFromCache(int id){
        for (Site site : this.SiteList) {
            if (site.getSiteId() == id) {
                this.SiteList.remove(site.getSiteId());
            }
        }
    }

    public List<Site> getTransferDestinations(int transferId)
    {
        return destinationsList;
    }

    public Map<Site, Map<Item_mock, Integer>> getTransferItemsWithDestinations(int transferId)
    {
        return orderItemsList;
    }
}
