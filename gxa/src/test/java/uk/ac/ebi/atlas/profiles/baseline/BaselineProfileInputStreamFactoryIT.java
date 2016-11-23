package uk.ac.ebi.atlas.profiles.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.ObjectInputStreamer;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.ExpressionProfileInputStream;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.File;
import java.text.MessageFormat;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/oracleContext.xml"})
public class BaselineProfileInputStreamFactoryIT {

    private final String E_MTAB_513 = "E-MTAB-513";
    private final String DEFAULT_QUERY_FACTOR = "ORGANISM_PART";

    @Inject
    private ExpressionSerializerService expressionSerializerService;

    @Inject
    ExperimentTrader experimentTrader;

    @Value("#{configuration['experiment.kryo_expressions.path.template']}")
    private String baselineExperimentSerializedDataFileUrlTemplate;

    @Inject
    @Qualifier("baselineProfileInputStreamFactory")
    private BaselineProfileInputStreamFactory subject;

    @Before
    public void setUp() {
        expressionSerializerService.kryoSerializeExpressionData(E_MTAB_513);
    }

    @Test
    public void experimentWithoutSerializedFileIsReadFromTsvFile() throws Exception {
        String serializedFileURL = MessageFormat.format(baselineExperimentSerializedDataFileUrlTemplate, E_MTAB_513);
        String temporaryFileURL = serializedFileURL + ".bak";

        File serializedFile = new File(serializedFileURL);
        File temporaryFile = new File(temporaryFileURL);

        serializedFile.renameTo(temporaryFile);

        ExpressionProfileInputStream<BaselineProfile, BaselineExpression> inputStream = subject
                .createBaselineProfileInputStream((BaselineExperiment) experimentTrader.getPublicExperiment(E_MTAB_513),
                        DEFAULT_QUERY_FACTOR, 0.1, new
                        HashSet<Factor>());
        assertThat(inputStream, instanceOf(BaselineProfilesTsvInputStream.class));

        temporaryFile.renameTo(serializedFile);
    }

    @Test
    public void testThreadSafe(){
        //new ThreadSafeTester().testSinglethreaded("E-PROT-1",1);
        new ThreadSafeTester().testMultithreaded("E-MTAB-513", 10,10);
        //new ThreadSafeTester().testMultithreaded("E-PROT-1", 10,10);
    }

    class ThreadSafeTester extends ObjectInputStreamer {

        @Override
        protected ObjectInputStream<?> createStream(String experimentAccession) throws Exception {
            return subject.createBaselineProfileInputStream((BaselineExperiment) experimentTrader.getPublicExperiment(E_MTAB_513), DEFAULT_QUERY_FACTOR, 0.1, new HashSet<Factor>());
        }


    }
}