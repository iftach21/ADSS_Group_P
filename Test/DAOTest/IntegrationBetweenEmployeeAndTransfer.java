package DAOTest;

import DataAccesObjects.Connection;
import Domain.Employee.*;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.weightType;
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

    WeeklyShiftAndWorkersManager wscontroller;

    List<Driver> drivers;
    DriverController driverControllerTest;



    @BeforeEach
    void createTransfer() throws SQLException {

        //create 4 drivers :

        wscontroller.addNewDriver(11,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.cold,weightType.lightWeight);

        wscontroller.addNewDriver(22,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.mediumWeight);

        wscontroller.addNewDriver(33,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.frozen,weightType.heavyWeight);

        wscontroller.addNewDriver(44,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.lightWeight);

        wscontroller.addemployee(55,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        wscontroller.addemployee(66,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        //creating weeklyshift for the stokes:
        wscontroller.createnewweeklyshift(1,1,1);

        wscontroller.createnewweeklyshift(1,1,0);


        // all of the data is for week num 1 and year 1:

        //stokes will be available for day1, day 2 on day shift:
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day1,55,2);
        wscontroller.addtoexistingweeklyshift(1,1,0,WindowType.day1,66,2);

        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,55,2);
        wscontroller.addtoexistingweeklyshift(1,1,0,WindowType.day3,66,2);

        //available for drivers:
        //for day 3: all of them
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,11,7);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,22,7);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,33,7);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,44,7);

        // for day 1 just driver 1 and 2.
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day1,11,7);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day1,22,7);

    }


    @AfterEach
    void tearDown() {

        Connection.DeleteRows();
    }


    @Test
    void getAvailableDriversTest()
    {
        //todo: complete
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

}
