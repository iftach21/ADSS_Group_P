package Interface;

import Domain.*;

import java.util.Scanner;

abstract class AInterface
{
    private OrderManger orderManger;
    private Supplier_Manger supplier_manger;
    public abstract void interfaceStartup();
    public Supplier_Manger getSupplier_manger()
    {
        return supplier_manger;
    }

    public void interfaceManagerLogin() {
    }

    public void interfaceWorkerLogin() {
    }
}
