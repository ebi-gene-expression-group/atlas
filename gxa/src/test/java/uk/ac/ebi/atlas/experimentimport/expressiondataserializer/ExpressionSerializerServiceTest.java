package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentChecker;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ExpressionSerializerServiceTest {

    static final String accession = "E-MTAB-513";

    @Mock
    ExperimentChecker experimentChecker;

    @Mock
    RnaSeqBaselineExpressionKryoSerializer rnaSeqBaselineExpressionKryoSerializer;

    ExpressionSerializerService subject;


    @Before
    public void setUp() throws IOException {
        subject = new ExpressionSerializerService(rnaSeqBaselineExpressionKryoSerializer, experimentChecker);
    }

    @Test
    public void weCheckTheFileExistsBeforeWeAttemptToSerializeIt(){
        subject.kryoSerializeExpressionData(accession, ExperimentType.RNASEQ_MRNA_BASELINE);
        verify(experimentChecker).checkAllFiles(eq(accession), any(ExperimentType.class));
    }

    @Test
    public void skipIffNotRnaSeqBaseline(){
        for(ExperimentType experimentType : ExperimentType.values()){
            String result = subject.kryoSerializeExpressionData(accession, experimentType);
            assertThat("skipped".equals(result), is(experimentType != ExperimentType.RNASEQ_MRNA_BASELINE));
        }
    }

    @Test
    public void delegateToRnaSeqBaselineSerializer(){
        for(ExperimentType experimentType : ExperimentType.values()){
            subject.kryoSerializeExpressionData(accession, experimentType);
            int timesExpected = experimentType == ExperimentType.RNASEQ_MRNA_BASELINE ? 1 : 0;
            verify(rnaSeqBaselineExpressionKryoSerializer, times(timesExpected)).serializeExpressionData(accession, ExpressionUnit.Absolute.Rna.TPM);
            reset(rnaSeqBaselineExpressionKryoSerializer);
        }
    }

}
