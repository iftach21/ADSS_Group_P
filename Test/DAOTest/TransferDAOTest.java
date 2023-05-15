
package DAOTest;

import DataAccesObjects.Transfer.*;
import Domain.Enums.TempLevel;
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
import java.util.stream.Collectors;

public class TransferDAOTest {
    Transfer transfer1;
    Transfer transfer2;

    TransferDAO transferDAO;
    TransferDestinationsDAO transferDestinationsDAO;
    TransferItemsDAO transferItemsDAO;
    TransferTrucksDAO transferTrucksDAO;

    public TransferDAOTest() throws SQLException {
        transferDAO = TransferDAO.getInstance();
        transferDestinationsDAO = TransferDestinationsDAO.getInstance();
        transferItemsDAO = TransferItemsDAO.getInstance();
        transferTrucksDAO =  TransferTrucksDAO.getInstance();
    }

    @BeforeEach
    void createMockTrucks() throws SQLException {
        //clear database before each Test
        transferDAO.deleteAll();
        transferDestinationsDAO.deleteAll();
        transferItemsDAO.deleteAll();
        transferTrucksDAO.deleteAll();

        //create 3 sites for transfer
        Site site1 = new Site(0,"Sano", "Zabotinski 12 Tel-Aviv", "09-7863908", "Moshe", 32.06845601633649, 34.783378215486955);
        Site site2 = new Site(1,"Tara", "Masada 12 Beer-Sheva", "09-7887645", "Ron", 31.262913845991317, 34.79982446327962);
        Site site3 = new Site(2,"SuperLi Ashkelon", "Shalom Alechem 12 Ashkelon", "09-7895627", "Shmuel", 31.666337654822353, 34.57402922681273);

        //add these site to the DB
        SiteDAO.getInstance().add(site1);
        SiteDAO.getInstance().add(site2);
        SiteDAO.getInstance().add(site3);


        //create mockItems
        Item_mock item1 = new Item_mock("13434gd", TempLevel.regular, "Toilet Cleaner");
        Item_mock item2 = new Item_mock("3252gfd", TempLevel.cold, "Cheddar Cheese");

        //add these items to the db
        Item_mockDAO.getInstance().add(item1);
        Item_mockDAO.getInstance().add(item2);

        //dests to transfer1
        Map<Site, Integer> dests1 = new HashMap<>();
        dests1.put(site1, 0);
        dests1.put(site3, 0);

        //dests to transfer2
        Map<Site, Integer> dests2 = new HashMap<>();
        dests2.put(site2, 0);
        dests2.put(site3, 0);

        //item 1 quantity
        Map<Item_mock, Integer> items1 = new HashMap<>();
        items1.put(item1, 500);

        //item 2 quantity
        Map<Item_mock, Integer> items2 = new HashMap<>();
        items2.put(item2, 400);

        //sites and items maps
        Map<Site, Map<Item_mock, Integer>> destsAndItems1 = new HashMap<>();
        Map<Site, Map<Item_mock, Integer>> destsAndItems2 = new HashMap<>();

        destsAndItems1.put(site1, items1);
        destsAndItems2.put(site2, items2);

        //create 2 transfers
        this.transfer1 = new Transfer(LocalDate.parse("2023-05-15"), LocalTime.parse("12:00:00"), LocalDate.parse("2023-05-15"), LocalTime.parse("15:00:00"), 12345678, "Moshe", site1, dests1, destsAndItems1, 0, 0, 1234);
        this.transfer2 = new Transfer(LocalDate.parse("2023-05-17"), LocalTime.parse("14:00:00"), LocalDate.parse("2023-05-15"), LocalTime.parse("17:00:00"), 78776678, "Alon", site2, dests2, destsAndItems2, 1, 0, 5422);
        transferDAO.add(transfer1);
        transferDAO.add(transfer2);
        transfer1.addToDAO(destsAndItems1, dests1);
        transfer2.addToDAO(destsAndItems2, dests2);
        transferTrucksDAO.add(transfer1.getTransferId(), transfer1.getTruckLicenseNumber());
        transferTrucksDAO.add(transfer2.getTransferId(), transfer2.getTruckLicenseNumber());
    }

    @Test
    void addAndGetTransferTest() throws SQLException {

        //Check Transfer table
        Assertions.assertEquals(transfer1.getTransferId(), transferDAO.get(transfer1.getTransferId()).getTransferId());
        Assertions.assertEquals(transfer1.getDateOfTransfer(), transferDAO.get(transfer1.getTransferId()).getDateOfTransfer());
        Assertions.assertEquals(transfer1.getTruckLicenseNumber(), transferDAO.get(transfer1.getTransferId()).getTruckLicenseNumber());
        Assertions.assertEquals(transfer1.getDriverName(), transferDAO.get(transfer1.getTransferId()).getDriverName());
        Assertions.assertEquals(transfer1.getSource().getSiteId(), transferDAO.get(transfer1.getTransferId()).getSource().getSiteId());

        Assertions.assertEquals(transfer2.getTransferId(), transferDAO.get(transfer2.getTransferId()).getTransferId());
        Assertions.assertEquals(transfer2.getDateOfTransfer(), transferDAO.get(transfer2.getTransferId()).getDateOfTransfer());
        Assertions.assertEquals(transfer2.getTruckLicenseNumber(), transferDAO.get(transfer2.getTransferId()).getTruckLicenseNumber());
        Assertions.assertEquals(transfer2.getDriverName(), transferDAO.get(transfer2.getTransferId()).getDriverName());
        Assertions.assertEquals(transfer2.getSource().getSiteId(), transferDAO.get(transfer2.getTransferId()).getSource().getSiteId());

        //check TransferDestinations table
        Assertions.assertEquals(transfer1.getDestinations(), transferDestinationsDAO.get(transfer1.getTransferId()));
        Assertions.assertEquals(transfer2.getDestinations(), transferDestinationsDAO.get(transfer2.getTransferId()));

        //check TransferItems table
        Assertions.assertEquals(transfer1.getOrderItems(), transferItemsDAO.get(transfer1.getTransferId()));
        Assertions.assertEquals(transfer2.getOrderItems(), transferItemsDAO.get(transfer2.getTransferId()));

        //check TransferTruck table
        List<Integer> IdsListFromTrucksTable1 = transferTrucksDAO.get(transfer1.getTruckLicenseNumber()).stream().map(Transfer::getTransferId).toList();
        List<Integer> IdsListFromTrucksTable2 = transferTrucksDAO.get(transfer2.getTruckLicenseNumber()).stream().map(Transfer::getTransferId).toList();
        Assertions.assertTrue(IdsListFromTrucksTable1.contains(transferDAO.get(transfer1.getTransferId()).getTransferId()));
        Assertions.assertTrue(IdsListFromTrucksTable2.contains(transferDAO.get(transfer2.getTransferId()).getTransferId()));
    }

    @Test
    void deleteTransferTest() throws SQLException {
        //check Transfer table
        transferDAO.delete(transfer1.getTransferId());
        Assertions.assertNull(transferDAO.get(transfer1.getTransferId()));

        transferDAO.delete(transfer2.getTransferId());
        Assertions.assertNull(transferDAO.get(transfer2.getTransferId()));

        //check TransferDestinations table
        Assertions.assertNull(transferDestinationsDAO.get(transfer1.getTransferId()));
        Assertions.assertNull(transferDestinationsDAO.get(transfer2.getTransferId()));

        //check TransferItems table
        Assertions.assertNull(transferItemsDAO.get(transfer1.getTransferId()));
        Assertions.assertNull(transferItemsDAO.get(transfer2.getTransferId()));

        //check TransferTrucks table
        Assertions.assertNull(transferItemsDAO.get(transfer1.getTruckLicenseNumber()));
        Assertions.assertNull(transferItemsDAO.get(transfer2.getTruckLicenseNumber()));

    }

    @Test
    void updateTransferTest(){

        //check Transfer table
        transfer1.updateTransferTruck(98765432);
        transferDAO.update(transfer1);
        Assertions.assertEquals(98765432, transferDAO.get(transfer1.getTransferId()).getTruckLicenseNumber());

        transfer2.updateTransferTruck(98765432);
        transferDAO.update(transfer2);
        Assertions.assertEquals(98765432, transferDAO.get(transfer2.getTransferId()).getTruckLicenseNumber());

    }

    @Test
    void getAllTransfersTest(){
        Map<Integer, Transfer> allTransfers = transferDAO.getAllTransfers();

        Assertions.assertEquals(transfer1.getTransferId(), allTransfers.get(transfer1.getTransferId()).getTransferId());
        Assertions.assertEquals(transfer2.getTransferId(), allTransfers.get(transfer2.getTransferId()).getTransferId());

    }
}
