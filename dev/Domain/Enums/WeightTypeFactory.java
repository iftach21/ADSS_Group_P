package Domain.Enums;

public class WeightTypeFactory {
    static public weightType weightType(int indx){
        if(indx==1){
            return weightType.lightWeight;
        }
        if(indx ==2){
            return weightType.mediumWeight;
        }
        if(indx==3){return weightType.heavyWeight;}
        else{return null;}
    }
}
