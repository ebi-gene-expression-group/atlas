package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BioentityIdentifierSearchServiceIT { }

//    private BioentityIdentifierSearchService subject;
//
//    @Before
//    @Inject
//    public void setUp(EmbeddedSolrClientProxyFactory embeddedSolrClientFactory) throws IOException {
//        SolrClient solrClient = embeddedSolrClientFactory.createSolrClient("analytics");
//        subject = new BioentityIdentifierSearchService(solrClient);
//    }
//
//    @Test
//    public emptyQueryReturnsEmpty
//}