package DataAccesObjects.Transfer;

import Domain.Transfer.Item_mock;
import Domain.Transfer.Site;
import Domain.Transfer.Transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TransferItemsDAO {
    private Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
    private Map<Integer, Map<Site, Map<Item_mock, Integer>>> orderItemsList;  //the key in the first map is transferId
    private static TransferItemsDAO instance = null;

    private TransferItemsDAO() throws SQLException
    {
        orderItemsList = new HashMap<>();
    }

    public static TransferItemsDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new TransferItemsDAO();
        }
        return instance;
    }


    public Map<Site, Map<Item_mock, Integer>> get(int transferId){
        Map<Site, Map<Item_mock, Integer>> orderItemsToReturn;
        orderItemsToReturn = this.getFromCache(transferId);
        if(orderItemsToReturn != null){
            return orderItemsToReturn;
        }
        else{
            orderItemsToReturn = new HashMap<>();
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TransferItems WHERE transferId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int siteId = rs.getInt("siteId");
                Site site = SiteDAO.getInstance().get(siteId);
                orderItemsToReturn.put(site, getItemsBySiteId(transferId, siteId));
                this.orderItemsList.put(transferId, orderItemsToReturn);
            }
            if (orderItemsToReturn.size() == 0){
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
        if (orderItemsToReturn.isEmpty())
            return null;
        return orderItemsToReturn;
    }


    public void update(int transferId, int siteId, String catalogNum, int quantity) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE TransferItems SET transferId=?, catalogNum=?, siteId=?, quantity=? WHERE transferId=? AND catalogNum=? AND siteId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            stmt.setString(2, catalogNum);
            stmt.setInt(3, siteId);
            stmt.setInt(4, quantity);
            stmt.setInt(5, transferId);
            stmt.setString(6, catalogNum);
            stmt.setInt(7, siteId);

            int rowsAffected = stmt.executeUpdate();
            updateCache();

            if (rowsAffected == 0) {
                //System.out.println("No transfer found with ID " + transferId + " to update.");
            } else {
                //System.out.println("transfer with ID " + transferId + " updated successfully.");
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


    public void add(int transferId, int siteId, String catalogNum, int quantity){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT or REPLACE INTO TransferItems (transferId, catalogNum, siteId, quantity) " +
                    "VALUES (?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            stmt.setString(2, catalogNum);
            stmt.setInt(3, siteId);
            stmt.setInt(4, quantity);

            stmt.executeUpdate();
            addToCache(transferId, siteId, catalogNum, quantity);

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

    public void delete(int transferId, String catalogNum, int siteId, int quantity) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferItems WHERE transferId=? AND catalogNum=? AND siteId=? AND quantity=?");
            stmt.setInt(1, transferId);
            stmt.setString(2, catalogNum);
            stmt.setInt(3, siteId);
            stmt.setInt(4, quantity);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No transfer items found with ID " + transferId);
            } else {
                //System.out.println("transfer items with ID " + transferId + " deleted successfully");
                deleteFromCache(transferId, catalogNum, siteId, quantity);
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

    private Map<Site, Map<Item_mock, Integer>> getFromCache(int transferId){
        for (Integer Id : this.orderItemsList.keySet()) {
            if (Id == transferId) {
                return orderItemsList.get(Id);
            }
        }
        return null;
    }

    private void updateCache() throws SQLException {
        orderItemsList.clear();
        Map<Integer, Transfer> transfers = TransferDAO.getInstance().getAllTransfers();
        for (Integer transferId : transfers.keySet()) {
            orderItemsList.put(transferId, get(transferId));
        }
    }

    public void deleteFromCache(int transferId, String catalogNum, int siteId, int quantity) throws SQLException {
        Iterator<Integer> idIterator = orderItemsList.keySet().iterator();
        while (idIterator.hasNext()) {
            Integer Id = idIterator.next();
            if (Id == transferId) {
                Map<Site, Map<Item_mock, Integer>> siteMap = orderItemsList.get(Id);
                Iterator<Site> siteIterator = siteMap.keySet().iterator();
                while (siteIterator.hasNext()) {
                    Site site = siteIterator.next();
                    if (site.getSiteId() == siteId) {
                        Map<Item_mock, Integer> itemMap = siteMap.get(site);
                        Iterator<Item_mock> itemIterator = itemMap.keySet().iterator();
                        while (itemIterator.hasNext()) {
                            Item_mock item = itemIterator.next();
                            if (catalogNum.equals(item.getCatalogNum())) {
                                itemIterator.remove();
                                if (itemMap.isEmpty()) {
                                    siteIterator.remove();
                                }
                                break;
                            }
                        }
                    }
                }
                if (siteMap.isEmpty()) {
                    idIterator.remove();
                }
            }
        }
    }

    public Map<Item_mock, Integer> getItemsBySiteId(int transferId, int siteId)
    {
        Map<Item_mock, Integer> itemsOfSite = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TransferItems WHERE transferId = ? AND siteId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            stmt.setInt(2, siteId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Item_mock item_mock = Item_mockDAO.getInstance().get(rs.getString("catalogNum"));
                int quantity = rs.getInt("quantity");
                itemsOfSite.put(item_mock, quantity);
            }
            if (itemsOfSite.size() == 0){
                //System.out.println("No items found for transfer with ID " + transferId + " and site with ID " + siteId);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return itemsOfSite;
    }

    public void deleteAll()
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TransferItems");
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("Table is empty");
            } else {
                //System.out.println("Table deleted successfully");
                orderItemsList.clear();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addToCache(int transferId, int siteId, String catalogNum, int quantity) throws SQLException {
        // Update orderItemsList map
        if (!orderItemsList.containsKey(transferId)) {
            orderItemsList.put(transferId, new HashMap<>());
        }

        Map<Site, Map<Item_mock, Integer>> siteMap = orderItemsList.get(transferId);
        Site site = SiteDAO.getInstance().get(siteId);

        if (!siteMap.containsKey(site)) {
            siteMap.put(site, new HashMap<>());
        }

        Map<Item_mock, Integer> itemMap = siteMap.get(site);
        Item_mock item = Item_mockDAO.getInstance().get(catalogNum);

        itemMap.put(item, quantity);
    }

    public void deleteAllByTransferId(int transferId)
    {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferItems WHERE transferId = ?");
            stmt.setInt(1, transferId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No transfer destination found with transfer Id " + transferId);
            } else {
                //System.out.println("transfer destination with transfer Id " + transferId);
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
            stmt = conn.prepareStatement("DELETE FROM TransferItems WHERE siteId = ?");
            stmt.setInt(1, siteId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No transfer destination found with site Id " + siteId);
            } else {
                //System.out.println("transfer destination with site Id " + siteId);
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

    public void deleteAllByCatalogNum(String catalogNum)
    {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferItems WHERE catalogNum = ?");
            stmt.setString(1, catalogNum);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No item found with catalog Id " + catalogNum);
            } else {
                //System.out.println("item with catalog Id " + catalogNum);
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
}

