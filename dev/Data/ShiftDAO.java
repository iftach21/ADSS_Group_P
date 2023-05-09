package Data;

import Domain.Employee.Shift;
import Domain.Employee.WeeklyShift;
import Domain.Employee.Workers;


import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class ShiftDAO {
    private Connection conn = Database.Connection.getConnectionToDatabase();
    private static ShiftDAO instance = null;
    private ArrayList<Shift> ShiftList; //holds all the weeklyshifts

    private ShiftDAO() throws SQLException {ShiftList = new ArrayList<>();
    }
    public static ShiftDAO getInstance() throws SQLException {
        if(instance==null){
            instance = new ShiftDAO();
        }
        return instance;
    }

    public int add(Shift s){
        int primaryKey = -1;

        try {
            // create SQL query string with placeholders for parameter values
            String sql = "INSERT INTO Shift (date, shift_manager_id, log, start_time) VALUES (?, ?, ?, ?)";

            // create PreparedStatement object and set parameter values
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, Date.valueOf(s.getDate()));
            stmt.setInt(2, s.getShiftManagerID());
            stmt.setString(3, s.getLog());
            stmt.setTime(4, Time.valueOf(s.getStartTime()));

            // execute query to insert new record
            stmt.executeUpdate();

            // retrieve generated keys and get primary key value
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                primaryKey = rs.getInt(1);
                s.setId(primaryKey);
            }

            // close resources
            rs.close();
            stmt.close();
            conn.close();

            System.out.println("New record inserted into Shift table with primary key " + primaryKey);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return primaryKey;
    }
    public Shift get(int shiftId) throws SQLException {
        for(Shift shift: ShiftList){
            if (shift.getShiftID()==shiftId){
                return shift;
            }
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Shift WHERE shift_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, shiftId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            Shift shift = new Shift();
            shift.setShiftID(rs.getInt("shift_id"));
            shift.setDate(String.valueOf(rs.getDate("date")));
            int id=rs.getInt("shift_manager_id");
            shift.setLog(rs.getString("log"));
            shift.setStartTime(String.valueOf(rs.getTime("start_time")));
            return shift;
        }
        return null;

    }

    public void update(Shift shiftToUpdate){
        //todo: complete: update workers_shift table
    }

    public void delete(Shift shiftToDelete){
        //todo: make sure to delete the workers_shift table accord..
        //allso make sure to delete from cache
    }
}
