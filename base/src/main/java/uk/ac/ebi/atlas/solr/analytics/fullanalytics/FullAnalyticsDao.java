package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.utils.ResourceUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class FullAnalyticsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FullAnalyticsDao.class);

    private final AnalyticsClient analyticsClient;
    private final String avgExpressionOverBioentityIdentifiersSortedBySpecificityJsonFacet;
    private final String maxExpressionOverBioentityIdentifiersJsonFacet;
    private final String avgExpressionOverBioentityIdentifiersSortedByExpressionJsonFacet;

    @Inject
    public FullAnalyticsDao(AnalyticsClient analyticsClient,
                            @Value("classpath:/uk/ac/ebi/atlas/solr/analytics/avgExpressionOverBioentityIdentifiers.json")
                                    Resource avgExpressionOverBioentityIdentifiersJson,
                            @Value("classpath:/uk/ac/ebi/atlas/solr/analytics/maxExpressionOverBioentityIdentifiers.json")
                                    Resource maxExpressionOverBioentityIdentifiersJson,
                            @Value("classpath:/uk/ac/ebi/atlas/solr/analytics/avgExpressionOverBioentityIdentifiersSortedByExpression.json")
                                    Resource maxExpressionOverBioentityIdentifiersSortedByExpressionJson) {
        this.analyticsClient = analyticsClient;
        avgExpressionOverBioentityIdentifiersSortedBySpecificityJsonFacet =
                ResourceUtils.readPlainTextResource(avgExpressionOverBioentityIdentifiersJson);
        maxExpressionOverBioentityIdentifiersJsonFacet =
                ResourceUtils.readPlainTextResource(maxExpressionOverBioentityIdentifiersJson);
        avgExpressionOverBioentityIdentifiersSortedByExpressionJsonFacet =
                ResourceUtils.readPlainTextResource(maxExpressionOverBioentityIdentifiersSortedByExpressionJson);
    }

    // When searching all assay groups we can use these:
    // private static final String SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE = "expression_level:[{0} TO *]";
    // private static final String NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE = "('{'!terms f=bioentity_identifier'}'{0}) AND expression_level:[{1} TO *]";
    // private static final String NON_SELECTED_ASSAY_GROUP_IDS_Q_TEMPLATE_ALT = "expression_level:[{1} TO *]";

    // Observed a slowdown in terms queries that exceed 30,000 terms
    // Disclaimer: this is a performance optimisation that needs to be carefully applied so that the modified query
    // doesn’t affect the results of the whole output (see the search service that calls this DAO). Here we’re
    // improving query speed at the expense of a bigger JSON document to parse. We can apply this when we search genes
    // in non-selected assay groups (the second query) because the service will score the genes returned from the first
    // query, superfluous data will be ignored.
    // This is a very rough approximation and we need to test consistently to see if it’s actually the case and, most
    // of all, if it’s something that happens in any index and across all queries. In order to be on the safe side it’s
    // left here for the time being.
    private static final int TERMS_QUERY_SIZE_THRESHOLD = 30000;

    public String genesInAllAssayGroupsWithAverageExpressionSortedByCounts(String experimentAccession,
                                                                           double expressionLevelThreshold) {
        AnalyticsQueryBuilder queryBuilder = new AnalyticsQueryBuilder();
        queryBuilder
                .filterExperimentAccession(experimentAccession)
                .queryExpressionLevel(expressionLevelThreshold)
                .setJsonFacet(avgExpressionOverBioentityIdentifiersSortedBySpecificityJsonFacet);

        LOGGER.debug(
                "Searching for genes in {} (cutoff={}) across all assay groups...",
                experimentAccession, expressionLevelThreshold);
        return analyticsClient.fetchResponseAsString(queryBuilder.build());
    }

    public String genesInAssayGroupsWithAverageExpressionSortedByCounts(String experimentAccession,
                                                                        Set<String> sliceAssayGroupIds,
                                                                        Set<String> selectedAssayGroupIds,
                                                                        double expressionLevelThreshold) {
        AnalyticsQueryBuilder queryBuilder = new AnalyticsQueryBuilder();
        queryBuilder
                .filterExperimentAccession(experimentAccession)
                .filterAssayGroupIds(sliceAssayGroupIds)
                .queryExpressionLevel(expressionLevelThreshold)
                .queryAssayGroupIds(selectedAssayGroupIds)
                .setJsonFacet(avgExpressionOverBioentityIdentifiersSortedBySpecificityJsonFacet);

        LOGGER.debug(
                "Searching for genes in {} (cutoff={}) across {}/{} assay groups...",
                experimentAccession, expressionLevelThreshold, selectedAssayGroupIds.size(), sliceAssayGroupIds.size());
        return analyticsClient.fetchResponseAsString(queryBuilder.build());
    }

    public String genesNotInAssayGroupsWithMaxExpressionSortedByCounts(String experimentAccession,
                                                                       Set<String> sliceAssayGroupIds,
                                                                       Set<String> selectedAssayGroupIds,
                                                                       Set<String> geneIds,
                                                                       double expressionLevelThreshold) {
        AnalyticsQueryBuilder queryBuilder = new AnalyticsQueryBuilder();
        queryBuilder
                .filterExperimentAccession(experimentAccession)
                .filterAssayGroupIds(sliceAssayGroupIds)
                .queryExpressionLevel(expressionLevelThreshold)
                .queryNotAssayGroupIds(selectedAssayGroupIds)
                .setJsonFacet(maxExpressionOverBioentityIdentifiersJsonFacet);

        if (geneIds.size() < TERMS_QUERY_SIZE_THRESHOLD) {
            queryBuilder.queryBioentityIdentifiers(geneIds);
        }

        LOGGER.debug(
                "Searching for max expression in {} (cutoff={}) over {} genes, across {}/{} assay groups...",
                experimentAccession, expressionLevelThreshold, geneIds.size(), selectedAssayGroupIds.size(), sliceAssayGroupIds.size());
        return analyticsClient.fetchResponseAsString(queryBuilder.build());
    }

    // Non-specific
    public String mostExpressedGenesInAllAssayGroupsWithAverageExpression(String experimentAccession,
                                                                         double expressionLevelThreshold) {
        AnalyticsQueryBuilder queryBuilder = new AnalyticsQueryBuilder();
        queryBuilder
                .filterExperimentAccession(experimentAccession)
                .queryExpressionLevel(expressionLevelThreshold)
                .setJsonFacet(avgExpressionOverBioentityIdentifiersSortedByExpressionJsonFacet);

        LOGGER.debug(
                "Searching for genes in {} (cutoff={}) across all assay groups...",
                experimentAccession, expressionLevelThreshold);
        return analyticsClient.fetchResponseAsString(queryBuilder.build());
    }
}
