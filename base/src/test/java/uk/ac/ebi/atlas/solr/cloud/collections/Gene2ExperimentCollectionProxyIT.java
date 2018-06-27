package uk.ac.ebi.atlas.solr.cloud.collections;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source.SearchStreamBuilder;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.solr.cloud.collections.Gene2ExperimentCollectionProxy.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.cloud.collections.Gene2ExperimentCollectionProxy.EXPERIMENT_ACCESSION;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
class Gene2ExperimentCollectionProxyIT {
    private Gene2ExperimentCollectionProxy subject;

    @Inject
    private SolrCloudCollectionProxyFactory collectionProxyFactory;

    @BeforeEach
    void setUp() {
        subject = collectionProxyFactory.create(Gene2ExperimentCollectionProxy.class);
    }

    @Test
    void canConnectAndQuery() {
        SolrQueryBuilder<Gene2ExperimentCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
        solrQueryBuilder
                .sortBy(EXPERIMENT_ACCESSION, SolrQuery.ORDER.asc)
                .setFieldList(EXPERIMENT_ACCESSION, BIOENTITY_IDENTIFIER);

        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(
                             new SearchStreamBuilder<>(subject, solrQueryBuilder).build())) {
            assertThat(tupleStreamer.get())
                    .isNotEmpty()
                    .allMatch(tuple -> isNotBlank(tuple.getString(BIOENTITY_IDENTIFIER.name())))
                    .allMatch(tuple -> isNotBlank(tuple.getString(EXPERIMENT_ACCESSION.name())));
        }
    }
}