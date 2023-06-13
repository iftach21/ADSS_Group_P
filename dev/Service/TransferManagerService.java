package Service;

import Domain.Employee.Driver;
import Domain.Enums.TempLevel;
import Domain.Transfer.*;
import Interface.Transfer.TransferManagerInterface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TransferManagerService {
    private static TransferManagerService Instance = null;
    private TransferController transferController = TransferController.getInstance();
    private TruckController truckController = TruckController.getInstance();
    private Map<Site, Map<Item_mock, Integer>> orderItems;
    private Integer orderDestinationSiteId;

    public TransferManagerService() throws SQLException {
        transferController.createMockOrder();
    }

    public static TransferManagerService getInstance() throws SQLException {
        if(Instance==null){Instance = new TransferManagerService();}
        return Instance;
    }

    /**
     * Initialize order items.
     */
    private void initializeOrderItems(){
        orderItems = transferController.getOrderItemsFromQueue();
    }

    private void initializeOrderDestinationSiteId()
    {
        orderDestinationSiteId = transferController.getOrderDestinationSiteIdFromQueue();
    }

    private void resetQueueIfNoAvailableTruckOrDriver()
    {
        transferController.resetQueue(orderItems, orderDestinationSiteId);
    }

    /**
     * Returns order's sites names
     */
    public List<String> getOrderSitesNames(){
        List<String> sitesNames = new LinkedList<>();
        initializeOrderItems();
        initializeOrderDestinationSiteId();
        Site[] sites = orderItems.keySet().toArray(new Site[0]);
        for(int i=0; i<sites.length; i++){
            sitesNames.add(sites[i].getSiteName());
        }
        return sitesNames;
    }

    /**
     * Calculate arriving date and time based on leaving date and time.
     */
    private LocalDateTime calculateArrivingTime(Integer sourceSiteIndex, LocalTime leavingTime, LocalDate leavingDate) throws SQLException {
        Site[] sites = orderItems.keySet().toArray(new Site[0]);
        Site sourceSite = sites[sourceSiteIndex];
        List<Site> destinationSites = transferController.initializeDestinationsSites(sites, sourceSite, orderDestinationSiteId);
        return transferController.calculateArrivingTime(sourceSite, destinationSites, leavingTime, leavingDate);
    }

    /**
     * check if the given date is legal (not in the past)
     */
    public boolean checkIfDateIsLegal(LocalDate leavingDate, LocalTime leavingTime) throws SQLException {
        return transferController.checkIfDateIsLegal(leavingDate, leavingTime);
    }

    /**
     * check if there is an available storekeeper in the Super-Branch in the arriving date
     */
    public boolean checkIfStoreKeeperIsThere(Integer sourceSiteIndex, LocalTime leavingTime, LocalDate leavingDate) throws SQLException {
        LocalDateTime arrivingDateTime = calculateArrivingTime(sourceSiteIndex, leavingTime, leavingDate);
        return transferController.checkIfStoreKeeperIsThere(arrivingDateTime.toLocalDate(), arrivingDateTime.toLocalTime(), orderDestinationSiteId);
    }

    /**
        find all the details of available drivers based on the transfer's leaving time
        * return: Map<Integer, List<String>>.The key is driverID, The value is list which contains the next details:
          driver name, temp type, weight type
          if there are no available drivers, return null.
     */
    public Map<Integer, List<String>> findDriversDetailsForTransfer(LocalDate leavingDate, LocalTime leavingTime) throws SQLException {
        TempLevel currMinTemp = transferController.lowestTempItem(orderItems);
        List<Driver> drivers = transferController.findDriversForTransfer(leavingDate, leavingTime, currMinTemp);
        Map<Integer, List<String>> details = new HashMap<>();

        if(drivers.size() == 0) {
            resetQueueIfNoAvailableTruckOrDriver();
            return null;
        }

        for(int i=0; i<drivers.size(); i++) {
            Driver currentDriver = drivers.get(i);
            List<String> currentDetials = new ArrayList<>(3);
            currentDetials.add(currentDriver.getName());
            currentDetials.add(currentDriver.getTempType());
            currentDetials.add(currentDriver.getWeightType());
            details.put(currentDriver.getId(), currentDetials);

        }
        return details;
    }

    /**
        find truck to the transfer
     */
    public Truck findTruckByDriver(Integer chosenDriverId, LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime) throws SQLException{
        TempLevel currMinTemp = transferController.lowestTempItem(orderItems);
        List<Driver> drivers = transferController.findDriversForTransfer(leavingDate, leavingTime, currMinTemp);
        Driver chosenDriver = null;
        for(Driver d: drivers){
            if (d.getId() == chosenDriverId) {
                chosenDriver = d;
                break;
            }
        }
        Truck truck = truckController.findTruckByDriver(chosenDriver, currMinTemp, leavingDate, leavingTime, arrivingDate, arrivingTime);
        if(truck == null)
        {
            resetQueueIfNoAvailableTruckOrDriver();
        }
        return truck;
    }

    /**
     *  create new transfer, and add it to DAO.
     */
    public Transfer initializeNewTransfer(List<Site> destinationSites, Site[] sites, LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime, Truck chosenTruck, Driver chosenDriver, Site sourceSite, Map<Site, Map<Item_mock, Integer>> orderItems) throws SQLException{
        return transferController.initializeNewTransfer(destinationSites, sites, leavingDate, leavingTime, arrivingDate, arrivingTime, chosenTruck, chosenDriver, sourceSite, orderItems);
    }

    /**
     * check if given transferId is valid
     */
    public boolean isValidTransfer(int transferId)
    {
        if(transferController.getTransferByTransferId(transferId) == null)
            return false;
        return true;
    }

    /**
     * Create a document for a transfer
     * */
    public void createDocument(int transferId)
    {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        transfer.createDocument();
    }

    /**
     * Create new track
     */
    public void initializeAndAddNewTruck(int licenseNumber, String model, int netWeight, int maxWeight, int indexChosen) throws SQLException {
        transferController.initializeAndAddNewTruck(licenseNumber, model, netWeight, maxWeight, indexChosen);
    }

    /**
     * view planned transfers details.
     * Return: Map<Integer, List<String>>. The keys are the transfers ids and the values are the next details:
     * TRANSFER ID, Source site, Last destination, Leaving date, Leaving time, Arriving date, Arriving time
     */
    public Map<Integer, List<String>> getDetailsForPlannedTransfers(){
        return transferController.getDetailsForPlannedTransfers();
    }

}