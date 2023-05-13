import java.util.Date;
import java.util.Scanner;

public class interfaceManager {
    private final InventoryController Inventory;
    private final ReportController Reports;
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
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Dairy", TempLevel.cold, "man2");
        milk3Percent.setMinQuantity(6);
        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        Item soyMilk = new Item("Soy Milk", "000789", 2, "Dairy", TempLevel.cold, "man3");
        soyMilk.setMinQuantity(1);
        soyMilk.addNewPrice(30, 40);

        Item regularButter = new Item("Best Butter", "000666", 4, "Dairy", TempLevel.cold, "man4");
        regularButter.setMinQuantity(3);
        regularButter.addNewPrice(50, 60);

        Item toiletPaper = new Item("Lalin Toilet Paper", "111123", 10, "Cleaning", TempLevel.regular,"man4");
        toiletPaper.setMinQuantity(2);
        toiletPaper.addNewPrice(20, 40);

        Item sodGel = new Item("Sod Gel", "111567", 6, "Cleaning", TempLevel.regular,"man5");
        sodGel.setMinQuantity(4);
        sodGel.addNewPrice(20, 26);

        //Specific Items
        specificItem milk1 = new specificItem(dateMilk1, false, Location.Store, milk3Percent);
        specificItem milk2 = new specificItem(dateMilk2, true, Location.Storage, milk3Percent);
        specificItem milk3 = new specificItem(dateMilk3, true, Location.Storage, milk3Percent);
        specificItem milk4 = new specificItem(dateMilk4, true, Location.Storage, milk3Percent);
        Inventory.addSpecificItem(milk3Percent, milk1);
        Inventory.addSpecificItem(milk3Percent, milk2);
        Inventory.addSpecificItem(milk3Percent, milk3);
        Inventory.addSpecificItem(milk3Percent, milk4);
        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);
        milk3Percent.addSpecificItem(milk3);
        milk3Percent.addSpecificItem(milk4);

        specificItem soyMilk1 = new specificItem(dateMilk1,false, Location.Store, soyMilk);
        specificItem soyMilk2 = new specificItem(dateMilk2,false, Location.Store, soyMilk);
        specificItem soyMilk3 = new specificItem(dateMilk3,false, Location.Store, soyMilk);
        specificItem soyMilk4 = new specificItem(dateMilk4,false, Location.Store, soyMilk);
        Inventory.addSpecificItem(soyMilk, soyMilk1);
        Inventory.addSpecificItem(soyMilk, soyMilk3);
        Inventory.addSpecificItem(soyMilk, soyMilk3);
        Inventory.addSpecificItem(soyMilk, soyMilk4);
        soyMilk.addSpecificItem(soyMilk1);
        soyMilk.addSpecificItem(soyMilk2);
        soyMilk.addSpecificItem(soyMilk3);
        soyMilk.addSpecificItem(soyMilk4);

        specificItem butter1 = new specificItem(dateMilk1, false, Location.Store, regularButter);
        specificItem butter2 = new specificItem(dateMilk2, false, Location.Store, regularButter);
        Inventory.addSpecificItem(regularButter, butter1);
        Inventory.addSpecificItem(regularButter, butter2);
        regularButter.addSpecificItem(butter1);
        regularButter.addSpecificItem(butter2);

        specificItem toiletPaper1 = new specificItem(null, false, Location.Store, toiletPaper);
        specificItem toiletPaper2 = new specificItem(null, false, Location.Store, toiletPaper);
        specificItem toiletPaper3 = new specificItem(null, false, Location.Store, toiletPaper);
        Inventory.addSpecificItem(toiletPaper, toiletPaper1);
        Inventory.addSpecificItem(toiletPaper, toiletPaper2);
        Inventory.addSpecificItem(toiletPaper, toiletPaper3);
        toiletPaper.addSpecificItem(toiletPaper1);
        toiletPaper.addSpecificItem(toiletPaper2);
        toiletPaper.addSpecificItem(toiletPaper3);

        specificItem sodGel1 = new specificItem(null, false, Location.Store, sodGel);
        specificItem sodGel2 = new specificItem(null, true, Location.Storage, sodGel);
        Inventory.addSpecificItem(sodGel, sodGel1);
        Inventory.addSpecificItem(sodGel, sodGel2);
        sodGel.addSpecificItem(sodGel1);
        sodGel.addSpecificItem(sodGel2);


        milksSubCat.addGeneralItem(milk3Percent);
        milksSubCat.addGeneralItem(soyMilk);
        buttersSubCat.addGeneralItem(regularButter);
        toiletSubCat.addGeneralItem(toiletPaper);
        washingMachineSubCat.addGeneralItem(sodGel);

        /*
        Inventory.addGeneralItem(milk3Percent);
        Inventory.addGeneralItem(soyMilk);
        Inventory.addGeneralItem(regularButter);
        Inventory.addGeneralItem(toiletPaper);
        Inventory.addGeneralItem(sodGel);

         */

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
            System.out.println("7: Provide a price history report for a product");
            System.out.println("8: Insert a defective product into the defective inventory");
            System.out.println("9: Print full inventory report");

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
                                Reports.addReport(Inventory.shortageReportGeneralItem(productNumber));
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
                    boolean flag = true;
                    while (flag) {
                        System.out.println("Which of the following would you choose:");
                        System.out.println("1: Create new category");
                        System.out.println("2: Create new subcategory");
                        System.out.println("3: Create new general Item");
                        System.out.println("4: Add a new specific item");
                        System.out.println("5: Delete category");
                        System.out.println("6: Delete general item");
                        System.out.println("7: Delete specific item");
                        System.out.println("8: Move a specific item");
                        System.out.println("0: Return to the Main Menu");

                        Scanner Second_obj = new Scanner(System.in);
                        String Sub_Ans = Second_obj.nextLine();  // Read user input

                        switch (Sub_Ans) {
                            //Create a new category
                            case "1":
                                System.out.println("What would you like to name the new category?");
                                Scanner categoryInput = new Scanner(System.in);
                                String categoryName = categoryInput.nextLine();
                                Inventory.getCategoryControl().addCategory(categoryName);
                                System.out.println("New category " + categoryName + " has been created!");
                                break;
                            //Create a new sub-category
                            case "2":
                                System.out.println("What is the name of your new sub-category?");
                                Scanner subCategoryInput = new Scanner(System.in);
                                String subCategoryName = subCategoryInput.nextLine();
                                System.out.println("Which category will store this sub category?");
                                categoryInput = new Scanner(System.in);
                                categoryName = categoryInput.nextLine();
                                subCategory currentSubCategory = new subCategory(subCategoryName);
                                Inventory.getCategoryControl().addSubCategory(Inventory.getCategoryControl().getCategory(categoryName), currentSubCategory);
                                System.out.println("A new sub-category " + subCategoryName + " was added into category " + categoryName);
                                break;
                            //Create new general item
                            case "3":
                                //User's inputs for the general item
                                System.out.println("What is the name of the new general item?");
                                Scanner itemInput = new Scanner(System.in);
                                String itemName = itemInput.nextLine();
                                System.out.println("What is the catalog number for the new general item?");
                                itemInput = new Scanner(System.in);
                                String catalogNumber = itemInput.nextLine();
                                System.out.println("What is the category name for the new general item?");
                                itemInput = new Scanner(System.in);
                                String CategoryName = itemInput.nextLine();
                                System.out.println("What is the weight for the new general item?");
                                itemInput = new Scanner(System.in);
                                double itemWeight = Double.parseDouble(itemInput.nextLine());
                                System.out.println("Who is the manufacturer of the general item?");
                                itemInput = new Scanner(System.in);
                                String itemMan = itemInput.nextLine();
                                System.out.println("Is the general item A) Regular, B) Cold, C) Frozen?");
                                itemInput = new Scanner(System.in);
                                String itemTemp = itemInput.nextLine();
                                TempLevel currentTemp = Inventory.returnTempLevel(itemTemp);
                                //Check user's input is valid
                                if (currentTemp == null){
                                    System.out.println("Invalid temp level choice.");
                                    break;
                                }
                                System.out.println("What is the minimum quantity of the product?");
                                itemInput = new Scanner(System.in);
                                int itemSize = Integer.parseInt(itemInput.nextLine());
                                Item currentItem = new Item(itemName, catalogNumber, itemWeight, CategoryName, currentTemp, itemMan);
                                currentItem.setMinQuantity(itemSize);

                                //Add price history for item
                                System.out.println("What is the buying price of the item?");
                                itemInput = new Scanner(System.in);
                                double itemBuyPrice = Double.parseDouble(itemInput.nextLine());
                                System.out.println("What is the selling price of the item?");
                                itemInput = new Scanner(System.in);
                                double itemSellPrice = Double.parseDouble(itemInput.nextLine());

                                currentItem.addNewPrice(itemBuyPrice, itemSellPrice);

                                //Add item to sub category and category
                                System.out.println("To which category would you like to add the general item?");
                                itemInput = new Scanner(System.in);
                                String itemCat = itemInput.nextLine();
                                Category currentCategory = Inventory.getCategoryControl().getCategory(itemCat);

                                System.out.println("To which sub category would you like to add the general item?");
                                itemInput = new Scanner(System.in);
                                String itemSubCat = itemInput.nextLine();
                                subCategory currentSubCat = currentCategory.getSubCategoryByName(itemSubCat);
                                Inventory.addGeneralItem(currentItem);
                                currentSubCat.addGeneralItem(currentItem);

                                //Announce new product
                                System.out.println("New general item has been created!\nCategory: " + currentCategory.getCategoryName() +
                                        " Sub-Category: " + currentSubCat.getSubCategoryName());
                                System.out.println(currentItem.toString());

                                break;
                            //Add new specific item
                            case "4":

                                //User's inputs
                                Date currentDate = new Date();
                                currentDate = null;
                                System.out.println("What is the catalog of the general item?");
                                itemInput = new Scanner(System.in);
                                String itemCatalogNumber = itemInput.nextLine();
                                currentItem = Inventory.getItemByCatalogNumber(itemCatalogNumber);
                                System.out.println("Does you item have an expiration date? Y) Yes N) No");
                                itemInput = new Scanner(System.in);
                                String expirationAns = itemInput.nextLine();
                                if (!expirationAns.equals("Y") && !expirationAns.equals("N")){
                                    System.out.println("Invalid input");
                                    break;
                                }
                                if (expirationAns.equals("Y")){
                                    System.out.println("What is the expiration year?");
                                    itemInput = new Scanner(System.in);
                                    int itemYear = Integer.parseInt(itemInput.nextLine());
                                    System.out.println("What is the expiration month?");
                                    itemInput = new Scanner(System.in);
                                    int itemMonth = Integer.parseInt(itemInput.nextLine());
                                    System.out.println("What is the expiration day?");
                                    itemInput = new Scanner(System.in);
                                    int itemDay = Integer.parseInt(itemInput.nextLine());
                                    currentDate = new Date(itemYear, itemMonth, itemDay);
                                }
                                specificItem currentSpecificItem = new specificItem(currentDate, false, Location.Storage, currentItem);
                                Inventory.addSpecificItem(currentItem, currentSpecificItem);
                                //currentItem.addSpecificItem(currentSpecificItem);
                                System.out.println("New specific item has been added!");
                                System.out.println(currentItem.toString());


                                break;
                            //Delete category
                            case "5":

                                System.out.println("What is theCategory you would like to delete?");
                                Scanner Input = new Scanner(System.in);
                                String CatName = Input.nextLine();
                                if (Inventory.deleteCat(CatName)){
                                    System.out.println("Category" + CatName + " has been deleted.");
                                }
                                else {
                                    System.out.println("Could not find such an item.");
                                }

                                break;
                            //Delete general item
                            case "6":

                                System.out.println("What is the Catalog Number for the general item you wish to remove?");
                                Input = new Scanner(System.in);
                                itemCatalogNumber = Input.nextLine();
                                if (Inventory.deleteGeneralItem(itemCatalogNumber)){
                                    System.out.println("Item" + itemCatalogNumber + " has been deleted.");
                                }
                                else {
                                    System.out.println("Could not find such an item.");
                                }

                                break;
                            //Delete specific item
                            case "7":

                                System.out.println("What is the ID for the specific item you wish to remove?");
                                Input = new Scanner(System.in);
                                int serialNumber = Integer.parseInt(Input.nextLine());
                                if (Inventory.deleteSpecificItem(serialNumber)){
                                    System.out.println("Item" + serialNumber + " has been deleted.");
                                }
                                else {
                                    System.out.println("Could not find such an item.");
                                }


                                break;
                            //Move specific item
                            case "8":

                                System.out.println("What is the item ID for the specific item you wish to move?");
                                Input = new Scanner(System.in);
                                serialNumber = Integer.parseInt(Input.nextLine());
                                currentSpecificItem = Inventory.findSpecificItem(serialNumber);
                                currentSpecificItem.moveSpecificItem();
                                System.out.println("Item has been moved!");
                                System.out.println(currentSpecificItem.toString());

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
                                System.out.println("Discount by: P) Percentage, S) Standard");
                                Scanner kind = new Scanner(System.in);
                                String KindAnswer = kind.nextLine();
                                System.out.println("How much:");
                                double discountAmount = kind.nextDouble();
                                if (KindAnswer.equals("S"))
                                    Inventory.FullStandardDiscount(discountAmount);
                                if (KindAnswer.equals("P"))
                                    Inventory.FullPercentageDiscount(discountAmount);
                                break;

                            case "2":
                                Scanner Category = new Scanner(System.in);
                                System.out.println("For which category do you want to give discount:");
                                String CategoryAnswer = Category.nextLine();
                                System.out.println("Discount by: P) Percentage, S) Standard");
                                String kindAnswer = Category.nextLine();
                                System.out.println("How much:");
                                double discountAmountCat = Category.nextDouble();
                                if (kindAnswer.equals("S"))
                                    Inventory.CategoryStandardDiscount(discountAmountCat,CategoryAnswer);
                                if (kindAnswer.equals("P"))
                                    Inventory.CategoryPercentageDiscount(discountAmountCat,CategoryAnswer);
                                break;

                            case "3":
                                System.out.println("Please enter the catalog number of the item you would like to provide a discount:");
                                Scanner Product = new Scanner(System.in);
                                String CatalogNumber = Product.nextLine();
                                System.out.println("Discount by: P) Percentage, S) Standard");
                                String SpecificKindAnswer = Product.nextLine();
                                System.out.println("How much:");
                                double discountAmountSpecific = Product.nextDouble();
                                if (SpecificKindAnswer.equals("S"))
                                    Inventory.SpecificStandardDiscount(discountAmountSpecific, CatalogNumber);
                                if (SpecificKindAnswer.equals("P"))
                                    Inventory.SpecificPercentageDiscount(discountAmountSpecific, CatalogNumber);
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
                //Provide a price history report for a product
                //-----------------------
                case "7": {

                    System.out.println("What is the catalog number for the product?");
                    Scanner Input = new Scanner(System.in);
                    String catalogNumber = Input.nextLine();
                    Reports.addReportHistory(Inventory.priceHistoryReport(catalogNumber));


                    break;
                }

                //-----------------------
                //Insert a defective product into the defective inventory
                //-----------------------
                case "8": {

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
                //Print full inventory report
                //-----------------------
                case "9": {
                    //System.out.println(this.Inventory.toString());
                    this.Inventory.printAllItems();
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
