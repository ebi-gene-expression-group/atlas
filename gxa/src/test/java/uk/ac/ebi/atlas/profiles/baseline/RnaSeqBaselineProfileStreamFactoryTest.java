package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
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
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqBaselineProfileStreamFactoryTest {


    @Mock
    BaselineExperiment baselineExperiment;

    AssayGroup assayGroup = new AssayGroup("g1", "r1");
    AssayGroup secondAssayGroup = new AssayGroup("g2", "r2");

    MockDataFileHub dataFileHub;

    RnaSeqBaselineProfileStreamFactory subject;


    @Mock
    BaselineExperiment twoAssayGroupBaselineExperiment;


    @Before
    public void setUp() throws Exception {
        when(baselineExperiment.getAccession()).thenReturn("accession");
        when(baselineExperiment.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        when(baselineExperiment.getDataColumnDescriptors()).thenReturn(ImmutableList.of(assayGroup));
        when(baselineExperiment.getDataColumnDescriptor("g1")).thenReturn(assayGroup);

        when(twoAssayGroupBaselineExperiment.getAccession()).thenReturn("second_accession");
        when(twoAssayGroupBaselineExperiment.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        when(twoAssayGroupBaselineExperiment.getDataColumnDescriptors()).thenReturn(ImmutableList.of(assayGroup, secondAssayGroup));
        when(twoAssayGroupBaselineExperiment.getDataColumnDescriptor("g1")).thenReturn(assayGroup);
        when(twoAssayGroupBaselineExperiment.getDataColumnDescriptor("g2")).thenReturn(secondAssayGroup);

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

    @Test
    public void tpmFilesAndFpkmFilesNeedNotHaveColumnsInTheSameOrder(){
        dataFileHub.addTpmsExpressionFile(twoAssayGroupBaselineExperiment.getAccession(), ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1", "g2"},
                new String[]{"id_1", "name_1", "1.0", "2.0"}
        ));
        dataFileHub.addFpkmsExpressionFile(twoAssayGroupBaselineExperiment.getAccession(), ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g2", "g1"},
                new String[]{"id_1", "name_1", "2.1", "1.1"}
        ));

        RnaSeqBaselineRequestPreferences rnaSeqBaselineRequestPreferences = new RnaSeqBaselineRequestPreferences();
        rnaSeqBaselineRequestPreferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);
        BaselineProfile resultTpms = subject.create(twoAssayGroupBaselineExperiment, new BaselineRequestContext<>(rnaSeqBaselineRequestPreferences, twoAssayGroupBaselineExperiment)).readNext();
        rnaSeqBaselineRequestPreferences.setUnit(ExpressionUnit.Absolute.Rna.FPKM);
        BaselineProfile resultFpkms = subject.create(twoAssayGroupBaselineExperiment, new BaselineRequestContext<>(rnaSeqBaselineRequestPreferences, twoAssayGroupBaselineExperiment)).readNext();


        assertThat(resultTpms.getExpressionLevel(assayGroup), is(1.0));
        assertThat(resultTpms.getExpressionLevel(secondAssayGroup), is(2.0));

        assertThat(resultFpkms.getExpressionLevel(assayGroup), is(1.1));
        assertThat(resultFpkms.getExpressionLevel(secondAssayGroup), is(2.1));

    }

}