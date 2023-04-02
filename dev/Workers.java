import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Workers {

    private int id;
    private String name;
    private String contract;
    private String start_date;
    private ArrayList<WindowType> available;
    private int wage;
    private int phoneNUM;

    private String personalinfo;
    private int bankNum;
    //manager 0, cashier 1,stoke 2 , security 3, cleaning 4, shelfstoking 5 ,general worker6.
    private boolean[] pro;

    private String drivingLicense;
    public Workers(int id, String name, String contract, String start_date, int wage, int phoneNUM, String personalinfo, int bankNum) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.start_date = start_date;
        this.wage = wage;
        this.phoneNUM = phoneNUM;
        this.personalinfo = personalinfo;
        this.pro = new boolean[7];
        Arrays.fill(pro, false);
        this.bankNum = bankNum;
        this.available = new ArrayList<WindowType>();
        this.drivingLicense = null;

    }


    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

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

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public int getBankNum() {
        return bankNum;
    }
    /**
     * Create an employee
     */

    public void drivingLicenseChange(String driverLicenseStatus){
        this.drivingLicense=driverLicenseStatus;
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

    /**
     * remove a profession to an employee
     */
    public  boolean removePro(int inx){
        this.pro[inx]=false;
        return true;
    }
    /**
     * Adding a profession to an employee
     */
    public  boolean addprof(int inx){
        this.pro[inx]=true;
        return true;
    }
    /**
     * Check if the employee is qualified to do a certain job
     */
    public boolean caniworkatprofindx(int indx){
        return this.pro[indx];
    }

    public  boolean canIShiftMange() {

        return this.pro[0];
    }
    public  boolean canICashier(){
        return this.pro[1];
    }

    public  boolean canIstoke(){
        return this.pro[2];
    }
    public  boolean canIsecurity(){
        return this.pro[3];
    }
    public  boolean canIcleaning(){
        return this.pro[4];
    }
    public  boolean canIshelfstoking(){
        return this.pro[5];
    }
    public  boolean canIgeneralworker(){
        return this.pro[6];
    }
    //manager 0, cashier 1,stoke 2 , security 3, cleaning 4, shelfstoking 5 ,general worker 6.

    public  String candrivetruck(){
        return this.drivingLicense;
    }
    /**
     * Add time an employee can work
     */
    public boolean addwindow(WindowType wt){
        this.available.add(wt);
        return true;
    }
    /**
     *  Removing time the employee is free to work
     */
    public boolean removewindow(WindowType wt){
        int inx=0;
        for (WindowType windowType: this.available){
            if(windowType==wt){
                this.available.remove(inx);
                break;
            }
            inx++;
        }
        return true;
    }

    /**
     * Checks if the employee can work, if he is free
     */
    public boolean canIworkat(WindowType wt) {
        //returns bool if can work on the windowtype.
        for (WindowType windowType : this.available) {
            if (windowType == wt) {
                return true;
            }
        }
        return false;
    }
    /**
     * Printing works
     */
    public void print(){
        String ans = "name: ";
        ans +=this.name + " id: " + String.valueOf(this.id);
        System.out.println(ans);
    }


}