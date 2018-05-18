package uk.ac.ebi.atlas.solr.cloud.fullanalytics.tuplestreamers;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.ExperimentRequestPreferencesSolrQueryFactory;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source.FacetStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator.SelectStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator.TopStreamBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.EXPRESSION_LEVEL_FPKM;
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
                                                      BaselineRequestPreferences<?> preferences) {
        AnalyticsCollectionProxy.AnalyticsSchemaField expressionLevelField =
                preferences.getUnit() == ExpressionUnit.Absolute.Rna.FPKM ?
                        EXPRESSION_LEVEL_FPKM :
                        EXPRESSION_LEVEL;

        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQuery(
                        experimentAccession, preferences);

        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                        .withQuery(solrQuery)
                        .sortByAbsoluteAverageDescending(expressionLevelField);

        SelectStreamBuilder<AnalyticsCollectionProxy> selectStreamBuilder =
                new SelectStreamBuilder<>(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + expressionLevelField.name() + "))", AVERAGE_EXPRESSION_KEY));

        TopStreamBuilder<AnalyticsCollectionProxy> topStreamBuilder =
                new TopStreamBuilder<>(selectStreamBuilder, preferences.getHeatmapMatrixSize(), AVERAGE_EXPRESSION_KEY);

        return TupleStreamer.of(topStreamBuilder.build());
    }

    public TupleStreamer createForBaselineSpecific(String experimentAccession,
                                                   BaselineRequestPreferences<?> preferences) {
        AnalyticsCollectionProxy.AnalyticsSchemaField expressionLevelField =
                preferences.getUnit() == ExpressionUnit.Absolute.Rna.FPKM ?
                        EXPRESSION_LEVEL_FPKM :
                        EXPRESSION_LEVEL;

        SolrQuery solrQuery =
                ExperimentRequestPreferencesSolrQueryFactory.createSolrQuery(
                        experimentAccession, preferences);

        FacetStreamBuilder<AnalyticsCollectionProxy> facetStreamBuilder =
                new FacetStreamBuilder<>(analyticsCollectionProxy, BIOENTITY_IDENTIFIER)
                        .withQuery(solrQuery)
                        .sortByCountsAscending()
                        .withAbsoluteAverageOf(expressionLevelField);

        SelectStreamBuilder<AnalyticsCollectionProxy> selectStreamBuilder =
                new SelectStreamBuilder<>(facetStreamBuilder)
                        .addFieldMapping(
                                ImmutableMap.of(
                                        BIOENTITY_IDENTIFIER.name(), GENE_KEY,
                                        "avg(abs(" + expressionLevelField.name() + "))", AVERAGE_EXPRESSION_KEY,
                                        "count(*)", SPECIFICITY_KEY));

        return TupleStreamer.of(selectStreamBuilder.build());
    }
}
