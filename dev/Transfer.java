import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Transfer {
    private LocalDate _dateOfTransfer;
    private LocalTime _leavingTime;
    private int _truckLicenseNumber;
    private String _driverName;
    private Site _source;
    private List<Site> _destinations;
    private Map<Site, Map<Item_mock, Integer>> _orderItems;
    private Map<Site, Integer> _weights;
    private boolean _isAlreadyLeft;
    private int _transferId;


    public Transfer(LocalDate dateOfTransfer, LocalTime leavingTime, int truck_LicenseNumber, String driverName, Site source, List<Site> destinations, Map<Site, Map<Item_mock, Integer>> orderItems, Map<Site, Integer> weights, int transferId)
    {
        this._dateOfTransfer = dateOfTransfer;
        this._leavingTime = leavingTime;
        this._truckLicenseNumber = truck_LicenseNumber;
        this._driverName = driverName;
        this._source = source;
        this._destinations = destinations;
        this._orderItems = orderItems;
        this._weights = weights;
        this._isAlreadyLeft = false;
        this._transferId = transferId;
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

    public void addTransferDestinations(Set<Site> sites)
    {
        for(Site site: sites)
        {
            if (!_destinations.contains(site))
                this._destinations.add(site);
        }
    }

    public void addTransferDestinations(Site site)
    {
        if (!_destinations.contains(site))
            this._destinations.add(site);
    }

    public void updateTransferTruck(int truck_LicenseNumber)
    {
        _truckLicenseNumber = truck_LicenseNumber;
    }

    public void removeTransferDestination(Site destinationToDelete)
    {
        _destinations.remove(destinationToDelete);
    }

    public void createDocument()
    {
        System.out.println("Creating transfer document (a text file will be created in current directory)...");
        String fileName = "transfer_" + _transferId +"Document.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("TRANSFER DETAILS: \n");
            fileWriter.write(String.format("%20s %20s \r\n", "Document ID", "Date", "Track's number", "Leaving time", "Driver name"));
            fileWriter.write(String.format("%20s %20s \r\n", _transferId, _dateOfTransfer.toString(), _truckLicenseNumber, _leavingTime.toString(), _driverName));
            fileWriter.write("SOURCE DETAILS: \n");
            fileWriter.write(String.format("%20s %20s \r\n", "Address", "Contact name", "Phone", "Truck weight"));
            fileWriter.write(String.format("%20s %20s \r\n", _source.getSiteAddress(), _source.get_contactName(), _source.get_phoneNumber(), ""));
            fileWriter.write("DESTINATION DETAILS: \n");
            for(int i=0; i<_destinations.size(); i++)
            {
                fileWriter.write("Destination name: "+ _destinations.get(i).getSiteName());
                if(i<_destinations.size()-1) {
                    fileWriter.write(String.format("%20s %20s \r\n", "Address", "Contact name", "Phone", "Truck Weight"));
                    fileWriter.write(String.format("%20s %20s \r\n", _destinations.get(i).getSiteAddress(), _destinations.get(i).get_contactName(), _destinations.get(i).get_phoneNumber(), ""));
                }
                else {
                    fileWriter.write(String.format("%20s %20s \r\n", "Address", "Contact name", "Phone"));
                    fileWriter.write(String.format("%20s %20s \r\n", _destinations.get(i).getSiteAddress(), _destinations.get(i).get_contactName(), _destinations.get(i).get_phoneNumber()));
                }
            }

            fileWriter.write("TRANSFER ITEMS CONTENT:");
            for (Site site : _orderItems.keySet()) {
                for (Item_mock product : _orderItems.get(site).keySet())
                {
                    fileWriter.write(String.format("%20s %20s \r\n", "Item name", "Quantity"));
                    fileWriter.write(String.format("%20s %20s \r\n", product.getItemName(), _orderItems.get(site).get(product)));
                }
            }

            fileWriter.flush();
            fileWriter.close();
        }
        catch(IOException e) {
            System.out.println("Error creating document " + e.getMessage());
        }
    }

    public void updateTruckWeight(int weight, Site site) {
        String fileName = "transfer_" + _transferId +"Document.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(site.getSiteAddress())) {
                    lines.set(i, String.format("%20s %20s %20s %20s \r\n", site.getSiteAddress(), site.get_contactName(), site.get_phoneNumber(), weight));
                }
            }
            Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error updating truck weight " + e.getMessage());
        }
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

    public Map<Site, Map<Item_mock, Integer>> getOrderItems(){
        return _orderItems;
    }

    public Site getSource(){
        return this._source;
    }

    public int getTruckLicenseNumber()
    {
        return _truckLicenseNumber;
    }
}