import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

class ItemMapDeserializer implements JsonDeserializer<Map<Item, Pair<Integer, Float>>> {
    @Override
    public Map<Item, Pair<Integer, Float>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Map<Item, Pair<Integer, Float>> map = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            Item item = context.deserialize(new JsonPrimitive(entry.getKey()), Item.class);
            Pair<Integer, Float> pair = context.deserialize(entry.getValue(), Pair.class);
            map.put(item, pair);
        }

        return map;
    }
}
