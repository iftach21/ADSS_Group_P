import java.sql.*;
import java.util.*;
import java.util.Date;

public class subCategoryMapper {
    private Connection conn;
    private final Map<String, List<subCategory>> subCategoriesByCategoryCache;
    private final Map<String, List<specificItem>> itemsBySubCategoryCache;

    public subCategoryMapper() {
        this.subCategoriesByCategoryCache = new HashMap<>();
        this.itemsBySubCategoryCache = new HashMap<>();
    }


    private void getConnection() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<subCategory> findSubCategoriesByCategory(String category) {
        if (subCategoriesByCategoryCache.containsKey(category)) {
            return subCategoriesByCategoryCache.get(category);
        }

        List<subCategory> subCategories = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        try {
            stmt = conn.prepareStatement("SELECT * FROM sub_categories WHERE category = ?");
            stmt.setString(1, category);
            rs = stmt.executeQuery();

            while (rs.next()) {
                subCategory currentSubCategory = new subCategory();
                currentSubCategory.setName(rs.getString("name"));
                currentSubCategory.setCategory(rs.getString("category"));
                subCategories.add(currentSubCategory);
            }

            subCategoriesByCategoryCache.put(category, subCategories);
            return subCategories;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public List<specificItem> findItemsBySubCategory(String subCategory) {
        if (itemsBySubCategoryCache.containsKey(subCategory)) {
            return itemsBySubCategoryCache.get(subCategory);
        }

        List<specificItem> specificItems = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet rs;
        getConnection();

        try {
            stmt = conn.prepareStatement("SELECT * FROM specific_items WHERE sub_category = ?");
            stmt.setString(1, subCategory);
            rs = stmt.executeQuery();

            while (rs.next()) {
                specificItem currentSpecific = new specificItem();
                currentSpecific.setName(rs.getString("name"));
                currentSpecific.setCatalogNum(rs.getString("catalog_number"));
                String locationString = rs.getString("location");

                if (locationString != null && !locationString.isEmpty()) {
                    locationString = locationString.substring(0, 1).toUpperCase() + locationString.substring(1).toLowerCase();
                }

                Location location = Location.valueOf(locationString);
                currentSpecific.setLocation(location);
                long currentDate = rs.getLong("expiration_date");
                currentSpecific.setDate(new Date(currentDate));
                currentSpecific.setDefected(rs.getBoolean("defected"));
                currentSpecific.setSerialNumber(rs.getInt("serial_number"));
                specificItems.add(currentSpecific);
            }

            itemsBySubCategoryCache.put(subCategory, specificItems);
            return specificItems;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
