import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Parser {
    public static Map<String, Pair<Integer, Float>> parse(String input) {
        Map<String, Pair<Integer, Float>> map = new HashMap<>();
        if(Objects.equals(input, "{}"))
        {
            return map;
        }
        String[] pairs = input.split("},");

        for (String pair : pairs) {
            String[] parts = pair.split(":\\{");
            String item =parts[0];
            Pair<Integer, Float> pairValues = getPair(parts[1]);
            map.put(item, pairValues);
        }

        return map;
    }

    private static Pair<Integer, Float> getPair(String input) {
        String[] parts = input.split(",");
        int number1 = 0;
        float number2 = 0;
        for (String part : parts) {
            String[] kv = part.split(":");
            String key = kv[0].replaceAll("[{}\"' ]", "");
            String value = kv[1].replaceAll("[{}\"' ]", "");
            switch (key) {
                case "first":
                     number1= Integer.parseInt(value);
                    break;
                case "second":
                    number2=Float.parseFloat(value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid key: " + key);
            }
        }
        return new Pair<>(number1,number2);
    }
}