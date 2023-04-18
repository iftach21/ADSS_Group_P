public class Driver {

    private DriverLicense _driversLicense;
    private String _driverName;
    private boolean _isAvailable;

    public Driver(DriverLicense driversLicense, String driverName, boolean isAvailable){
        this._driversLicense = driversLicense;
        this._driverName = driverName;
        this._isAvailable = isAvailable;
    }

    public boolean getIsAvailable(){
        return _isAvailable;
    }

    public void setIsAvailable(boolean val){
        this._isAvailable = val;
    }

    public DriverLicense getDriverLicense(){
        return _driversLicense;
    }

    public boolean checkLicenseWithItemTemp(TempLevel itemTemp)
    {
        if (itemTemp.compareTo(_driversLicense.getLicenseTempCapacity()) > 0)
            return false;
        return true;
    }

    public String getDriverName()
    {
        return _driverName;
    }
}
