package Domain;

public class Discount {
    private double PercentageDiscount;
    private double StandardDiscount;

    public Discount(double percentageDiscount, double standardDiscount) {
        this.PercentageDiscount = percentageDiscount;
        this.StandardDiscount = standardDiscount;
    }
    public Discount() {
        this.PercentageDiscount = 0;
        this.StandardDiscount = 0;
    }

    public double getPercentageDiscount() {
        return PercentageDiscount;
    }

    public void setPercentageDiscount(double percentageDiscount) {
        PercentageDiscount = percentageDiscount;
    }

    public double getStandardDiscount() {
        return StandardDiscount;
    }

    public void setStandardDiscount(double standardDiscount) {
        StandardDiscount = standardDiscount;
    }
}
