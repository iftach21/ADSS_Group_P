package DataAccesObjects.Employee;
import java.sql.*;

import Domain.Employee.Driver;
import Domain.Employee.Workers;
import Domain.Enums.TempTypeFactory;
import Domain.Enums.WeightTypeFactory;
import Domain.Enums.WindowType;

import java.util.ArrayList;
import java.util.List;

import static Domain.Enums.WindowType.*;

public class WorkersDAO {
    private List <Workers> cache = new ArrayList<>();
    private final java.sql.Connection conn = DataAccesObjects.Connection.getConnectionToDatabase();
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
        List<Workers> workers = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT id, name, contract, start_date, wage, phoneNUM, personalinfo, bankNum, pro0, pro1, pro2, pro3, pro4, pro5, pro6, weightType, TempType, day1, day2, day3, day4, day5, day6, day7, night1, night2, night3, night4, night5, night6, night7 FROM workers";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {

                 int id = rs.getInt("id");
                 Workers worker = this.gettingNewEmployee(rs,id);
                 this.cache.add(worker);
                 workers.add(worker);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return workers;
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
            String sql = "SELECT name, contract, start_date, wage, phoneNUM, personalinfo, bankNum, pro0, pro1, pro2, pro3, pro4, pro5, pro6, weightType, TempType, day1, day2, day3, day4, day5, day6, day7, night1, night2, night3, night4, night5, night6, night7 FROM workers WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {


                workerToReturn = this.gettingNewEmployee(rs,id);
                this.cache.add(workerToReturn);

            } else {
                System.out.println("No worker found with ID " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return workerToReturn;



    }
    public void update(Workers worker) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE workers SET name=?, contract=?, start_date=?, wage=?, phoneNUM=?, personalinfo=?, bankNum=?, pro0=?, pro1=?, pro2=?, pro3=?, pro4=?, pro5=?, pro6=?, weightType=?, TempType=?, day1=?, day2=?, day3=?, day4=?, day5=?, day6=?, day7=?, night1=?, night2=?, night3=?, night4=?, night5=?, night6=?, night7=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, worker.getName());
            stmt.setString(2, worker.getContract());
            stmt.setString(3, worker.getStart_date());
            stmt.setInt(4, worker.getWage());
            stmt.setInt(5, worker.getPhoneNUM());
            stmt.setString(6, worker.getPersonalinfo());
            stmt.setInt(7, worker.getBankNum());
            stmt.setBoolean(8, worker.getPro()[0]);
            stmt.setBoolean(9, worker.getPro()[1]);
            stmt.setBoolean(10, worker.getPro()[2]);
            stmt.setBoolean(11, worker.getPro()[3]);
            stmt.setBoolean(12, worker.getPro()[4]);
            stmt.setBoolean(13, worker.getPro()[5]);
            stmt.setBoolean(14, worker.getPro()[6]);
            stmt.setString(15, worker.getWeightType());
            stmt.setString(16, worker.getTempType());
            stmt.setBoolean(17, worker.canIworkat(day1));
            stmt.setBoolean(18, worker.canIworkat(day2));
            stmt.setBoolean(19, worker.canIworkat(day3));
            stmt.setBoolean(20, worker.canIworkat(day4));
            stmt.setBoolean(21, worker.canIworkat(day5));
            stmt.setBoolean(22, worker.canIworkat(day6));
            stmt.setBoolean(23, worker.canIworkat(day7));
            stmt.setBoolean(24, worker.canIworkat(night1));
            stmt.setBoolean(25, worker.canIworkat(night2));
            stmt.setBoolean(26, worker.canIworkat(night3));
            stmt.setBoolean(27, worker.canIworkat(night4));
            stmt.setBoolean(28, worker.canIworkat(night5));
            stmt.setBoolean(29, worker.canIworkat(night6));
            stmt.setBoolean(30, worker.canIworkat(night7));
            stmt.setInt(31, worker.getId());

            int rowsAffected = stmt.executeUpdate();

            //cache handling
            this.deleteFromCache(worker.getId());
            this.cache.add(worker);

            if (rowsAffected == 0) {
                System.out.println("No worker found with ID " + worker.getId() + " to update.");
            } else {
                System.out.println("Worker with ID " + worker.getId() + " updated successfully.");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public void add(Workers w){
        try {
            String sql = "INSERT INTO workers (id, name, contract, start_date, wage, phoneNUM, personalinfo, bankNum, pro0, pro1, pro2, pro3, pro4, pro5, pro6, weightType, TempType, day1, day2, day3, day4, day5, day6, day7, night1, night2, night3, night4, night5, night6, night7) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, w.getId());
            stmt.setString(2, w.getName());
            stmt.setString(3, w.getContract());
            stmt.setString(4, w.getStart_date());
            stmt.setInt(5, w.getWage());
            stmt.setInt(6, w.getPhoneNUM());
            stmt.setString(7, w.getPersonalinfo());
            stmt.setInt(8, w.getBankNum());
            stmt.setBoolean(9, w.getPro()[0]);
            stmt.setBoolean(10, w.getPro()[1]);
            stmt.setBoolean(11, w.getPro()[2]);
            stmt.setBoolean(12, w.getPro()[3]);
            stmt.setBoolean(13, w.getPro()[4]);
            stmt.setBoolean(14, w.getPro()[5]);
            stmt.setBoolean(15, w.getPro()[6]);
            stmt.setString(16, w.getWeightType());
            stmt.setString(17, w.getTempType());
            stmt.setBoolean(18, w.canIworkat(day1));
            stmt.setBoolean(19, w.canIworkat(day2));
            stmt.setBoolean(20, w.canIworkat(day3));
            stmt.setBoolean(21, w.canIworkat(day4));
            stmt.setBoolean(22, w.canIworkat(day5));
            stmt.setBoolean(23, w.canIworkat(day6));
            stmt.setBoolean(24, w.canIworkat(day7));
            stmt.setBoolean(25, w.canIworkat(night1));
            stmt.setBoolean(26, w.canIworkat(night2));
            stmt.setBoolean(27, w.canIworkat(night3));
            stmt.setBoolean(28, w.canIworkat(night4));
            stmt.setBoolean(29, w.canIworkat(night5));
            stmt.setBoolean(30, w.canIworkat(night6));
            stmt.setBoolean(31, w.canIworkat(night7));
            stmt.executeUpdate();

            cache.add(w);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM workers WHERE id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No worker found with ID " + id);
                deleteFromCache(id);
            } else {
                deleteFromCache(id);
                System.out.println("Worker with ID " + id + " deleted successfully");

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }


    private Workers getFromCache(int id){
        for (Workers workers : this.cache) {
            if (id == workers.getId()) {
                return workers;
            }
        }
        return null;
    }

    private Workers gettingNewEmployee(ResultSet rs,int id) throws SQLException {
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

        ArrayList<WindowType> windowTypes = new ArrayList<>();

        if(rs.getBoolean("day1")){windowTypes.add(day1);}
        if(rs.getBoolean("day2")){windowTypes.add(day2);}
        if(rs.getBoolean("day3")){windowTypes.add(day3);}
        if(rs.getBoolean("day4")){windowTypes.add(day4);}
        if(rs.getBoolean("day5")){windowTypes.add(day5);}
        if(rs.getBoolean("day6")){windowTypes.add(day6);}
        if(rs.getBoolean("day7")){windowTypes.add(day7);}

        if(rs.getBoolean("night1")){windowTypes.add(night1);}
        if(rs.getBoolean("night2")){windowTypes.add(night2);}
        if(rs.getBoolean("night3")){windowTypes.add(night3);}
        if(rs.getBoolean("night4")){windowTypes.add(night4);}
        if(rs.getBoolean("night5")){windowTypes.add(night5);}
        if(rs.getBoolean("night6")){windowTypes.add(night6);}
        if(rs.getBoolean("night7")){windowTypes.add(night7);}



        //=============================================================================



        //checking if is a driver of not:
        if(weightType != null && tempType != null){
            Workers workers = new Driver(id,name,contract,startDate,
                    wage,phoneNum,personalInfo,bankNum, TempTypeFactory.TempLevelFromString(tempType), WeightTypeFactory.weightTypeFromString(weightType));
            //adding windowtypes
            for(WindowType wt: windowTypes){workers.addwindow(wt);}

            return workers;

        }

        else{
            Workers workerToReturn = new Workers(id,name,contract,startDate,
                    wage,phoneNum,personalInfo,bankNum);
            //getting the prof for him
            workerToReturn.setPro(0,pro0);
            workerToReturn.setPro(1,pro1);
            workerToReturn.setPro(2,pro2);
            workerToReturn.setPro(3,pro3);
            workerToReturn.setPro(4,pro4);
            workerToReturn.setPro(5,pro5);
            workerToReturn.setPro(6,pro6);
            //adding windowtypes
            for(WindowType wt: windowTypes){workerToReturn.addwindow(wt);}

            return workerToReturn;
        }


    }

    public void deleteFromCache(int id){
        if(cache.size()==0){return;}
        this.cache.removeIf(worker -> worker.getId() == id);
    }

}
