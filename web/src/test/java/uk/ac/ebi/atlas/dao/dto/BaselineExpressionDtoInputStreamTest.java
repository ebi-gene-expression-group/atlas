package uk.ac.ebi.atlas.dao.dto;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Joiner;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BaselineExpressionDtoInputStreamTest {

    private static CsvReaderFactory csvReaderFactory = new CsvReaderFactory();


    public static final String GENE_ID_1 = "ENSMUSG00000030105";
    public static final String GENE_ID_2 = "ENSG00000127720";
    private static final String GENE_NAME_1 = "Arl8b";
    private static final String GENE_NAME_2 = "METTL25";

    private static final String TSV_HEADER = Joiner.on("\t").join(new String[]{"Gene ID", "Gene Name", "g1", "g2", "g3", "g4", "g5"});
    private static final String TSV_LINE_1 = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1, "1", "2", "3", "4", "-0.00248510654802851"});
    private static final String TSV_LINE_2 = Joiner.on("\t").join(new String[]{GENE_ID_2, GENE_NAME_2, "0.00", "0", "1", "0", "1"});
    private static final String TSV_LINE_NO_EXPRESSION = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1, "0", "0", "0", "0", "0"});

    private static String tsvContents = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_1, TSV_LINE_2});
    private static String tsvContentsNoExpression = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_NO_EXPRESSION});


    @Test
    public void readTwoTsvLines() throws IOException {
        Reader tsvSource = new StringReader(tsvContents);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        BaselineExpressionDtoInputStream subject = new BaselineExpressionDtoInputStream(csvReader);

        BaselineExpressionDto line1g1 = new BaselineExpressionDto(GENE_ID_1, "g1", 1.0);
        BaselineExpressionDto line1g2 = new BaselineExpressionDto(GENE_ID_1, "g2", 2.0);
        BaselineExpressionDto line1g3 = new BaselineExpressionDto(GENE_ID_1, "g3", 3.0);
        BaselineExpressionDto line1g4 = new BaselineExpressionDto(GENE_ID_1, "g4", 4.0);
        BaselineExpressionDto line1g5 = new BaselineExpressionDto(GENE_ID_1, "g5", -0.00248510654802851);

        BaselineExpressionDto line2g3 = new BaselineExpressionDto(GENE_ID_2, "g3", 1.0);
        BaselineExpressionDto line2g5 = new BaselineExpressionDto(GENE_ID_2, "g5", 1.0);

        assertThat(subject.readNext(), is(line1g1));
        assertThat(subject.readNext(), is(line1g2));
        assertThat(subject.readNext(), is(line1g3));
        assertThat(subject.readNext(), is(line1g4));
        assertThat(subject.readNext(), is(line1g5));

        assertThat(subject.readNext(), is(line2g3));
        assertThat(subject.readNext(), is(line2g5));
    }

    @Test
    public void readTsvLineWithNoExpression() throws IOException {
        Reader tsvSource = new StringReader(tsvContentsNoExpression);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        BaselineExpressionDtoInputStream subject = new BaselineExpressionDtoInputStream(csvReader);

        assertThat(subject.readNext(), is(nullValue()));
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
