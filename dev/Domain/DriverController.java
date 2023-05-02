package Domain;

import java.util.*;

public class DriverController {
    private static final WeeklyShiftAndWorkersManager weeklyShiftAndWorkersManager = WeeklyShiftAndWorkersManager.getInstance();


    public DriverController(){}

    public List<Driver> getAvailableDrivers(int weekNum,int yearNum, WindowType wt)
    {
        return weeklyShiftAndWorkersManager.giveMeViableDrivers(weekNum,yearNum,wt);
    }

    public List<Driver> findDriver(TempLevel currMinTemp,int weekNum,int yearNum, WindowType wt){
        Driver chosenDriver;

        //this will give the available drivers for the shift
        List<Driver> availableDrivers = getAvailableDrivers(weekNum,yearNum,wt);

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
            return null;
        else
            throw new NoSuchElementException("There is no available driver for this transfer");
    }

}
