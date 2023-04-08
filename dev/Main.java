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

        //New dates
        Date dateMilk1 = new Date(2023, 10, 7);
        Date dateMilk2 = new Date(2024, 1, 9);
        Date dateMilk3 = new Date(2025, 5, 11);
        Date dateMilk4 = new Date(2026, 6, 13);

        //Inventory
        CategoryController Inventory = new CategoryController();

        //Categories
        Category dairyCategory = new Category("Dairy");
        Inventory.addCategory("Dairy");

        //Sub-categories
        subCategory milksSubCat = new subCategory("Milk");
        subCategory buttersSubCat = new subCategory("Butter");
        Inventory.addSubCategory(dairyCategory, milksSubCat);
        Inventory.addSubCategory(dairyCategory, buttersSubCat);

        //General Items
        Item milk3Percent = new Item("Milk 3%", "000123", 2, "Tnuva", TempLevel.cold, 10);
        milk3Percent.addNewPrice(10, 20);
        Item soyMilk = new Item("Soy Milk", "000789", 2, "Tara", TempLevel.cold, 8);
        soyMilk.addNewPrice(30, 40);
        Item regularButter = new Item("Best Butter", "000666", 4, "Mama Mia", TempLevel.cold, 12);
        regularButter.addNewPrice(50, 60);

        //Specific Items
        specificItem milk1 = new specificItem(1, dateMilk1, false, Location.Store);
        specificItem milk2 = new specificItem(2, dateMilk2, false, Location.Store);
        specificItem milk3 = new specificItem(3, dateMilk3, false, Location.Store);
        specificItem milk4 = new specificItem(4, dateMilk4, false, Location.Store);
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

        milksSubCat.addGeneralItem(milk3Percent);
        milksSubCat.addGeneralItem(soyMilk);
        buttersSubCat.addGeneralItem(regularButter);

        System.out.println(Inventory.toString());

    }

}
