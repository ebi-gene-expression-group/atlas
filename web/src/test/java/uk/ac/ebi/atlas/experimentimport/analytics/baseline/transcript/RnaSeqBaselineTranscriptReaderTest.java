package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsInputStream;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class RnaSeqBaselineTranscriptReaderTest {

    private static final String TRANSCRIPT_ID_1 = "TRANSCRIPT_ID_1";
    private static final String TRANSCRIPT_ID_2 = "TRANSCRIPT_ID_2";
    private static final String TRANSCRIPT_ID_3 = "TRANSCRIPT_ID_3";
    private static final String TRANSCRIPT_ID_4 = "TRANSCRIPT_ID_4";
    private static final String A_GENE_ID = "A_GENE_ID";
    private static final String A_GENE_NAME = "A_GENE_NAME";
    private static final RnaSeqBaselineTranscript TRANSCRIPT_1 = RnaSeqBaselineTranscript.create(A_GENE_ID, TRANSCRIPT_ID_1, new Double[] {0.11, 1.3242, 0D, 0.0003});
    private static final RnaSeqBaselineTranscript TRANSCRIPT_2 = RnaSeqBaselineTranscript.create(A_GENE_ID, TRANSCRIPT_ID_2, new Double[] {0.00, 2D, 0D, 0.0002});
    private static final RnaSeqBaselineTranscript TRANSCRIPT_FAIL_1 = RnaSeqBaselineTranscript.create(A_GENE_ID, TRANSCRIPT_ID_3, new Double[] {0.00, 0D, 0D, 1D});
    private static final RnaSeqBaselineTranscript TRANSCRIPT_FAIL_2 = RnaSeqBaselineTranscript.create(A_GENE_ID, TRANSCRIPT_ID_4, new Double[] {0D, 0D, 0D, 0D});
    private static final String TSV_HEADER = Joiner.on("\t").join( new String[]{"Gene ID", "Gene Name", "Transcript ID", "g1", "g2", "g3", "g4"} );
    private static final String TSV_LINE_1 = Joiner.on("\t").join( new String[]{A_GENE_ID, A_GENE_NAME, TRANSCRIPT_ID_1, "0.11", "1.3242", "0", "0.0003"} );
    private static final String TSV_LINE_2 = Joiner.on("\t").join( new String[]{A_GENE_ID, A_GENE_NAME, TRANSCRIPT_ID_2, "0.00", "2", "0", "0.0002"} );
    private static final String TSV_LINE_FAIL_1 = Joiner.on("\t").join( new String[]{A_GENE_ID, A_GENE_NAME, TRANSCRIPT_ID_3, "0.00", "FAIL", "0", "1"} );
    private static final String TSV_LINE_FAIL_2 = Joiner.on("\t").join( new String[]{A_GENE_ID, A_GENE_NAME, TRANSCRIPT_ID_4, "FAIL", "FAIL", "FAIL", "FAIL"} );
    private static final String[] ORDERED_ASSAY_GROUP_IDS = new String[]{"g1", "g2", "g3", "g4"};

    private static final String TSV_CONTENTS = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_1, TSV_LINE_2});

    @Test
    public void readTwoTsvLines() throws IOException {
        Reader tsvSource = new StringReader(TSV_CONTENTS);
        CSVReader csvReader = CsvReaderFactory.createTsvReader(tsvSource);
        RnaSeqBaselineTranscriptReader subject = new RnaSeqBaselineTranscriptReader(csvReader, "Test", ORDERED_ASSAY_GROUP_IDS);

        assertThat(subject.next(), is(TRANSCRIPT_1));
        assertThat(subject.next(), is(TRANSCRIPT_2));
        assertThat(subject.hasNext(), is(false));
    }

    @Test
    public void readTsvLineWithFail() throws IOException {
        String tsvContents = Joiner.on("\n").join(new String[]{TSV_HEADER, TSV_LINE_FAIL_1, TSV_LINE_FAIL_2});

        Reader tsvSource = new StringReader(tsvContents);
        CSVReader csvReader = CsvReaderFactory.createTsvReader(tsvSource);
        RnaSeqBaselineTranscriptReader subject = new RnaSeqBaselineTranscriptReader(csvReader, "Test", ORDERED_ASSAY_GROUP_IDS);

        assertThat(subject.next(), is(TRANSCRIPT_FAIL_1));
        assertThat(subject.next(), is(TRANSCRIPT_FAIL_2));
        assertThat(subject.hasNext(), is(false));
    }

    @Test
    public void readTsvWithDifferentHeaderOrder() throws IOException {
        final String TSV_HEADER_DIFFERENT_ORDER = Joiner.on("\t").join( new String[]{"Gene ID", "Gene Name", "Transcript ID", "g2", "g1", "g4", "g3"} );
        final String TSV_LINE_DIFFERENT_ORDER = Joiner.on("\t").join( new String[]{A_GENE_ID, A_GENE_NAME, TRANSCRIPT_ID_1, "2", "1", "4", "3"} );
        final RnaSeqBaselineTranscript TRANSCRIPT_DIFFERENT_ORDER = RnaSeqBaselineTranscript.create(A_GENE_ID, TRANSCRIPT_ID_1, new Double[] {1D, 2D, 3D, 4D});

        String tsvContents = Joiner.on("\n").join(new String[]{TSV_HEADER_DIFFERENT_ORDER, TSV_LINE_DIFFERENT_ORDER});
        Reader tsvSource = new StringReader(tsvContents);

        CSVReader csvReader = CsvReaderFactory.createTsvReader(tsvSource);
        RnaSeqBaselineTranscriptReader subject = new RnaSeqBaselineTranscriptReader(csvReader, "Test", ORDERED_ASSAY_GROUP_IDS);

        assertThat(subject.next(), is(TRANSCRIPT_DIFFERENT_ORDER));
        assertThat(subject.hasNext(), is(false));
    }

    @Test
    public void tryResourcesClosesUnderlyingReaderWhenFinished() throws IOException {
        Reader tsvSource = spy(new StringReader(TSV_CONTENTS));
        CSVReader csvReader = CsvReaderFactory.createTsvReader(tsvSource);

        try (RnaSeqBaselineTranscriptReader subject = new RnaSeqBaselineTranscriptReader(csvReader, "Test", ORDERED_ASSAY_GROUP_IDS)) {
            subject.next();
        }

        verify(tsvSource).close();
    }

    @Test
    public void tryResourcesAutoClosesUnderlyingReaderOnException() throws IOException {
        Reader tsvSource = spy(new StringReader(TSV_CONTENTS));
        CSVReader csvReader = CsvReaderFactory.createTsvReader(tsvSource);

        try (RnaSeqBaselineTranscriptReader subject = new RnaSeqBaselineTranscriptReader(csvReader, "Test", ORDERED_ASSAY_GROUP_IDS)) {
            subject.next();
            throw new RuntimeException("foobar");
        } catch (RuntimeException e) {
            // ignore
        }

        verify(tsvSource).close();
    }

}
