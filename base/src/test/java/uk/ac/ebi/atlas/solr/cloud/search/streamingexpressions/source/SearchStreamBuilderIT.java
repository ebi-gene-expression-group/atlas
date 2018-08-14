package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source;

import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import javax.inject.Inject;

import java.io.UncheckedIOException;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.PROPERTY_NAME;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.PROPERTY_VALUE;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.SPECIES;

@DirtiesContext(classMode = BEFORE_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class SearchStreamBuilderIT {
    @Inject
    private SolrCloudCollectionProxyFactory collectionProxyFactory;

    private BioentitiesCollectionProxy bioentitiesCollectionProxy;

    @BeforeEach
    void setUp() {
        bioentitiesCollectionProxy = collectionProxyFactory.create(BioentitiesCollectionProxy.class);
    }

    @Test
    void testMinimalQuery() {
        SolrQueryBuilder<BioentitiesCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        solrQueryBuilder
                .setFieldList(BIOENTITY_IDENTIFIER, SPECIES, PROPERTY_NAME, PROPERTY_VALUE)
                .sortBy(BIOENTITY_IDENTIFIER, ORDER.asc);

        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(
                             new SearchStreamBuilder<>(bioentitiesCollectionProxy, solrQueryBuilder).build())) {

            assertThat(tupleStreamer.get().collect(toList())).isNotEmpty();
        }
    }

    @Test
    void testQuery() {
        SolrQueryBuilder<BioentitiesCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        solrQueryBuilder
                .addQueryFieldByTerm(SPECIES, "Mus_musculus")
                .setFieldList(BIOENTITY_IDENTIFIER, SPECIES, PROPERTY_NAME, PROPERTY_VALUE)
                .sortBy(BIOENTITY_IDENTIFIER, ORDER.asc);

        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(
                             new SearchStreamBuilder<>(bioentitiesCollectionProxy, solrQueryBuilder).build())) {

            assertThat(tupleStreamer.get().collect(toList()))
                    .allSatisfy(
                            tuple -> assertThat(tuple.getString(BIOENTITY_IDENTIFIER.name())).startsWith("ENSMUSG"));

        }
    }

    @Test
    void noResults() {
        SolrQueryBuilder<BioentitiesCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        solrQueryBuilder
                .addQueryFieldByTerm(PROPERTY_VALUE, "Foobar")
                .setFieldList(BIOENTITY_IDENTIFIER, SPECIES, PROPERTY_NAME, PROPERTY_VALUE)
                .sortBy(BIOENTITY_IDENTIFIER, ORDER.asc);

        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(
                             new SearchStreamBuilder<>(bioentitiesCollectionProxy, solrQueryBuilder).build())) {

            assertThat(tupleStreamer.get().collect(toList()))
                    .isEmpty();

        }
    }

    @Test
    void requiresSortFieldAndToBePresentInFieldList() {
        SolrQueryBuilder<BioentitiesCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> new SearchStreamBuilder<>(bioentitiesCollectionProxy, solrQueryBuilder).build());

        solrQueryBuilder.sortBy(BIOENTITY_IDENTIFIER, ORDER.asc);
        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> new SearchStreamBuilder<>(bioentitiesCollectionProxy, solrQueryBuilder).build());

        solrQueryBuilder.setFieldList(BIOENTITY_IDENTIFIER);
        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(
                             new SearchStreamBuilder<>(bioentitiesCollectionProxy, solrQueryBuilder).build())) {

            assertThat(tupleStreamer.get().collect(toList()))
                    .isNotEmpty();

        }

    }
}