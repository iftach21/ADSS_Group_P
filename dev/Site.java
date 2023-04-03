public class Site {
    //private siteType _type;
    private String _siteName;
    private String _address;
    private String _phoneNumber;
    private String _contactName;

    public Site(String siteName, String address, String phoneNumber, String contactName)
    {
        this._siteName = siteName;
        this._address = address;
        this._phoneNumber = phoneNumber;
        this._contactName = contactName;
    }

    public String getSiteName(){
        return this._siteName;
    }
}
