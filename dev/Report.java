import java.util.Date;

public class Report {
    private reportType type;
    private int reportNum;
    private Date reportDate;

    public Report(reportType type, int reportNum, Date reportDate) {
        this.type = type;
        this.reportNum = reportNum;
        this.reportDate = reportDate;
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

}
