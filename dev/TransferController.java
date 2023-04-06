import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TransferController {
    private Map<Integer, Transfer> _transfers;
    private int _documentsCounter;
    private TruckController tc;
    private DriverController dc;
    private SiteController sc;

    public TransferController(TruckController tc, DriverController dc, SiteController sc)
    {
        _transfers = new HashMap<>();
        _documentsCounter = 0;
        this.tc = tc;
        this.dc = dc;
        this.sc = sc;
    }

    public void createNewTransfer(Map<Site, Map<Item_mock, Integer>> orderItems, Integer orderDestinationSiteId)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello manager, We have a new orders ");
        System.out.println("Please enter the next details for the new transfer: ");

        LocalDate transferDate = LocalDate.now();

        LocalTime leavingTime = LocalTime.now();

        //choose driver for transfer
        Driver chosenDriver = findDriver(orderItems);

        //choose truck by the chosen driver
        Truck chosenTruck = findTruckByDriver(chosenDriver, orderItems);

        System.out.println("Please choose source site from the next options (enter number): ");
        Site[] sites = orderItems.keySet().toArray(new Site[0]);
        for(int i=0; i<sites.length; i++){
            System.out.println(i+1 + ". " + sites[i].getSiteName());
        }
        int sourceSiteNum = scanner.nextInt();
        Site sourceSite = sites[sourceSiteNum-1];


        List<Site> tempList = Arrays.asList(sites);

        List<Site> destinationSites = new ArrayList<>(tempList);

        destinationSites.remove(sourceSite);

        destinationSites.add(sc.getSiteById(orderDestinationSiteId));

        Map<Site, Integer> weights = new HashMap<>();
        for(int i=0; i < sites.length; i++)
        {
            weights.put(sites[i], 0);
        }

        Transfer newTransfer = new Transfer(transferDate, leavingTime, chosenTruck.getLicenseNumber(), chosenDriver.getDriverName(), sourceSite, destinationSites, orderItems, _documentsCounter);
        newTransfer.createDocument();
        _transfers.put(_documentsCounter, newTransfer);
        _documentsCounter++;

        System.out.println("Thanks manager! The transfer is ready to go!!");
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

            transferTruck.setTruckUnavailable();

            System.out.println("The transfer starts now");
            System.out.println("The truck chosen to the transfer is: ");
            System.out.println("License Number: " + transferTruck.getLicenseNumber());
            System.out.println("Truck Model: " + transferTruck.getTruckModel());
            System.out.println("Truck Max Weight: " + transferTruck.getMaxWeight());
            System.out.println("Truck Cooling Capacity: " + transferTruck.getTempCapacity());
            System.out.println("------------------------------------------------------------");


            System.out.print("The truck's driver start his drive from "+ newTransfer.getSource().getSiteName() + ", ");
            System.out.println("And he is picking up the following items: ");

            Map<Site, Map<Item_mock, Integer>> transferOrderItems = newTransfer.getOrderItems();
            Map<Item_mock, Integer> sourceItems = transferOrderItems.get(newTransfer.getSource());
            for (Item_mock item : sourceItems.keySet()) {
                System.out.println("Item name " + item.getItemName() + ", Quantity: " + sourceItems.get(item));
            }

            System.out.println("Please enter the weight of the truck: ");
            truckWeight = scanner.nextInt();
            transferTruck.updateWeight(truckWeight);
            newTransfer.documentUpdateTruckWeight(truckWeight, newTransfer.getSource());

            while (!transferTruck.checkWeightCapacity() && !transferRearranged)
            {
                transferRearranged = true;
                rearrangeTransfer(newTransfer);
                System.out.println("The transfer will restart from the source all over again, after the rearrangement");
            }

            if (transferRearranged)
                continue;

            List<Site> transferDest = newTransfer.getDestinations();
            for (int i=0; i<transferDest.size() - 1 && !transferRearranged; i++)
            {
                System.out.print("The truck's driver just arrived to: "+ transferDest.get(i).getSiteName() + ", ");
                System.out.println("And he is picking up the following items: ");

                Map<Item_mock, Integer> destItems = transferOrderItems.get(transferDest.get(i));
                for (Item_mock item : destItems.keySet()) {
                    System.out.println("Item name " + item.getItemName() + ", Quantity: " + destItems.get(item));
                }

                System.out.println("Please enter the weight of the truck: ");
                truckWeight = scanner.nextInt();
                transferTruck.updateWeight(truckWeight);
                newTransfer.documentUpdateTruckWeight(truckWeight, transferDest.get(i));


                while (!transferTruck.checkWeightCapacity() && !transferRearranged)
                {
                    transferRearranged = true;
                    rearrangeTransfer(newTransfer);
                    System.out.println("The transfer rearranged. It will begin again shortly!");
                }
            }

            if (transferRearranged)
                continue;
            else
            {
                System.out.println("The truck arrived to it's final destination: " + transferDest.get(transferDest.size() - 1).getSiteName());
                System.out.println("It will unload all the items here.");
                transferTruck.resetTruckWeight();
                break;
            }
        }
    }

    public void rearrangeTransfer(Transfer transfer){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Unfortunately, the truck is in overweight so the transfer need to be rearranged.");
        System.out.println("Please choose one of the following options: ");
        System.out.println("1. Change the transfer destinations");
        System.out.println("2. Change the transfer truck");
        System.out.println("3. Drop transfer Items");
        int selectedOption = scanner.nextInt();

        if (selectedOption == 1)
        {
            System.out.println("These are the transfer destinations. Please choose one destination to remove: ");
            for (int i = 0; i < transfer.getDestinations().size() - 1; i++)
            {
                System.out.println((i + 1) + "." + transfer.getDestinations().get(i).getSiteName());
            }
            int destToRemove = scanner.nextInt();

            transfer.removeTransferDestination(transfer.getDestinations().get(destToRemove - 1));

        }
        else if (selectedOption == 2)
        {
            System.out.println("These are the available trucks. Please choose one of the trucks, by type its license number: ");
            for (Integer LicenseNum : tc.getAllAvailableTrucks().keySet())
            {
                System.out.println("License number: " + LicenseNum + ", Truck Model: " + tc._trucks.get(LicenseNum).getTruckModel() + ", Temperature Capacity: " + tc._trucks.get(LicenseNum).getTempCapacity() + ", Weight Capacity: " + tc._trucks.get(LicenseNum).getTruckWeightType());
            }

            int chosenTruck = scanner.nextInt();

            transfer.updateTransferTruck(chosenTruck);
        }
        else if (selectedOption == 3)
        {
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
                int chosenIndexDest = scanner.nextInt();
                destToReduceItems = transfer.getDestinations().get(chosenIndexDest - 1);

                //add the destination to the list of destinations we remove
                allDestToReduceItems.add(destToReduceItems);

                //remove the destination from all destinations list
                allDestinations.remove(chosenIndexDest - 1);

                //ask if the manager would like to remove products from another destination
                System.out.println("Would you like to remove products from another destination? press 'y' for yes and 'n' for no.");
                System.out.println("Note that in the next step, you will choose the quantity of the items to drop from each destination you selected.");
                char yesOrNoInput = scanner.next().charAt(0);

                if (yesOrNoInput == 'n')
                    removeMoreDestinations = false;
            }

            //now for each destination in the allDestToReduceItems list I will ask which items to reduce
            for (Site destToReduceFrom: allDestToReduceItems) {
                Map<Item_mock, Integer> itemToChosenDest = transfer.getOrderItems().get(destToReduceFrom);
                Item_mock[] items = itemToChosenDest.keySet().toArray(new Item_mock[0]);
                boolean removeMoreItems = true;

                while(removeMoreItems && items.length > 0)
                {
                    System.out.println("Please choose an item you would like to reduce his quantity from the site " + destToReduceFrom.getSiteName());
                    for (int i = 0; i < items.length; i++)
                    {
                        System.out.println((i + 1) + ". " + items[i].getItemName());
                    }

                    //
                    int chosenIndexItem = scanner.nextInt();
                    Item_mock itemToReduceQuantity = items[chosenIndexItem - 1];

                    System.out.println("And now, please choose how much " + itemToReduceQuantity.getItemName() + " would you like to reduce: ");
                    System.out.println("Current quantity: " + itemToChosenDest.get(itemToReduceQuantity));
                    int chooseQuantity = scanner.nextInt();

                    //items to reduce and the quantity to reduce
                    Map<Item_mock, Integer> howMuchToReduce = new HashMap<>();
                    howMuchToReduce.put(itemToReduceQuantity, chooseQuantity);

                    //add to the reduction map
                    orderItemsToDelete.put(destToReduceFrom, howMuchToReduce);

                    //check if he wants to reduce more items from this site
                    System.out.println("Would you like to remove more products from this destination? press 'y' for yes and 'n' for no");
                    char yesOrNoInput = scanner.next().charAt(0);

                    if (yesOrNoInput == 'n')
                        removeMoreItems = false;
                }
            }

            //now remove all the items you gained
            transfer.removeTransferItems(orderItemsToDelete);

        }
    }

    public Driver findDriver(Map<Site, Map<Item_mock, Integer>> orderItems){
        Driver chosenDriver;

        List<Driver> availableDrivers = dc.getAvailableDrivers();

        Collections.sort(availableDrivers, new Comparator<Driver>() {
            @Override
            public int compare(Driver d1, Driver d2) {
                return d1.getDriverLicense().compareTo(d2.getDriverLicense());
            }
        });

        TempLevel currMinTemp = lowestTempItem(orderItems);

        chosenDriver = null;

        for (int i = 0; i < availableDrivers.size(); i++)
        {
            if (availableDrivers.get(i).checkLicenseWithItemTemp(currMinTemp))
            {
                chosenDriver = availableDrivers.get(i);
                break;
            }
        }

        if (chosenDriver != null)
            return chosenDriver;
        else
            throw new NoSuchElementException("There is no available driver for this transfer");
    }

    public Truck findTruckByDriver(Driver chosenDriver, Map<Site, Map<Item_mock, Integer>> orderItems) {
        Map<Integer, Truck> availableTrucks;

        if (chosenDriver.getDriverLicense().getLicenseWeightCapacity() == weightType.lightWeight)
        {
            availableTrucks = tc.getAvailableTrucksOfLightWeight();
        }
        else if (chosenDriver.getDriverLicense().getLicenseWeightCapacity() == weightType.mediumWeight)
        {
            availableTrucks = tc.getAvailableTrucksOfMiddleWeight();
            Map<Integer, Truck> availableTrucksLightWeight = tc.getAvailableTrucksOfLightWeight();
            availableTrucksLightWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
        }
        else
        {
            availableTrucks = tc.getAvailableTrucksOfHeavyWeight();
            Map<Integer, Truck> availableTrucksMiddleWeight = tc.getAvailableTrucksOfMiddleWeight();
            Map<Integer, Truck> availableTrucksLightWeight = tc.getAvailableTrucksOfLightWeight();
            availableTrucksMiddleWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
            availableTrucksLightWeight.forEach((k, v) -> availableTrucks.putIfAbsent(k, v));
        }

        Truck chosenTruck = null;

        TempLevel currMinTemp = lowestTempItem(orderItems);

        for (Integer truckId: availableTrucks.keySet()) {
            if (availableTrucks.get(truckId).getTempCapacity().compareTo(chosenDriver.getDriverLicense().getLicenseTempCapacity()) <= 0 && availableTrucks.get(truckId).getTempCapacity().compareTo(currMinTemp) >= 0)
            {
                chosenTruck = availableTrucks.get(truckId);
                break;
            }
        }

        if (chosenTruck != null)
            return chosenTruck;
        else
            throw new NoSuchElementException("There is no available truck for this transfer");

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

}
