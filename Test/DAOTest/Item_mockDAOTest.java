package DAOTest;

import DataAccesObjects.Transfer.Item_mockDAO;
import Domain.Enums.TempLevel;
import Domain.Transfer.Item_mock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class Item_mockDAOTest {
    Item_mock item1;
    Item_mock item2;

    Item_mockDAO item_mockDAO;

    public Item_mockDAOTest() throws SQLException {
        item_mockDAO = Item_mockDAO.getInstance();
    }

    @BeforeEach
    void createMockTrucks()
    {
        //clear database before each Test
        item_mockDAO.deleteAll();

        //create 2 items
        this.item1 = new Item_mock("1", TempLevel.regular, "Chocolate chips");
        this.item2 = new Item_mock("4", TempLevel.cold, "Cheddar Cheese");
    }

    @Test
    void addAndGetItemsTest(){

        item_mockDAO.add(item1);
        Assertions.assertEquals(item1.getCatalogNum(), item_mockDAO.get(item1.getCatalogNum()).getCatalogNum());
        Assertions.assertEquals(item1.getItemName(), item_mockDAO.get(item1.getCatalogNum()).getItemName());
        Assertions.assertEquals(item1.getItemTemp(), item_mockDAO.get(item1.getCatalogNum()).getItemTemp());


        item_mockDAO.add(item2);
        Assertions.assertEquals(item2.getCatalogNum(), item_mockDAO.get(item2.getCatalogNum()).getCatalogNum());
        Assertions.assertEquals(item2.getItemName(), item_mockDAO.get(item2.getCatalogNum()).getItemName());
        Assertions.assertEquals(item2.getItemTemp(), item_mockDAO.get(item2.getCatalogNum()).getItemTemp());
    }

    @Test
    void deleteTruckTest(){
        item_mockDAO.add(item1);
        item_mockDAO.delete(item1.getCatalogNum());
        Assertions.assertNull(item_mockDAO.get(item1.getCatalogNum()));

        item_mockDAO.add(item2);
        item_mockDAO.delete(item2.getCatalogNum());
        Assertions.assertNull(item_mockDAO.get(item2.getCatalogNum()));
    }

    @Test
    void updateTruckTest(){

        item_mockDAO.add(item1);
        item1.updateItemName("Pasta");
        item_mockDAO.update(item1);
        Assertions.assertEquals("Pasta", item_mockDAO.get(item1.getCatalogNum()).getItemName());

        item_mockDAO.add(item2);
        item2.updateItemTemp(TempLevel.frozen);
        item_mockDAO.update(item2);
        Assertions.assertEquals(TempLevel.frozen, item_mockDAO.get(item2.getCatalogNum()).getItemTemp());

    }
}
