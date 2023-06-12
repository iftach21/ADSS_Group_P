package Service;

import Domain.Employee.Driver;
import Domain.Enums.TempLevel;
import Domain.Transfer.*;
import Interface.Transfer.TransferManagerInterface;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class TransferManagerService {
    private TransferController transferController = TransferController.getInstance();
    private TruckController truckController = TruckController.getInstance();

    public TransferManagerService() throws SQLException {
    }

    /**
     * Calculate arriving date and time based on leaving date and time.
     */
    public LocalDateTime calculateArrivingTime(Site sourceSite, List<Site> destinationSites, LocalTime leavingTime, LocalDate leavingDate) throws SQLException {
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
    public boolean checkIfStoreKeeperIsThere(LocalDate arrivingDate, LocalTime arrivingTime, int orderDestinationSiteId) throws SQLException {
        return transferController.checkIfStoreKeeperIsThere(arrivingDate, arrivingTime, orderDestinationSiteId);
    }

    /**
     * get current min temp
     */
    public TempLevel lowestTempItem(Map<Site, Map<Item_mock, Integer>> orderItems) throws SQLException {
        return transferController.lowestTempItem(orderItems);
    }

    /**
        find all the available drivers based on the transfer's leaving time
     */
    public List<Driver> findDriversForTransfer(LocalDate leavingDate, LocalTime leavingTime, TempLevel currMinTemp) throws SQLException {
        return transferController.findDriversForTransfer(leavingDate, leavingTime, currMinTemp);
    }

    /**
        find truck to the transfer
     */
    public Truck findTruckByDriver(Driver chosenDriver, TempLevel currMinTemp, LocalDate leavingDate, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime) throws SQLException{
        return truckController.findTruckByDriver(chosenDriver, currMinTemp, leavingDate, leavingTime, arrivingDate, arrivingTime);
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