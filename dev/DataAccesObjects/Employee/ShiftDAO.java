package DataAccesObjects.Employee;

import DataAccesObjects.Pair;
import Domain.Employee.Driver;
import Domain.Employee.Shift;
import Domain.Employee.ShiftRequirement;
import Domain.Employee.Workers;


import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class ShiftDAO {
    private java.sql.Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
    private static ShiftDAO instance = null;
    private int shift_id_counter;

    private ShiftWorkerDAO shiftWorkerDAO=ShiftWorkerDAO.getInstance();

    private ArrayList<Shift> ShiftList; //holds all the weeklyshifts

    private ShiftDAO() throws SQLException {ShiftList = new ArrayList<>();
        shift_id_counter = this.getMaxShiftId() + 1;
    }
    public static ShiftDAO getInstance() throws SQLException {
        if(instance==null){
            instance = new ShiftDAO();
        }
        return instance;
    }

    public int add(Shift s) {
        int primaryKey = -1;
        int count = 0;

        try {
            // create SQL query string with placeholders for parameter values
            String sql = "INSERT INTO Shift (shift_id, date, shift_manager_id, log, start_time, req_1, req_2, req_3, req_4, req_5, req_6, req_7) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // create PreparedStatement object and set parameter values
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shift_id_counter+1);
            stmt.setString(2, s.getDate());
            stmt.setInt(3, s.getShiftManagerID());
            stmt.setString(4, s.getLog());
            stmt.setString(5, s.getStartTime());
            stmt.setInt(6, s.getShiftRequirement().getreqbyprof(0));
            stmt.setInt(7, s.getShiftRequirement().getreqbyprof(1));
            stmt.setInt(8, s.getShiftRequirement().getreqbyprof(2));
            stmt.setInt(9, s.getShiftRequirement().getreqbyprof(3));
            stmt.setInt(10, s.getShiftRequirement().getreqbyprof(4));
            stmt.setInt(11, s.getShiftRequirement().getreqbyprof(5));
            stmt.setInt(12, s.getShiftRequirement().getreqbyprof(6));

            // set primary key value
            shift_id_counter++;
            primaryKey = shift_id_counter;
            s.setId(primaryKey);

            for (ArrayList<Workers> workers: s.getWorkerInShift()){
                for(Workers worker: workers){
                    shiftWorkerDAO.add(worker,s,count);
                }
                count++;
            }
            for (Workers worker: s.getDrivers()) {
                shiftWorkerDAO.add(worker, s, 7);
            }


            // execute query to insert new record
            stmt.executeUpdate();



            // close resources
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return primaryKey;
    }



    public Shift get(int shiftId) throws SQLException {

        for(Shift shift: ShiftList){
            if (shift.getId()==shiftId){
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
            shift.setId(rs.getInt("shift_id"));
            shift.setDate(rs.getString("date"));
            int id=rs.getInt("shift_manager_id");
            shift.setLog(rs.getString("log"));
            shift.setStartTime(rs.getString("start_time"));
            ShiftRequirement shiftRequirement= new ShiftRequirement();
            shiftRequirement.setReq(0,rs.getInt("req_1"));
            shiftRequirement.setReq(1,rs.getInt("req_2"));
            shiftRequirement.setReq(2,rs.getInt("req_3"));
            shiftRequirement.setReq(3,rs.getInt("req_4"));
            shiftRequirement.setReq(4,rs.getInt("req_5"));
            shiftRequirement.setReq(5,rs.getInt("req_6"));
            shiftRequirement.setReq(6,rs.getInt("req_7"));
            shift.setShiftRequirement(shiftRequirement);

            //getting the workers back in:
            List<Pair<Workers, Integer>> listForShift = shiftWorkerDAO.get(shiftId);
            for(Pair p: listForShift){
                if((Integer) p.getSecond() == -1){
                    shift.addDriver((Driver)p.getFirst());
                }
                else{
                    shift.insertToShift((Workers) p.getFirst(),(Integer) p.getSecond());
                }

            }

            return shift;
        }
        return null;
    }

    public void update(Shift shift){
        try {
            // create SQL query string with placeholders for parameter values
            String sql = "UPDATE Shift SET date = ?, shift_manager_id = ?, log = ?, start_time = ?, req_1 = ?, req_2 = ?, req_3 = ?, req_4 = ?, req_5 = ?, req_6 = ?, req_7 = ? WHERE shift_id = ?";

            // create PreparedStatement object and set parameter values
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, shift.getDate());
            stmt.setInt(2, shift.getShiftManagerID());
            stmt.setString(3, shift.getLog());
            stmt.setString(4, shift.getStartTime());
            stmt.setInt(5, shift.getShiftRequirement().getreqbyprof(0));
            stmt.setInt(6, shift.getShiftRequirement().getreqbyprof(1));
            stmt.setInt(7, shift.getShiftRequirement().getreqbyprof(2));
            stmt.setInt(8, shift.getShiftRequirement().getreqbyprof(3));
            stmt.setInt(9, shift.getShiftRequirement().getreqbyprof(4));
            stmt.setInt(10, shift.getShiftRequirement().getreqbyprof(5));
            stmt.setInt(11, shift.getShiftRequirement().getreqbyprof(6));
            stmt.setInt(12,shift.getId());

            //return the list of new worker
            Shift oldshift = this.get(shift.getId());
            List<Workers> l1= shift.getDiffWorkers(oldshift);
            //return the list of old worker
            List<Workers> l2= oldshift.getDiffWorkers(shift);

            for(Workers worker:l1){
                shiftWorkerDAO.add(worker,shift,shift.workerPro(worker));
            }
            for(Workers worker:l2){
                shiftWorkerDAO.delete(worker.getId(),shift.getId());
            }

            // execute query to insert new record
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id){
        //allso make sure to delete from cache


        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Shift WHERE shift_id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            stmt = conn.prepareStatement("DELETE FROM shift_worker_in_shift WHERE shift_id = ?");
            stmt.setInt(1, id);
            int rowsAffected2 = stmt.executeUpdate();
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
        this.ShiftList.removeIf(shift -> shift.getId() == id);
    }
    public Shift returnFromCache(int id){
        for (Shift shift : this.ShiftList) {
            if (shift.getId() == id) {
                    return shift;
            }
        }
        return null;
    }
    private int getMaxShiftId() {
        int maxShiftId = -1;

        try {
            String sql = "SELECT MAX(shift_id) FROM Shift";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                maxShiftId = rs.getInt(1);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxShiftId;
    }


}
