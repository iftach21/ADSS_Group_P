package Domain.Transfer;

import Domain.Enums.siteType;

public class Site {
    private int _siteId;
    private siteType _type;
    private String _siteName;
    private String _address;
    private String _phoneNumber;
    private String _contactName;

    public Site(int siteId, String siteName, String address, String phoneNumber, String contactName)
    {
        this._siteId = siteId;
        this._siteName = siteName;
        this._address = address;
        this._phoneNumber = phoneNumber;
        this._contactName = contactName;
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
}
