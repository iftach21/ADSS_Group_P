package DataAccesObject;

import java.sql.*;
import java.util.ArrayList;
import Domain.*;
import java.util.List;

public class categoryMapper implements DAO<Category> {
    private Connection connection;
    private List<Category> identityMap;


    //constructor
    public categoryMapper(){
        this.identityMap = new ArrayList<>();
    }

    public void deleteAll() throws SQLException {
        getConnection();
        String sql = "DELETE FROM " + "Categories";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Category> getAll() throws SQLException {
        Category category = null;
        getConnection();
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
                    category.setAmount(amount);
                    identityMap.add(category);
                }
                categoryList.add(category);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try {
            connection.close();
        }
        catch (SQLException e){}
        return categoryList;
    }

    @Override
    public Category getById(String id) throws SQLException {
        for (Category category: identityMap){
            if (category.getCategoryName() == id){
                return category;
            }
        }
        getConnection();
        String sql = "SELECT * FROM Categories WHERE categoryName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                String categoryName = rs.getString("categoryName");
                int amount = rs.getInt("amount");
                Category category = new Category(id, amount);
                identityMap.add(category);
                return category;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try {
            connection.close();
        }
        catch (SQLException e){}
        return null;
    }

    @Override
    public void insert(Category category) throws SQLException {
        String sql = "INSERT INTO Categories (categoryName, amount) VALUES (?, ?)";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,category.getCategoryName());
            statement.setInt(2,category.getAmount());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                identityMap.add(category);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try {
            connection.close();
        }
        catch (SQLException e){}
    }

    @Override
    public void update(Category category) throws SQLException {
        String sql = "UPDATE Categories SET amount = ? WHERE categoryName = ?";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,category.getAmount());
            statement.setString(2,category.getCategoryName());
            statement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try {
            connection.close();
        }
        catch (SQLException e){}
    }

    @Override
    public void delete(Category category) throws SQLException {
        String sql = "DELETE FROM Categories WHERE categoryName = ?";
        getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,category.getCategoryName());
        statement.executeUpdate();
        try {
            connection.close();
        }
        catch (SQLException e){}
    }


    private void getConnection()
    {
        try
        {
            this.connection = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        }
        catch (SQLException e){}
    }
}

