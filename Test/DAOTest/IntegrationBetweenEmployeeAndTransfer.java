package DAOTest;

import DataAccesObjects.Connection;
import DataAccesObjects.Employee.WorkersDAO;
import Domain.Employee.*;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.weightType;
import Domain.Transfer.Transfer;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class IntegrationBetweenEmployeeAndTransfer {


    WeeklyShiftAndWorkersManager wscontroller = WeeklyShiftAndWorkersManager.getInstance();

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
        List<Driver> driversForDay1 = driverControllerTest.getAvailableDrivers(1, 2024, WindowType.day1);
        List<Integer> driversIds1 = driversForDay1.stream().map(urEntity -> urEntity.getId()).collect(Collectors.toList());

        //should have drivers 111 and 222 only
        Assertions.assertTrue(driversIds1.contains(111));
        Assertions.assertTrue(driversIds1.contains(222));
        // 333 and 444 shouldnt be in this list
        Assertions.assertFalse(driversIds1.contains(333));
        Assertions.assertFalse(driversIds1.contains(444));

        //check for day 3, all of them should be in the returned list
        List<Driver> driversForDay3 = driverControllerTest.getAvailableDrivers(1, 2024, WindowType.day3);
        List<Integer> driversIds3 = driversForDay3.stream().map(urEntity -> urEntity.getId()).collect(Collectors.toList());
        //should have drivers 111, 222, 333, 444
        Assertions.assertTrue(driversIds3.contains(111));
        Assertions.assertTrue(driversIds3.contains(222));
        Assertions.assertTrue(driversIds3.contains(333));
        Assertions.assertTrue(driversIds3.contains(444));
    }

    @Test
    void findDriverTest1() throws SQLException {
        //check for day3 with regular temp capacity. should return all of them
        List<Driver> regDriverDay3 = driverControllerTest.findDriver(TempLevel.regular, 1, 2024, WindowType.day3);
        List<Integer> driversIdsDay3 = regDriverDay3.stream().map(urEntity -> urEntity.getId()).collect(Collectors.toList());
        //should have drivers 111, 222, 333, 444
        Assertions.assertTrue(driversIdsDay3.contains(111));
        Assertions.assertTrue(driversIdsDay3.contains(222));
        Assertions.assertTrue(driversIdsDay3.contains(333));
        Assertions.assertTrue(driversIdsDay3.contains(444));

        //check for day1 with cold temp capacity. should return only 111
        List<Driver> regDriverDay1 = driverControllerTest.findDriver(TempLevel.cold, 1, 2024, WindowType.day1);
        List<Integer> driversIdsDay1 = regDriverDay1.stream().map(urEntity -> urEntity.getId()).collect(Collectors.toList());

        //should return only 111, because 222 temp is only regular
        Assertions.assertTrue(driversIdsDay1.contains(111));
        Assertions.assertFalse(driversIdsDay1.contains(222));
        Assertions.assertFalse(driversIdsDay1.contains(333));
        Assertions.assertFalse(driversIdsDay1.contains(444));
    }

    @Test
    void findDriverTest2() throws SQLException {
        //in this test none of the drivers should be returned
        List<Driver> regDriverDay1 = driverControllerTest.findDriver(TempLevel.frozen, 1, 2024, WindowType.day1);
        List<Integer> driversIdsDay1 = regDriverDay1.stream().map(urEntity -> urEntity.getId()).collect(Collectors.toList());

        //should return none
        Assertions.assertTrue(driversIdsDay1.isEmpty());

        //in this test too, none of the drivers should be returned
        List<Driver> regDriverDay4 = driverControllerTest.findDriver(TempLevel.frozen, 1, 2024, WindowType.day4);
        List<Integer> driversIdsDay4 = regDriverDay4.stream().map(urEntity -> urEntity.getId()).collect(Collectors.toList());

        //should return none
        Assertions.assertTrue(driversIdsDay4.isEmpty());
    }
    @Test
    void doIHaveStoke() throws SQLException {

        //suppose to have workers in day1, day2
        Assertions.assertTrue(wscontroller.doIHaveStokeForTheShipment(1,2024,4).contains(WindowType.day1));
        Assertions.assertTrue(wscontroller.doIHaveStokeForTheShipment(1,2024,4).contains(WindowType.day2));

        //shouldnt have any of these days in the windowTypes available
        Assertions.assertFalse(wscontroller.doIHaveStokeForTheShipment(1,2024,4).contains(WindowType.day3));
        Assertions.assertFalse(wscontroller.doIHaveStokeForTheShipment(1,2024,4).contains(WindowType.day4));
        Assertions.assertFalse(wscontroller.doIHaveStokeForTheShipment(1,2024,4).contains(WindowType.day5));
        Assertions.assertFalse(wscontroller.doIHaveStokeForTheShipment(1,2024,4).contains(WindowType.day6));
        Assertions.assertFalse(wscontroller.doIHaveStokeForTheShipment(1,2024,4).contains(WindowType.day7));
    }

}