package uk.ac.ebi.atlas.experimentpage.baseline;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.BioentityIdentifierSearchService;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.ExperimentRequestPreferencesSolrQueryFactory;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.ASSAY_GROUP_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVELS;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVELS_FPKM;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL_FPKM;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.asAnalyticsSchemaField;

@Named
public class BaselineExperimentProfilesService {

    private final AnalyticsCollectionProxy analyticsCollectionProxy;
    private final BioentityIdentifierSearchService bioentityIdentifierSearchService;

    @Inject
    public BaselineExperimentProfilesService(SolrCloudCollectionProxyFactory collectionProxyFactory,
                                             BioentityIdentifierSearchService bioentityIdentifierSearchService) {
        this.analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
        this.bioentityIdentifierSearchService = bioentityIdentifierSearchService;

    }

    public GeneProfilesList<BaselineProfile> searchTopGeneProfiles(String experimentAccession,
                                                                   List<AssayGroup> assayGroups,
                                                                   BaselineRequestPreferences<?> preferences) {

        List<String> topGeneIds = preferences.isSpecific() ?
                bioentityIdentifierSearchService.searchSpecificGenesInBaselineExperiment(
                        experimentAccession, preferences) :
                bioentityIdentifierSearchService.searchMostExpressedGenesInBaselineExperiment(
                        experimentAccession, preferences);

        return fetchProfiles(topGeneIds, assayGroups, preferences, experimentAccession);
    }

    public long fetchCount(String experimentAccession, BaselineRequestPreferences<?> preferences) {
        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQueryForBaselineExperiment(
                        experimentAccession, preferences);

        return analyticsCollectionProxy.fieldStats(BIOENTITY_IDENTIFIER, solrQuery).getCountDistinct();
    }

    public GeneProfilesList<BaselineProfile> fetchProfiles(List<String> geneIds,
                                                           List<AssayGroup> assayGroups,
                                                           BaselineRequestPreferences<?> preferences,
                                                           String experimentAccession) {
        // Number of rows multiplied by all or a subset of columns
        int maximumNumberOfDocs = geneIds.size() *
                (preferences.getSelectedColumnIds().isEmpty() ?
                        assayGroups.size() :
                        preferences.getSelectedColumnIds().size());

        Pair<AnalyticsCollectionProxy.AnalyticsSchemaField, AnalyticsCollectionProxy.AnalyticsSchemaField> expressionLevelFieldNames = getExpressionLevelFieldNames(preferences.getUnit());

        SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        solrQueryBuilder.filterField(EXPERIMENT_ACCESSION, experimentAccession)
                        .filterFieldLowerRange(expressionLevelFieldNames.getLeft(), preferences.getCutoff())
                        .queryFieldOr(BIOENTITY_IDENTIFIER_SEARCH, geneIds)
                        .queryFieldOr(ASSAY_GROUP_ID, preferences.getSelectedColumnIds())
                        .setFieldList(
                                BIOENTITY_IDENTIFIER,
                                expressionLevelFieldNames.getLeft(),
                                expressionLevelFieldNames.getRight(),
                                ASSAY_GROUP_ID,
                                asAnalyticsSchemaField(BioentityPropertyName.SYMBOL))
                        .setRows(maximumNumberOfDocs);

        QueryResponse queryResponse = analyticsCollectionProxy.queryWithBuilder(solrQueryBuilder);

        Map<String, List<SolrDocument>> resultsMap =
                queryResponse.getResults().stream()
                        .collect(
                                groupingBy(solrDocument ->
                                        (String) solrDocument.getFieldValue(BIOENTITY_IDENTIFIER.name())));

        GeneProfilesList<BaselineProfile> baselineProfiles = new GeneProfilesList<>();

        for (String geneId: geneIds) {
            List<SolrDocument> thisGeneIdDocs = resultsMap.get(geneId);
            String geneName =
                    thisGeneIdDocs.get(0).containsKey("keyword_symbol") ?
                            (String) thisGeneIdDocs.get(0).getFirstValue("keyword_symbol") :
                            geneId;
            BaselineProfile bp = new BaselineProfile(geneId, geneName);

            thisGeneIdDocs.forEach(solrDoc -> {
                BaselineExpression baselineExpression =
                        solrDoc.containsKey(expressionLevelFieldNames.getRight()) ?
                                parseSolrFieldValue(solrDoc.getFieldValues(expressionLevelFieldNames.getRight().name())) :
                                parseSolrFieldValue(solrDoc.getFieldValue(expressionLevelFieldNames.getLeft().name()));

                String assayGroupId = (String) solrDoc.getFieldValue(ASSAY_GROUP_ID.name());
                AssayGroup thisAssayGroup = assayGroups.stream()
                        .filter(assayGroup -> assayGroup.getId().equals(assayGroupId))
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new);

                bp.add(thisAssayGroup, baselineExpression);
            });

            baselineProfiles.add(bp);
        }

        return baselineProfiles;
    }

    private BaselineExpression parseSolrFieldValue(Collection<Object> values) {
        List<Double> quartiles =
                values.stream()
                        .mapToDouble(obj -> (Double) obj)
                        .sorted()
                        .boxed()
                        .collect(toList());

        return new BaselineExpression(quartiles.get(0),
                quartiles.get(1),
                quartiles.get(2),
                quartiles.get(3),
                quartiles.get(4));
    }

    private BaselineExpression parseSolrFieldValue(Object value) {
        return new BaselineExpression((double) value);
    }

    // Expression level field to the left, quartiles field to the right
    private static Pair<AnalyticsCollectionProxy.AnalyticsSchemaField, AnalyticsCollectionProxy.AnalyticsSchemaField>
    getExpressionLevelFieldNames(ExpressionUnit.Absolute unit) {
        return unit == ExpressionUnit.Absolute.Rna.FPKM ?
                Pair.of(EXPRESSION_LEVEL_FPKM, EXPRESSION_LEVELS_FPKM) :
                Pair.of(EXPRESSION_LEVEL, EXPRESSION_LEVELS);
    }
}
