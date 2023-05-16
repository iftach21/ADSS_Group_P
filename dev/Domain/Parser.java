package Domain;

import java.util.HashMap;
import java.util.Map;


public class Parser {

    public static Map<String, Pair<Integer, Float>> parse(String string) {
        Map<String, Pair<Integer, Float>> map = new HashMap<>();
        if(string.equals("{}")){
            return map;
        }
        string=string.substring(1,string.length()-1);
        string=string.replace("\"","");

        String[] pairs = string.split("},");
        for (String pair : pairs) {
            String[] keyValuePair =  pair.split("\\:\\{");
            String key = keyValuePair[0].substring(0);
            String number_two = keyValuePair[1].substring(0,keyValuePair[1].length());
            if(number_two.contains("}")){
                 number_two = keyValuePair[1].substring(0,keyValuePair[1].length()-1);
            }


            String[] valuePair = number_two.split(",");
            String value = valuePair[0].substring(6,valuePair[0].length());
            String floating = valuePair[1].substring(7,valuePair[1].length());
            float floatValue = Float.parseFloat(floating);

            Pair<Integer, Float> innerPair = new Pair<>(Integer.parseInt(value), floatValue);
            map.put(key, innerPair);
        }

        return map;
    }

}

