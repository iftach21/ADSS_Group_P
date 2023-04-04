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

    public TransferController()
    {
        _transfers = new HashMap<>();
        _documentsCounter = 0;
    }

    public void createTransfers(Map<Site, Map<Item_mock, Integer>> orderItems, Integer orderDestinationSiteId){
        System.out.println("Hello manager, We have the a new order. Please choose 1/2:");
        System.out.println("1. Add this order into an existing transfer");
        System.out.println("2. Create a new transfer to this order");
        Scanner scanner = new Scanner(System.in);
        int ans = scanner.nextInt();
        if(ans == 1){
            updateExistingTransfer(orderItems, orderDestinationSiteId);
        }
        if(ans == 2){
            Transfer newTransfer = createNewTransfer(orderItems);
            _transfers.put(_documentsCounter, newTransfer);
            _documentsCounter++;
        }
    }

    public void updateExistingTransfer(Map<Site, Map<Item_mock, Integer>> orderItems, Integer orderDestinationSiteId)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Here are the following planned transfers:");
        for (Integer transferId : _transfers.keySet())
        {
            System.out.println("Transfer Id: " + transferId);
            System.out.println("Date of transfer: " + _transfers.get(transferId).getDateOfTransfer());
            System.out.println("Time of transfer: " + _transfers.get(transferId).getLeavingTime());
            System.out.println("Source of transfer: " + _transfers.get(transferId).getSource());
            System.out.println("Destinations of transfer: " + Arrays.toString(_transfers.get(transferId).getDestinations().toArray()));
        }

        System.out.println("Please enter the id of your desired transfer: ");
        int chosenId = scanner.nextInt();
        Transfer chosenTransfer = _transfers.get(chosenId);
        chosenTransfer.addTransferDestinations(orderItems.keySet());
        //add branch destination
        chosenTransfer.addTransferDestinations(sc.getSiteById(orderDestinationSiteId));
        chosenTransfer.addTransferItems(orderItems);
    }

    public Transfer createNewTransfer(Map<Site, Map<Item_mock, Integer>> orderItems)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the next details for the new transfer: ");

        System.out.println("Please enter leaving date in \"dd/MM/yyyy\" format: ");
        String transferDateStr = scanner.next();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate transferDate = LocalDate.parse(transferDateStr, dateFormat);

        System.out.println("Please enter leaving time in \"HH:mm\" format");
        String leavingTimeStr = scanner.next();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime leavingTime = LocalTime.parse(leavingTimeStr, dtf);

        //choose driver for transfer
        Driver chosenDriver = findDriver(orderItems);

        //choose truck by the chosen driver
        Truck chosenTruck = findTruckByDriver(chosenDriver);

        System.out.println("Please choose source site from the next options (enter number): ");
        Site[] sites = orderItems.keySet().toArray(new Site[0]);
        for(int i=0; i<sites.length; i++){
            System.out.println(i+1 + ". " + sites[i].getSiteName());
        }
        int sourceSiteNum = scanner.nextInt();
        Site sourceSite = sites[sourceSiteNum-1];


        List<Site> destinationSites = Arrays.asList(sites);
        destinationSites.remove(sourceSite);

        Map<Site, Integer> weights = new HashMap<>();
        for(int i=0; i < sites.length; i++)
        {
            weights.put(sites[i], 0);
        }

        Transfer newTransfer = new Transfer(transferDate, leavingTime, chosenTruck.getLicenseNumber(), chosenDriver.getDriverName(), sourceSite, destinationSites, orderItems, weights);
        return newTransfer;
    }

    public void startTransfer()
    {
        //tc.get
    }

    public void rearangeTransfer(Transfer transfer){

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

        TempLevel currMinTemp = TempLevel.regular;
        for (Site site : orderItems.keySet()) {
            for (Item_mock product : orderItems.get(site).keySet())
            {
                if (product.getItemTemp().compareTo(currMinTemp) > 0)
                    currMinTemp = product.getItemTemp();
            }
        }

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

    public Truck findTruckByDriver(Driver chosenDriver) {
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

        for (Integer truckId: availableTrucks.keySet()) {
            if (availableTrucks.get(truckId).getTempCapacity().compareTo(chosenDriver.getDriverLicense().getLicenseTempCapacity()) <= 0)
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


}
