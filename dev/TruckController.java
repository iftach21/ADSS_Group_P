import java.util.HashMap;
import java.util.Map;

public class TruckController {
    private Map<Integer, Truck> _trucks;

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


}
