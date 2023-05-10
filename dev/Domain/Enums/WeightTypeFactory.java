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

    static public weightType weightTypeFromString(String weight){
        if(weight.compareTo("lightWeight")==0){
            return weightType.lightWeight;
        }
        if(weight.compareTo("mediumWeight")==0){
            return weightType.mediumWeight;
        }
        if(weight.compareTo("heavyWeight")==0){return weightType.heavyWeight;}
        else{return null;}
    }
}
