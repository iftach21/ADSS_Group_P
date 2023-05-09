package Domain.Enums;


import java.util.ArrayList;
import java.util.List;

public class WindowTypeCreater {
    public WindowTypeCreater() {
        //empty
    }
    // you give the funtion day and when (day\night) and it return the enum.
    public WindowType getwidowtype(int day, String time){
        if(time.equals("day")){
            if(day==1){return WindowType.day1;}
            if(day==2){return WindowType.day2;}
            if(day==3){return WindowType.day3;}
            if(day==4){return WindowType.day4;}
            if(day==5){return WindowType.day5;}
            if(day==6){return WindowType.day6;}
            if(day==7){return WindowType.day7;}
        }
        if(time.equals("night")){
            if(day==1){return WindowType.night1;}
            if(day==2){return WindowType.night2;}
            if(day==3){return WindowType.night3;}
            if(day==4){return WindowType.night4;}
            if(day==5){return WindowType.night5;}
            if(day==6){return WindowType.night6;}
            if(day==7){return WindowType.night7;}
        }
        return null;
    }

    static public List<WindowType> getAllWindowTypes(){
        List<WindowType> windowTypes = new ArrayList<>();
        windowTypes.add(WindowType.day1);
        windowTypes.add(WindowType.day2);
        windowTypes.add(WindowType.day3);
        windowTypes.add(WindowType.day4);
        windowTypes.add(WindowType.day5);
        windowTypes.add(WindowType.day6);
        windowTypes.add(WindowType.day7);
        windowTypes.add(WindowType.night1);
        windowTypes.add(WindowType.night2);
        windowTypes.add(WindowType.night3);
        windowTypes.add(WindowType.night4);
        windowTypes.add(WindowType.night5);
        windowTypes.add(WindowType.night6);
        windowTypes.add(WindowType.night7);
        return windowTypes;
    }

}
