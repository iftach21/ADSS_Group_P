package Domain;

import java.util.ArrayList;
import java.util.List;

public class ItemController {
    private List<Item> ItemList;

    public ItemController(List<Item> itemList) {
        this.ItemList = new ArrayList<Item>();
    }

    public Item addItem(String name, String catalogNum, double weight, String manufacturer, TempLevel temperature, String categoryName)
    {
        //Item _item = new Item(name, catalogNum, weight, manufacturer, temperature, minQuantity, categoryName);
        Item _item = new Item(name, catalogNum, weight, manufacturer, temperature, categoryName);
        ItemList.add(_item);
        return _item;
    }

    public void removeItem(String itemName){
        for (int i = 0; i < this.ItemList.size(); i++) {
            if (this.ItemList.get(i).getName() == itemName)
                ItemList.remove(ItemList.get(i));
        }
    }

}
