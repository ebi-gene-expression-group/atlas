package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.StringReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionsWriterImplTest {

    @Mock
    private CSVWriter csvWriterMock;

    private String[] header = {"GeneId", "GeneName", "SRR057596", "SRR057597", "SRR057598"};
    private String[] line1 = {"ens1", "name1", "1", "0", "10.5"};
    private String[] line2 = {"ens2", "name2", "1", "0", "10.5"};

    private ExpressionsWriterImpl subject;

    @Before
    public void initSubject() throws Exception {
        StringReader reader =
                new StringReader(
                        Joiner.on('\n').join(
                                Joiner.on('\t').join(header),
                                Joiner.on('\t').join(line1),
                                Joiner.on('\t').join(line2)));

        subject = new ExpressionsWriterImpl();
        subject.setReader(reader);
        subject.setHeaderBuilder(null);
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
        verify(csvWriterMock).writeNext(line1);
        verify(csvWriterMock).writeNext(line2);

        assertThat(count, is(2L));
    }
}
