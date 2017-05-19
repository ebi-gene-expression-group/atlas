package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.RnaSeqBaselineExpressionKryoSerializer;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqBaselineProfileStreamFactoryTest {


    @Mock
    BaselineExperiment baselineExperiment;

    AssayGroup assayGroup = new AssayGroup("g1", "r1");

    MockDataFileHub dataFileHub;

    RnaSeqBaselineProfileStreamFactory subject;

    @Before
    public void setUp() throws Exception {
        when(baselineExperiment.getAccession()).thenReturn("accession");
        when(baselineExperiment.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        when(baselineExperiment.getDataColumnDescriptors()).thenReturn(ImmutableList.of(assayGroup));
        when(baselineExperiment.getDataColumnDescriptor("g1")).thenReturn(assayGroup);
        dataFileHub = MockDataFileHub.get();


        subject = new RnaSeqBaselineProfileStreamFactory(dataFileHub);
    }

    private void setExpressionValuesTpmAndFpkm(Double tpm, Double fpkm){
        dataFileHub.addTpmsExpressionFile(baselineExperiment.getAccession(), ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", tpm.toString()}
        ));
        dataFileHub.addFpkmsExpressionFile(baselineExperiment.getAccession(), ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", fpkm.toString()}
        ));
    }


    @Test
    public void tpmsAndFpkmsAreDifferentFiles() throws Exception {
        setExpressionValuesTpmAndFpkm(42.0, 1.337);

        RnaSeqBaselineRequestPreferences rnaSeqBaselineRequestPreferences = new RnaSeqBaselineRequestPreferences();
        rnaSeqBaselineRequestPreferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);
        ObjectInputStream<BaselineProfile> resultTpms = subject.create(baselineExperiment, new BaselineRequestContext<>(rnaSeqBaselineRequestPreferences, baselineExperiment));
        rnaSeqBaselineRequestPreferences.setUnit(ExpressionUnit.Absolute.Rna.FPKM);
        ObjectInputStream<BaselineProfile> resultFpkms = subject.create(baselineExperiment, new BaselineRequestContext<>(rnaSeqBaselineRequestPreferences, baselineExperiment));

        assertThat(resultTpms.readNext().getExpressionLevel(assayGroup), is(42.0));

        assertThat(resultFpkms.readNext().getExpressionLevel(assayGroup), is(1.337));
    }

    @Test
    public void readFromKryoFile() {
        //given the right value can only be read off from kryo files
        ExpressionUnit.Absolute.Rna unit = ExpressionUnit.Absolute.Rna.TPM;
        new RnaSeqBaselineExpressionKryoSerializer(dataFileHub).delete(baselineExperiment.getAccession(), unit);
        setExpressionValuesTpmAndFpkm(13.37, 13.37);
        new RnaSeqBaselineExpressionKryoSerializer(dataFileHub).serializeExpressionData(baselineExperiment.getAccession(), unit);
        setExpressionValuesTpmAndFpkm(0.0, 0.0);

        RnaSeqBaselineRequestPreferences rnaSeqBaselineRequestPreferences = new RnaSeqBaselineRequestPreferences();
        rnaSeqBaselineRequestPreferences.setUnit(unit);
        ObjectInputStream<BaselineProfile> result = subject.create(baselineExperiment, new BaselineRequestContext<>(rnaSeqBaselineRequestPreferences, baselineExperiment));


        assertThat(result.readNext().getExpressionLevel(assayGroup), is(13.37));
    }

}