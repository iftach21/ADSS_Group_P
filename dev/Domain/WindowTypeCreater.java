package Domain;

public class WindowTypeCreater {
    public static WindowType getwindowtype(int day){
            if(day==1){return WindowType.day1;}
            if(day==2){return WindowType.day2;}
            if(day==3){return WindowType.day3;}
            if(day==4){return WindowType.day4;}
            if(day==5){return WindowType.day5;}
            if(day==6){return WindowType.day6;}
            if(day==7){return WindowType.day7;}
        return null;
    }
}
