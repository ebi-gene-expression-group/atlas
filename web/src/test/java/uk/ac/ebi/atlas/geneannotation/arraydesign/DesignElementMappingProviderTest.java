package uk.ac.ebi.atlas.geneannotation.arraydesign;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DesignElementMappingProviderTest {

    private DesignElementMappingProvider subject;

    @Mock
    private ArrayDesignDao arrayDesignDaoMock;

    @Before
    public void initializeSubject() {
        when(arrayDesignDaoMock.getGeneIdentifier("ad1", "de1")).thenReturn("g1");
        subject = new DesignElementMappingProvider(arrayDesignDaoMock);
    }

    @Test
    public void testGetEnsGeneId() throws Exception {
        assertThat(subject.getEnsGeneId("ad1", "de1"), is("g1"));

        assertThat(subject.getEnsGeneId("ad1", "not there"), is(nullValue()));

    }
}
