package uk.ac.ebi.atlas.experimentpage.baseline;

import org.apache.commons.lang3.tuple.Pair;
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
                        experimentAccession,
                        preferences.getGeneQuery(),
                        preferences.getCutoff(),
                        preferences.getHeatmapMatrixSize(),
                        preferences.getUnit(),
                        preferences.getSelectedColumnIds().toArray(new String[0])) :
                bioentityIdentifierSearchService.searchMostExpressedGenesInBaselineExperiment(
                        experimentAccession,
                        preferences.getGeneQuery(),
                        preferences.getCutoff(),
                        preferences.getHeatmapMatrixSize(),
                        preferences.getUnit(),
                        preferences.getSelectedColumnIds().toArray(new String[0]));

        return fetchProfiles(topGeneIds, assayGroups, preferences, experimentAccession);
    }

    public long fetchCount(String experimentAccession, BaselineRequestPreferences<?> preferences) {
        Pair<String, String> expressionLevelFieldNames = getExpressionLevelFieldNames(preferences.getUnit());
        SolrQueryBuilder solrQueryBuilder =
                new SolrQueryBuilder()
                        .addFilterTermsClause(EXPERIMENT_ACCESSION.name(), experimentAccession)
                        .addFilterLowerRangeClause(
                                expressionLevelFieldNames.getLeft(),
                                preferences.getCutoff());

        if (!preferences.getSelectedColumnIds().isEmpty()) {
            solrQueryBuilder.addQueryTermsClause(
                    ASSAY_GROUP_ID.name(),
                    preferences.getSelectedColumnIds().toArray(new String[preferences.getSelectedColumnIds().size()])
            );
        }

        return analyticsCollectionProxy.fieldStats(BIOENTITY_IDENTIFIER, solrQueryBuilder.build()).getCountDistinct();
    }

    public GeneProfilesList<BaselineProfile> fetchProfiles(List<String> geneIds,
                                                           List<AssayGroup> assayGroups,
                                                           BaselineRequestPreferences<?> preferences,
                                                           String experimentAccession) {
        // rows multiplied by all or a subset of columns
        int maximumNumberOfDocs = geneIds.size() *
                (preferences.getSelectedColumnIds().isEmpty() ?
                        assayGroups.size() :
                        preferences.getSelectedColumnIds().size());

        Pair<String, String> expressionLevelFieldNames = getExpressionLevelFieldNames(preferences.getUnit());

        SolrQueryBuilder solrQueryBuilder =
                new SolrQueryBuilder()
                        .addFilterTermsClause(EXPERIMENT_ACCESSION.name(), experimentAccession)
                        .addFilterLowerRangeClause(
                                expressionLevelFieldNames.getLeft(),
                                preferences.getCutoff())
                        // WARNING! This is a hack! We want to use terms query against a lowercase field, so that’s
                        // what we do, since there’s no equivalent field query parser:
                        // https://lucene.apache.org/solr/guide/7_1/other-parsers.html#term-query-parser
                        // https://lucene.apache.org/solr/guide/7_1/other-parsers.html#field-query-parser
                        .addQueryTermsClause(
                                BIOENTITY_IDENTIFIER_SEARCH.name(),
                                geneIds.stream().map(String::toLowerCase).toArray(String[]::new))
                        .setFieldList(
                                BIOENTITY_IDENTIFIER.name(),
                                expressionLevelFieldNames.getLeft(),
                                expressionLevelFieldNames.getRight(),
                                ASSAY_GROUP_ID.name(),
                                asAnalyticsSchemaField(BioentityPropertyName.SYMBOL).name())
                        .setRows(maximumNumberOfDocs);

        if (!preferences.getSelectedColumnIds().isEmpty()) {
            solrQueryBuilder.addQueryTermsClause(
                    ASSAY_GROUP_ID.name(),
                    preferences.getSelectedColumnIds().toArray(new String[preferences.getSelectedColumnIds().size()])
            );
        }

        QueryResponse queryResponse = analyticsCollectionProxy.query(solrQueryBuilder.build());

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
                                parseSolrFieldValue(solrDoc.getFieldValues(expressionLevelFieldNames.getRight())) :
                                parseSolrFieldValue(solrDoc.getFieldValue(expressionLevelFieldNames.getLeft()));

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
    private static Pair<String, String> getExpressionLevelFieldNames(ExpressionUnit.Absolute unit) {
        return unit == ExpressionUnit.Absolute.Rna.FPKM ?
                Pair.of(EXPRESSION_LEVEL_FPKM.name(), EXPRESSION_LEVELS_FPKM.name()) :
                Pair.of(EXPRESSION_LEVEL.name(), EXPRESSION_LEVELS.name());
    }
}
