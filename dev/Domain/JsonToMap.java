package Domain;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonToMap {
    public static Map<Item,Pair<Integer, Float>> convert(String itemsMapJson)
    {
        Map<Item, Pair<Integer, Float>> newMap = new HashMap<>();
        JSONObject newJson = new JSONObject(itemsMapJson);

        for (String key : newJson.keySet()) {
            JSONObject itemJson = newJson.getJSONObject(key);
            Item item = new Item(
                    itemJson.getString("name"),
                    itemJson.getString("catalogNum"),
                    itemJson.getDouble("weight"),
                    itemJson.getString("catalogName"),
                    TempLevel.valueOf(itemJson.getString("temperature")),
                    itemJson.getString("manufacturer"),
                    itemJson.getInt("minimum_quantity")
            );
            JSONObject pairJson = itemJson.getJSONObject("pair");
            Pair<Integer, Float> pair = new Pair<>(
                    pairJson.getInt("first"),
                    (float) pairJson.getDouble("second")
            );
            newMap.put(item, pair);
        }
        return newMap;
    }
}
