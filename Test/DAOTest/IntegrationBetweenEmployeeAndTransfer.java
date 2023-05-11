package DAOTest;

import Data.Connection;
import Data.ShiftDAO;
import Data.WorkersDAO;
import Domain.Employee.*;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.weightType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;
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

        wscontroller.addNewDriver(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.cold,weightType.lightWeight);

        wscontroller.addNewDriver(2,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.mediumWeight);

        wscontroller.addNewDriver(3,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.frozen,weightType.heavyWeight);

        wscontroller.addNewDriver(4,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.lightWeight);

        wscontroller.addemployee(5,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        wscontroller.addemployee(6,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        //creating weeklyshift for the stokes:
        wscontroller.createnewweeklyshift(1,1,1);

        wscontroller.createnewweeklyshift(1,1,0);


        // all of the data is for week num 1 and year 1:

        //stokes will be available for day1, day 2 on day shift:
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day1,5,2);
        wscontroller.addtoexistingweeklyshift(1,1,0,WindowType.day1,6,2);

        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,5,2);
        wscontroller.addtoexistingweeklyshift(1,1,0,WindowType.day3,6,2);

        //available for drivers:
        //for day 3: all of them
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,1,2);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,2,2);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,3,2);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day3,4,2);

        // for day 1 just driver 1 and 2.
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day1,1,2);
        wscontroller.addtoexistingweeklyshift(1,1,1, WindowType.day1,2,2);


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
