import java.util.Map;

public class Contract {
    Map<Item, Map<Integer, Double>> map;
    Item[] ItemList;

    public Contract(Map<Item, Map<Integer, Double>> map, Item[] itemList) {
        this.map = map;
        ItemList = itemList;
    }


    public Map<Item, Map<Integer, Double>> getMap() {
        return map;
    }

    public void setMap(Map<Item, Map<Integer, Double>> map) {
        this.map = map;
    }

    public Item[] getItemList() {
        return ItemList;
    }

    public void setItemList(Item[] itemList) {
        ItemList = itemList;
    }
}



