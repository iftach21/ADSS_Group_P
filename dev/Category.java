import java.util.List;
import java.util.Map;

public class Category {
    private String categoryName;
    private String categoryNum;
    private String subCategoryName;
    private int subCategoryNum;



    public Category(String categoryName, String categoryNum, String subCategoryName, int subCategoryNum) {
        this.categoryName = categoryName;
        this.categoryNum = categoryNum;
        this.subCategoryName = subCategoryName;
        this.subCategoryNum = subCategoryNum;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryNum() {
        return categoryNum;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public int getSubCategoryNum() {
        return subCategoryNum;
    }
}
