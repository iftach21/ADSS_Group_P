package DomainTest;

import DataAccesObjects.Transfer.*;
import Domain.Employee.Driver;
import Domain.Employee.DriverLicense;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;
import Domain.Transfer.Item_mock;
import Domain.Transfer.Site;
import Domain.Transfer.Transfer;
import Domain.Transfer.Truck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransferTests {

    Site site1;
    Site site2;
    Site site3;
    Item_mock item11;
    Item_mock item12;
    Item_mock item21;
    Item_mock item22;
    Map<Site, Map<Item_mock, Integer>> orderItems1;
    Map<Site, Integer> destinations;
    Map<Item_mock, Integer> orderItems1site1;
    Map<Item_mock, Integer> orderItems1site2;
    Truck truck1;
    DriverLicense dl1;
    Driver driver1;
    Transfer testTransfer;
    final SiteDAO siteDAO;
    final Item_mockDAO item_mockDAO;

    TransferTests() throws SQLException {
        this.siteDAO = SiteDAO.getInstance();
        this.item_mockDAO = Item_mockDAO.getInstance();
    }

    @BeforeEach
    void createMockTransfer() throws SQLException {
        //Clear database before each test
        siteDAO.deleteAll();
        item_mockDAO.deleteAll();
        TransferItemsDAO transferItemsDAO = TransferItemsDAO.getInstance();
        transferItemsDAO.deleteAll();
        TransferDestinationsDAO transferDestinationsDAO = TransferDestinationsDAO.getInstance();
        transferDestinationsDAO.deleteAll();
        TransferDAO.getInstance().deleteAll();

        //create 3 sites for destinations
        this.site1 = new Site(0,"Sano", "Zabotinski 12 Tel-Aviv", "09-7863908", "Moshe", 32.06845601633649, 34.783378215486955);
        siteDAO.add(site1);
        siteDAO.addToCache(site1);
        this.site2 = new Site(1,"Tara", "Masada 12 Beer-Sheva", "09-7887645", "Ron", 31.262913845991317, 34.79982446327962);
        siteDAO.add(site2);
        siteDAO.addToCache(site2);
        this.site3 = new Site(2,"Supe-Li Krayot", "HaZayit 5 Kiryat Haim", "09-7863231", "Avram", 32.835899363273555, 35.066426063499215);
        siteDAO.add(site3);
        siteDAO.addToCache(site3);

        //create 2 items for each site
        //items for site 1
        this.item11 = new Item_mock("1", TempLevel.regular, "Toilet Cleaner");
        item_mockDAO.add(item11);
        item_mockDAO.addToCache(item11);
        this.item12 = new Item_mock("2", TempLevel.regular, "Sink Cleaner");
        item_mockDAO.add(item12);
        item_mockDAO.addToCache(item12);

        //items for site 2
        this.item21 = new Item_mock("3", TempLevel.cold, "Almond Milk");
        item_mockDAO.add(item21);
        item_mockDAO.addToCache(item21);
        this.item22 = new Item_mock("4", TempLevel.cold, "Cheddar Cheese");
        item_mockDAO.add(item22);
        item_mockDAO.addToCache(item22);

        //create order items map and destinations list
        this.orderItems1 = new HashMap<>();
        this.destinations = new HashMap<>();

        //create map for each item with quantity for site 1
        this.orderItems1site1 = new HashMap<>();
        orderItems1site1.put(item11, 1000);
        orderItems1site1.put(item12, 600);

        //create map for each item with quantity for site 2
        this.orderItems1site2 = new HashMap<>();
        orderItems1site2.put(item21, 500);
        orderItems1site2.put(item22, 300);

        //add maps to orderItems1 map and sites to destination list assuming source is in site1
        orderItems1.put(site1, orderItems1site1);
        orderItems1.put(site2, orderItems1site2);

        destinations.put(site2, 0);
        destinations.put(site3, 0);

        //create truck and driver
        this.truck1 = new Truck(123456, "Mercedes 330", 8, 8, 15, TempLevel.frozen);
        this.dl1 = new DriverLicense(weightType.heavyWeight, TempLevel.frozen);
        this.driver1 = new Driver(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234,TempLevel.cold,weightType.heavyWeight);

        //create the transfer
        this.testTransfer = new Transfer(LocalDate.now().plusDays(10), LocalTime.now().plusHours(10), LocalDate.now().plusDays(20), LocalTime.now().plusHours(20), 123456, "Arnon", site1,destinations, orderItems1, 0, 0, 8735);
        testTransfer.addToDAO(orderItems1, destinations);
        truck1.addToDAO(testTransfer.getTransferId());
    }

    @Test
    void removeTransferItemsTest1()
    {
        //creating map with items to remove from site2
        Map<Item_mock, Integer> orderItems_toDelete = new HashMap<>();

        //add items to the map
        orderItems_toDelete.put(item22, 150);

        //create new map for site2 and remove the items from it
        Map<Site, Map<Item_mock, Integer>> orderItems1_toDelete = new HashMap<>();
        orderItems1_toDelete.put(site2, orderItems_toDelete);

        //remove from testTransfer
        testTransfer.removeTransferItems(orderItems1_toDelete);

        //remove from orderItems1 from site2 150 of item22
        orderItems1.get(site2).put(item22, 150);

        //test if equals
        assertEquals(orderItems1, testTransfer.getOrderItems());
    }

    @Test
    void removeTransferItemsTest2()
    {
        //creating map with items to remove from site2
        Map<Item_mock, Integer> orderItems_toDelete = new HashMap<>();

        //add items to the map
        orderItems_toDelete.put(item11, 100);

        //create new map for site2 and remove the items from it
        Map<Site, Map<Item_mock, Integer>> orderItems2_toDelete = new HashMap<>();
        orderItems2_toDelete.put(site1, orderItems_toDelete);

        //remove from testTransfer
        testTransfer.removeTransferItems(orderItems2_toDelete);

        //remove from orderItems1 from site2 150 of item22
        orderItems1.get(site1).put(item11, 900);

        //test if equals
        assertEquals(orderItems1, testTransfer.getOrderItems());
    }

    @Test
    void checkIfSiteRemoved()
    {
        //creating map with items to remove from site2
        Map<Item_mock, Integer> orderItems_toDelete = new HashMap<>();

        //add items to the map
        orderItems_toDelete.put(item21, 500);
        orderItems_toDelete.put(item22, 300);

        //create new map for site2 and remove the items from it
        Map<Site, Map<Item_mock, Integer>> orderItems1_toDelete = new HashMap<>();
        orderItems1_toDelete.put(site2, orderItems_toDelete);

        //remove from testTransfer
        testTransfer.removeTransferItems(orderItems1_toDelete);

        //test if true
        assertTrue(!testTransfer.getOrderItems().containsKey(site2));
    }

    @Test
    void checkIfTruckUpdated()
    {
        //change transfer truck
        testTransfer.updateTransferTruck(789699);

        //test if changed
        Assertions.assertEquals(789699, testTransfer.getTruckLicenseNumber());
    }

    @Test
    void checkIfDestinationRemoved()
    {
        //remove site2 from destinations
        destinations.remove(site2);

        //removing from actual transfer
        testTransfer.removeTransferDestination(site2);

        //test if equals
        assertEquals(destinations, testTransfer.getDestinations());

    }
}

