package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class BaselineExpressionsInputStreamFactoryIT extends ObjectInputStreamer {

    @Inject
    BaselineExpressionsInputStreamFactory subject;

    @Inject
    ExperimentTrader experimentTrader;

    @Override
    protected ObjectInputStream<?> createStream(String experimentAccession) throws IOException {
        return subject.createGeneExpressionsInputStream(experimentTrader.getPublicExperiment(experimentAccession));
    }

    @Test
    public void testThreadSafe() throws Exception {
        testMultithreaded("E-MTAB-2037", 10,10);
        testSinglethreaded("E-PROT-1",1);
        testMultithreaded("E-PROT-1", 10,10);
    }
}