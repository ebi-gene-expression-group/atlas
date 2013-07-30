package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.core.CoreContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class IndexBuilderIT {

    private EmbeddedSolrServer embeddedSolrServer;

    private IndexBuilder subject;
    private PropertyStream propertyStream;

    @Before
    public void setup() throws ParserConfigurationException, SAXException, IOException {

        CoreContainer coreContainer =  new CoreContainer();

        coreContainer.load();
        embeddedSolrServer = new EmbeddedSolrServer(coreContainer, "gxa");


        propertyStream = new PropertyStream(null);
        subject = new IndexBuilder(embeddedSolrServer, propertyStream);


    }

    @After
    public void cleanup() throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
        embeddedSolrServer.shutdown();
    }


    @Test
    public void embeddedSolrServerShouldBeInjected() throws IOException, SolrServerException {
        subject.build();

        SolrParams solrQuery = new SolrQuery("*:*");
        QueryResponse queryResponse = embeddedSolrServer.query(solrQuery);
        assertThat(queryResponse.getBeans(PropertyDocument.class), is(not(empty())));


    }

}
