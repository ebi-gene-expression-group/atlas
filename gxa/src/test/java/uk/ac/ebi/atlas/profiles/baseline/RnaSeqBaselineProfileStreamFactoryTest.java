package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqBaselineProfileStreamFactoryTest {


    @Mock
    BaselineExperiment baselineExperiment;

    MockDataFileHub dataFileHub;

    RnaSeqBaselineProfileStreamFactory subject;

    @Before
    public void setUp() throws Exception{
        when(baselineExperiment.getAccession()).thenReturn("accession");
        when(baselineExperiment.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        dataFileHub = MockDataFileHub.get();
        dataFileHub.addFpkmsExpressionFile(baselineExperiment.getAccession(), ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", "1.337"}
        ));
        dataFileHub.addFpkmsExpressionFile(baselineExperiment.getAccession(), ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", "42.0"}
        ));

        subject = new RnaSeqBaselineProfileStreamFactory(dataFileHub);
    }



    @Test
    public void testCreate() throws Exception {

        RnaSeqBaselineRequestPreferences rnaSeqBaselineRequestPreferences = new RnaSeqBaselineRequestPreferences();
        rnaSeqBaselineRequestPreferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);

        subject.create(baselineExperiment, new BaselineRequestContext<>(rnaSeqBaselineRequestPreferences, baselineExperiment));


    }
}