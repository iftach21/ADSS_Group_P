package Test;

import Data.WorkersDAO;
import Domain.Employee.Driver;
import Domain.Employee.Workers;
import Domain.Enums.TempLevel;
import Domain.Enums.weightType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkersDAOTest {
    Workers w1;
    Driver w2;

    WorkersDAO DAO = WorkersDAO.getInstance();

    public WorkersDAOTest() throws SQLException {}

    @BeforeEach
    void createMockTransfer()
    {
        this.w1 =new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);
        w1.addprof(0);

        this.w2 = new Driver(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234,TempLevel.cold,weightType.heavyWeight);
    }

    @Test
    void TestAddAndGetNewEmployee(){

        DAO.add(w1);
        Assertions.assertEquals(w1.getId(), DAO.get(w1.getId()).getId());
        Assertions.assertTrue(DAO.get(w1.getId()).caniworkatprofindx(0));

        DAO.add(w2);
        Assertions.assertEquals(w2.getId(), DAO.get(w2.getId()).getId());
        Assertions.assertTrue(DAO.get(w1.getId()).amIDriver());

    }

    @Test
    void TestDeletingEmployee(){
        DAO.add(w1);
        DAO.delete(w1.getId());
        Assertions.assertNull(DAO.get(w1.getId()));
    }

    @Test
    void TestGetAll(){
        DAO.add(w1);
        DAO.add(w2);


        List <Workers> fromdata = DAO.getAllworkerslist();

        Assertions.assertEquals(fromdata.get(0).getId(),w1.getId());
        Assertions.assertEquals(fromdata.get(1).getId(),w2.getId());

    }

    @Test
    void TestUpdate(){
        DAO.add(w1);
        w1.setName("test");
        DAO.update(w1);
        Assertions.assertEquals(0, DAO.get(w1.getId()).getName().compareTo("test"));
    }
}
