package Domain.Transfer;

import Domain.Enums.TempLevel;
import Domain.Enums.weightType;

import java.time.LocalDate;

public class Truck {
    private int _truck_licenseNumber;
    private String _truckModel;
    private int _truckNetoWeight;
    private int _truckWeight;
    private int _truck_maxWeight;
    private TempLevel _coolingCapacity;
    private LocalDate _unavailableStartDate;
    private LocalDate _unavailableEndDate;

    public Truck(int truck_licenseNumber, String truckModel, int truckNetoWeight, int truckWeight, int truck_maxWeight, TempLevel coolingCapacity, LocalDate unavailableStartDate, LocalDate unavailableEndDate)
    {
        this._truck_licenseNumber = truck_licenseNumber;
        this._truckModel = truckModel;
        this._truckNetoWeight = truckNetoWeight;
        this._truckWeight = truckWeight;
        this._truck_maxWeight = truck_maxWeight;
        this._coolingCapacity = coolingCapacity;
        this._unavailableStartDate = unavailableStartDate;
        this._unavailableEndDate = unavailableEndDate;
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

    public boolean getIsUsedInDate(LocalDate dateToCheck){ return !(dateToCheck.isAfter(_unavailableStartDate) && dateToCheck.isBefore(_unavailableEndDate));}

    public int getLicenseNumber(){return _truck_licenseNumber;}

    public boolean isHeavyWeight(){
        return this._truck_maxWeight>=16 && this._truck_maxWeight<=60;
    }

    public boolean isMiddleWeight(){
        return this._truck_maxWeight>=8 && this._truck_maxWeight<=15.9;
    }

    public boolean isLightWeight(){
        return this._truck_maxWeight>=3.5 && this._truck_maxWeight<=7.9;
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

    public void setTruckUnavailable(LocalDate startDate, LocalDate endDate)
    {
        _unavailableStartDate = startDate;
        _unavailableEndDate = endDate;
    }
}
