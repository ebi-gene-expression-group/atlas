package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IndexBuilderTest {

    @Mock
    private SolrServer solrServerMock;

    @Mock
    private PropertyStream propertyStreamMock;

    private IndexBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new IndexBuilder(solrServerMock, propertyStreamMock);
    }

    @Test
    public void testBuild() throws Exception {
        PropertyDocument document1 = new PropertyDocument();
        PropertyDocument document2 = new PropertyDocument();
        given(propertyStreamMock.next()).willReturn(document1).willReturn(document2).willReturn(null);
        subject.build();

        verify(solrServerMock).addBean(document1);
        verify(solrServerMock).addBean(document2);
        verify(propertyStreamMock, times(3)).next();
    }
}
