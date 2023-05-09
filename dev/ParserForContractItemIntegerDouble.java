import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParserForContractItemIntegerDouble {
    public static Map<Item, Map<Integer, Double>> parse(String input) {
        Map<Item, Map<Integer, Double>> map = new HashMap<>();
        if(Objects.equals(input, "{}"))
        {
            return map;
        }

        String[] pairs = input.split("},");

        for (String pair : pairs) {
            String[] parts = pair.split(":\\{");
            Item item = getItem(parts[0]);
            Map<Integer, Double> priceMap = getPriceMap(parts[1]);
            map.put(item, priceMap);
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