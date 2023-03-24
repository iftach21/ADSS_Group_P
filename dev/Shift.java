public class Shift {
    private String date;
    private Workers[] workers;
    private ShiftManager shiftManager;   //todo: i need to add here somethingh
    private String log;

    public Shift(String date, Workers[] workers, ShiftManager shiftManager, String log) {
        this.date = date;
        this.workers = workers;
        this.shiftManager = shiftManager;
        this.log = log;
    }
    public static String print(){
        //todo: i need to add here somethingh
        return null;

    }
}
