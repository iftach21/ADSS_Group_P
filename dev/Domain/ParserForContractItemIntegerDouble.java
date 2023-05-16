package Domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParserForContractItemIntegerDouble {
    public static Map<String, Map<Integer, Double>> parse(String input) {
        Map<String, Map<Integer, Double>> map = new HashMap<>();
        if(Objects.equals(input, "{}"))
        {
            return map;
        }
        input =input.substring(1,input.length()-1);
        input=input.replace("\"","");
        String[] pairs = input.split("},");

        for (String pair : pairs) {
            String[] parts = pair.split(":\\{");
            String item = parts[0];
            Map<Integer, Double> priceMap = getPriceMap(parts[1].substring(0,parts[1].length()-1));
            map.put(item, priceMap);
        }

        return map;
    }



    private static Map<Integer, Double> getPriceMap(String input) {
        String[] parts = input.split(",");
        Map<Integer, Double> priceMap = new HashMap<>();
        for (String part : parts) {
            String[] kv = part.split(":");
            String key = kv[0].replaceAll("[{}\"' ]", "");
            String value = kv[1].replaceAll("[{}\"' ]", "");
            priceMap.put(Integer.parseInt(key), Double.parseDouble(value));
        }
        return priceMap;
    }
}