import java.util.Date;

public class Main {
    public static void main(String[] args) {

        //New items
        

        //Create Dates
        Date dateMilk1 = new Date(2023, 10, 7);
        Date dateMilk2 = new Date(2024, 1, 9);
        Date dateMilk3 = new Date(2025, 5, 11);
        Date dateMilk4 = new Date(2026, 6, 13);

        //New specific items (MILK 3%)
        specificItem milk1 = new specificItem(1, dateMilk1, false, Location.Store);
        specificItem milk2 = new specificItem(2, dateMilk2, false, Location.Store);
        specificItem milk3 = new specificItem(3, dateMilk3, false, Location.Store);
        specificItem milk4 = new specificItem(4, dateMilk4, false, Location.Store);

        System.out.println(milk1.toString());
        System.out.println(milk2.toString());
        System.out.println(milk3.toString());
        System.out.println(milk4.toString());

        /*
        Interface in = new Interface();
        in.logIn();
         */

    }

}
