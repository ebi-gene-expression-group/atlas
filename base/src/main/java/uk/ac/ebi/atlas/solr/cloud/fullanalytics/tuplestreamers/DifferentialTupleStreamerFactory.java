package uk.ac.ebi.atlas.solr.cloud.fullanalytics.tuplestreamers;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.FacetStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.SelectStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TopStreamBuilder;

import javax.inject.Inject;
import javax.inject.Named;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.ADJUSTED_P_VALUE;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.CONTRAST_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
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
                                                          double log2FoldChangeCutoff,
                                                          double adjustedPValueCutoff,
                                                          int limit,
                                                          String... contrastIds) {
        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                        .addFilterTermsClause(EXPERIMENT_ACCESSION, experimentAccession)
                        .addFilterUpperRangeClause(ADJUSTED_P_VALUE, adjustedPValueCutoff)
                        .addFilterDoubleRangeClause(LOG_2_FOLD_CHANGE, -log2FoldChangeCutoff, log2FoldChangeCutoff)
                        .sortByAbsoluteAverageDescending(LOG_2_FOLD_CHANGE);

        if (contrastIds.length > 0) {
            facetStreamBuilder.addQueryTermsClause(CONTRAST_ID, contrastIds);
        }

        SelectStreamBuilder<AnalyticsCollectionProxy> selectStreamBuilder =
                new SelectStreamBuilder<>(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + LOG_2_FOLD_CHANGE.name() + "))", AVERAGE_EXPRESSION_KEY));

        TopStreamBuilder<AnalyticsCollectionProxy> topStreamBuilder =
                new TopStreamBuilder<>(selectStreamBuilder, limit, AVERAGE_EXPRESSION_KEY);

        return TupleStreamer.of(topStreamBuilder.build());
    }

    public TupleStreamer createForDifferentialSpecific(String experimentAccession,
                                                       double log2FoldChangeCutoff,
                                                       double adjustedPValueCutoff,
                                                       String... contrastIds) {
        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                        .addFilterTermsClause(EXPERIMENT_ACCESSION, experimentAccession)
                        .addFilterUpperRangeClause(ADJUSTED_P_VALUE, adjustedPValueCutoff)
                        .addFilterDoubleRangeClause(LOG_2_FOLD_CHANGE, -log2FoldChangeCutoff, log2FoldChangeCutoff)
                        .sortByCountsAscending()
                        .withAbsoluteAverageOf(LOG_2_FOLD_CHANGE);

        if (contrastIds.length > 0) {
            facetStreamBuilder.addQueryTermsClause(CONTRAST_ID, contrastIds);
        }

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
