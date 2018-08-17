package uk.ac.ebi.atlas.experimentpage.baseline.profiles;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.AnalyticsSchemaField;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.ExperimentRequestPreferencesSolrQueryFactory;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.ASSAY_GROUP_ID;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.asAnalyticsSchemaField;

@Component
public class BaselineExperimentProfilesDao {
    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    public BaselineExperimentProfilesDao(SolrCloudCollectionProxyFactory collectionProxyFactory) {
        analyticsCollectionProxy = collectionProxyFactory.create(AnalyticsCollectionProxy.class);
    }

    public long fetchCount(String experimentAccession, BaselineRequestPreferences<?> preferences) {
        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQuery(experimentAccession, preferences);

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

        Pair<AnalyticsSchemaField, AnalyticsSchemaField> expressionLevelFieldNames =
                AnalyticsCollectionProxy.getExpressionLevelFieldNames(preferences.getUnit());

        SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        solrQueryBuilder.addFilterFieldByTerm(EXPERIMENT_ACCESSION, experimentAccession)
                .addFilterFieldByRangeMin(expressionLevelFieldNames.getLeft(), preferences.getCutoff())
                .addQueryFieldByTerm(BIOENTITY_IDENTIFIER_SEARCH, geneIds)
                .addQueryFieldByTerm(ASSAY_GROUP_ID, preferences.getSelectedColumnIds())
                .setFieldList(
                        BIOENTITY_IDENTIFIER,
                        expressionLevelFieldNames.getLeft(),
                        expressionLevelFieldNames.getRight(),
                        ASSAY_GROUP_ID,
                        asAnalyticsSchemaField(BioentityPropertyName.SYMBOL))
                .setRows(maximumNumberOfDocs);

        QueryResponse queryResponse = analyticsCollectionProxy.query(solrQueryBuilder);

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
                        solrDoc.containsKey(expressionLevelFieldNames.getRight().name()) ?
                                parseSolrFieldValue(
                                        solrDoc.getFieldValues(expressionLevelFieldNames.getRight().name())) :
                                parseSolrFieldValue(
                                        solrDoc.getFieldValue(expressionLevelFieldNames.getLeft().name()));

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
}
