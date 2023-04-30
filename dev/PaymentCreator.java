public class PaymentCreator {
    public static PaymentMethod getpayment(int payment){
        if(payment==1){return PaymentMethod.SHOTEF;}
        if(payment==2){return PaymentMethod.SHOTEFPLUS30;}
        if(payment==3){return PaymentMethod.SHOTEFPLUS30;}

        return null;
    }
}
