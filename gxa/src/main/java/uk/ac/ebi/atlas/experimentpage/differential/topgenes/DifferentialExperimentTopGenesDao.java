package uk.ac.ebi.atlas.experimentpage.differential.topgenes;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator.SelectStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator.TopStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source.FacetStreamBuilder;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import static uk.ac.ebi.atlas.experimentpage.differential.topgenes.DifferentialExperimentTopGenesService.AVERAGE_EXPRESSION_KEY;
import static uk.ac.ebi.atlas.experimentpage.differential.topgenes.DifferentialExperimentTopGenesService.GENE_KEY;
import static uk.ac.ebi.atlas.experimentpage.differential.topgenes.DifferentialExperimentTopGenesService.SPECIFICITY_KEY;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.LOG_2_FOLD_CHANGE;

@Component
public class DifferentialExperimentTopGenesDao {
    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    public DifferentialExperimentTopGenesDao(SolrCloudCollectionProxyFactory collectionProxyFactory) {
        analyticsCollectionProxy = collectionProxyFactory.create(AnalyticsCollectionProxy.class);
    }

    public TupleStreamer createForDifferentialNonSpecific(String experimentAccession,
                                                          DifferentialRequestPreferences preferences) {

//        SolrQuery solrQuery =
//                ExperimentRequestPreferencesSolrQueryFactory.createSolrQueryForDifferentialExperiment(
//                        experimentAccession, preferences);


        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                        .withQuery(solrQuery)
                        .sortByCountsAscending()
                        .withAbsoluteAverageOf(LOG_2_FOLD_CHANGE);

        SelectStreamBuilder selectStreamBuilder =
                new SelectStreamBuilder(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + LOG_2_FOLD_CHANGE.name() + "))", AVERAGE_EXPRESSION_KEY));

        TopStreamBuilder topStreamBuilder =
                new TopStreamBuilder(selectStreamBuilder, preferences.getHeatmapMatrixSize(), AVERAGE_EXPRESSION_KEY);

        return TupleStreamer.of(topStreamBuilder.build());
    }

    public TupleStreamer createForDifferentialSpecific(String experimentAccession,
                                                       DifferentialRequestPreferences preferences) {

//        SolrQuery solrQuery =
//                ExperimentRequestPreferencesSolrQueryFactory.createSolrQueryForDifferentialExperiment(
//                        experimentAccession, preferences);


        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
//                        .withQuery(solrQuery)
                        .sortByCountsAscending()
                        .withAbsoluteAverageOf(LOG_2_FOLD_CHANGE);

        SelectStreamBuilder selectStreamBuilder =
                new SelectStreamBuilder(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + LOG_2_FOLD_CHANGE.name() + "))", AVERAGE_EXPRESSION_KEY,
                                        "count(*)", SPECIFICITY_KEY));

        return TupleStreamer.of(selectStreamBuilder.build());
    }
}
