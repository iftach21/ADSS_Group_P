package DAOTest;

import DataAccesObjects.Connection;
import Domain.Employee.*;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.weightType;
import Domain.Transfer.Transfer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class IntegrationBetweenEmployeeAndTransfer {

    //todo: need to fix those tests!

    Driver driver1;
    Driver driver2;
    Driver driver3;
    Driver driver4;

    Workers stoke1;

    Workers stoke2;

    WeeklyShiftAndWorkersManager wscontroller = WeeklyShiftAndWorkersManager.getInstance();

    List<Driver> drivers;
    DriverController driverControllerTest = DriverController.getInstance();

    public IntegrationBetweenEmployeeAndTransfer() throws SQLException {
    }


    @BeforeEach
    void createTransfer() throws SQLException {

//create 4 drivers :

        wscontroller.addNewDriver(111,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234, TempLevel.cold, weightType.lightWeight);

        wscontroller.addNewDriver(222,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.mediumWeight);

        wscontroller.addNewDriver(333,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.frozen,weightType.heavyWeight);

        wscontroller.addNewDriver(444,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.lightWeight);

        wscontroller.addemployee(555,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        wscontroller.addemployee(666,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        //creating weeklyshift for the stokes:
        wscontroller.createnewweeklyshift(1,2024,4);

        wscontroller.createnewweeklyshift(1,2024,5);

        wscontroller.createnewweeklyshift(1,2024,0);


        // all of the data is for week num 1 and year 1:

        //stokes will be available for day1, day 2 on day shift:
        wscontroller.addtoexistingweeklyshift(1,2024,4, WindowType.day1,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,4,WindowType.day1,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,4, WindowType.day2,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,4,WindowType.day2,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,4, WindowType.day3,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,4,WindowType.day3,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,4, WindowType.day4,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,4,WindowType.day4,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,4, WindowType.day5,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,4,WindowType.day5,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,4, WindowType.day6,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,4,WindowType.day6,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,4, WindowType.day7,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,4,WindowType.day7,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,5, WindowType.day3,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,5,WindowType.day3,666,2);

        wscontroller.addtoexistingweeklyshift(1,2024,5, WindowType.day4,555,2);
        wscontroller.addtoexistingweeklyshift(1,2024,5,WindowType.day4,666,2);

        //available for drivers:
        //for day 3: all of them
        wscontroller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,111,7);
        wscontroller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,222,7);
        wscontroller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,333,7);
        wscontroller.addtoexistingweeklyshift(1,2024,0, WindowType.day3,444,7);

        // for day 1 just driver 1 and 2.
        wscontroller.addtoexistingweeklyshift(1,2024,0, WindowType.day1,111,7);
        wscontroller.addtoexistingweeklyshift(1,2024,0, WindowType.day1,222,7);

    }


    @AfterEach
    void tearDown() {
       Connection.DeleteRows();
    }


    @Test
    void getAvailableDriversTest() throws SQLException {
        //Connection.databaseReboot();
        //List<Driver> driversForDay1 = driverControllerTest.getAvailableDrivers(11, 1999, WindowType.day1);
        //List<Integer> IdsListFromTrucksTable1 = transferTrucksDAO.get(transfer1.getTruckLicenseNumber()).stream().map(Transfer::getTransferId).toList();
    }

    @Test
    void findDriverTest1()
    {
        //todo: complete
    }

    @Test
    void findDriverTest2()
    {
        //todo: complete
    }
    @Test
    void doIHaveDriver() throws SQLException {
        wscontroller.doIHaveStokeForTheShipment(1,2024,4);
    }

}
