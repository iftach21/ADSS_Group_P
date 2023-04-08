
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class OrderMangerTest {
    @Test
    void test_add_item_list(){
        var ordermanger=new OrderManger();
        Map<Item,Integer> maplist =new HashMap<Item,Integer>();

        Item item1 = new Item("Apple", "1001", new Date(123456789), 0.5, "Fruits", 10.0);
        Item item2 = new Item("Milk", "2002", new Date(234567890), 1.0, "Dairy", 4.0);
        Item item3 = new Item("Bread", "3003", new Date(345678901), 0.8, "Bakery", 25.0);
        Item item4 = new Item("Salmon", "4004", new Date(456789012), 0.3, "Seafood", -2.0);

        maplist.put(item1,100);
        maplist.put(item2,100);
        maplist.put(item3,100);
        maplist.put(item4,100);

        Supplier_Manger masupplier=new Supplier_Manger();
        Contact_Person contactPerson = new Contact_Person("John Smith", "555-1234");
        NonDeliveringSupplier supplier = new NonDeliveringSupplier("Supplier Inc.", "123456789", "Credit Card", "S001", contactPerson, null, null);
        supplier.add_Items(item1,100,100);
        supplier.add_Items(item2,100,100);
        supplier.add_Items(item3,100,100);
        supplier.add_Items(item4,100,100);

        masupplier.getSuppliers().add(supplier);
        ordermanger.assing_Orders_to_Suppliers(maplist,masupplier,20);

    }


}