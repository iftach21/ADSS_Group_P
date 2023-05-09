package Data;

import Domain.Employee.Shift;
import Domain.Employee.ShiftRequirement;
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
            String sql = "INSERT INTO Shift (date, shift_manager_id, log, start_time, req_1, req_2, req_3, req_4, req_5, req_6, req_7) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // create PreparedStatement object and set parameter values
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, Date.valueOf(s.getDate()));
            stmt.setInt(2, s.getShiftManagerID());
            stmt.setString(3, s.getLog());
            stmt.setTime(4, Time.valueOf(s.getStartTime()));
            stmt.setInt(5, s.getShiftRequirement().getreqbyprof(0));
            stmt.setInt(6, s.getShiftRequirement().getreqbyprof(1));
            stmt.setInt(7, s.getShiftRequirement().getreqbyprof(2));
            stmt.setInt(8, s.getShiftRequirement().getreqbyprof(3));
            stmt.setInt(9, s.getShiftRequirement().getreqbyprof(4));
            stmt.setInt(10, s.getShiftRequirement().getreqbyprof(5));
            stmt.setInt(11, s.getShiftRequirement().getreqbyprof(6));

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
            ShiftRequirement shiftRequirement= new ShiftRequirement();
            shiftRequirement.setReq(0,rs.getInt("req_1"));
            shiftRequirement.setReq(1,rs.getInt("req_2"));
            shiftRequirement.setReq(2,rs.getInt("req_3"));
            shiftRequirement.setReq(3,rs.getInt("req_4"));
            shiftRequirement.setReq(4,rs.getInt("req_5"));
            shiftRequirement.setReq(5,rs.getInt("req_6"));
            shiftRequirement.setReq(6,rs.getInt("req_7"));
            shift.setShiftRequirement(shiftRequirement);
            return shift;
        }
        return null;

    }

    public void update(Shift shift){
        //todo: complete: update workers_shift table

        try {
            // create SQL query string with placeholders for parameter values
            String sql = "UPDATE Shift SET date = ?, shift_manager_id = ?, log = ?, start_time = ?, req_1 = ?, req_2 = ?, req_3 = ?, req_4 = ?, req_5 = ?, req_6 = ?, req_7 = ? WHERE shift_id = ?";

            // create PreparedStatement object and set parameter values
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, Date.valueOf(shift.getDate()));
            stmt.setInt(2, shift.getShiftManagerID());
            stmt.setString(3, shift.getLog());
            stmt.setTime(4, Time.valueOf(shift.getStartTime()));
            stmt.setInt(5, shift.getShiftRequirement().getreqbyprof(0));
            stmt.setInt(6, shift.getShiftRequirement().getreqbyprof(1));
            stmt.setInt(7, shift.getShiftRequirement().getreqbyprof(2));
            stmt.setInt(8, shift.getShiftRequirement().getreqbyprof(3));
            stmt.setInt(9, shift.getShiftRequirement().getreqbyprof(4));
            stmt.setInt(10, shift.getShiftRequirement().getreqbyprof(5));
            stmt.setInt(11, shift.getShiftRequirement().getreqbyprof(6));
            stmt.setInt(12,shift.getId());
            // execute query to insert new record
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("New UPDATE");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id){
        //todo: make sure to delete the workers_shift table accord..
        //allso make sure to delete from cache


        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Shift WHERE shift_id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No shift found with ID " + id);
            } else {
                System.out.println("shift with ID " + id + " deleted successfully");
                deleteFromCache(id);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void deleteFromCache(int id){
        for (Shift shift : this.ShiftList) {
            if (shift.getId() == id) {
                this.ShiftList.remove(shift.getId());
            }
        }
    }
}
