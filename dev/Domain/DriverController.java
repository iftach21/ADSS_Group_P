package Domain;

import java.util.*;

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

    public Driver findDriver(TempLevel currMinTemp){
        Driver chosenDriver;

        List<Driver> availableDrivers = getAvailableDrivers();

        Collections.sort(availableDrivers, new Comparator<Driver>() {
            @Override
            public int compare(Driver d1, Driver d2) {
                return d1.getDriverLicense().compareTo(d2.getDriverLicense());
            }
        });

        chosenDriver = null;

        for (int i = 0; i < availableDrivers.size(); i++)
        {
            if (availableDrivers.get(i).checkLicenseWithItemTemp(currMinTemp))
            {
                chosenDriver = availableDrivers.get(i);
                break;
            }
        }

        if (chosenDriver != null)
            return chosenDriver;
        else
            throw new NoSuchElementException("There is no available driver for this transfer");
    }

}
