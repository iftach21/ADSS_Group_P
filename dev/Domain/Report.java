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

//    public Report(){
//        this.reportNum = nextReportNum;
//    }

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

    public String[][] getAllReportsForJTable(Report report) {
        String data = report.toString();
        String[][] reportData = new String[reportItems.size()][4];
        int index = 0;

        for (Map.Entry<Item, Integer> entry : reportItems.entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();

            reportData[index][0] = String.valueOf(type);
            reportData[index][1] = String.valueOf(reportNum);
            reportData[index][2] = String.valueOf(reportDate);
            reportData[index][3] = "Report Information: \n" + reportInformationString + "\n" +
                    item.getName() + ", Catalog number: " + item.getCatalogNum() + " : " + amount;

            index++;
        }

        return reportData;
    }
}
