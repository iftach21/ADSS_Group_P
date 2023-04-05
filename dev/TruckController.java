import java.util.HashMap;
import java.util.Map;

public class TruckController {
    public Map<Integer, Truck> _trucks;

    public TruckController(Map<Integer, Truck> trucks)
    {
        this._trucks = trucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfLightWeight()
    {
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : _trucks.keySet()){
            if(!_trucks.get(licenseNumber).getIsCurrentlyUsed() && _trucks.get(licenseNumber).isLightWeight()){
                availableTrucks.put(_trucks.get(licenseNumber).getLicenseNumber(), _trucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfMiddleWeight()
    {
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : _trucks.keySet()){
            if(!_trucks.get(licenseNumber).getIsCurrentlyUsed() && _trucks.get(licenseNumber).isMiddleWeight()){
                availableTrucks.put(_trucks.get(licenseNumber).getLicenseNumber(), _trucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAvailableTrucksOfHeavyWeight()
    {
        Map<Integer, Truck> availableTrucks = new HashMap<>();
        for(int licenseNumber : _trucks.keySet()){
            if(!_trucks.get(licenseNumber).getIsCurrentlyUsed() && _trucks.get(licenseNumber).isHeavyWeight()){
                availableTrucks.put(_trucks.get(licenseNumber).getLicenseNumber(), _trucks.get(licenseNumber));
            }
        }
        return availableTrucks;
    }

    public Map<Integer, Truck> getAllAvailableTrucks()
    {
        Map<Integer, Truck> availableTrucks;
        availableTrucks = getAvailableTrucksOfHeavyWeight();
        Map<Integer, Truck> availableTrucksMiddleWeight = getAvailableTrucksOfMiddleWeight();
        Map<Integer, Truck> availableTrucksLightWeight = getAvailableTrucksOfLightWeight();
        availableTrucksMiddleWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
        availableTrucksLightWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));

        return availableTrucks;
    }


}
