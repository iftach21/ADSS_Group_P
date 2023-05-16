package Domain;

import DataAccesObject.FixedDaySupplierMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.EnumMap;
import java.util.Map;


public class FixedDaySupplier extends DeliveringSupplier {
    private WindowType currentDeliveryDay;
    public static FixedDaySupplierMapper fixedDaySupplierMapper;
    public FixedDaySupplier(WindowType currentDeliveryDay, String name, String business_id, int payment_method, String suplier_ID, ContactPerson person, Contract contract, Map<Item,Pair<Integer, Float>> items)
    {
        super(name, business_id, payment_method, suplier_ID, person, contract, items);
        this.currentDeliveryDay = currentDeliveryDay;
        Connection conn = null;
        try
        {
            String url = "jdbc:sqlite:res/SuperLeeDataBase.db";
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException ignored) {}
        finally
        {
            try {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ignored) {}
        }

        if(fixedDaySupplierMapper == null)
        {
//            fixedDaySupplierMapper = new DataAccesObject.FixedDaySupplierMapper(conn);
            fixedDaySupplierMapper = new FixedDaySupplierMapper();

        }
    }

    public WindowType getCurrentDeliveryDay() {
        return currentDeliveryDay;
    }


    public void setCurrentDeliveryDay(WindowType currentDeliveryDay) {
        this.currentDeliveryDay = currentDeliveryDay;
    }

    @Override
    public int get_days(){
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a mapping between WindowType and DayOfWeek
        Map<WindowType, DayOfWeek> dayOfWeekMap = new EnumMap<>(WindowType.class);
        dayOfWeekMap.put(WindowType.day1, DayOfWeek.MONDAY);
        dayOfWeekMap.put(WindowType.day2, DayOfWeek.TUESDAY);
        dayOfWeekMap.put(WindowType.day3, DayOfWeek.WEDNESDAY);
        dayOfWeekMap.put(WindowType.day4, DayOfWeek.THURSDAY);
        dayOfWeekMap.put(WindowType.day5, DayOfWeek.FRIDAY);
        dayOfWeekMap.put(WindowType.day6, DayOfWeek.SATURDAY);
        dayOfWeekMap.put(WindowType.day7, DayOfWeek.SUNDAY);

        // Find the next occurrence of the current delivery day
        DayOfWeek deliveryDayOfWeek = dayOfWeekMap.get(currentDeliveryDay);
        LocalDateTime nextDeliveryDateTime = currentDateTime.with(TemporalAdjusters.next(deliveryDayOfWeek));

        // Calculate the duration until the next delivery
        long secondsUntilDelivery = currentDateTime.until(nextDeliveryDateTime, ChronoUnit.SECONDS);

        return (int) (secondsUntilDelivery / (24 * 60 * 60)); // Convert seconds to days
    }
    public int getType(){
        return 0;
    }
}


