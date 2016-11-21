
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
public class ExpressionsWriterImplTest {

    @Mock
    private CsvReaderFactory csvReaderFactoryMock;

    @Mock
    private CSVWriter csvWriterMock;

    @Mock
    private CSVReader csvReaderMock;

    private String[] header = {"GeneId", "GeneName", "SRR057596", "SRR057597", "SRR057598"};
    private String[] line1 = {"ens1", "name1", "1", "0", "10.5"};
    private String[] line2 = {"ens2", "name2", "1", "0", "10.\"5"};

    private ExpressionsWriterImpl subject;

    @Before
    public void initSubject() throws Exception {
        when(csvReaderFactoryMock.createForTsv(anyString())).thenReturn(csvReaderMock);
        when(csvReaderMock.readNext()).thenReturn(header)
                .thenReturn(line1)
                .thenReturn(line2)
                .thenReturn(null);

        subject = new ExpressionsWriterImpl(csvReaderFactoryMock);
        subject.setFileUrlTemplate("magetab/{0}/{0}-row-counts.tsv");
        subject.setHeaderBuilder(null);
        subject.setResponseWriter(csvWriterMock);
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(new String[]{"GeneId", "GeneName", "SRR057596", "SRR057597", "SRR057598"}));
    }

    @Test
    public void testWrite() throws Exception {
        subject.setExperimentAccession("Exp1");
        Long count = subject.write();

        verify(csvWriterMock).writeNext(new String[]{"GeneId","GeneName","SRR057596","SRR057597","SRR057598"});

        verify(csvWriterMock).writeNext(new String[]{"ens1","name1","1","0","10.5"});

        verify(csvWriterMock).writeNext(new String[]{"ens2","name2","1","0","10.\"5"});

        assertThat(count, is(2L));
    }
}
