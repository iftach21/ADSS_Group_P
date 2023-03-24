public class Workers {
    private int id;
    private String name;
    private String contract;
    private String start_date;
    private WindowType[] available;
    private int wage;
    private int phoneNUM;
    private String personalinfo;
    private boolean[] pro;

    public Workers(int id, String name, String contract, String start_date, WindowType[] available, int wage, int phoneNUM, String personalinfo, boolean[] pro) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.start_date = start_date;
        this.available = available;
        this.wage = wage;
        this.phoneNUM = phoneNUM;
        this.personalinfo = personalinfo;
        this.pro = pro;
    }
    public static boolean removePro(){
        //todo: i need to add here somethingh
        return true;
    }
    public static boolean addprof(){
        //todo: i need to add here somethingh
        return true;
    }

    public static boolean canIShiftMange() {
        //todo: i need to add here somethingh
        return true;
    }
    public static boolean canICashier(){
        //todo: i need to add here somethingh
        return true;
    }
    public static boolean candrivetruck(){
        //todo: i need to add here somethingh
        return true;
    }
}
