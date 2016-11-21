package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentConfigurationTest {

    private static final String ARRAYDESIGN = "ARRAYDESIGN";

    @Mock
    private XmlReader xmlReaderMock;

    private MicroarrayExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        when(xmlReaderMock.getList("analytics/array_design")).thenReturn(Lists.newArrayList(ARRAYDESIGN));
        subject = new MicroarrayExperimentConfiguration(xmlReaderMock);
    }

    @Test
    public void testGetArrayDesignNames() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAYDESIGN));
    }
}