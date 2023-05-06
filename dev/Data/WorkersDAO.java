package Data;

import Domain.Employee.Workers;

import java.util.ArrayList;
import java.util.List;

public class WorkersDAO {
    private static WorkersDAO instance = null;
    private final List<Workers> allworkerslist; //holds all the workers available
    private WorkersDAO(){
        allworkerslist = new ArrayList<>();
    }
    public static WorkersDAO getInstance(){
        if(instance==null){
            instance =  new WorkersDAO();
        }
        return instance;
    }

    public List<Workers> getAllworkerslist() {
        return allworkerslist;
    }

    public Workers get(int id){
        //todo: complete later
        return null;
    }
    public void update(){
        //todo: complete
    }

    public void add(Workers w){
        //verify that the w allready exist.
        //todo: complete;
    }
    public void delete(int id){
        //todo: complete;
    }
}
