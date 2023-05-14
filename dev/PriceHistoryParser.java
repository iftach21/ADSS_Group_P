import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceHistoryParser {
    public List<PriceHistory> parse(String input) {
        List<PriceHistory> priceHistoryList = new ArrayList<>();

        // Remove the square brackets from the input string
        input = input.replace("[", "").replace("]", "");

        // Split the input string into individual price history entries
        String[] entries = input.split(", ");

        // Iterate over each entry and parse the values
        for (String entry : entries) {
            // Extract and parse the buy price
            double buyPrice = getValueFromEntry1(entry, "Buy price");

            // Extract and parse the sell price
            double sellPrice = getValueFromEntry1(entry, "sell price");

            // Extract and parse the date
            String dateString = getValueFromEntry2(entry, "updated in");
            Date currentDate;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                currentDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                // Handle parse exception if required
                e.printStackTrace();
                continue;
            }

            // Create PriceHistory object and add it to the list
            PriceHistory priceHistory = new PriceHistory(buyPrice, sellPrice, currentDate);
            priceHistoryList.add(priceHistory);
        }

        return priceHistoryList;
    }

    private double getValueFromEntry1(String entry, String key) {
        // Extract the value associated with the given key from the entry
        int startIndex = entry.indexOf(key) + key.length() + 2;
        int endIndex = entry.indexOf(",", startIndex);
        String valueString = entry.substring(startIndex, endIndex);

        // Parse and return the value
        return Double.parseDouble(valueString);
    }
    private String getValueFromEntry2(String entry, String key) {
        // Extract the value associated with the given key from the entry
        int startIndex = entry.indexOf(key) + key.length() + 2;
        int endIndex = entry.indexOf(",", startIndex);
        String valueString = entry.substring(startIndex, endIndex).trim();

        // Return the value
        return valueString;
    }
}