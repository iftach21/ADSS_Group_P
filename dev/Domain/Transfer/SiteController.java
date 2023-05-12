package Domain.Transfer;

import DataAccesObjects.Transfer.SiteDAO;

import java.sql.SQLException;

public class SiteController {

    //public Map<Integer, Truck> _trucks;
    private static SiteController Instance = null;
    private final SiteDAO siteDAO;

    public SiteController() throws SQLException {
        this.siteDAO = SiteDAO.getInstance();
    }

    public static SiteController getInstance() throws SQLException {
        if(Instance==null){Instance = new SiteController();}
        return Instance;
    }

    public Site getSiteById(int id)
    {
        return siteDAO.get(id);
    }
}
