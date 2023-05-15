package DataAccesObjects.Transfer;

import Domain.Transfer.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteDAO {
    private Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
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
            String sql = "SELECT * FROM Site WHERE siteID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                siteToReturn = this.gettingNewSite(rs,id);
                this.SiteList.add(siteToReturn);
            } else {
                //System.out.println("No site found with ID " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return siteToReturn;



    }
    public void update(Site site) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Site SET siteID=?, siteName=?, address=?, phoneNumber=?, contactName=?, x_Coordinate=?, y_Coordinate=? WHERE siteID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, site.getSiteId());
            stmt.setString(2, site.getSiteName());
            stmt.setString(3, site.getSiteAddress());
            stmt.setString(4, site.get_phoneNumber());
            stmt.setString(5, site.get_contactName());
            stmt.setDouble(6, site.getLatitude());
            stmt.setDouble(7, site.getLongitude());
            stmt.setInt(8, site.getSiteId());


            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                //System.out.println("No site found with ID " + site.getSiteId() + " to update.");
            } else {
                updateCache(site);
                //System.out.println("Site with ID " + site.getSiteId() + " updated successfully.");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }


    public void add(Site site){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT or REPLACE INTO Site (siteID, siteName, address, phoneNumber, contactName, x_Coordinate, y_Coordinate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, site.getSiteId());
            stmt.setString(2, site.getSiteName());
            stmt.setString(3, site.getSiteAddress());
            stmt.setString(4, site.get_phoneNumber());
            stmt.setString(5, site.get_contactName());
            stmt.setDouble(6, site.getLatitude());
            stmt.setDouble(7, site.getLongitude());
            stmt.executeUpdate();
            addToCache(site);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    public void delete(int id) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM Site WHERE siteID = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No site found with ID " + id);
            } else {
                //System.out.println("Site with ID " + id + " deleted successfully");
                deleteFromCache(id);
                TransferDestinationsDAO.getInstance().deleteAllBySiteId(id);
                TransferItemsDAO.getInstance().deleteAllBySiteId(id);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
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
        double y_Coordinate = rs.getDouble("y_Coordinate");

        Site siteToReturn = new Site(id, siteName, address, phoneNumber, contactName, x_Coordinate, y_Coordinate);
        return siteToReturn;
    }

    public void deleteFromCache(int id){
        for (int i = 0; i < SiteList.size(); i++)
        {
            Site site = SiteList.get(i);
            if (site.getSiteId() == id) {
                this.SiteList.remove(i);
                break;
            }
        }
    }

    public void deleteAll()
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Site");
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("Table is empty");
            } else {
                //System.out.println("Table deleted successfully");
                SiteList.clear();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addToCache(Site site)
    {
        this.SiteList.add(site);
    }

    private void updateCache(Site site){
        for (Site site_ : SiteList) {
            if (site_.getSiteId() == site.getSiteId()) {
                site_.setSiteName(site.getSiteName());
                site_.setSiteAddress(site.getSiteAddress());
                site_.setPhoneNumber(site.get_phoneNumber());
                site_.setContactName(site.get_contactName());
                site_.setLatitude(site.getLatitude());
                site_.setLongitude(site.getLongitude());
            }
        }
    }
}
