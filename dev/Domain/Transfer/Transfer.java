package Domain.Transfer;

import DataAccesObjects.Transfer.TransferDestinationsDAO;
import DataAccesObjects.Transfer.TransferItemsDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Transfer {
    private int _transferId;
    private LocalDate _dateOfTransfer;
    private LocalTime _leavingTime;
    private LocalDate _arrivingDate;
    private LocalTime _arrivingTime;
    private int _truckLicenseNumber;
    private String _driverName;
    private int _driverId;
    private Site _source;
    private int _weightAtSource;
    private final TransferDestinationsDAO _transferDestinationsDAO;
    private final TransferItemsDAO _transferItemsDAO;
    private List<Site> _destinations;
    //private Map<Site, Map<Item_mock, Integer>> _orderItems;

    public Transfer(LocalDate dateOfTransfer, LocalTime leavingTime, LocalDate arrivingDate, LocalTime arrivingTime, int truck_LicenseNumber, String driverName, Site source, Map<Site, Integer> destinations, Map<Site, Map<Item_mock, Integer>> orderItems, int transferId, int weightAtSource, int driverId) throws SQLException {
        this._dateOfTransfer = dateOfTransfer;
        this._leavingTime = leavingTime;
        this._arrivingDate = arrivingDate;
        this._arrivingTime = arrivingTime;
        this._truckLicenseNumber = truck_LicenseNumber;
        this._driverName = driverName;
        this._driverId = driverId;
        this._source = source;
        this._weightAtSource = weightAtSource; //-1 if no weight
        this._transferId = transferId;
        this._transferDestinationsDAO = TransferDestinationsDAO.getInstance();
        this._transferItemsDAO = TransferItemsDAO.getInstance();
        this._destinations = new LinkedList<>(destinations.keySet().stream().toList());
    }

    public void addToDAO(Map<Site, Map<Item_mock, Integer>> orderItems, Map<Site, Integer> destinations) throws SQLException {
        _transferDestinationsDAO.add(_transferId, destinations);
        for (Site site : orderItems.keySet()) {
            for (Item_mock item : orderItems.get(site).keySet())
            {
                _transferItemsDAO.add(_transferId, site.getSiteId(), item.getCatalogNum(), orderItems.get(site).get(item));
            }
        }
    }

    public void removeTransferItems(Map<Site, Map<Item_mock, Integer>> itemsToDelete)
    {
        for (Site site : itemsToDelete.keySet()) {
            for (Item_mock product : itemsToDelete.get(site).keySet())
            {
                Map<Site, Map<Item_mock, Integer>> orderItems = _transferItemsDAO.get(_transferId);

                Integer quantityToUpdate = updateQuantityOfItem(orderItems.get(site).get(product), itemsToDelete.get(site).get(product), site, product.getCatalogNum());

                if (quantityToUpdate == 0) {
                    orderItems = _transferItemsDAO.get(_transferId);
                    if (!orderItems.containsKey(site))
                    {
                        System.out.println("Please notice that you removed every item from this destination, so the destination has been removed from the transfer!");
                    }
                }
            }
        }
    }

    public Integer updateQuantityOfItem(Integer currentQuantity, Integer newQuantity, Site site, String catalogNum)
    {
        //calculate how much to reduce from each item
        int quantityToUpdate = currentQuantity - newQuantity;
        //update the database
        _transferItemsDAO.update(_transferId, site.getSiteId(), catalogNum, quantityToUpdate);
        if (quantityToUpdate == 0) {
            //update and remove product from the database
            _transferItemsDAO.delete(_transferId, catalogNum, site.getSiteId(), quantityToUpdate);
            Map<Site, Map<Item_mock, Integer>> orderItems = _transferItemsDAO.get(_transferId);
            if (!orderItems.containsKey(site))
            {
                //remove from the database
                removeTransferDestination(site);
            }
        }
        return quantityToUpdate;
    }

    public void updateTransferTruck(int truck_LicenseNumber)
    {
        _truckLicenseNumber = truck_LicenseNumber;
    }

    public void removeTransferDestination(Site destinationToDelete)
    {
        _transferDestinationsDAO.delete(_transferId, destinationToDelete.getSiteId());
        for (int i = 0; i < _destinations.size() ; i++)
        {
            if (_destinations.get(i).getSiteId() == destinationToDelete.getSiteId())
            {
                _destinations.remove(i);
                break;
            }
        }
    }

    public void createDocument()
    {
        Map<Site, Integer> destinations = _transferDestinationsDAO.get(_transferId); //value is the truck weight
        Map<Site, Map<Item_mock, Integer>> orderItems = _transferItemsDAO.get(_transferId);
        System.out.println("Creating transfer document (a text file will be created in current directory)...");
        String fileName = "transfer" + _transferId +"_Document.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(documentAddUnderline("TRANSFER DETAILS:") + "\n\n");
            fileWriter.write(String.format(" %20s %20s %20s %20s %20s \r\n\n", "Document ID: " +  _transferId, ", Date: "+ _dateOfTransfer.toString(), ", Track's number: "+ _truckLicenseNumber,  ", Leaving time: " +_leavingTime.toString(), ", Driver name: "+_driverName));
            fileWriter.write("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
            //fileWriter.write(String.format("%20s %20s %20s %20s %20s \r\n", _transferId, _dateOfTransfer.toString(), _truckLicenseNumber, _leavingTime.toString(), _driverName));
            fileWriter.write(documentAddUnderline(" SOURCE DETAILS: ") + "\n\n");
            fileWriter.write(String.format(" %20s \r\n", "Source name: "+ _source.getSiteName()));
            String truckWeight = "None";
            if(_weightAtSource != -1)
                truckWeight = Integer.toString(_weightAtSource);
            fileWriter.write(String.format(" %20s %20s %20s %20s \r\n\n", "Address: "+_source.getSiteAddress(), ", Contact name: "+ _source.get_contactName(), ", Phone: " + _source.get_phoneNumber(), ", Truck weight: " + truckWeight));
            //fileWriter.write(String.format("%20s %20s %20s %20s\r\n", _source.getSiteAddress(), _source.get_contactName(), _source.get_phoneNumber(), ""));
            fileWriter.write("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
            fileWriter.write(documentAddUnderline("DESTINATION DETAILS:") + "\n\n");
            int siteCounter = 0;
            for(Site dest: destinations.keySet())
            {
                fileWriter.write(String.format(" %20s \r\n","Destination name: "+ dest.getSiteName()));
                if(siteCounter<destinations.size()-1) {
                    truckWeight = "None";
                    if(destinations.get(dest) != -1)
                        truckWeight = Integer.toString(destinations.get(dest));
                    fileWriter.write(String.format(" %20s %20s %20s %20s \r\n\n", "Address: " + dest.getSiteAddress(), ", Contact name: "+dest.get_contactName(), ", Phone: "+dest.get_phoneNumber(), ", Truck Weight: "+ truckWeight));
                    //fileWriter.write(String.format("%20s %20s %20s %20s \r\n", _destinations.get(i).getSiteAddress(), _destinations.get(i).get_contactName(), _destinations.get(i).get_phoneNumber(), ""));
                }
                else {
                    fileWriter.write(String.format(" %20s %20s %20s \r\n\n", "Address: "+ dest.getSiteAddress() , ", Contact name: "+dest.get_contactName(), ", Phone: "+dest.get_phoneNumber()));
                    //fileWriter.write(String.format("%20s %20s %20s \r\n", _destinations.get(i).getSiteAddress(), _destinations.get(i).get_contactName(), _destinations.get(i).get_phoneNumber()));
                }
                siteCounter++;
            }
            fileWriter.write("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
            fileWriter.write(documentAddUnderline("TRANSFER ITEMS CONTENT:") + "\n\n");
            for (Site site : orderItems.keySet()) {
                for (Item_mock product : orderItems.get(site).keySet())
                {
                    fileWriter.write(String.format(" %20s %20s \r\n", "Item name: "+product.getItemName(), ", Quantity: "+orderItems.get(site).get(product)));
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
                        lines.set(i, String.format("%20s %20s %20s %20s ", "Address: " + site.getSiteAddress(), ", Contact name: "+site.get_contactName(), ", Phone: "+site.get_phoneNumber(), ", Truck Weight: "+ weight));
                    else
                        lines.set(i, String.format("%20s %20s %20s %20s ", "Address: " + site.getSiteAddress(), ", Contact name: "+site.get_contactName(), ", Phone: "+site.get_phoneNumber(), ", Truck Weight: "+ "None"));
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
                    lines.set(i, String.format(" %20s %20s %20s %20s %20s ", "Document ID: " +  _transferId, ", Date: "+ _dateOfTransfer.toString(), ", Track's number: "+ _truckLicenseNumber,  ", Leaving time: " +_leavingTime.toString(), ", Domain.Transfer.Transfer.Driver name: "+_driverName));
                }
            }
            Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.out.println("Error remove destination from document" + e.getMessage());
        }
    }

    public void documentUpdateOrderItems(){
        Map<Site, Map<Item_mock, Integer>> orderItems = _transferItemsDAO.get(_transferId);
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
                for (Site site : orderItems.keySet()) {
                    for (Item_mock product : orderItems.get(site).keySet())
                    {
                        lines.add(String.format(" %20s %20s ", "Item name: "+product.getItemName(), ", Quantity: "+orderItems.get(site).get(product)));
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

    public LocalDate getLeavingDate(){
        return this._dateOfTransfer;
    }

    public LocalTime get_arrivingTime(){return this._arrivingTime;}

    public LocalDate getArrivingDate(){return this._arrivingDate;}

    public Map<Site, Integer> getDestinations(){
        return _transferDestinationsDAO.get(_transferId);
    }

    public Map<Site, Map<Item_mock, Integer>> getOrderItems(){
        return _transferItemsDAO.get(_transferId);
    }

    public Site getSource(){
        return this._source;
    }

    public int getTruckLicenseNumber()
    {
        return _truckLicenseNumber;
    }

    public String getDriverName(){ return _driverName; }


    public void setArrivingDate(LocalDate arrivingDate)
    {
        _arrivingDate = arrivingDate;
    }

    public void setArrivingTime(LocalTime arrivingTime)
    {
        _arrivingTime = arrivingTime;
    }

    public void setDateOfTransfer(LocalDate dateOfTransfer){_dateOfTransfer = dateOfTransfer;}

    public void setLeavingTime(LocalTime leavingTime){_leavingTime = leavingTime;}

    public void setDriverName(String driverName){_driverName = driverName;}

    public void setSource(Site source){_source = source;}

    //from here i updated
    public String getTransferStatus()
    {
        /*
        returns whether the transfer has not started yet, in progress or done
         */
        String status = "NOT START";
        LocalDateTime transferLocalDateTimeLeaving = LocalDateTime.of(_dateOfTransfer, _leavingTime);
        LocalDateTime transferLocalDateTimeArriving = LocalDateTime.of(_arrivingDate, _arrivingTime);
        if(transferLocalDateTimeLeaving.isBefore(LocalDateTime.now()) && transferLocalDateTimeArriving.isAfter(LocalDateTime.now()))
        {
            status = "IN PROGRESS";
        }
        else if(transferLocalDateTimeArriving.isBefore(LocalDateTime.now()))
        {
            status = "DONE";
        }
        return status;
    }

    public int getTransferId()
    {
        return _transferId;
    }

    public void setWeightAtSource(int weight)
    {
        this._weightAtSource = weight;
    }

    public List<Site> getListOfDestinations()
    {
        return _destinations;
    }

    public int getWeightInSource()
    {
        return _weightAtSource;
    }

    public int getDriverId()
    {
        return _driverId;
    }
}

