package Data;

import Domain.Transfer.Item_mock;
import Domain.Transfer.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TransferItemsDAO {
    private Connection conn = Data.Connection.getConnectionToDatabase();
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
                System.out.println("No transfer found with ID " + transferId);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

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
            updateCache(transferId, siteId, catalogNum, quantity);

            if (rowsAffected == 0) {
                System.out.println("No transfer found with ID " + transferId + " to update.");
            } else {
                System.out.println("transfer with ID " + transferId + " updated successfully.");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public void add(int transferId, int siteId, String catalogNum, int quantity){
        try {
            String sql = "INSERT or REPLACE INTO TransferItems (transferId, catalogNum, siteId, quantity) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            stmt.setString(2, catalogNum);
            stmt.setInt(3, siteId);
            stmt.setInt(4, quantity);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void delete(int transferId, String catalogNum, int siteId, int quantity) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TransferItems WHERE transferId=? AND catalogNum=? AND siteId=? AND quantity=?");
            stmt.setInt(1, transferId);
            stmt.setString(2, catalogNum);
            stmt.setInt(3, siteId);
            stmt.setInt(4, quantity);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No transfer items found with ID " + transferId);
            } else {
                System.out.println("transfer items with ID " + transferId + " deleted successfully");
                deleteFromCache(transferId, catalogNum, siteId, quantity);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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

    private void updateCache(int transferId, int siteId, String catalogNum, int quantity){
        for (Integer Id : this.orderItemsList.keySet()) {
            if (Id == transferId) {
                for (Site site : this.orderItemsList.get(Id).keySet()) {
                    if (site.getSiteId() == siteId) {
                        for (Item_mock item : this.orderItemsList.get(Id).get(site).keySet()) {
                            if (catalogNum.equals(item.getCatalogNum())) {
                                this.orderItemsList.get(Id).get(site).put(item, quantity);
                            }
                        }
                    }
                }
            }
        }
    }

    public void deleteFromCache(int transferId, String catalogNum, int siteId, int quantity) throws SQLException {
        for (Integer Id : this.orderItemsList.keySet()) {
            if (Id == transferId) {
                for (Site site: this.orderItemsList.get(Id).keySet()) {
                    if (site.getSiteId() == siteId) {
                        for (Item_mock item: this.orderItemsList.get(Id).get(site).keySet()) {
                            if (catalogNum.equals(item.getCatalogNum())) {
                                this.orderItemsList.get(Id).get(site).remove(item);
                                if(this.orderItemsList.get(Id).get(site).size() == 0)
                                {
                                    this.orderItemsList.get(Id).remove(site);
                                }
                                break;
                            }
                        }
                    }
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
                System.out.println("No items found for transfer with ID " + transferId + " and site with ID " + siteId);
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
                System.out.println("Table is empty");
            } else {
                System.out.println("Table deleted successfully");
                orderItemsList.clear();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

