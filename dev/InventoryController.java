import java.util.*;

public class InventoryController {
    private CategoryController CategoryControl;
    private List<Item> ItemsList;
    private LinkedHashMap<Item, List<specificItem>> specificItemsMap;

    public InventoryController() {
        this.CategoryControl = new CategoryController();
        this.ItemsList = new ArrayList<Item>();
        this.specificItemsMap = new LinkedHashMap<Item, List<specificItem>>();
    }

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
        for (int i = 0; i < ItemsList.size(); i++){
            if (ItemsList.get(i).getCatalogNum().equals(itemCatalogNumber)){
                currentItem = ItemsList.get(i);
                return currentItem;
            }
        }
        return null;
    }
    //Method: deleteSpecificItem
    //This method deleted a specific item from the entire inventory
    public boolean deleteSpecificItem(int serialNumber){

        Item currentItem;
        specificItem currentSpecificItem;
        for (int i = 0; i < ItemsList.size(); i++){
            currentItem = ItemsList.get(i);
            for (int j = 0; j < currentItem.getAmount(); j++){
                currentSpecificItem = currentItem.getSpecificItemList(j);
                if (currentSpecificItem.getserialNumber() == serialNumber){
                    currentItem.removeSpecificItem(currentSpecificItem);
                    return true;
                }
            }
        }
        return false;
    }

    //Method: deleteGeneralItem
    //This method deletes a general item and all of it's specific items from the inventory
    public boolean deleteGeneralItem(String catalogNumber){
        Category currentCategory;
        subCategory currentSubCat;
        Item currentItem;
        specificItem currentSpecificItem;

        for (int i = 0; i < CategoryControl.getCategoriesList().size(); i++){
            currentCategory = CategoryControl.getCategoriesList().get(i);
            for (int j = 0; j < currentCategory.getAmount(); j++){
                currentSubCat = currentCategory.getSubCategory(j);
                for (int w = 0; w < currentSubCat.getGeneralItemsList().size(); w++){
                    currentItem = currentSubCat.getGeneralItemsList().get(w);
                    if (currentItem.getCatalogNum().equals(catalogNumber)){
                        currentSubCat.getGeneralItemsList().remove(currentItem);
                        ItemsList.remove(currentItem);
                        currentSubCat.setAmount(currentSubCat.getAmount() - 1);
                        return true;
                    }
                }
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

    public void addGeneralItem(Item generalItem) {
        ItemsList.add(generalItem);
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
        int specificAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            List<specificItem> values = entry.getValue();
            specificAmount = values.size();
            if (specificAmount < key.getMinQuantity()){
                currentReport.addReportItem(key, key.getMinQuantity() - specificAmount);
            }
            specificAmount = 0;
        }

        return currentReport;
    }

    //Method 5: shortageReportCategory
    //This method provides a report for all the products that need to be ordered by a specific category
    public Report shortageReportCategory(String categoryName){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        int specificAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            if (!key.getCatalogName().equals(categoryName)){
                continue;
            }
            List<specificItem> values = entry.getValue();
            specificAmount = values.size();
            if (specificAmount < key.getMinQuantity()){
                currentReport.addReportItem(key, key.getMinQuantity() - specificAmount);
            }
            specificAmount = 0;
        }
        return currentReport;
    }

    //Method 6: shortageReportGeneralItem
    //This method provides a shortage report for a general item
    public Report shortageReportGeneralItem(String catalogNumber){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        int specificAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            if (!key.getCatalogNum().equals(catalogNumber)){
                continue;
            }
            List<specificItem> values = entry.getValue();
            specificAmount = values.size();
            if (specificAmount < key.getMinQuantity()){
                currentReport.addReportItem(key, key.getMinQuantity() - specificAmount);
            }
            specificAmount = 0;
        }
        return currentReport;
    }

    public Report priceHistoryReport(String catalogNumber){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.PriceHistory, currentDate);
        String reportInformationString = "";

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            if (!key.getCatalogNum().equals(catalogNumber)){
                continue;
            }
            for (int i = 0; i < key.getPriceHistorySize(); i++){
                reportInformationString += key.getPriceHistorySpecific(i);
            }
        }
        currentReport.setReportData(reportInformationString);
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
        int specificAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            List<specificItem> values = entry.getValue();
            specificAmount = values.size();
            currentReport.addReportItem(key, specificAmount);
            specificAmount = 0;
        }

        return currentReport;
    }

    public Report CategoryCountingReport(String categoryName) {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        int specificAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            if (!key.getCatalogName().equals(categoryName)){
                continue;
            }
            List<specificItem> values = entry.getValue();
            specificAmount = values.size();
            currentReport.addReportItem(key, specificAmount);
            specificAmount = 0;
        }
        return currentReport;
    }


    public Report ItemCountingReport(String catalogNumber)
    {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        int specificAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            if (!key.getCatalogNum().equals(catalogNumber)){
                continue;
            }
            List<specificItem> values = entry.getValue();
            specificAmount = values.size();
            currentReport.addReportItem(key, specificAmount);
            specificAmount = 0;
        }
        return currentReport;
    }

    public Report FullDefectiveReport()
    {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        int defectiveAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            List<specificItem> values = entry.getValue();
            for (specificItem value : values) {
                if (value.getisDefected()){
                    defectiveAmount++;
                }
            }
            currentReport.addReportItem(key, defectiveAmount);
            defectiveAmount = 0;
        }

        return currentReport;
    }

    public Report CategoryDefectiveReport(String categoryName) {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        int defectiveAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            if (!key.getCatalogName().equals(categoryName)){
                continue;
            }
            List<specificItem> values = entry.getValue();
            for (specificItem value : values) {
                if (value.getisDefected()){
                    defectiveAmount++;
                }
            }
            currentReport.addReportItem(key, defectiveAmount);
            defectiveAmount = 0;
        }
        return currentReport;
    }

    public Report ItemDefectiveReport(String CatalogNum)
    {
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        int defectiveAmount = 0;

        for (Map.Entry<Item, List<specificItem>> entry : specificItemsMap.entrySet()) {
            Item key = entry.getKey();
            if (!key.getCatalogNum().equals(CatalogNum)){
                continue;
            }
            List<specificItem> values = entry.getValue();
            for (specificItem value : values) {
                if (value.getisDefected()){
                    defectiveAmount++;
                }
            }
            currentReport.addReportItem(key, defectiveAmount);
            defectiveAmount = 0;
        }
        return currentReport;
    }

    public void FullStandardDiscount(double _amount)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = this.ItemsList.get(i);
            currentItem.getDiscount().setStandardDiscount(_amount);
            currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - _amount);
        }
    }

    public void FullPercentageDiscount(double _amount)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = this.ItemsList.get(i);
            currentItem.getDiscount().setPercentageDiscount(_amount);
            currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - ((currentItem.getSellPrice() * (_amount /100 ))));

        }
    }

    public void CategoryPercentageDiscount(double _amount, String _CategoryName)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = this.ItemsList.get(i);
            if (currentItem.getCategoryName().equals(_CategoryName)){
                currentItem.getDiscount().setPercentageDiscount(_amount);
                currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - ((currentItem.getSellPrice() * (_amount /100 ))));

            }
        }
    }

    public void CategoryStandardDiscount(double _amount, String _CategoryName)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = this.ItemsList.get(i);
            if (currentItem.getCategoryName().equals(_CategoryName)){
                currentItem.getDiscount().setStandardDiscount(_amount);
                currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - _amount);
            }
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
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = this.ItemsList.get(i);
            if (currentItem.getCatalogNum().equals(_CatalogNum)){
                currentItem.getDiscount().setStandardDiscount(_amount);
                currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - _amount);
            }
        }
    }

    public void SpecificPercentageDiscount(double _amount, String _CatalogNum)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return;
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = this.ItemsList.get(i);
            if (currentItem.getCatalogNum().equals(_CatalogNum)){
                currentItem.getDiscount().setPercentageDiscount(_amount);
                currentItem.addNewPrice(currentItem.getBuyPrice(),currentItem.getSellPrice() - ((currentItem.getSellPrice() * (_amount /100 ))));
            }
        }
    }

    public void addSpecificItem(Item key, specificItem value) {
        if (specificItemsMap.containsKey(key)) {
            specificItemsMap.get(key).add(value);
        } else {
            specificItemsMap.put(key, new ArrayList<>(Arrays.asList(value)));
        }
    }



}
