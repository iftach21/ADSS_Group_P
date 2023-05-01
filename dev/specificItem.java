import java.time.Instant;
import java.util.Date;

public class specificItem extends Item{

    private final int serialNumber;
    private final Date expirationDate;
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

    public int getserialNumber() {
        return serialNumber;
    }

    public void setDefected(boolean defected) {
        isDefected = defected;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isExpired()
    {
        return expirationDate.before(Date.from(Instant.now()));
    }

    public boolean getisDefected(){
        return isDefected;
    }

    public String getLocationString() {
        return location.toString();
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
