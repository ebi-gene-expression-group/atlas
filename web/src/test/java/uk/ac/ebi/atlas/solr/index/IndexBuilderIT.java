package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class IndexBuilderIT {

    private EmbeddedSolrServer embeddedSolrServer;

    private IndexBuilder subject;

    @Before
    public void setup() throws ParserConfigurationException, SAXException, IOException {

        CoreContainer coreContainer =  new CoreContainer();
//        CoreContainer coreContainer = new CoreContainer("/Users/nsklyar/embedded-solr", new File("/Users/nsklyar/embedded-solr/solr.xml"));

        coreContainer.load();
        embeddedSolrServer = new EmbeddedSolrServer(coreContainer, "gxa");


        subject = new IndexBuilder(embeddedSolrServer);


    }

    @After
    public void cleanup() throws IOException, SolrServerException {
        embeddedSolrServer.deleteByQuery("*:*");
        embeddedSolrServer.shutdown();
    }


    @Test
    public void embeddedSolrServerShouldBeInjected() throws IOException, SolrServerException {
        subject.build();

    }

}
