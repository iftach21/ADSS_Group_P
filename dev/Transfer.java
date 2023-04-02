import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Transfer {
    private LocalDate _dateOfTransfer;
    private LocalTime _leavingTime;
    private int _truck_LicenseNumber;
    private String _driverName;
    private Site _source;
    private List<Site> _destinations;
    private Map<String, Map<String, Integer>> _orderItems;

    public Transfer(LocalDate dateOfTransfer, LocalTime leavingTime, int truck_LicenseNumber, String driverName, Site source, List<Site> destinations, Map<String, Map<String, Integer>> orderItems)
    {
        this._dateOfTransfer = dateOfTransfer;
        this._leavingTime = leavingTime;
        this._truck_LicenseNumber = truck_LicenseNumber;
        this._driverName = driverName;
        this._source = source;
        this._destinations = destinations;
        this._orderItems = orderItems;
    }

    public void updateTransferItems(Map<String, Map<String, Integer>> itemsToDelete)
    {
        for (String supplier : itemsToDelete.keySet()) {
            for (String product : itemsToDelete.get(supplier).keySet())
            {
                _orderItems.get(supplier).put(product, _orderItems.get(supplier).get(product) - itemsToDelete.get(supplier).get(product));
                if (_orderItems.get(supplier).get(product) == 0) {
                    _orderItems.get(supplier).remove(product);
                    _orderItems.remove(supplier);
                }
            }
        }
    }

    public void updateTransferTruck(int truck_LicenseNumber)
    {
        _truck_LicenseNumber = truck_LicenseNumber;
    }

    public void updateTransferDestinations(List<Site> destinationsToDelete)
    {
        _destinations.removeAll(destinationsToDelete);
    }

    public void createDocument()
    {

    }
}