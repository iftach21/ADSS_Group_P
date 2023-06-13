package Service;

import Domain.Employee.Driver;
import Domain.Enums.TempLevel;
import Domain.Transfer.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TransferManagerService {
    private static TransferManagerService Instance = null;
    private TransferController transferController = TransferController.getInstance();
    private TruckController truckController = TruckController.getInstance();
    private SiteController siteController = SiteController.getInstance();

    private Map<Site, Map<Item_mock, Integer>> orderItems;
    private Integer orderDestinationSiteId;
    private List<Site> destinationSites;
    private List<Driver> drivers;
    private Site[] sites;
    private Truck chosenTruck;

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

    /**
     * Initialize orderDestinationSiteId from queue
     */
    private void initializeOrderDestinationSiteId()
    {
        orderDestinationSiteId = transferController.getOrderDestinationSiteIdFromQueue();
    }

    /**
     * Initialize destination sites based on transfer source Site.
     * @param sourceSiteId
     */
    private void initializeDestinationsSites(Integer sourceSiteId){
        Site sourceSite = siteController.getSiteById(sourceSiteId);
        destinationSites = transferController.initializeDestinationsSites(sites, sourceSite, orderDestinationSiteId);
    }

    /**
     * Returns order's sites names
     */
    public Map<Integer, String>  getOrderSitesNames(){
        Map<Integer, String> sitesNames = new HashMap<>();
        initializeOrderItems();
        sites = orderItems.keySet().toArray(new Site[0]);
        initializeOrderDestinationSiteId();

        for(int i=0; i<sites.length; i++){
            sitesNames.put(sites[i].getSiteId(),sites[i].getSiteName());
        }

        return sitesNames;
    }

    /**
     * Calculate arriving date and time based on leaving date and time.
     */
    private LocalDateTime calculateArrivingTime(Integer sourceSiteId, LocalTime leavingTime, LocalDate leavingDate) throws SQLException {
        Site sourceSite = siteController.getSiteById(sourceSiteId);
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
    public boolean checkIfStoreKeeperIsThere(Integer sourceSiteId, LocalTime leavingTime, LocalDate leavingDate) throws SQLException {
        initializeDestinationsSites(sourceSiteId);
        LocalDateTime arrivingDateTime = calculateArrivingTime(sourceSiteId, leavingTime, leavingDate);
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
        drivers = transferController.findDriversForTransfer(leavingDate, leavingTime, currMinTemp, orderItems, orderDestinationSiteId);
        Map<Integer, List<String>> details = new HashMap<>();

        if(drivers.size() == 0)
            return null;

        for(int i=0; i<drivers.size(); i++) {
            Driver currentDriver = drivers.get(i);
            List<String> currentDetials = new ArrayList<>(3);
            currentDetials.add(currentDriver.getName());
            currentDetials.add(currentDriver.getTempType());
            currentDetials.add(currentDriver.getWeightType());
            details.put(currentDriver.getId(), currentDetials);

        }
        return details;    }

    /**
        find truck to the transfer
     */
    private void findTruckByDriver(Driver chosenDriver, LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime) throws SQLException{
        TempLevel currMinTemp = transferController.lowestTempItem(orderItems);
        chosenTruck = transferController.findTruckByDriver(chosenDriver, currMinTemp, leavingDate, leavingTime, arrivingDate, arrivingTime, orderItems, orderDestinationSiteId);
    }

    /**
     *  create new transfer, and add it to DAO.
     * @return: transfer id of the new transfer
     */
    public Integer initializeNewTransfer(LocalDate leavingDate, LocalTime leavingTime, Integer chosenDriverId, Integer sourceSiteId) throws SQLException{
        Site sourceSite = siteController.getSiteById(sourceSiteId);
        LocalDateTime arrivingDateTime = transferController.calculateArrivingTime(sourceSite, destinationSites, leavingTime, leavingDate);

        Driver chosenDriver = getChosenDriver(chosenDriverId);

        findTruckByDriver(chosenDriver, leavingDate, leavingTime, arrivingDateTime.toLocalDate(), arrivingDateTime.toLocalTime());

        Transfer newTransfer = transferController.initializeNewTransfer(destinationSites, sites, leavingDate, leavingTime, arrivingDateTime.toLocalDate(), arrivingDateTime.toLocalTime(), chosenTruck, chosenDriver, sourceSite, orderItems);
        transferController.addTransferTruckToDao(newTransfer);
        return newTransfer.getTransferId();
    }

    /**
     * get details for chosen truck
     */
    public List<String> getChosenTruckDetails()
    {
        List<String> details = new LinkedList<>();
        details.add(chosenTruck.getLicenseNumber()+"");
        details.add(chosenTruck.getTruckModel());
        details.add(chosenTruck.getMaxWeight()+"");
        details.add(chosenTruck.getTempCapacity()+"");

        return details;
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

    /**
     * @param chosenDriverId
     * @return chosen Driver by id
     */
    private Driver getChosenDriver(Integer chosenDriverId)
    {
        Driver chosenDriver = null;
        for(Driver d: drivers){
            if (d.getId() == chosenDriverId) {
                chosenDriver = d;
                break;
            }
        }
        return chosenDriver;
    }

    /**
     * get specific site items details
     * @return: Map<String, List<String>>. The key is the item catalog number, the values are:
     * item name, item quantity.
     */
    private Map<String, List<String>> getSiteDetailsItems(Integer siteId, Integer transferId)
    {
        Transfer newTransfer = transferController.getTransferByTransferId(transferId);
        Map<Item_mock, Integer> siteItems = transferController.getSiteItems(siteId, newTransfer);
        Map<String, List<String>> siteItemsDetails = new HashMap<>();
        List<String> details = new LinkedList<>();
        for (Item_mock item : siteItems.keySet()) {
            details.add(item.getItemName());
            details.add(siteItems.get(item)+"");
            siteItemsDetails.put(item.getCatalogNum(), details);
        }
        return siteItemsDetails;
    }

    /**
     * get all sites items details
     * @return: Map<Integer, Map<String, List<String>>). The key is the siteId, the value is the map as described in
     * getSiteDetailsItems() function.
     */
    public Map<Integer, Map<String, List<String>>> getAllSitesItemsDetails(Integer transferId)
    {
        Transfer newTransfer = transferController.getTransferByTransferId(transferId);
        Site sourceSite = newTransfer.getSource();
        Map<Integer, Map<String, List<String>>> sitesItems = new HashMap<>();
        Map<String, List<String>> sourceItems = getSiteDetailsItems(sourceSite.getSiteId(), transferId);
        sitesItems.put(sourceSite.getSiteId(), sourceItems);

        List<Site> transferDest = newTransfer.getListOfDestinations();
        for (int i=0; i<transferDest.size() - 1; i++)
        {
            Map<String, List<String>> Items = getSiteDetailsItems(transferDest.get(i).getSiteId(), transferId);
            sitesItems.put(transferDest.get(i).getSiteId(), Items);
        }
        return sitesItems;
    }

}