package Interface;

import Data.Item_mockDAO;
import Data.SiteDAO;
import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.TempLevel;
import Domain.Transfer.Item_mock;
import Domain.Transfer.Site;
import Domain.Transfer.TransferController;

import java.sql.SQLException;

public class TransferManagerInterface extends AInterface{

    private final TransferController controller = TransferController.getInstance();

    public TransferManagerInterface() throws SQLException {
    }

    @Override
    public void logIn() throws Exception {
        //delete if any
        Item_mockDAO.getInstance().deleteAll();
        SiteDAO.getInstance().deleteAll();

        //create and add some data to the DB before starting the system
        //creating sites
        Site Yarkan = new Site(1, "HaYarkaniya Shel Moshe", "Ben-Gurion 34 Beer Sheva", "052-3878098", "Moshe", 31.261050989825964, 34.802749075835074);
        Site Tnuva = new Site(2, "Tnuva", "Bugrashov 12 Tel Aviv", "03-9871324", "Daniel", 32.07633484890762, 34.77137179121594);
        Site Osem = new Site(3, "Osem", "HaZaiyt 90 Haifa", "02-9872534", "Carmel", 32.82764889925686, 34.97985639118071);
        Site SuperLiAshkelon = new Site(4, "SuperLi Ashkelon", "Agamim 12 Ashkelon", "03-4765182", "Toy", 31.65042860601009, 34.56465999071492);
        Site SuperLiMetula = new Site(5, "SuperLi Metula", "HaKneset 7 Metula", "04-1276540", "Mati", 33.27985900057013, 35.580390944371906);

        //creating mock Items
        Item_mock Bamba = new Item_mock("45325", TempLevel.regular, "Bamba");
        Item_mock Yolo = new Item_mock("hr4565", TempLevel.cold, "Yolo");
        Item_mock Potato = new Item_mock("5524f2", TempLevel.regular, "Potato");
        Item_mock Tomato = new Item_mock("543523f", TempLevel.regular, "Tomato");
        Item_mock FrozenPizza = new Item_mock("5838sk", TempLevel.frozen, "Frozen Pizza");
        Item_mock Doritos = new Item_mock("jl829kl", TempLevel.regular, "Doritos");
        Item_mock ChocolateIceCream = new Item_mock("84390j", TempLevel.frozen, "Chocolate Ice Cream");
        Item_mock CreamCheese = new Item_mock("23719g", TempLevel.frozen, "Cream Cheese");

        //add all created stuff to DB
        SiteDAO.getInstance().add(Yarkan);
        SiteDAO.getInstance().add(Tnuva);
        SiteDAO.getInstance().add(Osem);
        SiteDAO.getInstance().add(SuperLiAshkelon);
        SiteDAO.getInstance().add(SuperLiMetula);

        Item_mockDAO.getInstance().add(Bamba);
        Item_mockDAO.getInstance().add(Yolo);
        Item_mockDAO.getInstance().add(Potato);
        Item_mockDAO.getInstance().add(Tomato);
        Item_mockDAO.getInstance().add(FrozenPizza);
        Item_mockDAO.getInstance().add(Doritos);
        Item_mockDAO.getInstance().add(ChocolateIceCream);
        Item_mockDAO.getInstance().add(CreamCheese);

        controller.startTransferSystem();

        SiteDAO.getInstance().deleteAll();
        Item_mockDAO.getInstance().deleteAll();
    }
}
