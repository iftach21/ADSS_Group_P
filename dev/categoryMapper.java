import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class categoryMapper implements DAO<Category>{
    private Connection connection;
    private List<Category> identityMap;


    //constructor
    public categoryMapper() throws SQLException{
        this.connection = Database.connect();
        this.identityMap = new ArrayList<>();
    }

    @Override
    public List<Category> getAll() throws SQLException {
        Category category = null;
        String sql = "SELECT * FROM Categories";
        List<Category> categoryList = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                String categoryName = rs.getString("categoryName");
                int amount = rs.getInt("amount");
                category = getById(categoryName);
                if (category == null){
                    category = new Category(categoryName);
                    identityMap.add(category);
                }
                categoryList.add(category);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public Category getById(String id) throws SQLException {
        for (Category category: identityMap){
            if (category.getCategoryName() == id){
                return category;
            }
        }
        String sql = "SELECT * FROM Categories WHERE categoryName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                String categoryName = rs.getString("categoryName");
                Category category = new Category(id);
                identityMap.add(category);
                return category;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Category category) throws SQLException {
        String sql = "INSERT INTO Categories (categoryName, amount) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,category.getCategoryName());
            statement.setInt(2,category.getAmount());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                identityMap.add(category);
            }
            //TODO check if needed
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category category) throws SQLException {
        String sql = "UPDATE Categories SET amount = ?WHERE categoryName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,category.getAmount());
            statement.setString(2,category.getCategoryName());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Category category) throws SQLException {
        String sql = "DELETE FROM Categories WHERE categoryName = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,category.getCategoryName());
        statement.executeUpdate();
    }
}
