package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineAnalyticsInputStreamFactoryTest {

    private static final String EXPERIMENT_ACCESSION_RNASEQ_TPMS = "E-FOOBAR-TPMS";
    private static final String EXPERIMENT_ACCESSION_RNASQ_FPKMS = "E-FOOBAR-FPKMS";
    private static final String EXPERIMENT_ACCESSION_PROTEOMICS = "E-FOOBAR-PROT";
    private static final String EXPERIMENT_ACCESSION_NO_TPMS_FPKMS = "E-FOOBAR";

    private BaselineAnalyticsInputStreamFactory subject;

    @Before
    public void setUp() throws Exception {
        MockDataFileHub dataFileHub = MockDataFileHub.create();
        // “Contents” of files aren’t read
        dataFileHub.addTpmsExpressionFile(EXPERIMENT_ACCESSION_RNASEQ_TPMS, ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", "4.56"}));
        dataFileHub.addProteomicsExpressionFile(EXPERIMENT_ACCESSION_PROTEOMICS, ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1.WithInSampleAbundance"},
                new String[]{"id_1", "name_1", "0.0000000001"}));
        dataFileHub.addFpkmsExpressionFile(EXPERIMENT_ACCESSION_RNASQ_FPKMS, ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", "4.56"}));
        subject = new BaselineAnalyticsInputStreamFactory(dataFileHub);
    }

    @Test
    public void createForRnaSeqBaselineWithTpmsOnly() throws Exception {
        Object objectInputStream =
                subject.create(EXPERIMENT_ACCESSION_RNASEQ_TPMS, ExperimentType.RNASEQ_MRNA_BASELINE);
        assertThat(objectInputStream, is(instanceOf(RnaSeqBaselineAnalyticsInputStream.class)));
    }

    @Test
    public void createRnaSeqBaselineWithFpkmsOnly() throws Exception {
        subject.create(EXPERIMENT_ACCESSION_RNASQ_FPKMS, ExperimentType.RNASEQ_MRNA_BASELINE);
    }

    @Test
    public void createForProteomics() throws Exception {
        ObjectInputStream<BaselineAnalytics> objectInputStream =
                subject.create(EXPERIMENT_ACCESSION_PROTEOMICS, ExperimentType.PROTEOMICS_BASELINE);
        assertThat(objectInputStream, is(instanceOf(ProteomicsBaselineAnalyticsInputStream.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createRnaSeqBaselineWithNoFpkmsOrTpmsThrows() throws Exception {
        subject.create(EXPERIMENT_ACCESSION_NO_TPMS_FPKMS, ExperimentType.RNASEQ_MRNA_BASELINE);
    }
}
