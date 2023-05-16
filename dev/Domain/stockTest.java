package Domain;
import DataAccesObject.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class stockTest {
    //Test 1: Check item's current price
    @Test
    void testCheckCurrentPrice(){
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");

        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        assertEquals(40, milk3Percent.getSellPrice());
    }
    //Test 2: Check Item's amount of price history updates
    @Test
    void testCheckPriceHistoryAmount(){
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");

        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);
        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        int amount = milk3Percent.getPriceHistorySize();

        assertEquals(4, milk3Percent.getPriceHistorySize());
    }
    //Test 4:
    @Test
    void testCheckCategoryRemoveSubCategoryMethod(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, "Butter");

        dairyCat.addSubCategory(milkSubCat);
        dairyCat.addSubCategory(butterSubCat);

        milkSubCat.addGeneralItem(milk3Percent);
        milkSubCat.addGeneralItem(soyMilk);
        butterSubCat.addGeneralItem(regularButter);

        dairyCat.removeSubCategory(milkSubCat);
        dairyCat.removeSubCategory(butterSubCat);

        int amountOfSubCategories = dairyCat.getAmount();

        assertEquals(0, amountOfSubCategories);
    }
    //Test 5:
    @Test
    void testCheckItemGetName(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, "Butter");

        dairyCat.addSubCategory(milkSubCat);
        dairyCat.addSubCategory(butterSubCat);

        milkSubCat.addGeneralItem(milk3Percent);
        milkSubCat.addGeneralItem(soyMilk);
        butterSubCat.addGeneralItem(regularButter);

        String milksGetName = milkSubCat.getSubCategoryName();

        assertTrue(milksGetName.equals("Milk"));
    }
    //Test 6:
    /*
    @Test
    void testCheckMoveSpecificItem(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, "Butter");

        Date date1 = new Date(2023, 10, 7);
        Date date2 = new Date(2024, 1, 9);

        specificItem milk1 = new specificItem(date1, false, Location.Store, milk3Percent);
        specificItem milk2 = new specificItem(date2, true, Location.Storage, milk3Percent);

        dairyCat.addSubCategory(milkSubCat);
        dairyCat.addSubCategory(butterSubCat);

        milkSubCat.addGeneralItem(milk3Percent);
        milkSubCat.addGeneralItem(soyMilk);
        butterSubCat.addGeneralItem(regularButter);

        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);

        milk1.moveSpecificItem();

        assertTrue(milk1.getLocationString().equals("Storage"));

    }

     */
    //Test 7:
    @Test
    void testCheckUpdatingNewPrice(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, "Butter");

        Date date1 = new Date(2023, 10, 7);
        Date date2 = new Date(2024, 1, 9);

        specificItem milk1 = new specificItem(date1, false, Location.Store, milk3Percent);
        specificItem milk2 = new specificItem(date2, true, Location.Storage, milk3Percent);

        dairyCat.addSubCategory(milkSubCat);
        dairyCat.addSubCategory(butterSubCat);

        milkSubCat.addGeneralItem(milk3Percent);
        milkSubCat.addGeneralItem(soyMilk);
        butterSubCat.addGeneralItem(regularButter);

        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);

        milk3Percent.addNewPrice(100, 200);

        assertEquals(200, milk3Percent.getSellPrice());
    }
    //Test 8:
    @Test
    void test8(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, "Butter");

        Date date1 = new Date(2023, 10, 7);
        Date date2 = new Date(2024, 1, 9);

        specificItem milk1 = new specificItem(date1, false, Location.Store, milk3Percent);
        specificItem milk2 = new specificItem(date2, true, Location.Storage, milk3Percent);

        dairyCat.addSubCategory(milkSubCat);
        dairyCat.addSubCategory(butterSubCat);

        milkSubCat.addGeneralItem(milk3Percent);
        milkSubCat.addGeneralItem(soyMilk);
        butterSubCat.addGeneralItem(regularButter);

        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);

        Item currentItem = milkSubCat.getItem(1);

        assertEquals(0, currentItem.getAmount());

    }
    /*
    //Test 9:
    @Test
    void testCheckFetchItem(){
        InventoryController Inventory = new InventoryController();

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

        Inventory.SpecificPercentageDiscount(50, "000123");

        assertEquals(20, milk3Percent.getSellPrice());

    }

     */
    /*
    //Test 10
    @Test
    void testCheckSpecificItemsLocation(){
        InventoryController Inventory = new InventoryController();

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

        assertTrue(sodGel1.getLocationString().equals("Store"));

    }

     */

    //===================Assignment2===================//

    //test #1
    @Test
    void testShortageOrder(){
        Supplier_Manger masupplier = new Supplier_Manger();
        masupplier.delte_all();
        OrderManger orderManger=new OrderManger();
        orderManger.delte_db();

        InventoryController Inventory = new InventoryController();
        Inventory.checkForShortageTask();
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
        item1.setMinQuantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
        item2.setMinQuantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");

        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");

        //the supplier and his coract person
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);


        //add the supplier to the supplier manger
        masupplier.add_supplier(supplier_1);

        //update the supplier list
        masupplier.update_suppliers();


        //add an item to the supplier
        masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
        //add a discount to the supplier
        masupplier.add_item_discount_to_supplier("Supplier1 Inc.","Apple",10,0.8);
        masupplier.update_suppliers();
        //add a second supplier and an item to him
        WindowType currentDeliveryDay =WindowType.day2;
        FixedDaySupplier supplier_2 = new FixedDaySupplier(currentDeliveryDay,"Supplier2 Inc.", "123446789", 1, "S0056", contactPerson, null, null);
        masupplier.add_supplier(supplier_2);
        masupplier.add_item_to_supplier(supplier_2.getName(),item2,100,10);

    }

    //test #2
    @Test
    void testGetNameCategory(){
        CategoryController categoryController = new CategoryController();

        categoryController.addCategory("Dairy");
        Category category = categoryController.getCategory("Dairy");
        String CategoryName = category.getCategoryName();

        assertEquals("Dairy", CategoryName);
    }

    //test #4
    @Test
    void testGetNameSubCategory(){
        CategoryController categoryController = new CategoryController();
        InventoryController inventoryController = new InventoryController();

        categoryController.addCategory("Dairy");
        subCategory milkSubCat = new subCategory("Milk");
        inventoryController.addSubCatToMapper("Dairy",milkSubCat);
        subCategory subcategory = inventoryController.getSubCategory("Milk");
        String subCategoryName = subcategory.getSubCategoryName();

        assertEquals("Milk", subCategoryName);
    }

    //test #5
    @Test
    void testCheckItemGetNameDB(){
        ItemMapper itemMapper = new ItemMapper();
        Item item = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
        itemMapper.insert(item);
        Item ItemSelected = itemMapper.findByCatalogNum("200");
        String ItemName = ItemSelected.getName();
        assertEquals("Milk", ItemName);
    }

    //test #6
    @Test
    void testReportTypeByReportNum() throws SQLException {
        ReportMapper reportMapper = new ReportMapper();
        InventoryController inventoryController = new InventoryController();
        inventoryController.FullDefectiveReport();
        Report report = reportMapper.getById("1");
        //String reportType = report.getType().toString();
        String reportType = report.getTypeString();


        assertEquals("Defective", reportType);
    }

}

