package DataAccesObject;

import Domain.*;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportItemsMapper implements DAO<Report> {
    private Connection connection;
    private List<Report> identityMap;

    public ReportItemsMapper() {
        this.connection = connection;
        this.identityMap = new ArrayList<>();
    }


    @Override
    public List<Report> getAll() throws SQLException {
        Report report = null;
        getConnection();
        String sql = "SELECT r.reportNum, r.reportType, r.reportDate, r.reportInformationString, " +
                "i.catalog_number,ri.quantity, i.name, i.weight, i.catalog_name, " +
                "i.temperature, i.minimum_quantity, i.price_history, i.manufacturer " +
                "FROM Reports r " +
                "JOIN ReportItems ri ON r.reportNum = ri.reportNum " +
                "JOIN Items i ON ri.catalog_number = i.catalog_number";
        List<Report> reportList = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                int reportNum = rs.getInt("r.reportNum");
                String catalogNumber = rs.getString("i.catalog_number");
                int quantity = rs.getInt("ri.quantity");
                String reportTypeString = rs.getString("r.reportType");
                reportType reportTypeEnum = reportType.valueOf(reportTypeString);
                Date reportDate = rs.getDate("r.reportDate");
                String reportInformationString = rs.getString("r.reportInformationString");
                String ItemName = rs.getString("i.name");
                double weight = rs.getDouble("i.weight");
                String catalogName = rs.getString("i.catalog_name");
                String temperature = rs.getString("i.temperature");
                TempLevel tempValue = TempLevel.valueOf(temperature);
                int minimumQuantity = rs.getInt("i.minimum_quantity");
                String priceHistory = rs.getString("i.price_history");
                String manufacturer = rs.getString("i.manufacturer");

                String reportIdString = String.valueOf(reportNum);
                report = getById(reportIdString);
                if (report == null){
                    Item item = new Item(ItemName,catalogNumber,weight,catalogName,tempValue,manufacturer,minimumQuantity);
                    LinkedHashMap<Item,Integer> reportItems = new LinkedHashMap<>();
                    reportItems.put(item,quantity);
                    report = new Report(reportTypeEnum,reportDate,reportInformationString,reportNum,reportItems);
                    identityMap.add(report);
                }
                reportList.add(report);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try {
            connection.close();
        }
        catch (SQLException e){}
        return reportList;
    }

    @Override
    public Report getById(String id) throws SQLException {
        int reportid = Integer.parseInt(id);
        for (Report report: identityMap){
            if (report.getReportNum() == reportid){
                return report;
            }
        }
        getConnection();
        String sql = "SELECT r.reportType, r.reportDate, r.reportInformationString, " +
                "i.catalog_number, ri.quantity, i.name, i.weight, i.catalog_name, " +
                "i.temperature, i.minimum_quantity, i.price_history, i.manufacturer " +
                "FROM Reports r " +
                "JOIN ReportItems ri ON r.reportNum = ri.reportNum " +
                "JOIN Items i ON ri.catalog_number = i.catalog_number " +
                "WHERE r.reportNum = ?";

        LinkedHashMap<Item,Integer> reportItems = new LinkedHashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,reportid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                String catalogNumber = rs.getString("i.catalog_number");
                int quantity = rs.getInt("ri.quantity");
                String reportTypeString = rs.getString("r.reportType");
                reportType reportTypeEnum = reportType.valueOf(reportTypeString);
                Date reportDate = rs.getDate("r.reportDate");
                String reportInformationString = rs.getString("r.reportInformationString");
                String ItemName = rs.getString("i.name");
                double weight = rs.getDouble("i.weight");
                String catalogName = rs.getString("i.catalog_name");
                String temperature = rs.getString("i.temperature");
                TempLevel tempValue = TempLevel.valueOf(temperature);
                int minimumQuantity = rs.getInt("i.minimum_quantity");
                String priceHistory = rs.getString("i.price_history");
                String manufacturer = rs.getString("i.manufacturer");
                Item item = new Item(ItemName,catalogNumber,weight,catalogName,tempValue,manufacturer,minimumQuantity);
                reportItems.put(item,quantity);
                Report report = new Report(reportTypeEnum,reportDate,reportInformationString,reportid,reportItems);

                identityMap.add(report);
                return report;
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
    public void insert(Report report) throws SQLException {
        String sql = "INSERT INTO ReportItems (reportNum, catalog_number, quantity) VALUES (?, ?, ?)";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            LinkedHashMap<Item, Integer> reportItemsList = report.getReportItems();
            int reportNum = report.getReportNum();
            while (reportNumExists(reportNum)) {
                reportNum++;
            }
            for (Map.Entry<Item, Integer> entry : reportItemsList.entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                String catalog_number = item.getCatalogNum();
                statement.setInt(1, reportNum);
                statement.setString(2, catalog_number);
                statement.setInt(3, quantity);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    if (identityMap == null) {
                        identityMap = new ArrayList<>();
                    }
                    identityMap.add(report);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean reportNumExists(int reportNum) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ReportItems WHERE reportNum = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reportNum);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(Report report) throws SQLException {
        String sql = "UPDATE ReportItems SET quantity = ? WHERE reportNum = ? AND catalog_number = ?";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            LinkedHashMap<Item,Integer> reportItemsList = report.getReportItems();
            for (Map.Entry<Item,Integer> entry : reportItemsList.entrySet()){
                Item item = entry.getKey();
                int quantity = entry.getValue();
                String catalog_number = item.getCatalogNum();
                statement.setInt(1,report.getReportNum());
                statement.setString(2, catalog_number);
                statement.setInt(3,quantity);
                statement.executeUpdate();
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

    public void deleteAll() throws SQLException {
        getConnection();
        String sql = "DELETE FROM " + "ReportItems";
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
    public void delete(Report report) throws SQLException {
        String sql1 = "DELETE FROM ReportItems WHERE reportNum = ?";
        String sql2 = "DELETE FROM Reports WHERE reportNum = ?";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql1)) {
            statement.setInt(1, report.getReportNum());
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(sql2)){
            statement.setInt(1, report.getReportNum());
            statement.executeUpdate();
        }
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
