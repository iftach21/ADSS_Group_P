public enum PaymentMethod {
    SHOTEF(1),
    SHOTEFPLUS30(2),
    SHOTEFPLUS60(3);

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
