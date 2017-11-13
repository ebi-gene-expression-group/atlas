package uk.ac.ebi.atlas.search.diffanalytics;


import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class DiffAnalyticsSearchService {

    private final DiffAnalyticsDao diffAnalyticsDao;

    @Inject
    public DiffAnalyticsSearchService(DiffAnalyticsDao diffAnalyticsDao) {
        this.diffAnalyticsDao = diffAnalyticsDao;
    }


    public List<DiffAnalytics> fetchTopWithoutCountAnySpecies(String geneId) {
        return diffAnalyticsDao.fetchTopExpressions(geneId);
    }


}
