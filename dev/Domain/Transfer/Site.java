package Domain.Transfer;

import Domain.Enums.siteType;

public class Site {
    private int _siteId;
    private siteType _type;
    private String _siteName;
    private String _address;
    private String _phoneNumber;
    private String _contactName;
    private double x_coordinate;
    private double y_coordinate;

    public Site(int siteId, String siteName, String address, String phoneNumber, String contactName, double x_coordinate, double y_coordinate)
    {
        this._siteId = siteId;
        this._siteName = siteName;
        this._address = address;
        this._phoneNumber = phoneNumber;
        this._contactName = contactName;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public String getSiteName(){
        return this._siteName;
    }

    public siteType getSiteType()
    {
        return _type;
    }

    public int getSiteId(){
        return _siteId;
    }

    public String getSiteAddress(){
        return _address;
    }

    public String get_contactName(){
        return _contactName;
    }

    public String get_phoneNumber(){
        return _phoneNumber;
    }

    public double calculateDistance(Site site)
    {
        return Math.sqrt(Math.pow(this.x_coordinate-site.x_coordinate, 2) + Math.pow(this.y_coordinate=site.y_coordinate, 2));
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }
}
