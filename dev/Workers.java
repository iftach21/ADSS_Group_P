public class Workers {
    private int id;
    private String name;
    private String contract;
    private String start_date;
    private WindowType[] available;
    private int wage;
    private int phoneNUM;
    private int bankNum;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContract() {
        return contract;
    }

    public String getStart_date() {
        return start_date;
    }

    public WindowType[] getAvailable() {
        return available;
    }

    public int getWage() {
        return wage;
    }

    public int getPhoneNUM() {
        return phoneNUM;
    }

    public String getPersonalinfo() {
        return personalinfo;
    }

    public boolean[] getPro() {
        return pro;
    }

    private String personalinfo;
    private boolean[] pro;

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public int getBankNum() {
        return bankNum;
    }

    public Workers(int id, String name, String contract, String start_date, WindowType[] available, int wage, int phoneNUM, String personalinfo, boolean[] pro, int bankNum) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.start_date = start_date;
        this.available = available;
        this.wage = wage;
        this.phoneNUM = phoneNUM;
        this.personalinfo = personalinfo;
        this.pro = pro;
        this.bankNum = bankNum;
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

    public  boolean removePro(int inx){
        //todo: i need to add here somethingh
        return true;
    }
    public  boolean addprof(int inx){
        //todo: i need to add here somethingh
        return true;
    }

    public  boolean canIShiftMange() {
        //todo: i need to add here somethingh
        return true;
    }
    public  boolean canICashier(){
        //todo: i need to add here somethingh
        return true;
    }
    public  boolean candrivetruck(){
        //todo: i need to add here somethingh
        return true;
    }
    public boolean addwindow(WindowType wt){
        //todo: complete
        return true;
    }
    public boolean removewindow(WindowType wt){
        //todo: complete
        return true;
    }
}
