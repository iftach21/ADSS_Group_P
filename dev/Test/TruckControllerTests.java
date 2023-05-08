package Test;

import Domain.Employee.Driver;
import Domain.Employee.DriverLicense;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;
import Domain.Transfer.Truck;
import Domain.Transfer.TruckController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class TruckControllerTests {
    Driver driver1;
    DriverLicense driverLicense1;
    Truck truck1;
    Truck truck2;
    Truck truck3;
    Truck truck4;
    Map<Integer, Truck> trucks;
    TruckController truckControllerTest;

    @BeforeEach
    void createMockTransfer()
    {
        //create 4 trucks
        this.truck1 = new Truck(9874321, "Mercedes 330g", 8, 8,15,  TempLevel.frozen,false);
        this.truck2 = new Truck(8061999, "Nisan x", 4, 4,5,  TempLevel.regular,false);
        this.truck3 = new Truck(2541998, "Tesla 690x", 50, 50,60,  TempLevel.frozen,false);
        this.truck4 = new Truck(1045277, "Kia", 14, 14,40,  TempLevel.cold,true);

        //add trucks to map
        this.trucks = new HashMap<>();
        this.trucks.put(truck1.getLicenseNumber(), this.truck1);
        this.trucks.put(truck2.getLicenseNumber(), this.truck2);
        this.trucks.put(truck3.getLicenseNumber(), this.truck3);
        this.trucks.put(truck4.getLicenseNumber(), this.truck4);

        //create driver
        this.driverLicense1 = new DriverLicense(weightType.lightWeight, TempLevel.frozen);
        this.driver1 = new Driver(driverLicense1,"Nitzan", true);

        //create truckController
        this.truckControllerTest = new TruckController(trucks);
    }

    @Test
    void getAvailableTrucksOfLightWeightTest()
    {
        //creating list of available light weight trucks
        Map<Integer, Truck> availableLightTrucks = new HashMap<>();;
        availableLightTrucks.put(truck2.getLicenseNumber(), truck2);

        //test if equals
        assertEquals(availableLightTrucks, truckControllerTest.getAvailableTrucksOfLightWeight());
    }

    @Test
    void getAvailableTrucksOfMiddleWeightTest()
    {
        //creating list of available middle weight trucks
        Map<Integer, Truck> availableMiddleTrucks = new HashMap<>();;
        availableMiddleTrucks.put(truck1.getLicenseNumber(), truck1);

        //test if equals
        assertEquals(availableMiddleTrucks, truckControllerTest.getAvailableTrucksOfMiddleWeight());
    }

    @Test
    void getAvailableTrucksOfHeavyWeightTest()
    {
        //creating list of available heavy weight trucks
        Map<Integer, Truck> availableHeavyTrucks = new HashMap<>();;
        availableHeavyTrucks.put(truck3.getLicenseNumber(), truck3);

        //test if equals
        assertEquals(availableHeavyTrucks, truckControllerTest.getAvailableTrucksOfHeavyWeight());
    }

    @Test
    void findTruckByDriverTest()
    {
        //Set CurrMinTemp with Domain.Enums.TempLevel.regular and driver with lightWeight and frozen qualify and find corresponding truck
        Truck chosenTruck = truckControllerTest.findTruckByDriver(driver1, TempLevel.regular);

        //test if equals
        Assertions.assertEquals(truck2, chosenTruck);
    }
}
