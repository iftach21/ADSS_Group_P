import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class subCategory {
    private String subCategoryName;
    private String categoryName;
    private int amount = 0;
    private List<Item> generalItemsList;

    public subCategory(String subCategoryName) {
        this.subCategoryName = subCategoryName;
        this.generalItemsList = new ArrayList<Item>();
    }

    public subCategory() {
        this.subCategoryName = "";
        this.generalItemsList = new ArrayList<Item>();
    }

    public void setName(String name){ this.subCategoryName = name;}

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

    public void setCategory(String category) {
        this.categoryName = category;
    }
}
