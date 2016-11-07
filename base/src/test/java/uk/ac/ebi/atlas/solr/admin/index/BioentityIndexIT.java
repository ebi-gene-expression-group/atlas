
package uk.ac.ebi.atlas.solr.admin.index;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})//Embedded.xml"})
public class BioentityIndexIT {

    /*@Value("#{configuration['bioentity.properties']}")
    private String bioentityPropertyDirectory;

    @Inject
    private BioentityIndex subject;

    private static SolrServer embeddedSolrServer;

    @Inject
    public void setEmbeddedSolrServer(EmbeddedSolrServer embeddedSolrServer) {
        BioentityIndexIT.embeddedSolrServer = embeddedSolrServer;
    }

    @Before
    public void setup() throws ParserConfigurationException, SAXException, IOException {
    }

    @After
    public void cleanupData() throws IOException, SolrServerException {
        subject.deleteAll();
    }

    @AfterClass
    public static void shutdown() {
        embeddedSolrServer.shutdown();
    }*/

    @Test
    public void removeMe() {

    }

    // TODO: enable test again
    /*public void indexFileShouldSucceed() throws IOException, SolrServerException {
        subject.indexFile(Paths.get(bioentityPropertyDirectory, "anopheles_gambiae.A-AFFY-102.tsv"));

        SolrParams solrQuery = new SolrQuery("*:*");
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(10));

    }

    // TODO: enable test again
    public void addBioentityPropertiesShouldSucceed() throws IOException, SolrServerException, InterruptedException {
        subject.indexFile(Paths.get(bioentityPropertyDirectory, "anopheles_gambiae.ensgene.tsv"));

        SolrParams solrQuery = new SolrQuery("*:*").setRows(10000);
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(315));

    }*/
}
