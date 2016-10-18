
package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayDataWriterTest {

    @Mock
    private CsvReaderFactory csvReaderFactoryMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private CSVReader csvReaderMock;

    private String[] header = {"geneId","geneName", "DesignElementAccession", "C1", "C2", "C3"};
    private String[] line = {"id1", "name1", "de123", "1", "0", "10.5"};

    private ExpressionsWriterImpl subject;

    @Before
    public void initSubject() throws Exception {
        when(csvReaderFactoryMock.createTsvReader(anyString())).thenReturn(csvReaderMock);
        when(csvReaderMock.readNext()).thenReturn(header)
                .thenReturn(line)
                .thenReturn(null);

        AnalyticsDataHeaderBuilder headerBuilder = new AnalyticsDataHeaderBuilder();

        subject = new ExpressionsWriterImpl(csvReaderFactoryMock);
        subject.setFileUrlTemplate("magetab/{0}/{0}-row-counts.tsv");
        subject.setResponseWriter(csvWriterMock);
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(new String[]{"geneId", "geneName", "DesignElementAccession", "C1", "C2", "C3"}));
    }

    @Test
    public void testWrite() throws Exception {
        subject.setExperimentAccession("Exp1");
        Long count = subject.write();

        verify(csvWriterMock).writeNext(new String[]{"geneId","geneName","DesignElementAccession","C1","C2","C3"});

        verify(csvWriterMock).writeNext(new String[]{"id1", "name1","de123","1","0","10.5"});

        assertThat(count, is(1L));
    }
}
