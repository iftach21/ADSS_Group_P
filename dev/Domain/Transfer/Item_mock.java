package Domain.Transfer;

import Domain.Enums.TempLevel;

public class Item_mock {

    private String _catalogNum;
    private String _itemName;
    private TempLevel _temp;

    public Item_mock(String catalogNum, TempLevel temp,String itemName){
        this._catalogNum = catalogNum;
        this._temp = temp;
        this._itemName = itemName;
    }

    public TempLevel getItemTemp(){
        return this._temp;
    }

    public String getItemName()
    {
        return _itemName;
    }

    public String getCatalogNum() {
        return _catalogNum;
    }

    public void updateItemTemp(TempLevel temp)
    {
        this._temp = temp;
    }

    public void updateItemName(String itemName)
    {
        this._itemName =itemName;
    }
}
