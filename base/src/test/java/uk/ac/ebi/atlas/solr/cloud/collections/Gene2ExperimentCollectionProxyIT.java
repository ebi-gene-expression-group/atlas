package uk.ac.ebi.atlas.solr.cloud.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
class Gene2ExperimentCollectionProxyIT {
    private Gene2ExperimentCollectionProxy subject;

    @Test
    void canConnectAndQuery() {
        SolrQueryBuilder<Gene2ExperimentCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<>();


    }
}