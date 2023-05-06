package Domain.Enums;

public class TempTypeFactory {
    static public TempLevel TempLevel(int indx){
        if(indx==1){
            return TempLevel.regular;
        }
        if(indx ==2){
            return TempLevel.cold;
        }
        if(indx==3){return TempLevel.frozen;}
        else{return null;}
    }
}
