package Test;

import Domain.Employee.Driver;
import Domain.Employee.DriverController;
import Domain.Employee.DriverLicense;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class driverControllerTests {
    Driver driver1;
    Driver driver2;
    Driver driver3;
    Driver driver4;
    DriverLicense driverLicense1;
    DriverLicense driverLicense2;
    DriverLicense driverLicense3;
    DriverLicense driverLicense4;
    List<Driver> drivers;
    DriverController driverControllerTest;

    @BeforeEach
    void createMockTransfer()
    {
        //create 4 driverLicense for Drivers
        this.driverLicense1 = new DriverLicense(weightType.lightWeight, TempLevel.cold);
        this.driverLicense2 = new DriverLicense(weightType.mediumWeight, TempLevel.regular);
        this.driverLicense3 = new DriverLicense(weightType.heavyWeight, TempLevel.frozen);
        this.driverLicense4 = new DriverLicense(weightType.lightWeight, TempLevel.regular);
        //todo: fix!
        //create 4 drivers
        //this.driver1 = new Driver(driverLicense1,"Nitzan", true);
        //this.driver2 = new Driver(driverLicense2,"Tomer", true);
        //this.driver3 = new Driver(driverLicense3,"Ron", true);
        //this.driver4 = new Driver(driverLicense4, "Liraz", false);

        //add drivers to list
        this.drivers = new ArrayList<>();
        this.drivers.add(driver1);
        this.drivers.add(driver2);
        this.drivers.add(driver3);
        this.drivers.add(driver4);

        //create driverController
        //this.driverControllerTest = new DriverController(drivers);
    }

    @Test
    void getAvailableDriversTest()
    {
        //creating list of available drivers
        List<Driver> availableDrivers = new ArrayList<>();;
        availableDrivers.add(driver1);
        availableDrivers.add(driver2);
        availableDrivers.add(driver3);

        //test if equals
        assertEquals(availableDrivers, driverControllerTest.getAvailableDrivers());
    }

    @Test
    void findDriverTest1()
    {
        //Set minimum item temp with Domain.Enums.TempLevel.regular and find driver with corresponding license
        Driver ChosenDriver = driverControllerTest.findDriver(TempLevel.regular);

        //test if equals
        Assertions.assertEquals(this.driver1, ChosenDriver);
    }

    @Test
    void findDriverTest2()
    {
        //Set CurrMinTemp with Domain.Enums.TempLevel.frozen and find driver with corresponding license
        Driver ChosenDriver = driverControllerTest.findDriver(TempLevel.frozen);

        //test if equals
        Assertions.assertEquals(this.driver3, ChosenDriver);
    }
}
