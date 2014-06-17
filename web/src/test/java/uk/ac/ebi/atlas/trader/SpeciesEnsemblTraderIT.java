package uk.ac.ebi.atlas.trader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SpeciesEnsemblTraderIT {

    @Inject
    private SpeciesEnsemblTrader subject;

    @Test
    public void caenorhabditisElegansReturnsMetazoa() throws Exception {
        assertThat(subject.getEnsemblDb("caenorhabditis elegans"), is("metazoa"));
    }

    @Test
    public void bosTaurusReturnsEnsembl() throws Exception {
        assertThat(subject.getEnsemblDb("bos taurus"), is("ensembl"));
    }

    @Test
    public void oryzaSativaJaponicaGroupReturnsPlants() throws Exception {
        assertThat(subject.getEnsemblDb("oryza sativa japonica group"), is("plants"));
    }


}
