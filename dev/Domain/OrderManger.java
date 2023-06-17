package Domain;
import DataAccesObject.*;

import DataAccesObject.OrderMapper;
import DataAccesObject.PeriodicOrderMapper;

import java.util.*;
//this class is responsible for managing orders and periodic orders in a system.
public class OrderManger {


    private OrderMapper orderMapper;
    private PeriodicOrderMapper periodicOrderMapper;



    ///the constructor initializes the DataAccesObject.OrderMapper and DataAccesObject.PeriodicOrderMapper ,
    // which handle the database operations for regular orders and periodic orders, respectively.
    // it also creates a new order and periodic order objects and sets their numbers based on existing orders in the database.
    public OrderManger() {

        try
        {
//            Connection conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDataBase.db");
//            orderMapper = new DataAccesObject.OrderMapper(conn);
            orderMapper = new OrderMapper();
//            periodicOrderMapper=new DataAccesObject.PeriodicOrderMapper(conn);
            periodicOrderMapper = new PeriodicOrderMapper();
            Order order = new Order();
            if(orderMapper.findAll().size()!= 0)
            {
            order.set_number(orderMapper.findAll().get(orderMapper.findAll().size()-1).getOrderNum());}
            Period_Order periodOrders = new Period_Order(5);
            if(periodicOrderMapper.findAll().size()!= 0)
            {
                periodOrders.setOrdernumber(periodicOrderMapper.findAll().get(periodicOrderMapper.findAll().size()-1).getOrderNum());}
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public List<Order> getApprovedOrders()
    {
        return orderMapper.findAllOrderWithStatus("Approved");
    }



    public List<Order> getPendingForApproval()
    {
        return orderMapper.findAllOrderWithStatus("Waiting");
    }




    public List<Order> getOrdersHistory()
    {
        return orderMapper.findAllOrderWithStatus("History");
    }


    ///Updates the status of an order from approved to history by
    // the order with the specified order number from the DataAccesObject.OrderMapper and updating its status.
    public void move_from_pending_to_approved(int order_num)
    {
        Order updateOrder = orderMapper.findByOrderNum(Integer.toString(order_num));
        updateOrder.setStatusOrder(StatusOrder.Approved);
        orderMapper.update(updateOrder);


    }


    //Updates the status of an order from approved to history
    // by retrieving the order with the specified order number from the DataAccesObject.OrderMapper and updating its status.
    public void moveFromApprovalToHistory(Order order)
    {
        Order updateOrder = orderMapper.findByOrderNum(Integer.toString(order.getOrderNum()));
        updateOrder.setStatusOrder(StatusOrder.History);
        orderMapper.update(order);

    }



    //assigns orders to suppliers based on the provided item list, supplier manager,
    // and store number. It iterates through the suppliers, checks if they have the required items,
    // and selects the supplier with the lowest cost. It then creates an order and inserts it into the database
    // using the DataAccesObject.OrderMapper. If there are remaining items to be assigned, the function calls itself recursively
    // to assign them to other suppliers
    // . returns true if the assignment is successful, false if there are no suppliers available.
    public Boolean assing_Orders_to_Suppliers(Map<Item,Integer> itemlist, Supplier_Manger manger,int number_store)
    {
        //if there is no supplier do nothing
        if(manger == null)
        {
            return false;
        }
        manger.sort_supplier_by_deliver_days();
        //to find the min supplier(in case of one supplier have it all)
        Supplier min_sup=null;
        Order min_order = null;
        float min_cost = 0;
        //to cheak who have the max items in each call to the function
        int number_of_item=0;
        int trigger=0;
        int no_match=0;
        int min_number_days;


        for(Supplier supplier: manger.getSuppliers()){

            Set<Item> commonElements = new HashSet<>(supplier.getItems().keySet());
            commonElements.retainAll(itemlist.keySet());

            int numOfCommonElements = commonElements.size();

            if(numOfCommonElements > 0 && (number_of_item<=numOfCommonElements))
            {

                for(Item item:itemlist.keySet())
                {
                    if(supplier.getItems().containsKey(item))
                    {

                        if(itemlist.get(item)>supplier.getItems().get(item).getFirst())
                        {
                            trigger = 1;
                        }

                    }
                }

                //if the supplier dont have the right amount;
                if(trigger == 1)
                {
                    continue;
                }

                if(min_sup == null)
                {
                    no_match=1;
                    min_sup=supplier;
                }

                //make a new order to be cheak if there is better
                Order order = new Order(null,supplier,0,number_store);
                for(Item item :itemlist.keySet())
                {
                    if(supplier.getItems().containsKey(item))
                    {

                    double discount = 1;
                    int amount=itemlist.get(item);
                    float base_price = supplier.getItems().get(item).getSecond();
                    int new_amount=0;
                    float cost = 0;
                    //if the supplier give a discount on the item serch for the biggest amount
                    if(supplier.getContract().itemsMapDiscount.containsKey(item))
                    {
                        //cheak how much max amount have a discount from the curr amount
                        for(int i = 0; i < amount ;i++)
                        {
                            if(supplier.getContract().itemsMapDiscount.get(item).containsKey(amount-i)){
                                discount = supplier.getContract().itemsMapDiscount.get(item).get(amount-i);
                                new_amount = amount-i;
                                break;
                            }
                        }
                    }
                    //add the item to the total price o
                    cost = (float) (new_amount*base_price*discount + ((amount-new_amount)*base_price));
                    order.add_item(item,amount,cost);
                }
            }
               if(number_of_item < numOfCommonElements)
               {
                   number_of_item = numOfCommonElements;
                   min_sup = supplier;
                   min_cost = order.getCost();
                   min_order = order;
               }
               else
               {
                   if(min_cost>order.getCost())
                   {
                       min_sup=supplier;
                       min_cost=order.getCost();
                       min_order=order;

                   }
               }

            }
        }
        if(no_match==0)
        {
            return false;
        }

        //add it to the order's
//        pending_for_apporval.add(min_order);
        orderMapper.insert(min_order);
        Iterator<Item> iter = itemlist.keySet().iterator();
        while (iter.hasNext())
        {
            Item item = iter.next();
            if (min_sup.getItems().containsKey(item))
            {
                iter.remove();
            }
        }
        //reucsive call to cheak to supplie the other items
        if(itemlist.keySet().size()>0)
        {
            assing_Orders_to_Suppliers(itemlist,manger,number_store);
        }
        else
        {
            return true;
        }

        return true;
    }







    //Prints the order numbers for all pending, approved, and historical orders.
    public void print_all_orders_num()
    {
        System.out.println("pending:");
        for(Order order: orderMapper.findAllOrderWithStatus("Waiting"))
        {
            System.out.println(order.getOrderNum());
        }
        System.out.println("approved:");
        for(Order order: orderMapper.findAllOrderWithStatus("Approved"))
        {
            System.out.println(order.getOrderNum());
        }
        System.out.println("history:");
        for(Order order: orderMapper.findAllOrderWithStatus("History"))
        {
            System.out.println(order.getOrderNum());
        }


    }



    //Prints the details of all pending, approved, and historical orders.
    public void print_all_orders_details()
    {
        System.out.println("periodOrder:");
        for(Period_Order periodOrder: periodicOrderMapper.findAll())

        {
            periodOrder.print_order_detail();
        }

        System.out.println("pending:");

        for(Order order: orderMapper.findAllOrderWithStatus("Waiting"))

        {
            order.print_order_detail();
        }
        System.out.println("approved:");
        for(Order order: orderMapper.findAllOrderWithStatus("Approved"))

        {
            order.print_order_detail();
        }
        System.out.println("history:");

        for(Order order: orderMapper.findAllOrderWithStatus("History"))
        {
            order.print_order_detail();
        }
    }


    //Creates a periodic order by specifying the supplier, item list, store number, and the number of days in the order cycle. It calculates the cost of the order and inserts it into the database using the DataAccesObject.PeriodicOrderMapper.
    // Returns true if the periodic order creation is successful.
    public boolean period_order(Supplier supplier,Map<Item,Integer> list_items, int store_num ,int numberofdayscycle) {

        Map<Item, Pair<Integer, Float>> maplist;
        Order order = new Order(null, supplier, 0, store_num);
        for (Item item : list_items.keySet())
        {
            if (supplier.getItems().containsKey(item))
            {
                double discount = 1;

                int amount = list_items.get(item);
                float base_price = supplier.getItems().get(item).getSecond();
                int new_amount = 0;
                float cost = 0;
                //if the supplier give a discount on the item serch for the biggest amount
                if (supplier.getContract().itemsMapDiscount.containsKey(item))
                {
                    //cheak how much max amount have a discount from the curr amount
                    for (int i = 0; i < amount; i++)
                    {
                        if (supplier.getContract().itemsMapDiscount.get(item).containsKey(amount - i))
                        {
                            discount = supplier.getContract().itemsMapDiscount.get(item).get(amount - i);
                            new_amount = amount - i;
                            break;
                        }
                    }
                }
                //add the item to the total price o
                cost = (float) (new_amount * base_price * discount + ((amount - new_amount) * base_price));
                order.add_item(item, amount, cost);
            }
        }

        Period_Order periodOrders = new Period_Order(numberofdayscycle);


        periodOrders.setSupplier(order.getSupplier());
        periodOrders.setStore_number(order.getStore_number());
        periodOrders.setCost(order.getCost());
        periodOrders.setItemList(order.getItemList());
        periodOrders.setStatusOrder(order.getStatusOrder());


        this.periodicOrderMapper.insert(periodOrders);
        return true;
    }


    //Adds an item with the specified amount and cost to a periodic order identified by its ID. It retrieves the periodic order from the DataAccesObject.PeriodicOrderMapper, checks if it can be updated, and adds the item.
    // Returns true if the addition is successful, false if the order cannot be updated.
    public boolean update_add_to_period_order(String id,Item item,int amount ,float cost){
       Period_Order periodOrder= periodicOrderMapper.findByContractId(id);
       if(periodOrder.can_update()){
            periodOrder.add_item(item,amount,cost);
            periodicOrderMapper.update(periodOrder);
            return true;}
       else
           return false;

    }

    //Removes an item from a periodic order identified by its ID.
    // It retrieves the periodic order from the DataAccesObject.PeriodicOrderMapper, checks if it can be updated, and removes the item.
    // Returns true if the removal is successful, false if the order cannot be updated.
    public boolean update_remove_from_order(String id,Item item) {
        Period_Order periodOrder = periodicOrderMapper.findByContractId(id);
        if (periodOrder.can_update()) {
            periodOrder.remove_item(item);
            periodicOrderMapper.update(periodOrder);
            return true;
        }
        return false;
    }
    // Deletes a periodic order with the specified ID from the database using the DataAccesObject.PeriodicOrderMapper.
    public void delete_a_period_order(String id){
        periodicOrderMapper.delete(periodicOrderMapper.findByContractId(id));

    }

    //Deletes an order with the specified order number from the database using the DataAccesObject.OrderMapper.
    public  void delte_a_order(String id){
        orderMapper.delete(orderMapper.findByOrderNum(id));
    }

    //delete all db of orders
    public void  delte_db(){
        orderMapper.deleteAll();
        periodicOrderMapper.deleteAll();
    }

    //prient all the periods order's
    public void print_period_orders(){
        List<Period_Order>periodOrders=periodicOrderMapper.findAll();
        for(Period_Order periodOrder: periodOrders){
            periodOrder.print_order_detail();

        }
    }
    public List<Period_Order>get_Periodorders(){
        return periodicOrderMapper.findAll();
    }

    public boolean contain_Period_order(String string){
        if(periodicOrderMapper.findByContractId(string)!=null)
            return true;
        else{
            return false;
        }
    }
    public Period_Order get_period_order_by_id(String id){
        return periodicOrderMapper.findByContractId(id);
    }
    public boolean can_update_period_order(Period_Order periodOrder){
        return periodOrder.can_update();
    }

    public void checkPeriodOrders() {
        List<Period_Order> periodOrders = periodicOrderMapper.findAll();

        for (Period_Order periodOrder : periodOrders) {
            int remainingDays = periodOrder.getDay_left();
            if (remainingDays == 0) {
                // Period order has reached zero days, update its status
                Order order=new Order(periodOrder.getItemList(),periodOrder.getSupplier(),periodOrder.getCost(),periodOrder.getStore_number());
                orderMapper.insert(order);
                periodOrder.setDay_left(periodOrder.getDays_to_cycle());
                periodicOrderMapper.update(periodOrder);
                System.out.println("Periodic Order with ID " + periodOrder.getOrderNum() + " has reached zero days.");
            } else {
                // Decrease the remaining days by 1
                periodOrder.setDay_left(remainingDays - 1);
                periodicOrderMapper.update(periodOrder);
            }
        }
    }
    public Order get_order(String num){
        return orderMapper.findByOrderNum(num);

    }

}





