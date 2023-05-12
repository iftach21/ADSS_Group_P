
package DAOTest;

import DataAccesObjects.Transfer.TrucksDAO;
import Domain.Enums.TempLevel;
import Domain.Transfer.Truck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

public class TrucksDAOTest {
    Truck truck1;
    Truck truck2;

    TrucksDAO trucksDAO;

    public TrucksDAOTest() throws SQLException {
        trucksDAO = TrucksDAO.getInstance();
    }

    @BeforeEach
    void createMockTrucks() throws SQLException {
        //clear database before each Test
        trucksDAO.deleteAll();

        //create 2 trucks
        this.truck1 = new Truck(9874321, "Mercedes 330g", 8, 8,15,  TempLevel.frozen);
        this.truck2 = new Truck(8061999, "Nisan x", 4, 4,5,  TempLevel.regular);
    }

    @Test
    void addAndGetTruckTest(){

        trucksDAO.add(truck1);
        Assertions.assertEquals(truck1.getLicenseNumber(), trucksDAO.get(truck1.getLicenseNumber()).getLicenseNumber());
        Assertions.assertEquals(truck1.getTruckModel(), trucksDAO.get(truck1.getLicenseNumber()).getTruckModel());
        Assertions.assertEquals(truck1.getTempCapacity(), trucksDAO.get(truck1.getLicenseNumber()).getTempCapacity());
        Assertions.assertEquals(truck1.getMaxWeight(), trucksDAO.get(truck1.getLicenseNumber()).getMaxWeight());
        Assertions.assertEquals(truck1.getCurrentTruckWeight(), trucksDAO.get(truck1.getLicenseNumber()).getCurrentTruckWeight());
        Assertions.assertEquals(truck1.getTruckNetWeight(), trucksDAO.get(truck1.getLicenseNumber()).getTruckNetWeight());


        trucksDAO.add(truck2);
        Assertions.assertEquals(truck2.getLicenseNumber(), trucksDAO.get(truck2.getLicenseNumber()).getLicenseNumber());
        Assertions.assertEquals(truck2.getTruckModel(), trucksDAO.get(truck2.getLicenseNumber()).getTruckModel());
        Assertions.assertEquals(truck2.getTempCapacity(), trucksDAO.get(truck2.getLicenseNumber()).getTempCapacity());
        Assertions.assertEquals(truck2.getMaxWeight(), trucksDAO.get(truck2.getLicenseNumber()).getMaxWeight());
        Assertions.assertEquals(truck2.getCurrentTruckWeight(), trucksDAO.get(truck2.getLicenseNumber()).getCurrentTruckWeight());
        Assertions.assertEquals(truck2.getTruckNetWeight(), trucksDAO.get(truck2.getLicenseNumber()).getTruckNetWeight());

    }

    @Test
    void deleteTruckTest(){
        trucksDAO.add(truck1);
        trucksDAO.delete(truck1.getLicenseNumber());
        Assertions.assertNull(trucksDAO.get(truck1.getLicenseNumber()));

        trucksDAO.add(truck2);
        trucksDAO.delete(truck2.getLicenseNumber());
        Assertions.assertNull(trucksDAO.get(truck2.getLicenseNumber()));
    }

    @Test
    void updateTruckTest(){

        trucksDAO.add(truck1);
        truck1.updateWeight(10);
        trucksDAO.update(truck1);
        Assertions.assertEquals(10, trucksDAO.get(truck1.getLicenseNumber()).getCurrentTruckWeight());

        trucksDAO.add(truck2);
        truck2.updateWeight(5);
        trucksDAO.update(truck2);
        Assertions.assertEquals(5, trucksDAO.get(truck2.getLicenseNumber()).getCurrentTruckWeight());

    }

    @Test
    void getAllTrucksTest(){
        trucksDAO.add(truck1);
        trucksDAO.add(truck2);

        Map<Integer, Truck> allTrucks = trucksDAO.getAllTrucks();

        Assertions.assertEquals(truck1.getLicenseNumber(), allTrucks.get(truck1.getLicenseNumber()).getLicenseNumber());
        Assertions.assertEquals(truck2.getLicenseNumber(), allTrucks.get(truck2.getLicenseNumber()).getLicenseNumber());

    }
}
