package Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PriceHistoryParser {
    public List<PriceHistory> parse(String input) {
        List<PriceHistory> priceHistoryList = new ArrayList<>();

        // Remove the square brackets from the input string
        input = input.replace("[", "").replace("]", "");

        // Split the input string into individual price history entries
        String[] entries = input.split(", ");
        for (int i=0; i<(entries.length);i++) {
            double buyPrice = (double) getValueFromEntry(entries[i], "Buy price");
            i++;
            double sellPrice = (double) getValueFromEntry(entries[i], "sell price");
            i++;
            String temp =entries[i].replace("\\n", "").trim();
            String dateString = (String) getValueFromEntry(entries[i].substring(0,temp.length()-1), "updated in");
            Date currentDate;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("en"));
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

    private Object getValueFromEntry(String entry, String key) {
        // Extract the value associated with the given key from the entry
        int startIndex = entry.indexOf(key) + key.length() + 2;
        int endIndex = entry.length()-1;
        String valueString = entry.substring(startIndex, endIndex).trim();

        // Try to parse the value as a double
        try {
            return Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            // Return the value as a string if parsing as double fails
            return valueString;
        }
    }
}