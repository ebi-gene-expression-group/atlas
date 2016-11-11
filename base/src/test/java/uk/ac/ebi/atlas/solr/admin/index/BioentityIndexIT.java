package uk.ac.ebi.atlas.solr.admin.index;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.EmbeddedSolrServerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class BioentityIndexIT {

    @Value("#{configuration['bioentity.properties']}")
    private String bioentityPropertyDirectory;

    @Inject
    private EmbeddedSolrServer embeddedSolrServer;

    @Inject
    private BioentityIndex subject;

    @Before
    public void setUp() {
        subject.setSolrClient(embeddedSolrServer);
    }

    @After
    public void tearDown() {
        subject.deleteAll();
    }

    @Test
    public void indexFileShouldSucceed() throws IOException, SolrServerException {
        subject.indexFile(Paths.get(bioentityPropertyDirectory, "ensembl/anopheles_gambiae.A-AFFY-102.tsv"), false);
        embeddedSolrServer.commit();

        SolrParams solrQuery = new SolrQuery("*:*");
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(10));
    }

    @Test
    public void addBioentityPropertiesShouldSucceed() throws IOException, SolrServerException {
        subject.indexFile(Paths.get(bioentityPropertyDirectory, "ensembl/anopheles_gambiae.ensgene.tsv"), false);
        embeddedSolrServer.commit();

        SolrParams solrQuery = new SolrQuery("*:*").setRows(10000);
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(315));
    }

}
