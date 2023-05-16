package Domain;

import DataAccesObject.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public abstract class DeliveringSupplier extends Supplier {

    public static NonDeliveringSupplierMapper deliveringSupplierMapper;


    public DeliveringSupplier(String name, String business_id, int payment_method, String suplier_ID, ContactPerson person, Contract contract, Map<Item,Pair<Integer, Float>> items) {
        super(name, business_id, PaymentCreator.getpayment(payment_method), suplier_ID, person, contract, items);
        Connection conn = null;
        try
        {
            String url = "jdbc:sqlite:res/SuperLeeDataBase.db";
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException ignored) {}
        finally
        {
            try {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ignored) {}
        }

        if(deliveringSupplierMapper == null)
        {
//            deliveringSupplierMapper = new Interface.NonDeliveringSupplierMapper(conn);
            deliveringSupplierMapper = new NonDeliveringSupplierMapper();

        }
    }



}
