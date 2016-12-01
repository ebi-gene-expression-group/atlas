package uk.ac.ebi.atlas.species;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class SpeciesDaoIT {

    @Inject
    private SpeciesDao subject;

    @Test
    public void fetchAllSpecies() throws Exception {
        List<Species> allSpecies = subject.fetchAllSpecies();
        assertThat(allSpecies, hasSize(greaterThan(50)));
    }

    @Test
    public void fetchHomoSapines() throws Exception {
        Species species = subject.fetchSpecies("homo_sapiens");
        assertThat(species.name(), is("homo_sapiens"));
    }

    @Test
    public void plantsHaveTwoGenomeBrowsers() throws Exception {
        List<Species> allSpecies = subject.fetchAllSpecies();

        for (Species species : allSpecies) {
            if (species.isPlant()) {
                assertThat(species.resources().get("genome browser"), hasSize(2));
            }
        }
    }

    @Test
    public void wbpsSpecies() throws Exception {
        Species caenorhabditisElegans = subject.fetchSpecies("caenorhabditis_elegans");
        Species schistosomaMansoni = subject.fetchSpecies("schistosoma_mansoni");

        assertThat(caenorhabditisElegans.resources().get("genome browser").get(0), startsWith("http://parasite"));
        assertThat(schistosomaMansoni.resources().get("genome browser").get(0), startsWith("http://parasite"));
        assertThat(caenorhabditisElegans.defaultQueryFactorType(), is("DEVELOPMENTAL_STAGE"));
        assertThat(schistosomaMansoni.defaultQueryFactorType(), is("DEVELOPMENTAL_STAGE"));
    }
}