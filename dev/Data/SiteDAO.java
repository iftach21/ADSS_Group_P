package Data;

import Domain.Transfer.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteDAO {
    private Connection conn = Data.Connection.getConnectionToDatabase();
    private List<Site> SiteList;
    private static SiteDAO instance = null;

    private SiteDAO() throws SQLException
    {
        SiteList = new ArrayList<>();
    }

    public static SiteDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new SiteDAO();
        }
        return instance;
    }

    public Site get(int id){
        Site siteToReturn = this.getFromCache(id);
        if(siteToReturn != null){return siteToReturn;}

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Site WHERE siteId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                siteToReturn = this.gettingNewSite(rs,id);
                this.SiteList.add(siteToReturn);
            } else {
                System.out.println("No site found with ID " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return siteToReturn;



    }
    public void update(Site site) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Site SET siteId=?, siteName=?, address=?, phoneNumber=?, contactName=?, x_Coordinate=?, y_Coordinate=? WHERE siteId=?";
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
                System.out.println("No site found with ID " + site.getSiteId() + " to update.");
            } else {
                System.out.println("Site with ID " + site.getSiteId() + " updated successfully.");
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

    private Site getFromCache(int id){
        for (Site site : this.SiteList) {
            if (id == site.getSiteId()) {
                return site;
            }
        }
        return null;
    }

    private Site gettingNewSite(ResultSet rs, int id) throws SQLException {
        String siteName = rs.getString("siteName");
        String address = rs.getString("address");
        String phoneNumber = rs.getString("phoneNumber");
        String contactName = rs.getString("contactName");
        double x_Coordinate = rs.getDouble("x_Coordinate");
        double y_Coordinate = rs.getDouble("x_Coordinate");

        Site siteToReturn = new Site(id, siteName, address, phoneNumber, contactName, x_Coordinate, y_Coordinate);
        return siteToReturn;
    }

    public void deleteFromCache(int id){
        for (Site site : this.SiteList) {
            if (site.getSiteId() == id) {
                this.SiteList.remove(site.getSiteId());
            }
        }
    }
}
