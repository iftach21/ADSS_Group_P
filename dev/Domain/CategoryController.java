package Domain;

import DataAccesObject.categoryMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    int amount;
    private List<Category> CategoriesList;

    public categoryMapper catMap;

    //public CategoryController(List<subCategory> subCategoriesList)
    public CategoryController()
    {
        this.CategoriesList = new ArrayList<Category>();
        this.catMap = new categoryMapper();
    }

    public List<Category> getCategoriesList() {
        try {
            CategoriesList = catMap.getAll();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
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
        Category currentCategory = null;
        try {
            currentCategory = catMap.getById(categoryName);;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return currentCategory;
    }


    //Method 1: addCategory
    //This method recieves a string, and creates a new super category to be added to the store's inventory
    public void addCategory(String categoryName) {
        Category _Category = new Category(categoryName);
        try {
            catMap.insert(_Category);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
//        CategoriesList.add(_Category);
        amount ++;
    }

    //Method 2: removeCategory
    //This method recieves a category's name, and deletes it from the store's inventory
    public void removeCategory(String categoryName){
        Category _Category = null;
        try {
            _Category = catMap.getById(categoryName);
        }
        catch (SQLException e){}
        try {
            catMap.delete(_Category);
        }
        catch (SQLException e){}
        amount--;
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

    public void deleteCatMapper() {
        try {
            catMap.deleteAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
