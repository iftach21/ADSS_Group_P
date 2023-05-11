package DAOTest;

import Data.Connection;
import Data.WeeklyShiftDAO;
import Domain.Employee.Shift;
import Domain.Employee.WeeklyShift;
import Domain.Employee.Workers;
import Domain.Enums.WindowType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;


public class WeeklyShiftDAOTest {
    Shift shift1;
    Shift shift2;
    WeeklyShift weeklyShift;


    WeeklyShiftDAO DAO = WeeklyShiftDAO.getInstance();


    public WeeklyShiftDAOTest() throws SQLException {
    }

    @BeforeEach
    void create()
    {
        Connection.DeleteRows();
        weeklyShift= new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"ofir","lotsofmoney","23.2.23",90,12345,"student",1234);
        weeklyShift.addworkertoshift(w, WindowType.day1,0);
        shift1 = new Shift(w);

        shift2 = new Shift(w);


        Shift[] s = {new Shift(w),new Shift(w),new Shift(w),new Shift(w),new Shift(w),new Shift(w),new Shift(w)};
        Shift[] s2 =  {new Shift(w),new Shift(w),new Shift(w),new Shift(w),new Shift(w),new Shift(w),new Shift(w)};

        weeklyShift.setDayShift(s);
        weeklyShift.setNightShift(s2);
    }

    @AfterEach
    void tearDown() {
       // Connection.DeleteRows();
    }

    @Test
    void TestAddAndGetNewWeeklyShift() throws SQLException {

        DAO.add(weeklyShift);
        Assertions.assertNotNull(DAO.get(weeklyShift.getWeekNUm(),weeklyShift.getYear(),weeklyShift.getSupernum()));
    }

    @Test
    void TestDeletingWeeklyShift() throws SQLException {
        DAO.add(weeklyShift);
        DAO.delete(weeklyShift);
        Assertions.assertNull(DAO.get(weeklyShift.getWeekNUm(),weeklyShift.getYear(),weeklyShift.getSupernum()));
    }


    @Test
    void TestUpdate() throws SQLException {
        DAO.add(weeklyShift);
        Shift[] shifts = weeklyShift.getNightShift();
        shifts[1] = shift1;
        ArrayList<Workers>[] w =  new ArrayList[1];
        w[0]= new ArrayList<>();
        w[0].add(new Workers(1,"ofir","lotsofmoney","23.2.23",90,12345,"student",1234));
        shift1.setWorkerInShift(w);
        DAO.update(weeklyShift);
        Assertions.assertEquals(DAO.get(1,1,1),weeklyShift);
    }


}
