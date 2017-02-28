package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.ImmutableSortedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayRequestContextBuilderTest {

    private static final String ACCESSION = "ACCESSION";
    private static final Species SPECIES =
            new Species("Homo sapiens",
                    SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals",
                            ImmutableSortedMap.<String, List<String>>of()));

    @Mock
    private MicroarrayExperiment experimentMock;

    @Mock
    private MicroarrayRequestPreferences preferencesMock;

    private MicroarrayRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(preferencesMock.getArrayDesignAccession()).thenReturn(ACCESSION);
        when(preferencesMock.getGeneQuery()).thenReturn(SemanticQuery.create());
        when(experimentMock.getSpecies()).thenReturn(SPECIES);
        subject = new MicroarrayRequestContextBuilder(new MicroarrayRequestContext());
    }

    @Test
    public void testBuild() throws Exception {
        MicroarrayRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getArrayDesignAccession(), is(ACCESSION));
        assertThat(context.getSpecies(), is("homo sapiens"));
    }
}