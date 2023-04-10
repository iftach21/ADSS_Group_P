import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
public class stockTest {
    //Test 1: Check item's current price
    @Test
    void testCheckCurrentPrice(){
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");

        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        assertEquals(40, milk3Percent.getSellPrice());
    }
    //Test 2: Check Item's amount of price history updates
    @Test
    void testCheckPriceHistoryAmount(){
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");

        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);
        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        int amount = milk3Percent.getPriceHistorySize();

        assertEquals(4, milk3Percent.getPriceHistorySize());
    }
    //Test 3:
    @Test
    void testCheckAmountOfSubCategories(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, 2, "Butter");

        dairyCat.addSubCategory(milkSubCat);
        dairyCat.addSubCategory(butterSubCat);

        milkSubCat.addGeneralItem(milk3Percent);
        milkSubCat.addGeneralItem(soyMilk);
        butterSubCat.addGeneralItem(regularButter);

        int amountOfSubCategories = dairyCat.getAmount();

        assertEquals(2, amountOfSubCategories);
    }
    //Test 4:
    @Test
    void testCheckCategoryRemoveSubCategoryMethod(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, 2, "Butter");

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

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, 2, "Butter");

        dairyCat.addSubCategory(milkSubCat);
        dairyCat.addSubCategory(butterSubCat);

        milkSubCat.addGeneralItem(milk3Percent);
        milkSubCat.addGeneralItem(soyMilk);
        butterSubCat.addGeneralItem(regularButter);

        String milksGetName = milkSubCat.getSubCategoryName();

        assertTrue(milksGetName.equals("Milk"));
    }
    //Test 6:
    @Test
    void testCheckMoveSpecificItem(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, 2, "Butter");

        Date date1 = new Date(2023, 10, 7);
        Date date2 = new Date(2024, 1, 9);

        specificItem milk1 = new specificItem(date1, false, Location.Store);
        specificItem milk2 = new specificItem(date2, true, Location.Storage);

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
    //Test 7:
    @Test
    void testCheckUpdatingNewPrice(){
        Category dairyCat = new Category("Dairy");

        subCategory milkSubCat = new subCategory("Milk");
        subCategory butterSubCat = new subCategory("Butter");

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, 2, "Butter");

        Date date1 = new Date(2023, 10, 7);
        Date date2 = new Date(2024, 1, 9);

        specificItem milk1 = new specificItem(date1, false, Location.Store);
        specificItem milk2 = new specificItem(date2, true, Location.Storage);

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

        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item soyMilk = new Item("Soy Milk", "000234", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        Item regularButter = new Item("Milk 3%", "000345", 2, "Tnuva", TempLevel.cold, 2, "Butter");

        Date date1 = new Date(2023, 10, 7);
        Date date2 = new Date(2024, 1, 9);

        specificItem milk1 = new specificItem(date1, false, Location.Store);
        specificItem milk2 = new specificItem(date2, true, Location.Storage);

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
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        Item soyMilk = new Item("Soy Milk", "000789", 2, "Tara", TempLevel.cold, 2, "Dairy");
        soyMilk.addNewPrice(30, 40);
        Item regularButter = new Item("Best Butter", "000666", 4, "Mama Mia", TempLevel.cold, 12, "Dairy");
        regularButter.addNewPrice(50, 60);

        Item toiletPaper = new Item("Lalin Toilet Paper", "111123", 10, "Lalin", TempLevel.regular, 20,"Cleaning");
        toiletPaper.addNewPrice(20, 40);
        Item sodGel = new Item("Sod Gel", "111567", 6, "Sod", TempLevel.regular, 10,"Cleaning");
        sodGel.addNewPrice(20, 26);

        //Specific Items
        specificItem milk1 = new specificItem(dateMilk1, false, Location.Store);
        specificItem milk2 = new specificItem(dateMilk2, true, Location.Storage);
        specificItem milk3 = new specificItem(dateMilk3, true, Location.Storage);
        specificItem milk4 = new specificItem(dateMilk4, true, Location.Storage);
        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);
        milk3Percent.addSpecificItem(milk3);
        milk3Percent.addSpecificItem(milk4);

        specificItem soyMilk1 = new specificItem(dateMilk1,false, Location.Store);
        specificItem soyMilk2 = new specificItem(dateMilk2,false, Location.Store);
        specificItem soyMilk3 = new specificItem(dateMilk3,false, Location.Store);
        specificItem soyMilk4 = new specificItem(dateMilk4,false, Location.Store);
        soyMilk.addSpecificItem(soyMilk1);
        soyMilk.addSpecificItem(soyMilk2);
        soyMilk.addSpecificItem(soyMilk3);
        soyMilk.addSpecificItem(soyMilk4);

        specificItem butter1 = new specificItem(dateMilk1, false, Location.Store);
        specificItem butter2 = new specificItem(dateMilk2, false, Location.Store);
        regularButter.addSpecificItem(butter1);
        regularButter.addSpecificItem(butter2);

        specificItem toiletPaper1 = new specificItem(null, false, Location.Store);
        specificItem toiletPaper2 = new specificItem(null, false, Location.Store);
        specificItem toiletPaper3 = new specificItem(null, false, Location.Store);
        toiletPaper.addSpecificItem(toiletPaper1);
        toiletPaper.addSpecificItem(toiletPaper2);
        toiletPaper.addSpecificItem(toiletPaper3);

        specificItem sodGel1 = new specificItem(null, false, Location.Store);
        specificItem sodGel2 = new specificItem(null, true, Location.Storage);
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
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 2, "Dairy");
        milk3Percent.addNewPrice(10, 20);
        milk3Percent.addNewPrice(30,40);

        Item soyMilk = new Item("Soy Milk", "000789", 2, "Tara", TempLevel.cold, 2, "Dairy");
        soyMilk.addNewPrice(30, 40);
        Item regularButter = new Item("Best Butter", "000666", 4, "Mama Mia", TempLevel.cold, 12, "Dairy");
        regularButter.addNewPrice(50, 60);

        Item toiletPaper = new Item("Lalin Toilet Paper", "111123", 10, "Lalin", TempLevel.regular, 20,"Cleaning");
        toiletPaper.addNewPrice(20, 40);
        Item sodGel = new Item("Sod Gel", "111567", 6, "Sod", TempLevel.regular, 10,"Cleaning");
        sodGel.addNewPrice(20, 26);

        //Specific Items
        specificItem milk1 = new specificItem(dateMilk1, false, Location.Store);
        specificItem milk2 = new specificItem(dateMilk2, true, Location.Storage);
        specificItem milk3 = new specificItem(dateMilk3, true, Location.Storage);
        specificItem milk4 = new specificItem(dateMilk4, true, Location.Storage);
        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);
        milk3Percent.addSpecificItem(milk3);
        milk3Percent.addSpecificItem(milk4);

        specificItem soyMilk1 = new specificItem(dateMilk1,false, Location.Store);
        specificItem soyMilk2 = new specificItem(dateMilk2,false, Location.Store);
        specificItem soyMilk3 = new specificItem(dateMilk3,false, Location.Store);
        specificItem soyMilk4 = new specificItem(dateMilk4,false, Location.Store);
        soyMilk.addSpecificItem(soyMilk1);
        soyMilk.addSpecificItem(soyMilk2);
        soyMilk.addSpecificItem(soyMilk3);
        soyMilk.addSpecificItem(soyMilk4);

        specificItem butter1 = new specificItem(dateMilk1, false, Location.Store);
        specificItem butter2 = new specificItem(dateMilk2, false, Location.Store);
        regularButter.addSpecificItem(butter1);
        regularButter.addSpecificItem(butter2);

        specificItem toiletPaper1 = new specificItem(null, false, Location.Store);
        specificItem toiletPaper2 = new specificItem(null, false, Location.Store);
        specificItem toiletPaper3 = new specificItem(null, false, Location.Store);
        toiletPaper.addSpecificItem(toiletPaper1);
        toiletPaper.addSpecificItem(toiletPaper2);
        toiletPaper.addSpecificItem(toiletPaper3);

        specificItem sodGel1 = new specificItem(null, false, Location.Store);
        specificItem sodGel2 = new specificItem(null, true, Location.Storage);
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
}

