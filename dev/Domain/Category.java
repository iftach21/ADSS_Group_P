package Domain;

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

    public Category(String categoryName,int amount) {
        this.categoryName = categoryName;
        this.subCategoriesList = new ArrayList<subCategory>();
        this.amount =amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getAmount() {
        return amount;
    }

    public subCategory getSubCategory(int index){
        return this.subCategoriesList.get(index);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSubCategoriesList(List<subCategory> subCategoriesList) {
        this.subCategoriesList = subCategoriesList;
    }

    public subCategory getSubCategoryByName(String subName){
        subCategory currentSubCat;
        for (int i = 0; i < subCategoriesList.size(); i++){
            if (subCategoriesList.get(i).getSubCategoryName().equals(subName)){
                currentSubCat = subCategoriesList.get(i);
                return currentSubCat;
            }
        }
        return null;
    }

    public void removeSubCategory(subCategory toDeleteSubCat){
        subCategory currentSubCat;
        for (int i = 0; i < subCategoriesList.size(); i++){
            currentSubCat = subCategoriesList.get(i);
            if (currentSubCat.getSubCategoryName().equals(toDeleteSubCat.getSubCategoryName())){
                subCategoriesList.remove(toDeleteSubCat);
                amount --;
            }
        }
    }

    @Override
    public String toString() {
        String categoryString = "\n---------- Category name: " + categoryName + ", Amount of sub-categories: "
                + amount + " ----------";

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
