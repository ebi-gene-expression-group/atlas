package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.ASSAY_GROUP_ID;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.EXPRESSION_LEVEL;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.EXPRESSION_LEVEL_FPKM;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryUtils.createOrBooleanQuery;

public class ExperimentRequestPreferencesSolrQueryFactory {
    // The type of reqPreferences will determine the type of experiment. If you think this is confusing change the
    // method names to createSolrQueryForBaselineExperiment or something like that.
    public static SolrQuery createSolrQuery(String experimentAccession, BaselineRequestPreferences<?> reqPreferences) {

        AnalyticsCollectionProxy.AnalyticsSchemaField expressionLevelField =
                reqPreferences.getUnit() == ExpressionUnit.Absolute.Rna.FPKM ?
                        EXPRESSION_LEVEL_FPKM :
                        EXPRESSION_LEVEL;

        SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();

        // A single term OR boolean query will result in a single field query: foo:"bar"
        solrQueryBuilder
                .addFilterFieldByTerm(EXPERIMENT_ACCESSION, experimentAccession)
                .addFilterFieldByRangeMin(expressionLevelField, reqPreferences.getCutoff());

        Optional<String> assayGroupIds =
                reqPreferences.getSelectedColumnIds().isEmpty() ?
                        Optional.empty() :
                        Optional.of(
                                "(" +
                                createOrBooleanQuery(ASSAY_GROUP_ID, reqPreferences.getSelectedColumnIds()) +
                                ")");

        Optional<String> geneQuery =
                reqPreferences.getGeneQuery().isEmpty() ?
                        Optional.empty() :
                        Optional.of(
                                "(" +
                                AnalyticsCollectionProxy.asAnalyticsGeneQuery(reqPreferences.getGeneQuery()).entrySet()
                                        .stream()
                                        .map(entry -> createOrBooleanQuery(entry.getKey(), entry.getValue()))
                                        .collect(joining(" OR ")) +
                                ")");

        String query = Stream.concat(mapToStream(assayGroupIds), mapToStream(geneQuery)).collect(joining(" AND "));

        return solrQueryBuilder.build().setQuery(query.isEmpty() ? "*:*" : query);
    }

    private static <T> Stream<T> mapToStream(Optional<T> optional) {
        return optional.map(Stream::of).orElse(Stream.empty());
    }

//    TODO
//    public static SolrQuery create(String experimentAccession, DifferentialRequestPreferences reqPreferences) {
//    }
//
//    public static SolrQuery create(String experimentAccession, MicroarrayRequestPreferences reqPreferences) {
//    }
}
