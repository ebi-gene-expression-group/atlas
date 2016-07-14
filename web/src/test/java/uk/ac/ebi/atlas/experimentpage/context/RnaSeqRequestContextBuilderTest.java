package uk.ac.ebi.atlas.experimentpage.context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqRequestContextBuilderTest {

    private static final String SPECIES = "species";
    
    @Mock
    DifferentialExperiment experimentMock;

    @Mock
    DifferentialRequestPreferences preferencesMock;

    private RnaSeqRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(experimentMock.getSpecies()).thenReturn(SPECIES);
        when(preferencesMock.getGeneQuery()).thenReturn(GeneQuery.create());
        subject = new RnaSeqRequestContextBuilder(new RnaSeqRequestContext());
    }

    @Test
    public void testBuild() throws Exception {
        RnaSeqRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context, is(not(nullValue())));
        assertThat(context.getFilteredBySpecies(), is(SPECIES.toLowerCase()));
    }
}