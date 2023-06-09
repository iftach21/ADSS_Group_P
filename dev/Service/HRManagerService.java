package Service;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.TempTypeFactory;
import Domain.Enums.WeightTypeFactory;
import Domain.Enums.WindowTypeCreater;

import java.sql.SQLException;

public class HRManagerService {
    private WeeklyShiftAndWorkersManager controller =  WeeklyShiftAndWorkersManager.getInstance();

    public HRManagerService() throws SQLException {
    }

    /**
     * will add req how many needed for each prof index to which weekly shift identify as weeknum, year, and super num.
     * */
    public void addreqtoweeklyshift(int weeknum,int yearans,int supernum,int daynum,String don,int prof, int hm) throws SQLException {
        controller.addreqtoweeklyshift(weeknum, yearans, supernum, daynum, don, prof, hm);
    }

    /**
     * will add some worker with id and the prof he will serve to a specific weekly shift.
     * */
    public void addtoexistingweeklyshift(int weeknum,int year, int supernum, int daynum, String don, int id,int prof) throws SQLException {
        WindowTypeCreater wc = new WindowTypeCreater();
        controller.addtoexistingweeklyshift(weeknum, year, supernum, wc.getwidowtype(daynum, don), id, prof);
    }

    /**
     * will switch between 2 employees by their id, with given when and what weekly shift this happends
     * */
    public void switchemployeeinashift(int weeknum,int year,int supernum,int daynum, String don,int prof,int id1,int id2) throws SQLException {
        WindowTypeCreater wt = new WindowTypeCreater();
        controller.switchemployeeinashift(weeknum,year,supernum,wt.getwidowtype(daynum,don),prof,id1,id2);
    }

    /**
     * will fire employee when given id.
     * */
    public void fireemployee(int id){
        controller.fireemployee(id);
    }

    /**
     * will add a new driver with given personal info
     * */
    public void addNewDriver(int id,String name,String contract,String start_date,int wage,int phoneNUM,String personalinfo,int bankNum, int tempType, int weightType){
        controller.addNewDriver(id,name,contract,start_date,wage,phoneNUM,personalinfo,bankNum,
                TempTypeFactory.TempLevel(tempType), WeightTypeFactory.weightType(weightType));
    }

    /**
     * will add new employee when given personal info
     * */
    public void addemployee(int id,String name,String contract,String start_date,int wage,int phoneNUM,String personalinfo,int bankNum)  {
        controller.addemployee(id, name, contract, start_date, wage, phoneNUM, personalinfo, bankNum);
    }

    /**
     * will add more wage (or subtract) to an employee
     * */
    public void addwagetoemployee(int id,int wageAdd){
        controller.addwagetoemployee(id,wageAdd);
    }

    /**
     * will give back int - how much did a specific worker did in a week and specific year, doesn't matter the super
     * */
    public int getwageforemployee(int id,int weeknum,int year) {
        return controller.getwageforemployee(id,weeknum,year);
    }

    /**
     * will change the employee's contract when given new one
     * */
    public void changeemployeecontract(int id,String newContract){
        controller.changeemployeecontract(id,newContract);
    }

    /**
     * will update the employee's bank num.
     * */
    public void updateemployeesbank(int id,int bankNum){
        controller.updateemployeesbank(id,bankNum);
    }

    /**
     * will add when a worker can work,
     * daynum = the day he can
     * nord - is night or day (string)
     * */
    public void addavilableforemployee(int id,int daynum,String nord){
        WindowTypeCreater wdc = new WindowTypeCreater();
        controller.addavilableforemployee(id,wdc.getwidowtype(daynum,nord));
    }

    /**
     * will add new prof(by index) to employee by id
     * */
    public void addnewproforemployee(int id,int prof){
        controller.addnewproforemployee(id,prof);
    }

    /**
     * will remove prof (by index to employee
     */
    public void removeprofforemployee(int id,int prof) {
        controller.removeprofforemployee(id, prof);
    }

    /**
     * will remove for worker when he can't work
     * */
    public void removeavalbleforemployee(int id,int daynum,String nord){
        WindowTypeCreater wdc = new WindowTypeCreater();
        controller.removeavalbleforemployee(id,wdc.getwidowtype(daynum,nord));
    }

    /**
     * this will set the personal info for employee
     * */
    public void setPersonalinfo(int id,String persoinfo){
        controller.setPersonalinfo(id,persoinfo);
    }

    //todo: need here in the future to get some sort of all employees:

}
