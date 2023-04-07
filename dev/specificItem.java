import java.time.Instant;
import java.util.Date;

public class specificItem{

    private int itemID;
    private final Date expirationDate;
    private boolean isDefected = false;
    private Location location;

    public specificItem(int itemID, Date expirationDate, boolean isDefected, Location location) {
        this.itemID = itemID;
        this.expirationDate = expirationDate;
        this.isDefected = isDefected;
        this.location = location;
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

    public boolean isDefected()
    {
        if (isExpired() == true)
        {
            isDefected = true;
        }
        return isDefected;
    }

    @Override
    public String toString() {
        return "specificItem : " +
                "item ID : " + itemID +
                ", Expiration Date: " + expirationDate +
                ", isDefected: " + isDefected +
                ", location: " + location;
    }
}
