package uk.ac.ebi.atlas.trader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ConfigurationTraderIT {
    @Inject
    private DataFileHubFactory dataFileHubFactory;

    private ConfigurationTrader subject;

    @Before
    public void setUp() {
        subject = new ConfigurationTrader(dataFileHubFactory.getGxaDataFileHub());
    }

    @Test
    public void EProtOneHasTwoMenuFilterFactors() {
        BaselineExperimentConfiguration bc = subject.getBaselineFactorsConfiguration("E-PROT-1");
        assertThat(bc.getMenuFilterFactorTypes().size(), greaterThan(1));
    }
}