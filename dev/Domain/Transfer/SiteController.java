package Domain.Transfer;

import Data.SiteDAO;

import java.sql.SQLException;

public class SiteController {

    //public Map<Integer, Truck> _trucks;
    private final SiteDAO siteDAO;

    public SiteController() throws SQLException {
        this.siteDAO = SiteDAO.getInstance();
    }

    public Site getSiteById(int id)
    {
        return siteDAO.get(id);
    }
}
