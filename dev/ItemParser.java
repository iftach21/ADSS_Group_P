import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    private static final Pattern ITEM_PATTERN = Pattern.compile("Item\\{name='(\\w+)', catalogNum='(\\d+)', weight=([\\d\\.]+), catalogName='(\\w+)', temperature=(\\w+), priceHistory=\\[(.*)\\], manufacturer='(\\w+)', minimum_quantity=(\\d+)\\}\\{\"first\":(\\d+),\"second\":([\\d\\.]+)\\}");

    public static Map<Item, Pair<Integer, Float>> parse(String input) {
        Map<Item, Pair<Integer, Float>> map = new HashMap<>();
        String[] itemStrings = input.split("\\}\\s*,\\s*\\{");
        for (String itemString : itemStrings) {
            itemString = itemString.trim();
            Matcher matcher = ITEM_PATTERN.matcher(itemString);
            if (matcher.matches()) {
                String name = matcher.group(1);
                String catalogNum = matcher.group(2);
                double weight = Double.parseDouble(matcher.group(3));
                String catalogName = matcher.group(4);
                String temperatures = matcher.group(5);
                TempLevel tempValue = TempLevel.valueOf(temperatures);
                String[] priceHistoryString = matcher.group(6).split(",");
                float[] priceHistory = new float[priceHistoryString.length];
                for (int i = 0; i < priceHistoryString.length; i++) {
                    priceHistory[i] = Float.parseFloat(priceHistoryString[i]);
                }
                String manufacturer = matcher.group(7);
                int minimumQuantity = Integer.parseInt(כר.group(8));
                Item item = new Item(name,catalogNum,weight,catalogName,tempValue,manufacturer);
                item.setMinimum_quantity(minimumQuantity);
                String json = "{" + matcher.group(9) + "}";
                JSONObject jsonObject = new JSONObject(json);
                int first = jsonObject.getInt("first");
                float second = (float) jsonObject.getDouble("second");
                map.put(item, new Pair<>(first, second));
            } else {
                throw new IllegalArgumentException("Invalid input: " + itemString);
            }
        }
        return map;
    }
}
