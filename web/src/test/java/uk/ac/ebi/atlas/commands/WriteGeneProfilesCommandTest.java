package uk.ac.ebi.atlas.commands;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WriteGeneProfilesCommandTest {

    @Mock
    private ObjectInputStream<GeneProfile> inputStreamMock;
    @Mock
    private ApplicationProperties applicationPropertiesMock;
    @Mock
    private RequestPreferences requestPreferencesMock;
    @Mock
    private CSVWriter csvWriterMock;
    @Mock
    private GeneProfile geneProfileMock1;
    @Mock
    private GeneProfile geneProfileMock2;

    private WriteGeneProfilesCommand subject;

    @Before
    public void initMocks(){
        SortedSet<String> organismParts = Sets.newTreeSet(Sets.newHashSet("adipose", "brain", "breast", "liver", "lung"));

        when(requestPreferencesMock.getOrganismParts()).thenReturn(organismParts);

        when(inputStreamMock.readNext()).thenReturn(geneProfileMock1)
                                        .thenReturn(geneProfileMock2)
                                        .thenReturn(null);

        when(geneProfileMock1.getGeneName()).thenReturn("GN1");
        when(geneProfileMock1.getGeneId()).thenReturn("GI1");
        when(geneProfileMock1.getExpressionLevel("brain")).thenReturn(0.11d);
        when(geneProfileMock1.getExpressionLevel("lung")).thenReturn(9d);

        when(geneProfileMock2.getGeneName()).thenReturn("GN2");
        when(geneProfileMock2.getGeneId()).thenReturn("GI2");
        when(geneProfileMock2.getExpressionLevel("liver")).thenReturn(21.12d);
    }

    @Before
    public void initSubject() throws Exception {
        subject = new WriteGeneProfilesCommand(applicationPropertiesMock);
        subject.setRequestPreferences(requestPreferencesMock);
        subject.setCsvWriter(csvWriterMock);
    }

    @Test
    public void applyShouldUseCsvWriter() throws Exception {

        long count = subject.apply(requestPreferencesMock, inputStreamMock);

        verify(csvWriterMock).writeNext(new String[]{"Gene name", "Gene Id", "adipose", "brain", "breast", "liver", "lung"});

        verify(csvWriterMock).writeNext(new String[]{"GN1","GI1","0","0.11","0","0","9"});

        verify(csvWriterMock).writeNext(new String[]{"GN2","GI2","0","0","0","21.12","0"});

        assertThat(count, is(2L));

    }

}
