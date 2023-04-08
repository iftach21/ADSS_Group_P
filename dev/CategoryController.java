import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class CategoryController {
    private List<Category> CategoriesList;

    public CategoryController(List<subCategory> subCategoriesList) {
        this.CategoriesList = new ArrayList<Category>();
    }

    public void addCategory(String categoryName) {
        Category _Category = new Category(categoryName);
        CategoriesList.add(_Category);
    }

    public void removeCategory(String categoryName){
        for (int i = 0; i < this.CategoriesList.size(); i++) {
            if (this.CategoriesList.get(i).getCategoryName() == categoryName)
                CategoriesList.remove(CategoriesList.get(i));
        }
    }

    public void addSubCategory(Category _Category,subCategory subCategory) {
        _Category.addSubCategory(subCategory);
    }




}
