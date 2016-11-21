package uk.ac.ebi.atlas.solr.admin.index;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.solr.BioentityProperty;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
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
    public void indexFile() throws IOException, SolrServerException {
        subject.indexFile(Paths.get(bioentityPropertyDirectory, "ensembl/anopheles_gambiae.A-AFFY-102.tsv"), false);
        embeddedSolrServer.commit();

        SolrParams solrQuery = new SolrQuery("*:*");
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(10));
    }

    @Test
    public void addBioentityProperties() throws IOException, SolrServerException {
        subject.indexFile(Paths.get(bioentityPropertyDirectory, "ensembl/anopheles_gambiae.ensgene.tsv"), false);
        embeddedSolrServer.commit();

        SolrParams solrQuery = new SolrQuery("*:*").setRows(10000);
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = queryResponse.getBeans(BioentityProperty.class);
        assertThat(bioentityProperties, hasSize(315));
    }

}
