package uk.ac.ebi.atlas.search.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class BaselineExperimentSearchResultProducerIT {
    private BaselineExperimentSearchResultProducer subject;

    @Inject
    private ExperimentTrader experimentTrader;

    @Before
    public void setUp() {
        subject = new BaselineExperimentSearchResultProducer(experimentTrader);
    }


    @Test
    public void weHaveTwoColumnsCorrespondingToAdultAndFetus() {
        BaselineExperiment eProt1 = (BaselineExperiment) experimentTrader.getPublicExperiment("E-PROT-1");

        Map<String, Map<String, Double>> expressionsPerColumnPerExperiment = new HashMap<>();

        Map<String, Double> fakeResults = new HashMap<>();
        for (AssayGroup assayGroup: eProt1.getDataColumnDescriptors()) {
            fakeResults.put(assayGroup.getId(), Math.random() * 10000);
        }
        expressionsPerColumnPerExperiment.put(eProt1.getAccession(), fakeResults);

        BaselineExperimentProfilesList result =
                subject.buildProfilesForExperiments(expressionsPerColumnPerExperiment, "ORGANISM_PART");
        assertThat(result.getFactorsAcrossExperiments().size(), lessThan(eProt1.getDataColumnDescriptors().size()));
        assertThat(result.size(), is(2));
    }
}
