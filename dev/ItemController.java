import java.util.ArrayList;
import java.util.List;

public class ItemController {
    private List<Item> ItemList;

    public ItemController(List<Item> itemList) {
        this.ItemList = new ArrayList<Item>();
    }

    public Item addItem(String name, String catalogNum, double weight, String manufacturer, TempLevel temperature, int minQuantity)
    {
        Item _item = new Item(name, catalogNum, weight, manufacturer, temperature, minQuantity);
        ItemList.add(_item);
        return _item;
    }

    public void removeItem(String itemName){
        for (int i = 0; i < this.ItemList.size(); i++) {
            if (this.ItemList.get(i).getName() == itemName)
                ItemList.remove(ItemList.get(i));
        }
    }

    public boolean changeItemCategory(String catalogNum, String categoryName) {
        for (int i = 0; i < this.ItemList.size(); i++) {
            if (this.ItemList.get(i).getCatalogNum() == catalogNum)
                ItemList.get(i).setCategoryName(categoryName);
        }
        return true;
    }

}
