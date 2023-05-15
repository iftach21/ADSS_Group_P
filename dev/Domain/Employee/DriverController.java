package Domain.Employee;

import DataAccesObjects.Transfer.TransferDAO;
import DataAccesObjects.Transfer.TrucksDAO;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.WindowTypeCreater;
import Domain.Transfer.Transfer;
import Domain.Transfer.TruckController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;

public class DriverController {
    private static final WeeklyShiftAndWorkersManager weeklyShiftAndWorkersManager;
    private static DriverController Instance = null;


    static {
        try {
            weeklyShiftAndWorkersManager = WeeklyShiftAndWorkersManager.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DriverController(){}

    public static DriverController getInstance() throws SQLException {
        if(Instance==null){Instance = new DriverController();}
        return Instance;
    }

    public List<Driver> getAvailableDrivers(int weekNum, int yearNum, WindowType wt) throws SQLException {
        List<Driver> driversInShifts =  weeklyShiftAndWorkersManager.giveMeViableDrivers(weekNum,yearNum,wt);

        Map<Integer,Transfer> transfers = TransferDAO.getInstance().getAllTransfers();
        List<Driver> driversToDelete = new ArrayList<>();

        for(Integer transferId: transfers.keySet())
        {
            int driverId  = transfers.get(transferId).getDriverId();
            LocalDate dateOfTransfer = transfers.get(transferId).getDateOfTransfer();
            int day = dateOfTransfer.getDayOfMonth() % 7;
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNumber = dateOfTransfer.get(weekFields.weekOfWeekBasedYear());
            int year = dateOfTransfer.getYear();
            LocalTime time = transfers.get(transferId).getLeavingTime();
            String shift;
            if (time.isAfter(LocalTime.NOON))
                shift = "night";
            else
                shift = "day";

            //create window type
            WindowTypeCreater wtTransferCreate = new WindowTypeCreater();
            WindowType wtTransfer = wtTransferCreate.getwidowtype(day, shift);

            for(int i=0; i<driversInShifts.size(); i++)
            {
                if(driversInShifts.get(i).getId() == driverId && year==yearNum && weekNumber==weekNum && wt.equals(wtTransfer))
                {
                    driversToDelete.add(driversInShifts.get(i));
                }

            }
        }

        driversInShifts.removeAll(driversToDelete);
        return driversInShifts;
    }

    public List<Driver> findDriver(TempLevel currMinTemp, int weekNum, int yearNum, WindowType wt) throws SQLException {
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
