package DAOTest;

import DataAccesObjects.Connection;
import DataAccesObjects.Employee.WeeklyShiftDAO;
import DataAccesObjects.Employee.WorkersDAO;
import Domain.Employee.Shift;
import Domain.Employee.WeeklyShift;
import Domain.Employee.Workers;
import Domain.Enums.WindowType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


public class WeeklyShiftDAOTest {
    Shift shift1;
    Shift shift2;
    WeeklyShift weeklyShift;
    Workers w1;


    WeeklyShiftDAO DAO = WeeklyShiftDAO.getInstance();
    WorkersDAO DAOWorkers = WorkersDAO.getInstance();


    public WeeklyShiftDAOTest() throws SQLException {
    }

    @BeforeEach
    void create()
    {
        w1 = new Workers(2,"ofir","lotsofmoney","23.2.23",90,12345,"student",1234);
        //Connection.DeleteRows();
        weeklyShift= new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"ofir","lotsofmoney","23.2.23",90,12345,"student",1234);
        w.addprof(0);
        DAOWorkers.add(w);
        weeklyShift.addworkertoshift(w, WindowType.day1,0);

        shift1 = new Shift(w);

        shift2 = new Shift(w);

        Shift[] s = new Shift[7];
        Shift[] s2 =  new Shift[7];

        //for every shift i have worker:
        for(int i=0;i<7;i++){

            s[i] = new Shift();
            s2[i] = new Shift();

            s[i].insertToShift(w,0);
            s2[i].insertToShift(w,0);
        }


        weeklyShift.setDayShift(s);
        weeklyShift.setNightShift(s2);
    }

    @AfterEach
    void tearDown() {

        Connection.DeleteRows();
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
        DAOWorkers.add(w1);
        weeklyShift.addworkertoshift(w1,WindowType.day1,0);
        DAO.update(weeklyShift);
        Assertions.assertTrue(DAO.get(1,1,1).checkifworkallready(w1.getId(),WindowType.day1));
    }


}
