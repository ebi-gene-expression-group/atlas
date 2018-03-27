package uk.ac.ebi.atlas.model.experimentimport.analytics.baseline;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.RnaSeqBaselineAnalyticsInputStream;

import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

public class RnaSeqBaselineAnalyticsInputStreamTest {

    private static final String GENE_ID_1 = "ENSMUSG00000030105";
    private static final String GENE_ID_2 = "ENSG00000127720";
    private static final String GENE_NAME_1 = "Arl8b";
    private static final String GENE_NAME_2 = "METTL25";

    private static final String TSV_HEADER =
            Stream.of("Gene ID", "Gene Name", "g1", "g2", "g3", "g4", "g5").collect(joining("\t"));

    private static final String TSV_LINE_1_TPM =
            Stream.of(GENE_ID_1, GENE_NAME_1, "1", "2", "3", "4", "0.5").collect(joining("\t"));
    private static final String TSV_LINE_2_TPM =
            Stream.of(GENE_ID_2, GENE_NAME_2, "0.00", "0.1", "1", "0", "1").collect(joining("\t"));
    private static String TSV_CONTENTS_TPM =
            Stream.of(TSV_HEADER, TSV_LINE_1_TPM, TSV_LINE_2_TPM).collect(joining("\n"));

    private static final String TSV_LINE_1_FPKM =
            Stream.of(GENE_ID_1, GENE_NAME_1, "0.5", "0.8", "2", "2.9", "0.1").collect(joining("\t"));
    private static final String TSV_LINE_2_FPKM =
            Stream.of(GENE_ID_2, GENE_NAME_2, "0.00", "0", "0.5", "0", "0.5").collect(joining("\t"));
    private static String TSV_CONTENTS_FPKM =
            Stream.of(TSV_HEADER, TSV_LINE_1_FPKM, TSV_LINE_2_FPKM).collect(joining("\n"));

    private static final String NOT_REALLY_BAD_TSV_HEADER =
            Stream.of("GeneID", "GeneName", "g1", "g2", "g3", "g4", "g5").collect(joining("\t"));
    private static String TSV_CONTENTS_FPKM_NOT_REALLY_BAD_HEADER =
            Stream.of(NOT_REALLY_BAD_TSV_HEADER, TSV_LINE_1_FPKM, TSV_LINE_2_FPKM).collect(joining("\n"));

    private static final String BAD_TSV_HEADER =
            Stream.of("Gene ID", "Gene Name", "g1", "g2", "g3", "g5", "g4").collect(joining("\t"));
    private static String TSV_CONTENTS_FPKM_BAD_HEADER =
            Stream.of(BAD_TSV_HEADER, TSV_LINE_1_FPKM, TSV_LINE_2_FPKM).collect(joining("\n"));

    private static String TSV_CONTENTS_FPKM_BAD_GENE_IDS =
            Stream.of(TSV_HEADER, TSV_LINE_2_FPKM, TSV_LINE_1_FPKM).collect(joining("\n"));

    private static String TSV_CONTENTS_FPKM_ONE_LINE =
            Stream.of(TSV_HEADER, TSV_LINE_1_FPKM).collect(joining("\n"));

    private static final String TSV_LINE_NO_EXPRESSION =
            Stream.of(GENE_ID_1, GENE_NAME_1, "0", "0", "0", "0", "0").collect(joining("\t"));
    private static final String TSV_CONTENTS_TPM_WITH_ZERO_LINE =
            Stream.of(TSV_HEADER, TSV_LINE_NO_EXPRESSION, TSV_LINE_2_TPM).collect(joining("\n"));
    private static final String TSV_CONTENTS_FPKM_WITH_ZERO_LINE =
            Stream.of(TSV_HEADER, TSV_LINE_NO_EXPRESSION, TSV_LINE_2_FPKM).collect(joining("\n"));


    private static final String TSV_LINE_QUARTILES =
            Stream.of(
                    GENE_ID_1, GENE_NAME_1, "1,1,1,1,1", "2,2,2,2,2", "3,3,3,3,3", "4,4,4,4,4", "0.5,0.5,0.5,0.5,0.5")
                    .collect(joining("\t"));

    @Test
    public void skipsZeroes() {
        try (RnaSeqBaselineAnalyticsInputStream subject =
                     new RnaSeqBaselineAnalyticsInputStream(
                             Optional.of(new StringReader(TSV_CONTENTS_TPM)),
                             Optional.of(new StringReader(TSV_CONTENTS_FPKM)))) {
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g1", 1.0, 0.5));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g2", 2.0, 0.8));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g3", 3.0, 2.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g4", 4.0, 2.9));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g5", 0.5, 0.1));

            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g2", 0.1, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g3", 1.0, 0.5));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g5", 1.0, 0.5));
        }
    }

    @Test
    public void readTpmsOnly() {
        try (RnaSeqBaselineAnalyticsInputStream subject =
                     new RnaSeqBaselineAnalyticsInputStream(
                             Optional.of(new StringReader(TSV_CONTENTS_TPM)),
                             Optional.empty())) {
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g1", 1.0, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g2", 2.0, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g3", 3.0, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g4", 4.0, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g5", 0.5, 0.0));

            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g2", 0.1, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g3", 1.0, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g5", 1.0, 0.0));
        }
    }

    @Test
    public void readFpkmsOnly() {
        try (RnaSeqBaselineAnalyticsInputStream subject =
                     new RnaSeqBaselineAnalyticsInputStream(
                             Optional.empty(),
                             Optional.of(new StringReader(TSV_CONTENTS_FPKM)))) {
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g1", 0.0, 0.5));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g2", 0.0, 0.8));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g3", 0.0, 2.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g4", 0.0, 2.9));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g5", 0.0, 0.1));

            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g3", 0.0, 0.5));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g5", 0.0, 0.5));
        }
    }

    @Test
    public void skipFullZeroLines() {
        try (RnaSeqBaselineAnalyticsInputStream subject =
                     new RnaSeqBaselineAnalyticsInputStream(
                             Optional.of(new StringReader(TSV_CONTENTS_TPM_WITH_ZERO_LINE)),
                             Optional.of(new StringReader(TSV_CONTENTS_FPKM_WITH_ZERO_LINE)))) {
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g2", 0.1, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g3", 1.0, 0.5));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g5", 1.0, 0.5));
        }
    }

    @Test
    public void backingReadersAreAutoclosed() {
        StringReader readerTpmsSpy = Mockito.spy(new StringReader(TSV_CONTENTS_TPM));
        StringReader readerFpkmsSpy = Mockito.spy(new StringReader(TSV_CONTENTS_FPKM));

        try (RnaSeqBaselineAnalyticsInputStream subject =
                     new RnaSeqBaselineAnalyticsInputStream(Optional.of(readerTpmsSpy), Optional.of(readerFpkmsSpy))) {
            while(subject.readNext() != null) {

            }
        }

        verify(readerTpmsSpy).close();
        verify(readerFpkmsSpy).close();
    }

    @Test
    public void ignoresHeaderFieldsWhichAreNotAssayGroupIds() {
        try (RnaSeqBaselineAnalyticsInputStream subject =
                     new RnaSeqBaselineAnalyticsInputStream(
                             Optional.of(new StringReader(TSV_CONTENTS_TPM)),
                             Optional.of(new StringReader(TSV_CONTENTS_FPKM_NOT_REALLY_BAD_HEADER)))) {
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g1", 1.0, 0.5));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g2", 2.0, 0.8));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g3", 3.0, 2.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g4", 4.0, 2.9));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_1, "g5", 0.5, 0.1));

            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g2", 0.1, 0.0));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g3", 1.0, 0.5));
            assertThat(subject.readNext()).isEqualTo(BaselineAnalytics.create(GENE_ID_2, "g5", 1.0, 0.5));
        }
    }

    @Ignore
    public void ioExceptionsAreWrapped() {
        // Beacuse RnaSeqBaselineAnalyticsInputStream takes a Reader, sends it to TsvStream, which in turns wraps it in
        // a BufferedReader makes this case very hard to test, even using mocks. Left as an exercise to a future Atlas
        // developer with enough time to spare and better skills. :(
    }

    @Test
    public void throwsIfReadersAreEmpty() {
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    try (RnaSeqBaselineAnalyticsInputStream subject =
                                 new RnaSeqBaselineAnalyticsInputStream(Optional.empty(), Optional.empty())) {
                        // Use subject
                    }
                });
    }

    @Test
    public void throwsIfHeadersDontMatch() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(
                        () -> {
                            try (RnaSeqBaselineAnalyticsInputStream subject =
                                         new RnaSeqBaselineAnalyticsInputStream(
                                                 Optional.of(new StringReader(TSV_CONTENTS_TPM)),
                                                 Optional.of(new StringReader(TSV_CONTENTS_FPKM_BAD_HEADER)))) {
                                // Use subject
                            }
                        })
                .withMessage("TPMs and FPKMs header lines don’t match!");
    }

    @Test
    public void throwsIfGeneIdsDontMatch() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(
                        () -> {
                            try (RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(
                                    Optional.of(new StringReader(TSV_CONTENTS_TPM)),
                                    Optional.of(new StringReader(TSV_CONTENTS_FPKM_BAD_GENE_IDS)))) {
                                while (subject.readNext() != null) {

                                }
                            }
                        })
                .withMessageStartingWith("Gene IDs ")
                .withMessageEndingWith(" in the same line of TPM and FPKM file don’t match");
    }

    @Test
    public void throwsIfNumberOfLinesDontMatch() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(
                        () -> {
                            try (RnaSeqBaselineAnalyticsInputStream subject = new RnaSeqBaselineAnalyticsInputStream(
                                    Optional.of(new StringReader(TSV_CONTENTS_TPM)),
                                    Optional.of(new StringReader(TSV_CONTENTS_FPKM_ONE_LINE)))) {
                                while (subject.readNext() != null) {

                                }
                            }
                        })
                .withMessageStartingWith("Number of lines of FPKM and TPM files don’t match");
    }

}
