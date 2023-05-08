package Data;

import Domain.Employee.Shift;
import Domain.Employee.WeeklyShift;


import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class WeeklyShiftDAO {
    private static WeeklyShiftDAO instance = null;
    private List<WeeklyShift> weeklyShiftList; //holds all the weeklyshifts

    private WeeklyShiftDAO(){
        weeklyShiftList = new ArrayList<>();
    }
    public static WeeklyShiftDAO getInstance(){
        if(instance==null){
            instance =  new WeeklyShiftDAO();
        }
        return instance;
    }
    public WeeklyShift get(int weekNum, int yearNum, int superNum){
        //todo: to complete;
        return null;
    }

    public void update(){}

    public List<WeeklyShift> getWeeklyShiftList() {
        return weeklyShiftList;
    }

    public void add(Shift s){

    }

    public void delete(int weekNum, int yearNum, int superNum){
        //todo: complete;
    }

}
