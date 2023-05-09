
/*This class is representing the period order and is going to be used in the other class to insert when the cycle
 is ending a new order*/
public class Period_Order {
    private Order order;
    //day's to each cycle until the new order
    private int days_to_cycle;

    //how much time left until the order is issue again
    private int day_left;

    public Period_Order(Order order, int days_to_cycle) {
        this.order = order;
        this.days_to_cycle = days_to_cycle;
        this.day_left=days_to_cycle;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getDays_to_cycle() {
        return days_to_cycle;
    }

    public void setDays_to_cycle(int days_to_cycle) {
        this.days_to_cycle = days_to_cycle;
    }


    //lower the days to the period orders when the day pass
    public int lower_days(){
        if(this.day_left!=0){
        this.day_left=this.day_left-1;
        return 0;}
        else {
            this.day_left=this.days_to_cycle;
            return 1;}


    }
    //to check if the period order can be updated
    public boolean can_update(){
        if(this.day_left>1){
            return true;
        }
        else {
            return false;
        }
    }

}
