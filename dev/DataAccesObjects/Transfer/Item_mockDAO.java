package DataAccesObjects.Transfer;

import Domain.Enums.TempTypeFactory;
import Domain.Transfer.Item_mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Item_mockDAO {
    private Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
    private List<Item_mock> ItemList;
    private static Item_mockDAO instance = null;

    private Item_mockDAO() throws SQLException
    {
        ItemList = new ArrayList<>();
    }

    public static Item_mockDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new Item_mockDAO();
        }
        return instance;
    }

    public Item_mock get(String catalogNum){
        Item_mock item_mockToReturn = this.getFromCache(catalogNum);
        if(item_mockToReturn != null){return item_mockToReturn;}

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Item_mock WHERE catalogNum = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, catalogNum);
            rs = stmt.executeQuery();
            if (rs.next()) {
                item_mockToReturn = this.gettingNewItem_mock(rs,catalogNum);
                this.ItemList.add(item_mockToReturn);
            } else {
                //System.out.println("No item found with catalog num " + catalogNum);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return item_mockToReturn;



    }
    public void update(Item_mock item_mock) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Item_mock SET catalogNum=?, name=?, tempLevel=? WHERE catalogNum=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, item_mock.getCatalogNum());
            stmt.setString(2, item_mock.getItemName());
            stmt.setString(3, item_mock.getItemTemp().name());
            stmt.setString(4, item_mock.getCatalogNum());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                //System.out.println("No item found with catalog num " + item_mock.getCatalogNum() + " to update.");
            } else {
                //System.out.println("item with catalog num " + item_mock.getCatalogNum() + " updated successfully.");
                updateCache(item_mock);
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


    public void add(Item_mock item_mock){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT or REPLACE INTO Item_mock (catalogNum, name, tempLevel) " +
                    "VALUES (?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, item_mock.getCatalogNum());
            stmt.setString(2, item_mock.getItemName());
            stmt.setString(3, item_mock.getItemTemp().name());
            stmt.executeUpdate();
            ItemList.add(item_mock);

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

    public void delete(String catalogNum) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM Item_mock WHERE catalogNum = ?");
            stmt.setString(1, catalogNum);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No item found with catalog num " + catalogNum);
            } else {
                //System.out.println("Item with catalog num " + catalogNum + " deleted successfully");
                deleteFromCache(catalogNum);
                TransferItemsDAO.getInstance().deleteAllByCatalogNum(catalogNum);
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

    private Item_mock getFromCache(String catalogNum){
        for (Item_mock item_mock : this.ItemList) {
            if (catalogNum.equals(item_mock.getCatalogNum())) {
                return item_mock;
            }
        }
        return null;
    }

    private Item_mock gettingNewItem_mock(ResultSet rs, String catalogNum) throws SQLException {
        String name = rs.getString("name");
        String tempLevel = rs.getString("tempLevel");


        Item_mock Item_mockToReturn = new Item_mock(catalogNum, TempTypeFactory.TempLevelFromString(tempLevel), name);
        return Item_mockToReturn;
    }

    public void deleteFromCache(String catalogNum){
        for (int i = 0; i < ItemList.size(); i++)
        {
            Item_mock item_mock = ItemList.get(i);
            if (item_mock.getCatalogNum().equals(catalogNum)) {
                this.ItemList.remove(i);
                break;
            }
        }
    }

    public void deleteAll()
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Item_Mock");
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("Table is empty");
            } else {
                //System.out.println("Table deleted successfully");
                ItemList.clear();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addToCache(Item_mock item_mock)
    {
        this.ItemList.add(item_mock);
    }

    private void updateCache(Item_mock item_mock){
        for (Item_mock itemMock : ItemList) {
            if (item_mock.getCatalogNum().equals(itemMock.getCatalogNum())) {
                itemMock.updateItemTemp(item_mock.getItemTemp());
                itemMock.updateItemName(item_mock.getItemName());
            }
        }
    }
}