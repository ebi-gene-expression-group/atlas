
package uk.ac.ebi.atlas.model.differential.microarray;

import org.apache.commons.configuration.XMLConfiguration;
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

    public static final String ARRAYDESIGN = "ARRAYDESIGN";

    @Mock
    XMLConfiguration xmlConfigurationMock;

    MicroarrayExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        List list = new ArrayList<>();
        list.add(ARRAYDESIGN);
        when(xmlConfigurationMock.getList("analytics/array_design")).thenReturn(list);
        subject = new MicroarrayExperimentConfiguration(xmlConfigurationMock, null);
    }

    @Test
    public void testGetArrayDesignNames() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAYDESIGN));
    }
}