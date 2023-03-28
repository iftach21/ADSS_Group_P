public class Contract {
    String Order_num;
    Boolean Fulfilled;
    Boolean Fully_Fullfleld;

    public String getOrder_num() {
        return Order_num;
    }

    public void setOrder_num(String order_num) {
        Order_num = order_num;
    }

    public Boolean getFulfilled() {
        return Fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        Fulfilled = fulfilled;
    }

    public Boolean getFully_Fullfleld() {
        return Fully_Fullfleld;
    }

    public void setFully_Fullfleld(Boolean fully_Fullfleld) {
        Fully_Fullfleld = fully_Fullfleld;
    }

    public Contract(String order_num, Boolean fulfilled, Boolean fully_Fullfleld) {
        Order_num = order_num;
        Fulfilled = fulfilled;
        Fully_Fullfleld = fully_Fullfleld;
    }
}
