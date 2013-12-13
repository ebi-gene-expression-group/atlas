package uk.ac.ebi.atlas.file;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TsvDataStreamingParserTest {

    private static String tsvContents;

    @BeforeClass
    public static void init() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gene ID\tGene Name\tg1\tg2\tg3\tg4\tg5\n");
        sb.append("ENSMUSG00000030105\tArl8b\t1\t2\t3\t4\t-0.00248510654802851\n");
        sb.append("ENSG00000127720\tMETTL25\t0\t0\t0\t0\t1\n");
        tsvContents = sb.toString();
    }

    @Test
    public void readTwoTsvLines() throws IOException {
        Reader tsvReader = spy(new StringReader(tsvContents));

        Iterable<TsvData> subject = new TsvDataStreamingParser(new TsvStreamingParser(tsvReader));

        Iterator<TsvData> iterator = subject.iterator();

        TsvData line1 = iterator.next();
        TsvData line2 = iterator.next();

        assertThat(line1.getGeneId(), is("ENSMUSG00000030105"));
        assertThat(line1.getGeneName(), is("Arl8b"));

        assertThat(line1.getEverythingElse().keySet(), contains("g1","g2","g3","g4","g5"));
        assertThat(line1.getEverythingElse().values(), contains("1","2","3","4","-0.00248510654802851"));

        assertThat(line2.getGeneId(), is("ENSG00000127720"));
        assertThat(line2.getGeneName(), is("METTL25"));

        assertThat(line2.getEverythingElse().keySet(), contains("g1","g2","g3","g4","g5"));
        assertThat(line2.getEverythingElse().values(), contains("0", "0", "0", "0", "1"));

    }

    @Test
    public void tryRresourcesAutoClosesUnderlyingReaderOnException() throws IOException {
        Reader tsvReader = spy(new StringReader(tsvContents));

        try (TsvDataStreamingParser subject = new TsvDataStreamingParser(new TsvStreamingParser(tsvReader))) {
            throw new RuntimeException("foobar");
        } catch (RuntimeException e) {
            // ignore
        }

        verify(tsvReader).close();

    }

    @Test
    public void closesUnderlyingReaderWhenFinished() throws IOException {
        Reader tsvReader = spy(new StringReader(tsvContents));

        TsvDataStreamingParser subject = new TsvDataStreamingParser(new TsvStreamingParser(tsvReader));

        for (TsvData tsvData : subject) {
            System.out.println(tsvData);
        }

        verify(tsvReader).close();
    }

    @Test
    public void tryResourcesClosesUnderlyingReaderWhenFinished() throws IOException {
        Reader tsvReader = spy(new StringReader(tsvContents));

        try (TsvDataStreamingParser subject = new TsvDataStreamingParser(new TsvStreamingParser(tsvReader))) {
            for (TsvData tsvData : subject) {
                System.out.println(tsvData);
            }
        }

        verify(tsvReader).close();
    }


}
