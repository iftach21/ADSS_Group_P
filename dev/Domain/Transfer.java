package Domain;

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
    private int _transferId;


    public Transfer(LocalDate dateOfTransfer, LocalTime leavingTime, int truck_LicenseNumber, String driverName, Site source, List<Site> destinations, Map<Site, Map<Item_mock, Integer>> orderItems, int transferId)
    {
        this._dateOfTransfer = dateOfTransfer;
        this._leavingTime = leavingTime;
        this._truckLicenseNumber = truck_LicenseNumber;
        this._driverName = driverName;
        this._source = source;
        this._destinations = destinations;
        this._orderItems = orderItems;
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
                    if (_orderItems.get(site).size() == 0)
                    {
                        _orderItems.remove(site);
                        _destinations.remove(site);
                        System.out.println("Please notice that you removed every item from this destination, so the destination has been removed from the transfer!");
                    }
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
        String fileName = "transfer" + _transferId +"_Document.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(documentAddUnderline("TRANSFER DETAILS:") + "\n\n");
            fileWriter.write(String.format(" %20s %20s %20s %20s %20s \r\n\n", "Document ID: " +  _transferId, ", Date: "+ _dateOfTransfer.toString(), ", Track's number: "+ _truckLicenseNumber,  ", Leaving time: " +_leavingTime.toString(), ", Domain.Driver name: "+_driverName));
            fileWriter.write("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
            //fileWriter.write(String.format("%20s %20s %20s %20s %20s \r\n", _transferId, _dateOfTransfer.toString(), _truckLicenseNumber, _leavingTime.toString(), _driverName));
            fileWriter.write(documentAddUnderline(" SOURCE DETAILS: ") + "\n\n");
            fileWriter.write(String.format(" %20s \r\n", "Source name: "+ _source.getSiteName()));
            fileWriter.write(String.format(" %20s %20s %20s %20s \r\n\n", "Address: "+_source.getSiteAddress(), ", Contact name: "+ _source.get_contactName(), ", Phone: " + _source.get_phoneNumber(), ", Domain.Truck weight: " + "None"));
            //fileWriter.write(String.format("%20s %20s %20s %20s\r\n", _source.getSiteAddress(), _source.get_contactName(), _source.get_phoneNumber(), ""));
            fileWriter.write("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
            fileWriter.write(documentAddUnderline("DESTINATION DETAILS:") + "\n\n");
            for(int i=0; i<_destinations.size(); i++)
            {
                fileWriter.write(String.format(" %20s \r\n","Destination name: "+ _destinations.get(i).getSiteName()));
                if(i<_destinations.size()-1) {
                    fileWriter.write(String.format(" %20s %20s %20s %20s \r\n\n", "Address: " + _destinations.get(i).getSiteAddress(), ", Contact name: "+_destinations.get(i).get_contactName(), ", Phone: "+_destinations.get(i).get_phoneNumber(), ", Domain.Truck Weight: "+ "None"));
                    //fileWriter.write(String.format("%20s %20s %20s %20s \r\n", _destinations.get(i).getSiteAddress(), _destinations.get(i).get_contactName(), _destinations.get(i).get_phoneNumber(), ""));
                }
                else {
                    fileWriter.write(String.format(" %20s %20s %20s \r\n\n", "Address: "+_destinations.get(i).getSiteAddress() , ", Contact name: "+_destinations.get(i).get_contactName(), ", Phone: "+_destinations.get(i).get_phoneNumber()));
                    //fileWriter.write(String.format("%20s %20s %20s \r\n", _destinations.get(i).getSiteAddress(), _destinations.get(i).get_contactName(), _destinations.get(i).get_phoneNumber()));
                }
            }
            fileWriter.write("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
            fileWriter.write(documentAddUnderline("TRANSFER ITEMS CONTENT:") + "\n\n");
            for (Site site : _orderItems.keySet()) {
                for (Item_mock product : _orderItems.get(site).keySet())
                {
                    fileWriter.write(String.format(" %20s %20s \r\n", "Item name: "+product.getItemName(), ", Quantity: "+_orderItems.get(site).get(product)));
                    //fileWriter.write(String.format("%20s %20s \r\n", product.getItemName(), _orderItems.get(site).get(product)));
                }
            }

            fileWriter.flush();
            fileWriter.close();
        }
        catch(IOException e) {
            System.out.println("Error creating document " + e.getMessage());
        }
    }

    private String documentAddUnderline(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i)).append("\u0332"); // appending an underline character
        }
        return sb.toString();
    }

    public void documentUpdateTruckWeight(Integer weight, Site site) {
        String fileName = "transfer" + _transferId +"_Document.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(site.getSiteAddress())) {
                    if(weight!=null)
                        lines.set(i, String.format("%20s %20s %20s %20s ", "Address: " + site.getSiteAddress(), ", Contact name: "+site.get_contactName(), ", Phone: "+site.get_phoneNumber(), ", Domain.Truck Weight: "+ weight));
                    else
                        lines.set(i, String.format("%20s %20s %20s %20s ", "Address: " + site.getSiteAddress(), ", Contact name: "+site.get_contactName(), ", Phone: "+site.get_phoneNumber(), ", Domain.Truck Weight: "+ "None"));
                    //lines.set(i, String.format("%20s %20s %20s %20s \r\n", site.getSiteAddress(), site.get_contactName(), site.get_phoneNumber(), weight));
                    break;
                }
            }
            Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error updating truck weight in document" + e.getMessage());
        }
    }

    public void documentRemoveDestination(Site siteToRemove){
        String fileName = "transfer" + _transferId +"_Document.txt";
        int lineToRemove = -1;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(siteToRemove.getSiteAddress())) {
                    lineToRemove = i;
                    break;
                }
            }
            if(lineToRemove != -1) {
                lines.remove(lineToRemove-1);
                lines.remove(lineToRemove-1);
                lines.remove(lineToRemove-1);
                Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);
            }
            else
            {
                System.out.println("Error remove destination from document");
            }
        } catch (IOException e) {
            System.out.println("Error remove destination from document" + e.getMessage());
        }
    }


    public void documentUpdateTruckNumber(){
        String fileName = "transfer" + _transferId +"_Document.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains("Track's number:")) {
                    lines.set(i, String.format(" %20s %20s %20s %20s %20s ", "Document ID: " +  _transferId, ", Date: "+ _dateOfTransfer.toString(), ", Track's number: "+ _truckLicenseNumber,  ", Leaving time: " +_leavingTime.toString(), ", Domain.Driver name: "+_driverName));
                }
            }
            Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.out.println("Error remove destination from document" + e.getMessage());
        }
    }

    public void documentUpdateOrderItems(){
        String fileName = "transfer" + _transferId +"_Document.txt";
        int lineToRemoveFrom = -1;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains("T̲R̲A̲N̲S̲F̲E̲R̲ ̲I̲T̲E̲M̲S̲ ̲C̲O̲N̲T̲E̲N̲T̲:̲")) {
                    lineToRemoveFrom = i+1;
                    break;
                }
            }
            if(lineToRemoveFrom != -1) {
                lines.subList(lineToRemoveFrom, lines.size()).clear();
                for (Site site : _orderItems.keySet()) {
                    for (Item_mock product : _orderItems.get(site).keySet())
                    {
                        lines.add(String.format(" %20s %20s ", "Item name: "+product.getItemName(), ", Quantity: "+_orderItems.get(site).get(product)));
                    }
                }
                Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);

            }
            else
            {
                System.out.println("Error update order items in document");
            }
        } catch (IOException e) {
            System.out.println("Error update order items in document" + e.getMessage());
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

    public String getDriverName(){ return _driverName; }
}