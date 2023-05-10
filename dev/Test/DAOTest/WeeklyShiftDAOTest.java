import Data.WeeklyShiftDAO;
import Domain.Employee.Shift;
import Domain.Employee.WeeklyShift;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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

        this.shift1 = new Shift();
        this.shift2 = new Shift();
        weeklyShift = new WeeklyShift(1,1,1);
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
        Shift[] shifts = new Shift[7];
        shifts[0] = shift1;
        weeklyShift.setNightShift(shifts);
        DAO.update(weeklyShift);
        Assertions.assertNotNull(DAO.get(1,1,1).getNightShift()[0]);
    }
}
