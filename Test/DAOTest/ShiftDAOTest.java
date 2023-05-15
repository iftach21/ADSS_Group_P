package DAOTest;

import DataAccesObjects.Connection;
import DataAccesObjects.Employee.ShiftDAO;
import DataAccesObjects.Employee.WorkersDAO;
import Domain.Employee.Driver;
import Domain.Employee.Shift;
import Domain.Employee.Workers;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ShiftDAOTest {
    Workers w1;
    Driver w2;

    Shift shift;

    ShiftDAO DAO = ShiftDAO.getInstance();
    WorkersDAO DAOWorkers = WorkersDAO.getInstance();

    public ShiftDAOTest() throws SQLException {}

    @BeforeEach
    void create()
    {
        this.w1 =new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);
        w1.addprof(0);
        DAOWorkers.add(w1);



        this.w2 = new Driver(2,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234,TempLevel.cold,weightType.heavyWeight);

        DAOWorkers.add(w2);

        this.shift = new Shift();
        shift.addDriver(w2);
        shift.insertToShift(w1,0);

    }
    @AfterEach
    void tearDown() {
        Connection.DeleteRows();
    }

    @Test
    void TestAddAndGetNewShift() throws SQLException {

        DAO.add(shift);
        Assertions.assertEquals(shift.getId(), DAO.get(shift.getId()).getId());
        Assertions.assertTrue(DAO.get(shift.getId()).checkIfWorkerInShift(w1.getId()));


        //deleting from db:
        DAO.delete(shift.getId());
        DAOWorkers.delete(1);
        DAOWorkers.delete(2);

    }

    @Test
    void TestDeletingShift() throws SQLException {
        DAO.add(shift);
        DAO.delete(shift.getId());
        Assertions.assertNull(DAO.get(shift.getId()));
    }


    @Test
    void TestUpdate() throws SQLException {
        DAO.add(shift);
        shift.removalWorker(w1);
        DAO.update(shift);
        Assertions.assertFalse(DAO.get(shift.getId()).checkIfWorkerInShift(w1.getId()));

        //deleting from database
        DAO.delete(shift.getId());
        DAOWorkers.delete(1);
        DAOWorkers.delete(2);
    }
}
