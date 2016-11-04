
package uk.ac.ebi.atlas.experimentpage.context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayRequestContextTest {

    private MicroarrayRequestContext subject;

    @Mock
    private DifferentialRequestPreferences requestPreferencesMock;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayRequestContext();
        subject.setRequestPreferences(requestPreferencesMock);
        when(requestPreferencesMock.getRegulation()).thenReturn(Regulation.UP);
    }

    @Test
    public void testToString() throws Exception {
        assertThat(subject.toString(), containsString("arrayDesignAccession"));
        assertThat(subject.toString(), containsString("UP"));
    }

    @Test
    public void testSetArrayDesignAccession() throws Exception {
        subject.setArrayDesignAccession("ARRAY_DESIGN");
        assertThat(subject.getArrayDesignAccession(), is("ARRAY_DESIGN"));
    }

    @Test
    public void testGetArrayDesignAccession() throws Exception {
        assertThat(subject.getArrayDesignAccession(), is(nullValue()));
    }
}