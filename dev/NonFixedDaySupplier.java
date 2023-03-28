public class NonFixedDaySupplier extends DeliveringSupplier{
    int numOfDayToDeliver;

    public NonFixedDaySupplier(int numOfDayToDeliver) {
        this.numOfDayToDeliver = numOfDayToDeliver;
    }

    public int getNumOfDayToDeliver() {
        return numOfDayToDeliver;
    }

    public void setNumOfDayToDeliver(int numOfDayToDeliver) {
        this.numOfDayToDeliver = numOfDayToDeliver;
    }
}
