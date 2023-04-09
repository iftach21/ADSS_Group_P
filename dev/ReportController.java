import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private List<Report> reportsList;

    public ReportController() {
        this.reportsList = new ArrayList<Report>();
    }

    public void addReport(Report newReport){
        System.out.println(newReport.toString());
        reportsList.add(newReport);
    }

}
