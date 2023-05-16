package Domain;

public enum PaymentMethod {
    SHOTEF(0),
    SHOTEFPLUS30(1),
    SHOTEFPLUS60(2);

    private int numericValue;

    PaymentMethod(int value)
    {
        this.numericValue = numericValue;
    }

    public int getNumericValue()
    {
        return numericValue;
    }
}
