package Domain;

import DataAccesObject.*;

import java.sql.SQLException;
import java.util.*;

public class InventoryController {
    private CategoryController CategoryControl;
    private List<Item> ItemsList;
    private LinkedHashMap<Item, List<specificItem>> specificItemsMap;
    private subCategoryMapper subCategoryMapper;
    private ItemMapper itemMapper;
    private DataAccesObject.specificItemMapper specificItemMapper;
    private ReportMapper reportMapper;
    private ReportItemsMapper reportItemsMapper;
    private OrderManger orderManger;
    private Supplier_Manger supplierManger;
    private static final long DELAY = (0);
    private static final long PERIOD = 1000*10;

    public InventoryController() {
        this.CategoryControl = new CategoryController();
        this.ItemsList = new ArrayList<Item>();
        this.specificItemsMap = new LinkedHashMap<Item, List<specificItem>>();
        this.subCategoryMapper = new subCategoryMapper();
        this.itemMapper = new ItemMapper();
        this.specificItemMapper = new specificItemMapper();
        this.reportMapper = new ReportMapper();
        this.reportItemsMapper = new ReportItemsMapper();
        this.orderManger = new OrderManger();
        this.supplierManger= new Supplier_Manger();
    }

    //Category Mapper
    public void addCategoryToMapper(String categoryName){
        CategoryControl.addCategory(categoryName);
    }

    public void removeCategoryFromMapper(String categoryName){
        CategoryControl.removeCategory(categoryName);
    }

    //Sub category mapper
    public void addSubCatToMapper(String categoryName, subCategory currentSub){
        subCategoryMapper.insertSubCategory(categoryName, currentSub);
    }

    public subCategory getSubCategory(String subcategoryName){
        subCategory subCategory = null;
        subCategory = subCategoryMapper.getByID(subcategoryName);;
        return subCategory;
    }


    //Items Mapper
    public void printAllItems(){
        for(Item item: itemMapper.findAll())
        {
            System.out.println(item);
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                System.out.println(specificItem);
            }
        }
    }

    public Item findItemByCatalogNum(String catalogNum){
        Item currentItem = itemMapper.findByCatalogNum(catalogNum);
        return currentItem;
    }
    /*

     */
    public void insertNewItemToMapper(Item currentItem) {itemMapper.insert(currentItem);}
    /*

     */
    public void deleteItemFromMapper(Item currentItem) {itemMapper.delete(currentItem);}


    //Specific Items Mapper
    /*
    insertNewSpecificToMapper
    This function recieves a specificItem, and enters it into the specificItemsMapper.
     */
    public void insertNewSpecificToMapper(specificItem currentSpecific){
        specificItemMapper.insert(currentSpecific);
    }
    /*

     */
    public void deleteSpecificFromMapper(int currentSpecific) {specificItemMapper.deleteBySerialNumber(currentSpecific);}
    /*

     */
    public specificItem findSpecificItemBySerialNumber(int serialNumber){
        String stringSerialNumber = String.valueOf(serialNumber);
        specificItem currentSpecific = specificItemMapper.findSpecificItemBySerial(stringSerialNumber);
        return currentSpecific;
    }


    public void moveSpecificItemToDefectiveMapper(int serialNumber) {
        List<specificItem> itemList = specificItemMapper.findAll();
        if (itemList != null) {
            Iterator<specificItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                specificItem specificItem = iterator.next();
                if (specificItem.getserialNumber() == serialNumber) {
                    specificItem.setDefected(true);
                    specificItem.setLocation(Location.Storage);
                    specificItemMapper.delete(specificItem);
                    specificItemMapper.insert(specificItem);
                }
            }
        }
    }

    public void moveSpecificItemMapper(int serialNumber){
        List<specificItem> itemList = specificItemMapper.findAll();
        if (itemList != null) {
            Iterator<specificItem> iterator = itemList.iterator();
            while (iterator.hasNext()) {
                specificItem specificItem = iterator.next();
                if (specificItem.getserialNumber() == serialNumber) {
                    if (specificItem.getLocationString().equals("store")){
                        specificItem.setLocation(Location.Storage);
                    }
                    else if(specificItem.getLocationString().equals( "storage")) {
                        specificItem.setLocation(Location.Store);
                    }
                    specificItemMapper.delete(specificItem);
                    specificItemMapper.insert(specificItem);
                }
            }
        }
    }



//    public void moveSpecificItemToDefectiveMapper(int serialNumber) {
//        List<specificItem> itemList = specificItemMapper.findAll();
//        if (itemList != null) {
//            Iterator<specificItem> iterator = itemList.iterator();
//            while (iterator.hasNext()) {
//                specificItem specificItem = iterator.next();
//                if (specificItem.getserialNumber() == serialNumber) {
//                    specificItem.setDefected(true);
//                    specificItem.setLocation(Location.Storage);
//                    specificItemMapper.delete(specificItem);
//                    specificItemMapper.insert(specificItem);
//                }
//            }
//        }
//    }


    //END M

    public specificItem findSpecificItem(int serialNumber){
        specificItem currentSpecificItem;
        Item currentItem;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            List<specificItem> values = entry.getValue();
            for (specificItem value : values) {
                if (value.getserialNumber() == serialNumber){
                    return value;
                }
            }
        }

        return null;
    }

    public Item getItemByCatalogNumber(String itemCatalogNumber) {
        Item currentItem;
        currentItem = itemMapper.findByCatalogNum(itemCatalogNumber);
        if (currentItem == null) {
            return null;
        }
        return currentItem;
    }


    //Method: deleteSpecificItem
    //This method deleted a specific item from the entire inventory
    public boolean deleteSpecificItem(int serialNumber){

        for (List<specificItem> specificItemList : specificItemsMap.values()) {
            Iterator<specificItem> iter = specificItemList.iterator();
            while (iter.hasNext()) {
                specificItem item = iter.next();
                if (item.getserialNumber() == serialNumber) {
                    iter.remove();
                    return true;
                }
            }
        }
        return false;
    }

    //Method: deleteGeneralItem
    //This method deletes a general item and all of it's specific items from the inventory
    public boolean deleteGeneralItem(String catalogNumber){

        Iterator<Map.Entry<Item, List<specificItem>>> iter = specificItemsMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Item, List<specificItem>> entry = iter.next();
            Item key = entry.getKey();
            if (key.getCatalogNum().equals(catalogNumber)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public boolean deleteSubCat(String subCategoryName){
        Category currentCategory;
        subCategory currentSubCat;

        for (int i = 0; i < CategoryControl.getCategoriesList().size(); i++){
            currentCategory = CategoryControl.getCategoriesList().get(i);
            for (int j = 0; j < currentCategory.getAmount(); j++){
                currentSubCat = currentCategory.getSubCategory(j);
                if (currentSubCat.getSubCategoryName().equals(subCategoryName)){
                    currentCategory.removeSubCategory(currentSubCat);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteCat(String categoryName){
        Category currentCategory;

        Iterator<Map.Entry<Item, List<specificItem>>> iter = specificItemsMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Item, List<specificItem>> entry = iter.next();
            Item key = entry.getKey();
            if (key.getCatalogName().equals(categoryName)) {
                iter.remove();
            }
        }

        for (int i = 0; i < CategoryControl.getCategoriesList().size(); i++){
            currentCategory = CategoryControl.getCategoriesList().get(i);
            if (currentCategory.getCategoryName().equals(categoryName)){
                CategoryControl.getCategoriesList().remove(currentCategory);
                CategoryControl.setAmount(CategoryControl.getAmount() - 1);
                return true;
            }
        }
        return false;
    }

    public void addGeneralItem(Item key) {
        specificItemsMap.put(key, new ArrayList<>());
    }

    public CategoryController getCategoryControl() {
        return CategoryControl;
    }

    //Method 4: shortageReportFull
    //This method provides a report for all the products that need to be ordered
    public Report shortageReportFull(){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        boolean IsEmpty = true;
        int specificAmount = 0;
        for(Item item: itemMapper.findAll())
        {

            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                if (!specificItem.getisDefected())
                    specificAmount++;
            }
            if (specificAmount < item.getMinQuantity()){
                currentReport.addReportItem(item, item.getMinQuantity() - specificAmount);
                IsEmpty = false;
                specificAmount = 0;
            }
        }
        if (IsEmpty)
            return null;
        return currentReport;
    }

    //Method 5: shortageReportCategory
    //This method provides a report for all the products that need to be ordered by a specific category
    public Report shortageReportCategory(String categoryName){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        boolean IsEmpty = true;
        int specificAmount = 0;
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogName().equals(categoryName)){
                continue;
            }

            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                if (!specificItem.getisDefected())
                    specificAmount++;
            }
            if (specificAmount < item.getMinQuantity()){
                currentReport.addReportItem(item, item.getMinQuantity() - specificAmount);
                IsEmpty = false;
                specificAmount = 0;
            }
        }
        if (IsEmpty)
            return null;
        return currentReport;
    }

    //Method 6: shortageReportGeneralItem
    //This method provides a shortage report for a general item
    public Report shortageReportGeneralItem(String catalogNumber){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        boolean IsEmpty = true;
        int specificAmount = 0;
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogNum().equals(catalogNumber)){
                continue;
            }

            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                if (!specificItem.getisDefected())
                    specificAmount++;
            }
            if (specificAmount < item.getMinQuantity()){
                currentReport.addReportItem(item, item.getMinQuantity() - specificAmount);
                IsEmpty = false;
                specificAmount = 0;
            }
        }
        if (IsEmpty)
            return null;
        return currentReport;
    }

    public Report priceHistoryReport(String catalogNumber){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.PriceHistory, currentDate);
        String reportInformationString = "";
        boolean IsEmpty = true;
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogNum().equals(catalogNumber)){
                continue;
            }
            reportInformationString += item.getPriceHistory().toString();
            IsEmpty = false;
        }
        currentReport.setReportData(reportInformationString);
        if (IsEmpty)
            return null;
        return currentReport;
    }

    //Method 7: returnTempLevel
    //This method returns a TempLevel given the user's chouse
    public TempLevel returnTempLevel(String userInput){
        if (!userInput.equals("A") && !userInput.equals("B") && !userInput.equals("C")){
            return null;
        } else if (userInput.equals("A")) {
            return TempLevel.regular;
        } else if (userInput.equals("B")) {
            return TempLevel.cold;
        }
        else {
            return TempLevel.frozen;
        }
    }

    @Override
    public String toString() {
        String categoryController = "Inventory - Amount of categories: " + CategoryControl.getAmount() + '\n';
        Category previousCategory = null;
        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            String itemCategory = key.getCatalogName();
            Category currentCategory = this.CategoryControl.getCategory(itemCategory);
            List<specificItem> values = entry.getValue();
            if (currentCategory != previousCategory) {
                categoryController += currentCategory + ": " + '\n';
                previousCategory = currentCategory;
            }
            categoryController += key + ": " + '\n';
            categoryController += "Buy price: " + key.getBuyPrice() + " Sell price: " + key.getSellPrice() + '\n';
            for (specificItem value : values) {
                categoryController += "\t" + value + "\n";
            }
        }
        return categoryController;
    }
    public Report FullCountingReport() {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        boolean isEmpty = true;
        for(Item item: itemMapper.findAll())
        {
            int specificAmount = 0;
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                isEmpty = false;
                specificAmount++;
            }
            currentReport.addReportItem(item, specificAmount);
        }
        if (isEmpty)
            return null;
        return currentReport;
    }

    public Report CategoryCountingReport(String categoryName) {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        boolean isEmpty = true;
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogName().equals(categoryName)){
                continue;
            }
            isEmpty = false;
            int specificAmount = 0;
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                specificAmount++;
            }
            currentReport.addReportItem(item, specificAmount);
        }
        if (isEmpty)
            return null;
        return currentReport;
    }


    public Report ItemCountingReport(String catalogNumber)
    {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        boolean isEmpty = true;
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogNum().equals(catalogNumber)){
                continue;
            }
            isEmpty = false;
            int specificAmount = 0;
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                specificAmount++;
            }
            currentReport.addReportItem(item, specificAmount);
        }
        if (isEmpty)
            return null;
        return currentReport;
    }

    public Report FullDefectiveReport()
    {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Defective, currentDate);
        boolean isEmpty = true;
        for(Item item: itemMapper.findAll())
        {
            int defectiveAmount = 0;
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                if (specificItem.getisDefected()){
                    isEmpty = false;
                    defectiveAmount++;
                }
            }
            currentReport.addReportItem(item, defectiveAmount);

        }
        try {
            this.reportMapper.insert(currentReport);
        } catch (SQLException var8) {
            throw new RuntimeException(var8);
        }
        if (isEmpty)
            return null;
        return currentReport;
    }

    public Report CategoryDefectiveReport(String categoryName) {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Defective, currentDate);
        boolean isEmpty = true;
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogName().equals(categoryName)){
                continue;
            }
            isEmpty = false;
            int defectiveAmount = 0;
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                if (specificItem.getisDefected()){
                    defectiveAmount++;
                }
            }
            currentReport.addReportItem(item, defectiveAmount);
        }
        if (isEmpty)
            return null;
        return currentReport;
    }

    public Report ItemDefectiveReport(String CatalogNum)
    {
        //Set variables for method
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Defective, currentDate);
        boolean isEmpty = true;
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogNum().equals(CatalogNum)){
                continue;
            }
            isEmpty = false;
            int defectiveAmount = 0;
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                if (specificItem.getisDefected()){
                    defectiveAmount++;
                }
            }
            currentReport.addReportItem(item, defectiveAmount);
        }
        if (isEmpty)
            return null;
        return currentReport;
    }

    public void FullStandardDiscount(double _amount)
    {
        for(Item item: itemMapper.findAll())
        {
            double newPrice = item.getSellPrice() - _amount;
            item.addNewPrice(item.getBuyPrice(), newPrice);
            //System.out.println(item.getPriceHistory().toString());
            itemMapper.update(item);
        }
    }

    public void FullPercentageDiscount(double _amount)
    {
        for(Item item: itemMapper.findAll())
        {
            double newPrice = item.getSellPrice() - (item.getSellPrice()) * (_amount / 100);
            item.addNewPrice(item.getBuyPrice(), newPrice);
            itemMapper.update(item);
        }
    }

    public void CategoryPercentageDiscount(double _amount, String _CategoryName)
    {
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogName().equals(_CategoryName)){
                continue;
            }
            double newPrice = item.getSellPrice() - (item.getSellPrice()) * (_amount / 100);
            item.addNewPrice(item.getBuyPrice(), newPrice);
            itemMapper.update(item);
        }
    }

    public void CategoryStandardDiscount(double _amount, String _CategoryName)
    {
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogName().equals(_CategoryName)){
                continue;
            }
            double newPrice = item.getSellPrice() - _amount;
            item.addNewPrice(item.getBuyPrice(), newPrice);
            itemMapper.update(item);
        }

    }

    public void SubCategoryStandardDiscount(double _amount, String _CategoryName, String _Subcategory)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++)
        {
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            if (currentCategory.getCategoryName().equals(_CategoryName))
            {
                for (int j = 0; j < currentCategory.getAmount(); j++) {
                    subCategory currentSubCategory = currentCategory.getSubCategory(j);
                    if (currentSubCategory.getSubCategoryName().equals(_Subcategory)) {
                        for (int w = 0; w < currentSubCategory.getAmount(); w++) {
                            Item currentItem = currentSubCategory.getItem(w);
                            currentItem.getDiscount().setStandardDiscount(_amount);
                            currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - _amount);
                        }
                    }
                }
            }
        }
    }

    public void SubCategoryPercentageDiscount(double _amount, String _CategoryName, String _Subcategory)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++)
        {
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            if (currentCategory.getCategoryName().equals(_CategoryName))
            {
                for (int j = 0; j < currentCategory.getAmount(); j++) {
                    subCategory currentSubCategory = currentCategory.getSubCategory(j);
                    if (currentSubCategory.getSubCategoryName().equals(_Subcategory)) {
                        for (int w = 0; w < currentSubCategory.getAmount(); w++) {
                            Item currentItem = currentSubCategory.getItem(w);
                            currentItem.getDiscount().setPercentageDiscount(_amount);
                            currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - ((currentItem.getSellPrice() * (_amount /100 ))));

                        }
                    }
                }
            }
        }
    }

    public void SpecificStandardDiscount(double _amount, String _CatalogNum)
    {
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogNum().equals(_CatalogNum)){
                continue;
            }
            double newPrice = item.getSellPrice() - _amount;
            item.addNewPrice(item.getBuyPrice(), newPrice);
            itemMapper.update(item);
        }
    }

    public void SpecificPercentageDiscount(double _amount, String _CatalogNum)
    {
        for(Item item: itemMapper.findAll())
        {
            if (!item.getCatalogNum().equals(_CatalogNum)){
                continue;
            }
            double newPrice = item.getSellPrice() - (item.getSellPrice()) * (_amount / 100);
            item.addNewPrice(item.getBuyPrice(), newPrice);
            itemMapper.update(item);
        }
    }

    public void addSpecificItem(Item key, specificItem value) {
        if (specificItemsMap.containsKey(key)) {
            specificItemsMap.get(key).add(value);
        } else {
            specificItemsMap.put(key, new ArrayList<>(Arrays.asList(value)));
        }
    }

    public void checkForShortageTask(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ShortageCheck();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }, DELAY, PERIOD);
    }

    private void ShortageCheck() throws SQLException{
        Map ShortageOrderList = new HashMap<Item,Integer>();
        for(Item item: itemMapper.findAll())
        {
            int specificAmount = 0;
            for(specificItem specificItem: specificItemMapper.findByCatalogNum(item.getCatalogNum()))
            {
                specificAmount++;
            }
            if (specificAmount < item.getMinQuantity()){
                ShortageOrderList.put(item, item.getMinQuantity() - specificAmount);
            }
        }
        orderManger.assing_Orders_to_Suppliers(ShortageOrderList,supplierManger,1);
    }

    public void delete_db(){
        try {
            reportMapper.deleteAll();
            specificItemMapper.deleteAll();
            reportItemsMapper.deleteAll();
            CategoryControl.deleteCatMapper();
            subCategoryMapper.deleteAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ItemMapper getItemMapper() {
        return itemMapper;
    }

    public DataAccesObject.specificItemMapper getSpecificItemMapper() {
        return specificItemMapper;
    }

    public int getItemsAmount(String catalogNumber){

        return specificItemMapper.getSpecificAmount(catalogNumber);
    }
}
