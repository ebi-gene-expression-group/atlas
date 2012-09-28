package uk.ac.ebi.atlas.streams;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentRun;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileInputStreamBuilderTest {

    @Mock
    private CSVReader csvReaderMock;

    @Mock
    private InputStream inputStreamMock;

    private GeneProfilesInputStream.Builder subject;

    @Before
    public void initMocks() throws IOException{
        when(csvReaderMock.readNext()).thenReturn(new String[]{"", "header_value_1", "header_value_2"});
    }

    @Before
    public void initSubject(){
        subject = GeneProfilesInputStream.forInputStream(inputStreamMock)
                .withCsvReader(csvReaderMock);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenExperimentRunsHaventBeenSet(){
        //when
        subject.create();
    }

    @Test
    public void shouldBeOkWhenExperimentRunsHaventBeenSet(){
        //when
        subject.withExperimentRuns(new ArrayList<ExperimentRun>());
        //then
        assertThat(subject.create(), is(not(nullValue())));
    }

    @Test
    public void shouldHaveReadHeaderLineOnCreate() throws IOException{
        //when
        subject.withExperimentRuns(new ArrayList<ExperimentRun>());
        //then
        verify(csvReaderMock).readNext();
    }



}
