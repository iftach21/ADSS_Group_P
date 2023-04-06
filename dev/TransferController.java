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
        TempLevel currMinTemp = lowestTempItem(orderItems);
        Driver chosenDriver = dc.findDriver(currMinTemp);

        //choose truck by the chosen driver
        currMinTemp = lowestTempItem(orderItems);
        Truck chosenTruck = tc.findTruckByDriver(chosenDriver, currMinTemp);

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
                System.out.println("Item name: " + item.getItemName() + ", Quantity: " + sourceItems.get(item));
            }

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
                System.out.println("The transfer will restart from the source all over again, after the rearrangement");
            }

            if (transferRearranged) {
                continue;
            }
            List<Site> transferDest = newTransfer.getDestinations();
            for (int i=0; i<transferDest.size() - 1 && !transferRearranged; i++)
            {
                System.out.print("The truck's driver just arrived to: "+ transferDest.get(i).getSiteName() + ", ");
                System.out.println("And he is picking up the following items: ");

                Map<Item_mock, Integer> destItems = transferOrderItems.get(transferDest.get(i));
                for (Item_mock item : destItems.keySet()) {
                    System.out.println("Item name: " + item.getItemName() + ", Quantity: " + destItems.get(item));
                }

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
                newTransfer.documentUpdateTruckWeight(truckWeight, transferDest.get(i));


                while (!transferTruck.checkWeightCapacity() && !transferRearranged)
                {
                    transferRearranged = true;
                    rearrangeTransfer(newTransfer);
                    System.out.println("The transfer rearranged. It will begin again shortly!");
                }
            }

            if (transferRearranged) {
                continue;
            }
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
        else if (selectedOption == 2)
        {
            System.out.println("These are the available trucks. Please choose one of the trucks, by type its license number: ");
            for (Integer LicenseNum : tc.getAllAvailableTrucks().keySet())
            {
                System.out.println("License number: " + LicenseNum + ", Truck Model: " + tc._trucks.get(LicenseNum).getTruckModel() + ", Temperature Capacity: " + tc._trucks.get(LicenseNum).getTempCapacity() + ", Weight Capacity: " + tc._trucks.get(LicenseNum).getTruckWeightType());
            }

            int chosenTruck;

            while(true)
            {
                try {
                    chosenTruck = scanner.nextInt();
                    if (tc.getAllAvailableTrucks().containsKey(chosenTruck))
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
}
