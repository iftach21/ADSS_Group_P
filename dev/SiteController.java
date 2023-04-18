import java.util.List;

public class SiteController {
    private List<Site> _sites;

    public SiteController(List<Site> sites)
    {
        this._sites = sites;
    }

    public Site getSiteById(int id)
    {
        for (Site site: _sites) {
            if (site.getSiteId() == id)
                return site;
        }
        return null;
    }
}
