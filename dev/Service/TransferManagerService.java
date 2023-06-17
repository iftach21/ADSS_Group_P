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
    private Map<Integer, Integer> lastDestination;

    public TransferManagerService() throws SQLException {
        transferController.createMockOrder();
        lastDestination = new LinkedHashMap<>();
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
     * get num of orders
     */
    public int numOfOrders()
    {
        return transferController.getNumOfOrders();
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

    public boolean checkIfStoreKeeperIsThereByArrivingTime(Integer lastDestID, LocalTime arrivingTime, LocalDate arrivingDate) throws SQLException {
        return transferController.checkIfStoreKeeperIsThere(arrivingDate, arrivingTime, lastDestID);
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
        lastDestination.put(newTransfer.getTransferId(), newTransfer.getListOfDestinations().get(newTransfer.getListOfDestinations().size()-1).getSiteId());
        transferController.addTransferTruckToDao(newTransfer);
        return newTransfer.getTransferId();
    }

    /**
     * get details for chosen truck
     */
    public List<String> getChosenTruckDetails(Integer transferId)
    {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        Truck transferTruck = truckController.getTruck(transfer.getTruckLicenseNumber());
        List<String> details = new LinkedList<>();
        details.add(transferTruck.getLicenseNumber()+"");
        details.add(transferTruck.getTruckModel());
        details.add(transferTruck.getMaxWeight()+"");
        details.add(transferTruck.getTempCapacity()+"");

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
        for (Item_mock item : siteItems.keySet()) {
            List<String> details = new LinkedList<>();
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

    /**
     * @param siteId
     * @return: site name by site id
     */
    public String getSiteNameBySiteId(Integer siteId)
    {
        return siteController.getSiteById(siteId).getSiteName();
    }

    /**
     * Update the truck weight at source site
     * @param transferId
     * @param truckWeight: the weight of the truck (as we received from transfer manager)
     * @return: true if the weight is valid (greater than 0 && smaller than max weight)
     * and updated successfully, false otherwise.
     * If false - need to rearrange
     **/
    public boolean updateTruckWeightAtSource(int transferId, Integer truckWeight)
    {
        if(truckWeight>=0) {
            Transfer newTransfer = transferController.getTransferByTransferId(transferId);
            transferController.updateWeightAtSource(newTransfer, truckWeight);
            newTransfer.documentUpdateTruckWeight(truckWeight, newTransfer.getSource());
            Truck transferTruck = truckController.getTruck(newTransfer.getTruckLicenseNumber());
            if(transferTruck.checkWeightCapacity())
                return true;
        }
        return false;
    }

    /**
     * Update the truck weight at some destination site
     * @param transferId
     * @param truckWeight: the weight of the truck (as we received from transfer manager)
     * @return: true if the weight is valid (greater than 0 && smaller than max weight)
     * and updated successfully, false otherwise.
     * If false - need to rearrange
     **/
    public boolean updatesWeightsAtDestination(int transferId, Integer truckWeight, Integer destId) throws SQLException {
        if(truckWeight>=0) {
            Transfer newTransfer = transferController.getTransferByTransferId(transferId);
            transferController.updateWeightsAtDestination(newTransfer, truckWeight, destId);
            Site dest = siteController.getSiteById(destId);
            newTransfer.documentUpdateTruckWeight(truckWeight, dest);
            Truck transferTruck = truckController.getTruck(newTransfer.getTruckLicenseNumber());
            if(transferTruck.checkWeightCapacity())
                return true;
        }
        return false;
    }

    /**
     * Returns Transfer destination Ids and names
     * Should be called if the transfer manager choose the option of removing destination (in rearrange)
     */
    public Map<Integer, String> getTransferDestinationNames(Integer transferId)
    {
        Map<Integer, String> destDetails = new HashMap<>();
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        for (int i = 0; i < transfer.getDestinations().size() - 1; i++)
        {
            destDetails.put(transfer.getListOfDestinations().get(i).getSiteId(),transfer.getListOfDestinations().get(i).getSiteName());
        }
        return destDetails;
    }

    /**
     * Remove specific destination from transfer
     * @param destId: Id of destination to remove
     * @param transferId: transfer Id
     */
    public void removeDest(Integer destId, Integer transferId)
    {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        Site dest = siteController.getSiteById(destId);
        transferController.removeDest(dest, transfer);
        transferController.updateArrivingTime(transfer);
    }

    /**
     * get details of available trucks (need to be called when rearrange and change truck)
     * @param transferId
     * @return Map<String, List<String>>. The key is the license number and the values are:
     * Truck model, Temperature Capacity, Weight Capacity
     */
    public Map<Integer, List<String>> getAvailableTrucksDetails(Integer transferId)
    {
        Map<Integer, List<String>> details = new HashMap<>();
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        for (Integer LicenseNum : truckController.getAllAvailableTrucks(transfer.getLeavingDate(), transfer.getLeavingTime(), transfer.getArrivingDate(), transfer.get_arrivingTime()).keySet())
        {
            List<String> currentDetails = new LinkedList<>();
            currentDetails.add(truckController.getTruck(LicenseNum).getTruckModel());
            currentDetails.add(truckController.getTruck(LicenseNum).getTempCapacity() + "");
            currentDetails.add(truckController.getTruck(LicenseNum).getTruckWeightType() + "");
            details.put(LicenseNum, currentDetails);
        }
        return details;
    }

    /**
     * Need to be called after the transfer manager chose a new truck for the transfer
     */
    public void updateNewChosenTruck(Integer transferId, Integer truckLicenseNumber) throws SQLException {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        transferController.updateTruck(transfer, truckLicenseNumber);
        chosenTruck = truckController.getTruck(truckLicenseNumber);
    }

    /**
     * returns details of items in destinations which the transfer manager wants to remove items from
     * @param : transfer id
     * @param: list containing the ids of the destination that the transfer manager wants to remove
     * items from.
     */
    public Map<Integer, Map<String, List<String>>> getDetailsOfItemsInDestsToRemove(List<Integer> destinationIds, Integer transferId)
    {
        Map<Integer, Map<String, List<String>>> sitesItems = new HashMap<>();
        for (int i=0; i<destinationIds.size(); i++)
        {
            Map<String, List<String>> Items = getSiteDetailsItems(destinationIds.get(i), transferId);
            sitesItems.put(destinationIds.get(i), Items);
        }
        return sitesItems;
    }

    /**
     * This function reduce the quantity of specific item
     * @param catalogNumber
     * @param newQuantity
     * @param destinationId
     * @param transferId
     * @return: true if the quantity updated successfully, false if the new quantity is wrong
     */
    public boolean reduceItemQuantityFromDest(String catalogNumber, Integer newQuantity, Integer destinationId, Integer transferId)
    {
        boolean result = true;
        Site site = siteController.getSiteById(destinationId);
        Map<String, List<String>>  details = getSiteDetailsItems(destinationId, transferId);
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        Integer currentQuantity = Integer.parseInt(details.get(catalogNumber).get(1));
        if(!transferController.checkQuantity(newQuantity, currentQuantity))
        {
            result = false;
        }
        else {
            transfer.updateQuantityOfItem(currentQuantity, newQuantity, site, catalogNumber);
            transfer.documentUpdateOrderItems();
            transferController.updateArrivingTime(transfer);
        }
        return result;
    }


    /**
     * This function resets the weights of the items to null in document
     * Need to be called after transfer rearrange!!!
     * @param transferId
     */
    public void resetDocumentAfterRearrange(Integer transferId)
    {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        transferController.resetDocumentAfterRearrange(transfer);
    }

    /**
     * This function returns details of current transfers
     * @return: Map<Integer, List<String>>. The key is the transfer id, The value is a list contains:
     * Source site name, last destination name.
     * If no current transfers occurs, then the returned map is empty!!
     */
    public Map<Integer, List<String>> getDetailsOfCurrentTransfers()
    {
        Map<Integer, Transfer> currentTransfers = transferController.getCurrentTransfers();
        Map<Integer, List<String>> details = new HashMap<>();
        for(Integer transferId: currentTransfers.keySet())
        {
            Transfer transfer = currentTransfers.get(transferId);
            List<String> currentDetails = new LinkedList<>();
            currentDetails.add(transfer.getSource().getSiteName());
            currentDetails.add(transfer.getListOfDestinations().get(transfer.getDestinations().size()-1).getSiteName());
            details.put(transferId, currentDetails);
        }
        return details;
    }

    /**
     * This function should be called if the transfer manager chose the option of update arriving
     * date and time of current transfer
     */
    public void updateArrivingDateTime(Integer transferId, LocalTime arrivingTime, LocalDate arrivingDate)
    {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        transferController.updateArrivingTime(transfer, arrivingDate, arrivingTime);
    }

    /**
     * @return source site name
     */
    public Map<Integer, String> getSourceSiteName(Integer transferId)
    {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        Map<Integer, String> sourceSite = new HashMap<>();
        sourceSite.put(transfer.getSource().getSiteId(), transfer.getSource().getSiteName());
        return sourceSite;
    }

    /**
     * @return last destination id of the transfer
     */
    public Integer getLastDestinationId(Integer transferId)
    {
        Transfer transfer = transferController.getTransferByTransferId(transferId);
        return transfer.getListOfDestinations().get(transfer.getDestinations().size()-1).getSiteId();
    }

}