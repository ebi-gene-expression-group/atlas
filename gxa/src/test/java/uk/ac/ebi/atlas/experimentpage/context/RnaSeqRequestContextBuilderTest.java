package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.ImmutableSortedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqRequestContextBuilderTest {

    private static final Species SPECIES =
            new Species("Homo sapiens",
                    SpeciesProperties.create("homo sapiens", "Homo_sapiens", "ORGANISM_PART", "animals",
                            ImmutableSortedMap.<String, List<String>>of()));
    
    @Mock
    private DifferentialExperiment experimentMock;

    @Mock
    private DifferentialRequestPreferences preferencesMock;

    private RnaSeqRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        when(experimentMock.getSpecies()).thenReturn(SPECIES);
        when(preferencesMock.getGeneQuery()).thenReturn(SemanticQuery.create());
        subject = new RnaSeqRequestContextBuilder(new RnaSeqRequestContext());
    }

    @Test
    public void testBuild() throws Exception {
        RnaSeqRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context, is(not(nullValue())));
        assertThat(context.getFilteredBySpecies(), is("homo sapiens"));
    }
}