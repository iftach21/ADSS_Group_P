package Domain.Transfer;

import Domain.Enums.TempLevel;

public class Item_mock {

    private String _itemName;
    private TempLevel _temp;

    public Item_mock(TempLevel temp,String itemName){
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
}
