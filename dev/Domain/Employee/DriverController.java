package Domain.Employee;

import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;

import java.sql.SQLException;
import java.util.*;

public class DriverController {
    private static final WeeklyShiftAndWorkersManager weeklyShiftAndWorkersManager;

    static {
        try {
            weeklyShiftAndWorkersManager = WeeklyShiftAndWorkersManager.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public DriverController(){}

    public List<Driver> getAvailableDrivers(int weekNum, int yearNum, WindowType wt)
    {
        return weeklyShiftAndWorkersManager.giveMeViableDrivers(weekNum,yearNum,wt);
    }

    public List<Driver> findDriver(TempLevel currMinTemp, int weekNum, int yearNum, WindowType wt){
        Driver chosenDriver;

        //this will give the available drivers for the shift
        List<Driver> allAvailableDrivers = getAvailableDrivers(weekNum,yearNum,wt);

        //list of max 5 drivers, which will be shown to the transfer manager to choose from
        List<Driver> max5Drivers = new ArrayList<>();

        chosenDriver = null;

        for (int i = 0; i < allAvailableDrivers.size() && max5Drivers.size() < 5; i++)
        {
            if (allAvailableDrivers.get(i).checkLicenseWithItemTemp(currMinTemp))
            {
                max5Drivers.add(allAvailableDrivers.get(i));
            }
        }

        return max5Drivers;
    }
}
