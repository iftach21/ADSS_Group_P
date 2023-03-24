public class Shift {
    private String date;
    private int day;
    private Workers[] workers;
    private ShiftManager shiftManager;

    public Shift(String date, int day, Workers[] workers, ShiftManager shiftManager) {
        this.date = date;
        this.day = day;
        this.shift = shift;
        this.workers = workers;
        this.shiftManager = shiftManager;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ShiftType getShift() {
        return shift;
    }

    public void setShift(ShiftType shift) {
        this.shift = shift;
    }

    public Workers[] getWorkers() {
        return workers;
    }

    public void setWorkers(Workers[] workers) {
        this.workers = workers;
    }

    public ShiftManager getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(ShiftManager shiftManager) {
        this.shiftManager = shiftManager;
    }
}
