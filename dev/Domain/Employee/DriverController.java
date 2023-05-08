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
        List<Driver> availableDrivers = getAvailableDrivers(weekNum,yearNum,wt);

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
