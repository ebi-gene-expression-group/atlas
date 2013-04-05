package uk.ac.ebi.atlas.geneannotation.arraydesign;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;
import uk.ac.ebi.atlas.utils.DesignElementKeyGenerator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DesignElementMappingProviderTest {

    private DesignElementMappingProvider subject;

    @Mock
    private AnnotationEnvironment annotationEnvironmentMock;

    private ConcurrentMap<String, String> map = new ConcurrentHashMap();

    @Before
    public void initializeSubject() {
        map.put(DesignElementKeyGenerator.getKey("ad1", "de1"), "g1");
        when(annotationEnvironmentMock.geneDesignElementsToGeneNames()).thenReturn(map);
        subject = new DesignElementMappingProvider(annotationEnvironmentMock);
    }

    @Test
    public void testGetEnsGeneId() throws Exception {
        assertThat(subject.getEnsGeneId("ad1", "de1"), is("g1"));

        assertThat(subject.getEnsGeneId("ad1", "not there"), is(nullValue()));

    }
}
