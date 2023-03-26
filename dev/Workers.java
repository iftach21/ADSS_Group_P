import java.util.Arrays;
import java.util.List;

public class Workers {
    //todo: to add the licence type. and the function that's needed
    private int id;
    private String name;
    private String contract;
    private String start_date;
    private List<WindowType> available;
    private int wage;
    private int phoneNUM;
    private int bankNum;
    private boolean[] pro;

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

    public List<WindowType> getAvailable() {
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

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public int getBankNum() {
        return bankNum;
    }

    public Workers(int id, String name, String contract, String start_date, int wage, int phoneNUM, String personalinfo, int bankNum) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.start_date = start_date;
        this.wage = wage;
        this.phoneNUM = phoneNUM;
        this.personalinfo = personalinfo;
        this.pro = new boolean[5]; //todo: need to update this to bigger
        Arrays.fill(pro, false);
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

    public void setAvailable(List<WindowType> available) {
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
    public int getID(){
      return this.id;
    }
    public  boolean removePro(int inx){
        //todo: i need to add here somethingh
        this.pro[inx]=true;
        return true;
    }
    public  boolean addprof(int inx){
        //todo: i need to add here somethingh
        this.pro[inx]=false;
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
    public void addnewproforemployee(int id, int indxprof){

        //todo: complete accordingly
    }
    public void removeprofforemployee(int id,int indxprof){
        //todo: complete accordingly
    }

    public boolean caniworkat(WindowType wt){
        //returns bool if can work on the windowtype.
        return true;
        //todo: complete
    }





}
