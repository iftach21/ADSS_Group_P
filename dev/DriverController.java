import java.util.ArrayList;
import java.util.List;

public class DriverController {

    private List<Driver> _drivers;

    public DriverController(List<Driver> drivers){
        this._drivers = drivers;
    }

    public List<Driver> getAvailableDrivers()
    {
        List<Driver> availableDrivers = new ArrayList<>();

        for (int i=0; i<_drivers.size(); i++)
        {
            if(_drivers.get(i).getIsAvailable())
            {
                availableDrivers.add(_drivers.get(i));
            }
        }
        return availableDrivers;
    }
}
