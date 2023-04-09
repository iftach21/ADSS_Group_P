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
    
    //Method 4: shortageFullCategory
    //This method provides a report for all the product that need to be ordered
    public Report shortageReportCategory(String categoryName){
        Date currentDate = new Date();
        Report currentReport = new Report(reportType.Shortage, currentDate);
        if (categoryName == "FULL"){
            for (int i = 0; i < this.CategoriesList.size(); i++){
                Category currentCategory = this.CategoriesList.get(i);
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
            for (int i = 0; i < this.CategoriesList.size(); i++){
                Category currentCategory = this.CategoriesList.get(i);
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
        String categoryController = "Inventory - Amount of categories: " + amount + '\n';
        for (int i = 0; i < this.CategoriesList.size(); i++){
            Category currentCategory = this.CategoriesList.get(i);
            categoryController += currentCategory.toString();
        }
        return categoryController;
    }
}
