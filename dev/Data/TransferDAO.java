package Data;

import Domain.Transfer.Item_mock;
import Domain.Transfer.Site;
import Domain.Transfer.Transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferDAO {
    private Connection conn = Data.Connection.getConnectionToDatabase();
    private ArrayList<Transfer> TransferList;
    private static TransferDAO instance = null;

    private TransferDAO() throws SQLException {TransferList = new ArrayList<>();
    }

    public static TransferDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new TransferDAO();
        }
        return instance;
    }

    private Transfer getFromCache(int transferId){
        for(Transfer transfer : TransferList){
            if(transfer.getTransferId() == transferId) {
                return transfer;
            }
        }
        return null;
    }

    public Map<Integer, Transfer> getAllTransfers() {
        /*
            get all transfers from Transfer table
        */

        Map<Integer, Transfer> transfers = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Transfer";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int transferId = rs.getInt("transferId");
                Transfer transfer = this.createNewTransfer(rs);
                this.TransferList.add(transfer);
                transfers.put(transferId, transfer);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
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
        return transfers;
    }

    public Transfer get(int transferId){

        //checking if the transfer is in the cache:
        Transfer transferFromTable = this.getFromCache(transferId);
        if(transferFromTable != null){
            return transferFromTable;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Transfer WHERE transferId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transferId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                transferFromTable = this.createNewTransfer(rs);
                this.TransferList.add(transferFromTable);
            }
            else
            {
                System.out.println("No truck found with licenseNumber " + transferId);
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

        return transferFromTable;
    }

    public void update(Transfer transfer) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Transfer SET dateOfTransfer=?, leavingTime=?, arrivingDate=?, arrivingTime=?, truckLicenseNumber=?, driverName=?, sourceId=? WHERE transferId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, transfer.getDateOfTransfer().toString());
            stmt.setString(2, transfer.getLeavingTime().toString());
            stmt.setString(3, transfer.getArrivingDate().toString());
            stmt.setString(4, transfer.get_arrivingTime().toString());
            stmt.setInt(5, transfer.getTruckLicenseNumber());
            stmt.setString(6, transfer.getDriverName());
            stmt.setInt(7, transfer.getSource().getSiteId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No transfer found with ID " + transfer.getTransferId() + " to update");
            } else {
                System.out.println("Transfer with ID " + transfer.getTransferId() + " updated successfully.");
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


    public void add(Transfer transfer){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO Truck (transferId, dateOfTransfer, leavingTime, arrivingDate, arrivingTime, truckLicenseNumber, driverName, sourceId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, transfer.getTransferId());
            stmt.setString(2, transfer.getDateOfTransfer().toString());
            stmt.setString(3, transfer.getLeavingTime().toString());
            stmt.setString(4, transfer.getArrivingDate().toString());
            stmt.setString(5, transfer.get_arrivingTime().toString());
            stmt.setInt(6, transfer.getTruckLicenseNumber());
            stmt.setString(7, transfer.getDriverName());
            stmt.setInt(8, transfer.getSource().getSiteId());
            stmt.executeUpdate();

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

    public void delete(int transferId) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM Transfer WHERE transferId = ?");
            stmt.setInt(1, transferId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Transfer found with ID " + transferId);
            } else
            {
                System.out.println("Transfer with ID " + transferId + " deleted successfully");
                deleteFromCache(transferId);
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

    public void deleteFromCache(int transferId){
        for (Transfer transfer : this.TransferList) {
            if (transfer.getTransferId() == transferId) {
                this.TransferList.remove(transfer.getTransferId());
            }
        }
    }

    private Transfer createNewTransfer(ResultSet rs) throws SQLException {
        /*
        Create a new Truck based on Sql result
         */

        //transferId, dateOfTransfer, leavingTime, arrivingDate, arrivingTime, truckLicenseNumber, driverName, sourceId
        int transferId = rs.getInt("transferId");
        String dateOfTransferString = rs.getString("dateOfTransfer");
        String leavingTimeString = rs.getString("leavingTime");
        String arrivingDateString = rs.getString("arrivingDate");
        String arrivingTimeString = rs.getString("arrivingTime");
        int truckLicenseNumber = rs.getInt("truckLicenseNumber");
        String driverName = rs.getString("driverName");
        int sourceId = rs.getInt("sourceId");

        Site site = SiteDAO.getInstance().get(sourceId);

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfTransfer = LocalDate.parse(dateOfTransferString, formatterDate);
        LocalDate arrivingDate = LocalDate.parse(arrivingDateString, formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime leavingTime = LocalTime.parse(leavingTimeString, formatterTime);
        LocalTime arrivingTime = LocalTime.parse(arrivingTimeString, formatterTime);

        List<Site> transferDestination = TransferDestinationsDAO.getInstance().get(transferId);
        Map<Site, Map<Item_mock, Integer>> transferOrderItems = TransferItemsDAO.getInstance().get(transferId);

        Transfer transfer = new Transfer(dateOfTransfer, leavingTime, arrivingDate, arrivingTime, truckLicenseNumber, driverName, site, transferDestination, transferOrderItems, transferId);

        return transfer;
    }
}