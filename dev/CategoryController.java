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

    public void addCategory(String categoryName) {
        Category _Category = new Category(categoryName);
        CategoriesList.add(_Category);
        amount ++;
    }

    public void removeCategory(String categoryName){
        for (int i = 0; i < this.CategoriesList.size(); i++) {
            if (this.CategoriesList.get(i).getCategoryName() == categoryName)
                CategoriesList.remove(CategoriesList.get(i));
        }
        amount --;
    }

    public void addSubCategory(Category _Category,subCategory subCategory)
    {
        for (int i = 0; i < this.CategoriesList.size(); i++){
            if (_Category.getCategoryName() == this.CategoriesList.get(i).getCategoryName()){
                this.CategoriesList.get(i).addSubCategory(subCategory);
            }
        }
        //_Category.addSubCategory(subCategory);
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
