import java.util.Date;

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
}
