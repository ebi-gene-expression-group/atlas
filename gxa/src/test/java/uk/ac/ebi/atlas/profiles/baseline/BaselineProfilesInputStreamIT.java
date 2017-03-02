package uk.ac.ebi.atlas.profiles.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/oracleContext.xml"})
public class BaselineProfilesInputStreamIT {

    public static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    @Inject
    private ExpressionSerializerService expressionSerializerService;

    @Inject
    ExperimentTrader experimentTrader;

    @Resource(name = "baselineProfileInputStreamFactory")
    private BaselineProfileStreamFactory inputStreamFactory;

    private ObjectInputStream<BaselineProfile> subject;

    private Set<Factor> noFilterFactors = Collections.emptySet();

    @Before
    public void setUp() throws Exception {
        expressionSerializerService.kryoSerializeExpressionData(EXPERIMENT_ACCESSION);
        double defaultCutoff = 0.5;
        setUp(defaultCutoff);
    }

    public void setUp(double cutoff) throws Exception {
        String queryFactorType = "ORGANISM_PART";
        subject =
                inputStreamFactory.createBaselineProfileInputStream(
                        (BaselineExperiment) experimentTrader.getPublicExperiment(EXPERIMENT_ACCESSION),
                        queryFactorType, cutoff,
                        noFilterFactors);
    }

    @Test
    public void readNextShouldReturnFalseGivenAllExpressionLevelsHaveBeenRead() throws Exception {
        long countProfiles = 0;
        while (subject.readNext() != null) {
            ++countProfiles;
        }
        assertThat(countProfiles, greaterThan(10L));

        setUp(5);
        long countProfiles2 = 0;
        while (subject.readNext() != null) {
            ++countProfiles2;
        }
        assertThat(countProfiles2, greaterThan(10L));
        assertTrue(countProfiles>countProfiles2);
    }

    @Test(expected = IllegalStateException.class)
    public void givenTheReaderHasBeenClosedReadNextShouldThrowIllegalStateException() throws Exception {
        //given
        subject.close();
        //when
        subject.readNext();
    }

    @Test
    public void closingTwiceShouldNotThrowException() throws Exception {
        //given
        subject.close();
        //when
        subject.close();
    }

}
