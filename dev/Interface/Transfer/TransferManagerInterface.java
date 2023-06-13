package Interface.Transfer;

import DataAccesObjects.Transfer.Item_mockDAO;
import DataAccesObjects.Transfer.SiteDAO;
import DataAccesObjects.Transfer.TrucksDAO;
import Domain.Enums.TempLevel;
import Domain.Transfer.Item_mock;
import Domain.Transfer.Site;
import Domain.Transfer.TransferController;
import Domain.Transfer.Truck;
import Interface.AInterface;

import java.sql.SQLException;

public class TransferManagerInterface extends AInterface {

    private final TransferController controller = TransferController.getInstance();

    public TransferManagerInterface() throws SQLException {
    }

    @Override
    public void logIn() throws Exception {

        loadData();

        controller.startTransferSystem();

        SiteDAO.getInstance().deleteAll();
        Item_mockDAO.getInstance().deleteAll();
    }

    public void loadData() throws SQLException {
        SiteDAO.getInstance().deleteAll();
        Item_mockDAO.getInstance().deleteAll();

        //create and add some data to the DB before starting the system
        //creating sites
        Site Yarkan = new Site(1, "HaYarkaniya Shel Moshe", "Ben-Gurion 34 Beer Sheva", "052-3878098", "Moshe", 31.261050989825964, 34.802749075835074);
        Site Tnuva = new Site(2, "Tnuva", "Bugrashov 12 Tel Aviv", "03-9871324", "Daniel", 32.07633484890762, 34.77137179121594);
        Site Osem = new Site(3, "Osem", "HaZaiyt 90 Haifa", "02-9872534", "Carmel", 32.82764889925686, 34.97985639118071);
        Site SuperLiAshkelon = new Site(4, "SuperLi Ashkelon", "Agamim 12 Ashkelon", "08-4765182", "Toy", 31.65042860601009, 34.56465999071492);
        Site SuperLiMetula = new Site(5, "SuperLi Metula", "HaKneset 7 Metula", "04-1276540", "Mati", 33.27985900057013, 35.580390944371906);

        Site Golda = new Site(6, "Golda", "Shimoni 34 Beer Sheva", "052-5367733", "Noam", 31.253822164607076, 34.79551254719668);
        Site Tivol = new Site(7, "Tivol", "Alenbi 12 Tel Aviv", "03-1234563", "Aviv", 32.06837220803396, 34.77111510207023);
        Site Elit = new Site(8, "Elit", "Givati 90 Bat Yam", "02-3124512", "Yarden", 32.014512714344306, 34.74664906820799);
        Site SuperLiBeerSheva = new Site(9, "SuperLi Beer Sheva", "Bialik 12 Beer Sheva", "08-7645144", "Simcha", 31.24631471973522, 34.786584293148174);
        Site SuperLiTelAviv = new Site(10, "SuperLi Tel Aviv", "Bugrashov 10 Tel Aviv", "03-4216900", "Gadi", 32.07957916769778, 34.76742641566509);

        Site Shtraus = new Site(11, "Shtraus", "Herzel 1 Ramat Gan", "054-5533111", "Shlomo", 32.06800364437003, 34.823663464607066);
        Site Telma = new Site(12, "Telma", "Sokolov 30 Netanya", "03-4441322", "Liav", 32.318085755992975, 34.84636803073442);
        Site Tapuzina = new Site(13, "Tapuzina", "Ezra 3 Nehariya", "02-1232444", "Aviad", 33.01117227097503, 35.08874927613198);
        Site SuperLiPetahTikva = new Site(14, "SuperLi PetahTikva", "Em Hamoshavot 5 Petah Tikva", "03-4765182", "Kobi", 32.1032944252276, 34.87192055466674);
        Site SuperLiHolon = new Site(15, "SuperLi Holon", "Rabin 11 Holon", "04-9996540", "Eran", 32.008939557863286, 34.778454609019235);

        Site PriGat = new Site(16, "PriGat", "Rager 20 Sderot", "050-5556565", "Ella", 31.524537133868932, 34.5893951390522);
        Site Nestle = new Site(17, "Nestle", "Haolim 2 Rishon Lezion", "03-4321322", "Niv", 31.953441327282682, 34.7708701968806);
        Site Maadanot = new Site(18, "Maadanot", "HaZaiyt 4 Nes Ziona", "03-1116631", "Neve", 31.928151252585113, 34.798482805429295);
        Site SuperLiEilat = new Site(19, "SuperLi Eilat", "Kadesh 12 Eilat", "08-4055111", "Nissim", 29.550524667375743, 34.94041585540007);
        Site SuperLiHodHasharon = new Site(20, "SuperLi HodHasharon", "Hazomet 1 Hod Hasharon", "04-1276540", "GiGi", 32.16105885239832, 34.89361614062971);

        //creating mock Items
        Item_mock Bamba = new Item_mock("45325", TempLevel.regular, "Bamba");
        Item_mock Yolo = new Item_mock("hr4565", TempLevel.cold, "Yolo");
        Item_mock Potato = new Item_mock("5524f2", TempLevel.regular, "Potato");
        Item_mock Tomato = new Item_mock("543523f", TempLevel.regular, "Tomato");
        Item_mock FrozenPizza = new Item_mock("5838sk", TempLevel.frozen, "Frozen Pizza");
        Item_mock Doritos = new Item_mock("jl829kl", TempLevel.regular, "Doritos");
        Item_mock ChocolateIceCream = new Item_mock("84390j", TempLevel.frozen, "Chocolate Ice Cream");
        Item_mock CreamCheese = new Item_mock("23719g", TempLevel.frozen, "Cream Cheese");

        Item_mock Petiber = new Item_mock("125sa", TempLevel.regular, "Petiber");
        Item_mock Coffee = new Item_mock("mn5050", TempLevel.regular, "Coffee");
        Item_mock Milk = new Item_mock("1154n2", TempLevel.regular, "Milk");
        Item_mock Strawberry = new Item_mock("98988f", TempLevel.regular, "Strawberry");
        Item_mock VanillaIceCream = new Item_mock("3232mm", TempLevel.regular, "Vanilla iceCream");
        Item_mock Bagel = new Item_mock("jb668kd", TempLevel.regular, "Bagel");
        Item_mock Bread = new Item_mock("d4040g", TempLevel.regular, "Bread");
        Item_mock Onion = new Item_mock("3030g", TempLevel.regular, "Onion");

        Item_mock Garlic = new Item_mock("1111fu", TempLevel.regular, "Garlic");
        Item_mock Salt = new Item_mock("hh2222", TempLevel.cold, "Salt");
        Item_mock Nuts = new Item_mock("32ede4", TempLevel.regular, "Nuts");
        Item_mock Sugar = new Item_mock("cwec44", TempLevel.regular, "Sugar");
        Item_mock SoySauce = new Item_mock("32e453", TempLevel.cold, "Soy Sauce");
        Item_mock ChilliSauce = new Item_mock("sfv334", TempLevel.cold, "Chilli Sauce");
        Item_mock blackPepper = new Item_mock("ref452", TempLevel.cold, "black Pepper");
        Item_mock CottageCheese = new Item_mock("2324gr", TempLevel.cold, "Cottage Cheese");

        Item_mock Mayonnaise = new Item_mock("25fe3e", TempLevel.cold, "Mayonnaise");
        Item_mock GarlicSauce = new Item_mock("3ef63", TempLevel.cold, "Garlic Sauce");
        Item_mock Parmesan = new Item_mock("43f465", TempLevel.cold, "Parmesan");
        Item_mock Cucumber = new Item_mock("4r367j8", TempLevel.regular, "Cucumber");
        Item_mock CherryTomatoes = new Item_mock("54tg78", TempLevel.cold, "Cherry Tomatoes");
        Item_mock Apple = new Item_mock("fb1t87", TempLevel.regular, "Apple");
        Item_mock ChocolateNutella = new Item_mock("6cw352", TempLevel.cold, "Chocolate Nutella");
        Item_mock ChocolateHashahar = new Item_mock("vt653c2", TempLevel.cold, "Chocolate Hashahar");


        //Create trucks
        //create 2 trucks
        Truck truck1 = new Truck(98743212, "Mercedes 330g", 8, 8,15,  TempLevel.frozen);
        Truck truck2 = new Truck(80619991, "Nisan x", 4, 4,5,  TempLevel.regular);
        Truck truck3 = new Truck(38475623, "Tesla S", 50, 50,55,  TempLevel.frozen);
        Truck truck4 = new Truck(25419982, "Ford 8", 12, 12,14,  TempLevel.cold);

        //add all created stuff to DB
        SiteDAO.getInstance().add(Yarkan);
        SiteDAO.getInstance().add(Tnuva);
        SiteDAO.getInstance().add(Osem);
        SiteDAO.getInstance().add(SuperLiAshkelon);
        SiteDAO.getInstance().add(SuperLiMetula);

        SiteDAO.getInstance().add(Golda);
        SiteDAO.getInstance().add(Tivol);
        SiteDAO.getInstance().add(Elit);
        SiteDAO.getInstance().add(SuperLiBeerSheva);
        SiteDAO.getInstance().add(SuperLiTelAviv);

        SiteDAO.getInstance().add(Shtraus);
        SiteDAO.getInstance().add(Telma);
        SiteDAO.getInstance().add(Tapuzina);
        SiteDAO.getInstance().add(SuperLiPetahTikva);
        SiteDAO.getInstance().add(SuperLiHolon);

        SiteDAO.getInstance().add(PriGat);
        SiteDAO.getInstance().add(Nestle);
        SiteDAO.getInstance().add(Maadanot);
        SiteDAO.getInstance().add(SuperLiEilat);
        SiteDAO.getInstance().add(SuperLiHodHasharon);

        Item_mockDAO.getInstance().add(Bamba);
        Item_mockDAO.getInstance().add(Yolo);
        Item_mockDAO.getInstance().add(Potato);
        Item_mockDAO.getInstance().add(Tomato);
        Item_mockDAO.getInstance().add(FrozenPizza);
        Item_mockDAO.getInstance().add(Doritos);
        Item_mockDAO.getInstance().add(ChocolateIceCream);
        Item_mockDAO.getInstance().add(CreamCheese);

        Item_mockDAO.getInstance().add(Petiber);
        Item_mockDAO.getInstance().add(Coffee);
        Item_mockDAO.getInstance().add(Milk);
        Item_mockDAO.getInstance().add(Strawberry);
        Item_mockDAO.getInstance().add(VanillaIceCream);
        Item_mockDAO.getInstance().add(Bagel);
        Item_mockDAO.getInstance().add(Bread);
        Item_mockDAO.getInstance().add(Onion);

        Item_mockDAO.getInstance().add(Garlic);
        Item_mockDAO.getInstance().add(Salt);
        Item_mockDAO.getInstance().add(Nuts);
        Item_mockDAO.getInstance().add(Sugar);
        Item_mockDAO.getInstance().add(SoySauce);
        Item_mockDAO.getInstance().add(ChilliSauce);
        Item_mockDAO.getInstance().add(blackPepper);
        Item_mockDAO.getInstance().add(CottageCheese);

        Item_mockDAO.getInstance().add(Mayonnaise);
        Item_mockDAO.getInstance().add(GarlicSauce);
        Item_mockDAO.getInstance().add(Parmesan);
        Item_mockDAO.getInstance().add(Cucumber);
        Item_mockDAO.getInstance().add(CherryTomatoes);
        Item_mockDAO.getInstance().add(Apple);
        Item_mockDAO.getInstance().add(ChocolateNutella);
        Item_mockDAO.getInstance().add(ChocolateHashahar);

        TrucksDAO.getInstance().add(truck1);
        TrucksDAO.getInstance().add(truck2);
        TrucksDAO.getInstance().add(truck3);
        TrucksDAO.getInstance().add(truck4);
    }
}
