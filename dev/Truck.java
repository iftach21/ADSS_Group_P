public class Truck {
    private int _truck_licenseNumber;
    private String _truckModel;
    private int _truckNetoWeight;
    private int _truckWeight;
    private int _truck_maxWeight;
    private int _coolingCapacity;
    private boolean _isCurrentlyUsed;

    public Truck(int truck_licenseNumber, String truckModel, int truckNetoWeight, int truckWeight, int truck_maxWeight, int coolingCapacity, boolean isCurrentlyUsed)
    {
        this._truck_licenseNumber = truck_licenseNumber;
        this._truckModel = truckModel;
        this._truckNetoWeight = truckNetoWeight;
        this._truckWeight = truckWeight;
        this._truck_maxWeight = truck_maxWeight;
        this._coolingCapacity = coolingCapacity;
        this._isCurrentlyUsed = isCurrentlyUsed;
    }

    public int getCurrentTruckWeight()
    {
        return _truckWeight;
    }

    public void updateWeight(int weightToAdd)
    {
        _truckWeight += weightToAdd;
    }

    public boolean checkWeightCapacity()
    {
        return _truck_maxWeight >= _truckWeight;
    }

    public int getMaxWeight(){ return _truck_maxWeight;}

    public boolean getIsCurrentlyUsed(){ return _isCurrentlyUsed;}

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
}
