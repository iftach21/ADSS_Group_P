import java.time.Instant;
import java.util.Date;

public class specificItem extends Item{

    private final Date expirationDate;
    private boolean isDefected = false;
    private int itemID;
    private Location location;

    public specificItem(String name, String catalogNum, double weight, String catalogName, TempLevel temperature, int minQuantity, Date expirationDate, boolean isDefected, int itemID, Location location) {
        super(name, catalogNum, weight, catalogName, temperature, minQuantity);
        this.expirationDate = expirationDate;
        this.isDefected = isDefected;
        this.itemID = itemID;
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

}
