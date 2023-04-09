import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryController {
    private CategoryController CategoryControl;
    private List<Item> ItemsList;

    public InventoryController() {
        this.CategoryControl = new CategoryController();
        this.ItemsList = new ArrayList<Item>();
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
    public boolean deleteSpecificItem(int itemID){
        Item currentItem;
        specificItem currentSpecificItem;
        for (int i = 0; i < ItemsList.size(); i++){
            currentItem = ItemsList.get(i);
            for (int j = 0; j < currentItem.getAmount(); j++){
                currentSpecificItem = currentItem.getSpecificItemList(j);
                if (currentSpecificItem.getItemID() == itemID){
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
        String reportInformation = "";
        int defectedCount = 0;
        //Iterate every category
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++){
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            //Iterate every sub-category
            for (int j = 0; j < currentCategory.getAmount(); j++){
                subCategory currentSubCategory = currentCategory.getSubCategory(j);
                //Iterate every general-item
                for (int w = 0; w < currentSubCategory.getAmount(); w++){
                    Item currentItem = currentSubCategory.getItem(w);
                    //Check for each specific item if it is defected, and needs to be added to the order
                    for (int z = 0; z < currentItem.getAmount(); z++){
                        if (currentItem.getSpecificItemList(z).getisDefected()){
                            defectedCount++;
                        }
                    }
                    if (currentItem.getAmount() < currentItem.getMinQuantity() + defectedCount){
                        //Add the information collected to the report data
                        reportInformation += currentItem.toString() + "\n" +
                                " Defected amount: " + defectedCount + " Total to order: " +
                                (currentItem.getMinQuantity() - currentItem.getAmount() + defectedCount) + "\n";
                        currentReport.setReportData(reportInformation);
                        //Reset variables
                        reportInformation = "";
                        defectedCount = 0;
                    }
                }
            }
        }
        //If there are no shortages
        if (currentReport.getReportInformation().equals("")){
            currentReport.setReportData("There are no shortages.");
        }
        return currentReport;
    }

    //Method 5: shortageReportCategory
    //This method provides a report for all the products that need to be ordered by a specific category
    public Report shortageReportCategory(String categoryName){
        //Set variables for method
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        Category currentCategory = this.CategoryControl.getCategory(categoryName);
        String reportInformation = "";
        int defectedCount = 0;

        for (int j = 0; j < currentCategory.getAmount(); j++){
            subCategory currentSubCategory = currentCategory.getSubCategory(j);
            //Iterate every general-item
            for (int w = 0; w < currentSubCategory.getAmount(); w++){
                Item currentItem = currentSubCategory.getItem(w);
                //Check for each specific item if it is defected, and needs to be added to the order
                for (int z = 0; z < currentItem.getAmount(); z++){
                    if (currentItem.getSpecificItemList(z).getisDefected()){
                        defectedCount++;
                    }
                }
                if (currentItem.getAmount() < currentItem.getMinQuantity() + defectedCount){
                    //Add the information collected to the report data
                    reportInformation += currentItem.toString() + "\n" +
                            " Defected amount: " + defectedCount + " Total to order: " +
                            (currentItem.getMinQuantity() - currentItem.getAmount() + defectedCount) + "\n";
                    currentReport.setReportData(reportInformation);
                    //Reset variables
                    reportInformation = "";
                    defectedCount = 0;
                }
            }
        }
        //If there are no shortages
        if (currentReport.getReportInformation().equals("")){
            currentReport.setReportData("There are no shortages.");
        }
        return currentReport;
    }

    //Method 6: shortageReportGeneralItem
    //This method provides a shortage report for a general item
    public Report shortageReportGeneralItem(String catalogNumber){
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        Item currentItem;
        String reportInformation = "";
        int defectedCount = 0;
        for (int i = 0; i < this.ItemsList.size(); i++){
            currentItem = this.ItemsList.get(i);
            for (int z = 0; z < currentItem.getAmount(); z++){
                if (currentItem.getSpecificItemList(z).getisDefected()){
                    defectedCount++;
                }
            }
            if (currentItem.getCatalogNum().equals(catalogNumber) &&
                    (currentItem.getAmount() < currentItem.getMinQuantity() + defectedCount)){
                //Add the information collected to the report data
                reportInformation += currentItem.toString() + "\n" +
                        " Defected amount: " + defectedCount + " Total to order: " +
                        (currentItem.getMinQuantity() - currentItem.getAmount() + defectedCount) + "\n";
                currentReport.setReportData(reportInformation);
                //Reset variables
                reportInformation = "";
                defectedCount = 0;
            }

        }
        //If there are no shortages
        if (currentReport.getReportInformation().equals("")){
            currentReport.setReportData("There are no shortages.");
        }
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
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++){
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            categoryController += currentCategory.toString();
        }
        return categoryController;
    }
    public Report FullCountingReport() {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++) {
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            for (int j = 0; j < currentCategory.getAmount(); j++) {
                subCategory currentSubCategory = currentCategory.getSubCategory(j);
                for (int w = 0; w < currentSubCategory.getAmount(); w++) {
                    Item currentItem = currentSubCategory.getItem(w);
                    currentReport.setReportData(currentItem.toString());
                }
            }
        }
        return currentReport;
    }

    public Report CategoryCountingReport(String _CategoryName) {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++)
        {
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            if (currentCategory.getCategoryName().equals(_CategoryName))
            {
                for (int j = 0; j < currentCategory.getAmount(); j++){
                    subCategory currentSubCategory = currentCategory.getSubCategory(j);
                    for (int w = 0; w < currentSubCategory.getAmount(); w++)
                    {
                        Item currentItem = currentSubCategory.getItem(w);
                        currentReport.setReportData(currentItem.toString());
                    }
                }
            }
            else
            {
                System. out. println("There is no such category");
                return null;
            }
        }
        return currentReport;
    }


    public Report ItemCountingReport(String CatlogNum)
    {
        if (this.ItemsList.size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Inventory, currentDate);
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item CurrentItem = this.ItemsList.get(i);
            if (CurrentItem.getCatalogNum().equals(CatlogNum))
                currentReport.setReportData(CurrentItem.toString());
        }
        return currentReport;
    }

    public Report FullDefectiveReport()
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Defective, currentDate);
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = this.ItemsList.get(i);
            for (int j = 0; j < currentItem.getAmount(); j++)
            {
                specificItem currentSpecificItem = currentItem.getSpecificItemList(j);
                if (currentSpecificItem.getisDefected())
                {
                    //TODO - check if need to do diffrent func for that
//                    currentSpecificItem.setLocation(Location.DefctiveArea);
                    currentReport.setReportData(currentSpecificItem.toString());
                }
            }
        }
        return currentReport;
    }

    public Report CategoryDefectiveReport(String _CategoryName) {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Defective, currentDate);
        boolean flag = false;
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++)
        {
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            if (currentCategory.getCategoryName().equals(_CategoryName))
            {
                flag = true;
                for (int j = 0; j < currentCategory.getAmount(); j++){
                    subCategory currentSubCategory = currentCategory.getSubCategory(j);
                    for (int w = 0; w < currentSubCategory.getAmount(); w++)
                    {
                        Item currentItem = currentSubCategory.getItem(w);
                        for (int k = 0; k < currentItem.getAmount(); k++)
                        {
                            specificItem currentSpecificItem = currentItem.getSpecificItemList(k);
                            if (currentSpecificItem.getisDefected())
                            {
                                //TODO - check if need to do diffrent func for that
//                                currentSpecificItem.setLocation(Location.DefctiveArea);
                                currentReport.setReportData(currentSpecificItem.toString());
                            }
                        }
                    }
                }
            }
        }
        if (!flag)
        {
            System.out.println("There no such category");
            return null;
        }
        return currentReport;
    }

    public Report ItemDefectiveReport(String CatalogNum)
    {
        if (this.ItemsList.size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Defective, currentDate);
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item currentItem = ItemsList.get(i);
            if (currentItem.getCatalogNum().equals(CatalogNum))
            {
                for (int j = 0; j < currentItem.getAmount(); j++) {

                    specificItem currentSpecificItem = currentItem.getSpecificItemList(j);
                    if (currentSpecificItem.getisDefected()) {
                        //TODO - check if need to do diffrent func for that
                        //                                currentSpecificItem.setLocation(Location.DefctiveArea);
                        currentReport.setReportData(currentSpecificItem.toString());
                    }
                }
            }
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
        }
    }



}
