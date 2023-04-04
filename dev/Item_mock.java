public class Item_mock {

    private String _itemName;
    private TempLevel _temp;

    public Item_mock(TempLevel temp){
        this._temp = temp;
    }

    public TempLevel getItemTemp(){
        return this._temp;
    }

    public String getItemName()
    {
        return _itemName;
    }
}
