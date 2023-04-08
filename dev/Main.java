import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // Interface and user login prompt
        // -------------------------------------------------------
        /*
        Interface in = new Interface();
        in.logIn();
         */
        // -------------------------------------------------------
        // Demo categories, general items, and specific items for demonstration
        // -------------------------------------------------------

        //Super-categories
        Category dairyCategory = new Category("Dairy");

        //Sub-categories
        subCategory milksSubCat = new subCategory("Milk");
        subCategory buttersSubCat = new subCategory("Butter");

        //Inventory
        Map<Category, subCategory> storeInventory = new HashMap<>();
        Map<Category, subCategory> warehouseInventory = new HashMap<>();
        Map<Category, subCategory> defectedInventory = new HashMap<>();

        //New dates
        Date dateMilk1 = new Date(2023, 10, 7);
        Date dateMilk2 = new Date(2024, 1, 9);
        Date dateMilk3 = new Date(2025, 5, 11);
        Date dateMilk4 = new Date(2026, 6, 13);

        //New general-items
        Item milk3Percent = new Item("Milk 3%", "0001234567", 2, "Tnuva",
                TempLevel.cold, 10);
        Item milk1Percent = new Item("Milk 1%", "1111234567", 2, "Tara",
                TempLevel.cold, 6);

        //New specific items (MILK 3%)
        specificItem milk1 = new specificItem(1, dateMilk1, false, Location.Store);
        specificItem milk2 = new specificItem(2, dateMilk2, false, Location.Store);
        specificItem milk3 = new specificItem(3, dateMilk3, false, Location.Store);
        specificItem milk4 = new specificItem(4, dateMilk4, false, Location.Store);

        specificItem milk5 = new specificItem(1, dateMilk1, false, Location.Store);
        specificItem milk6 = new specificItem(2, dateMilk2, false, Location.Store);
        specificItem milk7 = new specificItem(3, dateMilk3, false, Location.Store);
        specificItem milk8 = new specificItem(4, dateMilk4, false, Location.Store);


        milk3Percent.addSpecificItem(milk1);
        milk3Percent.addSpecificItem(milk2);
        milk3Percent.addSpecificItem(milk3);
        milk3Percent.addSpecificItem(milk4);

        milk1Percent.addSpecificItem(milk5);
        milk1Percent.addSpecificItem(milk6);
        milk1Percent.addSpecificItem(milk7);
        milk1Percent.addSpecificItem(milk8);

        milk3Percent.addNewPrice(1, 2);
        milk1Percent.addNewPrice(3, 4);

        storeInventory.put(dairyCategory, milksSubCat);
        storeInventory.put(dairyCategory, buttersSubCat);

        milksSubCat.addGeneralItem(milk1Percent);
        milksSubCat.addGeneralItem(milk3Percent);
        dairyCategory.addSubCategory(milksSubCat);
        dairyCategory.addSubCategory(buttersSubCat);

        System.out.println(dairyCategory.toString());

        //System.out.println(milksSubCat.toString());

        //System.out.println(milk3Percent.toString());


    }

}
