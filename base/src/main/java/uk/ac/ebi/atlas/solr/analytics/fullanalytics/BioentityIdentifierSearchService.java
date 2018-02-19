package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.io.Tuple;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.search.FacetStreamBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.google.common.collect.Ordering.natural;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Named
public class BioentityIdentifierSearchService {

    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    @Inject
    public BioentityIdentifierSearchService(SolrCloudCollectionProxyFactory collectionProxyFactory) {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    public List<String> mostSpecificGenes(String experimentAccession, int maxSize) {
        // TODO Compare performance, the extra comparison could be noticeable
        // return mostSpecificGenes(experimentAccession, Double.MAX_VALUE, int maxSize)

        Multimap<Long, Tuple> mostExpressedGenesOnAverageGroupedBySpecificity =
                TreeMultimap.create(
                        natural(),
                        comparing(t -> t.getDouble("avg(" + analyticsCollectionProxy.EXPRESSION_LEVEL.name() + ")")));

        FacetStreamBuilder.create(analyticsCollectionProxy, analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                .withAverageOver(analyticsCollectionProxy.EXPRESSION_LEVEL)
                .sortByCountsAscending()
                .build()
                .get()
                .collect(groupingBy(tuple -> tuple.getLong("count(*)")))
                .forEach(mostExpressedGenesOnAverageGroupedBySpecificity::putAll);

        return mostExpressedGenesOnAverageGroupedBySpecificity.values().stream()
                .map(tuple -> tuple.getString(analyticsCollectionProxy.BIOENTITY_IDENTIFIER.name()))
                .limit(maxSize)
                .collect(toList());
    }

    public List<String> mostSpecificGenes(String experimentAccession, double expressionLevelCutoff, int maxSize) {
        Multimap<Long, Tuple> mostExpressedGenesOnAverageGroupedBySpecificity =
                TreeMultimap.create(
                        natural(),
                        comparing(t -> t.getDouble("avg(" + analyticsCollectionProxy.EXPRESSION_LEVEL.name() + ")")));

        FacetStreamBuilder.create(analyticsCollectionProxy, analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, expressionLevelCutoff)
                .withAverageOver(analyticsCollectionProxy.EXPRESSION_LEVEL)
                .sortByCountsAscending()
                .build()
                .get()
                .collect(groupingBy(tuple -> tuple.getLong("count(*)")))
                .forEach(mostExpressedGenesOnAverageGroupedBySpecificity::putAll);

        return mostExpressedGenesOnAverageGroupedBySpecificity.values().stream()
                .map(tuple -> tuple.getString(analyticsCollectionProxy.BIOENTITY_IDENTIFIER.name()))
                .limit(maxSize)
                .collect(toList());
    }

    public List<String> mostExpressedGenes(String experimentAccession, int maxSize) {
        // TODO Compare performance, the extra comparison could be noticeable
        // return mostExpressedGenes(experimentAccession, Double.MAX_VALUE, int maxSize)

        return FacetStreamBuilder.create(analyticsCollectionProxy, analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                .sortByAverageDescending(analyticsCollectionProxy.EXPRESSION_LEVEL)
                .build()
                .get()
                .map(tuple -> tuple.getString(analyticsCollectionProxy.BIOENTITY_IDENTIFIER.name()))
                .limit(maxSize)
                .collect(toList());
    }

    public List<String> mostExpressedGenes(String experimentAccession, double expressionLevelCutoff, int maxSize) {
        return FacetStreamBuilder.create(analyticsCollectionProxy, analyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .addFilterTermsClause(analyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                .addFilterRangeClause(analyticsCollectionProxy.EXPRESSION_LEVEL, expressionLevelCutoff)
                .sortByAverageDescending(analyticsCollectionProxy.EXPRESSION_LEVEL)
                .build()
                .get()
                .map(tuple -> tuple.getString(analyticsCollectionProxy.BIOENTITY_IDENTIFIER.name()))
                .limit(maxSize)
                .collect(toList());

    }

    // TODO Query with SemanticQuery and assay group IDs

}
