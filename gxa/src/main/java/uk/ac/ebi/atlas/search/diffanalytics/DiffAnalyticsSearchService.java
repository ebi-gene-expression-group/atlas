package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
@Scope("prototype")
public class DiffAnalyticsSearchService {

    private final DiffAnalyticsDao diffAnalyticsDao;

    @Inject
    public DiffAnalyticsSearchService(DiffAnalyticsDao diffAnalyticsDao) {
        this.diffAnalyticsDao = diffAnalyticsDao;
    }


    public List<DiffAnalytics> fetchTopWithoutCountAnySpecies(String geneId) {
        Collection<String> geneIds = Lists.newArrayList(geneId);

        String species = "";

        return diffAnalyticsDao.fetchTopExpressions(ImmutableSet.<IndexedAssayGroup>of(), geneIds, species);
    }


}
