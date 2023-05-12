package DomainTest;

import DataAccesObjects.Transfer.TrucksDAO;
import Domain.Employee.Driver;
import Domain.Employee.DriverLicense;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;
import Domain.Transfer.Truck;
import Domain.Transfer.TruckController;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TruckControllerTests {
    Driver driver1;
    DriverLicense driverLicense1;
    Truck truck1;
    Truck truck2;
    Truck truck3;
    Truck truck4;
    TruckController truckController;

    @BeforeEach
    void createMockTransfer() throws SQLException {
        TrucksDAO trucksDAO = TrucksDAO.getInstance();
        //create 4 trucks
        this.truck1 = new Truck(9874321, "Mercedes 330g", 8, 8,15,  TempLevel.frozen);
        this.truck2 = new Truck(8061999, "Nisan x", 4, 4,5,  TempLevel.regular);
        this.truck3 = new Truck(2541998, "Tesla 690x", 50, 50,60,  TempLevel.frozen);
        this.truck4 = new Truck(1045277, "Kia", 14, 14,40,  TempLevel.cold);

        //add trucks to DB
        trucksDAO.add(truck1);
        trucksDAO.add(truck2);
        trucksDAO.add(truck3);
        trucksDAO.add(truck4);

        truckController = TruckController.getInstance();

        //create driver
        this.driverLicense1 = new DriverLicense(weightType.lightWeight, TempLevel.frozen);
        this.driver1 = new Driver(1,"iftach","lotsofmoney",
            "23.2.23",90,12345,"student",1234,TempLevel.frozen,weightType.lightWeight);

    }

    @AfterEach
    void deleteFromDb() throws SQLException {
        TrucksDAO trucksDAO = TrucksDAO.getInstance();
        trucksDAO.deleteAll();
    }

    @Test
    void getAvailableTrucksOfLightWeightTest()
    {
        //creating list of available light weight trucks
        Map<Integer, Truck> availableLightTrucks = new HashMap<>();;
        availableLightTrucks.put(truck2.getLicenseNumber(), truck2);
        Map<Integer, Truck> availableToTest;
        availableToTest = truckController.getAvailableTrucksOfLightWeight(LocalDate.now().plusDays(20), LocalTime.now().plusHours(20), LocalDate.now().plusDays(30), LocalTime.now().plusHours(20));        //test if equals
        assertTrue(availableToTest.keySet().equals(availableLightTrucks.keySet()));
    }

    @Test
    void getAvailableTrucksOfMiddleWeightTest()
    {
        //creating list of available middle weight trucks
        Map<Integer, Truck> availableMiddleTrucks = new HashMap<>();;
        availableMiddleTrucks.put(truck1.getLicenseNumber(), truck1);
        Map<Integer, Truck> availableToTest;
        availableToTest = truckController.getAvailableTrucksOfMiddleWeight(LocalDate.now().plusDays(20), LocalTime.now().plusHours(20), LocalDate.now().plusDays(30), LocalTime.now().plusHours(20));
        //test if equals
        assertTrue(availableToTest.keySet().equals(availableMiddleTrucks.keySet()));
    }

    @Test
    void getAvailableTrucksOfHeavyWeightTest()
    {
        //creating list of available heavy weight trucks
        Map<Integer, Truck> availableHeavyTrucks = new HashMap<>();
        availableHeavyTrucks.put(truck3.getLicenseNumber(), truck3);
        availableHeavyTrucks.put(truck4.getLicenseNumber(), truck4);
        Map<Integer, Truck> availableToTest;
        availableToTest = truckController.getAvailableTrucksOfHeavyWeight(LocalDate.now().plusDays(20), LocalTime.now().plusHours(20), LocalDate.now().plusDays(30), LocalTime.now().plusHours(20));
        //test if equals

        assertTrue(availableToTest.keySet().equals(availableHeavyTrucks.keySet()));
    }

    /*
    @Test
    void findTruckByDriverTest()
    {
        //Set CurrMinTemp with Domain.Enums.TempLevel.regular and driver with lightWeight and frozen qualify and find corresponding truck
        Truck chosenTruck = truckControllerTest.findTruckByDriver(driver1, TempLevel.regular, LocalDate.now().plusDays(10), LocalTime.now().plusHours(10), LocalDate.now().plusDays(20), LocalTime.now().plusHours(20));

        //test if equals
        assertEquals(truck2, chosenTruck);
    }

     */
}
