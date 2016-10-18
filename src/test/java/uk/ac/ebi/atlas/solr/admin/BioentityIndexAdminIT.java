
package uk.ac.ebi.atlas.solr.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//this will shutdown spring context, otherwise things like singletons remain initialized between different test classes :(
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})//Embedded.xml"})
public class BioentityIndexAdminIT { /*implements Observer {

    @Inject
    private BioentityIndexAdmin subject;

    @Inject
    private BioentityIndexMonitor bioentityIndexMonitor;

    private CountDownLatch updateEventLatch; // used to react to event

    private static SolrServer embeddedSolrServer;

    @Inject
    public void setEmbeddedSolrServer(EmbeddedSolrServer embeddedSolrServer) {
        BioentityIndexAdminIT.embeddedSolrServer = embeddedSolrServer;
    }

    @Before
    public void init() {
        updateEventLatch = new CountDownLatch(1);
        bioentityIndexMonitor.addObserver(this);
    }

    @After
    public void cleanupData() throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
        embeddedSolrServer.commit();
    }

    @AfterClass
    public static void shutDown() {
        embeddedSolrServer.shutdown();
    }*/

    @Test
    public void removeMe() {

    }

    // TODO: enable test again @Test(timeout = 3000) //expect the indexing to happen in less than 2 seconds
    /*public void rebuildIndexShouldSucceed() throws Exception {
        subject.rebuildIndex();

        updateEventLatch.await();

        SolrParams solrQuery = new SolrQuery("*:*").setRows(1000);
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(640));

        BioentityIndexMonitor.Status status = bioentityIndexMonitor.getStatus();
        assertThat(status, is(BioentityIndexMonitor.Status.COMPLETED));
    }

    @Override
    public void update(Observable observable, Object argument) {
        assertTrue(observable == bioentityIndexMonitor);

        updateEventLatch.countDown();
    } */
}
