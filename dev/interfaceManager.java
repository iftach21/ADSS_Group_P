import java.util.Date;
import java.util.Scanner;

public class interfaceManager {
    private InventoryController Inventory;
    private ReportController Reports;
    public interfaceManager() {
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

        //Sub-categories
        subCategory milksSubCat = new subCategory("Milk");
        subCategory buttersSubCat = new subCategory("Butter");
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Dairy"), milksSubCat);
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Dairy"), buttersSubCat);

        subCategory toiletSubCat = new subCategory("Toilet Paper");
        subCategory washingMachineSubCat = new subCategory("Washing Machine Gels");
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Cleaning"), toiletSubCat);
        Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory("Cleaning"), washingMachineSubCat);

        //General Items
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2);
        milk3Percent.addNewPrice(10, 20);

        Item soyMilk = new Item("Soy Milk", "000789", 2, "Tara", TempLevel.cold, 2);
        soyMilk.addNewPrice(30, 40);
        Item regularButter = new Item("Best Butter", "000666", 4, "Mama Mia", TempLevel.cold, 12);
        regularButter.addNewPrice(50, 60);

        Item toiletPaper = new Item("Lalin Toilet Paper", "111123", 10, "Lalin", TempLevel.regular, 20);
        toiletPaper.addNewPrice(20, 40);
        Item sodGel = new Item("Sod Gel", "111567", 6, "Sod", TempLevel.regular, 10);
        sodGel.addNewPrice(20, 26);

        //Specific Items
        specificItem milk1 = new specificItem(1, dateMilk1, false, Location.Store);
        specificItem milk2 = new specificItem(2, dateMilk2, true, Location.Store);
        specificItem milk3 = new specificItem(3, dateMilk3, true, Location.Store);
        specificItem milk4 = new specificItem(4, dateMilk4, true, Location.Store);
        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);
        milk3Percent.addSpecificItem(milk3);
        milk3Percent.addSpecificItem(milk4);

        specificItem soyMilk1 = new specificItem(1, dateMilk1,false, Location.Store);
        specificItem soyMilk2 = new specificItem(2, dateMilk2,false, Location.Store);
        specificItem soyMilk3 = new specificItem(3, dateMilk3,false, Location.Store);
        specificItem soyMilk4 = new specificItem(4, dateMilk4,false, Location.Store);
        soyMilk.addSpecificItem(soyMilk1);
        soyMilk.addSpecificItem(soyMilk2);
        soyMilk.addSpecificItem(soyMilk3);
        soyMilk.addSpecificItem(soyMilk4);

        specificItem butter1 = new specificItem(1, dateMilk1, false, Location.Store);
        specificItem butter2 = new specificItem(2, dateMilk2, false, Location.Store);
        regularButter.addSpecificItem(butter1);
        regularButter.addSpecificItem(butter2);

        specificItem toiletPaper1 = new specificItem(1, null, false, Location.Store);
        specificItem toiletPaper2 = new specificItem(2, null, false, Location.Store);
        specificItem toiletPaper3 = new specificItem(3, null, false, Location.Store);
        toiletPaper.addSpecificItem(toiletPaper1);
        toiletPaper.addSpecificItem(toiletPaper2);
        toiletPaper.addSpecificItem(toiletPaper3);

        specificItem sodGel1 = new specificItem(1, null, false, Location.Store);
        specificItem sodGel2 = new specificItem(2, null, true, Location.Storage);
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

    public void interfaceManagerLogin() {
        boolean exit = true;
        while (exit) {
            System.out.println("Please choose what you would like to do:");
            System.out.println("1: Provide a product shortage report");
            System.out.println("2: Update inventory");
            System.out.println("3: Provide an inventory counting report");
            System.out.println("4: Provide a defective products report");
            System.out.println("5: Update discount on products");
            System.out.println("6: Place an order from a supplier");
            System.out.println("7: Update product details");
            System.out.println("8: Provide a price history report for a product");
            System.out.println("9: Insert a defective product into the defective inventory");
            System.out.println("10: Print full inventory report");

            System.out.println("0: Exit the system ");

            Scanner myObj = new Scanner(System.in);
            String ans = myObj.nextLine();  // Read user input

            switch (ans) {
                //-----------------------
                //Provide a product shortage report
                //-----------------------
                case "1": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("which report to provide:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Main Menu");

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
//                                    this.Shortge_Report(Pro_ans);
                                break;

                            case "0":
                                System.out.println("Going back to the Main Menu");
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
                //Update inventory
                //-----------------------
                case "2": {
                    System.out.println("This function is not yet available in the current version of the system.");
                    break;
                }

                //-----------------------
                //Provide an inventory counting report
                //-----------------------
                case "3": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("which report to provide:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Main Menu");

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
                                System.out.println("Going back to the Main Menu");
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
                case "4": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("which report to provide:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Main Menu");

                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input

                        switch (Sub_Ans) {
                            case "1":
                                    Reports.addReport(Inventory.FullDefectiveReport());
                                break;

                            case "2":
                                System.out.println("Please enter the category you would like to provide a defective products report for:");
                                Scanner Category = new Scanner(System.in);
                                String Cat_ans = Category.nextLine();
//                                    this.Defective_Report(Cat_ans);
                                break;

                            case "3":
                                System.out.println("Please enter the product you would like to provide a defective products report for:");
                                Scanner Product = new Scanner(System.in);
                                String Pro_ans = Product.nextLine();
//                                    this.Defective_Report(Pro_ans);
                                break;

                            case "0":
                                System.out.println("Going back to the Main Menu");
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
                //Update discount on products
                //-----------------------
                case "5": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("For which products to update:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Main Menu");

                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input

                        switch (Sub_Ans) {
                            case "1":
//                                    this.Update_discount("All");
                                break;

                            case "2":
                                System.out.println("Please enter the category you would like to provide a defective products report for:");
                                Scanner Category = new Scanner(System.in);
                                String Cat_ans = Category.nextLine();
//                                    this.Update_discount(Cat_ans);
                                break;

                            case "3":
                                System.out.println("Please enter the product you would like to provide a defective products report for:");
                                Scanner Product = new Scanner(System.in);
                                String Pro_ans = Product.nextLine();
//                                    this.Update_discount(Pro_ans);
                                break;

                            case "0":
                                System.out.println("Going back to the Main Menu");
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
                //Place an order from a supplier
                //-----------------------
                case "6": {
//                        this.Place_Order();
                    break;
                }

                //-----------------------
                //Update product details
                //-----------------------
                case "7": {
                    System.out.println("please enter catalog number:");
                    Scanner Second_obj = new Scanner(System.in);
                    String Sub_Ans = Second_obj.nextLine();  // Read user input
//                        this.Update_Details();
                    break;
                }

                //-----------------------
                //Provide a price history report for a product
                //-----------------------
                case "8": {
                    boolean flag = true;
                    while (flag) {
                        System.out.println("which history report to provide:");
                        System.out.println("1: To all products");
                        System.out.println("2: By Category");
                        System.out.println("3: For a specific product");
                        System.out.println("0: Return to the Main Menu");

                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input

                        switch (Sub_Ans) {
                            case "1":
//                                    this.History_Report("All");
                                break;

                            case "2":
                                System.out.println("Please enter the category you would like to provide a history report for:");
                                Scanner Category = new Scanner(System.in);
                                String Cat_ans = Category.nextLine();
//                                    this.History_Report(Cat_ans);
                                break;

                            case "3":
                                System.out.println("Please enter the product you would like to provide a history report for:");
                                Scanner Product = new Scanner(System.in);
                                String Pro_ans = Product.nextLine();
//                                    this.History_Report(Pro_ans);
                                break;

                            case "0":
                                System.out.println("Going back to the Main Menu");
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
                //Insert a defective product into the defective inventory
                //-----------------------
                case "9": {
                    System.out.println("please enter catalog number:");
                    Scanner Second_obj = new Scanner(System.in);
                    String Sub_Ans = Second_obj.nextLine();  // Read user input
//                        this.Insert_Defective();
                    break;
                }

                //-----------------------
                //Print full inventory report
                //-----------------------
                case "10": {
                    System.out.println(this.Inventory.toString());
                    break;
                }

                // -------------

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
    }
}
