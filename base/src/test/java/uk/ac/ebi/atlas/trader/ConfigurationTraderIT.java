package uk.ac.ebi.atlas.trader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentConfiguration;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml","/oracleContext.xml"})
public class ConfigurationTraderIT {

    @Inject
    ConfigurationTrader subject;


    @Test
    public void EProtOneHasTwoMenuFilterFactors() throws Exception {

        BaselineExperimentConfiguration bc = subject.getBaselineFactorsConfiguration("E-PROT-1");

        assertThat(bc.getMenuFilterFactorTypes().size(), greaterThan(1));


    }
}