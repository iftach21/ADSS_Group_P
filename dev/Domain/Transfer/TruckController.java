package Domain.Transfer;

import Domain.Employee.Driver;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TruckController {
    public Map<Integer, Truck> _trucks;

    public TruckController(Map<Integer, Truck> trucks)
    {
        this._trucks = trucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfLightWeight(LocalDate leavingDate)
    {
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : _trucks.keySet()){
            if(!_trucks.get(licenseNumber).getIsUsedInDate(leavingDate) && _trucks.get(licenseNumber).isLightWeight()){
                availableTrucks.put(_trucks.get(licenseNumber).getLicenseNumber(), _trucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfMiddleWeight(LocalDate leavingDate)
    {
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : _trucks.keySet()){
            if(!_trucks.get(licenseNumber).getIsUsedInDate(leavingDate) && _trucks.get(licenseNumber).isMiddleWeight()){
                availableTrucks.put(_trucks.get(licenseNumber).getLicenseNumber(), _trucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfHeavyWeight(LocalDate leavingDate)
    {
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : _trucks.keySet()){
            if(!_trucks.get(licenseNumber).getIsUsedInDate(leavingDate) && _trucks.get(licenseNumber).isHeavyWeight()){
                availableTrucks.put(_trucks.get(licenseNumber).getLicenseNumber(), _trucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAllAvailableTrucks(LocalDate leavingDate)
    {
        Map<Integer, Truck> availableTrucks;
        availableTrucks = getAvailableTrucksOfHeavyWeight(leavingDate);
        Map<Integer, Truck> availableTrucksMiddleWeight = getAvailableTrucksOfMiddleWeight(leavingDate);
        Map<Integer, Truck> availableTrucksLightWeight = getAvailableTrucksOfLightWeight(leavingDate);
        availableTrucksMiddleWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
        availableTrucksLightWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));

        return availableTrucks;
    }

    public Truck findTruckByDriver(Driver chosenDriver, TempLevel currMinTemp, LocalDate leavingDate) {
        Map<Integer, Truck> availableTrucks;

        if (chosenDriver.getDriverLicense().getLicenseWeightCapacity() == weightType.lightWeight)
        {
            availableTrucks = getAvailableTrucksOfLightWeight(leavingDate);
        }
        else if (chosenDriver.getDriverLicense().getLicenseWeightCapacity() == weightType.mediumWeight)
        {
            availableTrucks = getAvailableTrucksOfMiddleWeight(leavingDate);
            Map<Integer, Truck> availableTrucksLightWeight = getAvailableTrucksOfLightWeight(leavingDate);
            availableTrucksLightWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
        }
        else
        {
            availableTrucks = getAvailableTrucksOfHeavyWeight(leavingDate);
            Map<Integer, Truck> availableTrucksMiddleWeight = getAvailableTrucksOfMiddleWeight(leavingDate);
            Map<Integer, Truck> availableTrucksLightWeight = getAvailableTrucksOfLightWeight(leavingDate);
            availableTrucksMiddleWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
            availableTrucksLightWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
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
        else
            throw new NoSuchElementException("There is no available truck for this transfer");
    }
}
