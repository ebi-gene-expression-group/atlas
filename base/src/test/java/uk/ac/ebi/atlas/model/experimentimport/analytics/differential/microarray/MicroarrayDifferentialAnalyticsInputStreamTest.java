package uk.ac.ebi.atlas.model.experimentimport.analytics.differential.microarray;

import com.google.common.base.Joiner;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStream;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MicroarrayDifferentialAnalyticsInputStreamTest {

    private static final String CONTRAST_ID_1 = "g2_g3";
    private static final String CONTRAST_ID_2 = "g2_g1";
    private static final String GENE_ID_1 = "GENE_ID_1";
    private static final String GENE_ID_2 = "GENE_ID_2";
    private static final String GENE_NAME_1 = "GENE_NAME_1";
    private static final String GENE_NAME_2 = "GENE_NAME_2";
    private static final String DESIGN_ELEMENT_1 = "DESIGN_ELEMENT_1";
    private static final String DESIGN_ELEMENT_2 = "DESIGN_ELEMENT_2";
    private static final String NA = "NA";
    private static final String INF = "inf";
    private static final String NEGATIVE_INF = "-inf";
    private static final double P_VALUE_1 = 0.974206814501029;
    private static final double TSTAT_1 = 0.44248894656976;
    private static final double FOLD_CHANGE_1 = 0.0761328916666661;
    private static final double P_VALUE_2 = 0.979446913114856;
    private static final double TSTAT_2 = -0.241543364267522;
    private static final double FOLD_CHANGE_2 = -0.0282126666666667;

    private static final String TSV_HEADER_1 =
            Joiner.on("\t").join(new String[] {
                    "Gene ID", "Gene Name", "Design Element",
                    CONTRAST_ID_1 + ".p-value", CONTRAST_ID_1 + ".t-statistic", CONTRAST_ID_1 + ".log2foldchange"});
    private static final String TSV_LINE_1 =
            Joiner.on("\t").join(new String[] {
                    GENE_ID_1, GENE_NAME_1, DESIGN_ELEMENT_1, "" + P_VALUE_1, "" + TSTAT_1, "" + FOLD_CHANGE_1});
    private static final String TSV_LINE_2 =
            Joiner.on("\t").join(new String[] {
                    GENE_ID_2, GENE_NAME_2, DESIGN_ELEMENT_2, "" + P_VALUE_2, "" + TSTAT_2, "" + FOLD_CHANGE_2});

    private static final String TSV_HEADER_2_CONTRASTS =
            Joiner.on("\t").join(new String[] {
                    "Gene ID", "Gene Name", "Design Element",
                    CONTRAST_ID_1 + ".p-value", CONTRAST_ID_1 + ".t-statistic", CONTRAST_ID_1 + ".log2foldchange",
                    CONTRAST_ID_2 + ".p-value", CONTRAST_ID_2 + ".t-statistic", CONTRAST_ID_2 + ".log2foldchange"});
    private static final String TSV_LINE_2_CONTRASTS =
            Joiner.on("\t").join(new String[] {
                    GENE_ID_1, GENE_NAME_1, DESIGN_ELEMENT_1,
                    "" + P_VALUE_1, "" + TSTAT_1, "" + FOLD_CHANGE_1,
                    "" + P_VALUE_2, "" + TSTAT_2, "" + FOLD_CHANGE_2});

    private static final String TSV_LINE_1_NA =
            Joiner.on("\t").join(new String[] {
                    GENE_ID_1, GENE_NAME_1, DESIGN_ELEMENT_1, "" + P_VALUE_1, NA, "" + FOLD_CHANGE_1});

    private static final String TSV_LINE_1_ZERO =
            Joiner.on("\t").join(new String[] {
                    GENE_ID_1, GENE_NAME_1, DESIGN_ELEMENT_1, "" + P_VALUE_1, "" + TSTAT_1, "" + 0});

    private static final String TSV_LINE_1_INF =
            Joiner.on("\t").join(new String[] {
                    GENE_ID_1, GENE_NAME_1, DESIGN_ELEMENT_1, "" + P_VALUE_1, INF, "" + FOLD_CHANGE_1});

    private static final String TSV_LINE_2_NEGATIVE_INF =
            Joiner.on("\t").join(new String[] {
                    GENE_ID_2, GENE_NAME_2, DESIGN_ELEMENT_2, "" + P_VALUE_2, NEGATIVE_INF, "" + FOLD_CHANGE_2});

    private static final String TSV_MANY_NA_LINES = Joiner.on("\n").join(Collections.nCopies(5000, TSV_LINE_1_NA));

    private static String tsvContents1Contrast =
            Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1, TSV_LINE_2});
    private static String tsvContents2Contrasts =
            Joiner.on("\n").join(new String[]{TSV_HEADER_2_CONTRASTS, TSV_LINE_2_CONTRASTS});
    private static String tsvContents1ContrastNA =
            Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1_NA, TSV_LINE_2});
    private static String tsvContents1ContrastZero =
            Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1_ZERO, TSV_LINE_2});
    private static String tsvContents1ContrastINF =
            Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1_INF, TSV_LINE_2_NEGATIVE_INF});

    private static final String TSV_CONTENTS_MANY_NAS =
            Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_MANY_NA_LINES, TSV_LINE_2});


    @Test
    public void readOneContrastTsv() {
        Reader reader = new StringReader(tsvContents1Contrast);
        MicroarrayDifferentialAnalyticsInputStream subject =
                new MicroarrayDifferentialAnalyticsInputStream(reader, "Test");

        MicroarrayDifferentialAnalytics dto1 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_1, DESIGN_ELEMENT_1, CONTRAST_ID_1, P_VALUE_1, FOLD_CHANGE_1, TSTAT_1);
        MicroarrayDifferentialAnalytics dto2 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_2, DESIGN_ELEMENT_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2, TSTAT_2);

        assertThat(subject.readNext(), is(dto1));
        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readTwoContrastTsv() {
        Reader reader = new StringReader(tsvContents2Contrasts);
        MicroarrayDifferentialAnalyticsInputStream subject =
                new MicroarrayDifferentialAnalyticsInputStream(reader, "Test");

        MicroarrayDifferentialAnalytics dto1 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_1, DESIGN_ELEMENT_1, CONTRAST_ID_1, P_VALUE_1, FOLD_CHANGE_1, TSTAT_1);
        MicroarrayDifferentialAnalytics dto2 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_1, DESIGN_ELEMENT_1, CONTRAST_ID_2, P_VALUE_2, FOLD_CHANGE_2, TSTAT_2);

        assertThat(subject.readNext(), is(dto1));
        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastContainingNA() {
        Reader reader = new StringReader(tsvContents1ContrastNA);
        MicroarrayDifferentialAnalyticsInputStream subject =
                new MicroarrayDifferentialAnalyticsInputStream(reader, "Test");

        MicroarrayDifferentialAnalytics dto2 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_2, DESIGN_ELEMENT_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2, TSTAT_2);

        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastWithZeroFoldChange() {
        Reader reader = new StringReader(tsvContents1ContrastZero);
        MicroarrayDifferentialAnalyticsInputStream subject =
                new MicroarrayDifferentialAnalyticsInputStream(reader, "Test");

        MicroarrayDifferentialAnalytics dto2 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_2, DESIGN_ELEMENT_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2, TSTAT_2);

        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastContainingManyNAsWithoutStackOverflow() {
        Reader reader = new StringReader(TSV_CONTENTS_MANY_NAS);
        MicroarrayDifferentialAnalyticsInputStream subject =
                new MicroarrayDifferentialAnalyticsInputStream(reader, "Test");

        MicroarrayDifferentialAnalytics dto2 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_2, DESIGN_ELEMENT_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2, TSTAT_2);

        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastWithInf() {
        Reader reader = new StringReader(tsvContents1ContrastINF);
        MicroarrayDifferentialAnalyticsInputStream subject =
                new MicroarrayDifferentialAnalyticsInputStream(reader, "Test");

        MicroarrayDifferentialAnalytics dto1 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_1, DESIGN_ELEMENT_1, CONTRAST_ID_1, P_VALUE_1, FOLD_CHANGE_1,
                        Double.POSITIVE_INFINITY);
        MicroarrayDifferentialAnalytics dto2 =
                new MicroarrayDifferentialAnalytics(
                        GENE_ID_2, DESIGN_ELEMENT_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2,
                        Double.NEGATIVE_INFINITY);

        assertThat(subject.readNext(), is(dto1));
        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void tryResourcesClosesUnderlyingReaderWhenFinished() throws IOException {
        Reader reader = spy(new StringReader(tsvContents1Contrast));

        try (MicroarrayDifferentialAnalyticsInputStream subject =
                     new MicroarrayDifferentialAnalyticsInputStream(reader, "Test")) {
            subject.readNext();
        }

        verify(reader).close();
    }

    @Test
    public void tryResourcesAutoClosesUnderlyingReaderOnException() throws IOException {
        Reader reader = spy(new StringReader(tsvContents1Contrast));

        try (MicroarrayDifferentialAnalyticsInputStream subject =
                     new MicroarrayDifferentialAnalyticsInputStream(reader, "Test")) {
            subject.readNext();
            throw new RuntimeException("foobar");
        } catch (RuntimeException e) {
            // ignore
        }

        verify(reader).close();
    }
}
