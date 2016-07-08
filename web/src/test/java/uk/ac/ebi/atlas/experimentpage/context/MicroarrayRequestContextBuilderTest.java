package uk.ac.ebi.atlas.experimentpage.context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayRequestContextBuilderTest {

    public static final String ACCESSION = "ACCESSION";
    private static final String SPECIES = "SPECIES";
    @Mock
    MicroarrayExperiment experimentMock;

    @Mock
    MicroarrayRequestPreferences preferencesMock;

    MicroarrayRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(preferencesMock.getArrayDesignAccession()).thenReturn(ACCESSION);
        when(preferencesMock.getGeneQuery()).thenReturn(GeneQuery.create());
        when(experimentMock.getFirstOrganism()).thenReturn(SPECIES);
        subject = new MicroarrayRequestContextBuilder(new MicroarrayRequestContext());
    }

    @Test
    public void testBuild() throws Exception {
        MicroarrayRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getArrayDesignAccession(), is(ACCESSION));
        assertThat(context.getFilteredBySpecies(), is(SPECIES.toLowerCase()));
    }
}