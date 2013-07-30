package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IndexBuilderTest {

    @Mock
    private SolrServer solrServerMock;

    private IndexBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new IndexBuilder(solrServerMock);
    }

    @Test
    public void testBuild() throws Exception {

    }
}
