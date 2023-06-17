package Domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PriceHistory {
    private double buyPrice;
    private double sellPrice;
    private SimpleDateFormat currentDate;

    public PriceHistory(double buyPrice, double sellPrice, Date currentDate) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.currentDate  = new SimpleDateFormat("dd/MM/yyyy", new Locale("en"));
        this.currentDate.setLenient(false);
        this.currentDate.format(currentDate);
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public String getCurrentDate() {
        return currentDate.format(new Date());
    }

    @Override
    public String toString() {
        return "Buy price: " + buyPrice +
                ", sell price: " + sellPrice +
                ", updated in: " + getCurrentDate() + "\n";
    }
}
