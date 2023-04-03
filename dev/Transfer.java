import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Transfer {
    private LocalDate _dateOfTransfer;
    private LocalTime _leavingTime;
    private int _truck_LicenseNumber;
    private String _driverName;
    private Site _source;
    private List<Site> _destinations;
    private Map<Site, Map<Item_mock, Integer>> _orderItems;
    private Map<Site, Integer> _weights;
    private boolean _isAlreadyLeft;


    public Transfer(LocalDate dateOfTransfer, LocalTime leavingTime, int truck_LicenseNumber, String driverName, Site source, List<Site> destinations, Map<Site, Map<Item_mock, Integer>> orderItems, Map<Site, Integer> weights)
    {
        this._dateOfTransfer = dateOfTransfer;
        this._leavingTime = leavingTime;
        this._truck_LicenseNumber = truck_LicenseNumber;
        this._driverName = driverName;
        this._source = source;
        this._destinations = destinations;
        this._orderItems = orderItems;
        this._weights = weights;
        this._isAlreadyLeft = false;
    }

    public void removeTransferItems(Map<Site, Map<Item_mock, Integer>> itemsToDelete)
    {
        for (Site site : itemsToDelete.keySet()) {
            for (Item_mock product : itemsToDelete.get(site).keySet())
            {
                _orderItems.get(site).put(product, _orderItems.get(site).get(product) - itemsToDelete.get(site).get(product));
                if (_orderItems.get(site).get(product) == 0) {
                    _orderItems.get(site).remove(product);
                    _orderItems.remove(site);
                }
            }
        }
    }

    public void addTransferItems(Map<Site, Map<Item_mock, Integer>> itemsToAdd){
        for (Site site : itemsToAdd.keySet()) {
            if(_orderItems.containsKey(site)) {
                for (Item_mock product : itemsToAdd.get(site).keySet()) {
                    if (_orderItems.get(site).containsKey(product)) {
                        Integer x = _orderItems.get(site).get(product);
                        _orderItems.get(site).put(product, x + itemsToAdd.get(site).get(product));
                    } else {
                        _orderItems.get(site).put(product, itemsToAdd.get(site).get(product));
                    }
                }
            }
            else {
                _orderItems.put(site, itemsToAdd.get(site));
            }
        }
    }

    public void updateTransferDestinations(Set<Site> sites)
    {
        for(Site site: sites)
        {
            if (!_destinations.contains(site))
                this._destinations.add(site);
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

    public LocalDate getDateOfTransfer(){
        return this._dateOfTransfer;
    }

    public LocalTime getLeavingTime(){
        return this._leavingTime;
    }

    public List<Site> getDestinations(){
        return this._destinations;
    }

    public Site getSource(){
        return this._source;
    }
}