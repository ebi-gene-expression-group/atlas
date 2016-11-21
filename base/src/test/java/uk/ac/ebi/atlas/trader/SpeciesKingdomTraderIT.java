package uk.ac.ebi.atlas.trader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class SpeciesKingdomTraderIT {

    @Inject
    JdbcTemplate jdbcTemplate;

    private SpeciesKingdomTrader subject;

    @Before
    public void setUp(){
        this.subject = new SpeciesKingdomTrader(jdbcTemplate);
    }

    @Test
    public void caenorhabditisElegansKingdomIsAnimals() throws Exception {
        assertThat(subject.getKingdom("caenorhabditis elegans"), is("animals"));
    }

    @Test
    public void bosTaurusKingdomIsAnimals() throws Exception {
        assertThat(subject.getKingdom("bos taurus"), is("animals"));
    }

    @Test
    public void oryzaSativaJaponicaGroupKingdomIsPlants() throws Exception {
        assertThat(subject.getKingdom("oryza sativa japonica group"), is("plants"));
    }


}
