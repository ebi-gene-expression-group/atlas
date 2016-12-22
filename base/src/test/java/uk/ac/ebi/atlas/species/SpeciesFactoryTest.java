package uk.ac.ebi.atlas.species;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class SpeciesFactoryTest {

    @Inject
    private SpeciesFactory subject;

    @Test
    public void unknownSpecies() {
        assertThat(subject.create("foobar").isUnknown(), is(true));
    }

//
//    @Test
//    public void plantsHaveTwoGenomeBrowsers() throws Exception {
//        List<SpeciesProperties> allSpeciesProperties = subject.fetchAll();
//
//        for (SpeciesProperties speciesProperties : allSpeciesProperties) {
//            if ("plants".equalsIgnoreCase(speciesProperties.kingdom())) {
//                assertThat(species.resources().get("genome_browser"), hasSize(2));
//            }
//        }
//    }


//    @Test
//    public void wbpsSpecies() throws Exception {
//        Species caenorhabditisElegans = subject.fetchSpecies("caenorhabditis_elegans");
//        Species schistosomaMansoni = subject.fetchSpecies("schistosoma_mansoni");
//
//        assertThat(caenorhabditisElegans.resources().get("genome_browser").get(0), startsWith("http://parasite"));
//        assertThat(schistosomaMansoni.resources().get("genome_browser").get(0), startsWith("http://parasite"));
//        assertThat(caenorhabditisElegans.defaultQueryFactorType(), is("DEVELOPMENTAL_STAGE"));
//        assertThat(schistosomaMansoni.defaultQueryFactorType(), is("DEVELOPMENTAL_STAGE"));
//    }
}