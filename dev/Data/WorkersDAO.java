package Data;
import java.sql.*;

import Domain.Employee.Driver;
import Domain.Employee.Workers;
import Domain.Enums.TempTypeFactory;
import Domain.Enums.WeightTypeFactory;

import java.util.ArrayList;
import java.util.List;

public class WorkersDAO {
    private List <Workers> cache = new ArrayList<>();
    private Connection conn = Database.Connection.getConnectionToDatabase();
    private static WorkersDAO instance = null;
    private WorkersDAO() throws SQLException {
    }

    public static WorkersDAO getInstance() throws SQLException {
        if(instance==null){
            instance =  new WorkersDAO();
        }
        return instance;
    }

    public List<Workers> getAllworkerslist() {
        //todo: need to be completed
        return null;
    }

    public Workers get(int id){
        //-------------------------------------------------
        //checking if its in the cache:
        Workers workerToReturn = this.getFromCache(id);
        if(workerToReturn != null){return workerToReturn;}
        //--------------------------------------------------

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT name, contract, start_date, wage, phoneNUM, personalinfo, bankNum, pro0, pro1, pro2, pro3, pro4, pro5, pro6, weightType, TempType FROM workers WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {

                //getiing all the things for him!
                //======================================================================
                String name = rs.getString("name");
                String contract = rs.getString("contract");
                String startDate = rs.getString("start_date");
                int wage = rs.getInt("wage");
                int phoneNum = rs.getInt("phoneNUM");
                String personalInfo = rs.getString("personalinfo");
                int bankNum = rs.getInt("bankNum");
                boolean pro0 = rs.getBoolean("pro0");
                boolean pro1 = rs.getBoolean("pro1");
                boolean pro2 = rs.getBoolean("pro2");
                boolean pro3 = rs.getBoolean("pro3");
                boolean pro4 = rs.getBoolean("pro4");
                boolean pro5 = rs.getBoolean("pro5");
                boolean pro6 = rs.getBoolean("pro6");
                String weightType = rs.getString("weightType");
                String tempType = rs.getString("TempType");


                //=============================================================================

                //getting the prof for him
                workerToReturn.setPro(0,pro0);
                workerToReturn.setPro(1,pro1);
                workerToReturn.setPro(2,pro2);
                workerToReturn.setPro(3,pro3);
                workerToReturn.setPro(4,pro4);
                workerToReturn.setPro(5,pro5);
                workerToReturn.setPro(6,pro6);

                //checking if is a driver of not:
                if(weightType != null && tempType != null){
                    workerToReturn = new Driver(id,name,contract,startDate,
                            wage,phoneNum,personalInfo,bankNum, TempTypeFactory.TempLevelFromString(tempType), WeightTypeFactory.weightTypeFromString(weightType));
                }

                else{
                    workerToReturn = new Workers(id,name,contract,startDate,
                        wage,phoneNum,personalInfo,bankNum);
                }

                return workerToReturn;



            } else {
                System.out.println("No worker found with ID " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return workerToReturn;



    }
    public void update(){
        //todo: complete
    }

    public void add(Workers w){
        try {
            String sql = "INSERT INTO workers (name, contract, start_date, wage, phoneNUM, personalinfo, bankNum, pro0, pro1, pro2, pro3, pro4, pro5, pro6, weightType, TempType) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, w.getName());
            stmt.setString(2, w.getContract());
            stmt.setString(3, w.getStart_date());
            stmt.setInt(4, w.getWage());
            stmt.setInt(5, w.getPhoneNUM());
            stmt.setString(6, w.getPersonalinfo());
            stmt.setInt(7, w.getBankNum());
            stmt.setBoolean(8, w.getPro()[0]);
            stmt.setBoolean(9, w.getPro()[1]);
            stmt.setBoolean(10, w.getPro()[2]);
            stmt.setBoolean(11, w.getPro()[3]);
            stmt.setBoolean(12, w.getPro()[4]);
            stmt.setBoolean(13, w.getPro()[5]);
            stmt.setBoolean(14, w.getPro()[6]);
            stmt.setString(15, w.getWeightType());
            stmt.setString(16, w.getTempType());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void delete(int id){
        //todo: complete;
    }

    private Workers getFromCache(int id){
        for (Workers workers : this.cache) {
            if (workers.getId() == id) {
                return workers;
            }
        }
        return null;
    }
}
