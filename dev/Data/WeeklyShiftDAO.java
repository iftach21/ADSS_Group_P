package Data;

import Domain.WeeklyShift;

import java.util.ArrayList;
import java.util.List;

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

    public void add(WeeklyShift ws){
        //todo: complete
    }

    public void delete(int weekNum, int yearNum, int superNum){
        //todo: complete;
    }

}
