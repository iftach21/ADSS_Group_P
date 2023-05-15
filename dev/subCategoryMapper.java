import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;
import java.util.Date;
public class subCategoryMapper {
    private Connection conn;
    private final Map<String, List<subCategory>> cacheSubCategories;
    public subCategoryMapper() {
        this.cacheSubCategories = new HashMap<>();
    }

    public void insertSubCategory(String categoryName, subCategory subCat) {
        try {
            getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO subCategories(subCategoryName, categoryName, amount) VALUES(?, ?, ?)");
            stmt.setString(2, categoryName);
            stmt.setString(1, subCat.getSubCategoryName());
            stmt.setInt(3, subCat.getAmount());
            stmt.executeUpdate();

            // Update cache
            if (cacheSubCategories.containsKey(categoryName)) {
                cacheSubCategories.get(categoryName).add(subCat);
            } else {
                List<subCategory> subCategories = new ArrayList<>();
                subCategories.add(subCat);
                cacheSubCategories.put(categoryName, subCategories);
            }
            updateCategoryAmount(categoryName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCategoryAmount(String categoryName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Categories SET amount = amount + 1 WHERE categoryName = ?");
        stmt.setString(1, categoryName);
        stmt.executeUpdate();
    }

    private void getConnection()
    {
        try
        {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        }
        catch (SQLException e){}
    }

}
