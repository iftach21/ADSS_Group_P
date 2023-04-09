import java.util.Date;

public class Report {
    private reportType type;
    private static int nextReportNum = 1;
    private int reportNum;
    private Date reportDate;
    private String reportInformation;

    public Report(reportType type, Date reportDate) {
        this.type = type;
        this.reportDate = reportDate;
        this.reportNum = nextReportNum;
        nextReportNum++;
        this.reportInformation = "";
    }

    public reportType getType() {
        return type;
    }

    public int getReportNum() {
        return reportNum;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public String getReportInformation() {
        return reportInformation;
    }

    //Method 1: setReportData
    //This method adds the reports information into the report
    public void setReportData(String data){
        this.reportInformation += data;
    }

    @Override
    public String toString() {
        return "------ Report: " +
                "Report Type: " + type +
                ", Report number: " + reportNum +
                ", Report Date: " + reportDate +
                ", Report Information: \n" + reportInformation ;
    }
}
