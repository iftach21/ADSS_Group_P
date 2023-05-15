package DataAccesObjects.Transfer;

import DataAccesObjects.Connection;
import Domain.Transfer.Transfer;
import Domain.Transfer.Truck;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferTrucksDAO {
    private java.sql.Connection conn = Connection.getConnectionToDatabase();
    private Map<Integer, List<Transfer>> trucksTransfers;  //the key in the first map is licenseNumber, value is its transfers
    private static TransferTrucksDAO instance = null;

    private TransferTrucksDAO() throws SQLException
    {
        trucksTransfers = new HashMap<>();
    }

    public static TransferTrucksDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new TransferTrucksDAO();
        }
        return instance;
    }


    public List<Transfer> get(int licenseNumber){
        List<Transfer> trucksTransfers;
        trucksTransfers = this.getFromCache(licenseNumber);
        if(trucksTransfers != null){
            return trucksTransfers;
        }
        else{
            trucksTransfers = new ArrayList<>();
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TransferTrucks WHERE licenseNumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, licenseNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Transfer transferToReturn = TransferDAO.getInstance().get(rs.getInt("transferId"));
                trucksTransfers.add(transferToReturn);
                this.trucksTransfers.put(licenseNumber, trucksTransfers);
            }
            if (trucksTransfers.size() == 0){
                //System.out.println("No transfer found for truck with license number " + licenseNumber);
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

        return trucksTransfers;
    }


    public void update(int transferId, int licenseNumber) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE TransferTrucks SET licenseNumber=? WHERE transferId=? AND licenseNumber=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, licenseNumber);
            stmt.setInt(2, transferId);
            stmt.setInt(3, licenseNumber);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                //System.out.println("No transfer found for truck with license number " + licenseNumber + " to update.");
            } else {
                updateCache();
                //System.out.println("truck with license number " + licenseNumber + " and transfer Id " + transferId + " updated successfully.");
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


    public void add(int transferId, int licenseNumber){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT or IGNORE INTO TransferTrucks (licenseNumber, transferId) " +
                    "VALUES (?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, licenseNumber);
            stmt.setInt(2, transferId);

            stmt.executeUpdate();
            addToCache(transferId, licenseNumber);

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

    public void delete(int transferId, int licenseNumber) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferTrucks WHERE licenseNumber=? AND transferId=?");
            stmt.setInt(1, licenseNumber);
            stmt.setInt(2, transferId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No transfer trucks found with ID " + transferId);
            } else {
                //System.out.println("transfer truck with licencse number " + licenseNumber + " deleted successfully");
                deleteFromCache(transferId, licenseNumber);
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

    private List<Transfer> getFromCache(int LicenseNumber){
        for (Integer licenseNum : this.trucksTransfers.keySet()) {
            if (licenseNum == LicenseNumber) {
                 trucksTransfers.get(licenseNum);
                 break;
            }
        }
        return null;
    }

    public void deleteFromCache(int transferId, int licenseNumber) throws SQLException
    {
        for (int i = 0; i < this.trucksTransfers.get(licenseNumber).size(); i++)
        {
            Transfer transfer = this.trucksTransfers.get(licenseNumber).get(i);
            if (transfer.getTransferId() == transferId) {
                this.trucksTransfers.get(licenseNumber).remove(i);
                break;
            }
        }
    }

    public void deleteAll()
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TransferTrucks");
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("Table is empty");
            } else {
                //System.out.println("Table deleted successfully");
                trucksTransfers.clear();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addToCache(int transferId, int licenseNumber) throws SQLException {
        if (trucksTransfers.keySet().contains(licenseNumber))
            trucksTransfers.get(licenseNumber).add(TransferDAO.getInstance().get(transferId));
        else
        {
            List<Transfer> transfersList = new ArrayList<>();
            transfersList.add(TransferDAO.getInstance().get(transferId));
            trucksTransfers.put(licenseNumber, transfersList);
        }
    }

    public void updateCache() throws SQLException {
        trucksTransfers.clear();
        Map<Integer, Truck> trucks = TrucksDAO.getInstance().getAllTrucks();
        for (Integer licenseNumber : trucks.keySet()) {
            if (get(licenseNumber) != null)
                trucksTransfers.put(licenseNumber, get(licenseNumber));
        }
    }


    public void deleteAllByTransferId(int transferId) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferTrucks WHERE transferId = ?");
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

    public void deleteAllByLicenseNumber(int licenseNumber) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM TransferTrucks WHERE licenseNumber = ?");
            stmt.setInt(1, licenseNumber);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                //System.out.println("No truck found with licenseNumber " + licenseNumber);
            } else {
                //System.out.println("truck found with licenseNumber " + licenseNumber);
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
