package uk.ac.ebi.atlas.model.experimentimport.analytics.baseline;

import com.google.common.base.Joiner;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.RnaSeqBaselineAnalyticsInputStream;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class RnaSeqBaselineAnalyticsInputStreamTest {

    private static final String GENE_ID_1 = "ENSMUSG00000030105";
    private static final String GENE_ID_2 = "ENSG00000127720";
    private static final String GENE_NAME_1 = "Arl8b";
    private static final String GENE_NAME_2 = "METTL25";

    private static final String TSV_HEADER = Joiner.on("\t").join(new String[]{"Gene ID", "Gene Name", "g1", "g2", "g3", "g4", "g5"});
    private static final String TSV_LINE_1 = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1, "1", "2", "3", "4", "0.5"});
    private static final String TSV_LINE_2 = Joiner.on("\t").join(new String[]{GENE_ID_2, GENE_NAME_2, "0.00", "0", "1", "0", "1"});
    private static final String TSV_LINE_NO_EXPRESSION = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1, "0", "0", "0", "0", "0"});
    private static final String TSV_LINE_NA = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1, "0", "0", "NA", "1", "2"});
    private static final String TSV_LINE_QUARTILES = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1, "1,1,1,1,1", "2,2,2,2,2", "3,3,3,3,3", "4,4,4,4,4", "0.5,0.5,0.5,0.5,0.5"});

    private static String TSV_CONTENTS = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_1, TSV_LINE_2});


    @Test
    public void readTwoTsvLines() throws IOException {
        Reader reader = new StringReader(TSV_CONTENTS);
        RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(reader, "Test");

        BaselineAnalytics line1g1 = new BaselineAnalytics(GENE_ID_1, "g1", 1.0);
        BaselineAnalytics line1g2 = new BaselineAnalytics(GENE_ID_1, "g2", 2.0);
        BaselineAnalytics line1g3 = new BaselineAnalytics(GENE_ID_1, "g3", 3.0);
        BaselineAnalytics line1g4 = new BaselineAnalytics(GENE_ID_1, "g4", 4.0);
        BaselineAnalytics line1g5 = new BaselineAnalytics(GENE_ID_1, "g5", 0.5);

        BaselineAnalytics line2g3 = new BaselineAnalytics(GENE_ID_2, "g3", 1.0);
        BaselineAnalytics line2g5 = new BaselineAnalytics(GENE_ID_2, "g5", 1.0);

        assertThat(subject.readNext(), is(line1g1));
        assertThat(subject.readNext(), is(line1g2));
        assertThat(subject.readNext(), is(line1g3));
        assertThat(subject.readNext(), is(line1g4));
        assertThat(subject.readNext(), is(line1g5));

        assertThat(subject.readNext(), is(line2g3));
        assertThat(subject.readNext(), is(line2g5));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readTsvLineWithNoExpression() throws IOException {
        String tsvContents = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_NO_EXPRESSION});

        Reader reader = new StringReader(tsvContents);
        RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(reader, "Test");

        assertThat(subject.readNext(), is(nullValue()));
    }


    @Test
    public void readTsvLineWithNA() throws IOException {
        String tsvContents = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_NA});

        Reader reader = new StringReader(tsvContents);
        RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(reader, "Test");

        assertThat(subject.readNext(), is(new BaselineAnalytics(GENE_ID_1, "g4", 1.0)));
        assertThat(subject.readNext(), is(new BaselineAnalytics(GENE_ID_1, "g5", 2.0)));

    }

    @Test
    public void readTsvLineWithQuartiles() throws IOException {
        String tsvContents = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_QUARTILES});

        Reader reader = new StringReader(tsvContents);
        RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(reader, "Test");

        BaselineAnalytics line1g1 = new BaselineAnalytics(GENE_ID_1, "g1", 1.0);
        BaselineAnalytics line1g2 = new BaselineAnalytics(GENE_ID_1, "g2", 2.0);
        BaselineAnalytics line1g3 = new BaselineAnalytics(GENE_ID_1, "g3", 3.0);
        BaselineAnalytics line1g4 = new BaselineAnalytics(GENE_ID_1, "g4", 4.0);
        BaselineAnalytics line1g5 = new BaselineAnalytics(GENE_ID_1, "g5", 0.5);

        assertThat(subject.readNext(), is(line1g1));
        assertThat(subject.readNext(), is(line1g2));
        assertThat(subject.readNext(), is(line1g3));
        assertThat(subject.readNext(), is(line1g4));
        assertThat(subject.readNext(), is(line1g5));

        assertThat(subject.readNext(), is(nullValue()));
    }


    @Test
    public void tryResourcesClosesUnderlyingReaderWhenFinished() throws IOException {
        Reader reader = spy(new StringReader(TSV_CONTENTS));

        try (RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(reader, "Test")) {
            subject.readNext();
        }

        verify(reader).close();
    }

    @Test
    public void tryResourcesAutoClosesUnderlyingReaderOnException() throws IOException {
        Reader reader = spy(new StringReader(TSV_CONTENTS));

        try (RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(reader, "Test")) {
            subject.readNext();
            throw new RuntimeException("foobar");
        } catch (RuntimeException e) {
            // ignore
        }

        verify(reader).close();
    }

}
