package Domain.Transfer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

import Domain.Employee.Driver;
import Domain.Employee.DriverController;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowTypeCreater;


public class TransferController {
    private Map<Integer, Transfer> _transfers;
    private int _documentsCounter;
    private TruckController tc;
    private DriverController dc;
    private SiteController sc;
    private Queue<Map<Site, Map<Item_mock, Integer>>> _ordersQueue;
    private Queue<Integer> _orderDestinationSiteIdQueue;


    public TransferController(TruckController tc, DriverController dc, SiteController sc)
    {
        _transfers = new HashMap<>();
        _documentsCounter = 0;
        this.tc = tc;
        this.dc = dc;
        this.sc = sc;
        this._ordersQueue = new LinkedList<>();
        this._orderDestinationSiteIdQueue = new LinkedList<>();
    }

    public void startTransferSystem()
    {
        boolean systemIsOn = true;
        Scanner scanner = new Scanner(System.in);
        while (systemIsOn)
        {
            System.out.println("------------------------------------------------------------");
            System.out.println("Hello transfer manager, welcome to the transfer system!");
            System.out.println("Please pick one of the following options:");
            System.out.println("1. View previous transfers");
            System.out.println("2. Create transfer for pending orders. you have " + _ordersQueue.size() + " orders waiting to transfer");
            System.out.println("3. View and update current transfers ");
            System.out.println("4. Add a new truck to the system ");
            System.out.println("5. Exit the transfer system");

            int optionSelection;
            while(true) {
                try {
                    optionSelection = scanner.nextInt();
                    if (optionSelection == 1 || optionSelection == 2 || optionSelection == 3 || optionSelection == 4 || optionSelection == 5) {
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
                if (_transfers.size() == 0)
                {
                    System.out.println("Unfortunatly, there are no previous transfers to watch. You'll be taken to the main menu.");
                    System.out.println("------------------------------------------------------------");
                }
                else
                {
                    System.out.println("Please enter the transfer id of the transfer you would like to watch: ");
                    int transferId;
                    while(true) {
                        try {
                            transferId = scanner.nextInt();
                            if (!_transfers.containsKey(transferId)) {
                                System.out.println("Sorry transfer manager, but your input is illegal. please enter a valid transfer Id");
                            }
                            else if (_transfers.containsKey(transferId) && _transfers.get(transferId).getTransferStatus().equals("IN PROGRESS")){
                                System.out.println("Sorry transfer manager, but your selected transfer is in progress. Please choose another transfer");
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

                    Transfer chosenTransfer = _transfers.get(transferId);
                    chosenTransfer.createDocument();
                    System.out.println("A document with transfer details has been downloaded. You'll be taken to the main menu.");
                }
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
                    break;
                }
                else
                {
                    System.out.println("Those are the transfers that are currently takes place:");
                    for(Integer transferId: currentTransfers.keySet())
                    {
                        Transfer transfer = currentTransfers.get(transferId);
                        System.out.println("transfer Id: " + transferId + ", Source site: " + transfer.getSource().getSiteName() + ", Destination site: " + transfer.getDestinations().get(transfer.getDestinations().size()-1));
                    }
                    System.out.println("Please enter the transferId of your chosen transfer");
                    int transferId;
                    while(true) {
                        try {
                            transferId = scanner.nextInt();
                            if (_transfers.containsKey(transferId)) {
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
                    updateCurrentTransferDetails(_transfers.get(transferId));
                    System.out.println("You'll be taken to the main menu.");
                }
            }
            else if(optionSelection == 4)
            {
                addTruckToSystem();
                System.out.println("Truck added successfully. You'll be taken to the main menu.");
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


    public void createNewTransfer(Map<Site, Map<Item_mock, Integer>> orderItems, Integer orderDestinationSiteId)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the next details for the new transfer: ");
        LocalDate leavingDate;
        LocalTime leavingTime;
        while(true)
        {
            //choose date
            leavingDate = chooseDateForTransfer();

            //choose time
            leavingTime = chooseTimeForTransfer();
            if (checkIfDateIsLegal(leavingDate, leavingTime))
            {
                break;
            }
            else
            {
                System.out.println("Sorry transfer manager' but the date you entered is in the past. please enter a date in the future.");
            }
        }

        //get lowest temp item
        TempLevel currMinTemp = lowestTempItem(orderItems);

        //choose driver for transfer
        Driver chosenDriver = chooseDriverForTransfer(leavingDate, leavingTime, currMinTemp, orderItems);
        if (chosenDriver == null)
        {
            System.out.println("Unfortunately, there is no available driver to this transfer.");
            _ordersQueue.add(orderItems);
            _orderDestinationSiteIdQueue.add(orderDestinationSiteId);
            return;
        }

        //choose truck by the chosen driver
        Truck chosenTruck = tc.findTruckByDriver(chosenDriver, currMinTemp, leavingDate);
        if (chosenTruck == null)
        {
            System.out.println("Unfortunately, there is no available truck to this transfer.");
            _ordersQueue.add(orderItems);
            _orderDestinationSiteIdQueue.add(orderDestinationSiteId);
            return;
        }

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

        //calculate arriving date and time
        LocalDateTime arrivingDateTime = calculateArrivingTime(sourceSite, destinationSites, leavingTime, leavingDate);
        LocalDate arrivingDate = arrivingDateTime.toLocalDate();
        LocalTime arrivingTime = arrivingDateTime.toLocalTime();

        Map<Site, Integer> weights = new HashMap<>();
        for(int i=0; i < sites.length; i++)
        {
            weights.put(sites[i], 0);
        }

        Transfer newTransfer = new Transfer(leavingDate, leavingTime, arrivingDate, arrivingTime, chosenTruck.getLicenseNumber(), chosenDriver.getName(), sourceSite, destinationSites, orderItems, _documentsCounter);
        newTransfer.createDocument();
        _transfers.put(_documentsCounter, newTransfer);
        _documentsCounter++;

        System.out.println("Thanks manager! The transfer will be ready in short time. You'll now need to predict the weight in each destination, and rearrange the transfer if needed.");
        startTransfer(newTransfer);
    }

    public void startTransfer(Transfer newTransfer)
    {
        while(true)
        {
            boolean transferRearranged = false;

            int truckWeight;
            Scanner scanner = new Scanner(System.in);
            Truck transferTruck = tc._trucks.get(newTransfer.getTruckLicenseNumber());
            transferTruck.setTruckUnavailable(newTransfer.getLeavingDate(), newTransfer.getArrivingDate());

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
                List<Site> transferDest = newTransfer.getDestinations();
                System.out.println("It will unload all the items at his final destination - " + transferDest.get(transferDest.size() - 1).getSiteName());
                System.out.println("------------------------------------------------------------");
                break;
            }
        }
    }

    public void rearrangeTransfer(Transfer transfer){

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
        }
        else if (selectedOption == 2)
        {
            changeTruckOfTransfer(transfer);
        }
        else if (selectedOption == 3)
        {
            removeItemsOfTransfer(transfer);
        }

        transfer.documentUpdateTruckWeight(null, transfer.getSource());
        for(int i=0; i<transfer.getDestinations().size()-1; i++){
            transfer.documentUpdateTruckWeight(null, transfer.getDestinations().get(i));
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

        for(Integer trandferId: _transfers.keySet()){
            Transfer transfer = _transfers.get(trandferId);
            LocalDateTime transferLocalDateTimeLeaving = LocalDateTime.of(transfer.getDateOfTransfer(), transfer.getLeavingTime());
            LocalDateTime transferLocalDateTimeArriving = LocalDateTime.of(transfer.getArrivingDate(), transfer.get_arrivingTime());
            if(transferLocalDateTimeLeaving.isBefore(LocalDateTime.now()) && transferLocalDateTimeArriving.isAfter(LocalDateTime.now()))
            {
                currentTransfers.put(trandferId, transfer);
            }
        }

        return currentTransfers;
    }

    public boolean updateWeightsForTransfer(Transfer newTransfer, boolean happensRightNow)
    {
        int truckWeight;

        Scanner scanner = new Scanner(System.in);
        Truck transferTruck = tc._trucks.get(newTransfer.getTruckLicenseNumber());
        transferTruck.setTruckUnavailable(newTransfer.getLeavingDate(), newTransfer.getArrivingDate());

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
        List<Site> transferDest = newTransfer.getDestinations();
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
            System.out.println((i + 1) + "." + transfer.getDestinations().get(i).getSiteName());
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

        transfer.documentRemoveDestination(transfer.getDestinations().get(destToRemove - 1));
        transfer.removeTransferDestination(transfer.getDestinations().get(destToRemove - 1));
    }

    public void changeTruckOfTransfer(Transfer transfer)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("These are the available trucks. Please choose one of the trucks, by type its license number: ");
        for (Integer LicenseNum : tc.getAllAvailableTrucks(transfer.getLeavingDate()).keySet())
        {
            System.out.println("License number: " + LicenseNum + ", Domain.Truck Model: " + tc._trucks.get(LicenseNum).getTruckModel() + ", Temperature Capacity: " + tc._trucks.get(LicenseNum).getTempCapacity() + ", Weight Capacity: " + tc._trucks.get(LicenseNum).getTruckWeightType());
        }

        int chosenTruck;

        while(true)
        {
            try {
                chosenTruck = scanner.nextInt();
                if (tc.getAllAvailableTrucks(transfer.getLeavingDate()).containsKey(chosenTruck))
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

        transfer.updateTransferTruck(chosenTruck);
        transfer.documentUpdateTruckNumber();
    }

    public void removeItemsOfTransfer(Transfer transfer)
    {
        Scanner scanner = new Scanner(System.in);
        Map<Site, Map<Item_mock, Integer>> orderItemsToDelete = new HashMap<>();
        boolean removeMoreDestinations = true;

        List<Site> allDestinations = new ArrayList<>(transfer.getDestinations());
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
        System.out.println("Please enter the leaving date of the transfer, in this format - dd/mm/yyy: ");
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
                scanner.next();
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
                scanner.next();
            }
        }
        return leavingTime;
    }

    public boolean checkIfDateIsLegal(LocalDate leavingDate, LocalTime leavingTime)
    {
        LocalDateTime transferLocalDateTimeLeaving = LocalDateTime.of(leavingDate, leavingTime);
        return transferLocalDateTimeLeaving.isAfter(LocalDateTime.now());

    }

    public Driver chooseDriverForTransfer(LocalDate leavingDate, LocalTime leavingTime, TempLevel currMinTemp, Map<Site, Map<Item_mock, Integer>> orderItems)
    {
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
            System.out.println((i+1) + Drivers.get(i).getId() + ". ID: " + ", Name:" + Drivers.get(i).getName() + ", License Weight Capacity:" + Drivers.get(i).getDriverLicense().getLicenseWeightCapacity() + ", License Temp Capacity:" + Drivers.get(i).getDriverLicense().getLicenseTempCapacity());
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

    public void updateCurrentTransferDetails(Transfer transferToUpdate)
    {
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
        }
    }

    public void addTruckToSystem()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To add a new truck to the transfer system, you'll need to enter the following details:");

        System.out.println("Please enter the truck License Number:");
        int licenseNumber;
        while(true)
        {
            try {
                licenseNumber = scanner.nextInt();
                if (licenseNumber >= 0)
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
        String model = scanner.next();

        System.out.println("Please enter the truck net Weight:");
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

        System.out.println("Please choose from the following options the truck cooling capacity:");
        System.out.println("1. Regular");
        System.out.println("2. Cold");
        System.out.println("3. Frozen");
        int indexChosen;
        while(true)
        {
            try {
                indexChosen = scanner.nextInt();
                if (netWeight >= 1 && netWeight <= 3)
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
        Truck newTruck = new Truck(licenseNumber, model, netWeight, maxWeight, netWeight, coolingCapacity, null, null);
        tc._trucks.put(licenseNumber, newTruck);
    }

    //should be called also when rearrange transfer
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
        for(int i=0; i<_transfers.size(); i++)
        {
            Transfer currentTransfer = _transfers.get(i);
            if(currentTransfer.getTransferStatus().equals("NOT START") || currentTransfer.getTransferStatus().equals("IN PROGRESS")){
                numOfPlannedTransfer++;
                System.out.println("---------------------------------------------------------------");
                System.out.println("TRANSFER ID: " + currentTransfer.getTransferId());
                System.out.println("Source site: " + currentTransfer.getTransferId());
                System.out.println("Last destination: " + currentTransfer.getDestinations().get(currentTransfer.getDestinations().size()-1));
                System.out.println("Leaving date: " + currentTransfer.getLeavingDate());
                System.out.println("Leaving time: " + currentTransfer.getLeavingTime());
                System.out.println("Arriving date: " + currentTransfer.getArrivingDate());
                System.out.println("Arriving time: " + currentTransfer.get_arrivingTime());
            }
        }
        if (numOfPlannedTransfer == 0)
            System.out.println("There are no planned transfers!");
        else
        {
            System.out.println("Those are all the next planned transfers! \n" +
                    "If you would like to get some more details for some transfer, you can easily download the transfer's document :)");
        }
    }
}
