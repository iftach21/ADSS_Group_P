package Domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.*;

public class Report {
    private reportType type;
    private static int nextReportNum = 1;
    private int reportNum;
    private Date reportDate;
    private String reportInformationString;
    private LinkedHashMap<Item, Integer> reportItems;


    public Report(reportType type, Date reportDate) {
        this.type = type;
        this.reportDate = reportDate;
        this.reportNum = nextReportNum;
        nextReportNum++;
        this.reportInformationString = "";
        this.reportItems = new LinkedHashMap<Item, Integer>();
    }

    public Report(reportType type, Date reportDate, String reportInformationString, int reportNum,LinkedHashMap<Item,Integer> reportItems) {
        this.type = type;
        this.reportDate = reportDate;
        this.reportNum = nextReportNum;
        this.reportInformationString = reportInformationString;
        this.reportItems = reportItems;
    }

    public Report(reportType type, Date reportDate, String reportInformationString, int reportNum) {
        this.type = type;
        this.reportDate = reportDate;
        this.reportNum = nextReportNum;
        this.reportInformationString = reportInformationString;
        this.reportItems = new LinkedHashMap<>();
    }

    public reportType getType() {
        return type;
    }

    public String getTypeString() {
        String type = this.getType().toString();
        return type;
    }

    public int getReportNum() {
        return reportNum;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public String getreportInformationString() {
        return reportInformationString;
    }

    //Method 1: setReportData
    //This method adds the reports information into the report
    public void setReportData(String data){
        this.reportInformationString += data;
    }

    public void addReportItem(Item currentItem, int amount){
        this.reportItems.put(currentItem, amount);
    }

    public LinkedHashMap<Item, Integer> getReportItems() {
        return reportItems;
    }

    @Override
    public String toString() {
        String reportString = "Report: " +
                "Report Type: " + type +
                ", Report number: " + reportNum +
                ", Report Date: " + reportDate +
                ", Report Information: \n" + reportInformationString;
        for (Map.Entry<Item, Integer> entry : reportItems.entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            reportString += item.getName() + ", Catalog number: " + item.getCatalogNum() + " : " + amount + "\n";
        }
        return reportString;
    }

    public String[] toStringArray() {
        List<String> stringList = new ArrayList<>();

        stringList.add("Report: ");
        stringList.add("Report Type: " + type);
        stringList.add("Report number: " + reportNum);
        stringList.add("Report Date: " + reportDate);
        stringList.add("Report Information: ");
        stringList.add(reportInformationString);

        for (Map.Entry<Item, Integer> entry : reportItems.entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            stringList.add(item.getName() + ", Catalog number: " + item.getCatalogNum() + " : " + amount);
        }

        return stringList.toArray(new String[0]);
    }


    public String printPriceHistory() {
        String reportString = "Report: " +
                "Report Type: " + type +
                ", Report number: " + reportNum +
                ", Report Date: " + reportDate +
                ", Report Information: \n" + reportInformationString;
        return reportString;
    }
}
