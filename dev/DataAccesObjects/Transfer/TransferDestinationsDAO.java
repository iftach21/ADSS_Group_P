package DataAccesObjects.Transfer;

import Domain.Transfer.Site;
import Domain.Transfer.Transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferDestinationsDAO {
    private Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
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

        destsToReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TransferDestinations WHERE transferId = ?";
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
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        if (destsToReturn.isEmpty())
            return null;
        return destsToReturn;
    }

    public void add(int transferId, List<Site> destinations){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT or REPLACE INTO TransferDestinations (transferId, siteId) " +
                    "VALUES (?, ?)";
            for (Site dest: destinations) {
                stmt  = conn.prepareStatement(sql);
                stmt.setInt(1, transferId);
                stmt.setInt(2, dest.getSiteId());
                stmt.executeUpdate();
            }
            destinationsList.put(transferId, destinations);
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

    public void delete(int transferId, int siteId) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferDestinations WHERE transferId = ? AND siteId = ?");
            stmt.setInt(1, transferId);
            stmt.setInt(2, siteId);
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

    private List<Site> getFromCache(int transferId){
        for (Integer Id : this.destinationsList.keySet()) {
            if (Id == transferId) {
                return destinationsList.get(Id);
            }
        }
        return null;
    }

    public void deleteFromCache(int transferId, int siteId){
        for (int i = 0; i < this.destinationsList.get(transferId).size(); i++)
        {
            Site site = this.destinationsList.get(transferId).get(i);
            if (site.getSiteId() == siteId) {
                this.destinationsList.get(transferId).remove(i);
            }
        }
    }

    public void deleteAll()
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TransferDestinations");
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Table is empty");
            } else {
                System.out.println("Table deleted successfully");
                destinationsList.clear();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteAllByTransferId(int transferId)
    {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferDestinations WHERE transferId = ?");
            stmt.setInt(1, transferId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No transfer destination found with transfer Id " + transferId);
            } else {
                System.out.println("transfer destination with transfer Id " + transferId);
                updateCache();
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

    public void deleteAllBySiteId(int siteId)
    {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferDestinations WHERE siteId = ?");
            stmt.setInt(1, siteId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No transfer destination found with site Id " + siteId);
            } else {
                System.out.println("transfer destination with site Id " + siteId);
                updateCache();
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

    private void updateCache() throws SQLException {
        destinationsList.clear();
        Map<Integer, Transfer> transfers = TransferDAO.getInstance().getAllTransfers();
        for (Integer transferId : transfers.keySet()) {
            destinationsList.put(transferId, get(transferId));
        }
    }


}

