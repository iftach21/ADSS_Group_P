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

    //Method 4: shortageFullCategory
    //This method provides a report for all the product that need to be ordered
    public Report shortageReportCategory(String categoryName){
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        if (categoryName == "FULL"){
            for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++){
                Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
                for (int j = 0; j < currentCategory.getAmount(); j++){
                    subCategory currentSubCategory = currentCategory.getSubCategory(j);
                    for (int w = 0; w < currentSubCategory.getAmount(); w++){
                        Item currentItem = currentSubCategory.getItem(w);
                        if (currentItem.getAmount() < currentItem.getMinQuantity()){
                            currentReport.setReportData(currentItem.toString());
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++){
                Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
                if (currentCategory.getCategoryName().equals(categoryName)){
                    for (int j = 0; j < currentCategory.getAmount(); j++){
                        subCategory currentSubCategory = currentCategory.getSubCategory(j);
                        for (int w = 0; w < currentSubCategory.getAmount(); w++){
                            Item currentItem = currentSubCategory.getItem(w);
                            if (currentItem.getAmount() < currentItem.getMinQuantity()){
                                currentReport.setReportData(currentItem.toString());
                            }
                        }
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


    public Report ItemCountingReport(String _CategoryName, String _SubCategoryName, String ItemName)
    {
        if (this.CategoryControl.getCategoriesList().size() == 0)
            return null;
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        for (int i = 0; i < this.CategoryControl.getCategoriesList().size(); i++) {
            Category currentCategory = this.CategoryControl.getCategoriesList().get(i);
            if (currentCategory.getCategoryName().equals(_CategoryName)) {
                for (int j = 0; j < currentCategory.getAmount(); j++) {
                    subCategory currentSubCategory = currentCategory.getSubCategory(j);
                    for (int w = 0; w < currentSubCategory.getAmount(); w++)
                    {
                        if (currentSubCategory.getItem(w).getName().equals(ItemName))
                        {
                            Item currentItem = currentSubCategory.getItem(w);
                            currentReport.setReportData(currentItem.toString());
                        }
                    }
                }
            }
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
