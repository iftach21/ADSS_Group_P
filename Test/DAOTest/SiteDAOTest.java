package DAOTest;

import DataAccesObjects.Transfer.SiteDAO;
import Domain.Transfer.Site;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class SiteDAOTest {
    Site site1;
    Site site2;

    SiteDAO siteDAO;

    public SiteDAOTest() throws SQLException {
        siteDAO = SiteDAO.getInstance();
    }

    @BeforeEach
    void createMockSites()
    {
        //clear database before each Test
        siteDAO.deleteAll();

        //create 2 sites
        this.site1 = new Site(0,"Sano", "Zabotinski 12 Tel-Aviv", "09-7863908", "Moshe", 32.06845601633649, 34.783378215486955);
        this.site2 = new Site(1,"Tara", "Masada 12 Beer-Sheva", "09-7887645", "Ron", 31.262913845991317, 34.79982446327962);

    }

    @Test
    void addAndGetSiteTest(){

        siteDAO.add(site1);
        Assertions.assertEquals(site1.getSiteId(), siteDAO.get(site1.getSiteId()).getSiteId());
        Assertions.assertEquals(site1.getSiteName(), siteDAO.get(site1.getSiteId()).getSiteName());
        Assertions.assertEquals(site1.getSiteAddress(), siteDAO.get(site1.getSiteId()).getSiteAddress());
        Assertions.assertEquals(site1.get_contactName(), siteDAO.get(site1.getSiteId()).get_contactName());
        Assertions.assertEquals(site1.get_phoneNumber(), siteDAO.get(site1.getSiteId()).get_phoneNumber());
        Assertions.assertEquals(site1.getSiteType(), siteDAO.get(site1.getSiteId()).getSiteType());
        Assertions.assertEquals(site1.getLatitude(), siteDAO.get(site1.getSiteId()).getLatitude());
        Assertions.assertEquals(site1.getLongitude(), siteDAO.get(site1.getSiteId()).getLongitude());

        siteDAO.add(site2);
        Assertions.assertEquals(site2.getSiteId(), siteDAO.get(site2.getSiteId()).getSiteId());
        Assertions.assertEquals(site2.getSiteName(), siteDAO.get(site2.getSiteId()).getSiteName());
        Assertions.assertEquals(site2.getSiteAddress(), siteDAO.get(site2.getSiteId()).getSiteAddress());
        Assertions.assertEquals(site2.get_contactName(), siteDAO.get(site2.getSiteId()).get_contactName());
        Assertions.assertEquals(site2.get_phoneNumber(), siteDAO.get(site2.getSiteId()).get_phoneNumber());
        Assertions.assertEquals(site2.getSiteType(), siteDAO.get(site2.getSiteId()).getSiteType());
        Assertions.assertEquals(site2.getLatitude(), siteDAO.get(site2.getSiteId()).getLatitude());
        Assertions.assertEquals(site2.getLongitude(), siteDAO.get(site2.getSiteId()).getLongitude());

    }

    @Test
    void deleteSiteTest(){
        siteDAO.add(site1);
        siteDAO.delete(site1.getSiteId());
        Assertions.assertNull(siteDAO.get(site1.getSiteId()));
    }

    @Test
    void updateSiteTest(){

        siteDAO.add(site1);
        site1.setSiteAddress("some nice place in israel");
        siteDAO.update(site1);
        Assertions.assertEquals("some nice place in israel", siteDAO.get(site1.getSiteId()).getSiteAddress());

    }
}
