import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //Making mock transfer system.

        //Creating Sites for destinations:
        //SitesList for siteController
        List<Site> mock_Sites = new ArrayList<>();

        //Creating 6 Sites:
        Site site1 = new Site(0, "Coca-Cola", "HaZionut 12 Bnei-Brak", "03-4789653", "Shmuel");
        Site site2 = new Site(1, "Tnuva", "zion 100 Beer-Sheva", "09-1001002", "Ramiel");
        Site site3 = new Site(2, "Super-Li Ashkelon", "Lola 97 Ashkelon", "08-6543210", "Taco");
        Site site4 = new Site(3,"Super-Li Haifa", "Bahaian-Gardens 1700", "08-1231700", "Alicia");
        Site site5 = new Site(4,"Super-Li Eilat", "AnotherUglyStreet 46 Eilat", "02-0000000", "Johnson");
        Site site6 = new Site(5,"Osem", "HaNachshon 89 Tel-Aviv", "09-7689809", "Ron");

        //Add these sites to the list:
        mock_Sites.add(site1);
        mock_Sites.add(site2);
        mock_Sites.add(site3);
        mock_Sites.add(site4);
        mock_Sites.add(site5);
        mock_Sites.add(site6);

        //Create SitesController:
        SiteController sc = new SiteController(mock_Sites);

        //Creating drivers for DriverController:
        //Drivers List for DriverController:
        List<Driver> mock_Drivers = new ArrayList<>();

        //Creating 3 DriversLicense for the drivers:
        DriverLicense driverLicense1 = new DriverLicense(weightType.lightWeight, TempLevel.cold);
        DriverLicense driverLicense2 = new DriverLicense(weightType.heavyWeight, TempLevel.regular);
        DriverLicense driverLicense3 = new DriverLicense(weightType.mediumWeight, TempLevel.frozen);

        //Creating 3 Drivers for DriverController:
        Driver driver1 = new Driver(driverLicense1, "Avi", true);
        Driver driver2 = new Driver(driverLicense2, "Moshe", true);
        Driver driver3 = new Driver(driverLicense3, "Arnon", true);

        //Add these sites to the list:
        mock_Drivers.add(driver1);
        mock_Drivers.add(driver2);
        mock_Drivers.add(driver3);

        //Create DriversController:
        DriverController dc = new DriverController(mock_Drivers);

        //Creating Trucks for truckController:
        //SitesList for truckController
        Map<Integer, Truck> mock_Trucks = new HashMap<>();

        //Creating 3 Trucks:
        Truck truck1 = new Truck(9874321, "Mercedes 330g", 8, 8,15,  TempLevel.frozen,false);
        Truck truck2 = new Truck(8061999, "Nisan x", 4, 4,5,  TempLevel.regular,false);
        Truck truck3 = new Truck(2541998, "Tesla 690x", 50, 50,60,  TempLevel.frozen,false);

        //Add these trucks to the list:
        mock_Trucks.put(truck1.getLicenseNumber(), truck1);
        mock_Trucks.put(truck2.getLicenseNumber(), truck2);
        mock_Trucks.put(truck3.getLicenseNumber(), truck3);

        //Create TrucksController:
        TruckController tc = new TruckController(mock_Trucks);

        //Create TransferController:
        TransferController transferController = new TransferController(tc, dc, sc);

        //Create some mock orders:
        //Order1 to site3 mock items map:
        Map<Site, Map<Item_mock, Integer>> order1_Items = new HashMap<>();

        //Order1 mockItems map with quantity:
        Map<Item_mock, Integer> order1_Items_site1 = new HashMap<>();

        //Create 3 mock items for first destination:
        Item_mock Fanta1 = new Item_mock(TempLevel.cold, "Fanta");
        Item_mock Cola1 = new Item_mock(TempLevel.cold, "Coca-Cola");

        //put the mock items with quantity to order for site1:
        order1_Items_site1.put(Fanta1, 200);
        order1_Items_site1.put(Cola1, 500);

        //Order2 mockItems map with quantity:
        Map<Item_mock, Integer> order1_Items_site6 = new HashMap<>();

        //Create 3 mock items for second destination:
        Item_mock Doritos1 = new Item_mock(TempLevel.regular, "Doritos");
        Item_mock bamba1 = new Item_mock(TempLevel.regular, "Bamba");

        //put the mock items with quantity to order for site6:
        order1_Items_site6.put(Doritos1, 800);
        order1_Items_site6.put(bamba1, 400);

        //Order3 mockItems map with quantity:
        Map<Item_mock, Integer> order1_Items_site2 = new HashMap<>();

        //Create 3 mock items for third destination:
        Item_mock TomatoSauce1 = new Item_mock(TempLevel.regular, "Tomato Sauce");
        Item_mock CreamCheese1 = new Item_mock(TempLevel.cold, "Cream Cheese");
        Item_mock Yolo1 = new Item_mock(TempLevel.cold, "Yolo");
        Item_mock FrozenPizza1 = new Item_mock(TempLevel.frozen, "Frozen Pizza");

        //put the mock items with quantity to order for site3:
        order1_Items_site2.put(TomatoSauce1, 500);
        order1_Items_site2.put(CreamCheese1, 300);
        order1_Items_site2.put(Yolo1, 900);
        order1_Items_site2.put(FrozenPizza1, 200);

        //put the sites and items with quantity in the order map:
        //put site1 with items in the map:
        order1_Items.put(site1, order1_Items_site1);

        //put site6 with items in the map:
        order1_Items.put(site6, order1_Items_site6);

        //put site2 with items in the map:
        order1_Items.put(site2, order1_Items_site2);

        //Let's start the transfer!
        transferController.createNewTransfer(order1_Items, site3.getSiteId());

    }
}
