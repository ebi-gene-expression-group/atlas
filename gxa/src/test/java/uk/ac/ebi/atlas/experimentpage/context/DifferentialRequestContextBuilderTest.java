package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialRequestContextBuilderTest {

    private static final Species SPECIES =
            new Species("Homo sapiens",
                    SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals",
                            ImmutableSortedMap.<String, List<String>>of()));

    private static final String CONTRAST_NAME1 = "a";
    private static final String CONTRAST_NAME2 = "b";

    @Mock
    private MicroarrayExperiment experimentMock;

    @Mock
    private MicroarrayRequestPreferences preferencesMock;

    @Mock
    private Contrast contrastMock1;

    @Mock
    private Contrast contrastMock2;

    private MicroarrayRequestContextBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayRequestContextBuilder(new MicroarrayRequestContext());

        when(experimentMock.getSpecies()).thenReturn(SPECIES);

        when(contrastMock1.getDisplayName()).thenReturn(CONTRAST_NAME1);
        when(contrastMock2.getDisplayName()).thenReturn(CONTRAST_NAME2);
        List<Contrast> contrasts = new ArrayList<>();
        contrasts.add(contrastMock1);
        contrasts.add(contrastMock2);

        when(experimentMock.getDataColumnDescriptors()).thenReturn(contrasts);
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet("a")));
        when(preferencesMock.getGeneQuery()).thenReturn(SemanticQuery.create());
        when(experimentMock.getDataColumnDescriptor(CONTRAST_NAME1)).thenReturn(contrastMock1);

    }

    @Test
    public void testBuild() throws Exception {
        MicroarrayRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getDataColumnsToReturn(), hasItem(contrastMock1));
        assertThat(context.getFilteredBySpecies(), is("homo sapiens"));
        assertThat(context.getAllDataColumnDescriptors(), hasItems(contrastMock1, contrastMock2));
    }
}