package Domain.Employee;

import Domain.Enums.TempLevel;
import Domain.Enums.weightType;

public class Driver extends Workers {

    private DriverLicense _driversLicense;



    public Driver(int id, String name, String contract, String start_date, int wage, int phoneNUM, String personalinfo, int bankNum, TempLevel tl, weightType wt){
        super(id, name, contract, start_date, wage, phoneNUM, personalinfo, bankNum);
        this._driversLicense = new DriverLicense(wt,tl);
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
    public void set_driversLicense(DriverLicense _driversLicense) {
        this._driversLicense = _driversLicense;
    }
    public boolean amIDriver(){return true;}

}
