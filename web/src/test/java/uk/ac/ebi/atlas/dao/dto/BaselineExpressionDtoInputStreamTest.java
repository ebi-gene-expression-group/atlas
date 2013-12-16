package uk.ac.ebi.atlas.dao.dto;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BaselineExpressionDtoInputStreamTest {

    private static String tsvContents;
    private static CsvReaderFactory csvReaderFactory = new CsvReaderFactory();

    @BeforeClass
    public static void init() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gene ID\tGene Name\tg1\tg2\tg3\tg4\tg5\n");
        sb.append("ENSMUSG00000030105\tArl8b\t1\t2\t3\t4\t-0.00248510654802851\n");
        sb.append("ENSG00000127720\tMETTL25\t0\t0\t1\t0\t1\n");
        tsvContents = sb.toString();
    }

    @Test
    public void readTwoTsvLines() throws IOException {
        Reader tsvSource = new StringReader(tsvContents);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        BaselineExpressionDtoInputStream subject = new BaselineExpressionDtoInputStream(csvReader);

        BaselineExpressionDto line1g1 = new BaselineExpressionDto("ENSMUSG00000030105", "g1", 1.0);
        BaselineExpressionDto line1g2 = new BaselineExpressionDto("ENSMUSG00000030105", "g2", 2.0);
        BaselineExpressionDto line1g3 = new BaselineExpressionDto("ENSMUSG00000030105", "g3", 3.0);
        BaselineExpressionDto line1g4 = new BaselineExpressionDto("ENSMUSG00000030105", "g4", 4.0);
        BaselineExpressionDto line1g5 = new BaselineExpressionDto("ENSMUSG00000030105", "g5", -0.00248510654802851);

        BaselineExpressionDto line2g1 = new BaselineExpressionDto("ENSG00000127720", "g1", 0.0);
        BaselineExpressionDto line2g2 = new BaselineExpressionDto("ENSG00000127720", "g2", 0.0);
        BaselineExpressionDto line2g3 = new BaselineExpressionDto("ENSG00000127720", "g3", 1.0);
        BaselineExpressionDto line2g4 = new BaselineExpressionDto("ENSG00000127720", "g4", 0.0);
        BaselineExpressionDto line2g5 = new BaselineExpressionDto("ENSG00000127720", "g5", 1.0);

        assertThat(subject.readNext(), is(line1g1));
        assertThat(subject.readNext(), is(line1g2));
        assertThat(subject.readNext(), is(line1g3));
        assertThat(subject.readNext(), is(line1g4));
        assertThat(subject.readNext(), is(line1g5));

        assertThat(subject.readNext(), is(line2g1));
        assertThat(subject.readNext(), is(line2g2));
        assertThat(subject.readNext(), is(line2g3));
        assertThat(subject.readNext(), is(line2g4));
        assertThat(subject.readNext(), is(line2g5));
    }

    @Test
    public void tryResourcesClosesUnderlyingReaderWhenFinished() throws IOException {
        Reader tsvSource = spy(new StringReader(tsvContents));
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);

        try (BaselineExpressionDtoInputStream subject = new BaselineExpressionDtoInputStream(csvReader)) {
            subject.readNext();
        }

        verify(tsvSource).close();
    }

    @Test
    public void tryResourcesAutoClosesUnderlyingReaderOnException() throws IOException {
        Reader tsvSource = spy(new StringReader(tsvContents));
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);

        try (BaselineExpressionDtoInputStream subject = new BaselineExpressionDtoInputStream(csvReader)) {
            subject.readNext();
            throw new RuntimeException("foobar");
        } catch (RuntimeException e) {
            // ignore
        }

        verify(tsvSource).close();
    }

}
