import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static Map<Item, Pair<Integer, Float>> parse(String input) {
        Map<Item, Pair<Integer, Float>> map = new HashMap<>();
        if(input == "{}")
        {
            return map;
        }
        String[] pairs = input.split("},");

        for (String pair : pairs) {
            String[] parts = pair.split(":\\{");
            Item item = getItem(parts[0]);
            Pair<Integer, Float> pairValues = getPair(parts[1]);
            map.put(item, pairValues);
        }

        return map;
    }

    private static Item getItem(String input) {
        String[] parts = input.split(",");
        Item item = new Item();
        for (String part : parts) {
            String[] kv = part.split("=");
            String key = kv[0].replaceAll("[{}\"' ]", "");
            String value = kv[1].replaceAll("[{}\"' ]", "");
            switch (key) {
                case "Itemname":
                    item.setName(value);
                    break;
                case "catalogNum":
                    item.setCatalogNum(value);
                    break;
                case "weight":
                    item.setWeight(Float.parseFloat(value));
                    break;
                case "catalogName":
                    item.setCatalogName(value);
                    break;
                case "temperature":
                    item.setTemperature(TempLevel.valueOf(value));
                    break;
                case "manufacturer":
                    item.setManufacturer(value);
                    break;
                case "minimum_quantity":
                    item.setMinimum_quantity(Integer.parseInt(value));
                    break;
                case "priceHistory":
                    // ignore this field
                    break;
                default:
                    throw new IllegalArgumentException("Invalid key: " + key);
            }
        }
        return item;
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