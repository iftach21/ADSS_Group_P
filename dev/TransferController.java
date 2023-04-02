import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void startTransfer(Map<Supplier, Map<String, Integer>> orderItems)
    {
        tc.get
    }

    public void rearangeTransfer(Transfer transfer){

    }

    public void findDriverAndTruck(){
        List<Driver> availableDrivers = dc.getAvailableDrivers();
        if(availableDrivers.get(0).)
    }
}
