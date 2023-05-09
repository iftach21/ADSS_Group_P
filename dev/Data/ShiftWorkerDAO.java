package Data;

import Domain.Employee.Shift;
import Domain.Employee.Workers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ShiftWorkerDAO {

    private Connection conn = Data.Connection.getConnectionToDatabase();
    private static ShiftWorkerDAO instance = null;

    private ShiftWorkerDAO() throws SQLException {
    }

    public static ShiftWorkerDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ShiftWorkerDAO();
        }
        return instance;
    }

    public void add(Workers w, Shift s, int workersPro) {
        try {
            String sql = "INSERT INTO shift_worker_in_shift (shift_id, worker_id, workers_pro) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, s.getShiftID());
            stmt.setInt(2, w.getId());
            stmt.setString(3, String.valueOf(workersPro));


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    //!!!
    public List<Pair<Workers, Integer>> get(int shiftId) throws SQLException {
        List<Pair<Workers, Integer>> workersList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        // Execute a SELECT query to retrieve all worker IDs from shift_worker_in_shift
        String sql = "SELECT * FROM shift_worker_in_shift WHERE shift_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, shiftId);
        rs = stmt.executeQuery();
        // Iterate through the result set and print each worker ID
        while (rs.next()) {
            int workerId = rs.getInt("worker_id");
            int workersPro = rs.getInt("workersPro");
            Pair<Workers, Integer> pair = new Pair<Workers, Integer>(WorkersDAO.getInstance().get(workerId), workersPro);
            workersList.add(pair);
        }
        return workersList;
    }
    public void update(){
        //todo: complete
    }

    private void delete(int idWorkerShift){

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM shift_worker_in_shift WHERE id = ?");
            stmt.setInt(1, idWorkerShift);
            int rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    void delete(int idWorker, int idShift){

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM shift_worker_in_shift WHERE worker_id = ? AND shift_id=?");
            stmt.setInt(1, idWorker);
            stmt.setInt(2, idShift);
            int rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
