public class Driver {

    private licenseType _license_type;
    private String _driverName;
    private boolean _isAvailable;


    public Driver(licenseType license_type, String driverName, boolean isAvailable){
        this._license_type = license_type;
        this._driverName = driverName;
        this._isAvailable = isAvailable;
    }

    public boolean getIsAvailable(){
        return _isAvailable;
    }
}
