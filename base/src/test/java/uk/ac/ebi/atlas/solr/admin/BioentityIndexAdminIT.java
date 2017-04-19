package uk.ac.ebi.atlas.solr.admin;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.admin.index.BioentityIndex;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class BioentityIndexAdminIT implements Observer {

    @Inject
    private EmbeddedSolrServer embeddedSolrServer;

    @Inject
    private BioentityIndex bioentityIndex;

    @Inject
    private BioentityIndexAdmin subject;

    @Inject
    private BioentityIndexMonitor bioentityIndexMonitor;

    private CountDownLatch updateEventLatch; // used to react to event

    @Before
    public void setUp() throws IOException {
        bioentityIndex.setSolrClient(embeddedSolrServer);
        subject.setBioentityIndex(bioentityIndex);

        updateEventLatch = new CountDownLatch(1);
        bioentityIndexMonitor.addObserver(this);
    }

    @After
    public void tearDown() throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
        embeddedSolrServer.commit();
    }

    @Test(timeout = 3000)
    public void rebuildIndex() throws Exception {
        subject.rebuildIndex();

        updateEventLatch.await();

        SolrParams solrQuery = new SolrQuery("*:*").setRows(1000);
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(325));

        BioentityIndexMonitor.Status status = bioentityIndexMonitor.getStatus();
        assertThat(status, is(BioentityIndexMonitor.Status.COMPLETED));
    }

    @Override
    public void update(Observable observable, Object argument) {
        assertTrue(observable == bioentityIndexMonitor);
        updateEventLatch.countDown();
    }

}
