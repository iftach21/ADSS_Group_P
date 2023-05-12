package Domain.Transfer;

import DataAccesObjects.Transfer.TransferTrucksDAO;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Truck {
    private int _truck_licenseNumber;
    private String _truckModel;
    private int _truckNetoWeight;
    private int _truckWeight;
    private int _truck_maxWeight;
    private TempLevel _coolingCapacity;
    private final TransferTrucksDAO _transferTrucksDAO;


    public Truck(int truck_licenseNumber, String truckModel, int truckNetoWeight, int truckWeight, int truck_maxWeight, TempLevel coolingCapacity) throws SQLException {
        this._truck_licenseNumber = truck_licenseNumber;
        this._truckModel = truckModel;
        this._truckNetoWeight = truckNetoWeight;
        this._truckWeight = truckWeight;
        this._truck_maxWeight = truck_maxWeight;
        this._coolingCapacity = coolingCapacity;
        this._transferTrucksDAO = TransferTrucksDAO.getInstance();

    }

    public int getCurrentTruckWeight()
    {
        return _truckWeight;
    }

    public void updateWeight(int weightToUpdate)
    {
        _truckWeight = weightToUpdate;
    }

    public boolean checkWeightCapacity()
    {
        return _truck_maxWeight >= _truckWeight;
    }

    public int getMaxWeight(){ return _truck_maxWeight;}

    public boolean getIsUsedInDate(LocalDateTime leavingDate, LocalDateTime arrivingDate)
    {
        List<Transfer> trucksTransfers =  _transferTrucksDAO.get(_truck_licenseNumber);
        for (Transfer transfer : trucksTransfers)
        {
            LocalDateTime transferStartDate = LocalDateTime.of(transfer.getLeavingDate(), transfer.getLeavingTime());
            LocalDateTime transferEndDate = LocalDateTime.of(transfer.getArrivingDate(), transfer.get_arrivingTime());
            if (!(transferStartDate.isAfter(arrivingDate) || transferEndDate.isBefore(leavingDate)))
            {
                return true;
            }
        }
        return false;
    }

    public int getLicenseNumber(){return _truck_licenseNumber;}

    public boolean isHeavyWeight(){
        return this._truck_maxWeight>=16 && this._truck_maxWeight<=60;
    }

    public boolean isMiddleWeight(){
        return this._truck_maxWeight>=8 && this._truck_maxWeight<=15.9;
    }

    public boolean isLightWeight(){
        return this._truck_maxWeight>=0 && this._truck_maxWeight<=7.9;
    }

    public TempLevel getTempCapacity(){return _coolingCapacity;}

    public void resetTruckWeight()
    {
        _truckWeight = _truckNetoWeight;
    }

    public String getTruckModel()
    {
        return _truckModel;
    }

    public weightType getTruckWeightType()
    {
        if (_truck_maxWeight>=3.5 && this._truck_maxWeight<=7.9)
            return weightType.lightWeight;
        else if (_truck_maxWeight>=8 && this._truck_maxWeight<=15.9)
            return weightType.mediumWeight;
        else
            return weightType.heavyWeight;
    }

    public void addToDAO(int transferId) throws SQLException {
        _transferTrucksDAO.add(transferId, _truck_licenseNumber);
    }

    public void deleteFromDAO(int transferId) throws SQLException {
        _transferTrucksDAO.delete(transferId, _truck_licenseNumber);
    }

    public int getTruckNetWeight()
    {
        return _truckNetoWeight;
    }

    public void setTruckModel(String model){this._truckModel = model;}

    public void setTruckNetWeight(int netWeight){this._truckNetoWeight = netWeight;}

    public void setTruckMaxWeight(int maxWeight){this._truck_maxWeight = maxWeight;}

    public void setTempCapacity(TempLevel tempCapacity){this._coolingCapacity = tempCapacity;}

}

