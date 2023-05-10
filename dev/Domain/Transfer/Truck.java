package Domain.Transfer;

import Domain.Enums.TempLevel;
import Domain.Enums.weightType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Truck {
    private int _truck_licenseNumber;
    private String _truckModel;
    private int _truckNetoWeight;
    private int _truckWeight;
    private int _truck_maxWeight;
    private TempLevel _coolingCapacity;
    private LocalDateTime _unavailableStartTime;
    private LocalDateTime _unavailableEndTime;

    public Truck(int truck_licenseNumber, String truckModel, int truckNetoWeight, int truckWeight, int truck_maxWeight, TempLevel coolingCapacity, LocalDate unavailableStartDate, LocalTime unavailableStartTime, LocalDate unavailableEndDate, LocalTime unavailableEndTime)
    {
        this._truck_licenseNumber = truck_licenseNumber;
        this._truckModel = truckModel;
        this._truckNetoWeight = truckNetoWeight;
        this._truckWeight = truckWeight;
        this._truck_maxWeight = truck_maxWeight;
        this._coolingCapacity = coolingCapacity;
        this._unavailableStartTime = LocalDateTime.of(unavailableStartDate, unavailableStartTime);
        this._unavailableEndTime = LocalDateTime.of(unavailableEndDate, unavailableEndTime);
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

    public boolean getIsUsedInDate(LocalDateTime leavingDate, LocalDateTime arrivingDate){ return !(leavingDate.isAfter(_unavailableEndTime) || arrivingDate.isBefore(_unavailableStartTime));}

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

    public void setTruckUnavailable(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime)
    {
        _unavailableStartTime = LocalDateTime.of(startDate, startTime);
        _unavailableEndTime = LocalDateTime.of(endDate, endTime);;
    }

    public int getTruckNetWeight()
    {
        return _truckNetoWeight;
    }

    public LocalDateTime getUnavailableStartTime()
    {
        return _unavailableStartTime;
    }

    public LocalDateTime getUnavailableEndTime() {return  _unavailableEndTime;}
}
