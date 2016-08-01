package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
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

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Named
@Scope("prototype")
public class DiffAnalyticsSearchService {

    private final DiffAnalyticsDao diffAnalyticsDao;
    private final DifferentialConditionsSearchService differentialConditionsSearchService;
    private final AnalyticsSearchService analyticsSearchService;

    @Inject
    public DiffAnalyticsSearchService(DiffAnalyticsDao diffAnalyticsDao,
                                      DifferentialConditionsSearchService differentialConditionsSearchService,
                                      AnalyticsSearchService analyticsSearchService) {
        this.diffAnalyticsDao = diffAnalyticsDao;
        this.differentialConditionsSearchService = differentialConditionsSearchService;
        this.analyticsSearchService = analyticsSearchService;
    }


    public int visitEachExpression(SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species,
                                   Visitor<DiffAnalytics> visitor) {

        Collection<IndexedAssayGroup> contrastsResult = findContrasts(conditionQuery.asSolr1DNF());

        ImmutableSet<String> geneIdsResult = analyticsSearchService.searchBioentityIdentifiers(geneQuery, conditionQuery, species);

        if (geneIdsResult.isEmpty() || contrastsResult.isEmpty()) {
             // no contrasts when condition specified, or no genes when gene ids specified, so return empty results
             return 0;
         }

        CountingVisitor<DiffAnalytics> counter = new CountingVisitor<>(visitor);

        diffAnalyticsDao.visitEachExpression(contrastsResult, geneIdsResult, counter, species.mappedName);

        return counter.getCount();

    }

    public List<DiffAnalytics> fetchTopWithoutCountAnySpecies(String geneId) {
        Collection<String> geneIds = Lists.newArrayList(geneId);

        String species = "";

        return diffAnalyticsDao.fetchTopExpressions(ImmutableSet.<IndexedAssayGroup>of(), geneIds, species);
    }


    private Collection<IndexedAssayGroup> findContrasts(String condition) {
        if (isBlank(condition)) {
            return ImmutableSet.of();
        }

        return differentialConditionsSearchService.findContrasts(condition);
    }

}
