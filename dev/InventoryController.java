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
        Report currentReport = new Report(reportType.Shortage, currentDate);
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
        Report currentReport = new Report(reportType.Shortage, currentDate);
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
        Report currentReport = new Report(reportType.Shortage, currentDate);
        for (int i = 0; i < this.ItemsList.size(); i++)
        {
            Item CurrentItem = this.ItemsList.get(i);
            currentReport.setReportData(CurrentItem.toString());
        }
        return currentReport;
    }

    public Report FullDefectiveReport() {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
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


}
