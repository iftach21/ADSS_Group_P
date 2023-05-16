package Domain;

import java.util.ArrayList;
import java.util.List;

public class subCategory {
    private String subCategoryName;
    private int amount = 0;
    private List<Item> generalItemsList;

    public subCategory(String subCategoryName) {
        this.subCategoryName = subCategoryName;
        this.generalItemsList = new ArrayList<Item>();
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Item> getGeneralItemsList() {
        return generalItemsList;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public void setGeneralItemsList(List<Item> generalItemsList) {
        this.generalItemsList = generalItemsList;
    }

    public int getAmount() {
        return amount;
    }

    public Item getItem(int index){
        return this.generalItemsList.get(index);
    }

    @Override
    public String toString() {
        String subCategoryString = "------ Sub-Category name: " + subCategoryName +
                ", Amount of general items: " + amount + " ------";
        /*
        for (int i = 0; i < this.generalItemsList.size(); i++){
            subCategoryString += '\n';
            subCategoryString += this.generalItemsList.get(i).toString();
        }

         */
        return subCategoryString;
    }

    public void addGeneralItem(Item generalItem) {
        this.amount ++;
        generalItemsList.add(generalItem);
    }
}
