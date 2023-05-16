package DataAccesObject;

import org.json.JSONObject;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Domain.*;

public class ReportMapper implements DAO<Report> {
    private Connection connection;
    private List<Report> identityMap;

    public ReportMapper() {
        this.connection = connection;
        this.identityMap = new ArrayList<>();
    }

    @Override
    public List<Report> getAll() throws SQLException {
        Report report = null;
        getConnection();
        String sql = "SELECT * FROM Reports";
        List<Report> ReportList = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                int reportNum = rs.getInt("reportNum");
                Date reportDate = rs.getDate("reportDate");
                String reportInformationString = rs.getString("reportInformationString");
                String reportTypeString = rs.getString("reportType");
                reportType reportTypeEnum = reportType.valueOf(reportTypeString);
                String reportNumString = String.valueOf(reportNum);
                report = getById(reportNumString);
                if (report == null){
                    report = new Report(reportTypeEnum,reportDate);
                    identityMap.add(report);
                }
                ReportList.add(report);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        try {
            connection.close();
        }
        catch (SQLException e){}
        return ReportList;
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
        String sql = "SELECT * FROM Reports WHERE reportNum = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                int reportNum = rs.getInt("reportNum");
                Date reportDate = rs.getDate("reportDate");
                String reportInformationString = rs.getString("reportInformationString");
                String reportTypeString = rs.getString("reportType");
                reportType reportTypeEnum = reportType.valueOf(reportTypeString);
                Report report = new Report(reportTypeEnum,reportDate,reportInformationString,reportNum);
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
        String sql = "INSERT INTO Reports (reportNum, reportDate, reportInformationString, reportType) VALUES (?, ?, ?, ?)";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,report.getReportNum());
            int reportNum = report.getReportNum();
            if (reportNumExists(reportNum)) {
                reportNum = generateReportNum();
            }
            statement.setInt(1, reportNum);
            if (report.getReportDate() != null) {
                statement.setDate(2, new java.sql.Date(report.getReportDate().getTime()));
            } else {
                statement.setDate(2, null);
            }
            statement.setString(3,report.getreportInformationString());
            statement.setString(4,report.getType().name());
            statement.setString(3, report.getreportInformationString());
            statement.setString(4, report.getType().name());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                identityMap.add(report);
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

    private boolean reportNumExists(int reportNum) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Reports WHERE reportNum = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reportNum);
            ResultSet rs = statement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private int generateReportNum() throws SQLException {
        String sql = "SELECT MAX(reportNum) FROM Reports";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }



    @Override
    public void update(Report report) throws SQLException {
        String sql = "UPDATE Reports SET reportDate = ?, reportInformationString = ?, reportType = ? WHERE reportNum = ?";
        getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, (java.sql.Date) report.getReportDate());
            statement.setString(2,report.getreportInformationString());
            statement.setString(3,report.getType().name());
            statement.setInt(4,report.getReportNum());
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
    public void delete(Report report) throws SQLException {
        String sql = "DELETE FROM Reports WHERE reportNum = ?";
        getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,report.getReportNum());
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

    public void deleteAll() throws SQLException {
        getConnection();
        String sql = "DELETE FROM " + "Reports";
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
}

