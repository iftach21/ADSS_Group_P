import java.util.List;
import java.util.ArrayList;



public class Category {
    private String categoryName;
    private int amount = 0;
    private List<subCategory> subCategoriesList;


    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.subCategoriesList = new ArrayList<subCategory>();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        String categoryString = "Category name: " + categoryName + ", Amount of sub-categories: " + amount;
        for (int i = 0; i < this.subCategoriesList.size(); i++){
            categoryString += '\n';
            categoryString += this.subCategoriesList.get(i).toString();
        }
        return categoryString;
    }

    public void addSubCategory(subCategory subCategory) {
        this.amount ++;
        subCategoriesList.add(subCategory);
    }
}
