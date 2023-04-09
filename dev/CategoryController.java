import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class CategoryController {
    int amount;
    private List<Category> CategoriesList;

    //public CategoryController(List<subCategory> subCategoriesList)
    public CategoryController()
    {
        this.CategoriesList = new ArrayList<Category>();
    }

    public int getAmount() {
        return amount;
    }

    //getCategory
    //This getter recieves a category's name from the user (String), and returns that same category
    public Category getCategory(String categoryName) {
        Category currentCategory;
        for (int i = 0; i < this.amount; i++){
            currentCategory = this.CategoriesList.get(i);
            if (currentCategory.getCategoryName().equals(categoryName)){
                return currentCategory;
            }
        }
        return null;
    }

    //Method 1: addCategory
    //This method recieves a string, and creates a new super category to be added to the store's inventory
    public void addCategory(String categoryName) {
        Category _Category = new Category(categoryName);
        CategoriesList.add(_Category);
        amount ++;
    }

    //Method 2: removeCategory
    //This method recieves a category's name, and deletes it from the store's inventory
    public void removeCategory(String categoryName){
        for (int i = 0; i < this.CategoriesList.size(); i++) {
            if (this.CategoriesList.get(i).getCategoryName() == categoryName)
                CategoriesList.remove(CategoriesList.get(i));
        }
        amount --;
    }

    //Method 3: addSubCategory
    //This method adds a new sub category into a super category's list
    public void addSubCategory(Category _Category,subCategory subCategory)
    {
        for (int i = 0; i < this.CategoriesList.size(); i++){
            if (_Category.getCategoryName() == this.CategoriesList.get(i).getCategoryName()){
                this.CategoriesList.get(i).addSubCategory(subCategory);
            }
        }
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
        for (int i = 0; i < this.CategoriesList.size(); i++){
            Category currentCategory = this.CategoriesList.get(i);
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
        String reportInformation = "";
        int defectedCount = 0;

        return currentReport;
    }

    @Override
    public String toString() {
        String categoryController = "Inventory - Amount of categories: " + amount + '\n';
        for (int i = 0; i < this.CategoriesList.size(); i++){
            Category currentCategory = this.CategoriesList.get(i);
            categoryController += currentCategory.toString();
        }
        return categoryController;
    }
}
