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

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.ASSAY_GROUP_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.BioentityIdentifierSearchService.AVERAGE_EXPRESSION_KEY;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.BioentityIdentifierSearchService.GENE_KEY;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.BioentityIdentifierSearchService.SPECIFICITY_KEY;

@Named
public class BaselineTupleStreamerFactory {
    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    @Inject
    public BaselineTupleStreamerFactory(SolrCloudCollectionProxyFactory collectionProxyFactory) {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    public TupleStreamer createForBaselineNonSpecific(String experimentAccession,
                                                      double expressionLevelCutoff,
                                                      int limit,
                                                      String... assayGroupIds) {
        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                        .addFilterTermsClause(EXPERIMENT_ACCESSION, experimentAccession)
                        .addFilterLowerRangeClause(EXPRESSION_LEVEL, expressionLevelCutoff)
                        .sortByAbsoluteAverageDescending(EXPRESSION_LEVEL);

        if (assayGroupIds.length > 0) {
            facetStreamBuilder.addQueryTermsClause(ASSAY_GROUP_ID, assayGroupIds);
        }

        SelectStreamBuilder<AnalyticsCollectionProxy> selectStreamBuilder =
                new SelectStreamBuilder<>(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + EXPRESSION_LEVEL.name() + "))", AVERAGE_EXPRESSION_KEY));

        TopStreamBuilder<AnalyticsCollectionProxy> topStreamBuilder =
                new TopStreamBuilder<>(selectStreamBuilder, limit, AVERAGE_EXPRESSION_KEY);

        return TupleStreamer.of(topStreamBuilder.build());
    }

    public TupleStreamer createForBaselineSpecific(String experimentAccession,
                                                   double expressionLevelCutoff,
                                                   String... assayGroupIds) {
        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                        .addFilterTermsClause(EXPERIMENT_ACCESSION, experimentAccession)
                        .addFilterLowerRangeClause(EXPRESSION_LEVEL, expressionLevelCutoff)
                        .sortByCountsAscending()
                        .withAbsoluteAverageOf(EXPRESSION_LEVEL);

        if (assayGroupIds.length > 0) {
            facetStreamBuilder.addQueryTermsClause(ASSAY_GROUP_ID, assayGroupIds);
        }

        SelectStreamBuilder<AnalyticsCollectionProxy> selectStreamBuilder =
                new SelectStreamBuilder<>(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + EXPRESSION_LEVEL.name() + "))", AVERAGE_EXPRESSION_KEY,
                                        "count(*)", SPECIFICITY_KEY));

        return TupleStreamer.of(selectStreamBuilder.build());
    }
}
