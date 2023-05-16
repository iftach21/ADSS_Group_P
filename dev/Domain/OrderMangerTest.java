package Domain;

import DataAccesObject.*;
import DataAccesObject.ItemMapper;
import DataAccesObject.OrderMapper;

import org.junit.jupiter.api.Test;
import DataAccesObject.*;


import java.sql.*;
import java.util.*;

class OrderMangerTest {
    @Test
    void test_add_item_list_one_supplier() throws SQLException { //1
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
//        item1.setMinimum_quantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
//        item2.setMinimum_quantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
//        item3.setMinimum_quantity(3);
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
//        item4.setMinimum_quantity(2);


//        Connection conn1 = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
        Supplier_Manger masupplier = new Supplier_Manger();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonDeliveringSupplier supplier = new NonDeliveringSupplier("Supplier Inc.", "123456789", 1, "S001", contactPerson, null, null);
        DeliveringSupplier supplier1 = new FixedDaySupplier(WindowType.day2, "Supplier Inc.","2", 1,"S002",contactPerson,null,null);
        supplier.add_Items(item1,100,100);
        supplier.add_Items(item2,100,100);
        supplier.add_Items(item3,100,100);
        supplier.add_Items(item4,100,100);
//        Interface.NonDeliveringSupplierMapper mapper1 = new Interface.NonDeliveringSupplierMapper(conn1);
        NonDeliveringSupplierMapper mapper1 = new NonDeliveringSupplierMapper();

//        Connection conn2 = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");


//        DataAccesObject.ContractMapper contractMapperTest =new DataAccesObject.ContractMapper(conn2);
        ContractMapper contractMapperTest =new ContractMapper();
        Contract contractTest = new Contract();
        contractMapperTest.insert(contractTest);
        contractTest.add_to_contract(item1,100,50);
        contractTest.add_to_contract(item2,80,70);
        contractTest.setSupplierId("S002");
        contractTest.add_to_contract(item3,95,35);
        contractTest.setTotalDiscount(25);
        contractMapperTest.update(contractTest);

        supplier.add_item_to_contract(item1.getName(),100,50);
        supplier.add_item_to_contract(item2.getName(),420,69);
        supplier.add_item_to_contract(item3.getName(),96,96);
        supplier.add_item_to_contract(item4.getName(),54,45);
        contractMapperTest.update(supplier.getContract());
        Contract supplierIdContractTest = contractMapperTest.findBySupplierId("S001");
        Contract supplierIdContractTest1 = contractMapperTest.findByContractId(2);
        List<Contract> allContractsTest = contractMapperTest.findAll();
        System.out.println("---------------------");
//        DataAccesObject.OrderMapper orderMapperTest = new DataAccesObject.OrderMapper(conn2);
        OrderMapper orderMapperTest = new OrderMapper();

        Order orderTest = new Order();
        orderTest.setSupplier(supplier);

        orderTest.add_item(item1,420,69);
        orderTest.add_item(item2,300,20);
        orderTest.add_item(item3,45,6);
//        orderMapperTest.insert(orderTest);
        orderTest.add_item(item4,4,850);
//        orderMapperTest.update(orderTest);
//        Order orderTest1 = orderMapperTest.findByOrderNum("0");
//        List<Order> allOrdersTest = orderMapperTest.findAll();
        System.out.println("---------------------");
        orderMapperTest.delete(orderTest);

//        try{
//        mapper1.insert(supplier);}
//        catch(SQLException ignored){}
//        masupplier.getSuppliers().add(supplier);
//        ordermanger.assing_Orders_to_Suppliers(maplist,masupplier,20);
//
//        supplier.print_items();
//        ordermanger.getPending_for_apporval().get(0).print_items();
//        Item catalogItem = mapper.findByCatalogNum(item1.getCatalogNum());
//        NonDeliveringSupplier test = mapper1.findBySupplierId(supplier.getSupplierID());
//        supplier.add_item_to_contract(item1.getName(),10,0.5);
//        conn1.close();
//        conn.close();
////        con.insert(supplier.getContract());
//        mapper1.update(supplier);

    }



    ///cheak period order to see that its work
    @Test
    void test_add_item_list_two_supplier_split_orders_with_deals_on_amount(){
        //init an item mapper
        ItemMapper itemMapper=new ItemMapper();
        Supplier_Manger masupplier=new Supplier_Manger();
        masupplier.delte_all();
        masupplier.delte_all();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);
        masupplier.add_supplier(supplier_1);
        System.out.println(masupplier.getSuppliers().size());
        masupplier.update_suppliers();
        System.out.println(masupplier.getSuppliers().size());
//        assertEquals(1,masupplier.getSuppliers().size());
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
//        item1.setMinimum_quantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
//        item2.setMinimum_quantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
//        item3.setMinimum_quantity(3);
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,40);
        itemMapper.insert(item1);

        masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
        OrderManger orderManger = new OrderManger();
        orderManger.delte_db();

        Map<Item,Integer> maplist = new HashMap<Item,Integer>();
        maplist.put(item1,10);
        orderManger.period_order(masupplier.get_supplier_by_id(supplier_1.getSupplierID()),maplist,5,5);
        orderManger.print_all_orders_details();

        orderManger.delte_db();

        masupplier.delte_all();

    }



    //tetst 6  : This cheak that the order is move correctle thoa nother status
    @Test
    void move_order_to_approvel(){

        //init an item mapper
        ItemMapper itemMapper=new ItemMapper();
        Supplier_Manger masupplier=new Supplier_Manger();
        masupplier.delte_all();
        masupplier.delte_all();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);
        masupplier.add_supplier(supplier_1);
        System.out.println(masupplier.getSuppliers().size());
        masupplier.update_suppliers();
        System.out.println(masupplier.getSuppliers().size());
//        assertEquals(1,masupplier.getSuppliers().size());
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
//        item1.setMinimum_quantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
//        item2.setMinimum_quantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
//        item3.setMinimum_quantity(3);
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,40);
        itemMapper.insert(item1);

        masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
        OrderManger orderManger = new OrderManger();

        Map<Item,Integer> maplist = new HashMap<Item,Integer>();
        maplist.put(item1,10);

        orderManger.assing_Orders_to_Suppliers(maplist,masupplier,50);
        masupplier.add_item_discount_to_supplier("Supplier1 Inc." ,item1.getName(),10,0.4);
        orderManger.move_from_pending_to_approved(1);




        masupplier.delte_all();




    }
    // test 7 this Test is to
    @Test
    void three_supllier_split(){
        //init an item mapper
        ItemMapper itemMapper=new ItemMapper();
        Supplier_Manger masupplier=new Supplier_Manger();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);
        masupplier.add_supplier(supplier_1);
        System.out.println(masupplier.getSuppliers().size());
        masupplier.update_suppliers();
        System.out.println(masupplier.getSuppliers().size());
//        assertEquals(1,masupplier.getSuppliers().size());
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
//        item1.setMinimum_quantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
//        item2.setMinimum_quantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
//        item3.setMinimum_quantity(3);
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,40);
        itemMapper.insert(item1);

        masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
        OrderManger orderManger = new OrderManger();

        Map<Item,Integer> maplist = new HashMap<Item,Integer>();
        maplist.put(item1,10);

        orderManger.assing_Orders_to_Suppliers(maplist,masupplier,50);
        masupplier.add_item_discount_to_supplier("Supplier1 Inc." ,item1.getName(),10,0.4);




        masupplier.delte_all();




    }
    //test 8
    @Test
    void order_move_to_history(){
        Supplier_Manger masupplier=new Supplier_Manger();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        System.out.println(masupplier.getSuppliers().size());
        masupplier.update_suppliers();
        System.out.println(masupplier.getSuppliers().size());
//        assertEquals(1,masupplier.getSuppliers().size());
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
//        item1.setMinimum_quantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
//        item2.setMinimum_quantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
//        item3.setMinimum_quantity(3);
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
        System.out.println(masupplier.get_supplier_by_id("S0016").getItems().size());

        OrderManger orderManger=new OrderManger();


        Map<Item,Integer> maplist = new HashMap<Item,Integer>();
        maplist.put(item1,10);
        //This is to add a peiod order
        orderManger.period_order(masupplier.get_supplier_by_id("S0016"),maplist,20,40);


        masupplier.delte_all();

    }
    @Test
        //test 9
    void manipule_supplier(){
        Supplier_Manger masupplier=new Supplier_Manger();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);
        masupplier.add_supplier(supplier_1);
        System.out.println(masupplier.getSuppliers().size());
        masupplier.update_suppliers();
        System.out.println(masupplier.getSuppliers().size());
//        assertEquals(1,masupplier.getSuppliers().size());
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
//        item1.setMinimum_quantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
//        item2.setMinimum_quantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
//        item3.setMinimum_quantity(3);
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
        masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
        masupplier.add_item_discount_to_supplier("Supplier1 Inc.","Apple",10,0.8);
        masupplier.update_suppliers();
        WindowType currentDeliveryDay =WindowType.day2;
        FixedDaySupplier supplier_2 = new FixedDaySupplier(currentDeliveryDay,"Supplier2 Inc.", "123446789", 1, "S0056", contactPerson, null, null);
        masupplier.add_supplier(supplier_2);
        masupplier.add_item_to_supplier(supplier_2.getName(),item2,100,10);
        NonDeliveringSupplier supplier_3 =new NonDeliveringSupplier("Supplier3 Inc.", "143446789", 1, "S4056", contactPerson, null, null);
        masupplier.add_supplier(supplier_3);
        masupplier.remove_supplier(supplier_2.getName());



        masupplier.delte_all();

    }
    //test 10
    @Test
    void test_only_supplier_exist(){
        ItemMapper itemMapper=new ItemMapper();
        Supplier_Manger masupplier=new Supplier_Manger();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);
        masupplier.add_supplier(supplier_1);
        System.out.println(masupplier.getSuppliers().size());
        masupplier.update_suppliers();
        System.out.println(masupplier.getSuppliers().size());
//        assertEquals(1,masupplier.getSuppliers().size());
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
//        item1.setMinimum_quantity(10);
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
//        item2.setMinimum_quantity(5);
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
//        item3.setMinimum_quantity(3);
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,25);
        item1.addNewPrice(10,40);
        itemMapper.insert(item1);
        WindowType currentDeliveryDay =WindowType.day2;
        FixedDaySupplier supplier_2 = new FixedDaySupplier(currentDeliveryDay,"Supplier2 Inc.", "123446789", 1, "S0056", contactPerson, null, null);

        NonDeliveringSupplier supplier_3 =new NonDeliveringSupplier("Supplier3 Inc.", "143446789", 1, "S4056", contactPerson, null, null);
        masupplier.add_supplier(supplier_3);

        masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
        OrderManger orderManger = new OrderManger();

        Map<Item,Integer> maplist = new HashMap<Item,Integer>();

        orderManger.assing_Orders_to_Suppliers(maplist,masupplier,50);
        itemMapper.findAll().get(0).print_item();
        itemMapper.findAll().get(1).print_item();



        masupplier.delte_all();

    }

    @Test
    void testHistoryParser()
    {
        ItemMapper itemMapper=new ItemMapper();
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
        itemMapper.insert(item1);
        item1.addNewPrice(10,25);
        itemMapper.update(item1);
        item1.addNewPrice(5,78);
        itemMapper.update(item1);
        item1.addNewPrice(420,69);
        itemMapper.update(item1);
        item1.addNewPrice(69,420);
        itemMapper.update(item1);
        item1.addNewPrice(80085,80085);
        itemMapper.update(item1);

        Item itemTest = itemMapper.findByCatalogNum("100");
        System.out.println(itemTest);


    }
    @Test
    void testAddItems()
    {
        Supplier_Manger masupplier = new Supplier_Manger();
        ContactPerson contactPerson = new ContactPerson("John Smith", "555-1234");
        NonFixedDaySupplier supplier_1 = new NonFixedDaySupplier(1,"Supplier1 Inc.", "123456789", 1, "S0016", contactPerson, null, null);
        masupplier.add_supplier(supplier_1);
        masupplier.update_suppliers();
        Item item1 = new Item("Apple", "100", 0.5, "Fruits", TempLevel.cold, "Green Farms");
        Item item2 = new Item("Milk", "200", 1.0, "Dairy", TempLevel.cold, "Happy Cow Dairy");
        Item item3 = new Item("Bread", "300", 1.0, "Bakery", TempLevel.cold, "Whole Grain Bakers");
        Item item4 = new Item("Chicken", "400", 2.0, "Meat", TempLevel.cold, "Fresh Farms");
        masupplier.add_item_to_supplier("Supplier1 Inc.",item1,100,100);
        ItemMapper itemMapper=new ItemMapper();
        itemMapper.insert(item1);
        item1.addNewPrice(10,25);
        itemMapper.update(item1);
        item1.addNewPrice(5,78);
        itemMapper.update(item1);
        item1.addNewPrice(420,69);
        itemMapper.update(item1);
        item1.addNewPrice(69,420);
        itemMapper.update(item1);
        item1.addNewPrice(80085,80085);
        itemMapper.update(item1);
        masupplier.add_item_to_supplier("Supplier1 Inc.",item2,69,420);
        masupplier.add_item_to_supplier("Supplier1 Inc.",item3,7,85);
        masupplier.add_item_to_supplier("Supplier1 Inc.",item4,12,45);
        NonFixedDaySupplier supplierTes = (NonFixedDaySupplier) masupplier.get_supplier_by_id("S0016");
        System.out.println(supplierTes);

    }

}