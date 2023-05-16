package Interface;
import Domain.*;
import java.util.Date;
import java.util.Scanner;

public class interfaceWorker extends AInterface {
    private final InventoryController Inventory;
    private final ReportController Reports;
    public interfaceWorker() {
        this.Inventory = new InventoryController();
        this.Reports = new ReportController();
    }

    public void interfaceStartup() {
        // -------------------------------------------------------
        // Demo categories, general items, and specific items for demonstration
        // -------------------------------------------------------

        //New dates
        Date dateMilk1 = new Date(2023, 10, 7);
        Date dateMilk2 = new Date(2024, 1, 9);
        Date dateMilk3 = new Date(2025, 5, 11);
        Date dateMilk4 = new Date(2026, 6, 13);

        //Categories
        Inventory.getCategoryControl().addCategory("Dairy");
        Inventory.getCategoryControl().addCategory("Cleaning");
        //Default
        Inventory.getCategoryControl().addCategory("Etc");

        //Sub-categories
        subCategory milksSubCat = new subCategory("Milk");
        subCategory buttersSubCat = new subCategory("Butter");
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Dairy"), milksSubCat);
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Dairy"), buttersSubCat);

        subCategory toiletSubCat = new subCategory("Toilet Paper");
        subCategory washingMachineSubCat = new subCategory("Washing Machine Gels");
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Cleaning"), toiletSubCat);
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Cleaning"), washingMachineSubCat);

        //Default
        subCategory allSubCat = new subCategory("All");
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Etc"), allSubCat);

        //General Items
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");
        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        Item soyMilk = new Item("Soy Milk", "000789", 2, "Tara", TempLevel.cold, "Dairy");
        soyMilk.addNewPrice(30, 40);
        Item regularButter = new Item("Best Butter", "000666", 4, "Mama Mia", TempLevel.cold, "Dairy");
        regularButter.addNewPrice(50, 60);

        Item toiletPaper = new Item("Lalin Toilet Paper", "111123", 10, "Lalin", TempLevel.regular,"Cleaning");
        toiletPaper.addNewPrice(20, 40);
        Item sodGel = new Item("Sod Gel", "111567", 6, "Sod", TempLevel.regular,"Cleaning");
        sodGel.addNewPrice(20, 26);

        //Specific Items
        specificItem milk1 = new specificItem(dateMilk1, false, Location.Store, milk3Percent);
        specificItem milk2 = new specificItem(dateMilk2, true, Location.Storage, milk3Percent);
        specificItem milk3 = new specificItem(dateMilk3, true, Location.Storage, milk3Percent);
        specificItem milk4 = new specificItem(dateMilk4, true, Location.Storage, milk3Percent);
        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);
        milk3Percent.addSpecificItem(milk3);
        milk3Percent.addSpecificItem(milk4);

        specificItem soyMilk1 = new specificItem(dateMilk1,false, Location.Store, soyMilk);
        specificItem soyMilk2 = new specificItem(dateMilk2,false, Location.Store, soyMilk);
        specificItem soyMilk3 = new specificItem(dateMilk3,false, Location.Store, soyMilk);
        specificItem soyMilk4 = new specificItem(dateMilk4,false, Location.Store, soyMilk);
        soyMilk.addSpecificItem(soyMilk1);
        soyMilk.addSpecificItem(soyMilk2);
        soyMilk.addSpecificItem(soyMilk3);
        soyMilk.addSpecificItem(soyMilk4);

        specificItem butter1 = new specificItem(dateMilk1, false, Location.Store, regularButter);
        specificItem butter2 = new specificItem(dateMilk2, false, Location.Store, regularButter);
        regularButter.addSpecificItem(butter1);
        regularButter.addSpecificItem(butter2);

        specificItem toiletPaper1 = new specificItem(null, false, Location.Store, toiletPaper);
        specificItem toiletPaper2 = new specificItem(null, false, Location.Store, toiletPaper);
        specificItem toiletPaper3 = new specificItem(null, false, Location.Store, toiletPaper);
        toiletPaper.addSpecificItem(toiletPaper1);
        toiletPaper.addSpecificItem(toiletPaper2);
        toiletPaper.addSpecificItem(toiletPaper3);

        specificItem sodGel1 = new specificItem(null, false, Location.Store, sodGel);
        specificItem sodGel2 = new specificItem(null, true, Location.Storage, sodGel);
        sodGel.addSpecificItem(sodGel1);
        sodGel.addSpecificItem(sodGel2);


        milksSubCat.addGeneralItem(milk3Percent);
        milksSubCat.addGeneralItem(soyMilk);
        buttersSubCat.addGeneralItem(regularButter);
        toiletSubCat.addGeneralItem(toiletPaper);
        washingMachineSubCat.addGeneralItem(sodGel);

        Inventory.addGeneralItem(milk3Percent);
        Inventory.addGeneralItem(soyMilk);
        Inventory.addGeneralItem(regularButter);
        Inventory.addGeneralItem(toiletPaper);
        Inventory.addGeneralItem(sodGel);

    }

    public void interfaceWorkerLogin() {
        boolean exit = true;
        while (exit){
            System.out.println("Please choose what you would like to do:");
            System.out.println("1: Provide a product shortage report");
            System.out.println("2: Provide an inventory counting report");
            System.out.println("3: Provide a defective products report");
            System.out.println("4: Provide a price history report for a product");
            System.out.println("5: Insert a defective product into the defective inventory");

            System.out.println("0: Exit the system ");

            Scanner myObj = new Scanner(System.in);
            String ans = myObj.nextLine();  // Read user input

            switch (ans) {
                //-----------------------
                // Provide a product shortage report
                //-----------------------
                case "1": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("which report to provide:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Interface.Main Menu");

                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input

                        switch (Sub_Ans) {
                            case "1":
                                Reports.addReport(Inventory.shortageReportFull());
                                break;

                            case "2":
                                System.out.println("For which category?");
                                Scanner categoryInput = new Scanner(System.in);
                                String categoryName = categoryInput.nextLine();
                                Reports.addReport(Inventory.shortageReportCategory(categoryName));
                                break;
                            case "3":
                                System.out.println("Please enter the catalog number for the product you would like to provide a shortage report for:");
                                Scanner Product = new Scanner(System.in);
                                String productNumber = Product.nextLine();
                                Reports.addReport(Inventory.shortageReportGeneralItem(productNumber));
                                break;

                            case "0":
                                System.out.println("Going back to the Interface.Main Menu");
                                flag = false;
                                break;

                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                    }
                    break;
                }

                //-----------------------
                //Provide an inventory counting report
                //-----------------------
                case "2": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("which report to provide:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Interface.Main Menu");

                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input

                        switch (Sub_Ans) {
                            case "1":
                                Reports.addReport(Inventory.FullCountingReport());
                                break;

                            case "2":
                                System.out.println("Please enter the category you would like to provide a inventory counting report for:");
                                Scanner Category = new Scanner(System.in);
                                String categoryName = Category.nextLine();
                                Reports.addReport(Inventory.CategoryCountingReport(categoryName));
                                break;

                            case "3":
                                Scanner Product = new Scanner(System.in);
                                System.out.println("Please enter the catalog number of the product you would like to provide a inventory counting report for:");
                                String CatalogNum = Product.nextLine();
                                Reports.addReport(Inventory.ItemCountingReport(CatalogNum));
                                break;

                            case "0":
                                System.out.println("Going back to the Interface.Main Menu");
                                flag = false;
                                break;

                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                    }
                    break;
                }

                //-----------------------
                //Provide a defective products report
                //-----------------------
                case "3": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("which report to provide:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Interface.Main Menu");

                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input

                        switch (Sub_Ans) {
                            case "1":
                                Reports.addReport(Inventory.FullDefectiveReport());
                                break;

                            case "2":
                                System.out.println("Please enter the category you would like to provide a defective products report for:");
                                Scanner Category = new Scanner(System.in);
                                String categoryName = Category.nextLine();
                                Reports.addReport(Inventory.CategoryDefectiveReport(categoryName));
                                break;

                            case "3":
                                System.out.println("Please enter the product you would like to provide a defective products report for:");
                                Scanner Product = new Scanner(System.in);
                                String CatalogNum = Product.nextLine();
                                Reports.addReport(Inventory.ItemDefectiveReport(CatalogNum));
                                break;

                            case "0":
                                System.out.println("Going back to the Interface.Main Menu");
                                flag = false;
                                break;

                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                    }
                    break;
                }

                //-----------------------
                //Provide a price history report for a product
                //-----------------------
                case "4": {
                    System.out.println("What is the catalog number for the product?");
                    Scanner Input = new Scanner(System.in);
                    String catalogNumber = Input.nextLine();
                    Reports.addReport(Inventory.priceHistoryReport(catalogNumber));
                    //Reports.addReport(Inventory.shortageReportGeneralItem(productNumber));
                    break;
                }

                //-----------------------
                //Insert a defective product into the defective inventory
                //-----------------------
                case "5": {
                    System.out.println("What is the item ID for the item to be set as defected?");
                    Scanner defectedItemInput = new Scanner(System.in);
                    int defectedserialNumber = Integer.parseInt(defectedItemInput.nextLine());
                    specificItem currentSpecific = Inventory.findSpecificItem(defectedserialNumber);
                    currentSpecific.setDefected(true);
                    currentSpecific.setLocation(Location.Storage);
                    System.out.println("Item numbered " + defectedserialNumber + " has been set has defected and" +
                            "moved into the warehouse storage." );
                    System.out.println(currentSpecific.toString());
                    break;
                }

                //-----------------------
                //Exit the system
                //-----------------------
                case "0": {
                    exit = false;
                    break;
                }

                default: {
                    System.out.println("please enter valid input");
                }

            }
        }

    }}
