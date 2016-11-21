
package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayDataWriterTest {

    @Mock
    private CSVWriter csvWriterMock;

    private String[] header = {"geneId", "geneName", "DesignElementAccession", "C1", "C2", "C3"};
    private String[] line = {"id1", "name1", "de123", "1", "0", "10.5"};

    private ExpressionsWriterImpl subject;

    @Before
    public void initSubject() throws Exception {
        StringReader reader =
                new StringReader(
                        Joiner.on('\n').join(
                                Joiner.on('\t').join(header),
                                Joiner.on('\t').join(line)));

        subject = new ExpressionsWriterImpl();
        subject.setReader(reader);
        subject.setResponseWriter(csvWriterMock);
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(header));
    }

    @Test
    public void testWrite() throws Exception {
        Long count = subject.write();

        verify(csvWriterMock).writeNext(header);
        verify(csvWriterMock).writeNext(line);

        assertThat(count, is(1L));
    }
}
