package uk.ac.ebi.atlas.model.differential.microarray;

import org.apache.commons.configuration2.XMLConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentConfigurationTest {

    private static final String ARRAYDESIGN = "ARRAYDESIGN";

    @Mock
    private XMLConfiguration xmlConfigurationMock;

    private MicroarrayExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(ARRAYDESIGN);
        when(xmlConfigurationMock.getList(String.class, "analytics/array_design")).thenReturn(list);
        subject = new MicroarrayExperimentConfiguration(xmlConfigurationMock, null, null);
    }

    @Test
    public void testGetArrayDesignNames() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAYDESIGN));
    }
}