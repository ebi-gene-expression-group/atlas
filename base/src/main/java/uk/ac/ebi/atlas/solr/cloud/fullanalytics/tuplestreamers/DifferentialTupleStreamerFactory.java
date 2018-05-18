package uk.ac.ebi.atlas.solr.cloud.fullanalytics.tuplestreamers;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source.FacetStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator.SelectStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator.TopStreamBuilder;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.LOG_2_FOLD_CHANGE;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.BioentityIdentifierSearchService.AVERAGE_EXPRESSION_KEY;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.BioentityIdentifierSearchService.GENE_KEY;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.BioentityIdentifierSearchService.SPECIFICITY_KEY;

@Named
public class DifferentialTupleStreamerFactory {
    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    @Inject
    public DifferentialTupleStreamerFactory(SolrCloudCollectionProxyFactory collectionProxyFactory) {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
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

        SelectStreamBuilder<AnalyticsCollectionProxy> selectStreamBuilder =
                new SelectStreamBuilder<>(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + LOG_2_FOLD_CHANGE.name() + "))", AVERAGE_EXPRESSION_KEY));

        TopStreamBuilder<AnalyticsCollectionProxy> topStreamBuilder =
                new TopStreamBuilder<>(selectStreamBuilder, preferences.getHeatmapMatrixSize(), AVERAGE_EXPRESSION_KEY);

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

        SelectStreamBuilder<AnalyticsCollectionProxy> selectStreamBuilder =
                new SelectStreamBuilder<>(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + LOG_2_FOLD_CHANGE.name() + "))", AVERAGE_EXPRESSION_KEY,
                                        "count(*)", SPECIFICITY_KEY));

        return TupleStreamer.of(selectStreamBuilder.build());
    }
}
