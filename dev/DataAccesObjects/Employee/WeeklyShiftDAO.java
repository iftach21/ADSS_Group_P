package DataAccesObjects.Employee;

import Domain.Employee.Shift;
import Domain.Employee.WeeklyShift;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class WeeklyShiftDAO {

    private java.sql.Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
    private ShiftDAO shiftDAO = ShiftDAO.getInstance();
    private static WeeklyShiftDAO instance = null;
    private List<WeeklyShift> cache; //holds all the weeklyshifts

    private WeeklyShiftDAO() throws SQLException {
        cache = new ArrayList<>();
    }
    public static WeeklyShiftDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new WeeklyShiftDAO();
        }
        return instance;
    }

    public WeeklyShift get(int weekNum, int year, int superNum) throws SQLException {
        String sql = "SELECT * FROM weekly_shift WHERE week_num = ? AND year = ? AND supernum = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, weekNum);
        statement.setInt(2, year);
        statement.setInt(3, superNum);
        ResultSet resultSet = statement.executeQuery();
        WeeklyShift weeklyShift = null;


        if (resultSet.next()) {
            weeklyShift = getWeeklyShiftFromDatabase(resultSet);
        }

        resultSet.close();
        statement.close();
        return weeklyShift;
    }

    /**
     * this will update the weeklyshift in the database.
     */

    public void update(WeeklyShift weeklyShift) {
        try {
            // Update the day and night shifts in the WeeklyShift object
            for (int i = 0; i < 7; i++) {
                if(weeklyShift.getDayShift()[i] != null){
                shiftDAO.update(weeklyShift.getDayShift()[i]);}
                if(weeklyShift.getNightShift()[i] != null){
                shiftDAO.update(weeklyShift.getNightShift()[i]);}
            }

            // Update the corresponding record in the database
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE weekly_shift SET day_shift_1 = ?, day_shift_2 = ?, day_shift_3 = ?," +
                            " day_shift_4 = ?, day_shift_5 = ?, day_shift_6 = ?, day_shift_7 = ?," +
                            " night_shift_1 = ?, night_shift_2 = ?, night_shift_3 = ?," +
                            " night_shift_4 = ?, night_shift_5 = ?, night_shift_6 = ?, night_shift_7 = ?" +
                            " WHERE week_num = ? AND year = ? AND supernum = ?"
            );
            statement.setInt(1, weeklyShift.getDayShift()[0].getId());
            statement.setInt(2, weeklyShift.getDayShift()[1].getId());
            statement.setInt(3, weeklyShift.getDayShift()[2].getId());
            statement.setInt(4, weeklyShift.getDayShift()[3].getId());
            statement.setInt(5, weeklyShift.getDayShift()[4].getId());
            statement.setInt(6, weeklyShift.getDayShift()[5].getId());
            statement.setInt(7, weeklyShift.getDayShift()[6].getId());
            statement.setInt(8, weeklyShift.getNightShift()[0].getId());
            statement.setInt(9, weeklyShift.getNightShift()[1].getId());
            statement.setInt(10, weeklyShift.getNightShift()[2].getId());
            statement.setInt(11, weeklyShift.getNightShift()[3].getId());
            statement.setInt(12, weeklyShift.getNightShift()[4].getId());
            statement.setInt(13, weeklyShift.getNightShift()[5].getId());
            statement.setInt(14, weeklyShift.getNightShift()[6].getId());
            statement.setInt(15, weeklyShift.getWeekNUm());
            statement.setInt(16, weeklyShift.getYear());
            statement.setInt(17, weeklyShift.getSupernum());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WeeklyShift> getWeeklyShiftList(int week,int year) {
        List<WeeklyShift> weeklyShifts = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM weekly_shift WHERE week_num = ? AND year = ?");
            preparedStatement.setInt(1, week);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                WeeklyShift ws = getWeeklyShiftFromDatabase(resultSet);
                weeklyShifts.add(ws);
                cache.add(ws);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weeklyShifts;
    }


    public void add(WeeklyShift weeklyShift) throws SQLException {
        if(getFromCache(weeklyShift) != null){
            return ;}
        cache.add(weeklyShift);

        String sql = "INSERT INTO weekly_shift (week_num, year, supernum, day_shift_1," +
                " day_shift_2, day_shift_3, day_shift_4, day_shift_5, day_shift_6," +
                " day_shift_7, night_shift_1, night_shift_2, night_shift_3, night_shift_4," +
                " night_shift_5, night_shift_6, night_shift_7) VALUES (?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, weeklyShift.getWeekNUm());
        statement.setInt(2, weeklyShift.getYear());
        statement.setInt(3, weeklyShift.getSupernum());
        statement.setInt(4, shiftDAO.add(weeklyShift.getDayShift()[0]));
        statement.setInt(5, shiftDAO.add(weeklyShift.getDayShift()[1]));
        statement.setInt(6, shiftDAO.add(weeklyShift.getDayShift()[2]));
        statement.setInt(7, shiftDAO.add(weeklyShift.getDayShift()[3]));
        statement.setInt(8, shiftDAO.add(weeklyShift.getDayShift()[4]));
        statement.setInt(9, shiftDAO.add(weeklyShift.getDayShift()[5]));
        statement.setInt(10, shiftDAO.add(weeklyShift.getDayShift()[6]));
        statement.setInt(11, shiftDAO.add(weeklyShift.getNightShift()[0]));
        statement.setInt(12, shiftDAO.add(weeklyShift.getNightShift()[1]));
        statement.setInt(13, shiftDAO.add(weeklyShift.getNightShift()[2]));
        statement.setInt(14, shiftDAO.add(weeklyShift.getNightShift()[3]));
        statement.setInt(15, shiftDAO.add(weeklyShift.getNightShift()[4]));
        statement.setInt(16, shiftDAO.add(weeklyShift.getNightShift()[5]));
        statement.setInt(17, shiftDAO.add(weeklyShift.getNightShift()[6]));
        statement.executeUpdate();
        statement.close();
    }

    public void delete(WeeklyShift weeklyShift) throws SQLException {
        // Remove the WeeklyShift from the cache
        if (getFromCache(weeklyShift) != null) {
            cache.remove(weeklyShift);
        }

        // Delete the associated Shift records
        for (Shift shift : weeklyShift.getDayShift()) {
            shiftDAO.delete(shift.getId());
        }
        for (Shift shift : weeklyShift.getNightShift()) {
            shiftDAO.delete(shift.getId());
        }

        // Delete the WeeklyShift record from the database
        String sql = "DELETE FROM weekly_shift WHERE week_num = ? AND year = ? AND supernum = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, weeklyShift.getWeekNUm());
        statement.setInt(2, weeklyShift.getYear());
        statement.setInt(3, weeklyShift.getSupernum());
        statement.executeUpdate();
        statement.close();
    }
    private WeeklyShift getFromCache(WeeklyShift weeklyShift){
        for(WeeklyShift ws : cache){
            if(ws.getYear() == weeklyShift.getYear() && ws.getSupernum() ==weeklyShift.getSupernum() &&
                    ws.getWeekNUm() == weeklyShift.getWeekNUm())
            {return ws;}
        }
        return null;
    }

    private WeeklyShift getWeeklyShiftFromDatabase(ResultSet resultSet) throws SQLException {
        WeeklyShift weeklyShift = new WeeklyShift(
                resultSet.getInt("week_num"),
                resultSet.getInt("year"),
                resultSet.getInt("supernum"));

        Shift[] dayshift = new Shift[7];
        dayshift[0] = this.shiftDAO.get(resultSet.getInt("day_shift_1"));
        dayshift[1] = this.shiftDAO.get(resultSet.getInt("day_shift_2"));
        dayshift[2] = this.shiftDAO.get(resultSet.getInt("day_shift_3"));
        dayshift[3] = this.shiftDAO.get(resultSet.getInt("day_shift_4"));
        dayshift[4] = this.shiftDAO.get(resultSet.getInt("day_shift_5"));
        dayshift[5] = this.shiftDAO.get(resultSet.getInt("day_shift_6"));
        dayshift[6] = this.shiftDAO.get(resultSet.getInt("day_shift_7"));
        weeklyShift.setDayShift(dayshift);


        Shift[] nightshift = new Shift[7];
        nightshift[0] = this.shiftDAO.get(resultSet.getInt("night_shift_1"));
        nightshift[1] = this.shiftDAO.get(resultSet.getInt("night_shift_2"));
        nightshift[2] = this.shiftDAO.get(resultSet.getInt("night_shift_3"));
        nightshift[3] = this.shiftDAO.get(resultSet.getInt("night_shift_4"));
        nightshift[4] = this.shiftDAO.get(resultSet.getInt("night_shift_5"));
        nightshift[5] = this.shiftDAO.get(resultSet.getInt("night_shift_6"));
        nightshift[6] = this.shiftDAO.get(resultSet.getInt("night_shift_7"));
        weeklyShift.setNightShift(nightshift);

        return weeklyShift;
    }
}