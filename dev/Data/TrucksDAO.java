package Data;

import Domain.Enums.TempLevel;
import Domain.Enums.TempTypeFactory;
import Domain.Transfer.Truck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrucksDAO {
    private Connection conn = Data.Connection.getConnectionToDatabase();
    private ArrayList<Truck> TruckList;
    private static TrucksDAO instance = null;

    private TrucksDAO() throws SQLException {TruckList = new ArrayList<>();
    }

    public static TrucksDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new TrucksDAO();
        }
        return instance;
    }

    private Truck getFromCache(int licenseNumber){
        for(Truck truck_ : TruckList){
            if(truck_.getLicenseNumber() == licenseNumber) {
                return truck_;
            }
        }
        return null;
    }

    public Map<Integer, Truck> getAllTrucks() {
        /*
            get all trucks from Truck table
        */

        //licenseNumber, model, netWeight, currentWeight, maxWeight, coolingCapacity, unavailableStartDate, unavailableEndDate
        Map<Integer, Truck> trucks = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Truck";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int licenseNumber = rs.getInt("licenseNumber");
                Truck truck = this.createNewTruck(rs,licenseNumber);
                this.TruckList.add(truck);
                trucks.put(licenseNumber, truck);
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
        return trucks;
    }

    public Truck get(int licenseNumber){

        //checking if the truck is in the cache:
        Truck truckFromTable = this.getFromCache(licenseNumber);
        if(truckFromTable != null){
            return truckFromTable;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Truck WHERE licenseNumber = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, licenseNumber);
            rs = stmt.executeQuery();
            if (rs.next()) {
                truckFromTable = this.createNewTruck(rs,licenseNumber);
                this.TruckList.add(truckFromTable);
            }
            else
            {
                System.out.println("No truck found with licenseNumber " + licenseNumber);
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

        return truckFromTable;
    }

    public void update(Truck truck) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Truck SET model=?, netWeight=?, currentWeight=?, maxWeight=?, coolingCapacity=?, unavailableStartDate=?, unavailableEndDate=? WHERE licenseNumber=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, truck.getTruckModel());
            stmt.setInt(2, truck.getTruckNetWeight());
            stmt.setInt(3, truck.getCurrentTruckWeight());
            stmt.setInt(4, truck.getMaxWeight());
            stmt.setString(5, truck.getTempCapacity().name());
            stmt.setString(6, truck.getUnavailableStartTime().toString());
            stmt.setString(7, truck.getUnavailableEndTime().toString());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No truck found with ID " + truck.getLicenseNumber() + " to update");
            } else {
                System.out.println("Truck with ID " + truck.getLicenseNumber() + " updated successfully.");
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


    public void add(Truck truck){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO Truck (licenseNumber, model, netWeight, currentWeight, maxWeight, coolingCapacity, unavailableStartDate, unavailableEndDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, truck.getLicenseNumber());
            stmt.setString(2, truck.getTruckModel());
            stmt.setInt(3, truck.getTruckNetWeight());
            stmt.setInt(4, truck.getCurrentTruckWeight());
            stmt.setInt(5, truck.getMaxWeight());
            stmt.setString(6, truck.getTempCapacity().name());
            stmt.setString(7, truck.getUnavailableStartTime().toString());
            stmt.setString(8, truck.getUnavailableEndTime().toString());
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

    public void delete(int licenseNumber) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM Truck WHERE licenseNumber = ?");
            stmt.setInt(1, licenseNumber);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No Truck found with ID " + licenseNumber);
            } else
            {
                System.out.println("Truck with ID " + licenseNumber + " deleted successfully");
                deleteFromCache(licenseNumber);
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

    public void deleteFromCache(int licenseNumber){
        for (Truck truck : this.TruckList) {
            if (truck.getLicenseNumber() == licenseNumber) {
                this.TruckList.remove(truck.getLicenseNumber());
            }
        }
    }

    private Truck createNewTruck(ResultSet rs,int id) throws SQLException {
        /*
        Create new Truck based on Sql result
         */

        int licenseNumber = rs.getInt("licenseNumber");
        String model = rs.getString("model");
        int netWeight = rs.getInt("netWeight");
        int currentWeight = rs.getInt("currentWeight");
        int maxWeight = rs.getInt("maxWeight");
        TempLevel coolingCapacity = TempTypeFactory.TempLevelFromString(rs.getString("coolingCapacity"));
        String unavailableStartDateString = rs.getString("unavailableStartDate");
        String unavailableStartTimeString = rs.getString("unavailableStartTime");
        String unavailableEndDateString = rs.getString("unavailableEndDate");
        String unavailableEndTimeString = rs.getString("unavailableEndTime");

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate unavailableStartDate = LocalDate.parse(unavailableStartDateString, formatterDate);
        LocalDate unavailableEndDate = LocalDate.parse(unavailableEndDateString, formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime unavailableStartTime = LocalTime.parse(unavailableStartTimeString, formatterTime);
        LocalTime unavailableEndTime = LocalTime.parse(unavailableEndTimeString, formatterTime);

        Truck truck = new Truck(licenseNumber, model, netWeight, currentWeight, maxWeight, coolingCapacity, unavailableStartDate, unavailableStartTime, unavailableEndDate, unavailableEndTime);

        return truck;
    }
}