import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PriceHistory {
    private double buyPrice;
    private double sellPrice;
    private Date currentDate;

    public PriceHistory(double buyPrice, double sellPrice, Date currentDate) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.currentDate = currentDate;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        return "Buy price: " + buyPrice +
                ", sell price: " + sellPrice +
                ", updated in: " + dateFormat.format(currentDate) + "\n";
    }
}
