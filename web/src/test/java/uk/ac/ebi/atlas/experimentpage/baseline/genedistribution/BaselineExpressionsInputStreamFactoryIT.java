package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreamer;

import javax.inject.Inject;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExpressionsInputStreamFactoryIT extends ObjectInputStreamer{

    @Inject
    BaselineExpressionsInputStreamFactory subject;

    @Override
    protected ObjectInputStream<?> createStream(String experimentAccession) {
        return subject.createGeneExpressionsInputStream(experimentAccession);
    }

    @Test
    public void testThreadSafe(){
        testMultithreaded("E-MTAB-2037", 10,10);
        testSinglethreaded("E-PROT-1",1);
        testMultithreaded("E-PROT-1", 10,10);
    }
}