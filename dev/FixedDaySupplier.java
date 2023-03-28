public class FixedDaySupplier {
    private WindowType currentDeliveryDay;

    public WindowType getCurrentDeliveryDay() {
        return currentDeliveryDay;
    }

    public void setCurrentDeliveryDay(WindowType currentDeliveryDay) {
        this.currentDeliveryDay = currentDeliveryDay;
    }

    public FixedDaySupplier(WindowType currentDeliveryDay) {
        this.currentDeliveryDay = currentDeliveryDay;
    }
}
