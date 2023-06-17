package DataAccesObjects.Transfer;

import Domain.Transfer.Site;
import Domain.Transfer.Transfer;
import Domain.Transfer.Truck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TransferDestinationsDAO {
    private Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
    private Map<Integer, Map<Site, Integer>> destinationsList;  //the key in the first map is transferId
    private static TransferDestinationsDAO instance = null;

    private TransferDestinationsDAO() throws SQLException
    {
        destinationsList = new LinkedHashMap<>();
    }

    public static TransferDestinationsDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new TransferDestinationsDAO();
        }
        return instance;
    }


    public Map<Site, Integer> get(int transferId){
        Map<Site, Integer> destsAndWeightsToReturn1 = this.getFromCache(transferId);
        if(destsAndWeightsToReturn1 != null){return destsAndWeightsToReturn1;}
        Map<Site, Integer> destsAndWeightsToReturn = new LinkedHashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TransferDestinations WHERE transferId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Site siteToReturn = SiteDAO.getInstance().get(rs.getInt("siteId"));
                int weightInSite =  rs.getInt("weightInSite");
                destsAndWeightsToReturn.put(siteToReturn, weightInSite);
            }
            destinationsList.put(transferId, destsAndWeightsToReturn);
            if (destsAndWeightsToReturn == null){
                //System.out.println("No transfer found with ID " + transferId);
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
        if (destsAndWeightsToReturn.isEmpty())
            return null;
        return destsAndWeightsToReturn;
    }

    public void add(int transferId, Map<Site, Integer> destinations){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT or REPLACE INTO TransferDestinations (transferId, siteId, weightInSite) " +
                    "VALUES (?, ?, ?)";
            for (Site dest: destinations.keySet()) {
                stmt  = conn.prepareStatement(sql);
                stmt.setInt(1, transferId);
                stmt.setInt(2, dest.getSiteId());
                stmt.setInt(3, destinations.get(dest));
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
                //System.out.println("transfer destination with transfer Id " + transferId + "and site Id" + siteId +" deleted successfully");
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

    private Map<Site, Integer> getFromCache(int transferId){
        for (Integer Id : this.destinationsList.keySet()) {
            if (Id == transferId) {
                return destinationsList.get(Id);
            }
        }
        return null;
    }

    public void deleteFromCache(int transferId, int siteId) throws SQLException {
        Map<Site, Integer> innerMap = destinationsList.get(transferId);

        if (innerMap != null) {
            // If the inner map is not null, remove the entry with the given site
            innerMap.remove(SiteDAO.getInstance().get(siteId));

            // If the inner map is now empty, remove the entire entry from the outer map
            if (innerMap.isEmpty()) {
                destinationsList.remove(transferId);
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

    public void update(int transferId, int siteId, int weight) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE TransferDestinations SET weightInSite=? WHERE transferId=? AND siteId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, weight);
            stmt.setInt(2, transferId);
            stmt.setInt(3, siteId);

            int rowsAffected = stmt.executeUpdate();
            updateCache();

            if (rowsAffected == 0) {
                System.out.println("No transfer found with ID " + transferId + " to update.");
            } else {
                System.out.println("transfer with ID " + transferId + " updated successfully.");
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
}

