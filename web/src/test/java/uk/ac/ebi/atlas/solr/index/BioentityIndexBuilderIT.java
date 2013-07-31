package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class BioentityIndexBuilderIT {

    @Inject
    private BioentityIndexBuilder subject;

    @Inject
    private SolrServer embeddedSolrServer;

    @Before
    public void init() {
    }

    @After
    public void cleanupData() throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
    }

    @Test
    public void testIndex() throws Exception {
        subject.index();

        SolrParams solrQuery = new SolrQuery("*:*").setRows(1000);
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(355));
    }
}
