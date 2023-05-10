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
    static public TempLevel TempLevelFromString(String templvl){
        if(templvl.compareTo("regular")==0){
            return TempLevel.regular;
        }
        if(templvl.compareTo("cold")==0){
            return TempLevel.cold;
        }
        if(templvl.compareTo("frozen")==0){return TempLevel.frozen;}
        else{return null;}
    }
}
