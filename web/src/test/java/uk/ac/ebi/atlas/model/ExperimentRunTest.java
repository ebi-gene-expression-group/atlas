package uk.ac.ebi.atlas.model;

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
    private FactorValue factorValueMock1;

    @Mock
    private FactorValue factorValueMock2;

    private static final String organismPart = "finger";

    @Before
    public void setUp() throws Exception {

        when(factorValueMock1.getType()).thenReturn("A_TYPE");
        when(factorValueMock1.getName()).thenReturn("factor1");
        when(factorValueMock1.getValue()).thenReturn("value1");

        when(factorValueMock2.getType()).thenReturn("ORGANISM_PART");
        when(factorValueMock2.getName()).thenReturn("org");
        when(factorValueMock2.getValue()).thenReturn(organismPart);

        subject = new ExperimentRun("RUN_ACCESSION")
                .addFactorValue(factorValueMock1)
                .addFactorValue(factorValueMock2);
    }

    @Test
    public void testGetOrganismPart() throws Exception {

        assertThat(subject.getFactorValue(factorValueMock2.getType()).getValue(), is(organismPart));

    }

}
