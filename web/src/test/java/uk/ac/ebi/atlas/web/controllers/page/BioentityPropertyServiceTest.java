package uk.ac.ebi.atlas.web.controllers.page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.utils.UniProtClient;
import uk.ac.ebi.atlas.web.BioentityPageProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class BioentityPropertyServiceTest {

    public static final String IDENTIFIER = "IDENTIFIER";

    private BioentityPropertyService subject;

    @Mock
    private UniProtClient uniprotClientMock;

    @Mock
    private BioentityPageProperties bioentityPageProperties;

    @Mock
    private SolrClient solrClientMock;


    @Before
    public void setUp() throws Exception {
        subject = new BioentityPropertyService(solrClientMock, uniprotClientMock, bioentityPageProperties);
    }

    @Test
    public void testGetSpecies() throws Exception {

    }

    @Test
    public void testGetPropertyLinks() throws Exception {

    }

    @Test
    public void testTransformOrthologToSymbol() throws Exception {
        assertThat(subject.transformOrthologToSymbol(IDENTIFIER), is("symbol (SPECIES)"));

    }

    @Test
    public void testCreateLink() throws Exception {

    }

    @Test
    public void testGetLinkText() throws Exception {

    }

    @Test
    public void testGetEncodedString() throws Exception {

    }

    @Test
    public void testGetFirstValueOfProperty() throws Exception {

    }

    @Test
    public void testAddReactomePropertyValues() throws Exception {

    }
}
