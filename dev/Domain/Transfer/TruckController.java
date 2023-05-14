package Domain.Transfer;

import DataAccesObjects.Transfer.TrucksDAO;
import Domain.Employee.Driver;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class TruckController {
    private static TruckController Instance = null;
    //public Map<Integer, Truck> _trucks;
    private final TrucksDAO trucksDAO;

    public TruckController() throws SQLException {
        this.trucksDAO = TrucksDAO.getInstance();
    }

    public static TruckController getInstance() throws SQLException {
        if(Instance==null){Instance = new TruckController();}
        return Instance;
    }

    public Map<Integer, Truck> getAvailableTrucksOfLightWeight(LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime)
    {
        Map<Integer,Truck> allTrucks = trucksDAO.getAllTrucks();
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : allTrucks.keySet())
        {
            LocalDateTime leaving = LocalDateTime.of(leavingDate, leavingTime);
            LocalDateTime arriving = LocalDateTime.of(arrivingDate, arrivingTime);

            if(!allTrucks.get(licenseNumber).getIsUsedInDate(leaving, arriving) && allTrucks.get(licenseNumber).isLightWeight()){
                availableTrucks.put(allTrucks.get(licenseNumber).getLicenseNumber(), allTrucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfMiddleWeight(LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime)
    {
        Map<Integer,Truck> allTrucks = trucksDAO.getAllTrucks();
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : allTrucks.keySet()){

            LocalDateTime leaving = LocalDateTime.of(leavingDate, leavingTime);
            LocalDateTime arriving = LocalDateTime.of(arrivingDate, arrivingTime);

            if(!allTrucks.get(licenseNumber).getIsUsedInDate(leaving, arriving) && allTrucks.get(licenseNumber).isMiddleWeight()){
                availableTrucks.put(allTrucks.get(licenseNumber).getLicenseNumber(), allTrucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfHeavyWeight(LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime)
    {
        Map<Integer,Truck> allTrucks = trucksDAO.getAllTrucks();
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : allTrucks.keySet()){

            LocalDateTime leaving = LocalDateTime.of(leavingDate, leavingTime);
            LocalDateTime arriving = LocalDateTime.of(arrivingDate, arrivingTime);

            if(!allTrucks.get(licenseNumber).getIsUsedInDate(leaving, arriving) && allTrucks.get(licenseNumber).isHeavyWeight()){
                availableTrucks.put(allTrucks.get(licenseNumber).getLicenseNumber(), allTrucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAllAvailableTrucks(LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime)
    {
        Map<Integer, Truck> availableTrucks;
        availableTrucks = getAvailableTrucksOfHeavyWeight(leavingDate, leavingTime, arrivingDate, arrivingTime);
        Map<Integer, Truck> availableTrucksMiddleWeight = getAvailableTrucksOfMiddleWeight(leavingDate, leavingTime, arrivingDate, arrivingTime);
        Map<Integer, Truck> availableTrucksLightWeight = getAvailableTrucksOfLightWeight(leavingDate, leavingTime, arrivingDate, arrivingTime);
        availableTrucksMiddleWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
        availableTrucksLightWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));

        return availableTrucks;
    }

    public Truck findTruckByDriver(Driver chosenDriver, TempLevel currMinTemp, LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime) {
        Map<Integer, Truck> availableTrucks;

        availableTrucks = getAvailableTrucksOfLightWeight(leavingDate, leavingTime, arrivingDate, arrivingTime);

        if (chosenDriver.getDriverLicense().getLicenseWeightCapacity() == weightType.mediumWeight || chosenDriver.getDriverLicense().getLicenseWeightCapacity() == weightType.heavyWeight)
        {
            Map<Integer, Truck> availableTrucksMiddleWeight = getAvailableTrucksOfMiddleWeight(leavingDate, leavingTime, arrivingDate, arrivingTime);
            availableTrucks.putAll(availableTrucksMiddleWeight);
        }
        if (chosenDriver.getDriverLicense().getLicenseWeightCapacity() == weightType.heavyWeight)
        {
            Map<Integer, Truck> availableTrucksHeavyWeight = getAvailableTrucksOfHeavyWeight(leavingDate, leavingTime, arrivingDate, arrivingTime);
            availableTrucks.putAll(availableTrucksHeavyWeight);
        }

        Truck chosenTruck = null;

        for (Integer truckId: availableTrucks.keySet()) {
            if (availableTrucks.get(truckId).getTempCapacity().compareTo(chosenDriver.getDriverLicense().getLicenseTempCapacity()) <= 0 && availableTrucks.get(truckId).getTempCapacity().compareTo(currMinTemp) >= 0)
            {
                chosenTruck = availableTrucks.get(truckId);
                break;
            }
        }

        if (chosenTruck != null)
            return chosenTruck;
        return null;
    }

    public void addTruck(Truck newTrack)
    {
        trucksDAO.add(newTrack);
    }

    public Truck getTruck(int licenseNumnber)
    {
        Truck truck = trucksDAO.get(licenseNumnber);
        return truck;
    }

    public void updateTruck(Truck truck)
    {
        trucksDAO.update(truck);
    }
}
