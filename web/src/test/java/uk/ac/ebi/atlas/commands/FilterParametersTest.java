package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilterParametersTest {

    public static final String ORGANISM_PART = "ORGANISM_PART";
    public static final String CELL_LINE = "CELL_LINE";
    private static final String EXPERIMENT_ACCESSION = "E-GEKO";

    private FilterParameters subject;

    @Mock
    ExperimentsCache experimentCacheMock;

    @Mock
    Experiment experimentMock;

    @Mock
    SolrClient solrClientMock;

    @Before
    public void initSubject(){

        when(experimentMock.getFactorName(anyString(),anyString())).thenReturn("X");

        FilterParameters.Builder builder = new FilterParameters.Builder();

        subject = builder.forExperiment(experimentMock)
                         .withFilterFactors(Sets.newHashSet("A:B", "C:D"))
                         .build();

    }

    @Test
    public void formatForDisplayShouldReplaceUnderscoresWithSpacesAndCapitilizeFirstLetter() {
        assertThat(subject.formatForDisplay(ORGANISM_PART), is("Organism part"));
        assertThat(subject.formatForDisplay(CELL_LINE), is("Cell line"));
    }

}
