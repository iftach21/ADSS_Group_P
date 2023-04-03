import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;

public class TransferController {
    private Map<Integer, Transfer> _transfers;
    private int _documentsCounter;
    private TruckController tc;
    private DriverController dc;

    public TransferController()
    {
        _transfers = new HashMap<>();
        _documentsCounter = 0;
    }

    public void createTransfers(Map<Site, Map<Item_mock, Integer>> orderItems, Integer orderDestinationBranchId){
        System.out.println("Hello manager, We have the a new order. Please choose 1/2:");
        System.out.println("1. Add this order into an existing transfer");
        System.out.println("2. Create a new transfer to this order");
        Scanner scanner = new Scanner(System.in);
        int ans = scanner.nextInt();
        if(ans == 1){
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
            chosenTransfer.updateTransferDestinations(orderItems.keySet());
            //need to add branch destination
            //chosenTransfer.updateTransferDestinations(orderDestinationBranchId);
            chosenTransfer.addTransferItems(orderItems);
        }
        if(ans == 2){
            System.out.println("Please enter the next details for the new transfer: ");

            System.out.println("Please enter leaving date in \"dd/MM/yyyy\" format: ");
            String transferDateStr = scanner.next();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate transferDate = LocalDate.parse(transferDateStr, dateFormat);

            System.out.println("Please enter leaving time in \"HH:mm\" format");
            String leavingTimeStr = scanner.next();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime leavingTime = LocalTime.parse(leavingTimeStr, dtf);

            //call findDriverAndTruck

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

            Transfer transfer = new Transfer(transferDate, leavingTime, 0, "", sourceSite, destinationSites, orderItems, weights);
        }

    }
    public void startTransfer()
    {
        //tc.get
    }

    public void rearangeTransfer(Transfer transfer){

    }

    public void findDriverAndTruck(Map<Site, Map<Item_mock, Integer>> orderItems){
        List<Driver> availableDrivers = dc.getAvailableDrivers();

        availableDrivers.sort(Comparator.comparing(Driver::getLicenseType));
        for (Site site : orderItems.keySet()) {
            for (Item_mock product : orderItems.get(site).keySet())
            {

            }
        }

        Driver ChosenDriver;

    }
}
