package Domain;

import java.time.Instant;
import java.util.Date;

public class specificItem extends Item{

    private int serialNumber;
    private Date expirationDate;
    private boolean isDefected = false;
    private Location location;
    private static int nextserialNumber = 1;

    public specificItem(Date expirationDate, boolean isDefected, Location location, Item fatherItem) {
        super(fatherItem);
        this.serialNumber = nextserialNumber;
        nextserialNumber++;
        this.expirationDate = expirationDate;
        this.isDefected = isDefected;
        this.location = location;
    }

    public specificItem() {
        this.expirationDate = null;
        this.location = null;
        this.serialNumber = nextserialNumber;
        nextserialNumber++;
    }

    public int getserialNumber() {
        return serialNumber;
    }

    public void setDefected(boolean defected) {
        isDefected = defected;
    }
    public void setDate(Date currentDate) {this.expirationDate = currentDate; }
    public void setSerialNumber(int newSerial) {this.serialNumber = newSerial; }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) { super.setName(name); }

    public boolean isExpired()
    {
        return expirationDate.before(Date.from(Instant.now()));
    }

    public boolean getisDefected(){
        return isDefected;
    }

    public String getLocationString() {
        return location.toString().toLowerCase();
    }
    public Date getDate() {return this.expirationDate; }
    public int getSerialNumber() {return this.serialNumber; }
    public String getSerialNumberString() {
        String nameSerial = this.getName();
//        String serialNumber = String.valueOf(this.serialNumber);
//        String returnName = nameSerial + serialNumber;
        return nameSerial;
    }

    public boolean isDefected()
    {
        if (isExpired() == true)
        {
            isDefected = true;
        }
        return isDefected;
    }

    public void moveSpecificItem(){
        if (this.getLocationString().equals("Store")){
            this.location = Location.Storage;
        }
        else {
            this.location = Location.Store;
        }
    }

    @Override
    public String toString() {
        return  "item ID : " + serialNumber +
                ", Expiration Date: " + expirationDate +
                ", isDefected: " + isDefected +
                ", location: " + location;
    }


}
