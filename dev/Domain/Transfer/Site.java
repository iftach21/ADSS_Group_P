package Domain.Transfer;

import Domain.Enums.siteType;

public class Site {
    private int _siteId;
    private siteType _type;
    private String _siteName;
    private String _address;
    private String _phoneNumber;
    private String _contactName;
    private double _latitude;
    private double _longitude;

    public Site(int siteId, String siteName, String address, String phoneNumber, String contactName, double _latitude, double _longitude)
    {
        this._siteId = siteId;
        this._siteName = siteName;
        this._address = address;
        this._phoneNumber = phoneNumber;
        this._contactName = contactName;
        this._latitude = _latitude;
        this._longitude = _longitude;
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

    //return distance in km between this site to a given site
    public double calculateDistance(Site site)
    {
        double lon1 = _longitude;
        double lat1 = _latitude;
        double lon2 = site.getLongitude();
        double lat2 = site.getLatitude();

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance) / 1000;
    }

    public double getLatitude() {
        return _latitude;
    }

    public double getLongitude() {
        return _longitude;
    }

    public void setSiteAddress(String address)
    {
        this._address = address;
    }

    public void setSiteName(String name)
    {
        this._siteName = name;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this._phoneNumber = phoneNumber;
    }

    public void setContactName(String contactName)
    {
        this._contactName = contactName;
    }

    public void setLatitude(double latitude)
    {
        this._latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this._longitude = longitude;
    }

}
