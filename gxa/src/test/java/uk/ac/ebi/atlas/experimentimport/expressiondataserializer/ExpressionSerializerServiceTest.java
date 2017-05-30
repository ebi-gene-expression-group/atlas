package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ExpressionSerializerServiceTest {

    String accession = "accession";

    MicroarrayProfileStreamFactory microarrayProfileStreamFactory;
    RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;
    RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory;
    ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory;
    MockDataFileHub dataFileHub;


    ExpressionSerializerService subject;


    @Before
    public void setUp() throws IOException {
        dataFileHub = MockDataFileHub.get();
        microarrayProfileStreamFactory = new MicroarrayProfileStreamFactory(dataFileHub);
        rnaSeqProfileStreamFactory = new RnaSeqProfileStreamFactory(dataFileHub);
        rnaSeqBaselineProfileStreamFactory = Mockito.spy(new RnaSeqBaselineProfileStreamFactory(dataFileHub));
        proteomicsBaselineProfileStreamFactory = new ProteomicsBaselineProfileStreamFactory(dataFileHub);

        subject = new ExpressionSerializerService(
                microarrayProfileStreamFactory,
                rnaSeqProfileStreamFactory,
                rnaSeqBaselineProfileStreamFactory,
                proteomicsBaselineProfileStreamFactory,
                dataFileHub);
    }

    @Test
    public void persistTwoFilesForRnaSeqBaseline(){

        dataFileHub.addTpmsExpressionFile(accession, ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", "1.23"}
        ));
        dataFileHub.addFpkmsExpressionFile(accession, ImmutableList.of(
                new String[]{"Gene ID", "Gene name", "g1"},
                new String[]{"id_1", "name_1", "4.56"}
        ));

        AssayGroup assayGroup = new AssayGroup("g1", "r1");

        BaselineExperiment experiment = mock(BaselineExperiment.class);
        when(experiment.getAccession()).thenReturn(accession);
        when(experiment.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        when(experiment.getDataColumnDescriptors()).thenReturn(ImmutableList.of(assayGroup));
        when(experiment.getDataColumnDescriptor("g1")).thenReturn(assayGroup);
        subject.kryoSerializeExpressionData(experiment);

        verify(rnaSeqBaselineProfileStreamFactory, times(2)).persist(eq(experiment), Matchers.any(BaselineProfileStreamOptions.class));

    }

}
