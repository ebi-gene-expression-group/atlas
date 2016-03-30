package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.utils.CountingVisitor;
import uk.ac.ebi.atlas.utils.Visitor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class DiffAnalyticsSearchService {

    private DiffAnalyticsDao diffAnalyticsDao;
    private DifferentialConditionsSearchService differentialConditionsSearchService;
    private SolrQueryService solrQueryService;

    @Inject
    public DiffAnalyticsSearchService(DiffAnalyticsDao diffAnalyticsDao,
                                      DifferentialConditionsSearchService differentialConditionsSearchService,
                                      SolrQueryService solrQueryService
                                     ) {
        this.diffAnalyticsDao = diffAnalyticsDao;
        this.differentialConditionsSearchService = differentialConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }


    public int visitEachExpression(String geneQuery, String condition, String specie, boolean isExactMatch, Visitor<DiffAnalytics> visitor) {

        Optional<Collection<IndexedAssayGroup>> contrastsResult = findContrasts(condition);

        String species = StringUtils.isNotBlank(specie) ? specie : "";

        Optional<Set<String>> geneIdsResult = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, species, isExactMatch);

        if (geneIdsResult.isPresent() && geneIdsResult.get().isEmpty()
                 || contrastsResult.isPresent() && contrastsResult.get().isEmpty()) {
             // no contrasts when condition specified, or no genes when gene ids specified,
             // so return empty results
             return 0;
         }

        CountingVisitor<DiffAnalytics> counter = new CountingVisitor<>(visitor);

        diffAnalyticsDao.visitEachExpression(contrastsResult, geneIdsResult, counter, species);

        return counter.getCount();

    }

    public List<DiffAnalytics> fetchTopWithoutCountAnySpecies(String geneId) {
        Collection<String> geneIds = Lists.newArrayList(geneId);

        String species = "";

        return diffAnalyticsDao.fetchTopExpressions(Optional.<Collection<IndexedAssayGroup>>absent(), Optional.of(geneIds), species);
    }

    public DiffAnalyticsList fetchTopAnySpecies(Collection<String> geneIdentifiers) {

        if (CollectionUtils.isNotEmpty(geneIdentifiers)) {

            List<DiffAnalytics> expressions = diffAnalyticsDao.fetchTopExpressions(Optional.<Collection<IndexedAssayGroup>>absent(),
                    Optional.of(geneIdentifiers), "");

            int resultCount = diffAnalyticsDao.fetchResultCount(Optional.<Collection<IndexedAssayGroup>>absent(),
                    Optional.of(geneIdentifiers), "");

            return new DiffAnalyticsList(expressions, resultCount);

        }
        return new DiffAnalyticsList();
    }

    public DiffAnalyticsList fetchTop(String geneQuery, String condition, String species, boolean isExactMatch) {

        Optional<Collection<IndexedAssayGroup>> contrastsResult = findContrasts(condition);

        //TODO: move outside into caller, because this is called twice, here and in BaselineExperimentAssayGroupSearchService, and BaselineExperimentProfileSearchService
        Optional<Set<String>> geneIdsResult = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, species.toLowerCase(), isExactMatch);


        if (geneIdsResult.isPresent() && geneIdsResult.get().isEmpty()
                || contrastsResult.isPresent() && contrastsResult.get().isEmpty()) {
            // no contrasts when condition specified, or no genes when gene ids specified,
            // so return empty results
            return new DiffAnalyticsList();
        }

        List<DiffAnalytics> expressions = diffAnalyticsDao.fetchTopExpressions(contrastsResult, geneIdsResult, species);
        int resultCount = diffAnalyticsDao.fetchResultCount(contrastsResult, geneIdsResult, species);

        return new DiffAnalyticsList(expressions, resultCount);
    }

    private Optional<Collection<IndexedAssayGroup>> findContrasts(String condition) {
        if (StringUtils.isBlank(condition)) {
            return Optional.absent();
        }

        Collection<IndexedAssayGroup> contrasts = differentialConditionsSearchService.findContrasts(condition);

        return Optional.of(contrasts);
    }

}
