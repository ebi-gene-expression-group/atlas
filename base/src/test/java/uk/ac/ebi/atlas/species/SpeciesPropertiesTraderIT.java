package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class SpeciesPropertiesTraderIT {

    @Inject
    private SpeciesPropertiesTrader subject = new SpeciesPropertiesTrader();

    // The following tests are actually validating species-properties.json

    @Test
    public void propertiesAreInitialisedAfterBeanCreation() {
        assertThat(subject.getAll(), hasSize(greaterThan(50)));
        assertThat(subject.get("Homo sapiens").referenceName(), is("homo sapiens"));
        assertThat(subject.get("Homo sapiens").ensemblName(), is("Homo_sapiens"));
        assertThat(subject.get("Homo sapiens").kingdom(), is("animals"));
        assertThat(subject.get("Hordeum vulgare").getResourcesOfType("genome_browser"), hasSize(2));
    }

    public void plantsHaveTwoGenomeBrowsers() throws Exception {
        Collection<SpeciesProperties> allSpeciesProperties = subject.getAll();

        for (SpeciesProperties speciesProperties : allSpeciesProperties) {
            if ("plants".equalsIgnoreCase(speciesProperties.kingdom())) {
                assertThat(speciesProperties.getResourcesOfType("genome_browser"), hasSize(2));
            }
        }
    }

    @Test
    public void wbpsSpecies() throws Exception {
        SpeciesProperties caenorhabditisElegans = subject.get("Caenorhabditis elegans");
        SpeciesProperties schistosomaMansoni = subject.get("Schistosoma mansoni");

        assertThat(caenorhabditisElegans.defaultQueryFactorType(), is("DEVELOPMENTAL_STAGE"));
        assertThat(caenorhabditisElegans.getResourcesOfType("genome_browser"), hasSize(1));
        for (ImmutableMap<String, String> resource : caenorhabditisElegans.resources()) {
            if (resource.containsKey("type") && SpeciesProperties.GENOME_BROWSER_TYPE.equalsIgnoreCase(resource.get("type"))) {
                assertThat(resource, hasEntry(is("url"), startsWith("http://parasite")));
            }
        }

        assertThat(schistosomaMansoni.defaultQueryFactorType(), is("DEVELOPMENTAL_STAGE"));
        assertThat(schistosomaMansoni.getResourcesOfType("genome_browser"), hasSize(1));
        for (ImmutableMap<String, String> resource : caenorhabditisElegans.resources()) {
            if (resource.containsKey("type") && SpeciesProperties.GENOME_BROWSER_TYPE.equalsIgnoreCase(resource.get("type"))) {
                assertThat(resource, hasEntry(is("url"), startsWith("http://parasite")));
            }
        }
    }

    @Test
    public void exceptionsApply() {
        //http://www.ensembl.org/Canis_familiaris/Info/Index
        SpeciesProperties dog = subject.get("Canis lupus familiaris");

        assertNotEquals(dog, SpeciesProperties.UNKNOWN);
        assertThat(dog.ensemblName(), is("Canis_familiaris"));
    }
}