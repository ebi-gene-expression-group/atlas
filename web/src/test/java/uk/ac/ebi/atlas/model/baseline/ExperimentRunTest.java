package uk.ac.ebi.atlas.model.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentRunTest {

    private ExperimentRun subject;

    @Mock
    private Factor factorMock1;

    @Mock
    private Factor factorMock2;

    private static final String organismPart = "finger";

    @Before
    public void setUp() throws Exception {

        when(factorMock1.getType()).thenReturn("A_TYPE");
        when(factorMock1.getValue()).thenReturn("value1");

        when(factorMock2.getType()).thenReturn("ORGANISM_PART");
        when(factorMock2.getValue()).thenReturn(organismPart);

        subject = new ExperimentRun("RUN_ACCESSION")
                .addFactor(factorMock1)
                .addFactor(factorMock2);
    }

    @Test
    public void testGetOrganismPart() throws Exception {

        assertThat(subject.getFactorByType(factorMock2.getType()).getValue(), is(organismPart));

    }

}
