package uk.ac.ebi.atlas.search.geneids;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.collections.Gene2ExperimentCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator.IntersectStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source.SearchStreamBuilder;

import java.util.Optional;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.PROPERTY_NAME;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.PROPERTY_VALUE;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.SPECIES;
import static uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder.SOLR_MAX_ROWS;

// Search gene IDs in scxa-gene2experiment by gene property name/value and species (bioentities collection)
@Component
public class GeneIdSearchDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneIdSearchDao.class);

    private final BioentitiesCollectionProxy bioentitiesCollectionProxy;
    private final Gene2ExperimentCollectionProxy gene2ExperimentCollectionProxy;

    public GeneIdSearchDao(SolrCloudCollectionProxyFactory collectionProxyFactory) {
        bioentitiesCollectionProxy = collectionProxyFactory.create(BioentitiesCollectionProxy.class);
        gene2ExperimentCollectionProxy = collectionProxyFactory.create(Gene2ExperimentCollectionProxy.class);
    }

    // This is one of the edge cases where an empty optional is semantically different than an empty collection. The
    // former signals that there were no matching gene IDs in the “Atlas knowledge base” (aka bioentities collection),
    // whereas the latter means that a known gene ID couldn’t be found in any SC experiment. Another alternative could
    // be to return a Pair<String, Set> where the left would contain a message if the right is empty or null (like
    // monads similar to Try, Maybe or Either).
    public Optional<ImmutableSet<String>> searchGeneIds(String propertyValue, String propertyName, String species) {
        SolrQueryBuilder<BioentitiesCollectionProxy> bioentitiesQueryBuilder =
                new SolrQueryBuilder<BioentitiesCollectionProxy>()
                        .addFilterFieldByTerm(SPECIES, species)
                        .addQueryFieldByTerm(PROPERTY_VALUE, propertyValue)
                        .addQueryFieldByTerm(PROPERTY_NAME, propertyName)
                        .setFieldList(BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER)
                        .sortBy(BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER, SolrQuery.ORDER.asc);

        return searchInTwoSteps(bioentitiesQueryBuilder);
    }

    public Optional<ImmutableSet<String>> searchGeneIds(String propertyValue, String propertyName) {
        SolrQueryBuilder<BioentitiesCollectionProxy> bioentitiesQueryBuilder =
                new SolrQueryBuilder<BioentitiesCollectionProxy>()
                        .addQueryFieldByTerm(PROPERTY_VALUE, propertyValue)
                        .addQueryFieldByTerm(PROPERTY_NAME, propertyName)
                        .setFieldList(BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER)
                        .sortBy(BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER, SolrQuery.ORDER.asc);

        return searchInTwoSteps(bioentitiesQueryBuilder);
    }

    private Optional<ImmutableSet<String>> searchInTwoSteps(
            SolrQueryBuilder<BioentitiesCollectionProxy> bioentitiesQueryBuilder) {
        bioentitiesQueryBuilder.setRows(1);

        SearchStreamBuilder<BioentitiesCollectionProxy> bioentitiesSearchBuilder =
                new SearchStreamBuilder<>(bioentitiesCollectionProxy, bioentitiesQueryBuilder);

        LOGGER.debug("Searching bioentities collection: [{}]", bioentitiesQueryBuilder.build().getQuery());
        try (TupleStreamer tupleStreamer = TupleStreamer.of(bioentitiesSearchBuilder.build())) {
            return tupleStreamer.get()
                    .findFirst()
                    .map((x) ->
                            searchWithinGeneIdsExpressedInExperiments(
                                    bioentitiesQueryBuilder.setRows(SolrQueryBuilder.DEFAULT_ROWS)));
        }

    }

    // A set since we don’t care about the order, we might want a list if results are ranked somehow
    private ImmutableSet<String> searchWithinGeneIdsExpressedInExperiments(
            SolrQueryBuilder<BioentitiesCollectionProxy> bioentitiesQueryBuilder) {

        SolrQueryBuilder<Gene2ExperimentCollectionProxy> g2eQueryBuilder =
                new SolrQueryBuilder<Gene2ExperimentCollectionProxy>()
                        .setFieldList(Gene2ExperimentCollectionProxy.BIOENTITY_IDENTIFIER)
                        .sortBy(Gene2ExperimentCollectionProxy.BIOENTITY_IDENTIFIER, SolrQuery.ORDER.asc)
                        .setRows(SOLR_MAX_ROWS);

        SearchStreamBuilder<BioentitiesCollectionProxy> bioentitiesSearchBuilder =
                new SearchStreamBuilder<>(bioentitiesCollectionProxy, bioentitiesQueryBuilder);
        SearchStreamBuilder<Gene2ExperimentCollectionProxy> g2eSearchBuilder =
                new SearchStreamBuilder<>(gene2ExperimentCollectionProxy, g2eQueryBuilder);

        String sortField = BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER.name();
        IntersectStreamBuilder intersectBuilder =
                new IntersectStreamBuilder(bioentitiesSearchBuilder, g2eSearchBuilder, sortField);

        // There’s no easy way to get a string representation of a streaming expression unless we set up a
        // StreamFactory, add all functions, equalitors, comparators, collection, etc. :(
        LOGGER.debug("Matching documents in bioentities found, retrieving intersection with gene2experiment");
        try (TupleStreamer tupleStreamer = TupleStreamer.of(intersectBuilder.build())) {
            return tupleStreamer.get()
                    .map(tuple -> tuple.getString(sortField))
                    .collect(toImmutableSet());
        }
    }
}
