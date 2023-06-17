package Domain;

/*This class is representing the period order and is going to be used in the other class to insert when the cycle
 is ending a new order*/
public class Period_Order extends Order {

   static int ordernumber ;

//    private Order order;
    //day's to each cycle until the new order
    private int days_to_cycle;

    //how much time left until the order is issue again
    private int day_left;

//    private int periodicOrderId;

    public Period_Order() {
    }

//    public Period_Order(Order order, int days_to_cycle)
    public Period_Order(int days_to_cycle)

    {
//        this.order = order;
        this.days_to_cycle = days_to_cycle;
        this.day_left=days_to_cycle;
        this.setOrderNum(ordernumber);
        ordernumber ++;
    }

//    public static int getOrdernumber() {
//        return ordernumber;
//    }
//
//    public static void setOrdernumber(int ordernumber) {
//        Period_Order.ordernumber = ordernumber;
//    }

    public int getDay_left() {
        return day_left;
    }

    public void setDay_left(int day_left) {
        this.day_left = day_left;
    }

//    public int getPeriodicOrderId() {
//        return periodicOrderId;
//    }
//
//    public void setPeriodicOrderId(int periodicOrderId) {
//        this.periodicOrderId = periodicOrderId;
//    }

//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }

    public int getDays_to_cycle() {
        return days_to_cycle;
    }

    public void setDays_to_cycle(int days_to_cycle) {
        this.days_to_cycle = days_to_cycle;
    }


    //lower the days to the period orders when the day pass

    //to check if the period order can be updated
    public boolean can_update(){
        if(this.day_left>1){
            return true;
        }
        else {
            return false;
        }
    }
    public void setOrdernumber(int sum){
        ordernumber=sum+1;
    }


    public String orderString() {
        String details = "Order number: " + this.getOrderNum()+
                "\nStore number: " + this.getStore_number() +
                "\nCost of the order: " + this.getCost() +
                "\nDate: " + this.currentDate +
                "\nSupplier name: " + this.getSupplier().getName() +
                "\n cycle number of days :" +this.days_to_cycle +
                "\n day left until new order emarge:" + this.day_left +
                "\nItems:\n";

        for (Item item : this.getItemList().keySet()) {
            details += "-------------------------------------------------------------\n";
            details += item.toString() + "\n";
            details += "Base price per unit (no discounts): " + this.getSupplier().getItems().get(item).getSecond() + "\n";
            details += "Amount: " + this.getItemList().get(item).getFirst() + "\n";
            details += "Price: " + this.getItemList().get(item).getSecond() + "\n";
            details += "-------------------------------------------------------------\n";
        }

        return details;
    }




}
