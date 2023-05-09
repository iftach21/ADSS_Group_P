import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private List<Report> reportsList;

    public ReportController() {
        this.reportsList = new ArrayList<Report>();
    }

    //Method 1: addReport
    //This method recieves a new report to be added to the reports list and prints it to the user
    public void addReport(Report newReport){
        System.out.println(newReport.toString());
        reportsList.add(newReport);
    }

    public void addReportHistory(Report priceHistoryReport) {
        System.out.println(priceHistoryReport.printPriceHistory());
        reportsList.add(priceHistoryReport);
    }
}
