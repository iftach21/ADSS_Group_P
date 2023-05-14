package Domain.Transfer;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

import DataAccesObjects.Transfer.Item_mockDAO;
import DataAccesObjects.Transfer.SiteDAO;
import DataAccesObjects.Transfer.TransferDAO;
import Domain.Employee.Driver;
import Domain.Employee.DriverController;
import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.WindowTypeCreater;


public class TransferController {
    private static TransferController Instance = null;
    private int _documentsCounter;
    private TruckController tc = TruckController.getInstance();
    private DriverController dc = DriverController.getInstance();
    private SiteController sc = SiteController.getInstance();
    private Queue<Map<Site, Map<Item_mock, Integer>>> _ordersQueue; //should be taken from suppliers DB (in the future)
    private Queue<Integer> _orderDestinationSiteIdQueue; //should be taken from suppliers DB (in the future)
    private final TransferDAO transfersDAO;
    private WeeklyShiftAndWorkersManager weeklyShiftManager = WeeklyShiftAndWorkersManager.getInstance();

    public TransferController() throws SQLException {
        //_transfers = new HashMap<>();
        this.transfersDAO = TransferDAO.getInstance();
        _documentsCounter = 0;
        this._ordersQueue = new LinkedList<>();
        this._orderDestinationSiteIdQueue = new LinkedList<>();
    }

    public static TransferController getInstance() throws SQLException {
        if(Instance==null){Instance = new TransferController();}
        return Instance;
    }

    public void startTransferSystem() throws SQLException {
        boolean systemIsOn = true;
        Scanner scanner = new Scanner(System.in);
        //here we supposed to check the database for new orders
        //in this stage we don't have any orders' database, so we will create some mock orders from the mock data in item and sites
        //create mock orders
        createMockOrder();

        while (systemIsOn)
        {
            System.out.println("------------------------------------------------------------");
            System.out.println("Hello transfer manager, welcome to the transfer system!");
            System.out.println("Please pick one of the following options:");
            System.out.println("1. View and download a transfer document of your choice");
            System.out.println("2. Create transfer for pending orders. you have " + _ordersQueue.size() + " orders waiting to transfer");
            System.out.println("3. Update current transfers ");
            System.out.println("4. Add a new truck to the system ");
            System.out.println("5. View Planned Transfers");
            System.out.println("6. Exit the transfer system");

            int optionSelection;
            while(true) {
                try {
                    optionSelection = scanner.nextInt();
                    if (optionSelection == 1 || optionSelection == 2 || optionSelection == 3 || optionSelection == 4 || optionSelection == 5 || optionSelection == 6) {
                        break;
                    } else {
                        System.out.println("Sorry transfer manager, but your input is illegal. please enter number in th range 1 - 3");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter number in th range 1 - 3");
                    scanner.next();
                }
            }

            if (optionSelection == 1)
            {
                viewTransferDocument();
            }
            else if (optionSelection == 2)
            {
                if (_ordersQueue.size() == 0)
                {
                    System.out.println("Unfortunatly, there are no orders to handle. You'll be taken to the main menu.");
                    System.out.println("------------------------------------------------------------");
                }
                else
                {
                    createNewTransfer(_ordersQueue.remove(), _orderDestinationSiteIdQueue.remove());
                    System.out.println("You'll be taken to the main menu.");
                }
            }
            else if (optionSelection == 3)
            {
                Map<Integer, Transfer> currentTransfers = getCurrentTransfers();
                if (currentTransfers.size() == 0)
                {
                    System.out.println("Unfortunatly, there are no orders to handle. You'll be taken to the main menu.");
                    System.out.println("------------------------------------------------------------");
                }
                else
                {
                    System.out.println("Those are the transfers that are currently takes place:");
                    for(Integer transferId: currentTransfers.keySet())
                    {
                        Transfer transfer = currentTransfers.get(transferId);
                        System.out.println("transfer Id: " + transferId + ", Source site: " + transfer.getSource().getSiteName() + ", Destination site: " + transfer.getListOfDestinations().get(transfer.getDestinations().size()-1).getSiteName());
                    }
                    System.out.println("Please enter the transferId of your chosen transfer");
                    int transferId;
                    while(true) {
                        try {
                            transferId = scanner.nextInt();
                            if (transfersDAO.get(transferId)!=null) {
                                break;
                            } else {
                                System.out.println("Sorry transfer manager, but your input is illegal. please enter a valid transfer Id");
                            }
                        }
                        catch (Exception e)
                        {
                            System.out.println("Sorry transfer manager, but your input is illegal. please enter a valid transfer Id");
                            scanner.next();
                        }
                    }
                    updateCurrentTransferDetails(transfersDAO.get(transferId));
                    System.out.println("You'll be taken to the main menu.");
                }
            }
            else if(optionSelection == 4)
            {
                addTruckToSystem();
                System.out.println("Truck added successfully. You'll be taken to the main menu.");
            }
            else if(optionSelection == 5)
            {
                watchPlannedTransfers();
            }
            else
            {
                System.out.println("Thanks for using the transfer system. See you next time!");
                systemIsOn = false;
            }
        }
    }

    public void newOrderReceived(Map<Site, Map<Item_mock, Integer>> orderItems, Integer orderDestinationSiteId)
    {
        _ordersQueue.add(orderItems);
        _orderDestinationSiteIdQueue.add(orderDestinationSiteId);
    }


    public void createNewTransfer(Map<Site, Map<Item_mock, Integer>> orderItems, Integer orderDestinationSiteId) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the next details for the new transfer: ");

        System.out.println("Please choose source site from the next options (enter number): ");
        Site[] sites = orderItems.keySet().toArray(new Site[0]);
        for(int i=0; i<sites.length; i++){
            System.out.println(i+1 + ". " + sites[i].getSiteName());
        }
        Site sourceSite;
        while(true)
        {
            try {
                int sourceSiteNum = scanner.nextInt();
                if (sourceSiteNum <= sites.length && sourceSiteNum >= 1)
                {
                    sourceSite = sites[sourceSiteNum-1];
                    break;
                }
                else
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter number in th range 1 - " + sites.length);
                }
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please enter number in th range 1 - " + sites.length);
                scanner.next();
            }
        }

        List<Site> tempList = Arrays.asList(sites);

        List<Site> destinationSites = new ArrayList<>(tempList);

        destinationSites.remove(sourceSite);

        destinationSites.add(sc.getSiteById(orderDestinationSiteId));

        LocalDate arrivingDate;
        LocalTime arrivingTime;

        LocalDate leavingDate;
        LocalTime leavingTime;

        while(true)
        {
            //choose date
            leavingDate = chooseDateForTransfer();

            //choose time
            leavingTime = chooseTimeForTransfer();

            //calculate arriving date and time
            LocalDateTime arrivingDateTime = calculateArrivingTime(sourceSite, destinationSites, leavingTime, leavingDate);
            arrivingDate = arrivingDateTime.toLocalDate();
            arrivingTime = arrivingDateTime.toLocalTime();

            if (!checkIfDateIsLegal(leavingDate, leavingTime))
            {
                System.out.println("Sorry transfer manager, but the date you entered is in the past. Please enter a date in the future.");
            }
            else if (!checkIfStoreKeeperIsThere(arrivingDate, arrivingTime, orderDestinationSiteId))
            {
                System.out.println("Sorry transfer manager, there is no stoke in the last destination. Please enter another date.");
            }
            else
            {
                break;
            }
        }

        //get lowest temp item
        TempLevel currMinTemp = lowestTempItem(orderItems);

        //choose driver for transfer
        Driver chosenDriver = chooseDriverForTransfer(leavingDate, leavingTime, currMinTemp);
        if (chosenDriver == null)
        {
            System.out.println("Unfortunately, there is no available driver to this transfer.");
            _ordersQueue.add(orderItems);
            _orderDestinationSiteIdQueue.add(orderDestinationSiteId);
            return;
        }

        //choose truck by the chosen driver
        Truck chosenTruck = tc.findTruckByDriver(chosenDriver, currMinTemp, leavingDate, leavingTime, arrivingDate, arrivingTime);
        if (chosenTruck == null)
        {
            System.out.println("Unfortunately, there is no available truck to this transfer. You'll have to start all over again.");
            _ordersQueue.add(orderItems);
            _orderDestinationSiteIdQueue.add(orderDestinationSiteId);
            return;
        }

        Map<Site, Integer> destinationAndWeights = new HashMap<>();
        for(int i=0; i < sites.length; i++)
        {
            destinationAndWeights.put(sites[i], 0);
        }

        Transfer newTransfer = new Transfer(leavingDate, leavingTime, arrivingDate, arrivingTime, chosenTruck.getLicenseNumber(), chosenDriver.getName(), sourceSite, destinationAndWeights, orderItems, _documentsCounter, -1);
        newTransfer.addToDAO(orderItems, destinationAndWeights);
        transfersDAO.add(newTransfer);

        newTransfer.createDocument();
        _documentsCounter++;

        System.out.println("Thanks manager! The transfer will be ready in short time. You'll now need to predict the weight in each destination, and rearrange the transfer if needed.");
        startTransfer(newTransfer);
    }

    public void startTransfer(Transfer newTransfer) throws SQLException {
        while(true)
        {
            boolean transferRearranged = false;

            Truck transferTruck = tc.getTruck(newTransfer.getTruckLicenseNumber());
            transferTruck.addToDAO(newTransfer.getTransferId());

            System.out.println("The truck chosen to the transfer is: ");
            System.out.println("License Number: " + transferTruck.getLicenseNumber());
            System.out.println("Truck Model: " + transferTruck.getTruckModel());
            System.out.println("Truck Max Weight: " + transferTruck.getMaxWeight());
            System.out.println("Truck Cooling Capacity: " + transferTruck.getTempCapacity());
            System.out.println("------------------------------------------------------------");

            System.out.println("The driver chosen to the transfer is: ");
            System.out.println("Driver Name: " + newTransfer.getDriverName());
            System.out.println("------------------------------------------------------------");

            transferRearranged = updateWeightsForTransfer(newTransfer, false);

            if (transferRearranged) {
                continue;
            }
            else
            {
                List<Site> transferDest = newTransfer.getListOfDestinations();
                System.out.println("It will unload all the items at his final destination - " + transferDest.get(transferDest.size() - 1).getSiteName());
                System.out.println("------------------------------------------------------------");
                break;
            }
        }
    }

    public void rearrangeTransfer(Transfer transfer) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Unfortunately, the truck will be in overweight so the transfer need to be rearranged.");
        System.out.println("Please choose one of the following options: ");
        System.out.println("1. Change the transfer destinations");
        System.out.println("2. Change the transfer truck");
        System.out.println("3. Drop transfer Items");
        int selectedOption;
        while(true)
        {
            try {
                selectedOption = scanner.nextInt();
                if (selectedOption <= 3 && selectedOption >= 1)
                {
                    break;
                }
                else
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - 3");
                }
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - 3");
                scanner.next();
            }
        }

        if (selectedOption == 1)
        {
            removeOneDestOfTransfer(transfer);
            LocalDateTime arrivingTime = calculateArrivingTime(transfer.getSource(), transfer.getListOfDestinations(), transfer.getLeavingTime(), transfer.getLeavingDate());
            transfer.setArrivingTime(arrivingTime.toLocalTime());
            transfersDAO.update(transfer);
            transfer.setArrivingDate(arrivingTime.toLocalDate());
            transfersDAO.update(transfer);
        }
        else if (selectedOption == 2)
        {
            changeTruckOfTransfer(transfer);
        }
        else if (selectedOption == 3)
        {
            removeItemsOfTransfer(transfer);
            LocalDateTime arrivingTime = calculateArrivingTime(transfer.getSource(), transfer.getListOfDestinations(), transfer.getLeavingTime(), transfer.getLeavingDate());
            transfer.setArrivingTime(arrivingTime.toLocalTime());
            transfersDAO.update(transfer);
            transfer.setArrivingDate(arrivingTime.toLocalDate());
            transfersDAO.update(transfer);
        }

        transfer.documentUpdateTruckWeight(null, transfer.getSource());
        for(int i=0; i<transfer.getDestinations().size()-1; i++){
            transfer.documentUpdateTruckWeight(null, transfer.getListOfDestinations().get(i));
        }
    }

    public TempLevel lowestTempItem(Map<Site, Map<Item_mock, Integer>> orderItems)
    {
        TempLevel currMinTemp = TempLevel.regular;
        for (Site site : orderItems.keySet()) {
            for (Item_mock product : orderItems.get(site).keySet())
            {
                if (product.getItemTemp().compareTo(currMinTemp) > 0)
                    currMinTemp = product.getItemTemp();
            }
        }
        return currMinTemp;
    }

    public Map<Integer, Transfer> getCurrentTransfers(){
        Map<Integer, Transfer> currentTransfers = new HashMap<>();
        Map<Integer, Transfer> allTransfer = transfersDAO.getAllTransfers();

        for(Integer trandferId: allTransfer.keySet()){
            Transfer transfer = allTransfer.get(trandferId);
            LocalDateTime transferLocalDateTimeLeaving = LocalDateTime.of(transfer.getDateOfTransfer(), transfer.getLeavingTime());
            LocalDateTime transferLocalDateTimeArriving = LocalDateTime.of(transfer.getArrivingDate(), transfer.get_arrivingTime());
            if(transferLocalDateTimeLeaving.isBefore(LocalDateTime.now()) && transferLocalDateTimeArriving.isAfter(LocalDateTime.now()))
            {
                currentTransfers.put(trandferId, transfer);
            }
        }

        return currentTransfers;
    }

    public boolean updateWeightsForTransfer(Transfer newTransfer, boolean happensRightNow) throws SQLException {
        int truckWeight;

        Scanner scanner = new Scanner(System.in);
        Truck transferTruck = tc.getTruck(newTransfer.getTruckLicenseNumber());

        boolean transferRearranged = false;

        if (!happensRightNow)
        {
            System.out.print("The truck's driver will start his drive from "+ newTransfer.getSource().getSiteName() + ", ");
            System.out.println("And he will pick up the following items: ");
        }
        else
        {
            System.out.print("The truck's driver started his drive from "+ newTransfer.getSource().getSiteName() + ", ");
            System.out.println("And he picked up the following items: ");
        }

        Map<Site, Map<Item_mock, Integer>> transferOrderItems = newTransfer.getOrderItems();
        Map<Item_mock, Integer> sourceItems = transferOrderItems.get(newTransfer.getSource());
        for (Item_mock item : sourceItems.keySet()) {
            System.out.println("Item name: " + item.getItemName() + ", Quantity: " + sourceItems.get(item));
        }

        if (!happensRightNow)
            System.out.println("Please enter the expected weight of the truck: ");
        else
            System.out.println("Please enter the weight of the truck: ");

        while(true)
        {
            try {
                truckWeight = scanner.nextInt();
                if (truckWeight >=0)
                {
                    transferTruck.updateWeight(truckWeight);
                    tc.updateTruck(transferTruck);
                    newTransfer.setWeightAtSource(truckWeight);
                    transfersDAO.update(newTransfer);
                    break;
                }
                else
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter a positive whole number");
                }
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please enter a positive whole number");
                scanner.next();
            }

        }
        newTransfer.documentUpdateTruckWeight(truckWeight, newTransfer.getSource());

        while (!transferTruck.checkWeightCapacity() && !transferRearranged)
        {
            transferRearranged = true;
            rearrangeTransfer(newTransfer);
            System.out.println("After the transfer rearranged, we will start all over again, so you can enter the updated weights.");
        }

        if (transferRearranged) {
            return true;
        }
        List<Site> transferDest = newTransfer.getListOfDestinations();
        for (int i=0; i<transferDest.size() - 1 && !transferRearranged; i++)
        {
            if (!happensRightNow)
            {
                System.out.print("Next, the truck's driver will arrive to: "+ transferDest.get(i).getSiteName() + ", ");
                System.out.println("And he will pick up the following items: ");
            }
            else
            {
                System.out.print("Next, the truck's driver arrived to: "+ transferDest.get(i).getSiteName() + ", ");
                System.out.println("And he picked up the following items: ");
            }

            Map<Item_mock, Integer> destItems = transferOrderItems.get(transferDest.get(i));
            for (Item_mock item : destItems.keySet()) {
                System.out.println("Item name: " + item.getItemName() + ", Quantity: " + destItems.get(item));
            }

            System.out.println("Please enter expected the weight of the truck: ");
            while(true)
            {
                try {
                    truckWeight = scanner.nextInt();
                    if (truckWeight >=0)
                    {
                        transferTruck.updateWeight(truckWeight);
                        tc.updateTruck(transferTruck);
                        break;
                    }
                    else
                    {
                        System.out.println("Sorry transfer manager, but your input is illegal. please enter a positive whole number");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter a positive whole number");
                    scanner.next();
                }
            }
            newTransfer.documentUpdateTruckWeight(truckWeight, transferDest.get(i));
            while (!transferTruck.checkWeightCapacity() && !transferRearranged)
            {
                transferRearranged = true;
                rearrangeTransfer(newTransfer);
                System.out.println("After the transfer rearranged, we will start all over again, so you can enter the updated weights.");
            }
        }
        return transferRearranged;
    }

    public void removeOneDestOfTransfer(Transfer transfer)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("These are the transfer destinations. Please choose one destination to remove: ");
        for (int i = 0; i < transfer.getDestinations().size() - 1; i++)
        {
            System.out.println((i + 1) + "." + transfer.getListOfDestinations().get(i).getSiteName());
        }

        int destToRemove;
        while(true)
        {
            try {
                destToRemove = scanner.nextInt();
                if (transfer.getDestinations().size() - 1 <= 3 && destToRemove >= 1)
                {
                    break;
                }
                else
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - " + (transfer.getDestinations().size() - 1));
                }
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - " + (transfer.getDestinations().size() - 1));
                scanner.next();
            }
        }

        transfer.documentRemoveDestination(transfer.getListOfDestinations().get(destToRemove - 1));
        transfer.removeTransferDestination(transfer.getListOfDestinations().get(destToRemove - 1));
    }

    public void changeTruckOfTransfer(Transfer transfer) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("These are the available trucks. Please choose one of the trucks, by type its license number: ");
        for (Integer LicenseNum : tc.getAllAvailableTrucks(transfer.getLeavingDate(), transfer.getLeavingTime(), transfer.getArrivingDate(), transfer.get_arrivingTime()).keySet())
        {
            System.out.println("License number: " + LicenseNum + ", Domain.Truck Model: " + tc.getTruck(LicenseNum).getTruckModel() + ", Temperature Capacity: " + tc.getTruck(LicenseNum).getTempCapacity() + ", Weight Capacity: " + tc.getTruck(LicenseNum).getTruckWeightType());
        }

        int chosenTruck;

        while(true)
        {
            try {
                chosenTruck = scanner.nextInt();
                if (tc.getAllAvailableTrucks(transfer.getLeavingDate(), transfer.getLeavingTime(), transfer.getArrivingDate(), transfer.get_arrivingTime()).containsKey(chosenTruck))
                {
                    break;
                }
                else
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter a valid license number");
                }
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please enter a valid license number");
                scanner.next();
            }
        }
        tc.getTruck(transfer.getTruckLicenseNumber()).deleteFromDAO(transfer.getTransferId());
        tc.getTruck(chosenTruck).addToDAO(transfer.getTransferId());

        tc.updateTruck(tc.getTruck(transfer.getTruckLicenseNumber()));
        tc.updateTruck(tc.getTruck(chosenTruck));

        transfer.updateTransferTruck(chosenTruck);
        transfersDAO.update(transfer);
        transfer.documentUpdateTruckNumber();
    }

    public void removeItemsOfTransfer(Transfer transfer)
    {
        Scanner scanner = new Scanner(System.in);
        Map<Site, Map<Item_mock, Integer>> orderItemsToDelete = new HashMap<>();
        boolean removeMoreDestinations = true;

        List<Site> allDestinations = new ArrayList<>(transfer.getListOfDestinations());
        List<Site> allDestToReduceItems = new ArrayList<>();
        while (removeMoreDestinations)
        {
            if (allDestinations.size() - 1 == 0) {
                System.out.println("there are no more destinations to drop items from.");
                break;
            }
            System.out.println("These are the destinations the driver pick up items for. Please choose by enter the index number, the destination you would like to drop items from:");
            Site destToReduceItems;
            for (int i = 0; i < allDestinations.size() - 1; i++) {
                System.out.println((i+1) + ". " + allDestinations.get(i).getSiteName());
            }

            //get input dest from the manager
            int chosenIndexDest;

            while(true)
            {
                try {
                    chosenIndexDest = scanner.nextInt();
                    if (chosenIndexDest >= 1 && chosenIndexDest <= (allDestinations.size() - 1))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - " + (allDestinations.size() - 1));
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - " + (allDestinations.size() - 1));
                    scanner.next();
                }
            }

            destToReduceItems = allDestinations.get(chosenIndexDest - 1);

            //add the destination to the list of destinations we remove
            allDestToReduceItems.add(destToReduceItems);

            //remove the destination from all destinations list
            allDestinations.remove(destToReduceItems);

            //ask if the manager would like to remove products from another destination
            System.out.println("Would you like to remove products from another destination? press 'y' for yes and 'n' for no.");
            System.out.println("Note that in the next step, you will choose the quantity of the items to drop from each destination you selected.");
            char yesOrNoInput;

            while(true)
            {
                try {
                    yesOrNoInput = scanner.next().charAt(0);
                    if (yesOrNoInput == 'y' || yesOrNoInput == 'Y' || yesOrNoInput == 'n' || yesOrNoInput == 'n')
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Sorry transfer manager, but your input is illegal. Please enter 'y' or 'n'");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. Please enter 'y' or 'n'");
                    scanner.next();
                }
            }

            if (yesOrNoInput == 'n')
                removeMoreDestinations = false;
        }

        //now for each destination in the allDestToReduceItems list I will ask which items to reduce
        for (Site destToReduceFrom: allDestToReduceItems) {
            Map<Item_mock, Integer> itemToChosenDest = transfer.getOrderItems().get(destToReduceFrom);
            Item_mock[] items = itemToChosenDest.keySet().toArray(new Item_mock[0]);
            boolean removeMoreItems = true;
            Map<Item_mock, Integer> howMuchToReduce = new HashMap<>();

            while(removeMoreItems && items.length > 0)
            {
                System.out.println("Please choose an item you would like to reduce his quantity from the site " + destToReduceFrom.getSiteName());
                for (int i = 0; i < items.length; i++)
                {
                    System.out.println((i + 1) + ". " + items[i].getItemName());
                }

                //
                int chosenIndexItem;

                while(true)
                {
                    try {
                        chosenIndexItem = scanner.nextInt();
                        if (chosenIndexItem >= 1 && chosenIndexItem <= items.length)
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - " + items.length);
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 1 - " + items.length);
                        scanner.next();
                    }
                }

                Item_mock itemToReduceQuantity = items[chosenIndexItem - 1];

                System.out.println("And now, please choose how much " + itemToReduceQuantity.getItemName() + " would you like to reduce: ");
                System.out.println("Current quantity: " + itemToChosenDest.get(itemToReduceQuantity));
                int chooseQuantity;

                while(true)
                {
                    try {
                        chooseQuantity = scanner.nextInt();
                        if (chooseQuantity >= 0 && chooseQuantity <= itemToChosenDest.get(itemToReduceQuantity))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 0 - " + itemToChosenDest.get(itemToReduceQuantity));
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Sorry transfer manager, but your input is illegal. please enter a number between 0 - " + itemToChosenDest.get(itemToReduceQuantity));
                        scanner.next();
                    }
                }

                //items to reduce and the quantity to reduce
                howMuchToReduce.put(itemToReduceQuantity, chooseQuantity);

                //check if he wants to reduce more items from this site
                System.out.println("Would you like to remove more products from this destination? press 'y' for yes and 'n' for no");
                char yesOrNoInput;

                while(true)
                {
                    try {
                        yesOrNoInput = scanner.next().charAt(0);
                        if (yesOrNoInput == 'y' || yesOrNoInput == 'Y' || yesOrNoInput == 'n' || yesOrNoInput == 'n')
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Sorry transfer manager, but your input is illegal. Please enter 'y' or 'n'");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Sorry transfer manager, but your input is illegal. Please enter 'y' or 'n'");
                        scanner.next();
                    }
                }

                if (yesOrNoInput == 'n')
                    removeMoreItems = false;
            }

            //add to the reduction map
            orderItemsToDelete.put(destToReduceFrom, howMuchToReduce);

        }

        //now remove all the items you gained
        transfer.removeTransferItems(orderItemsToDelete);
        transfer.documentUpdateOrderItems();

    }

    public LocalDate chooseDateForTransfer()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the leaving date of the transfer, in this format - dd/mm/yyyy: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate leavingDate;
        while(true)
        {
            try {
                String date = scanner.next();
                leavingDate = LocalDate.parse(date, formatter);
                break;
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
            }
        }
        return leavingDate;
    }

    public LocalTime chooseTimeForTransfer()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the leaving time of the transfer, in this format - HH:mm : ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime leavingTime;
        while(true)
        {
            try {
                String time = scanner.next();
                leavingTime = LocalTime.parse(time, formatter);
                break;
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
            }
        }
        return leavingTime;
    }

    public boolean checkIfDateIsLegal(LocalDate leavingDate, LocalTime leavingTime)
    {
        LocalDateTime transferLocalDateTimeLeaving = LocalDateTime.of(leavingDate, leavingTime);
        return transferLocalDateTimeLeaving.isAfter(LocalDateTime.now());

    }

    public Driver chooseDriverForTransfer(LocalDate leavingDate, LocalTime leavingTime, TempLevel currMinTemp) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        //get week num
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = leavingDate.get(weekFields.weekOfWeekBasedYear());

        //get the day number in the week
        DayOfWeek dayOfWeek = leavingDate.getDayOfWeek();
        int dayOfWeekNum = dayOfWeek.getValue();

        //create window type
        WindowTypeCreater wt = new WindowTypeCreater();

        //check whether the transfer leaves in dayshift or nightshift
        String shift;
        if (leavingTime.isAfter(LocalTime.NOON))
            shift = "day";
        else
            shift = "night";
        //
        List<Driver> Drivers = dc.findDriver(currMinTemp, weekNumber, leavingDate.getYear(), wt.getwidowtype(dayOfWeekNum, shift));

        if (Drivers.size() ==0)
            return null;

        System.out.println("Now, please choose 1 driver from the following list:");
        for (int i = 0; i < Drivers.size(); i++)
        {
            System.out.println((i+1) + ". ID: " + Drivers.get(i).getId() + ", Name:" + Drivers.get(i).getName() + ", License Weight Capacity:" + Drivers.get(i).getDriverLicense().getLicenseWeightCapacity() + ", License Temp Capacity:" + Drivers.get(i).getDriverLicense().getLicenseTempCapacity());
        }
        Driver chosenDriver;
        int chosenDriverIndex;
        while(true)
        {
            try {
                chosenDriverIndex = scanner.nextInt();
                if (chosenDriverIndex >= 1 && chosenDriverIndex <= Drivers.size()) {
                    chosenDriver = Drivers.get(chosenDriverIndex - 1);
                    break;
                }
                else
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please choose a number between 1-" + (Drivers.size()-1));
                }
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                scanner.next();
            }
        }

        return chosenDriver;
    }

    public void updateCurrentTransferDetails(Transfer transferToUpdate) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose one of the following options:");
        System.out.println("1. Change weights reported in each site");
        System.out.println("2. Update transfer arriving time");
        int optionChosen;
        while(true)
        {
            try {
                optionChosen = scanner.nextInt();
                if(optionChosen == 1 || optionChosen == 2)
                {
                    break;
                }
                else
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                }
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                scanner.next();
            }
        }

        if (optionChosen == 1)
        {
            updateWeightsForTransfer(transferToUpdate, true);
        }
        else
        {
            System.out.println("Please enter the updated arriving date of the transfer, in this format - dd/mm/yyy: ");
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate arrivingDate;
            while(true)
            {
                try {
                    String date = scanner.next();
                    arrivingDate = LocalDate.parse(date, formatter1);
                    break;
                }
                catch (Exception e)
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                    scanner.next();
                }
            }

            transferToUpdate.setArrivingDate(arrivingDate);
            transfersDAO.update(transferToUpdate);

            //update arriving time
            System.out.println("Please enter the updated arriving time of the transfer, in this format - HH:mm : ");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime arrivingTime;
            while(true)
            {
                try {
                    String time = scanner.next();
                    arrivingTime = LocalTime.parse(time, formatter2);
                    break;
                }
                catch (Exception e)
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                    scanner.next();
                }
            }
            transferToUpdate.setArrivingTime(arrivingTime);
            transfersDAO.update(transferToUpdate);
        }
    }

    public void addTruckToSystem() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To add a new truck to the transfer system, you'll need to enter the following details:");

        System.out.println("Please enter the truck License Number. Please note that license number is 8 digits long:");
        int licenseNumber;
        while(true)
        {
            try {
                licenseNumber = scanner.nextInt();
                if (licenseNumber >= 0 && licenseNumber <= 99999999)
                    break;
                else
                    System.out.println("Sorry transfer manager, but your input is illegal. please try again");
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                scanner.next();
            }
        }

        System.out.println("Please enter the truck Model:");
        String model = scanner.nextLine();
        model += scanner.nextLine();

        System.out.println("Please enter the truck net Weight. Note that truck weight is between 0 - 60 tons:");
        int netWeight;
        while(true)
        {
            try {
                netWeight = scanner.nextInt();
                if (netWeight >= 0 && netWeight <= 60)
                    break;
                else
                    System.out.println("Sorry transfer manager, but your input is illegal. please try again");
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                scanner.next();
            }
        }

        System.out.println("Please enter the truck max Weight:");
        int maxWeight;
        while(true)
        {
            try {
                maxWeight = scanner.nextInt();
                if (maxWeight >= 0 && maxWeight <= 60 && maxWeight >= netWeight)
                    break;
                else
                    System.out.println("Sorry transfer manager, but your input is illegal. please try again");
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                scanner.next();
            }
        }

        System.out.println("Please choose from the following options the truck cooling capacity:");
        System.out.println("1. Regular");
        System.out.println("2. Cold");
        System.out.println("3. Frozen");
        int indexChosen;
        while(true)
        {
            try {
                indexChosen = scanner.nextInt();
                if (indexChosen >= 1 && indexChosen <= 3)
                    break;
                else
                    System.out.println("Sorry transfer manager, but your input is illegal. please try again");
            }
            catch (Exception e)
            {
                System.out.println("Sorry transfer manager, but your input is illegal. please try again");
                scanner.next();
            }
        }
        TempLevel coolingCapacity;

        if (indexChosen == 1)
        {
            coolingCapacity = TempLevel.regular;
        }
        else if (indexChosen == 2)
        {
            coolingCapacity = TempLevel.cold;
        }
        else
        {
            coolingCapacity = TempLevel.frozen;
        }

        //create the truck
        Truck newTruck = new Truck(licenseNumber, model, netWeight, maxWeight, netWeight, coolingCapacity);
        tc.addTruck(newTruck);
    }


    public LocalDateTime calculateArrivingTime(Site sourceSite, List<Site> destinationSites, LocalTime leavingTime, LocalDate leavingDate){
        /*
        Calculates the expected arriving times, based on the distance between the sites in the transfer and the driver's speed.
         */
        int speed = 80;

        //get the distance between source site to the first destination site
        double distanceToNextSite = sourceSite.calculateDistance(destinationSites.get(0));

        //Calculate the time to drive this distance
        double timeOfTransfer =  distanceToNextSite/speed;

        for(int i=0; i<destinationSites.size()-1; i++)
        {
            Site currentSite = destinationSites.get(i);

            //get the distance between current site to the next site
            distanceToNextSite = currentSite.calculateDistance(destinationSites.get(i+1));

            //Calculate the time to drive this distance and add it to current time
            timeOfTransfer = timeOfTransfer + distanceToNextSite/speed;
        }


        // Calculate the number of hours and remaining minutes of the transfer
        long minutes = (long) (timeOfTransfer * 60);
        int hoursOfTransfer = (int) minutes / 60;
        int minutesOdTransfer = (int) minutes % 60;


        LocalDateTime transferLocalDateTimeLeaving = LocalDateTime.of(leavingDate, leavingTime);
        LocalDateTime arrivingTime = transferLocalDateTimeLeaving.plusHours(hoursOfTransfer).plusMinutes(minutesOdTransfer);

        return arrivingTime;
    }

    //the storeKeeper should also be  able to watch the planned transfers
    public void watchPlannedTransfers()
    {
        System.out.println("Here are the next planned transfers: ");

        int numOfPlannedTransfer = 0;
        Map<Integer,Transfer> allTransfers = transfersDAO.getAllTransfers();
        for(Integer trandferId: allTransfers.keySet()){
            Transfer currentTransfer = allTransfers.get(trandferId);
            if(currentTransfer.getTransferStatus().equals("NOT START") || currentTransfer.getTransferStatus().equals("IN PROGRESS")){
                numOfPlannedTransfer++;
                System.out.println("---------------------------------------------------------------");
                System.out.println("TRANSFER ID: " + currentTransfer.getTransferId());
                System.out.println("Source site: " + currentTransfer.getTransferId());
                System.out.println("Last destination: " + currentTransfer.getListOfDestinations().get(currentTransfer.getListOfDestinations().size()-1).getSiteName());
                System.out.println("Leaving date: " + currentTransfer.getLeavingDate());
                System.out.println("Leaving time: " + currentTransfer.getLeavingTime());
                System.out.println("Arriving date: " + currentTransfer.getArrivingDate());
                System.out.println("Arriving time: " + currentTransfer.get_arrivingTime());
                System.out.println("");
            }
        }
        if (numOfPlannedTransfer == 0)
            System.out.println("There are no planned transfers!");
        else
        {
            System.out.println("That's it! Those are all the next planned transfers! \n" +
                    "If you would like to get some more details for any transfer, you can easily download the transfer's document :)");
        }
    }

    public boolean checkIfStoreKeeperIsThere(LocalDate arrivingDate, LocalTime arrivingTime, int orderDestinationSiteId) throws SQLException {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = arrivingDate.get(weekFields.weekOfWeekBasedYear());

        //get the day number in the week
        DayOfWeek dayOfWeek = arrivingDate.getDayOfWeek();
        int dayOfWeekNum = dayOfWeek.getValue();

        WindowTypeCreater wtc = new WindowTypeCreater();

        //check whether the transfer leaves in dayshift or nightshift
        String shift;
        if (arrivingTime.isAfter(LocalTime.NOON))
            shift = "night";
        else
            shift = "day";

        WindowType wt = wtc.getwidowtype(dayOfWeekNum, shift);

        List<WindowType> stokeWindowTypes = weeklyShiftManager.doIHaveStokeForTheShipment(weekNumber, arrivingDate.getYear(), orderDestinationSiteId);

        return stokeWindowTypes.contains(wt);
    }

    public void viewTransferDocument() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Transfer> allTransfers =  TransferDAO.getInstance().getAllTransfers();
        if (allTransfers.size() == 0)
        {
            System.out.println("Unfortunatly, there are no transfers to watch. You'll be taken to the main menu.");
            System.out.println("------------------------------------------------------------");
        }
        else
        {
            System.out.println("There are " + allTransfers.size() + " transfers documents in the system. here are basic details on some of them: ");
            for (int i = 0; i < 5 && i < allTransfers.size(); i++)
            {
                System.out.println((i+1) + ". Transfer ID: " + allTransfers.get(i).getTransferId() + ", Source Site: " + allTransfers.get(i).getSource().getSiteName() + ", Final Destination: " + allTransfers.get(i).getListOfDestinations().get(allTransfers.get(i).getDestinations().size() - 1).getSiteName());
            }
            System.out.println("Please enter the transfer id of the transfer document you would like to download: ");
            int transferId;
            while(true) {
                try
                {
                    transferId = scanner.nextInt();
                    if (transfersDAO.get(transferId) == null ) {
                        System.out.println("Sorry transfer manager, but your input is illegal. please enter a valid transfer Id");
                    }
                    else
                    {
                        break;
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Sorry transfer manager, but your input is illegal. please enter a valid transfer Id");
                    scanner.next();
                }
            }

            Transfer chosenTransfer = transfersDAO.get(transferId);
            chosenTransfer.createDocument();
            System.out.println("A document with transfer details has been downloaded. You'll be taken to the main menu.");
        }
    }

    public void createMockOrder() throws SQLException {
        //get all sites
        Site Yarkan = SiteDAO.getInstance().get(1);
        Site Tnuva = SiteDAO.getInstance().get(2);
        Site Osem = SiteDAO.getInstance().get(3);
        Site SuperLiAshkelon = SiteDAO.getInstance().get(4);
        Site SuperLiMetula = SiteDAO.getInstance().get(5);

        //get all items
        Item_mock Bamba = Item_mockDAO.getInstance().get("45325");
        Item_mock Yolo = Item_mockDAO.getInstance().get("hr4565");
        Item_mock Potato = Item_mockDAO.getInstance().get("5524f2");
        Item_mock Tomato = Item_mockDAO.getInstance().get("543523f");
        Item_mock FrozenPizza = Item_mockDAO.getInstance().get("5838sk");
        Item_mock Doritos = Item_mockDAO.getInstance().get("jl829kl");
        Item_mock ChocolateIceCream = Item_mockDAO.getInstance().get("84390j");
        Item_mock CreamCheese = Item_mockDAO.getInstance().get("23719g");

        //create order items with quantity
        Map<Item_mock, Integer> OsemOrder1Items = new HashMap<>();
        OsemOrder1Items.put(Bamba, 300);
        OsemOrder1Items.put(Doritos, 500);
        OsemOrder1Items.put(ChocolateIceCream, 200);

        Map<Item_mock, Integer> TnuvaOrder1Items = new HashMap<>();
        TnuvaOrder1Items.put(Yolo, 500);
        TnuvaOrder1Items.put(FrozenPizza, 100);
        TnuvaOrder1Items.put(CreamCheese, 400);

        Map<Item_mock, Integer> YarkanOrder1Items = new HashMap<>();
        YarkanOrder1Items.put(Potato, 450);
        YarkanOrder1Items.put(Tomato, 600);

        //create order
        Map<Site, Map<Item_mock, Integer>> order1 = new HashMap<>();
        order1.put(Yarkan, YarkanOrder1Items);
        order1.put(Tnuva, TnuvaOrder1Items);
        order1.put(Osem, OsemOrder1Items);

        //create order items with quantity
        Map<Item_mock, Integer> OsemOrder2Items = new HashMap<>();
        OsemOrder2Items.put(Bamba, 700);
        OsemOrder2Items.put(ChocolateIceCream, 200);

        Map<Item_mock, Integer> TnuvaOrder2Items = new HashMap<>();
        TnuvaOrder2Items.put(Yolo, 500);
        TnuvaOrder2Items.put(FrozenPizza, 270);

        Map<Item_mock, Integer> YarkanOrder2Items = new HashMap<>();
        YarkanOrder2Items.put(Potato, 450);
        YarkanOrder2Items.put(Tomato, 900);

        //create order
        Map<Site, Map<Item_mock, Integer>> order2 = new HashMap<>();
        order2.put(Yarkan, YarkanOrder1Items);
        order2.put(Tnuva, TnuvaOrder1Items);
        order2.put(Osem, OsemOrder1Items);

        //add orders to the orders queue
        newOrderReceived(order1, SuperLiAshkelon.getSiteId());
        newOrderReceived(order2, SuperLiMetula.getSiteId());

    }
}
