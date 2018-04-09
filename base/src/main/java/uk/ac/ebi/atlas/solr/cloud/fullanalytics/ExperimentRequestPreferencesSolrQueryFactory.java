package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.ASSAY_GROUP_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL_FPKM;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder.createFieldQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder.createLowBoundRangeQuery;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder.createOrBooleanQuery;

public class ExperimentRequestPreferencesSolrQueryFactory {

    public static <U extends ExpressionUnit.Absolute> SolrQuery createSolrQueryForBaselineExperiment(
            String experimentAccession, BaselineRequestPreferences<U> reqPreferences) {

        AnalyticsCollectionProxy.AnalyticsSchemaField expressionLevelField =
                reqPreferences.getUnit() == ExpressionUnit.Absolute.Rna.FPKM ?
                        EXPRESSION_LEVEL_FPKM :
                        EXPRESSION_LEVEL;

        SolrQuery solrQuery = new SolrQuery();

        solrQuery.addFilterQuery(createFieldQuery(EXPERIMENT_ACCESSION, experimentAccession))
                 .addFilterQuery(createLowBoundRangeQuery(expressionLevelField, reqPreferences.getCutoff()));

        Stream<String> assayGroupIds =
                reqPreferences.getSelectedColumnIds().isEmpty() ?
                        Stream.empty() :
                        Stream.of(
                                "(" +
                                createOrBooleanQuery(ASSAY_GROUP_ID, reqPreferences.getSelectedColumnIds()) +
                                ")");

        Stream<String> geneQuery =
                reqPreferences.getGeneQuery().isEmpty() ?
                        Stream.empty() :
                        Stream.of(
                                "(" +
                                AnalyticsCollectionProxy.asAnalyticsGeneQuery(reqPreferences.getGeneQuery()).entrySet()
                                        .stream()
                                        .map(entry -> createOrBooleanQuery(entry.getKey(), entry.getValue()))
                                        .collect(joining(" OR ")) +
                                ")");

        solrQuery.setQuery(Stream.concat(assayGroupIds, geneQuery).collect(joining(" AND ")));

        return solrQuery;

    }

//    TODO
//    public static SolrQuery create(String experimentAccession, DifferentialRequestPreferences reqPreferences) {
//    }
//
//    public static SolrQuery create(String experimentAccession, MicroarrayRequestPreferences reqPreferences) {
//    }


}
