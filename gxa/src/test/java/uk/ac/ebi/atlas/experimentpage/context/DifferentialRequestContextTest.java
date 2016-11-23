
package uk.ac.ebi.atlas.experimentpage.context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialRequestContextTest {

    DifferentialRequestContext subject;

    @Mock
    DifferentialRequestPreferences requestPreferencesMock;

    @Before
    public void setUp() throws Exception {
        subject = new DifferentialRequestContext();

        when(requestPreferencesMock.getRegulation()).thenReturn(Regulation.UP);

        subject.setRequestPreferences(requestPreferencesMock);
    }

    @Test
    public void testGetRegulation() throws Exception {
        assertThat(subject.getRegulation(), is(Regulation.UP));
    }

    @Test
    public void testSetRegulation() throws Exception {
        when(requestPreferencesMock.getRegulation()).thenReturn(Regulation.DOWN);
        assertThat(subject.getRegulation(), is(Regulation.DOWN));
    }

    @Test
    public void testToString() throws Exception {
        assertThat(subject.toString(), containsString("regulation"));
    }
}