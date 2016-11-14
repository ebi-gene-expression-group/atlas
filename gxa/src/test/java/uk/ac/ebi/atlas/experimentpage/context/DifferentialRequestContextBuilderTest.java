package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialRequestContextBuilderTest {

    private static final Species SPECIES = new Species("homo sapiens", "homo sapiens", "homo sapiens", "animals");
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
        SortedSet<Contrast> sortedSet = new TreeSet<>();
        sortedSet.add(contrastMock1);
        sortedSet.add(contrastMock2);

        when(experimentMock.getContrasts()).thenReturn(sortedSet);
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet("a")));
        when(preferencesMock.getGeneQuery()).thenReturn(SemanticQuery.create());
        when(experimentMock.getContrast(CONTRAST_NAME1)).thenReturn(contrastMock1);

    }

    @Test
    public void testBuild() throws Exception {
        MicroarrayRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getSelectedQueryFactors(), hasItem(contrastMock1));
        assertThat(context.getFilteredBySpecies(), is("homo sapiens"));
        assertThat(context.getAllQueryFactors(), hasItems(contrastMock1, contrastMock2));
    }
}