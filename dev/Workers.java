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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setAvailable(WindowType[] available) {
        this.available = available;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public void setPhoneNUM(int phoneNUM) {
        this.phoneNUM = phoneNUM;
    }

    public void setPersonalinfo(String personalinfo) {
        this.personalinfo = personalinfo;
    }

    public void setPro(boolean[] pro) {
        this.pro = pro;
    }

    public static boolean removePro(int inx){
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
