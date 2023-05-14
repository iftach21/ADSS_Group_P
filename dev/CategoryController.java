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

    public List<Category> getCategoriesList() {
        return CategoriesList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public void removeCateogry(String categoryName){
        Category _Category = getCategory(categoryName);
        CategoriesList.remove(_Category);
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
}
