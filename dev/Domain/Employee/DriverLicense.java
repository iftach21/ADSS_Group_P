package Domain.Employee;

import Domain.Enums.TempLevel;
import Domain.Enums.weightType;

import java.util.Comparator;

public class DriverLicense implements Comparable<DriverLicense> {
    private weightType _licenseWeightCapacity;
    private TempLevel _licenseTempCapacity;

    public DriverLicense(weightType licenseWeightCapacity, TempLevel licenseTempCapacity)
    {
        this._licenseWeightCapacity = licenseWeightCapacity;
        this._licenseTempCapacity = licenseTempCapacity;
    }

    public weightType getLicenseWeightCapacity()
    {
        return _licenseWeightCapacity;
    }

    public TempLevel getLicenseTempCapacity()
    {
        return _licenseTempCapacity;
    }

    public void set_licenseWeightCapacity(weightType _licenseWeightCapacity) {
        this._licenseWeightCapacity = _licenseWeightCapacity;
    }

    public void set_licenseTempCapacity(TempLevel _licenseTempCapacity) {
        this._licenseTempCapacity = _licenseTempCapacity;
    }

    @Override
    public int compareTo(DriverLicense d) {
        return Comparator.comparing(DriverLicense::getLicenseWeightCapacity).compare(this, d);
    }
}
