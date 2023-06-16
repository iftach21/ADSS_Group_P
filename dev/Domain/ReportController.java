package Domain;

import DataAccesObject.ReportItemsMapper;
import DataAccesObject.ReportMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private List<Report> reportsList;
    private ReportMapper reportMapper;
    private ReportItemsMapper reportItemsMapper;

    public ReportController() {
        this.reportMapper = new ReportMapper();
        this.reportItemsMapper = new ReportItemsMapper();
        this.reportsList = new ArrayList<Report>();
    }

    //Method 1: addReport
    //This method recieves a new report to be added to the reports list and prints it to the user
    public void addReport(Report newReport){
        if (newReport == null)
            return;
        try
        {
            System.out.println(newReport.toString());
            reportMapper.insert(newReport);;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addShortageReport(Report newReport){
        if (newReport == null)
            return;
        try
        {
            System.out.println(newReport.toString());
            reportItemsMapper.insert(newReport);;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Report getReportById(String reportNum){
        Report report = null;
        try {
            report = reportMapper.getById(reportNum);;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return report;
    }

    public Report getShortageReportById(String reportNum){
        Report report = null;
        try {
            report = reportItemsMapper.getById(reportNum);;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return report;
    }

}
