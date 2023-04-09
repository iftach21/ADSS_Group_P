import java.time.Instant;
import java.util.Date;

public class specificItem{

    private int itemID;
    private final Date expirationDate;
    private boolean isDefected = false;
    private Location location;
    private static int nextItemID = 1;

    public specificItem(Date expirationDate, boolean isDefected, Location location) {
        this.itemID = nextItemID;
        nextItemID++;
        this.expirationDate = expirationDate;
        this.isDefected = isDefected;
        this.location = location;
    }

    public int getItemID() {
        return itemID;
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
