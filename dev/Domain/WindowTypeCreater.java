package Domain;

public class WindowTypeCreater {
    public WindowTypeCreater() {
        //empty
    }
    // you give the funtion day and when (day\night) and it return the enum.
    public WindowType getwidowtype(int day,String time){
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

}
